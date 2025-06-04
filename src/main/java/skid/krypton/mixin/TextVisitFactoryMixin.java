package skid.krypton.mixin;

import net.minecraft.text.TextVisitFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import skid.krypton.Krypton;
import skid.krypton.module.Module;
import skid.krypton.module.modules.NameProtect;

@Mixin({TextVisitFactory.class})
public class TextVisitFactoryMixin {
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z", ordinal = 0), method = {"visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"}, index = 0)
    private static String adjustText(final String s) {
        final Module moduleByClass = Krypton.INSTANCE.MODULE_MANAGER.getModuleByClass(NameProtect.class);
        if (!moduleByClass.isEnabled()) {
            return s;
        }
        final String username = Krypton.mc.getSession().getUsername();
        if (s.contains(username)) {
            return s.replace(username, ((NameProtect) moduleByClass).getFakeName());
        }
        return s;
    }
}
