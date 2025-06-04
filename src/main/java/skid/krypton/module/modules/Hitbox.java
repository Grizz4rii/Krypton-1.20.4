package skid.krypton.module.modules;

import net.minecraft.entity.player.PlayerEntity;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TargetMarginEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;

public final class Hitbox extends Module {
    private final NumberSetting c;
    private final BooleanSetting d;

    public Hitbox() {
        super(EncryptedString.of("HitBox"), EncryptedString.of("Expands a player's hitbox."), -1, Category.COMBAT);
        this.c = new NumberSetting(EncryptedString.of("Expand"), 0.0, 2.0, 0.5, 0.05);
        this.d = new BooleanSetting("Enable Render", true);
        this.addSettings(this.d, this.c);
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
    public void a(final TargetMarginEvent targetMarginEvent) {
        if (targetMarginEvent.entity instanceof PlayerEntity) {
            targetMarginEvent.floatCallbackInfoReturnable.setReturnValue((float) this.c.getValue());
        }
    }

    public double j() {
        if (!this.d.getValue()) {
            return 0.0;
        }
        return this.c.getValue();
    }
}