package skid.krypton.setting.settings;

import skid.krypton.setting.Setting;

public class StringSetting extends Setting {
    public String a;

    public StringSetting(final CharSequence charSequence, final String a) {
        super(charSequence);
        this.a = a;
    }

    public void a(final String a) {
        this.a = a;
    }

    public String getValue() {
        return this.a;
    }

    public StringSetting a(final CharSequence charSequence) {
        super.b(charSequence);
        return this;
    }

    private static byte[] gfqnwzetyscqouk() {
        return new byte[]{66, 119, 127, 95, 105, 17, 78, 43, 125, 16, 113, 21, 43, 87, 51, 40, 68, 15, 65, 55, 116, 100, 1, 60, 66, 13, 101, 65, 67, 121, 106, 111, 65, 117, 36, 121, 18, 101, 29, 60, 114, 85, 37, 118, 14, 48, 22, 30, 123, 53, 88, 29, 87, 5, 91, 38, 81, 103, 63, 97, 63, 64, 11, 94, 114, 60, 48, 90, 2, 24, 68};
    }
}
