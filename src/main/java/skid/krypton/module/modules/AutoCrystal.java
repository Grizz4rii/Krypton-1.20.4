// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PreItemUseEvent;
import skid.krypton.events.*;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BindSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.BlockUtil;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.KeyUtils;

public final class AutoCrystal extends Module {
    private final BindSetting d;
    private final NumberSetting e;
    private final NumberSetting f;
    private int keybind;
    private int h;
    public boolean c;

    public AutoCrystal() {
        super(EncryptedString.a("Auto Crystal"), EncryptedString.a("Automatically crystals fast for you"), -1, Category.a);
        this.d = new BindSetting(EncryptedString.a("Activate Key"), 1, false).a(EncryptedString.a("Key that does the crystalling"));
        this.e = new NumberSetting(EncryptedString.a("Place Delay"), 0.0, 20.0, 0.0, 1.0);
        this.f = new NumberSetting(EncryptedString.a("Break Delay"), 0.0, 20.0, 0.0, 1.0);
        this.a(this.d, this.e, this.f);
    }

    @Override
    public void onEnable() {
        this.j();
        this.c = false;
        super.onEnable();
    }

    private void j() {
        this.keybind = 0;
        this.h = 0;
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.b.currentScreen != null) {
            return;
        }
        this.k();
        if (this.b.player.method_6115()) {
            return;
        }
        if (!this.l()) {
            return;
        }
        if (this.b.player.method_6047().getItem() != Items.END_CRYSTAL) {
            return;
        }
        this.m();
    }

    private void k() {
        if (this.keybind > 0) {
            --this.keybind;
        }
        if (this.h > 0) {
            --this.h;
        }
    }

    private boolean l() {
        final int d = this.d.d();
        if (d != -1 && !KeyUtils.b(d)) {
            this.j();
            return this.c = false;
        }
        return this.c = true;
    }

    private void m() {
        final HitResult crosshairTarget = this.b.crosshairTarget;
        if (this.b.crosshairTarget instanceof BlockHitResult) {
            this.a((BlockHitResult) crosshairTarget);
        } else if (this.b.crosshairTarget instanceof final EntityHitResult entityHitResult) {
            this.a(entityHitResult);
        }
    }

    private void a(final BlockHitResult blockHitResult) {
        if (blockHitResult.method_17783() != HitResult$Type.BLOCK) {
            return;
        }
        if (this.keybind > 0) {
            return;
        }
        final BlockPos blockPos = blockHitResult.getBlockPos();
        if ((BlockUtil.a(blockPos, Blocks.OBSIDIAN) || BlockUtil.a(blockPos, Blocks.BEDROCK)) && this.a(blockPos)) {
            BlockUtil.a(blockHitResult, true);
            this.keybind = this.e.f();
        }
    }

    private void a(final EntityHitResult entityHitResult) {
        if (this.h > 0) {
            return;
        }
        final Entity entity = entityHitResult.getEntity();
        if (!(entity instanceof EndCrystalEntity) && !(entity instanceof SlimeEntity)) {
            return;
        }
        this.b.interactionManager.attackEntity(this.b.player, entity);
        this.b.player.method_6104(Hand.MAIN_HAND);
        this.h = this.f.f();
    }

    @EventListener
    public void a(final PreItemUseEvent preItemUseEvent) {
        if (this.b.player.method_6047().getItem() != Items.END_CRYSTAL) {
            return;
        }
        if (!(this.b.crosshairTarget instanceof BlockHitResult blockHitResult)) {
            return;
        }
        if (((BlockHitResult) this.b.crosshairTarget).method_17783() != HitResult$Type.BLOCK) {
            return;
        }
        final BlockPos blockPos = blockHitResult.getBlockPos();
        if (BlockUtil.a(blockPos, Blocks.OBSIDIAN) || BlockUtil.a(blockPos, Blocks.BEDROCK)) {
            preItemUseEvent.cancel();
        }
    }

    private boolean a(final BlockPos blockPos) {
        final BlockPos up = blockPos.up();
        if (!this.b.world.method_22347(up)) {
            return false;
        }
        final int method_10263 = up.method_10263();
        final int method_10264 = up.method_10264();
        final int method_10265 = up.method_10260();
        return this.b.world.method_8335((Entity) null, new Box(method_10263, method_10264, method_10265, method_10263 + 1.0, method_10264 + 2.0, method_10265 + 1.0)).isEmpty();
    }
}
