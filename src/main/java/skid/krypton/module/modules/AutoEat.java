// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.mixin.MinecraftClientAccessor;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;

public final class AutoEat extends Module {
    private final NumberSetting d;
    private final NumberSetting e;
    public boolean c;
    private int f;
    private int keybind;

    public AutoEat() {
        super(EncryptedString.a("Auto Eat"), EncryptedString.a(" It detects whenever the hungerbar/health falls a certain threshold, selects food in your hotbar, and starts eating."), -1, Category.b);
        this.d = new NumberSetting(EncryptedString.a("Health Threshold"), 0.0, 19.0, 17.0, 1.0);
        this.e = new NumberSetting(EncryptedString.a("Hunger Threshold"), 0.0, 19.0, 19.0, 1.0);
        this.a(this.d, this.e);
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
    public void a(final TickEvent tickEvent) {
        if (this.c) {
            if (this.j()) {
                if (this.b.player.getInventory().getStack(this.f).get(DataComponentTypes.FOOD) != null) {
                    final int k = this.k();
                    if (k == -1) {
                        this.n();
                        return;
                    }
                    this.b(k);
                }
                this.m();
            } else {
                this.n();
            }
        } else if (this.j()) {
            this.f = this.k();
            if (this.f != -1) {
                this.l();
            }
        }
    }

    public boolean j() {
        final boolean b = this.b.player.getHealth() <= this.d.f();
        final boolean b2 = this.b.player.getHungerManager().getFoodLevel() <= this.e.f();
        return this.k() != -1 && (b || b2);
    }

    private int k() {
        int n = -1;
        int n2 = -1;
        for (int i = 0; i < 9; ++i) {
            final Object value = this.b.player.getInventory().getStack(i).getItem().getComponents().get(DataComponentTypes.FOOD);
            if (value != null) {
                final int nutrition = ((FoodComponent) value).nutrition();
                if (nutrition > n2) {
                    n = i;
                    n2 = nutrition;
                }
            }
        }
        return n;
    }

    private void l() {
        this.keybind = this.b.player.getInventory().selectedSlot;
        this.m();
    }

    private void m() {
        this.b(this.f);
        this.c(true);
        if (!this.b.player.isUsingItem()) {
            ((MinecraftClientAccessor) this.b).invokeDoItemUse();
        }
        this.c = true;
    }

    private void n() {
        this.b(this.keybind);
        this.c(false);
        this.c = false;
    }

    private void c(final boolean pressed) {
        this.b.options.useKey.setPressed(pressed);
    }

    private void b(final int f) {
        InventoryUtil.a(f);
        this.f = f;
    }
}
