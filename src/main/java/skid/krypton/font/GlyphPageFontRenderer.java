package skid.krypton.font;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import skid.krypton.utils.EncryptedString;

import java.awt.*;
import java.util.Random;

public final class GlyphPageFontRenderer {
    public Random a;
    private float b;
    private float c;
    private final int[] d;
    private boolean e;
    private boolean f;
    private boolean g;
    private boolean h;
    private final GlyphPage i;
    private final GlyphPage j;
    private final GlyphPage k;
    private final GlyphPage l;

    public GlyphPageFontRenderer(final GlyphPage i, final GlyphPage j, final GlyphPage k, final GlyphPage l) {
        this.a = new Random();
        this.d = new int[32];
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
        for (int n = 0; n < 32; ++n) {
            final int n2 = (n >> 3 & 0x1) * 85;
            int n3 = (n >> 2 & 0x1) * 170 + n2;
            int n4 = (n >> 1 & 0x1) * 170 + n2;
            int n5 = (n & 0x1) * 170 + n2;
            if (n == 6) {
                n3 += 85;
            }
            if (n >= 16) {
                n3 /= 4;
                n4 /= 4;
                n5 /= 4;
            }
            this.d[n] = ((n3 & 0xFF) << 16 | (n4 & 0xFF) << 8 | (n5 & 0xFF));
        }
    }

    public static GlyphPageFontRenderer a(final CharSequence charSequence, final int n, final boolean b, final boolean b2, final boolean b3) {
        final char[] array = new char[256];
        for (int i = 0; i < 256; ++i) {
            array[i] = (char) i;
        }
        final GlyphPage glyphPage = new GlyphPage(new Font(charSequence.toString(), 0, n), true, true);
        glyphPage.a(array);
        glyphPage.a();
        GlyphPage glyphPage2;
        if (b) {
            glyphPage2 = new GlyphPage(new Font(charSequence.toString(), 1, n), true, true);
            glyphPage2.a(array);
            glyphPage2.a();
        } else {
            glyphPage2 = glyphPage;
        }
        GlyphPage glyphPage3;
        if (b2) {
            glyphPage3 = new GlyphPage(new Font(charSequence.toString(), 2, n), true, true);
            glyphPage3.a(array);
            glyphPage3.a();
        } else {
            glyphPage3 = glyphPage;
        }
        GlyphPage glyphPage4;
        if (b3) {
            glyphPage4 = new GlyphPage(new Font(charSequence.toString(), 3, n), true, true);
            glyphPage4.a(array);
            glyphPage4.a();
        } else {
            glyphPage4 = glyphPage;
        }
        return new GlyphPageFontRenderer(glyphPage, glyphPage2, glyphPage3, glyphPage4);
    }

