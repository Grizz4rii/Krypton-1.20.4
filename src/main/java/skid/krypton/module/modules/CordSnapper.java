// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module.modules;

import skid.krypton.auth.EmbedSender;
import skid.krypton.event.EventListener;
import skid.krypton.events.*;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.setting.settings.BindSetting;
import skid.krypton.setting.settings.StringSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.KeyUtils;

import java.util.concurrent.CompletableFuture;

public final class CordSnapper extends Module {
    private final BindSetting c;
    private final StringSetting d;
    private int e;

    public CordSnapper() {
        super(EncryptedString.a("Cord Snapper"), EncryptedString.a("Sends base coordinates to discord webhook"), -1, Category.b);
        this.c = new BindSetting(EncryptedString.a("Activate Key"), -1, false);
        this.d = new StringSetting(EncryptedString.a("Webhook"), "");
        this.e = 0;
        this.a(this.c, this.d);
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
        if (this.b.player == null) {
            return;
        }
        if (this.e > 0) {
            --this.e;
            return;
        }
        if (KeyUtils.b(this.c.d())) {
            new EmbedSender(this.d.a).a("Coordinates: x: " + this.b.player.method_23317() + " y: " + this.b.player.method_23318() + " z: " + this.b.player.method_23321());
            CompletableFuture.runAsync(() -> embedSender.a());
            this.e = 40;
        }
    }
}
