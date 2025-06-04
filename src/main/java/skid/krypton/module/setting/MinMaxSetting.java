package skid.krypton.module.setting;

import java.util.Random;

public class MinMaxSetting extends Setting {
    private final double a;
    private final double c;
    private final double d;
    private final double e;
    private final double f;
    private double g;
    private double h;

    public MinMaxSetting(final CharSequence charSequence, final double a, final double c, final double d, final double n, final double n2) {
        super(charSequence);
        this.a = a;
        this.c = c;
        this.d = d;
        this.g = n;
        this.h = n2;
        this.e = n;
        this.f = n2;
    }

    public int a() {
        return (int) this.g;
    }

    public float b() {
        return (float) this.g;
    }

    public long c() {
        return (long) this.g;
    }

    public int d() {
        return (int) this.h;
    }

    public float e() {
        return (float) this.h;
    }

    public long f() {
        return (long) this.h;
    }

    public double g() {
        return this.a;
    }

    public double h() {
        return this.c;
    }

    public double i() {
        return this.g;
    }

    public double j() {
        return this.h;
    }

    public double k() {
        return this.e;
    }

    public double l() {
        return this.f;
    }

    public double m() {
        return this.d;
    }

    public double n() {
        if (this.j() > this.i()) {
            return new Random().nextDouble(this.i(), this.j());
        }
        return this.i();
    }

    public int o() {
        if (this.j() > this.i()) {
            return new Random().nextInt(this.a(), this.d());
        }
        return this.a();
    }

    public float p() {
        if (this.j() > this.i()) {
            return new Random().nextFloat(this.b(), this.e());
        }
        return this.b();
    }

    public long q() {
        if (this.j() > this.i()) {
            return new Random().nextLong(this.c(), this.f());
        }
        return this.c();
    }

    public void a(final double b) {
        final double n = 1.0 / this.d;
        this.g = Math.round(Math.max(this.a, Math.min(this.c, b)) * n) / n;
    }

    public void b(final double b) {
        final double n = 1.0 / this.d;
        this.h = Math.round(Math.max(this.a, Math.min(this.c, b)) * n) / n;
    }
}