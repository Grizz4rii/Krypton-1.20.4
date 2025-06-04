package skid.krypton.event.events;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skid.krypton.event.CancellableEvent;

public class TargetMarginEvent extends CancellableEvent {
    public Entity entity;
    public CallbackInfoReturnable<Float> floatCallbackInfoReturnable;

    public TargetMarginEvent(final Entity a, final CallbackInfoReturnable<Float> b) {
        this.entity = a;
        this.floatCallbackInfoReturnable = b;
    }
}
