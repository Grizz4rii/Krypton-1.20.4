// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.setting.settings;

import skid.krypton.setting.Setting;

public final class BooleanSetting extends Setting {
    private boolean a;
    private final boolean c;

    public BooleanSetting(final CharSequence charSequence, final boolean b) {
        super(charSequence);
        this.a = b;
        this.c = b;
    }

    public void a() {
        this.a(!this.a);
    }

    public void a(final boolean a) {
        this.a = a;
    }

    public boolean b() {
        return this.c;
    }

    public boolean c() {
        return this.a;
    }

    public BooleanSetting a(final CharSequence charSequence) {
        super.b(charSequence);
        return this;
    }

    private static byte[] jmwfaglzomyfqjf() {
        return new byte[]{12, 49, 22, 88, 9, 40, 6, 59, 70, 87, 115, 78, 7, 93, 101, 117, 12, 12, 50, 125, 29, 6, 27, 88, 29, 42, 2, 50, 119, 6, 26, 56, 125, 116, 3, 91, 5, 99, 62, 103, 23, 70, 94, 12};
    }
}
