// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import skid.krypton.enums.Enum6;
import skid.krypton.event.events.PacketReceiveEvent;
import skid.krypton.gui.ClickGUI;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.EnumSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;

import java.util.EventListener;
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
            skid.krypton.Krypton.INSTANCE.GUI.method_25419();
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
        l = new EnumSetting<Enum6>(EncryptedString.a("Animations"), Enum6.a, Enum6.class);
        m = new BooleanSetting(EncryptedString.a("MSAA"), true).a(EncryptedString.a("Anti Aliasing | This can impact performance if you're using tracers but gives them a smoother look |"));
    }

    private static byte[] uqjulljmwrmvbth() {
        return new byte[]{0, 0, 0, 16, 0, 0, 1, 31};
    }

    private static byte[] vdggchcetocflwc() {
        return new byte[]{0, 0, 0, 4, 0, 0, 1, 47};
    }

    private static byte[] zljlndecoivtcdc() {
        return new byte[]{0, 0, 0, 14, 0, 0, 1, 51};
    }

    private static byte[] eraytdmhgoestqg() {
        return new byte[]{0, 0, 0, 15, 0, 0, 1, 65};
    }

    private static byte[] utucwnayewjurmu() {
        return new byte[]{0, 0, 0, 15, 0, 0, 1, 80};
    }

    private static byte[] bppmcvpdpmmcnva() {
        return new byte[]{0, 0, 0, 19, 0, 0, 1, 95};
    }

    private static byte[] bbmhqmyfcdxvvdo() {
        return new byte[]{0, 0, 0, 6, 0, 0, 1, 114};
    }

    private static byte[] vqiaalhzripimix() {
        return new byte[]{0, 0, 0, 8, 0, 0, 1, 120};
    }

    private static byte[] qunplqwdrjogcod() {
        return new byte[]{0, 0, 0, 8, 0, 0, 1, -128};
    }

    private static byte[] xjzrdufqwbgqvpp() {
        return new byte[]{0, 0, 0, 1, 0, 0, 1, -120};
    }

    private static byte[] dqkaylhatgklstr() {
        return new byte[]{0, 0, 0, 8, 0, 0, 1, -119};
    }

    private static byte[] fmimijxrxnheswq() {
        return new byte[]{0, 0, 0, 3, 0, 0, 1, -111};
    }

    private static byte[] rvcgololephhcjh() {
        return new byte[]{0, 0, 0, 8, 0, 0, 1, -108};
    }

    private static byte[] hygmqehjxpodrih() {
        return new byte[]{0, 0, 0, 1, 0, 0, 1, -100};
    }

    private static byte[] tfmszrsgoseliwk() {
        return new byte[]{0, 0, 0, 4, 0, 0, 1, -99};
    }

    private static byte[] xwwbmerkziumjpa() {
        return new byte[]{0, 0, 0, 20, 0, 0, 1, -95};
    }

    private static byte[] mezzyewjlvivhda() {
        return new byte[]{0, 0, 0, 6, 0, 0, 1, -75};
    }

    private static byte[] jvxqelfjflcyvqd() {
        return new byte[]{0, 0, 0, 14, 0, 0, 1, -69};
    }

    private static byte[] zecshaolxkvwgzt() {
        return new byte[]{0, 0, 0, 8, 0, 0, 1, -55};
    }

    private static byte[] idfcokqshccchsw() {
        return new byte[]{0, 0, 0, 3, 0, 0, 1, -47};
    }

    private static byte[] zrufudrxankiemq() {
        return new byte[]{0, 0, 0, 5, 0, 0, 1, -44};
    }

    private static byte[] tqgmowauduehche() {
        return new byte[]{0, 0, 0, 12, 0, 0, 1, -39};
    }

    private static byte[] koqtmijwyemmfmi() {
        return new byte[]{0, 0, 0, 16, 0, 0, 1, -27};
    }

    private static byte[] fqhlejnakkhsmfb() {
        return new byte[]{0, 0, 0, 4, 0, 0, 1, -11};
    }
}
