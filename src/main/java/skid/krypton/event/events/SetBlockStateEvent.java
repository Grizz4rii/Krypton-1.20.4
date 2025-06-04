package skid.krypton.event.events;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import skid.krypton.event.CancellableEvent;

public class SetBlockStateEvent extends CancellableEvent {
    public BlockPos a;
    public BlockState b;
    public BlockState c;

    public SetBlockStateEvent(final BlockPos a, final BlockState b, final BlockState c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
