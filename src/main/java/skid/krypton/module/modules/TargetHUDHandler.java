// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;

public class TargetHUDHandler {
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    final /* synthetic */ TargetHUD this$0;

    TargetHUDHandler(final TargetHUD this$0) {
        this.this$0 = this$0;
    }

    public boolean isAttackPacket(final PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket) {
        String string;
        try {
            string = playerInteractEntityC2SPacket.toString();
            if (string.contains("ATTACK")) {
                return true;
            }
        } catch (final Exception ex) {
            return MC.player != null && MC.player.getAttacking() != null && MC.player.getAttacking() instanceof PlayerEntity;
        }
        try {
            if (MC.player == null || MC.player.getAttacking() == null || !(MC.player.getAttacking() instanceof PlayerEntity)) {
                return false;
            }
            final boolean contains = string.contains(Hand.MAIN_HAND.toString());
            final boolean contains2 = string.contains("INTERACT_AT");
            if (contains && contains2) {
                return true;
            }
        } catch (final Exception ex2) {
            return MC.player != null && MC.player.getAttacking() != null && MC.player.getAttacking() instanceof PlayerEntity;
        }
        try {
            return MC.player.handSwinging && MC.player.getAttacking() != null;
        } catch (final Exception ex3) {
        }
        return MC.player != null && MC.player.getAttacking() != null && MC.player.getAttacking() instanceof PlayerEntity;
    }
}
