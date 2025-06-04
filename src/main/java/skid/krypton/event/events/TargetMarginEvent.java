package skid.krypton.event.events;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skid.krypton.event.CancellableEvent;

public class TargetMarginEvent extends CancellableEvent {
    public Entity a;
    public CallbackInfoReturnable<Float> b;

    public TargetMarginEvent(final Entity a, final CallbackInfoReturnable b) {
        this.a = a;
        this.b = (CallbackInfoReturnable<Float>) b;
    }
}
