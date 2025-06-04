// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.mixin;

import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import skid.krypton.event.events.KeyEvent;
import skid.krypton.manager.EventManager;

@Mixin({Keyboard.class})
public class KeyboardMixin {
    @Inject(method = {"onKey"}, at = {@At("HEAD")}, cancellable = true)
    private void onPress(final long n, final int n2, final int n3, final int n4, final int n5, final CallbackInfo callbackInfo) {
        if (n2 == -1) {
            return;
        }
        final KeyEvent keyEvent = new KeyEvent(n2, n, n4);
        EventManager.b(keyEvent);
        if (keyEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
