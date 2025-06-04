package skid.krypton.utils;

import skid.krypton.enums.Enum6;
import skid.krypton.module.modules.Krypton;

public final class Animation {
    private double a;
    private final double b;

    public Animation(final double n) {
        this.a = n;
        this.b = n;
    }

    public double a(final double n, final double a) {
        if (Krypton.l.b(Enum6.a)) {
            this.a = MathUtil.a((float) n, this.a, a);
        } else if (Krypton.l.b(Enum6.b)) {
            this.a = MathUtil.a(n, this.a, a);
        } else if (Krypton.l.b(Enum6.c)) {
            this.a = a;
        }
        return this.a;
    }

    public double getAnimation() {
        return this.a;
    }

    public void a(final double n) {
        this.a = MathUtil.a(n, this.a, this.b);
    }
}
