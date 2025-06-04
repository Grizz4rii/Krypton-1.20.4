package skid.krypton.module.modules;

import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.Setting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;

public final class ShulkerDropper extends Module {
    private final NumberSetting c;
    private int d;

    public ShulkerDropper() {
        super(EncryptedString.of("Shulker Dropper"), EncryptedString.of("Goes to shop buys shulkers and drops automatically"), -1, Category.DONUT);
        this.c = new NumberSetting(EncryptedString.of("Delay"), 0.0, 20.0, 1.0, 1.0);
        this.d = 0;
        this.addSettings(new Setting[]{this.c});
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
        if (this.mc.player == null) {
            return;
        }
        if (this.d > 0) {
            --this.d;
            return;
        }
        final ScreenHandler currentScreenHandler = this.mc.player.currentScreenHandler;
        if (!(this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
            this.mc.getNetworkHandler().sendChatCommand("shop");
            this.d = 20;
            return;
        }
        if (((GenericContainerScreenHandler) currentScreenHandler).getRows() != 3) {
            return;
        }
        if (currentScreenHandler.getSlot(11).getStack().isOf(Items.END_STONE) && currentScreenHandler.getSlot(11).getStack().getCount() == 1) {
            this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 11, 0, SlotActionType.PICKUP, this.mc.player);
            this.d = 20;
            return;
        }
        if (currentScreenHandler.getSlot(17).getStack().isOf(Items.SHULKER_BOX)) {
            this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 17, 0, SlotActionType.PICKUP, this.mc.player);
            this.d = 20;
            return;
        }
        if (currentScreenHandler.getSlot(13).getStack().isOf(Items.SHULKER_BOX)) {
            this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, 23, 0, SlotActionType.PICKUP, this.mc.player);
            this.d = this.c.getIntValue();
            this.mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, Direction.DOWN));
        }
    }
}
