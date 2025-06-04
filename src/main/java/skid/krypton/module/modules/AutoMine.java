package skid.krypton.module.modules;

import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import skid.krypton.Krypton;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BooleanSetting;
import skid.krypton.module.setting.NumberSetting;
import skid.krypton.utils.EncryptedString;

public final class AutoMine extends Module {
    private final BooleanSetting c;
    private final NumberSetting d;
    private final NumberSetting e;

    public AutoMine() {
        super(EncryptedString.a("Auto Mine"), EncryptedString.a("Module that allows players to automatically mine"), -1, Category.b);
        this.c = new BooleanSetting(EncryptedString.a("Lock View"), true);
        this.d = new NumberSetting(EncryptedString.a("Pitch"), -180.0, 180.0, 0.0, 0.1);
        this.e = new NumberSetting(EncryptedString.a("Yaw"), -180.0, 180.0, 0.0, 0.1);
        this.a(this.c, this.d, this.e);
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
    public void a(final TickEvent tickEvent) {
        if (this.b.currentScreen != null) {
            return;
        }
        final Module moduleByClass = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(AutoEat.class);
        if (moduleByClass.isEnabled() && ((AutoEat) moduleByClass).j()) {
            return;
        }
        this.c(true);
        if (this.c.getValue()) {
            final float getYaw = this.b.player.getYaw();
            final float getPitch = this.b.player.getPitch();
            final float g = this.e.getFloatValue();
            final float g2 = this.d.getFloatValue();
            if (getYaw != g || getPitch != g2) {
                this.b.player.setYaw(g);
                this.b.player.setPitch(g2);
            }
        }
    }

    private void c(final boolean b) {
        if (!this.b.player.isUsingItem()) {
            if (b && this.b.crosshairTarget != null && this.b.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                final BlockHitResult blockHitResult = (BlockHitResult) this.b.crosshairTarget;
                final BlockPos blockPos = ((BlockHitResult) this.b.crosshairTarget).getBlockPos();
                if (!this.b.world.getBlockState(blockPos).isAir()) {
                    final Direction side = blockHitResult.getSide();
                    if (this.b.interactionManager.updateBlockBreakingProgress(blockPos, side)) {
                        this.b.particleManager.addBlockBreakingParticles(blockPos, side);
                        this.b.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            } else {
                this.b.interactionManager.cancelBlockBreaking();
            }
        }
    }
}
