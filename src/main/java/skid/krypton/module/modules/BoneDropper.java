package skid.krypton.module.modules;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import skid.krypton.enums.Enum1;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.ModeSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;

public final class BoneDropper extends Module {
    private final ModeSetting<Enum1> c;
    private final NumberSetting d;
    private final NumberSetting e;
    private int f;
    private boolean g;

    public BoneDropper() {
        super(EncryptedString.a("Bone Dropper"), EncryptedString.a("Automatically drops bones from spawner"), -1, Category.c);
        this.c = new ModeSetting<Enum1>(EncryptedString.a("Mode"), Enum1.a, Enum1.class);
        this.d = new NumberSetting(EncryptedString.a("Drop Delay"), 0.0, 120.0, 30.0, 1.0).getValue(EncryptedString.a("How often it should start dropping bones in minutes"));
        this.e = new NumberSetting(EncryptedString.a("Page Switch Delay"), 0.0, 720.0, 4.0, 1.0).getValue(EncryptedString.a("How often it should switch pages in seconds"));
        this.a(this.c, this.d, this.e);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.f = 20;
        this.g = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final TickEvent tickEvent) {
        if (this.f > 0) {
            --this.f;
            return;
        }
        if (this.b.player == null) {
            return;
        }
        if (this.c.isMode(Enum1.a)) {
            if (!(this.b.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
                KeyBinding.onKeyPressed(InputUtil.Type.MOUSE.createFromCode(1));
                this.f = 20;
                return;
            }
            if (this.b.player.currentScreenHandler.getSlot(13).getStack().isOf(Items.SKELETON_SKULL)) {
                this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 11, 0, SlotActionType.PICKUP, this.b.player);
                this.f = 20;
                return;
            }
            if (!this.b.player.currentScreenHandler.getSlot(53).getStack().isOf(Items.GOLD_INGOT)) {
                this.b.player.closeHandledScreen();
                this.f = 20;
                return;
            }
            if (this.b.player.currentScreenHandler.getSlot(48).getStack().isOf(Items.ARROW)) {
                this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 48, 0, SlotActionType.PICKUP, this.b.player);
                this.f = 20;
                return;
            }
            boolean b = true;
            for (int i = 0; i < 45; ++i) {
                if (!this.b.player.currentScreenHandler.getSlot(i).getStack().isOf(Items.BONE)) {
                    b = false;
                    break;
                }
            }
            if (b) {
                this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 52, 1, SlotActionType.THROW, this.b.player);
                this.g = true;
                this.f = this.e.getIntValue() * 20;
            } else if (this.g) {
                this.g = false;
                this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 50, 0, SlotActionType.PICKUP, this.b.player);
                this.f = 20;
            } else {
                this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 45, 1, SlotActionType.THROW, this.b.player);
                this.g = false;
                this.f = 1200 * this.d.getIntValue();
            }
        } else {
            final ScreenHandler currentScreenHandler = this.b.player.currentScreenHandler;
            if (!(this.b.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
                this.b.getNetworkHandler().sendChatCommand("order");
                this.f = 20;
                return;
            }
            if (((GenericContainerScreenHandler) currentScreenHandler).getRows() == 6) {
                if (currentScreenHandler.getSlot(49).getStack().isOf(Items.MAP)) {
                    this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 51, 0, SlotActionType.PICKUP, this.b.player);
                    this.f = 20;
                    return;
                }
                for (int j = 0; j < 45; ++j) {
                    if (currentScreenHandler.getSlot(j).getStack().isOf(Items.BONE)) {
                        this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, j, 1, SlotActionType.THROW, this.b.player);
                        this.f = this.d.getIntValue();
                        return;
                    }
                }
                int n;
                if (this.g) {
                    n = 45;
                } else {
                    n = 53;
                }
                this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, n, 0, SlotActionType.PICKUP, this.b.player);
                this.g = !this.g;
                this.f = this.e.getIntValue();
            } else if (((GenericContainerScreenHandler) currentScreenHandler).getRows() == 3) {
                if (this.b.currentScreen == null) {
                    return;
                }
                if (this.b.currentScreen.getTitle().getString().contains("Your Orders")) {
                    for (int k = 0; k < 26; ++k) {
                        if (currentScreenHandler.getSlot(k).getStack().isOf(Items.BONE)) {
                            this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, k, 0, SlotActionType.PICKUP, this.b.player);
                            this.f = 20;
                            return;
                        }
                    }
                    this.f = 200;
                    return;
                }
                if (this.b.currentScreen.getTitle().getString().contains("Edit Order")) {
                    if (currentScreenHandler.getSlot(13).getStack().isOf(Items.CHEST)) {
                        this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 13, 0, SlotActionType.PICKUP, this.b.player);
                        this.f = 20;
                        return;
                    }
                    if (currentScreenHandler.getSlot(15).getStack().isOf(Items.CHEST)) {
                        this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 15, 0, SlotActionType.PICKUP, this.b.player);
                        this.f = 20;
                        return;
                    }
                }
                this.f = 200;
            }
        }
    }
}
