// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import skid.krypton.auth.Auth;
import skid.krypton.gui.ClickGUI;
import skid.krypton.manager.ConfigManager;
import skid.krypton.manager.EventManager;
import skid.krypton.manager.ModuleManager;
import skid.krypton.utils.EncryptedString;

import java.io.File;

public final class Krypton {
    public ConfigManager configManager;
    public ModuleManager MODULE_MANAGER;
    public EventManager EVENT_BUS;
    public Auth auth;
    public static MinecraftClient e;
    public String build;
    public static boolean g;
    public static Krypton INSTANCE;
    public boolean i;
    public ClickGUI GUI;
    public Screen k;
    public long modified;
    public File jar;

    public Krypton(final Auth auth) {
        try {
            Krypton.INSTANCE = this;
            this.build = " b1.3";
            this.k = null;
            this.auth = auth;
            this.EVENT_BUS = new EventManager();
            this.MODULE_MANAGER = new ModuleManager();
            this.GUI = new ClickGUI();
            this.configManager = new ConfigManager();
            this.a().a();
            this.jar = new File(Krypton.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            this.modified = this.jar.lastModified();
            this.i = false;
            Krypton.e = MinecraftClient.getInstance();
        } catch (Throwable _t) {
            _t.printStackTrace(System.err);
        }
    }

    public ConfigManager a() {
        return this.configManager;
    }

    public ModuleManager b() {
        return this.MODULE_MANAGER;
    }

    public EventManager c() {
        return this.EVENT_BUS;
    }

    public ClickGUI d() {
        return this.GUI;
    }

    public void e() {
        this.jar.setLastModified(this.modified);
    }

    public String f() {
        return this.build;
    }

    static {
        Krypton.INSTANCE = new Krypton(new Auth(new EncryptedString("127.0.0.1")));
        Krypton.g = true;
    }
}
