package skid.krypton.event.events;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import skid.krypton.event.CancellableEvent;

public class AttackBlockEvent extends CancellableEvent {
    public BlockPos a;
    public Direction b;

    public AttackBlockEvent(final BlockPos a, final Direction b) {
        this.a = a;
        this.b = b;
    }
}
