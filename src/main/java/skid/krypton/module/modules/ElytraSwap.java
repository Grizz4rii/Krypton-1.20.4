package skid.krypton.module.modules;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BindSetting;
import skid.krypton.setting.settings.BooleanSetting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;
import skid.krypton.utils.KeyUtils;

import java.util.function.Predicate;

public final class ElytraSwap extends Module {
    private final BindSetting c;
    private final NumberSetting d;
    private final BooleanSetting e;
    private final NumberSetting f;
    private final BooleanSetting g;
    private final NumberSetting h;
    private boolean i;
    private boolean j;
    private boolean k;
    private int l;
    private int m;
    private int n;
    private int o;

    public ElytraSwap() {
        super(EncryptedString.a("Elytra Swap"), EncryptedString.a("Seamlessly swap between an Elytra and a Chestplate with a configurable keybinding"), -1, Category.a);
        this.c = new BindSetting(EncryptedString.a("Activate Key"), 71, false);
        this.d = new NumberSetting(EncryptedString.a("Delay"), 0.0, 20.0, 0.0, 1.0);
        this.e = new BooleanSetting(EncryptedString.a("Switch Back"), true);
        this.f = new NumberSetting(EncryptedString.a("Switch Delay"), 0.0, 20.0, 0.0, 1.0);
        this.g = new BooleanSetting(EncryptedString.a("Move to slot"), true).a("If elytra is not in hotbar it will move it from inventory to preferred slot");
        this.h = new NumberSetting(EncryptedString.a("Elytra Slot"), 1.0, 9.0, 9.0, 1.0).a(EncryptedString.a("Your preferred elytra slot"));
        this.a(this.c, this.d, this.e, this.f, this.g, this.h);
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
        if (this.b.currentScreen != null) {
            return;
        }
        if (this.b.player == null) {
            return;
        }
        if (this.n > 0) {
            --this.n;
        } else if (KeyUtils.b(this.c.d())) {
            this.i = true;
            this.n = 4;
        }
        if (this.i) {
            if (this.o == -1) {
                this.o = this.b.player.getInventory().selectedSlot;
            }
            if (this.l < this.d.f()) {
                ++this.l;
                return;
            }
            Predicate predicate;
            if (this.b.player.getInventory().getArmorStack(2).isOf(Items.ELYTRA)) {
                predicate = (item -> item instanceof ArmorItem && ((ArmorItem) item).getSlotType() == EquipmentSlot.CHEST);
            } else {
                predicate = (item2 -> item2.equals(Items.ELYTRA));
            }
            if (!this.k) {
                if (!InventoryUtil.b(predicate)) {
                    if (!this.g.c()) {
                        this.k();
                        return;
                    }
                    while (!predicate.test(this.b.player.getInventory().getStack(9).getItem())) {
                    }
                    this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 9, this.h.f() - 1, SlotActionType.SWAP, this.b.player);
                    this.l = 0;
                    return;
                } else {
                    this.k = true;
                }
            }
            if (!this.j) {
                this.b.interactionManager.interactItem(this.b.player, Hand.MAIN_HAND);
                this.b.player.swingHand(Hand.MAIN_HAND);
                this.j = true;
            }
            if (this.e.c()) {
                this.j();
            } else {
                this.k();
            }
        }
    }

    private void j() {
        if (this.m < this.f.f()) {
            ++this.m;
            return;
        }
        InventoryUtil.a(this.o);
        this.k();
    }

    private void k() {
        this.o = -1;
        this.m = 0;
        this.l = 0;
        this.i = false;
        this.j = false;
        this.k = false;
    }

    private static byte[] zsxvaozopuqcoga() {
        return new byte[]{74, 39, 16, 36, 29, 19, 56, 3, 70, 81, 69, 54, 111, 33, 61, 6, 115, 31, 112, 28, 90};
    }
}
