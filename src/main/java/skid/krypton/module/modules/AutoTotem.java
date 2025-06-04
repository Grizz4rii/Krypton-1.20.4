package skid.krypton.module.modules;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import skid.krypton.Krypton;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.Setting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;

public final class AutoTotem extends Module {
    private final NumberSetting c;
    private boolean d;
    private int e;

    public AutoTotem() {
        super(EncryptedString.a("Auto Totem"), EncryptedString.a("Automatically holds totem in your off hand"), -1, Category.a);
        this.c = new NumberSetting(EncryptedString.a("Delay"), 0.0, 5.0, 1.0, 1.0);
        this.a(new Setting[]{this.c});
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
        if (this.b.player == null) {
            return;
        }
        final Module moduleByClass = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(RtpBaseFinder.class);
        if (moduleByClass.isEnabled() && ((RtpBaseFinder) moduleByClass).j()) {
            return;
        }
        final Module moduleByClass2 = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(TunnelBaseFinder.class);
        if (moduleByClass2.isEnabled() && ((TunnelBaseFinder) moduleByClass2).j()) {
            return;
        }
        if (this.b.player.getInventory().getStack(40).getItem() == Items.TOTEM_OF_UNDYING) {
            this.e = this.c.getIntValue();
            return;
        }
        if (this.e > 0) {
            --this.e;
            return;
        }
        final int a = this.a(Items.TOTEM_OF_UNDYING);
        if (a == -1) {
            return;
        }
        this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, b(a), 40, SlotActionType.SWAP, this.b.player);
        this.e = this.c.getIntValue();
    }

    public int a(final Item item) {
        if (this.b.player == null) {
            return -1;
        }
        for (int i = 0; i < 36; ++i) {
            if (this.b.player.getInventory().getStack(i).isOf(item)) {
                return i;
            }
        }
        return -1;
    }

    private static int b(final int n) {
        if (n < 9) {
            return 36 + n;
        }
        return n;
    }
}
