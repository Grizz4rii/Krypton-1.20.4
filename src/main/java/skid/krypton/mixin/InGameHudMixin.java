// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import skid.krypton.event.events.Render2DEvent;
import skid.krypton.manager.EventManager;

@Mixin({InGameHud.class})
public class InGameHudMixin {
    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void onRenderHud(final DrawContext drawContext, final RenderTickCounter renderTickCounter, final CallbackInfo callbackInfo) {
        EventManager.b(new Render2DEvent(drawContext, renderTickCounter.getTickDelta(true)));
    }
}
