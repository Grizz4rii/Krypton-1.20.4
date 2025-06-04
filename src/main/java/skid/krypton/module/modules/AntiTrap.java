// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.entity.EntityType;
import skid.krypton.event.events.EntitySpawnEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.Setting;
import skid.krypton.utils.EncryptedString;

import java.util.ArrayList;
import java.util.EventListener;

public final class AntiTrap extends Module {
    public AntiTrap() {
        super(EncryptedString.a("Anti Trap"), EncryptedString.a("Module that helps you escape Polish traps"), -1, Category.c);
        this.a();
    }

    @Override
    public void onEnable() {
        this.j();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventListener
    public void a(final EntitySpawnEvent entitySpawnEvent) {
        if (this.a(entitySpawnEvent.a.getEntityType())) {
            entitySpawnEvent.cancel();
        }
    }

    private void j() {
        if (this.b.world == null) {
            return;
        }
        final ArrayList list = new ArrayList();
        this.b.world.getEntities().forEach(entity -> {
            if (entity != null && this.a(entity.getType())) {
                list2.add(entity);
            }
        });
        list.forEach(entity2 -> {
            if (!entity2.isRemoved()) {
                entity2.remove(Entity$RemovalReason.DISCARDED);
            }
        });
    }

    private boolean a(final EntityType entityType) {
        return entityType != null && (entityType.equals(EntityType.ARMOR_STAND) || entityType.equals(EntityType.CHEST_MINECART));
    }

    private static byte[] jmqumvdvcpenwqz() {
        return new byte[]{4, 2, 68, 51, 69, 23, 12, 25, 88, 53, 64, 24, 75, 17, 68, 121, 106, 53, 64, 54, 79, 80, 55};
    }
}
