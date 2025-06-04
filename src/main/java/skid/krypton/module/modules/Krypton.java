package skid.krypton.module.modules;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import skid.krypton.enums.Enum6;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PacketReceiveEvent;
import skid.krypton.gui.ClickGUI;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.ModeSetting;
import skid.krypton.module.setting.NumberSetting;
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
    public static final ModeSetting<Enum6> l;
    public static final BooleanSetting m;
    public boolean shouldPreventClose;

    public Krypton() {
        super(EncryptedString.of("Krypton+"), EncryptedString.of("Settings for the client"), 344, Category.CLIENT);
        this.n = new BooleanSetting(EncryptedString.of("Prevent Close"), true).setDescription(EncryptedString.of("For servers with freeze plugins that don't let you open the GUI"));
        this.addSettings(Krypton.c, Krypton.d, Krypton.e, Krypton.f, Krypton.i, this.n, Krypton.k, Krypton.l, Krypton.m);
    }

    @Override
    public void onEnable() {
        skid.krypton.Krypton.INSTANCE.screen = this.mc.currentScreen;
        if (skid.krypton.Krypton.INSTANCE.GUI != null) {
            this.mc.setScreenAndRender(skid.krypton.Krypton.INSTANCE.GUI);
        } else if (this.mc.currentScreen instanceof InventoryScreen) {
            shouldPreventClose = true;
        }
        if (new Random().nextInt(3) == 1) {
            CompletableFuture.runAsync(() -> {
            });
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (this.mc.currentScreen instanceof ClickGUI) {
            skid.krypton.Krypton.INSTANCE.GUI.close();
            this.mc.setScreenAndRender(skid.krypton.Krypton.INSTANCE.screen);
            skid.krypton.Krypton.INSTANCE.GUI.onGuiClose();
        } else if (this.mc.currentScreen instanceof InventoryScreen) {
            shouldPreventClose = false;
        }
        super.onDisable();
    }

    @EventListener
    public void a(final PacketReceiveEvent packetReceiveEvent) {
        if (shouldPreventClose && packetReceiveEvent.a instanceof OpenScreenS2CPacket && this.n.getValue()) {
            packetReceiveEvent.cancel();
        }
    }

    static {
        c = new NumberSetting(EncryptedString.of("Red"), 0.0, 255.0, 120.0, 1.0);
        d = new NumberSetting(EncryptedString.of("Green"), 0.0, 255.0, 190.0, 1.0);
        e = new NumberSetting(EncryptedString.of("Blue"), 0.0, 255.0, 255.0, 1.0);
        f = new NumberSetting(EncryptedString.of("Window Alpha"), 0.0, 255.0, 170.0, 1.0);
        g = new BooleanSetting(EncryptedString.of("Breathing"), false).setDescription(EncryptedString.of("Color breathing effect (only with rainbow off)"));
        h = new BooleanSetting(EncryptedString.of("Rainbow"), false).setDescription(EncryptedString.of("Enables LGBTQ mode"));
        i = new BooleanSetting(EncryptedString.of("Background"), true).setDescription(EncryptedString.of("Renders the background of the Click Gui"));
        j = new BooleanSetting(EncryptedString.of("Custom Font"), true);
        k = new NumberSetting(EncryptedString.of("Roundness"), 1.0, 10.0, 5.0, 1.0);
        l = new ModeSetting<>(EncryptedString.of("Animations"), Enum6.a, Enum6.class);
        m = new BooleanSetting(EncryptedString.of("MSAA"), true).setDescription(EncryptedString.of("Anti Aliasing | This can impact performance if you're using tracers but gives them a smoother look |"));
    }
}