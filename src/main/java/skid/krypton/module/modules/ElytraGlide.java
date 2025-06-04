package skid.krypton.module.modules;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PreItemUseEvent;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;

public final class ElytraGlide extends Module {
    private boolean c;
    private boolean d;
    private boolean e;

    public ElytraGlide() {
        super(EncryptedString.of("Elytra Glide"), EncryptedString.of("Starts flying when attempting to use a firework"), -1, Category.MISC);
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
    public void a(final TickEvent event) {
        if (this.mc.currentScreen != null) {
            return;
        }
        if (this.e) {
            this.e = false;
            KeyBinding.setKeyPressed(this.mc.options.jumpKey.getDefaultKey(), false);
            return;
        }
        if (this.c) {
            if (this.d) {
                final int selectedSlot = this.mc.player.getInventory().selectedSlot;
                if (!this.mc.player.getMainHandStack().isOf(Items.FIREWORK_ROCKET) && !InventoryUtil.a(Items.FIREWORK_ROCKET)) {
                    return;
                }
                this.mc.interactionManager.interactItem(this.mc.player, this.mc.player.getActiveHand());
                this.mc.player.getInventory().selectedSlot = selectedSlot;
                this.d = false;
                this.c = false;
            } else if (this.mc.player.isOnGround()) {
                KeyBinding.setKeyPressed(this.mc.options.jumpKey.getDefaultKey(), true);
                this.e = true;
            } else {
                KeyBinding.setKeyPressed(this.mc.options.jumpKey.getDefaultKey(), true);
                this.e = true;
                this.d = true;
            }
        }
    }

    @EventListener
    public void a(final PreItemUseEvent event) {
        if (!mc.player.getMainHandStack().isOf(Items.FIREWORK_ROCKET) || !mc.player.getInventory().getArmorStack(EquipmentSlot.CHEST.getEntitySlotId()).isOf(Items.ELYTRA) || !mc.player.isFallFlying()) return;
        this.c = true;
    }
}