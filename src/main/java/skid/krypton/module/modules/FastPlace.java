// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PostItemUseEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;

public class FastPlace extends Module {
    private final BooleanSetting c;
    private final BooleanSetting d;
    private final BooleanSetting e;
    private final NumberSetting f;

    public FastPlace() {
        super(EncryptedString.a("Fast Place"), EncryptedString.a("Spams use action."), -1, Category.b);
        this.c = new BooleanSetting("Only XP", false);
        this.d = new BooleanSetting("Blocks", true);
        this.e = new BooleanSetting("Items", true);
        this.f = new NumberSetting("Delay", 0.0, 10.0, 0.0, 1.0);
        this.a(this.c, this.d, this.e, this.f);
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
        final ItemStack method_6047 = this.b.player.method_6047();
        final ItemStack method_6048 = this.b.player.method_6079();
        final Item item = method_6047.getItem();
        final Item item2 = this.b.player.method_6079().getItem();
        if (!method_6047.isOf(Items.EXPERIENCE_BOTTLE) && !method_6048.isOf(Items.EXPERIENCE_BOTTLE) && this.c.c()) {
            return;
        }
        if (!this.c.c()) {
            if (item instanceof BlockItem || item2 instanceof BlockItem) {
                if (!this.d.c()) {
                    return;
                }
            } else if (!this.e.c()) {
                return;
            }
        }
        if (item.getComponents().get(DataComponentTypes.FOOD) != null) {
            return;
        }
        if (item2.getComponents().get(DataComponentTypes.FOOD) != null) {
            return;
        }
        if (method_6047.isOf(Items.RESPAWN_ANCHOR) || method_6047.isOf(Items.GLOWSTONE) || method_6048.isOf(Items.RESPAWN_ANCHOR) || method_6048.isOf(Items.GLOWSTONE)) {
            return;
        }
        if (item instanceof RangedWeaponItem || item2 instanceof RangedWeaponItem) {
            return;
        }
        postItemUseEvent.a = this.f.f();
    }
}
