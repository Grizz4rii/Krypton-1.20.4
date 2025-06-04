// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.utils;

public final class MathUtil {
    public static double a(final double n, final double n2) {
        return n2 * Math.round(n / n2);
    }

    public static double a(final double b, final double n, final double n2) {
        final double max = Math.max(0.0, Math.min(1.0, b));
        return n + (n2 - n) * (max * max * (3.0 - 2.0 * max));
    }

    public static double a(final float n, final double n2, final double n3) {
        final double ceil = Math.ceil(Math.abs(n3 - n2) * n);
        if (n2 < n3) {
            return Math.min(n2 + (int) ceil, n3);
        }
        return Math.max(n2 - (int) ceil, n3);
    }

    public static double b(final double n, final double n2, final double n3) {
        return n2 + (n3 - n2) * n;
    }

    public static double a(final double n, final double n2, final double a, final double b) {
        return b(1.0f - (float) Math.pow(a, b), n, n2);
    }

    public static double c(final double a, final double a2, final double b) {
        return Math.max(a2, Math.min(a, b));
    }

    public static int a(final int a, final int a2, final int b) {
        return Math.max(a2, Math.min(a, b));
    }

    private static byte[] qdktmhyeqbcmeuf() {
        return new byte[]{119, 32, 124, 73, 72, 72, 40, 77, 43, 68, 118, 62, 89, 64, 13, 16, 9, 102, 125, 44, 30, 36, 79, 111, 84, 18, 14, 96, 95, 125, 88, 5, 21, 92, 53, 23, 93, 37, 52, 7, 4, 90, 81, 44, 94, 89, 84, 72, 29, 67, 12, 37, 21, 121, 87, 118, 25, 58, 112, 40, 84, 18, 112, 79, 60, 23, 38, 58, 5, 122, 89, 73, 89, 59, 98, 53, 20, 48, 45, 104, 121, 126, 19, 19, 34, 42, 13, 62, 88, 28, 9, 118, 58, 93, 113, 96, 121, 66, 38, 54, 44, 5, 14, 40, 36, 64, 79, 62, 97, 104, 85, 127, 18};
    }
}
