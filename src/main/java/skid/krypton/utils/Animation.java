package skid.krypton.utils;

import skid.krypton.module.modules.client.Krypton;

public final class Animation {
    private double value;
    private final double b;

    public Animation(final double n) {
        this.value = n;
        this.b = n;
    }

    public double animate(final double n, final double a) {
        if (Krypton.animationMode.isMode(Krypton.AnimationMode.NORMAL)) {
            this.value = MathUtil.a((float) n, this.value, a);
        } else if (Krypton.animationMode.isMode(Krypton.AnimationMode.POSITIVE)) {
            this.value = MathUtil.a(n, this.value, a);
        } else if (Krypton.animationMode.isMode(Krypton.AnimationMode.OFF)) {
            this.value = a;
        }
        return this.value;
    }

    public double getAnimation() {
        return this.value;
    }

    public void a(final double n) {
        this.value = MathUtil.a(n, this.value, this.b);
    }
}
