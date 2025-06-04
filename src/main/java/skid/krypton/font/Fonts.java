package skid.krypton.font;

public final class Fonts {
    public static GlyphPageFontRenderer fontRenderer;

    static {
        Fonts.fontRenderer = GlyphPageFontRenderer.init("/font.ttf", 35, false, false, false);
    }
}
