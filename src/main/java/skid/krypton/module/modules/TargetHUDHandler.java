// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;

public class TargetHUDHandler {
    final /* synthetic */ TargetHUD this$0;

    private TargetHUDHandler(final TargetHUD this$0) {
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
            return TargetHUD.f(this.this$0).player != null && TargetHUD.g(this.this$0).player.method_6052() != null && TargetHUD.h(this.this$0).player.method_6052() instanceof PlayerEntity;
        }
        try {
            if (TargetHUD.a(this.this$0).player == null || TargetHUD.b(this.this$0).player.method_6052() == null || !(TargetHUD.c(this.this$0).player.method_6052() instanceof PlayerEntity)) {
                return false;
            }
            final boolean contains = string.contains(Hand.MAIN_HAND.toString());
            final boolean contains2 = string.contains("INTERACT_AT");
            if (contains && contains2) {
                return true;
            }
        } catch (final Exception ex2) {
            return TargetHUD.f(this.this$0).player != null && TargetHUD.g(this.this$0).player.method_6052() != null && TargetHUD.h(this.this$0).player.method_6052() instanceof PlayerEntity;
        }
        try {
            return TargetHUD.d(this.this$0).player.field_6252 && TargetHUD.e(this.this$0).player.method_6052() != null;
        } catch (final Exception ex3) {
        }
        return TargetHUD.f(this.this$0).player != null && TargetHUD.g(this.this$0).player.method_6052() != null && TargetHUD.h(this.this$0).player.method_6052() instanceof PlayerEntity;
    }
}
