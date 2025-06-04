// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import skid.krypton.event.EventListener;
import skid.krypton.events.*;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BindSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.BlockUtil;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;
import skid.krypton.utils.KeyUtils;

public final class DoubleAnchor extends Module {
    private final BindSetting c;
    private final NumberSetting d;
    private final NumberSetting e;
    private int f;
    private int keybind;
    private boolean h;

    public DoubleAnchor() {
        super(EncryptedString.a("Double Anchor"), EncryptedString.a("Automatically Places 2 anchors"), -1, Category.a);
        this.c = new BindSetting(EncryptedString.a("Activate Key"), 71, false).a(EncryptedString.a("Key that starts double anchoring"));
        this.d = new NumberSetting(EncryptedString.a("Switch Delay"), 0.0, 20.0, 0.0, 1.0);
        this.e = new NumberSetting(EncryptedString.a("Totem Slot"), 1.0, 9.0, 1.0, 1.0);
        this.f = 0;
        this.keybind = 0;
        this.h = false;
        this.a(this.d, this.e, this.c);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.b.currentScreen != null) {
            return;
        }
        if (this.b.player == null) {
            return;
        }
        if (!this.k()) {
            return;
        }
        if (!this.h && !this.l()) {
            return;
        }
        final HitResult crosshairTarget = this.b.crosshairTarget;
        if (!(this.b.crosshairTarget instanceof BlockHitResult) || BlockUtil.a(((BlockHitResult) crosshairTarget).getBlockPos(), Blocks.AIR)) {
            this.h = false;
            this.m();
            return;
        }
        if (this.f < this.d.f()) {
            ++this.f;
            return;
        }
        if (this.keybind == 0) {
            InventoryUtil.a(Items.RESPAWN_ANCHOR);
        } else if (this.keybind == 1) {
            BlockUtil.a((BlockHitResult) crosshairTarget, true);
        } else if (this.keybind == 2) {
            InventoryUtil.a(Items.GLOWSTONE);
        } else if (this.keybind == 3) {
            BlockUtil.a((BlockHitResult) crosshairTarget, true);
        } else if (this.keybind == 4) {
            InventoryUtil.a(Items.RESPAWN_ANCHOR);
        } else if (this.keybind == 5) {
            BlockUtil.a((BlockHitResult) crosshairTarget, true);
            BlockUtil.a((BlockHitResult) crosshairTarget, true);
        } else if (this.keybind == 6) {
            InventoryUtil.a(Items.GLOWSTONE);
        } else if (this.keybind == 7) {
            BlockUtil.a((BlockHitResult) crosshairTarget, true);
        } else if (this.keybind == 8) {
            InventoryUtil.a(this.e.f() - 1);
        } else if (this.keybind == 9) {
            BlockUtil.a((BlockHitResult) crosshairTarget, true);
        } else if (this.keybind == 10) {
            this.h = false;
            this.keybind = 0;
            this.m();
            return;
        }
        ++this.keybind;
    }

    private boolean k() {
        boolean b = false;
        boolean b2 = false;
        for (int i = 0; i < 9; ++i) {
            final ItemStack method_5438 = this.b.player.method_31548().method_5438(i);
            if (method_5438.getItem().equals(Items.RESPAWN_ANCHOR)) {
                b = true;
            }
            if (method_5438.getItem().equals(Items.GLOWSTONE)) {
                b2 = true;
            }
        }
        return b && b2;
    }

    private boolean l() {
        final int d = this.c.d();
        if (d == -1 || !KeyUtils.b(d)) {
            this.m();
            return false;
        }
        return this.h = true;
    }

    private void m() {
        this.f = 0;
    }

    public boolean j() {
        return this.h;
    }
}
