package skid.krypton.utils;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class CustomInventoryScreen extends InventoryScreen {
    public CustomInventoryScreen(final PlayerEntity playerEntity) {
        super(playerEntity);
    }

    protected void a(final Slot slot, final int n, final int n2, final SlotActionType slotActionType) {
    }

    public boolean a(final double n, final double n2, final int n3) {
        return false;
    }

    private static byte[] ilxblwvvytjbtvq() {
        return new byte[]{92, 127, 64, 7, 57, 109, 77, 12, 35, 44, 35, 17, 127, 106, 106, 103, 41, 117, 7, 64, 114, 32, 122, 23, 112, 116, 35, 38, 87, 44, 38, 27, 12, 24, 76, 59, 64, 84, 8, 35, 121, 121, 98, 61, 13, 73, 74, 99, 59, 15, 122, 57, 27, 123, 5, 81, 125, 29, 56, 67, 123, 101, 57, 87, 4, 26, 89, 101, 21, 57, 57, 78, 16, 96, 3, 95, 73, 51, 3, 31, 81, 78, 64, 27, 45};
    }
}