    public static GlyphPageFontRenderer init(final CharSequence charSequence, final int n, final boolean b, final boolean b2, final boolean b3) {
        final char[] array = new char[256];
        for (int i = 0; i < 256; ++i) {
            array[i] = (char) i;
        }
        Font deriveFont = null;
        try {
            deriveFont = Font.createFont(0, GlyphPageFontRenderer.class.getResourceAsStream(charSequence.toString())).deriveFont(0, (float) n);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
        final GlyphPage glyphPage = new GlyphPage(deriveFont, true, true);
        glyphPage.a(array);
        glyphPage.a();
        GlyphPage glyphPage2 = null;
        GlyphPage glyphPage4 = null;
        GlyphPage glyphPage3 = null;
        try {
            glyphPage2 = glyphPage;
            glyphPage3 = (glyphPage4 = glyphPage2);
            glyphPage2 = (glyphPage3 = glyphPage4);
            if (b) {
                final GlyphPage glyphPage5 = new GlyphPage(Font.createFont(0, GlyphPageFontRenderer.class.getResourceAsStream(charSequence.toString())).deriveFont(1, (float) n), true, true);
                glyphPage5.a(array);
                glyphPage5.a();
                glyphPage2 = glyphPage5;
            }
            if (b2) {
                final GlyphPage glyphPage6 = new GlyphPage(Font.createFont(0, GlyphPageFontRenderer.class.getResourceAsStream(charSequence.toString())).deriveFont(2, (float) n), true, true);
                glyphPage6.a(array);
                glyphPage6.a();
                glyphPage3 = glyphPage6;
            }
            if (b3) {
                final GlyphPage glyphPage7 = new GlyphPage(Font.createFont(0, GlyphPageFontRenderer.class.getResourceAsStream(charSequence.toString())).deriveFont(3, (float) n), true, true);
                glyphPage7.a(array);
                glyphPage7.a();
                glyphPage4 = glyphPage7;
            }
        } catch (final Exception ex2) {
            ex2.printStackTrace();
        }
        return new GlyphPageFontRenderer(glyphPage, glyphPage2, glyphPage3, glyphPage4);
    }

    public int a(final MatrixStack matrixStack, final CharSequence charSequence, final float n, final float n2, final int n3) {
        return this.a(matrixStack, charSequence, n, n2, n3, true);
    }

    public int a(final MatrixStack matrixStack, final CharSequence charSequence, final double n, final double n2, final int n3) {
        return this.a(matrixStack, charSequence, (float) n, (float) n2, n3, true);
    }

    public int b(final MatrixStack matrixStack, final CharSequence charSequence, final float n, final float n2, final int n3) {
        return this.a(matrixStack, charSequence, n, n2, n3, false);
    }

    public int b(final MatrixStack matrixStack, final CharSequence charSequence, final double n, final double n2, final int n3) {
        return this.a(matrixStack, charSequence, (float) n, (float) n2, n3, false);
    }

    public int a(final MatrixStack matrixStack, final CharSequence charSequence, final double n, final double n2, final float n3, final int n4) {
        return this.a(matrixStack, charSequence, (float) n - this.a(charSequence) / 2, (float) n2, n3, n4, false);
    }

    public int c(final MatrixStack matrixStack, final CharSequence charSequence, final double n, final double n2, final int n3) {
        return this.a(matrixStack, charSequence, (float) n - this.a(charSequence) / 2, (float) n2, n3, false);
    }

    public int d(final MatrixStack matrixStack, final CharSequence charSequence, final double n, final double n2, final int n3) {
        return this.a(matrixStack, charSequence, (float) n - this.a(charSequence) / 2, (float) n2, n3, true);
    }

    public int a(final MatrixStack matrixStack, final CharSequence charSequence, final float n, final float n2, final float n3, final int n4, final boolean b) {
        this.c();
        int n5;
        if (b) {
            n5 = Math.max(this.b(matrixStack, charSequence, n + 1.0f, n2 + 1.0f, n3, n4, true), this.b(matrixStack, charSequence, n, n2, n3, n4, false));
        } else {
            n5 = this.b(matrixStack, charSequence, n, n2, n3, n4, false);
        }
        return n5;
    }

    public int a(final MatrixStack matrixStack, final CharSequence charSequence, final float n, final float n2, final int n3, final boolean b) {
        this.c();
        int n4;
        if (b) {
            n4 = Math.max(this.b(matrixStack, charSequence, n + 1.0f, n2 + 1.0f, n3, true), this.b(matrixStack, charSequence, n, n2, n3, false));
        } else {
            n4 = this.b(matrixStack, charSequence, n, n2, n3, false);
        }
        return n4;
    }

    private int b(final MatrixStack matrixStack, final CharSequence charSequence, final float n, final float n2, int n3, final boolean b) {
        if (charSequence == null) {
            return 0;
        }
        if ((n3 & 0xFC000000) == 0x0) {
            n3 |= 0xFF000000;
        }
        if (b) {
            n3 = ((n3 & 0xFCFCFC) >> 2 | (n3 & 0xFF000000));
        }
        this.b = n * 2.0f;
        this.c = n2 * 2.0f;
        this.a(matrixStack, charSequence, b, n3);
        return (int) (this.b / 4.0f);
    }

    private int b(final MatrixStack matrixStack, final CharSequence charSequence, final float n, final float n2, final float n3, int n4, final boolean b) {
        if (charSequence == null) {
            return 0;
        }
        if ((n4 & 0xFC000000) == 0x0) {
            n4 |= 0xFF000000;
        }
        if (b) {
            n4 = ((n4 & 0xFCFCFC) >> 2 | (n4 & 0xFF000000));
        }
        this.b = n * 2.0f;
        this.c = n2 * 2.0f;
        this.a(matrixStack, charSequence, n3, b, n4);
        return (int) (this.b / 4.0f);
    }

    private void a(final MatrixStack matrixStack, final CharSequence charSequence, final boolean b, final int n) {
        GlyphPage glyphPage = this.b();
        float n2 = (n >> 16 & 0xFF) / 255.0f;
        float n3 = (n >> 8 & 0xFF) / 255.0f;
        float n4 = (n & 0xFF) / 255.0f;
        matrixStack.push();
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(770, 771);
        glyphPage.b();
        GlStateManager._texParameter(3553, 10240, 9729);
        for (int i = 0; i < charSequence.length(); ++i) {
            final char char1 = charSequence.charAt(i);
            if (char1 == '�' && i + 1 < charSequence.length()) {
                int index = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(charSequence.charAt(i + 1)));
                if (index < 16) {
                    this.e = false;
                    this.h = false;
                    this.g = false;
                    this.f = false;
                    if (index < 0) {
                        index = 15;
                    }
                    if (b) {
                        index += 16;
                    }
                    final int n5 = this.d[index];
                    n2 = (n5 >> 16 & 0xFF) / 255.0f;
                    n3 = (n5 >> 8 & 0xFF) / 255.0f;
                    n4 = (n5 & 0xFF) / 255.0f;
                } else if (index != 16) {
                    if (index == 17) {
                        this.e = true;
                    } else if (index == 18) {
                        this.h = true;
                    } else if (index == 19) {
                        this.g = true;
                    } else if (index == 20) {
                        this.f = true;
                    } else {
                        this.e = false;
                        this.h = false;
                        this.g = false;
                        this.f = false;
                    }
                }
                ++i;
            } else {
                glyphPage = this.b();
                glyphPage.b();
                this.a(glyphPage.a(matrixStack, char1, this.b, this.c, n2, n4, n3, (n >> 24 & 0xFF) / 255.0f), glyphPage);
            }
        }
        glyphPage.c();
        matrixStack.pop();
    }

