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
        super(EncryptedString.of("Auto Mine"), EncryptedString.of("Module that allows players to automatically mine"), -1, Category.MISC);
        this.c = new BooleanSetting(EncryptedString.of("Lock View"), true);
        this.d = new NumberSetting(EncryptedString.of("Pitch"), -180.0, 180.0, 0.0, 0.1);
        this.e = new NumberSetting(EncryptedString.of("Yaw"), -180.0, 180.0, 0.0, 0.1);
        this.addSettings(this.c, this.d, this.e);
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
        if (this.mc.currentScreen != null) {
            return;
        }
        final Module moduleByClass = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(AutoEat.class);
        if (moduleByClass.isEnabled() && ((AutoEat) moduleByClass).j()) {
            return;
        }
        this.c(true);
        if (this.c.getValue()) {
            final float getYaw = this.mc.player.getYaw();
            final float getPitch = this.mc.player.getPitch();
            final float g = this.e.getFloatValue();
            final float g2 = this.d.getFloatValue();
            if (getYaw != g || getPitch != g2) {
                this.mc.player.setYaw(g);
                this.mc.player.setPitch(g2);
            }
        }
    }

    private void c(final boolean b) {
        if (!this.mc.player.isUsingItem()) {
            if (b && this.mc.crosshairTarget != null && this.mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                final BlockHitResult blockHitResult = (BlockHitResult) this.mc.crosshairTarget;
                final BlockPos blockPos = ((BlockHitResult) this.mc.crosshairTarget).getBlockPos();
                if (!this.mc.world.getBlockState(blockPos).isAir()) {
                    final Direction side = blockHitResult.getSide();
                    if (this.mc.interactionManager.updateBlockBreakingProgress(blockPos, side)) {
                        this.mc.particleManager.addBlockBreakingParticles(blockPos, side);
                        this.mc.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            } else {
                this.mc.interactionManager.cancelBlockBreaking();
            }
        }
    }
}
