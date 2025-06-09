package skid.krypton.module.modules.combat;

import net.minecraft.block.Blocks; import net.minecraft.component.DataComponentTypes; import net.minecraft.item.Items; import net.minecraft.item.ShieldItem; import net.minecraft.util.hit.BlockHitResult; import net.minecraft.util.hit.HitResult; import net.minecraft.util.math.BlockPos; import org.lwjgl.glfw.GLFW; import skid.krypton.event.EventListener; import skid.krypton.event.events.TickEvent; import skid.krypton.module.Category; import skid.krypton.module.Module; import skid.krypton.module.setting.NumberSetting; import skid.krypton.utils.BlockUtil; import skid.krypton.utils.EncryptedString; import skid.krypton.utils.InventoryUtil;

public final class AnchorMacro extends Module { private final NumberSetting switchDelay = new NumberSetting(EncryptedString.of("Switch Delay"), 0.0, 20.0, 0.0, 1.0); private final NumberSetting glowstoneDelay = new NumberSetting(EncryptedString.of("Glowstone Delay"), 0

