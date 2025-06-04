// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.utils;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.Iterator;
import java.util.Set;

public class EnchantmentUtil {
    public static boolean a(final ItemStack itemStack, final RegistryKey registryKey) {
        if (itemStack.isEmpty()) {
            return false;
        }
        final Object2IntArrayMap object2IntArrayMap = new Object2IntArrayMap();
        a(itemStack, object2IntArrayMap);
        return a(object2IntArrayMap, registryKey);
    }

    private static boolean a(final Object2IntMap object2IntMap, final RegistryKey registryKey) {
        for (Object o : object2IntMap.keySet()) {
            if (((RegistryEntry) o).matchesKey(registryKey)) {
                return true;
            }
        }
        return false;
    }

    public static void a(final ItemStack itemStack, final Object2IntMap object2IntMap) {
        object2IntMap.clear();
        if (!itemStack.isEmpty()) {
            Set set;
            if (itemStack.getItem() == Items.ENCHANTED_BOOK) {
                set = itemStack.get(DataComponentTypes.STORED_ENCHANTMENTS).getEnchantmentEntries();
            } else {
                set = itemStack.getEnchantments().getEnchantmentEntries();
            }
            for (final Object next : set) {
                object2IntMap.put(((Object2IntMap.Entry) next).getKey(), ((Object2IntMap.Entry) next).getIntValue());
            }
        }
    }

    private static byte[] dflsaewudiywvgc() {
        return new byte[]{72, 41, 31, 64, 12, 55, 93, 105, 42, 112, 9, 117, 95, 100, 96, 112, 45, 61, 57, 6, 115, 7, 56, 42, 60, 19, 83, 109, 27, 127, 80, 89, 27, 64, 119, 74, 59, 64, 85, 124, 9, 26, 23, 105, 85, 22, 127, 47, 82, 46, 53, 25};
    }
}
