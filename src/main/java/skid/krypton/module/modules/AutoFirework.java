// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PostItemUseEvent;
import skid.krypton.events.*;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BindSetting;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;
import skid.krypton.utils.KeyUtils;

public final class AutoFirework extends Module {
    private final BindSetting c;
    private final NumberSetting d;
    private final BooleanSetting e;
    private final NumberSetting f;
    private boolean g;
    private boolean h;
    private int i;
    private int j;
    private int k;
    private int l;

    public AutoFirework() {
        super(EncryptedString.a("Auto Firework"), EncryptedString.a("Switches to a firework and uses it when you press a bind."), -1, Category.b);
        this.c = new BindSetting(EncryptedString.a("Activate Key"), -1, false);
        this.d = new NumberSetting(EncryptedString.a("Delay"), 0.0, 20.0, 0.0, 1.0);
        this.e = new BooleanSetting(EncryptedString.a("Switch Back"), true);
        this.f = new NumberSetting(EncryptedString.a("Switch Delay"), 0.0, 20.0, 0.0, 1.0).a(EncryptedString.a("Delay after using firework before switching back."));
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
        if (this.l > 0) {
            --this.l;
            return;
        }
        if (this.b.player != null && KeyUtils.b(this.c.d()) && (Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(ElytraGlide.class).isEnabled() || this.b.player.method_6128()) && this.b.player.method_31548().getArmorStack(2).isOf(Items.ELYTRA) && !this.b.player.method_31548().getMainHandStack().isOf(Items.FIREWORK_ROCKET) && !this.b.player.method_6047().getItem().getComponents().contains(DataComponentTypes.FOOD) && !(this.b.player.method_6047().getItem() instanceof ArmorItem)) {
            this.g = true;
        }
        if (this.g) {
            if (this.j == -1) {
                this.j = this.b.player.method_31548().selectedSlot;
            }
            if (!InventoryUtil.a(Items.FIREWORK_ROCKET)) {
                this.k();
                return;
            }
            if (this.i < this.d.f()) {
                ++this.i;
                return;
            }
            if (!this.h) {
                this.b.interactionManager.interactItem(this.b.player, Hand.MAIN_HAND);
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
        this.l = 4;
        this.g = false;
        this.h = false;
    }

    @EventListener
    public void a(final PostItemUseEvent postItemUseEvent) {
        if (this.b.player.method_6047().isOf(Items.FIREWORK_ROCKET)) {
            this.h = true;
        }
        if (this.l > 0) {
            postItemUseEvent.cancel();
        }
    }

    private static byte[] hzvoneomkmqudhx() {
        return new byte[]{126, 93, 45, 64, 126, 5, 56, 66, 14, 81, 6, 31, 3, 10, 114, 126, 122, 55, 114, 52, 34, 7, 38, 111, 10, 114, 113, 21, 99, 24, 65, 123, 49, 108, 113, 120, 104, 102, 32, 40, 84, 34, 52, 11, 112, 79, 8, 53, 5, 114, 8, 67, 111, 66, 12, 28, 124, 86, 79, 85, 69, 108, 5, 111, 20, 63, 91, 13, 105, 126, 4};
    }
}
