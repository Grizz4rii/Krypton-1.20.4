// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.setting.settings;

import skid.krypton.setting.Setting;

public final class BindSetting extends Setting {
    private int a;
    private boolean c;
    private final boolean d;
    private final int e;

    public BindSetting(final CharSequence charSequence, final int n, final boolean d) {
        super(charSequence);
        this.a = n;
        this.e = n;
        this.d = d;
    }

    public boolean a() {
        return this.d;
    }

    public boolean b() {
        return this.c;
    }

    public int c() {
        return this.e;
    }

    public void a(final boolean c) {
        this.c = c;
    }

    public int d() {
        return this.a;
    }

    public void a(final int a) {
        this.a = a;
    }

    public void e() {
        this.c = !this.c;
    }

    public BindSetting a(final CharSequence charSequence) {
        super.b(charSequence);
        return this;
    }
}
