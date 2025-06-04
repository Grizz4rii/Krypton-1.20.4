// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.module;

import skid.krypton.utils.EncryptedString;

public enum Category {
    a("COMBAT", 0, EncryptedString.a("Combat")),
    b("MISC", 1, EncryptedString.a("Misc")),
    c("DONUT", 2, EncryptedString.a("Donut")),
    d("RENDER", 3, EncryptedString.a("Render")),
    e("CLIENT", 4, EncryptedString.a("Client"));

    public final CharSequence f;

    Category(final String name, final int ordinal, final CharSequence f) {
        this.f = f;
    }
}
