package skid.krypton.event.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skid.krypton.event.CancellableEvent;

public class TargetPoseEvent extends CancellableEvent {
    public Entity entity;
    public CallbackInfoReturnable<EntityPose> entityPoseCallbackInfoReturnable;

    public TargetPoseEvent(final Entity a, final CallbackInfoReturnable<EntityPose> b) {
        this.entity = a;
        this.entityPoseCallbackInfoReturnable = b;
    }
}
