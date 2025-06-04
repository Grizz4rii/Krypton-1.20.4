// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import skid.krypton.enums.Enum5;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.EnumSetting;
import skid.krypton.setting.settings.MinMaxSetting;
import skid.krypton.setting.settings.StringSetting;
import skid.krypton.utils.EncryptedString;

public final class AutoTPA extends Module {
    private final MinMaxSetting c;
    private final EnumSetting<Enum5> d;
    private final StringSetting e;
    private int f;

    public AutoTPA() {
        super(EncryptedString.a("Auto Tpa"), EncryptedString.a("Module that helps you teleport streamers to you"), -1, Category.b);
        this.c = new MinMaxSetting(EncryptedString.a("Delay"), 1.0, 80.0, 1.0, 10.0, 30.0);
        this.d = new EnumSetting<Enum5>(EncryptedString.a("Mode"), Enum5.b, Enum5.class);
        this.e = new StringSetting(EncryptedString.a("Player"), "DrDonutt");
        this.a(this.d, this.c, this.e);
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
        final ClientPlayNetworkHandler networkHandler = this.b.getNetworkHandler();
        String s;
        if (this.d.a().equals(Enum5.a)) {
            s = "tpa ";
        } else {
            s = "tpahere ";
        }
        networkHandler.sendCommand(s + this.e.getValue());
        this.f = this.c.o();
    }
}