    private void a(final MatrixStack matrixStack, final CharSequence charSequence, final float n, final boolean b, final int n2) {
        GlyphPage glyphPage = this.b();
        float n3 = (n2 >> 16 & 0xFF) / 255.0f;
        float n4 = (n2 >> 8 & 0xFF) / 255.0f;
        float n5 = (n2 & 0xFF) / 255.0f;
        matrixStack.push();
        matrixStack.scale(n, n, n);
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(770, 771);
        glyphPage.b();
        GlStateManager._texParameter(3553, 10240, 9729);
        for (int i = 0; i < charSequence.length(); ++i) {
            final char char1 = charSequence.charAt(i);
            if (char1 == '�' && i + 1 < charSequence.length()) {
                int index = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(charSequence.charAt(i + 1)));
                if (index < 16) {
                    this.e = false;
                    this.h = false;
                    this.g = false;
                    this.f = false;
                    if (index < 0) {
                        index = 15;
                    }
                    if (b) {
                        index += 16;
                    }
                    final int n6 = this.d[index];
                    n3 = (n6 >> 16 & 0xFF) / 255.0f;
                    n4 = (n6 >> 8 & 0xFF) / 255.0f;
                    n5 = (n6 & 0xFF) / 255.0f;
                } else if (index != 16) {
                    if (index == 17) {
                        this.e = true;
                    } else if (index == 18) {
                        this.h = true;
                    } else if (index == 19) {
                        this.g = true;
                    } else if (index == 20) {
                        this.f = true;
                    } else {
                        this.e = false;
                        this.h = false;
                        this.g = false;
                        this.f = false;
                    }
                }
                ++i;
            } else {
                glyphPage = this.b();
                glyphPage.b();
                this.a(glyphPage.a(matrixStack, char1, this.b, this.c, n3, n5, n4, (n2 >> 24 & 0xFF) / 255.0f), glyphPage);
            }
        }
        glyphPage.c();
        matrixStack.pop();
    }

    private void a(final float n, final GlyphPage glyphPage) {
        if (this.h) {
            final BufferBuilder begin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
            begin.vertex(this.b, this.c + glyphPage.f() / 2, 0.0f);
            begin.vertex(this.b + n, this.c + glyphPage.f() / 2, 0.0f);
            begin.vertex(this.b + n, this.c + glyphPage.f() / 2 - 1.0f, 0.0f);
            begin.vertex(this.b, this.c + glyphPage.f() / 2 - 1.0f, 0.0f);
            BufferRenderer.drawWithGlobalProgram(begin.end());
        }
        if (this.g) {
            final BufferBuilder begin2 = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
            int n2;
            if (this.g) {
                n2 = -1;
            } else {
                n2 = 0;
            }
            begin2.vertex(this.b + n2, this.c + glyphPage.f(), 0.0f);
            begin2.vertex(this.b + n, this.c + glyphPage.f(), 0.0f);
            begin2.vertex(this.b + n, this.c + glyphPage.f() - 1.0f, 0.0f);
            begin2.vertex(this.b + n2, this.c + glyphPage.f() - 1.0f, 0.0f);
            BufferRenderer.drawWithGlobalProgram(begin2.end());
        }
        this.b += n;
    }

    private GlyphPage b() {
        if (this.e && this.f) {
            return this.l;
        }
        if (this.e) {
            return this.j;
        }
        if (this.f) {
            return this.k;
        }
        return this.i;
    }

    private void c() {
        this.e = false;
        this.f = false;
        this.g = false;
        this.h = false;
    }

    public int a() {
        return this.i.f() / 2;
    }

    public int a(final CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < charSequence.length(); ++i) {
            final char char1 = charSequence.charAt(i);
            if (char1 == '\ufffd') {
                n2 = 1;
            } else if (n2 != 0 && char1 >= '0' && char1 <= 'r') {
                final int index = "0123456789abcdefklmnor".indexOf(char1);
                if (index < 16) {
                    this.e = false;
                    this.f = false;
                } else if (index == 17) {
                    this.e = true;
                } else if (index == 20) {
                    this.f = true;
                } else if (index == 21) {
                    this.e = false;
                    this.f = false;
                }
                ++i;
                n2 = 0;
            } else {
                if (n2 != 0) {
                    --i;
                }
                n += (int) (this.b().a(charSequence.charAt(i)) - 8.0f);
            }
        }
        return n / 2;
    }

    public CharSequence a(final CharSequence charSequence, final int n) {
        return this.a(charSequence, n, false);
    }

    public CharSequence a(final CharSequence charSequence, final int n, final boolean b) {
        final StringBuilder sb = new StringBuilder();
        int n2 = 0;
        int n3;
        if (b) {
            n3 = charSequence.length() - 1;
        } else {
            n3 = 0;
        }
        int n4;
        if (b) {
            n4 = -1;
        } else {
            n4 = 1;
        }
        int n5 = 0;
        while (n3 >= 0 && n3 < charSequence.length() && n3 < n) {
            char c = charSequence.charAt(n3);
            if (c == '\ufffd') {
                n2 = 1;
            } else if (n2 != 0 && c >= '0' && c <= 'r') {
                final int index = "0123456789abcdefklmnor".indexOf(c);
                if (index < 16) {
                    this.e = false;
                    this.f = false;
                } else if (index == 17) {
                    this.e = true;
                } else if (index == 20) {
                    this.f = true;
                } else if (index == 21) {
                    this.e = false;
                    this.f = false;
                }
                ++n3;
                n2 = 0;
            } else {
                if (n2 != 0) {
                    --n3;
                }
                c = charSequence.charAt(n3);
                n5 += (int) ((this.b().a(c) - 8.0f) / 2.0f);
            }
            if (n3 > n5) {
                break;
            }
            if (b) {
                sb.insert(0, c);
            } else {
                sb.append(c);
            }
            n3 += n4;
        }
        return EncryptedString.of(sb.toString());
    }
}
