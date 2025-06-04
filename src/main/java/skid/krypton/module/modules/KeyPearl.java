package skid.krypton.module.modules;

import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BindSetting;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.NumberSetting;
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
        super(EncryptedString.of("Key Pearl"), EncryptedString.of("Switches to an ender pearl and throws it when you press a bind"), -1, Category.MISC);
        this.c = new BindSetting(EncryptedString.of("Activate Key"), -1, false);
        this.d = new NumberSetting(EncryptedString.of("Delay"), 0.0, 20.0, 0.0, 1.0);
        this.e = new BooleanSetting(EncryptedString.of("Switch Back"), true);
        this.f = new NumberSetting(EncryptedString.of("Switch Delay"), 0.0, 20.0, 0.0, 1.0).getValue(EncryptedString.of("Delay after throwing pearl before switching back"));
        this.addSettings(this.c, this.d, this.e, this.f);
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
        if (this.mc.currentScreen != null) {
            return;
        }
        if (KeyUtils.b(this.c.getValue())) {
            this.g = true;
        }
        if (this.g) {
            if (this.j == -1) {
                this.j = this.mc.player.getInventory().selectedSlot;
            }
            InventoryUtil.a(Items.ENDER_PEARL);
            if (this.i < this.d.getIntValue()) {
                ++this.i;
                return;
            }
            if (!this.h) {
                final ActionResult interactItem = this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
                if (interactItem.isAccepted() && interactItem.shouldSwingHand()) {
                    this.mc.player.swingHand(Hand.MAIN_HAND);
                }
                this.h = true;
            }
            if (this.e.getValue()) {
                this.j();
            } else {
                this.k();
            }
        }
    }

    private void j() {
        if (this.k < this.f.getIntValue()) {
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
