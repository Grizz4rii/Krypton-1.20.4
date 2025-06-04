package skid.krypton.module.modules;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import skid.krypton.enums.Enum8;
import skid.krypton.enums.Enum9;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.ModeSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;

public final class AutoSell extends Module {
    private final ModeSetting<Enum9> c;
    private final ModeSetting<Enum8> d;
    private final NumberSetting e;
    private int f;
    private boolean g;

    public AutoSell() {
        super(EncryptedString.of("Auto Sell"), EncryptedString.of("Automatically sells pickles"), -1, Category.DONUT);
        this.c = new ModeSetting<Enum9>(EncryptedString.of("Mode"), Enum9.a, Enum9.class);
        this.d = new ModeSetting<Enum8>(EncryptedString.of("Item"), Enum8.a, Enum8.class);
        this.e = new NumberSetting(EncryptedString.of("delay"), 0.0, 20.0, 2.0, 1.0).getValue(EncryptedString.of("What should be delay in ticks"));
        this.addSettings(this.c, this.d, this.e);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.f = 20;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.mc.player == null) {
            return;
        }
        if (this.f > 0) {
            --this.f;
            return;
        }
        if (this.c.getValue().equals(Enum9.a)) {
            final ScreenHandler currentScreenHandler = this.mc.player.currentScreenHandler;
            if (!(this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler) || ((GenericContainerScreenHandler) currentScreenHandler).getRows() != 5) {
                this.mc.getNetworkHandler().sendChatCommand("sell");
                this.f = 20;
                return;
            }
            if (InventoryUtil.b(Items.AIR) > 0) {
                int n;
                Item item;
                do {
                    n = 45;
                    item = this.mc.player.currentScreenHandler.getStacks().get(n).getItem();
                } while (item == Items.AIR || (!this.d.getValue().equals(Enum8.f) && !item.equals(this.j())));
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, n, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                this.f = this.e.getIntValue();
                return;
            }
            this.mc.player.closeHandledScreen();
        } else {
            final ScreenHandler fishHook = this.mc.player.currentScreenHandler;
            if (!(this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
                this.mc.getNetworkHandler().sendChatCommand("order " + this.k());
                this.f = 20;
                return;
            }
            if (((GenericContainerScreenHandler) fishHook).getRows() == 6) {
                final ItemStack stack = fishHook.getSlot(47).getStack();
                if (stack.isOf(Items.AIR)) {
                    this.f = 2;
                    return;
                }
                for (final Object next : stack.getTooltip(Item.TooltipContext.create(this.mc.world), this.mc.player, TooltipType.BASIC)) {
                    final String string = next.toString();
                    if (string.contains("Most Money Per Item") && (((Text) next).getStyle().toString().contains("white") || string.contains("white"))) {
                        this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 47, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                        this.f = 5;
                        return;
                    }
                }
                for (int i = 0; i < 44; ++i) {
                    if (fishHook.getSlot(i).getStack().isOf(this.j())) {
                        this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, i, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                        this.f = 10;
                        return;
                    }
                }
                this.f = 40;
                this.mc.player.closeHandledScreen();
                return;
            } else if (((GenericContainerScreenHandler) fishHook).getRows() == 4) {
                final int b = InventoryUtil.b(Items.AIR);
                if (b <= 0) {
                    this.mc.player.closeHandledScreen();
                    this.f = 10;
                    return;
                }
                if (this.g && b == 36) {
                    this.g = false;
                    this.mc.player.closeHandledScreen();
                    return;
                }
                final Item j = this.j();
                while (true) {
                    final int n2 = 36;
                    final Item item2 = this.mc.player.currentScreenHandler.getStacks().get(n2).getItem();
                    if (item2 != Items.AIR && item2 == j) {
                        this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, n2, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                        this.f = this.e.getIntValue();
                        if (this.e.getIntValue() != 0) {
                            break;
                        }
                        continue;
                    }
                }
                return;
            } else if (((GenericContainerScreenHandler) fishHook).getRows() == 3) {
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 15, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                this.f = 10;
                return;
            }
        }
        this.f = 20;
    }

    private Item j() {
        final Enum a = this.d.getValue();
        if (a == Enum8.f) {
            for (int i = 0; i < 35; ++i) {
                final ItemStack stack = ((Inventory) this.mc.player.getInventory()).getStack(i);
                if (!stack.isOf(Items.AIR)) {
                    return stack.getItem();
                }
            }
            return Items.AIR;
        }
        final int n = a.ordinal() ^ 0x7F1F7668;
        int n2;
        if (n != 0) {
            n2 = ((n * 31 >>> 4) % n ^ n >>> 16);
        } else {
            n2 = 0;
        }
        Item item = null;
        switch (n2) {
            case 105679472: {
                item = Items.PUMPKIN;
                break;
            }
            case 105679476: {
                item = Items.SWEET_BERRIES;
                break;
            }
            case 105679474: {
                item = Items.BAMBOO;
                break;
            }
            default: {
                item = Items.AIR;
                break;
            }
            case 105679470: {
                item = Items.BONE;
                break;
            }
            case 105679478: {
                item = Items.SEA_PICKLE;
                break;
            }
        }
        return item;
    }

    private String k() {
        final Item j = this.j();
        if (j.equals(Items.BONE)) {
            return "Bones";
        }
        return j.getName().getString();
    }
}
