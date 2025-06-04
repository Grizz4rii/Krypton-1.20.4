package skid.krypton.module.modules;

import net.minecraft.entity.player.PlayerEntity;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TargetMarginEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;

public final class Hitbox extends Module {
    private final NumberSetting c;
    private final BooleanSetting d;

    public Hitbox() {
        super(EncryptedString.a("HitBox"), EncryptedString.a("Expands a player's hitbox."), -1, Category.a);
        this.c = new NumberSetting(EncryptedString.a("Expand"), 0.0, 2.0, 0.5, 0.05);
        this.d = new BooleanSetting("Enable Render", true);
        this.a(this.d, this.c);
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
        if (targetMarginEvent.a instanceof PlayerEntity) {
            targetMarginEvent.b.setReturnValue((float) this.c.a());
        }
    }

    public double j() {
        if (!this.d.c()) {
            return 0.0;
        }
        return this.c.a();
    }

    private static byte[] pqiwnsxnnjwdwse() {
        return new byte[]{21, 113, 66, 45, 27, 111, 12, 123, 125, 48, 5, 58, 42, 91, 97, 119, 91, 109, 25};
    }
}
