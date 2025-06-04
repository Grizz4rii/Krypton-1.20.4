// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skid.krypton.Krypton;
import skid.krypton.event.events.TargetMarginEvent;
import skid.krypton.event.events.TargetPoseEvent;
import skid.krypton.manager.EventManager;
import skid.krypton.module.Module;
import skid.krypton.module.modules.Freecam;

@Mixin({Entity.class})
public class EntityMixin {
    @Inject(method = {"getTargetingMargin"}, at = {@At("HEAD")}, cancellable = true)
    private void onSendMovementPackets(final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        EventManager.b(new TargetMarginEvent((Entity)(Object) this, callbackInfoReturnable));
    }

    @Inject(method = {"getPose"}, at = {@At("HEAD")}, cancellable = true)
    private void onGetPose(final CallbackInfoReturnable<EntityPose> callbackInfoReturnable) {
        EventManager.b(new TargetPoseEvent((Entity)(Object) this, callbackInfoReturnable));
    }

    @Inject(method = {"changeLookDirection"}, at = {@At("HEAD")}, cancellable = true)
    private void updateChangeLookDirection(final double n, final double n2, final CallbackInfo callbackInfo) {
        if ((Object) this != Krypton.e.player) {
            return;
        }
        final Module freecam = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(Freecam.class);
        if (freecam.isEnabled()) {
            ((Freecam) freecam).a(n * 0.15, n2 * 0.15);
            callbackInfo.cancel();
        }
    }
}