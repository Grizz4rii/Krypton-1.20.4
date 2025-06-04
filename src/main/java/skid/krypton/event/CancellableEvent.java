// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.event;

public abstract class CancellableEvent implements Event {
    private boolean cancelled;

    public CancellableEvent() {
        this.cancelled = false;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }
}