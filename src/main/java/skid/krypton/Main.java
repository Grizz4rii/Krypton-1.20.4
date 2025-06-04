// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton;

import net.fabricmc.api.ModInitializer;
import skid.krypton.auth.Auth;
import skid.krypton.utils.EncryptedString;

public final class Main implements ModInitializer {
    public void onInitialize() {
        final Krypton krypton = new Krypton(new Auth(new EncryptedString("127.0.0.1")));
    }
}