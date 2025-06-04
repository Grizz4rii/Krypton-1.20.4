// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.setting.settings;

import skid.krypton.setting.Setting;

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

    private static byte[] glildvbdyyoarrp() {
        return new byte[]{68, 77, 17, 118, 88, 66, 26, 42, 109, 7, 55, 12, 68, 24, 115, 10, 42, 35, 35, 90, 100, 121, 31, 80, 76, 67, 17, 70, 52, 53, 87, 113, 5, 117, 101, 90, 110, 20, 37, 4, 92, 90, 100, 57, 53, 52, 95, 15, 121, 45, 85, 101, 115, 33, 75, 93, 100, 5, 58, 111, 44, 37, 88, 118, 79, 65, 103, 97, 28, 123, 117, 71, 54, 40, 33, 8, 76, 6, 100, 126, 30, 119, 100, 53, 83, 61, 87, 63, 70, 83, 16, 23, 40, 15, 108, 90, 79, 112, 19, 98, 27, 91, 67, 4, 119, 120, 26, 122, 104, 64, 12, 94, 28, 67, 65, 71, 89, 2, 73, 87, 124, 6, 32, 41, 25, 112, 118};
    }
}
