// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.setting.settings;

import net.minecraft.item.Item;
import skid.krypton.setting.Setting;

public class ItemSetting extends Setting {
    private Item a;
    private final Item c;

    public ItemSetting(final CharSequence charSequence, final Item item) {
        super(charSequence);
        this.a = item;
        this.c = item;
    }

    public Item a() {
        return this.a;
    }

    public void a(final Item a) {
        this.a = a;
    }

    public Item b() {
        return this.c;
    }

    public void c() {
        this.a = this.c;
    }

    private static byte[] nkczpkvhgkefosa() {
        return new byte[]{63, 8, 37, 8, 37, 23, 27, 38, 23, 93, 25, 23, 17, 60, 101, 59, 68, 114, 22, 78, 7, 109, 121, 59, 72, 110, 56, 7, 23, 7, 20, 74, 92, 7, 91, 23, 48, 18, 93, 9, 106, 15, 34, 15, 33, 104, 77, 119, 38, 73, 89, 68, 20, 61, 63, 67, 71, 94, 72, 123, 116, 14, 86, 41, 35, 12, 37, 31, 2, 104, 22, 9, 38, 120, 35, 30, 52, 122, 126, 121, 82, 43, 10, 123, 127, 40, 16, 44, 28, 125, 64, 52, 107, 118, 88, 24, 100, 126, 17};
    }
}
