package skid.krypton.event.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skid.krypton.event.CancellableEvent;

public class TargetPoseEvent extends CancellableEvent {
    public Entity a;
    public CallbackInfoReturnable<EntityPose> b;

    public TargetPoseEvent(final Entity a, final CallbackInfoReturnable b) {
        this.a = a;
        this.b = (CallbackInfoReturnable<EntityPose>) b;
    }
}
