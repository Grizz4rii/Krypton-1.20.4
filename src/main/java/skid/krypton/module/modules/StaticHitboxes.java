// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TargetPoseEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.utils.EncryptedString;

public final class StaticHitboxes extends Module {
    public StaticHitboxes() {
        super(EncryptedString.a("Static HitBoxes"), EncryptedString.a("Expands a Player's Hitbox"), -1, Category.a);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final TargetPoseEvent targetPoseEvent) {
        if (this.isEnabled() && targetPoseEvent.a instanceof PlayerEntity && !((PlayerEntity) targetPoseEvent.a).isMainPlayer()) {
            targetPoseEvent.b.setReturnValue(EntityPose.STANDING);
        }
    }
}
