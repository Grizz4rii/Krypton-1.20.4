package skid.krypton.module.modules;

import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.Setting;
import skid.krypton.module.setting.StringSetting;
import skid.krypton.utils.EncryptedString;

public class NameProtect extends Module {
    private final StringSetting fakeName;

    public NameProtect() {
        super(EncryptedString.a("Name Protect"), EncryptedString.a("Replaces your name with given one."), -1, Category.b);
        this.fakeName = new StringSetting("Fake Name", "Player");
        this.a(new Setting[]{this.fakeName});
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public String getFakeName() {
        return this.fakeName.getValue();
    }
}
