package skid.krypton.utils;

import org.jetbrains.annotations.NotNull;

import java.nio.CharBuffer;
import java.security.SecureRandom;
import java.util.Arrays;

public class EncryptedString implements AutoCloseable, CharSequence {
    private final char[] a;
    private final char[] b;
    private final int c;
    private static final int d = 128;
    private static final SecureRandom e;
    private boolean f;

    public EncryptedString(final String s) {
        this.f = false;
        if (s == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        this.c = s.length();
        this.a = a(Math.min(this.c, 128));
        this.b = new char[this.c];
        s.getChars(0, this.c, this.b, 0);
        a(this.b, this.a, 0, this.c);
    }

    public EncryptedString(final char[] original, final char[] original2) {
        this.f = false;
        if (original == null || original2 == null) {
            throw new IllegalArgumentException("Neither encrypted value nor key can be null");
        }
        if (original2.length == 0) {
            throw new IllegalArgumentException("Encryption key cannot be empty");
        }
        this.c = original.length;
        this.b = Arrays.copyOf(original, original.length);
        this.a = Arrays.copyOf(original2, original2.length);
    }

    public static EncryptedString a(final String s) {
        return new EncryptedString(s);
    }

    public static EncryptedString a(final String s, final String s2) {
        if (s == null || s2 == null) {
            throw new IllegalArgumentException("Neither encrypted data nor key can be null");
        }
        return new EncryptedString(s.toCharArray(), s2.toCharArray());
    }

    private static char[] a(final int n) {
        final char[] array = new char[n];
        for (int i = 0; i < n; ++i) {
            array[i] = (char) EncryptedString.e.nextInt(65536);
        }
        return array;
    }

    private static void a(final char[] array, final char[] array2, final int n, final int n2) {
        for (int i = 0; i < n2; ++i) {
            final int n3 = n + i;
            array[n3] ^= array2[i % array2.length];
        }
    }

    @Override
    public int length() {
        this.c();
        return this.c;
    }

    @Override
    public char charAt(final int n) {
        this.c();
        if (n < 0 || n >= this.c) {
            throw new IndexOutOfBoundsException("Index: " + n + ", Length: " + this.c);
        }
        return (char) (this.b[n] ^ this.a[n % this.a.length]);
    }

    @NotNull
    @Override
    public CharSequence subSequence(final int n, final int n2) {
        this.c();
        if (n < 0 || n2 > this.c || n > n2) {
            throw new IndexOutOfBoundsException("Invalid subsequence range: " + n + " to " + n2 + " (length: " + this.c);
        }
        final int n3 = n2 - n;
        final char[] array = new char[n3];
        System.arraycopy(this.b, n, array, 0, n3);
        final char[] array2 = new char[n3];
        for (int i = 0; i < n3; ++i) {
            array2[i] = this.a[(n + i) % this.a.length];
        }
        a(array, this.a, 0, n3);
        a(array, array2, 0, n3);
        return new EncryptedString(array, array2);
    }

    @NotNull
    @Override
    public String toString() {
        this.c();
        final char[] array = new char[this.c];
        for (int i = 0; i < this.c; ++i) {
            array[i] = this.charAt(i);
        }
        final String s = new String(array);
        Arrays.fill(array, '\0');
        return s;
    }

    @NotNull
    public String a() {
        this.c();
        return this.toString();
    }

    public CharBuffer b() {
        this.c();
        final CharBuffer allocate = CharBuffer.allocate(this.c);
        for (int i = 0; i < this.c; ++i) {
            allocate.put(i, this.charAt(i));
        }
        allocate.flip();
        return allocate.asReadOnlyBuffer();
    }

    @Override
    public void close() {
        if (!this.f) {
            Arrays.fill(this.b, '\0');
            Arrays.fill(this.a, '\0');
            this.f = true;
        }
    }

    private void c() {
        if (this.f) {
            throw new IllegalStateException("This EncryptedString has been closed and cannot be used");
        }
    }

    @Override
    public boolean equals(final Object o) {
        this.c();
        if (this == o) {
            return true;
        }
        if (!(o instanceof CharSequence)) {
            return false;
        }
        if (this.c != ((CharSequence) o).length()) {
            return false;
        }
        for (int i = 0; i < this.c; ++i) {
            if (this.charAt(i) != ((CharSequence) o).charAt(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        this.c();
        int n = 0;
        for (int i = 0; i < this.c; ++i) {
            n = 31 * n + this.charAt(i);
        }
        return n;
    }

    static {
        e = new SecureRandom();
    }
}
