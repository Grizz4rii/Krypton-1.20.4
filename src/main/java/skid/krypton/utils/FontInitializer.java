// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.utils;

import skid.krypton.utils.font.FontRenderer;

public final class FontInitializer {
    public static FontRenderer fontRenderer;

    static {
        FontInitializer.fontRenderer = FontRenderer.init("/font.ttf", 35, false, false, false);
    }
}
