// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import skid.krypton.Krypton;
import skid.krypton.module.Module;
import skid.krypton.module.modules.Freecam;

@Mixin({Camera.class})
public class CameraMixin {
    @Unique
    private float tickDelta;

    @Inject(method = {"update"}, at = {@At("HEAD")})
    private void onUpdateHead(final BlockView blockView, final Entity entity, final boolean b, final boolean b2, final float tickDelta, final CallbackInfo callbackInfo) {
        this.tickDelta = tickDelta;
    }

    @ModifyArgs(method = {"update"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V"))
    private void update(final Args args) {
        final Module freecam = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(Freecam.class);
        if (freecam.isEnabled()) {
            args.set(0, (Object) ((Freecam) freecam).a(this.tickDelta));
            args.set(1, (Object) ((Freecam) freecam).b(this.tickDelta));
            args.set(2, (Object) ((Freecam) freecam).c(this.tickDelta));
        }
    }

    @ModifyArgs(method = {"update"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"))
    private void onUpdateSetRotationArgs(final Args args) {
        final Module freecam = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(Freecam.class);
        if (freecam.isEnabled()) {
            args.set(0, (Object) (float) ((Freecam) freecam).d(this.tickDelta));
            args.set(1, (Object) (float) ((Freecam) freecam).e(this.tickDelta));
        }
    }
}