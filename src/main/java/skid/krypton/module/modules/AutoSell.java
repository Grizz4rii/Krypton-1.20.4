package skid.krypton.module.modules;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.ModeSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;

public final class AutoSell extends Module {
    private final ModeSetting<Mode> c;
    private final ModeSetting<Items> d;
    private final NumberSetting e;
    private int f;
    private boolean g;

    public AutoSell() {
        super(EncryptedString.a("Auto Sell"), EncryptedString.a("Automatically sells pickles"), -1, Category.c);
        this.c = new ModeSetting<Mode>(EncryptedString.a("Mode"), Mode.SELL, Mode.class);
        this.d = new ModeSetting<Items>(EncryptedString.a("Item"), Items.SEAPICKLE, Items.class);
        this.e = new NumberSetting(EncryptedString.a("delay"), 0.0, 20.0, 2.0, 1.0).getValue(EncryptedString.a("What should be delay in ticks"));
        this.a(this.c, this.d, this.e);
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
        if (this.b.player == null) {
            return;
        }
        if (this.f > 0) {
            --this.f;
            return;
        }
        if (this.c.getValue().equals(Mode.SELL)) {
            final ScreenHandler currentScreenHandler = this.b.player.currentScreenHandler;
            if (!(this.b.player.currentScreenHandler instanceof GenericContainerScreenHandler) || ((GenericContainerScreenHandler) currentScreenHandler).getRows() != 5) {
                this.b.getNetworkHandler().sendChatCommand("sell");
                this.f = 20;
                return;
            }
            if (InventoryUtil.b(net.minecraft.item.Items.AIR) > 0) {
                int n;
                Item item;
                do {
                    n = 45;
                    item = this.b.player.currentScreenHandler.getStacks().get(n).getItem();
                } while (item == net.minecraft.item.Items.AIR || (!this.d.getValue().equals(Items.ALL) && !item.equals(this.j())));
                this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, n, 1, SlotActionType.QUICK_MOVE, this.b.player);
                this.f = this.e.getIntValue();
                return;
            }
            this.b.player.closeHandledScreen();
        } else {
            final ScreenHandler fishHook = this.b.player.currentScreenHandler;
            if (!(this.b.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
                this.b.getNetworkHandler().sendChatCommand("order " + this.k());
                this.f = 20;
                return;
            }
            if (((GenericContainerScreenHandler) fishHook).getRows() == 6) {
                final ItemStack stack = fishHook.getSlot(47).getStack();
                if (stack.isOf(net.minecraft.item.Items.AIR)) {
                    this.f = 2;
                    return;
                }
                for (final Object next : stack.getTooltip(Item.TooltipContext.create(this.b.world), this.b.player, TooltipType.BASIC)) {
                    final String string = next.toString();
                    if (string.contains("Most Money Per Item") && (((Text) next).getStyle().toString().contains("white") || string.contains("white"))) {
                        this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 47, 1, SlotActionType.QUICK_MOVE, this.b.player);
                        this.f = 5;
                        return;
                    }
                }
                for (int i = 0; i < 44; ++i) {
                    if (fishHook.getSlot(i).getStack().isOf(this.j())) {
                        this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, i, 1, SlotActionType.QUICK_MOVE, this.b.player);
                        this.f = 10;
                        return;
                    }
                }
                this.f = 40;
                this.b.player.closeHandledScreen();
                return;
            } else if (((GenericContainerScreenHandler) fishHook).getRows() == 4) {
                final int b = InventoryUtil.b(net.minecraft.item.Items.AIR);
                if (b <= 0) {
                    this.b.player.closeHandledScreen();
                    this.f = 10;
                    return;
                }
                if (this.g && b == 36) {
                    this.g = false;
                    this.b.player.closeHandledScreen();
                    return;
                }
                final Item j = this.j();
                while (true) {
                    final int n2 = 36;
                    final Item item2 = this.b.player.currentScreenHandler.getStacks().get(n2).getItem();
                    if (item2 != net.minecraft.item.Items.AIR && item2 == j) {
                        this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, n2, 1, SlotActionType.QUICK_MOVE, this.b.player);
                        this.f = this.e.getIntValue();
                        if (this.e.getIntValue() != 0) {
                            break;
                        }
                        continue;
                    }
                }
                return;
            } else if (((GenericContainerScreenHandler) fishHook).getRows() == 3) {
                this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 15, 1, SlotActionType.QUICK_MOVE, this.b.player);
                this.f = 10;
                return;
            }
        }
        this.f = 20;
    }

    private Item j() {
        final Enum a = this.d.getValue();
        if (a == Items.ALL) {
            for (int i = 0; i < 35; ++i) {
                final ItemStack stack = ((Inventory) this.b.player.getInventory()).getStack(i);
                if (!stack.isOf(net.minecraft.item.Items.AIR)) {
                    return stack.getItem();
                }
            }
            return net.minecraft.item.Items.AIR;
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
                item = net.minecraft.item.Items.PUMPKIN;
                break;
            }
            case 105679476: {
                item = net.minecraft.item.Items.SWEET_BERRIES;
                break;
            }
            case 105679474: {
                item = net.minecraft.item.Items.BAMBOO;
                break;
            }
            default: {
                item = net.minecraft.item.Items.AIR;
                break;
            }
            case 105679470: {
                item = net.minecraft.item.Items.BONE;
                break;
            }
            case 105679478: {
                item = net.minecraft.item.Items.SEA_PICKLE;
                break;
            }
        }
        return item;
    }

    private String k() {
        final Item j = this.j();
        if (j.equals(net.minecraft.item.Items.BONE)) {
            return "Bones";
        }
        return j.getName().getString();
    }

    enum Items {
        SEAPICKLE("Sea_Pickle", 0),
        SWEETBERRIES("Sweet_Berries", 1),
        BAMBOO("Bamboo", 2),
        PUMPKIN("Pumpkin", 3),
        BONE("Bone", 4),
        ALL("All", 5);

        Items(final String name, final int ordinal) {
        }
    }

    public enum Mode {
        SELL("Sell", 0),
        ORDER("Order", 1);

        Mode(final String name, final int ordinal) {
        }
    }


}
