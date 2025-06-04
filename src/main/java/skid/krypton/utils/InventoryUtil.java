// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.utils;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import skid.krypton.Krypton;
import skid.krypton.mixin.ClientPlayerInteractionManagerAccessor;

import java.util.function.Predicate;

public final class InventoryUtil {
    public static void a(final int selectedSlot) {
        if (selectedSlot < 0 || selectedSlot > 8) {
            return;
        }
        Krypton.e.player.getInventory().selectedSlot = selectedSlot;
        ((ClientPlayerInteractionManagerAccessor) Krypton.e.interactionManager).syncSlot();
    }

    public static boolean a(final Predicate<ItemStack> predicate) {
        final PlayerInventory getInventory = Krypton.e.player.getInventory();
        for (int i = 0; i < 9; ++i) {
            if (predicate.test(getInventory.getStack(i))) {
                getInventory.selectedSlot = i;
                return true;
            }
        }
        return false;
    }

    public static boolean b(final Predicate<Item> predicate) {
        final PlayerInventory getInventory = Krypton.e.player.getInventory();
        for (int i = 0; i < 9; ++i) {
            if (predicate.test(getInventory.getStack(i).getItem())) {
                getInventory.selectedSlot = i;
                return true;
            }
        }
        return false;
    }

    public static boolean a(Item item) {
        return InventoryUtil.b((Item item2) -> item2 == item);
    }

    public static int b(final Item obj) {
        final ScreenHandler currentScreenHandler = Krypton.e.player.currentScreenHandler;
        if (Krypton.e.player.currentScreenHandler instanceof GenericContainerScreenHandler) {
            int n = 0;
            for (int i = 0; i < ((GenericContainerScreenHandler) Krypton.e.player.currentScreenHandler).getRows() * 9; ++i) {
                if (currentScreenHandler.getSlot(i).getStack().getItem().equals(obj)) {
                    ++n;
                }
            }
            return n;
        }
        return 0;
    }
}