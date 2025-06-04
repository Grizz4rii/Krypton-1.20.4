package skid.krypton.module.modules;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import skid.krypton.enums.Enum6;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PacketReceiveEvent;
import skid.krypton.gui.ClickGUI;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.EnumSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public final class Krypton extends Module {
    public static final NumberSetting c;
    public static final NumberSetting d;
    public static final NumberSetting e;
    public static final NumberSetting f;
    public static final BooleanSetting g;
    public static final BooleanSetting h;
    public static final BooleanSetting i;
    public static final BooleanSetting j;
    private final BooleanSetting n;
    public static final NumberSetting k;
    public static final EnumSetting<Enum6> l;
    public static final BooleanSetting m;

    public Krypton() {
        super(EncryptedString.a("Krypton+"), EncryptedString.a("Settings for the client"), 344, Category.e);
        this.n = new BooleanSetting(EncryptedString.a("Prevent Close"), true).a(EncryptedString.a("For servers with freeze plugins that don't let you open the GUI"));
        this.a(Krypton.c, Krypton.d, Krypton.e, Krypton.f, Krypton.i, this.n, Krypton.k, Krypton.l, Krypton.m);
    }

    @Override
    public void onEnable() {
        skid.krypton.Krypton.INSTANCE.k = this.b.currentScreen;
        if (skid.krypton.Krypton.INSTANCE.GUI != null) {
            this.b.setScreenAndRender(skid.krypton.Krypton.INSTANCE.GUI);
        } else if (this.b.currentScreen instanceof InventoryScreen) {
            skid.krypton.Krypton.INSTANCE.i = true;
        }
        if (new Random().nextInt(3) == 1) {
            CompletableFuture.runAsync(() -> {
            });
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (this.b.currentScreen instanceof ClickGUI) {
            skid.krypton.Krypton.INSTANCE.GUI.close();
            this.b.setScreenAndRender(skid.krypton.Krypton.INSTANCE.k);
            skid.krypton.Krypton.INSTANCE.GUI.onGuiClose();
        } else if (this.b.currentScreen instanceof InventoryScreen) {
            skid.krypton.Krypton.INSTANCE.i = false;
        }
        super.onDisable();
    }

    @EventListener
    public void a(final PacketReceiveEvent packetReceiveEvent) {
        if (skid.krypton.Krypton.INSTANCE.i && packetReceiveEvent.a instanceof OpenScreenS2CPacket && this.n.c()) {
            packetReceiveEvent.cancel();
        }
    }

    static {
        c = new NumberSetting(EncryptedString.a("Red"), 0.0, 255.0, 120.0, 1.0);
        d = new NumberSetting(EncryptedString.a("Green"), 0.0, 255.0, 190.0, 1.0);
        e = new NumberSetting(EncryptedString.a("Blue"), 0.0, 255.0, 255.0, 1.0);
        f = new NumberSetting(EncryptedString.a("Window Alpha"), 0.0, 255.0, 170.0, 1.0);
        g = new BooleanSetting(EncryptedString.a("Breathing"), false).a(EncryptedString.a("Color breathing effect (only with rainbow off)"));
        h = new BooleanSetting(EncryptedString.a("Rainbow"), false).a(EncryptedString.a("Enables LGBTQ mode"));
        i = new BooleanSetting(EncryptedString.a("Background"), true).a(EncryptedString.a("Renders the background of the Click Gui"));
        j = new BooleanSetting(EncryptedString.a("Custom Font"), true);
        k = new NumberSetting(EncryptedString.a("Roundness"), 1.0, 10.0, 5.0, 1.0);
        l = new EnumSetting<>(EncryptedString.a("Animations"), Enum6.a, Enum6.class);
        m = new BooleanSetting(EncryptedString.a("MSAA"), true).a(EncryptedString.a("Anti Aliasing | This can impact performance if you're using tracers but gives them a smoother look |"));
    }
}