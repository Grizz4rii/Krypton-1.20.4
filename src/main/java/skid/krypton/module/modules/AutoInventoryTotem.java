// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import skid.krypton.events.*;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
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
        super(EncryptedString.a("Auto Inv Totem"), EncryptedString.a("Automatically equips a totem in your offhand and main hand if empty"), -1, Category.a);
        this.e = new NumberSetting(EncryptedString.a("Delay"), 0.0, 20.0, 0.0, 1.0);
        this.f = new BooleanSetting(EncryptedString.a("Hotbar"), true).a(EncryptedString.a("Puts a totem in your hotbar as well, if enabled (Setting below will work if this is enabled)"));
        this.g = new NumberSetting(EncryptedString.a("Totem Slot"), 1.0, 9.0, 1.0, 1.0).a(EncryptedString.a("Your preferred totem slot"));
        this.h = new BooleanSetting(EncryptedString.a("Auto Switch"), false).a(EncryptedString.a("Switches to totem slot when going inside the inventory"));
        this.i = new BooleanSetting(EncryptedString.a("Force Totem"), false).a(EncryptedString.a("Puts the totem in the slot, regardless if its space is taken up by something else"));
        this.j = new BooleanSetting(EncryptedString.a("Auto Open"), false).a(EncryptedString.a("Automatically opens and closes the inventory for you"));
        this.k = new NumberSetting(EncryptedString.a("Stay Open For"), 0.0, 20.0, 0.0, 1.0);
        this.c = -1;
        this.d = -1;
        this.a(this.e, this.f, this.g, this.h, this.i, this.j, this.k);
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
        if (this.k() && this.j.c()) {
            this.b.setScreen(new CustomInventoryScreen(this.b.player));
        }
        if (!(this.b.currentScreen instanceof InventoryScreen) && !(this.b.currentScreen instanceof CustomInventoryScreen)) {
            this.c = -1;
            this.d = -1;
            return;
        }
        if (this.c == -1) {
            this.c = this.e.f();
        }
        if (this.d == -1) {
            this.d = this.k.f();
        }
        if (this.c > 0) {
            --this.c;
        }
        final PlayerInventory method_31548 = this.b.player.method_31548();
        if (this.h.c()) {
            method_31548.selectedSlot = this.g.f() - 1;
        }
        if (this.c <= 0) {
            if (method_31548.offHand.get(0).getItem() != Items.TOTEM_OF_UNDYING) {
                final int l = this.l();
                if (l != -1) {
                    this.b.interactionManager.clickSlot(((PlayerScreenHandler) ((InventoryScreen) this.b.currentScreen).method_17577()).field_7763, l, 40, SlotActionType.SWAP, (PlayerEntity) this.b.player);
                    return;
                }
            }
            if (this.f.c()) {
                final ItemStack method_31549 = this.b.player.method_6047();
                if (method_31549.isEmpty() || (this.i.c() && method_31549.getItem() != Items.TOTEM_OF_UNDYING)) {
                    final int i = this.l();
                    if (i != -1) {
                        this.b.interactionManager.clickSlot(((PlayerScreenHandler) ((InventoryScreen) this.b.currentScreen).method_17577()).field_7763, i, method_31548.selectedSlot, SlotActionType.SWAP, (PlayerEntity) this.b.player);
                        return;
                    }
                }
            }
            if (this.j() && this.j.c()) {
                if (this.d != 0) {
                    --this.d;
                    return;
                }
                this.b.currentScreen.close();
                this.d = this.k.f();
            }
        }
    }

    public boolean j() {
        if (this.f.c()) {
            return this.b.player.method_31548().method_5438(this.g.f() - 1).getItem() == Items.TOTEM_OF_UNDYING && this.b.player.method_6079().getItem() == Items.TOTEM_OF_UNDYING && this.b.currentScreen instanceof CustomInventoryScreen;
        }
        return this.b.player.method_6079().getItem() == Items.TOTEM_OF_UNDYING && this.b.currentScreen instanceof CustomInventoryScreen;
    }

    public boolean k() {
        if (this.f.c()) {
            return (this.b.player.method_6079().getItem() != Items.TOTEM_OF_UNDYING || this.b.player.method_31548().method_5438(this.g.f() - 1).getItem() != Items.TOTEM_OF_UNDYING) && !(this.b.currentScreen instanceof CustomInventoryScreen) && this.a(item -> item == Items.TOTEM_OF_UNDYING) != 0;
        }
        return this.b.player.method_6079().getItem() != Items.TOTEM_OF_UNDYING && !(this.b.currentScreen instanceof CustomInventoryScreen) && this.a(item2 -> item2 == Items.TOTEM_OF_UNDYING) != 0;
    }

    private int l() {
        final PlayerInventory method_31548 = this.b.player.method_31548();
        new Random();
        final ArrayList list = new ArrayList();
        while (true) {
            if (method_31548.main.get(9).getItem() == Items.TOTEM_OF_UNDYING) {
                list.add(9);
            }
        }
    }

    private int a(final Predicate predicate) {
        int n = 0;
        while (true) {
            final ItemStack method_5438 = this.b.player.method_31548().method_5438(9);
            if (predicate.test(method_5438.getItem())) {
                n += method_5438.getCount();
            }
        }
    }
}
