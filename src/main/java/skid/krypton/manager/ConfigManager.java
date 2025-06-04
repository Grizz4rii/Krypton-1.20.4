package skid.krypton.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import skid.krypton.Krypton;
import skid.krypton.module.Module;
import skid.krypton.module.setting.Setting;
import skid.krypton.module.setting.*;

public final class ConfigManager {
    private JsonObject a;

    public void init() {
        try {
            if (this.a == null) {
                this.a = new JsonObject();
                return;
            }
            for (final Module next : Krypton.INSTANCE.getModuleManager().c()) {
                final JsonElement value = this.a.get(next.getName().toString());
                if (value != null) {
                    if (!value.isJsonObject()) {
                        continue;
                    }
                    final JsonObject asJsonObject = value.getAsJsonObject();
                    final JsonElement value2 = asJsonObject.get("enabled");
                    if (value2 != null && value2.isJsonPrimitive() && value2.getAsBoolean()) {
                        next.toggle(true);
                    }
                    for (final Object next2 : next.getSettings()) {
                        final JsonElement value3 = asJsonObject.get(((Setting) next2).getName().toString());
                        if (value3 == null) {
                            continue;
                        }
                        this.a((Setting) next2, value3, next);
                    }
                }
            }
        } catch (final Exception ex) {
            System.err.println("Error loading profile: " + ex.getMessage());
        }
    }

    private void a(final Setting setting, final JsonElement jsonElement, final Module module) {
        try {
            if (setting instanceof final BooleanSetting booleanSetting) {
                if (jsonElement.isJsonPrimitive()) {
                    booleanSetting.setValue(jsonElement.getAsBoolean());
                }
            } else if (setting instanceof final ModeSetting enumSetting) {
                if (jsonElement.isJsonPrimitive()) {
                    final int asInt = jsonElement.getAsInt();
                    if (asInt != -1) {
                        enumSetting.setModeIndex(asInt);
                    } else {
                        enumSetting.setModeIndex(enumSetting.getOriginalValue());
                    }
                }
            } else if (setting instanceof final NumberSetting numberSetting) {
                if (jsonElement.isJsonPrimitive()) {
                    numberSetting.getValue(jsonElement.getAsDouble());
                }
            } else if (setting instanceof final BindSetting bindSetting) {
                if (jsonElement.isJsonPrimitive()) {
                    final int asInt2 = jsonElement.getAsInt();
                    bindSetting.setValue(asInt2);
                    if (bindSetting.isModuleKey()) {
                        module.a(asInt2);
                    }
                }
            } else if (setting instanceof final StringSetting stringSetting) {
                if (jsonElement.isJsonPrimitive()) {
                    stringSetting.setValue(jsonElement.getAsString());
                }
            } else if (setting instanceof final MinMaxSetting minMaxSetting) {
                if (jsonElement.isJsonObject()) {
                    final JsonObject asJsonObject = jsonElement.getAsJsonObject();
                    if (asJsonObject.has("min") && asJsonObject.has("max")) {
                        final double asDouble = asJsonObject.get("min").getAsDouble();
                        final double asDouble2 = asJsonObject.get("max").getAsDouble();
                        minMaxSetting.a(asDouble);
                        minMaxSetting.b(asDouble2);
                    }
                }
            } else if (setting instanceof ItemSetting && jsonElement.isJsonPrimitive()) {
                ((ItemSetting) setting).a(Registries.ITEM.get(Identifier.of(jsonElement.getAsString())));
            }
        } catch (final Exception ex) {
        }
    }

    public void shutdown() {
        try {
            this.a = new JsonObject();
            for (final Module module : Krypton.INSTANCE.getModuleManager().c()) {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("enabled", module.isEnabled());
                for (Setting setting : module.getSettings()) {
                    this.a(setting, jsonObject, module);
                }
                this.a.add(module.getName().toString(), jsonObject);
            }
        } catch (final Exception _t) {
            _t.printStackTrace(System.err);
        }
    }

    private void a(final Setting setting, final JsonObject jsonObject, final Module module) {
        try {
            if (setting instanceof final BooleanSetting booleanSetting) {
                jsonObject.addProperty(setting.getName().toString(), booleanSetting.getValue());
            } else if (setting instanceof final ModeSetting<?> enumSetting) {
                jsonObject.addProperty(setting.getName().toString(), enumSetting.getModeIndex());
            } else if (setting instanceof final NumberSetting numberSetting) {
                jsonObject.addProperty(setting.getName().toString(), numberSetting.getValue());
            } else if (setting instanceof final BindSetting bindSetting) {
                jsonObject.addProperty(setting.getName().toString(), bindSetting.getValue());
            } else if (setting instanceof final StringSetting stringSetting) {
                jsonObject.addProperty(setting.getName().toString(), stringSetting.getValue());
            } else if (setting instanceof MinMaxSetting) {
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("min", ((MinMaxSetting) setting).i());
                jsonObject2.addProperty("max", ((MinMaxSetting) setting).j());
                jsonObject.add(setting.getName().toString(), jsonObject2);
            } else if (setting instanceof final ItemSetting itemSetting) {
                jsonObject.addProperty(setting.getName().toString(), Registries.ITEM.getId(itemSetting.a()).toString());
            }
        } catch (final Exception ex) {
            System.err.println("Error saving setting " + setting.getName() + ": " + ex.getMessage());
        }
    }
}
