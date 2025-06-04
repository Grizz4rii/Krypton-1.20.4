package skid.krypton.module.modules;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import skid.krypton.enums.Enum5;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.ModeSetting;
import skid.krypton.module.setting.MinMaxSetting;
import skid.krypton.module.setting.StringSetting;
import skid.krypton.utils.EncryptedString;

public final class AutoTPA extends Module {
    private final MinMaxSetting c;
    private final ModeSetting<Enum5> d;
    private final StringSetting e;
    private int f;

    public AutoTPA() {
        super(EncryptedString.of("Auto Tpa"), EncryptedString.of("Module that helps you teleport streamers to you"), -1, Category.MISC);
        this.c = new MinMaxSetting(EncryptedString.of("Delay"), 1.0, 80.0, 1.0, 10.0, 30.0);
        this.d = new ModeSetting<Enum5>(EncryptedString.of("Mode"), Enum5.b, Enum5.class);
        this.e = new StringSetting(EncryptedString.of("Player"), "DrDonutt");
        this.addSettings(this.d, this.c, this.e);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.f > 0) {
            --this.f;
            return;
        }
        final ClientPlayNetworkHandler networkHandler = this.mc.getNetworkHandler();
        String s;
        if (this.d.getValue().equals(Enum5.a)) {
            s = "tpa ";
        } else {
            s = "tpahere ";
        }
        networkHandler.sendCommand(s + this.e.getValue());
        this.f = this.c.getRandomIntInRange();
    }
}
