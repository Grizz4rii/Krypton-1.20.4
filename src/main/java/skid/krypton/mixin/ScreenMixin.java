// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import skid.krypton.gui.ClickGUI;

@Mixin({Screen.class})
public class ScreenMixin {
    @Shadow
    @Nullable
    protected MinecraftClient client;

    @Inject(method = {"renderBackground"}, at = {@At("HEAD")}, cancellable = true)
    private void dontRenderBackground(final DrawContext drawContext, final int n, final int n2, final float n3, final CallbackInfo callbackInfo) {
        if (this.client.currentScreen instanceof ClickGUI) {
            callbackInfo.cancel();
        }
    }
}
