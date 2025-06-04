// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.manager;

import net.minecraft.client.gui.screen.ChatScreen;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.KeyEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.modules.*;
import skid.krypton.setting.settings.BindSetting;
import skid.krypton.utils.EncryptedString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public final class ModuleManager {
    private final List<Module> modules;

    public ModuleManager() {
        this.modules = new ArrayList<>();
        this.a();
        this.d();
    }

    public void a() {
        this.a(new ElytraSwap());
        this.a(new MaceSwap());
        this.a(new Hitbox());
        this.a(new StaticHitboxes());
        this.a(new AutoTotem());
        this.a(new HoverTotem());
        this.a(new AutoInventoryTotem());
        this.a(new AnchorMacro());
        this.a(new AutoCrystal());
        this.a(new DoubleAnchor());
        this.a(new FastPlace());
        this.a(new Freecam());
        this.a(new AutoFirework());
        this.a(new ElytraGlide());
        this.a(new AutoTool());
        this.a(new AutoEat());
        this.a(new AutoMine());
        this.a(new CordSnapper());
        this.a(new KeyPearl());
        this.a(new NameProtect());
        this.a(new AutoTPA());
        this.a(new RtpBaseFinder());
        this.a(new TunnelBaseFinder());
        this.a(new NetheriteFinder());
        this.a(new BoneDropper());
        this.a(new AutoSell());
        this.a(new ShulkerDropper());
        this.a(new AntiTrap());
        this.a(new AuctionSniper());
        this.a(new AutoSpawnerSell());
        this.a(new HUD());
        this.a(new PlayerESP());
        this.a(new StorageESP());
        this.a(new TargetHUD());
        this.a(new Krypton());
        this.a(new SelfDestruct());
    }

    public List<Module> b() {
        return this.modules.stream().filter(Module::isEnabled).toList();
    }

    public List<Module> c() {
        return this.modules;
    }

    public void d() {
        skid.krypton.Krypton.INSTANCE.c().register(this);
        for (final Module next : this.modules) {
            next.a(new BindSetting(EncryptedString.a("Keybind"), next.getKeybind(), true).a(EncryptedString.a("Key to enabled the module")));
        }
    }

    public List a(final Category category) {
        return this.modules.stream().filter(module -> module.getCategory() == category2).toList();
    }

    public Module getModuleByClass(final Class<? extends Module> obj) {
        final Stream<Object> stream = this.modules.stream();
        Objects.requireNonNull(obj);
        return stream.filter(obj::isInstance).findFirst().orElse(null);
    }

    public void a(final Module module) {
        skid.krypton.Krypton.INSTANCE.c().register(module);
        this.modules.add(module);
    }

    @EventListener
    public void a(final KeyEvent keyEvent) {
        if (skid.krypton.Krypton.e.player != null && skid.krypton.Krypton.e.currentScreen instanceof ChatScreen) {
            return;
        }
        if (!SelfDestruct.c) {
            this.modules.forEach(module -> {
                if (module.getKeybind() == keyEvent2.a && keyEvent2.b == 1) {
                    module.toggle();
                }
            });
        }
    }
}
