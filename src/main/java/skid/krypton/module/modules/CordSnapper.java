package skid.krypton.module.modules;

import skid.krypton.utils.embed.EmbedSender;
import skid.krypton.event.EventListener;
import skid.krypton.event.events.TickEvent;
import skid.krypton.module.Category;
import skid.krypton.module.Module;
import skid.krypton.module.setting.BindSetting;
import skid.krypton.module.setting.StringSetting;
import skid.krypton.utils.EncryptedString;
import skid.krypton.utils.KeyUtils;

import java.util.concurrent.CompletableFuture;

public final class CordSnapper extends Module {
    private final BindSetting c;
    private final StringSetting d;
    private int e;

    public CordSnapper() {
        super(EncryptedString.of("Cord Snapper"), EncryptedString.of("Sends base coordinates to discord webhook"), -1, Category.MISC);
        this.c = new BindSetting(EncryptedString.of("Activate Key"), -1, false);
        this.d = new StringSetting(EncryptedString.of("Webhook"), "");
        this.e = 0;
        this.addSettings(this.c, this.d);
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
        if (this.mc.player == null) {
            return;
        }
        if (this.e > 0) {
            --this.e;
            return;
        }
        if (KeyUtils.b(this.c.getValue())) {
            EmbedSender embedSender = new EmbedSender(this.d.value);
            embedSender.a("Coordinates: x: " + this.mc.player.getX() + " y: " + this.mc.player.getY() + " z: " + this.mc.player.getZ());
            CompletableFuture.runAsync(() -> {
                try {
                    embedSender.a();
                } catch (Throwable _t) {
                    _t.printStackTrace(System.err);
                }
            });
            this.e = 40;
        }
    }
}
