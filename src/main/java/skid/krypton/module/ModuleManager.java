package skid.krypton.module;

import net.minecraft.client.gui.screen.ChatScreen;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.KeyEvent;
import skid.krypton.module.modules.*;
import skid.krypton.module.setting.BindSetting;
import skid.krypton.utils.EncryptedString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ModuleManager {
    private final List<Module> modules;

    public ModuleManager() {
        this.modules = new ArrayList<>();
        this.register();
        this.initialize();
    }

    public void register() {
        this.addModule(new ElytraSwap());
        this.addModule(new MaceSwap());
        this.addModule(new Hitbox());
        this.addModule(new StaticHitboxes());
        this.addModule(new AutoTotem());
        this.addModule(new HoverTotem());
        this.addModule(new AutoInventoryTotem());
        this.addModule(new AnchorMacro());
        this.addModule(new AutoCrystal());
        this.addModule(new DoubleAnchor());
        this.addModule(new FastPlace());
        this.addModule(new Freecam());
        this.addModule(new AutoFirework());
        this.addModule(new ElytraGlide());
        this.addModule(new AutoTool());
        this.addModule(new AutoEat());
        this.addModule(new AutoMine());
        this.addModule(new CordSnapper());
        this.addModule(new KeyPearl());
        this.addModule(new NameProtect());
        this.addModule(new AutoTPA());
        this.addModule(new RtpBaseFinder());
        this.addModule(new TunnelBaseFinder());
        this.addModule(new NetheriteFinder());
        this.addModule(new BoneDropper());
        this.addModule(new AutoSell());
        this.addModule(new ShulkerDropper());
        this.addModule(new AntiTrap());
        this.addModule(new AuctionSniper());
        this.addModule(new AutoSpawnerSell());
        this.addModule(new HUD());
        this.addModule(new PlayerESP());
        this.addModule(new StorageESP());
        this.addModule(new TargetHUD());
        this.addModule(new Krypton());
        this.addModule(new SelfDestruct());
    }

    public List<Module> b() {
        return this.modules.stream().filter(Module::isEnabled).toList();
    }

    public List<Module> c() {
        return this.modules;
    }

    public void initialize() {
        skid.krypton.Krypton.INSTANCE.c().register(this);
        for (final Module next : this.modules) {
            next.addSetting(new BindSetting(EncryptedString.of("Keybind"), next.getKeybind(), true).setDescription(EncryptedString.of("Key to enabled the module")));
        }
    }

    public List<Module> getModulesByCategory(final Category category) {
        return this.modules.stream().filter(module -> module.getCategory() == category).toList();
    }

    public Module getModuleByClass(final Class<? extends Module> klass) {
        Objects.requireNonNull(klass);
        return this.modules.stream().filter(klass::isInstance).findFirst().orElse(null);
    }

    public void addModule(final Module module) {
        skid.krypton.Krypton.INSTANCE.c().register(module);
        this.modules.add(module);
    }

    @EventListener
    public void a(final KeyEvent event) {
        if (skid.krypton.Krypton.mc.player == null || skid.krypton.Krypton.mc.currentScreen instanceof ChatScreen) {
            return;
        }

        if (!SelfDestruct.DESTRUCTED) {
            this.modules.forEach(module -> {
                if (module.getKeybind() == event.key && event.mode == 1) {
                    module.toggle();
                }
            });
        }
    }
}
