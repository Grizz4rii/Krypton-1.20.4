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
import skid.krypton.module.setting.BindSetting;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.NumberSetting;
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
        super(EncryptedString.of("Elytra Swap"), EncryptedString.of("Seamlessly swap between an Elytra and a Chestplate with a configurable keybinding"), -1, Category.COMBAT);
        this.c = new BindSetting(EncryptedString.of("Activate Key"), 71, false);
        this.d = new NumberSetting(EncryptedString.of("Delay"), 0.0, 20.0, 0.0, 1.0);
        this.e = new BooleanSetting(EncryptedString.of("Switch Back"), true);
        this.f = new NumberSetting(EncryptedString.of("Switch Delay"), 0.0, 20.0, 0.0, 1.0);
        this.g = new BooleanSetting(EncryptedString.of("Move to slot"), true).setDescription("If elytra is not in hotbar it will move it from inventory to preferred slot");
        this.h = new NumberSetting(EncryptedString.of("Elytra Slot"), 1.0, 9.0, 9.0, 1.0).getValue(EncryptedString.of("Your preferred elytra slot"));
        this.addSettings(this.c, this.d, this.e, this.f, this.g, this.h);
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
        if (this.mc.player == null) {
            return;
        }
        if (this.n > 0) {
            --this.n;
        } else if (KeyUtils.b(this.c.getValue())) {
            this.i = true;
            this.n = 4;
        }
        if (this.i) {
            if (this.o == -1) {
                this.o = this.mc.player.getInventory().selectedSlot;
            }
            if (this.l < this.d.getIntValue()) {
                ++this.l;
                return;
            }
            Predicate predicate;
            if (this.mc.player.getInventory().getArmorStack(2).isOf(Items.ELYTRA)) {
                predicate = (item -> item instanceof ArmorItem && ((ArmorItem) item).getSlotType() == EquipmentSlot.CHEST);
            } else {
                predicate = (item2 -> item2.equals(Items.ELYTRA));
            }
            if (!this.k) {
                if (!InventoryUtil.b(predicate)) {
                    if (!this.g.getValue()) {
                        this.k();
                        return;
                    }
                    while (!predicate.test(this.mc.player.getInventory().getStack(9).getItem())) {
                    }
                    this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 9, this.h.getIntValue() - 1, SlotActionType.SWAP, this.mc.player);
                    this.l = 0;
                    return;
                } else {
                    this.k = true;
                }
            }
            if (!this.j) {
                this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
                this.mc.player.swingHand(Hand.MAIN_HAND);
                this.j = true;
            }
            if (this.e.getValue()) {
                this.j();
            } else {
                this.k();
            }
        }
    }

    private void j() {
        if (this.m < this.f.getIntValue()) {
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
