// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import skid.krypton.event.EventListener;
import skid.krypton.events.*;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BindSetting;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;
import skid.krypton.utils.KeyUtils;

public final class KeyPearl extends Module {
    private final BindSetting c;
    private final NumberSetting d;
    private final BooleanSetting e;
    private final NumberSetting f;
    private boolean g;
    private boolean h;
    private int i;
    private int j;
    private int k;

    public KeyPearl() {
        super(EncryptedString.a("Key Pearl"), EncryptedString.a("Switches to an ender pearl and throws it when you press a bind"), -1, Category.b);
        this.c = new BindSetting(EncryptedString.a("Activate Key"), -1, false);
        this.d = new NumberSetting(EncryptedString.a("Delay"), 0.0, 20.0, 0.0, 1.0);
        this.e = new BooleanSetting(EncryptedString.a("Switch Back"), true);
        this.f = new NumberSetting(EncryptedString.a("Switch Delay"), 0.0, 20.0, 0.0, 1.0).a(EncryptedString.a("Delay after throwing pearl before switching back"));
        this.a(this.c, this.d, this.e, this.f);
    }

    @Override
    public void onEnable() {
        this.k();
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
        if (KeyUtils.b(this.c.d())) {
            this.g = true;
        }
        if (this.g) {
            if (this.j == -1) {
                this.j = this.b.player.method_31548().selectedSlot;
            }
            InventoryUtil.a(Items.ENDER_PEARL);
            if (this.i < this.d.f()) {
                ++this.i;
                return;
            }
            if (!this.h) {
                final ActionResult interactItem = this.b.interactionManager.interactItem(this.b.player, Hand.MAIN_HAND);
                if (interactItem.isAccepted() && interactItem.shouldSwingHand()) {
                    this.b.player.method_6104(Hand.MAIN_HAND);
                }
                this.h = true;
            }
            if (this.e.c()) {
                this.j();
            } else {
                this.k();
            }
        }
    }

    private void j() {
        if (this.k < this.f.f()) {
            ++this.k;
            return;
        }
        InventoryUtil.a(this.j);
        this.k();
    }

    private void k() {
        this.j = -1;
        this.i = 0;
        this.k = 0;
        this.g = false;
        this.h = false;
    }
}
