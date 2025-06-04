// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.mixin.HandledScreenMixin;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;

public final class HoverTotem extends Module {
    private final NumberSetting c;
    private final BooleanSetting d;
    private final NumberSetting e;
    private final BooleanSetting f;
    private int keybind;

    public HoverTotem() {
        super(EncryptedString.a("Hover Totem"), EncryptedString.a("Equips a totem in offhand and optionally hotbar when hovering over one in inventory"), -1, Category.a);
        this.c = new NumberSetting(EncryptedString.a("Tick Delay"), 0.0, 20.0, 0.0, 1.0).a(EncryptedString.a("Ticks to wait between operations"));
        this.d = new BooleanSetting(EncryptedString.a("Hotbar Totem"), true).a(EncryptedString.a("Also places a totem in your preferred hotbar slot"));
        this.e = new NumberSetting(EncryptedString.a("Hotbar Slot"), 1.0, 9.0, 1.0, 1.0).a(EncryptedString.a("Your preferred hotbar slot for totem (1-9)"));
        this.f = new BooleanSetting(EncryptedString.a("Auto Switch To Totem"), false).a(EncryptedString.a("Automatically switches to totem slot when inventory is opened"));
        this.a(this.c, this.d, this.e, this.f);
    }

    @Override
    public void onEnable() {
        this.j();
        super.onEnable();
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.b.player == null) {
            return;
        }
        final Screen currentScreen = this.b.currentScreen;
        if (!(this.b.currentScreen instanceof InventoryScreen)) {
            this.j();
            return;
        }
        final Slot focusedSlot = ((HandledScreenMixin) currentScreen).getFocusedSlot();
        if (focusedSlot == null || focusedSlot.getIndex() > 35) {
            return;
        }
        if (this.f.c()) {
            this.b.player.getInventory().selectedSlot = this.e.f() - 1;
        }
        if (focusedSlot.getStack().getItem() != Items.TOTEM_OF_UNDYING) {
            return;
        }
        if (this.keybind > 0) {
            --this.keybind;
            return;
        }
        final int index = focusedSlot.getIndex();
        final int syncId = ((InventoryScreen) currentScreen).getScreenHandler().syncId;
        if (!this.b.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
            this.a(syncId, index);
            return;
        }
        if (this.d.c()) {
            final int n = this.e.f() - 1;
            if (!this.b.player.getInventory().getStack(n).isOf(Items.TOTEM_OF_UNDYING)) {
                this.a(syncId, index, n);
            }
        }
    }

    private void a(final int n, final int n2) {
        this.b.interactionManager.clickSlot(n, n2, 40, SlotActionType.SWAP, this.b.player);
        this.j();
    }

    private void a(final int n, final int n2, final int n3) {
        this.b.interactionManager.clickSlot(n, n2, n3, SlotActionType.SWAP, this.b.player);
        this.j();
    }

    private void j() {
        this.keybind = this.c.f();
    }
}
