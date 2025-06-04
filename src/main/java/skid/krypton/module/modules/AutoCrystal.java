package skid.krypton.module.modules;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PreItemUseEvent;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BindSetting;
import skid.krypton.module.setting.NumberSetting;
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
        super(EncryptedString.of("Auto Crystal"), EncryptedString.of("Automatically crystals fast for you"), -1, Category.COMBAT);
        this.d = new BindSetting(EncryptedString.of("Activate Key"), 1, false).setDescription(EncryptedString.of("Key that does the crystalling"));
        this.e = new NumberSetting(EncryptedString.of("Place Delay"), 0.0, 20.0, 0.0, 1.0);
        this.f = new NumberSetting(EncryptedString.of("Break Delay"), 0.0, 20.0, 0.0, 1.0);
        this.addSettings(this.d, this.e, this.f);
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
        if (this.mc.currentScreen != null) {
            return;
        }
        this.k();
        if (this.mc.player.isUsingItem()) {
            return;
        }
        if (!this.l()) {
            return;
        }
        if (this.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL) {
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
        final int d = this.d.getValue();
        if (d != -1 && !KeyUtils.b(d)) {
            this.j();
            return this.c = false;
        }
        return this.c = true;
    }

    private void m() {
        final HitResult crosshairTarget = this.mc.crosshairTarget;
        if (this.mc.crosshairTarget instanceof BlockHitResult) {
            this.a((BlockHitResult) crosshairTarget);
        } else if (this.mc.crosshairTarget instanceof final EntityHitResult entityHitResult) {
            this.a(entityHitResult);
        }
    }

    private void a(final BlockHitResult blockHitResult) {
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }
        if (this.keybind > 0) {
            return;
        }
        final BlockPos blockPos = blockHitResult.getBlockPos();
        if ((BlockUtil.a(blockPos, Blocks.OBSIDIAN) || BlockUtil.a(blockPos, Blocks.BEDROCK)) && this.a(blockPos)) {
            BlockUtil.a(blockHitResult, true);
            this.keybind = this.e.getIntValue();
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
        this.mc.interactionManager.attackEntity(this.mc.player, entity);
        this.mc.player.swingHand(Hand.MAIN_HAND);
        this.h = this.f.getIntValue();
    }

    @EventListener
    public void a(final PreItemUseEvent preItemUseEvent) {
        if (this.mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL) {
            return;
        }
        if (!(this.mc.crosshairTarget instanceof BlockHitResult blockHitResult)) {
            return;
        }
        if (this.mc.crosshairTarget.getType() != HitResult.Type.BLOCK) {
            return;
        }
        final BlockPos blockPos = blockHitResult.getBlockPos();
        if (BlockUtil.a(blockPos, Blocks.OBSIDIAN) || BlockUtil.a(blockPos, Blocks.BEDROCK)) {
            preItemUseEvent.cancel();
        }
    }

    private boolean a(final BlockPos blockPos) {
        final BlockPos up = blockPos.up();
        if (!this.mc.world.isAir(up)) {
            return false;
        }
        final int getX = up.getX();
        final int getY = up.getY();
        final int compareTo = up.getZ();
        return this.mc.world.getOtherEntities(null, new Box(getX, getY, compareTo, getX + 1.0, getY + 2.0, compareTo + 1.0)).isEmpty();
    }
}
