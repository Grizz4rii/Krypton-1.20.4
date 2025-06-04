package skid.krypton.module.setting;

public final class BindSetting extends Setting {
    private int value;
    private boolean listening;
    private final boolean moduleKey;
    private final int defaultValue;

    public BindSetting(final CharSequence charSequence, final int n, final boolean d) {
        super(charSequence);
        this.value = n;
        this.defaultValue = n;
        this.moduleKey = d;
    }

    public boolean isModuleKey() {
        return this.moduleKey;
    }

    public boolean isListening() {
        return this.listening;
    }

    public int getDefaultValue() {
        return this.defaultValue;
    }

    public void setListening(final boolean listening) {
        this.listening = listening;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(final int a) {
        this.value = a;
    }

    public void toggleListening() {
        this.listening = !this.listening;
    }

    public BindSetting setDescription(final CharSequence charSequence) {
        super.setDescription(charSequence);
        return this;
    }
}
