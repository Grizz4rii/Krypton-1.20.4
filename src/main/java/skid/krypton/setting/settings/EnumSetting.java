package skid.krypton.setting.settings;

import skid.krypton.setting.Setting;

import java.util.Arrays;
import java.util.List;

public final class EnumSetting<T extends Enum<T>> extends Setting {
    public int a;
    private final List<T> c;
    private final int d;

    public EnumSetting(final CharSequence charSequence, final Enum enum1, final Class clazz) {
        super(charSequence);
        this.c = Arrays.asList((T[]) clazz.getEnumConstants());
        this.a = this.c.indexOf(enum1);
        this.d = this.a;
    }

    public Enum a() {
        return this.c.get(this.a);
    }

    public void a(final Enum enum1) {
        this.a = this.c.indexOf(enum1);
    }

    public void a(final int a) {
        this.a = a;
    }

    public int b() {
        return this.a;
    }

    public int c() {
        return this.d;
    }

    public void d() {
        if (this.a < this.c.size() - 1) {
            ++this.a;
        } else {
            this.a = 0;
        }
    }

    public void e() {
        if (this.a > 0) {
            --this.a;
        } else {
            this.a = this.c.size() - 1;
        }
    }

    public boolean b(final Enum enum1) {
        return this.a == this.c.indexOf(enum1);
    }

    public List f() {
        return this.c;
    }

    public EnumSetting a(final CharSequence charSequence) {
        super.b(charSequence);
        return this;
    }
}
