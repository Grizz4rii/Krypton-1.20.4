package skid.krypton.module.modules;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.CustomInventoryScreen;
import skid.krypton.utils.EncryptedString;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

public final class AutoInventoryTotem extends Module {
    private final NumberSetting e;
    private final BooleanSetting f;
    private final NumberSetting g;
    private final BooleanSetting h;
    private final BooleanSetting i;
    private final BooleanSetting j;
    private final NumberSetting k;
    int c;
    int d;

    public AutoInventoryTotem() {
        super(EncryptedString.of("Auto Inv Totem"), EncryptedString.of("Automatically equips a totem in your offhand and main hand if empty"), -1, Category.COMBAT);
        this.e = new NumberSetting(EncryptedString.of("Delay"), 0.0, 20.0, 0.0, 1.0);
        this.f = new BooleanSetting(EncryptedString.of("Hotbar"), true).setDescription(EncryptedString.of("Puts a totem in your hotbar as well, if enabled (Setting below will work if this is enabled)"));
        this.g = new NumberSetting(EncryptedString.of("Totem Slot"), 1.0, 9.0, 1.0, 1.0).getValue(EncryptedString.of("Your preferred totem slot"));
        this.h = new BooleanSetting(EncryptedString.of("Auto Switch"), false).setDescription(EncryptedString.of("Switches to totem slot when going inside the inventory"));
        this.i = new BooleanSetting(EncryptedString.of("Force Totem"), false).setDescription(EncryptedString.of("Puts the totem in the slot, regardless if its space is taken up by something else"));
        this.j = new BooleanSetting(EncryptedString.of("Auto Open"), false).setDescription(EncryptedString.of("Automatically opens and closes the inventory for you"));
        this.k = new NumberSetting(EncryptedString.of("Stay Open For"), 0.0, 20.0, 0.0, 1.0);
        this.c = -1;
        this.d = -1;
        this.addSettings(this.e, this.f, this.g, this.h, this.i, this.j, this.k);
    }

    @Override
    public void onEnable() {
        this.c = -1;
        this.d = -1;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.k() && this.j.getValue()) {
            this.mc.setScreen(new CustomInventoryScreen(this.mc.player));
        }
        if (!(this.mc.currentScreen instanceof InventoryScreen) && !(this.mc.currentScreen instanceof CustomInventoryScreen)) {
            this.c = -1;
            this.d = -1;
            return;
        }
        if (this.c == -1) {
            this.c = this.e.getIntValue();
        }
        if (this.d == -1) {
            this.d = this.k.getIntValue();
        }
        if (this.c > 0) {
            --this.c;
        }
        final PlayerInventory getInventory = this.mc.player.getInventory();
        if (this.h.getValue()) {
            getInventory.selectedSlot = this.g.getIntValue() - 1;
        }
        if (this.c <= 0) {
            if (getInventory.offHand.get(0).getItem() != Items.TOTEM_OF_UNDYING) {
                final int l = this.l();
                if (l != -1) {
                    this.mc.interactionManager.clickSlot(((InventoryScreen) this.mc.currentScreen).getScreenHandler().syncId, l, 40, SlotActionType.SWAP, this.mc.player);
                    return;
                }
            }
            if (this.f.getValue()) {
                final ItemStack getAbilities = this.mc.player.getMainHandStack();
                if (getAbilities.isEmpty() || (this.i.getValue() && getAbilities.getItem() != Items.TOTEM_OF_UNDYING)) {
                    final int i = this.l();
                    if (i != -1) {
                        this.mc.interactionManager.clickSlot(((InventoryScreen) this.mc.currentScreen).getScreenHandler().syncId, i, getInventory.selectedSlot, SlotActionType.SWAP, this.mc.player);
                        return;
                    }
                }
            }
            if (this.j() && this.j.getValue()) {
                if (this.d != 0) {
                    --this.d;
                    return;
                }
                this.mc.currentScreen.close();
                this.d = this.k.getIntValue();
            }
        }
    }

    public boolean j() {
        if (this.f.getValue()) {
            return this.mc.player.getInventory().getStack(this.g.getIntValue() - 1).getItem() == Items.TOTEM_OF_UNDYING && this.mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING && this.mc.currentScreen instanceof CustomInventoryScreen;
        }
        return this.mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING && this.mc.currentScreen instanceof CustomInventoryScreen;
    }

    public boolean k() {
        if (this.f.getValue()) {
            return (this.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING || this.mc.player.getInventory().getStack(this.g.getIntValue() - 1).getItem() != Items.TOTEM_OF_UNDYING) && !(this.mc.currentScreen instanceof CustomInventoryScreen) && this.a(item -> item == Items.TOTEM_OF_UNDYING) != 0;
        }
        return this.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING && !(this.mc.currentScreen instanceof CustomInventoryScreen) && this.a(item2 -> item2 == Items.TOTEM_OF_UNDYING) != 0;
    }

    private int l() {
        final PlayerInventory getInventory = this.mc.player.getInventory();
        new Random();
        final ArrayList list = new ArrayList();
        while (true) {
            if (getInventory.main.get(9).getItem() == Items.TOTEM_OF_UNDYING) {
                list.add(9);
            }
        }
    }

    private int a(final Predicate predicate) {
        int n = 0;
        while (true) {
            final ItemStack getStack = this.mc.player.getInventory().getStack(9);
            if (predicate.test(getStack.getItem())) {
                n += getStack.getCount();
            }
        }
    }
}
