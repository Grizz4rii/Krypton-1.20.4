package skid.krypton.module.modules;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.InventoryUtil;

public final class AutoSpawnerSell extends Module {
    private final NumberSetting c;
    private final NumberSetting d;
    private final NumberSetting e;
    private final NumberSetting f;
    private int keybind;
    private int h;
    private boolean i;
    private boolean j;
    private boolean k;

    public AutoSpawnerSell() {
        super(EncryptedString.of("Auto Spawner Sell"), EncryptedString.of("Automatically drops bones from spawner and sells them"), -1, Category.DONUT);
        this.c = new NumberSetting(EncryptedString.of("Drop Delay"), 0.0, 120.0, 30.0, 1.0).getValue(EncryptedString.of("How often it should start dropping bones in minutes"));
        this.d = new NumberSetting(EncryptedString.of("Page Amount"), 1.0, 10.0, 2.0, 1.0).getValue(EncryptedString.of("How many pages should it drop before selling"));
        this.e = new NumberSetting(EncryptedString.of("Page Switch Delay"), 0.0, 720.0, 4.0, 1.0).getValue(EncryptedString.of("How often it should switch pages in seconds"));
        this.f = new NumberSetting(EncryptedString.of("delay"), 0.0, 20.0, 1.0, 1.0).getValue(EncryptedString.of("What should be delay in ticks"));
        this.addSettings(this.c, this.d, this.e, this.f);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.keybind = 20;
        this.i = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.keybind > 0) {
            --this.keybind;
            return;
        }
        if (this.mc.player == null) {
            return;
        }
        if (this.h >= this.d.getIntValue()) {
            this.j = true;
            this.h = 0;
            this.keybind = 40;
            return;
        }
        if (this.j) {
            final ScreenHandler currentScreenHandler = this.mc.player.currentScreenHandler;
            if (!(this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
                this.mc.getNetworkHandler().sendChatCommand("order " + this.k());
                this.keybind = 20;
                return;
            }
            if (((GenericContainerScreenHandler) currentScreenHandler).getRows() == 6) {
                final ItemStack stack = currentScreenHandler.getSlot(47).getStack();
                if (stack.isOf(Items.AIR)) {
                    this.keybind = 2;
                    this.mc.player.closeHandledScreen();
                    return;
                }
                for (final Object next : stack.getTooltip(Item.TooltipContext.create(this.mc.world), this.mc.player, TooltipType.BASIC)) {
                    final String string = next.toString();
                    if (string.contains("Most Money Per Item") && (((Text) next).getStyle().toString().contains("white") || string.contains("white"))) {
                        this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 47, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                        this.keybind = 5;
                        return;
                    }
                }
                for (int i = 0; i < 44; ++i) {
                    if (currentScreenHandler.getSlot(i).getStack().isOf(this.j())) {
                        this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, i, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                        this.keybind = 10;
                        return;
                    }
                }
                this.keybind = 40;
                this.mc.player.closeHandledScreen();
            } else if (((GenericContainerScreenHandler) currentScreenHandler).getRows() == 4) {
                final int b = InventoryUtil.b(Items.AIR);
                if (b <= 0) {
                    this.mc.player.closeHandledScreen();
                    this.keybind = 10;
                    return;
                }
                if (this.k && b == 36) {
                    this.k = false;
                    this.mc.player.closeHandledScreen();
                    return;
                }
                final Item j = this.j();
                while (true) {
                    final int n = 36;
                    final Item item = this.mc.player.currentScreenHandler.getStacks().get(n).getItem();
                    if (item != Items.AIR && item == j) {
                        this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, n, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                        this.keybind = this.f.getIntValue();
                        if (this.f.getIntValue() != 0) {
                            break;
                        }
                        continue;
                    }
                }
            } else if (((GenericContainerScreenHandler) currentScreenHandler).getRows() == 3) {
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 15, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                this.k = true;
                this.keybind = 10;
            }
        } else {
            final ScreenHandler fishHook = this.mc.player.currentScreenHandler;
            if (!(this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
                KeyBinding.onKeyPressed(InputUtil.Type.MOUSE.createFromCode(1));
                this.keybind = 20;
                return;
            }
            if (fishHook.getSlot(15).getStack().isOf(Items.LIME_STAINED_GLASS_PANE)) {
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 15, 1, SlotActionType.QUICK_MOVE, this.mc.player);
                this.keybind = 10;
                return;
            }
            if (this.mc.player.currentScreenHandler.getSlot(13).getStack().isOf(Items.SKELETON_SKULL)) {
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 11, 0, SlotActionType.PICKUP, this.mc.player);
                this.keybind = 20;
                return;
            }
            if (!this.mc.player.currentScreenHandler.getSlot(53).getStack().isOf(Items.GOLD_INGOT)) {
                this.mc.player.closeHandledScreen();
                this.keybind = 20;
                return;
            }
            if (this.mc.player.currentScreenHandler.getSlot(48).getStack().isOf(Items.ARROW)) {
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 48, 0, SlotActionType.PICKUP, this.mc.player);
                this.keybind = 20;
                return;
            }
            boolean b2 = true;
            for (int k = 0; k < 45; ++k) {
                if (!this.mc.player.currentScreenHandler.getSlot(k).getStack().isOf(Items.BONE)) {
                    b2 = false;
                    break;
                }
            }
            if (b2) {
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 52, 1, SlotActionType.THROW, this.mc.player);
                this.i = true;
                this.keybind = this.e.getIntValue() * 20;
                ++this.h;
            } else if (this.i) {
                this.i = false;
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 50, 0, SlotActionType.PICKUP, this.mc.player);
                this.keybind = 20;
            } else {
                this.i = false;
                if (this.h != 0) {
                    this.h = 0;
                    this.j = true;
                    this.keybind = 40;
                    return;
                }
                this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 45, 1, SlotActionType.THROW, this.mc.player);
                this.keybind = 1200 * this.c.getIntValue();
            }
        }
    }

    private Item j() {
        for (int i = 0; i < 35; ++i) {
            final ItemStack stack = ((Inventory) this.mc.player.getInventory()).getStack(i);
            if (!stack.isOf(Items.AIR)) {
                return stack.getItem();
            }
        }
        return Items.AIR;
    }

    private String k() {
        final Item j = this.j();
        if (j.equals(Items.BONE)) {
            return "Bones";
        }
        return j.getName().getString();
    }
}
