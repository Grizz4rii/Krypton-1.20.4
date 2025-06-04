// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PreItemUseEvent;
import skid.krypton.events.*;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;

public final class ElytraGlide extends Module {
    private boolean c;
    private boolean d;
    private boolean e;

    public ElytraGlide() {
        super(EncryptedString.a("Elytra Glide"), EncryptedString.a("Starts flying when attempting to use a firework"), -1, Category.b);
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
        if (this.e) {
            this.e = false;
            KeyBinding.setKeyPressed(this.b.options.jumpKey.getDefaultKey(), false);
            return;
        }
        if (this.c) {
            if (this.d) {
                final int selectedSlot = this.b.player.method_31548().selectedSlot;
                if (!this.b.player.method_6047().isOf(Items.FIREWORK_ROCKET) && !InventoryUtil.a(Items.FIREWORK_ROCKET)) {
                    return;
                }
                this.b.interactionManager.interactItem((PlayerEntity) this.b.player, this.b.player.method_6058());
                this.b.player.method_31548().selectedSlot = selectedSlot;
                this.d = false;
                this.c = false;
            } else if (this.b.player.method_24828()) {
                KeyBinding.setKeyPressed(this.b.options.jumpKey.getDefaultKey(), true);
                this.e = true;
            } else {
                KeyBinding.setKeyPressed(this.b.options.jumpKey.getDefaultKey(), true);
                this.e = true;
                this.d = true;
            }
        }
    }

    @EventListener
    public void a(final PreItemUseEvent preItemUseEvent) {
        if (this.b.player.method_6047().getItem().equals(Items.FIREWORK_ROCKET) && this.b.player.method_31548().getArmorStack(2).isOf(Items.ELYTRA) && !this.b.player.method_6128()) {
            this.c = true;
        }
    }

    private static byte[] hagehazugatgdaa() {
        return new byte[]{120, 95, 121, 122, 80, 66, 65, 72, 70, 8, 97, 78, 126, 34, 53, 100, 36, 15, 98, 59, 8, 1, 14, 114, 75, 13, 17, 24, 42, 38, 100, 25, 58, 110, 14, 92, 4, 105, 80, 37, 17, 85, 60, 68, 84, 19, 45, 68, 40, 41, 74, 68, 63, 74, 18, 7, 38, 47, 119, 22, 111, 61, 98, 30, 19, 16, 71, 26, 109, 98, 39};
    }
}
