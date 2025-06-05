package skid.krypton;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import skid.krypton.gui.ClickGUI;
import skid.krypton.manager.ConfigManager;
import skid.krypton.manager.EventManager;
import skid.krypton.module.ModuleManager;

import java.io.File;

public final class Krypton {
    public ConfigManager configManager;
    public ModuleManager MODULE_MANAGER;
    public EventManager EVENT_BUS;
    public static MinecraftClient mc;
    public String version;
    public static Krypton INSTANCE;
    public boolean shouldPreventClose;
    public ClickGUI GUI;
    public Screen screen;
    public long modified;
    public File jar;

    public Krypton() {
        try {
            Krypton.INSTANCE = this;
            this.version = " b1.3";
            this.screen = null;
            this.EVENT_BUS = new EventManager();
            this.MODULE_MANAGER = new ModuleManager();
            this.GUI = new ClickGUI();
            this.configManager = new ConfigManager();
            this.getConfigManager().loadProfile();
            this.jar = new File(Krypton.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            this.modified = this.jar.lastModified();
            this.shouldPreventClose = false;
            Krypton.mc = MinecraftClient.getInstance();
        } catch (Throwable _t) {
            _t.printStackTrace(System.err);
        }
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public ModuleManager getModuleManager() {
        return this.MODULE_MANAGER;
    }

    public EventManager getEventBus() {
        return this.EVENT_BUS;
    }

    public void resetModifiedDate() {
        this.jar.setLastModified(this.modified);
    }

}
