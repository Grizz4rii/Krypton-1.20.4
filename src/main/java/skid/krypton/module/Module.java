package skid.krypton.module;

import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import skid.krypton.Krypton;
import skid.krypton.manager.EventManager;
import skid.krypton.module.setting.Setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Module implements Serializable {
    @NotNull protected final MinecraftClient mc;
    @NotNull protected final EventManager EVENT_BUS;
    @NotNull private final List<Setting> settings;
    @Nullable private CharSequence name;
    @Nullable private CharSequence description;
    @NotNull private Category category;
    private boolean enabled;
    private int keybind;

    public Module(final @Nullable CharSequence name, final @Nullable CharSequence description, final int keybind, final @NotNull Category category) {
        this.settings = new ArrayList<>();
        this.EVENT_BUS = Krypton.INSTANCE.c();
        this.mc = MinecraftClient.getInstance();
        this.name = name;
        this.description = description;
        this.enabled = false;
        this.keybind = keybind;
        this.category = category;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (this.enabled) this.onEnable();
        else this.onDisable();
    }

    public @Nullable CharSequence getName() {
        return this.name;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public @Nullable CharSequence getDescription() {
        return this.description;
    }

    public int getKeybind() {
        return this.keybind;
    }

    public @NotNull Category getCategory() {
        return this.category;
    }

    public void setCategory(final @NotNull Category category) {
        this.category = category;
    }

    public void setName(final @Nullable CharSequence name) {
        this.name = name;
    }

    public void setDescription(final @Nullable CharSequence description) {
        this.description = description;
    }

    public void a(final int keybind) {
        this.keybind = keybind;
    }

    public @NotNull List<Setting> getSettings() {
        return this.settings;
    }

    public void onEnable() {}
    public void onDisable() {}

    public void addSetting(final Setting setting) {
        this.settings.add(setting);
    }

    public void addSettings(final Setting... a) {
        this.settings.addAll(Arrays.asList(a));
    }

    public void toggle(final boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) this.onEnable();
            else this.onDisable();
        }
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}