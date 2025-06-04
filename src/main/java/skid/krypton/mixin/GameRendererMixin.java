// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import skid.krypton.Krypton;
import skid.krypton.event.events.Render3DEvent;
import skid.krypton.manager.EventManager;
import skid.krypton.module.modules.Freecam;

@Mixin({GameRenderer.class})
public abstract class GameRendererMixin {
    @Shadow
    @Final
    private Camera camera;

    @Shadow
    public abstract Matrix4f getBasicProjectionMatrix(final double p0);

    @Shadow
    protected abstract double getFov(final Camera p0, final float p1, final boolean p2);

    @Inject(method = {"renderWorld"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 1)})
    private void onWorldRender(final RenderTickCounter rtc, final CallbackInfo ci) {
        EventManager.b(new Render3DEvent(new MatrixStack(), this.getBasicProjectionMatrix(this.getFov(this.camera, rtc.getTickDelta(true), true)), rtc.getTickDelta(true)));
    }

    @Inject(method = {"shouldRenderBlockOutline"}, at = {@At("HEAD")}, cancellable = true)
    private void onShouldRenderBlockOutline(final CallbackInfoReturnable<Boolean> cir) {
        if (Krypton.INSTANCE.b().getModuleByClass(Freecam.class).isEnabled()) {
            cir.setReturnValue(false);
        }
    }
}