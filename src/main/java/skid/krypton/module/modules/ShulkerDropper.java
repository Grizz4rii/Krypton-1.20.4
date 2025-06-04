// 
// Decompiled by Procyon v0.6.0
// 

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
import skid.krypton.setting.Setting;
import skid.krypton.setting.settings.NumberSetting;
import skid.krypton.utils.EncryptedString;

public final class ShulkerDropper extends Module {
    private final NumberSetting c;
    private int d;

    public ShulkerDropper() {
        super(EncryptedString.a("Shulker Dropper"), EncryptedString.a("Goes to shop buys shulkers and drops automatically"), -1, Category.c);
        this.c = new NumberSetting(EncryptedString.a("Delay"), 0.0, 20.0, 1.0, 1.0);
        this.d = 0;
        this.a(new Setting[]{this.c});
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
        if (this.b.player == null) {
            return;
        }
        if (this.d > 0) {
            --this.d;
            return;
        }
        final ScreenHandler currentScreenHandler = this.b.player.currentScreenHandler;
        if (!(this.b.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
            this.b.getNetworkHandler().sendChatCommand("shop");
            this.d = 20;
            return;
        }
        if (((GenericContainerScreenHandler) currentScreenHandler).getRows() != 3) {
            return;
        }
        if (currentScreenHandler.getSlot(11).getStack().isOf(Items.END_STONE) && currentScreenHandler.getSlot(11).getStack().getCount() == 1) {
            this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 11, 0, SlotActionType.PICKUP, this.b.player);
            this.d = 20;
            return;
        }
        if (currentScreenHandler.getSlot(17).getStack().isOf(Items.SHULKER_BOX)) {
            this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 17, 0, SlotActionType.PICKUP, this.b.player);
            this.d = 20;
            return;
        }
        if (currentScreenHandler.getSlot(13).getStack().isOf(Items.SHULKER_BOX)) {
            this.b.interactionManager.clickSlot(this.b.player.currentScreenHandler.syncId, 23, 0, SlotActionType.PICKUP, this.b.player);
            this.d = this.c.f();
            this.b.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, Direction.DOWN));
        }
    }
}
