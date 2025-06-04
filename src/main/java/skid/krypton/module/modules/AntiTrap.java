// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.EntitySpawnEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.utils.EncryptedString;

import java.util.ArrayList;

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
        final ArrayList<Entity> list = new ArrayList<>();
        this.b.world.getEntities().forEach(entity -> {
            if (entity != null && this.a(entity.getType())) {
                list.add(entity);
            }
        });
        list.forEach(entity2 -> {
            if (!entity2.isRemoved()) {
                entity2.remove(Entity.RemovalReason.DISCARDED);
            }
        });
    }

    private boolean a(final EntityType<?> entityType) {
        return entityType != null && (entityType.equals(EntityType.ARMOR_STAND) || entityType.equals(EntityType.CHEST_MINECART));
    }
}