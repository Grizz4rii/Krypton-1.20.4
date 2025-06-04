package skid.krypton.module.modules;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import skid.krypton.Krypton;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.PostItemUseEvent;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BindSetting;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;
import skid.krypton.utils.KeyUtils;

public final class AutoFirework extends Module {
    private final BindSetting c;
    private final NumberSetting d;
    private final BooleanSetting e;
    private final NumberSetting f;
    private boolean g;
    private boolean h;
    private int i;
    private int j;
    private int k;
    private int l;

    public AutoFirework() {
        super(EncryptedString.of("Auto Firework"), EncryptedString.of("Switches to a firework and uses it when you press a bind."), -1, Category.MISC);
        this.c = new BindSetting(EncryptedString.of("Activate Key"), -1, false);
        this.d = new NumberSetting(EncryptedString.of("Delay"), 0.0, 20.0, 0.0, 1.0);
        this.e = new BooleanSetting(EncryptedString.of("Switch Back"), true);
        this.f = new NumberSetting(EncryptedString.of("Switch Delay"), 0.0, 20.0, 0.0, 1.0).getValue(EncryptedString.of("Delay after using firework before switching back."));
        this.addSettings(this.c, this.d, this.e, this.f);
    }

    @Override
    public void onEnable() {
        this.k();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.mc.currentScreen != null) {
            return;
        }
        if (this.l > 0) {
            --this.l;
            return;
        }
        if (this.mc.player != null && KeyUtils.b(this.c.getValue()) && (Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(ElytraGlide.class).isEnabled() || this.mc.player.isFallFlying()) && this.mc.player.getInventory().getArmorStack(2).isOf(Items.ELYTRA) && !this.mc.player.getInventory().getMainHandStack().isOf(Items.FIREWORK_ROCKET) && !this.mc.player.getMainHandStack().getItem().getComponents().contains(DataComponentTypes.FOOD) && !(this.mc.player.getMainHandStack().getItem() instanceof ArmorItem)) {
            this.g = true;
        }
        if (this.g) {
            if (this.j == -1) {
                this.j = this.mc.player.getInventory().selectedSlot;
            }
            if (!InventoryUtil.a(Items.FIREWORK_ROCKET)) {
                this.k();
                return;
            }
            if (this.i < this.d.getIntValue()) {
                ++this.i;
                return;
            }
            if (!this.h) {
                this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
                this.h = true;
            }
            if (this.e.getValue()) {
                this.j();
            } else {
                this.k();
            }
        }
    }

    private void j() {
        if (this.k < this.f.getIntValue()) {
            ++this.k;
            return;
        }
        InventoryUtil.a(this.j);
        this.k();
    }

    private void k() {
        this.j = -1;
        this.i = 0;
        this.k = 0;
        this.l = 4;
        this.g = false;
        this.h = false;
    }

    @EventListener
    public void a(final PostItemUseEvent postItemUseEvent) {
        if (this.mc.player.getMainHandStack().isOf(Items.FIREWORK_ROCKET)) {
            this.h = true;
        }
        if (this.l > 0) {
            postItemUseEvent.cancel();
        }
    }
}
