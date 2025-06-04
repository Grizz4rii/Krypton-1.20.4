// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import skid.krypton.event.events.SendMovementPacketsEvent;
import skid.krypton.event.events.TickEvent;
import skid.krypton.manager.EventManager;

@Mixin({ClientPlayerEntity.class})
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow
    @Final
    protected MinecraftClient field_3937;

    public ClientPlayerEntityMixin(final ClientWorld clientWorld, final GameProfile gameProfile) {
        super(clientWorld, gameProfile);
    }

    @Inject(method = {"sendMovementPackets"}, at = {@At("HEAD")})
    private void onSendMovementPackets(final CallbackInfo callbackInfo) {
        EventManager.b(new SendMovementPacketsEvent());
    }

    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void onPlayerTick(final CallbackInfo callbackInfo) {
        EventManager.b(new TickEvent());
    }
}
