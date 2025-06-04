//
// Decompiled by Procyon v0.6.0
//

package skid.krypton.mixin;

import skid.krypton.imixin.IKeybinding;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import skid.krypton.Krypton;

@Mixin({KeyBinding.class})
public abstract class KeyBindingMixin implements IKeybinding {
    @Shadow
    private InputUtil.Key boundKey;

    @Override
    public boolean isActuallyPressed() {
        return InputUtil.isKeyPressed(Krypton.e.getWindow().getHandle(), this.boundKey.getCode());
    }

    @Override
    public void resetPressed() {
        this.setPressed(this.isActuallyPressed());
    }

    @Shadow
    public abstract void setPressed(final boolean p0);
}