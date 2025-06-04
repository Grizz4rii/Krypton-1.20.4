package skid.krypton.module.modules;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.AttackEvent;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EnchantmentUtil;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;

public final class MaceSwap extends Module {
    private final BooleanSetting c;
    private final BooleanSetting d;
    private final BooleanSetting e;
    private final BooleanSetting f;
    private final BooleanSetting g;
    private final NumberSetting h;
    private boolean i;
    private int j;
    private int k;

    public MaceSwap() {
        super(EncryptedString.a("Mace Swap"), EncryptedString.a("Switches to a mace when attacking."), -1, Category.a);
        this.c = new BooleanSetting(EncryptedString.a("Wind Burst"), true);
        this.d = new BooleanSetting(EncryptedString.a("Breach"), true);
        this.e = new BooleanSetting(EncryptedString.a("Only Sword"), false);
        this.f = new BooleanSetting(EncryptedString.a("Only Axe"), false);
        this.g = new BooleanSetting(EncryptedString.a("Switch Back"), true);
        this.h = new NumberSetting(EncryptedString.a("Switch Delay"), 0.0, 20.0, 0.0, 1.0);
        this.a(this.c, this.d, this.e, this.f, this.g, this.h);
    }

    @Override
    public void onEnable() {
        this.l();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.b.currentScreen != null) {
            return;
        }
        if (this.b.player == null) {
            return;
        }
        if (this.i) {
            if (this.g.c()) {
                this.k();
            } else {
                this.l();
            }
        }
    }

    @EventListener
    public void a(final AttackEvent attackEvent) {
        if (this.b.player == null) {
            return;
        }
        if (!this.j()) {
            return;
        }
        if (this.j == -1) {
            this.j = this.b.player.getInventory().selectedSlot;
        }
        if ((this.c.c() && this.d.c()) || (!this.c.c() && !this.d.c())) {
            InventoryUtil.a(Items.MACE);
        } else {
            if (this.c.c()) {
                InventoryUtil.a(itemStack -> EnchantmentUtil.a(itemStack, Enchantments.WIND_BURST));
            }
            if (this.d.c()) {
                InventoryUtil.a(itemStack2 -> EnchantmentUtil.a(itemStack2, Enchantments.BREACH));
            }
        }
        this.i = true;
    }

    private boolean j() {
        final Item item = this.b.player.getMainHandStack().getItem();
        if (this.e.c() && this.f.c()) {
            return item instanceof SwordItem || item instanceof AxeItem;
        }
        return (!this.e.c() || item instanceof SwordItem) && (!this.f.c() || item instanceof AxeItem);
    }

    private void k() {
        if (this.k < this.h.f()) {
            ++this.k;
            return;
        }
        InventoryUtil.a(this.j);
        this.l();
    }

    private void l() {
        this.j = -1;
        this.k = 0;
        this.i = false;
    }
}
