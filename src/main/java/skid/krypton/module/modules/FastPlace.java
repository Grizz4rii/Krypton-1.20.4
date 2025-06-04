package skid.krypton.module.modules;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PostItemUseEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;

public class FastPlace extends Module {
    private final BooleanSetting c;
    private final BooleanSetting d;
    private final BooleanSetting e;
    private final NumberSetting f;

    public FastPlace() {
        super(EncryptedString.of("Fast Place"), EncryptedString.of("Spams use action."), -1, Category.MISC);
        this.c = new BooleanSetting("Only XP", false);
        this.d = new BooleanSetting("Blocks", true);
        this.e = new BooleanSetting("Items", true);
        this.f = new NumberSetting("Delay", 0.0, 10.0, 0.0, 1.0);
        this.addSettings(this.c, this.d, this.e, this.f);
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
    public void a(final PostItemUseEvent postItemUseEvent) {
        final ItemStack getMainHandStack = this.mc.player.getMainHandStack();
        final ItemStack getItemUseTime = this.mc.player.getOffHandStack();
        final Item item = getMainHandStack.getItem();
        final Item item2 = this.mc.player.getOffHandStack().getItem();
        if (!getMainHandStack.isOf(Items.EXPERIENCE_BOTTLE) && !getItemUseTime.isOf(Items.EXPERIENCE_BOTTLE) && this.c.getValue()) {
            return;
        }
        if (!this.c.getValue()) {
            if (item instanceof BlockItem || item2 instanceof BlockItem) {
                if (!this.d.getValue()) {
                    return;
                }
            } else if (!this.e.getValue()) {
                return;
            }
        }
        if (item.getComponents().get(DataComponentTypes.FOOD) != null) {
            return;
        }
        if (item2.getComponents().get(DataComponentTypes.FOOD) != null) {
            return;
        }
        if (getMainHandStack.isOf(Items.RESPAWN_ANCHOR) || getMainHandStack.isOf(Items.GLOWSTONE) || getItemUseTime.isOf(Items.RESPAWN_ANCHOR) || getItemUseTime.isOf(Items.GLOWSTONE)) {
            return;
        }
        if (item instanceof RangedWeaponItem || item2 instanceof RangedWeaponItem) {
            return;
        }
        postItemUseEvent.cooldown = this.f.getIntValue();
    }
}
