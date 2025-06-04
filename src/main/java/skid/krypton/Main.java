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

    private static byte[] nmrcmehxcqqammw() {
        return new byte[]{92, 4, 15, 125};
    }

    private static byte[] mrvdqzewgrkicsx() {
        return new byte[]{-109, -55, 54, 3, 105, 65, 59, 54, 101, 65, 62, 59, 101, 94, 58, 36, 104, 29};
    }

    private static byte[] flgjatbyclxvuoy() {
        return new byte[]{-109, -56, 55, 39, 104, 92, 60, 41, 109, 80, 60, 43, 106, 67, 62, 43, 107, 126, 62, 43, 100, 75};
    }
}
