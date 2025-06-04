// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import skid.krypton.event.events.MouseButtenEvent;
import skid.krypton.event.events.MouseScrolledEvent;
import skid.krypton.manager.EventManager;

@Mixin({Mouse.class})
public abstract class MouseMixin {
    @Inject(method = {"onMouseButton"}, at = {@At("HEAD")}, cancellable = true)
    private void onMouseButton(final long n, final int n2, final int n3, final int n4, final CallbackInfo callbackInfo) {
        if (n2 == -1) {
            return;
        }
        final MouseButtenEvent mouseButtenEvent = new MouseButtenEvent(n2, n, n3);
        EventManager.b(mouseButtenEvent);
        if (mouseButtenEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"onMouseScroll"}, at = {@At("HEAD")}, cancellable = true)
    private void onMouseScroll(final long n, final double n2, final double n3, final CallbackInfo callbackInfo) {
        final MouseScrolledEvent mouseScrolledEvent = new MouseScrolledEvent(n3);
        EventManager.b(mouseScrolledEvent);
        if (mouseScrolledEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
