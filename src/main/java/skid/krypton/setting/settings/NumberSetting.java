package skid.krypton.setting.settings;

import skid.krypton.setting.Setting;

public final class NumberSetting extends Setting {
    private final double a;
    private final double c;
    private double d;
    private final double e;
    private final double f;

    public NumberSetting(final CharSequence charSequence, final double a, final double c, final double n, final double e) {
        super(charSequence);
        this.a = a;
        this.c = c;
        this.d = n;
        this.e = e;
        this.f = n;
    }

    public double a() {
        return this.d;
    }

    public double b() {
        return this.f;
    }

    public double c() {
        return this.e;
    }

    public double d() {
        return this.a;
    }

    public double e() {
        return this.c;
    }

    public int f() {
        return (int) this.d;
    }

    public float g() {
        return (float) this.d;
    }

    public long h() {
        return (long) this.d;
    }

    public void a(final double b) {
        final double n = 1.0 / this.e;
        this.d = Math.round(Math.max(this.a, Math.min(this.c, b)) * n) / n;
    }

    public NumberSetting a(final CharSequence charSequence) {
        super.b(charSequence);
        return this;
    }
}
