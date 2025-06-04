// 
// Decompiled by Procyon v0.6.0
// 

package skid.krypton.font;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

public final class GlyphPage {
    private int a;
    private int b;
    private final Font c;
    private final boolean d;
    private final boolean e;
    private final HashMap<Character, Rectangle> f;
    private BufferedImage g;
    private AbstractTexture h;

    public GlyphPage(final Font c, final boolean d, final boolean e) {
        this.b = -1;
        this.f = new HashMap<>();
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public void a(final char[] array) {
        double width = -1.0;
        double height = -1.0;
        final FontRenderContext frc = new FontRenderContext(new AffineTransform(), this.d, this.e);
        for (int i = 0; i < array.length; ++i) {
            final Rectangle2D stringBounds = this.c.getStringBounds(Character.toString(array[i]), frc);
            if (width < stringBounds.getWidth()) {
                width = stringBounds.getWidth();
            }
            if (height < stringBounds.getHeight()) {
                height = stringBounds.getHeight();
            }
        }
        final double a = width + 2.0;
        final double b = height + 2.0;
        this.a = (int) Math.ceil(Math.max(Math.ceil(Math.sqrt(a * a * array.length) / a), Math.ceil(Math.sqrt(b * b * array.length) / b)) * Math.max(a, b)) + 1;
        this.g = new BufferedImage(this.a, this.a, 2);
        final Graphics2D graphics = this.g.createGraphics();
        graphics.setFont(this.c);
        graphics.setColor(new Color(255, 255, 255, 0));
        graphics.fillRect(0, 0, this.a, this.a);
        graphics.setColor(Color.white);
        final RenderingHints.Key key_FRACTIONALMETRICS = RenderingHints.KEY_FRACTIONALMETRICS;
        Object o;
        if (this.e) {
            o = RenderingHints.VALUE_FRACTIONALMETRICS_ON;
        } else {
            o = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
        }
        graphics.setRenderingHint(key_FRACTIONALMETRICS, o);
        final RenderingHints.Key key_ANTIALIASING = RenderingHints.KEY_ANTIALIASING;
        Object o2;
        if (this.d) {
            o2 = RenderingHints.VALUE_ANTIALIAS_ON;
        } else {
            o2 = RenderingHints.VALUE_ANTIALIAS_OFF;
        }
        graphics.setRenderingHint(key_ANTIALIASING, o2);
        final RenderingHints.Key key_TEXT_ANTIALIASING = RenderingHints.KEY_TEXT_ANTIALIASING;
        Object o3;
        if (this.d) {
            o3 = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
        } else {
            o3 = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
        }
        graphics.setRenderingHint(key_TEXT_ANTIALIASING, o3);
        final FontMetrics fontMetrics = graphics.getFontMetrics();
        int height2 = 0;
        int x = 0;
        int y = 1;
        for (int j = 0; j < array.length; ++j) {
            final char c = array[j];
            final Rectangle value = new Rectangle();
            final Rectangle2D stringBounds2 = fontMetrics.getStringBounds(Character.toString(c), graphics);
            value.width = stringBounds2.getBounds().width + 8;
            value.height = stringBounds2.getBounds().height;
            if (x + value.width >= this.a) {
                x = 0;
                y += height2;
                height2 = 0;
            }
            value.x = x;
            value.y = y;
            if (value.height > this.b) {
                this.b = value.height;
            }
            if (value.height > height2) {
                height2 = value.height;
            }
            graphics.drawString(Character.toString(c), x + 2, y + fontMetrics.getAscent());
            x += value.width;
            this.f.put(c, value);
        }
    }

    public void a() {
        try {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(this.g, "png", output);
            final byte[] byteArray = output.toByteArray();
            final ByteBuffer put = BufferUtils.createByteBuffer(byteArray.length).put(byteArray);
            put.flip();
            this.h = new NativeImageBackedTexture(NativeImage.read(put));
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public void b() {
        RenderSystem.setShaderTexture(0, this.h.getGlId());
    }

    public void c() {
        RenderSystem.setShaderTexture(0, 0);
    }

    public float a(final MatrixStack matrixStack, final char c, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final Rectangle value = this.f.get(c);
        if (value == null) {
            return 0.0f;
        }
        final float n7 = value.x / (float) this.a;
        final float n8 = value.y / (float) this.a;
        final float n9 = value.width / (float) this.a;
        final float n10 = value.height / (float) this.a;
        final float n11 = (float) value.width;
        final float n12 = (float) value.height;
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        this.b();
        final BufferBuilder begin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        begin.vertex(matrixStack.peek().getPositionMatrix(), n, n2 + n12, 0.0f).color(n3, n5, n4, n6).texture(n7, n8 + n10);
        begin.vertex(matrixStack.peek().getPositionMatrix(), n + n11, n2 + n12, 0.0f).color(n3, n5, n4, n6).texture(n7 + n9, n8 + n10);
        begin.vertex(matrixStack.peek().getPositionMatrix(), n + n11, n2, 0.0f).color(n3, n5, n4, n6).texture(n7 + n9, n8);
        begin.vertex(matrixStack.peek().getPositionMatrix(), n, n2, 0.0f).color(n3, n5, n4, n6).texture(n7, n8);
        BufferRenderer.drawWithGlobalProgram(begin.end());
        this.c();
        return n11 - 8.0f;
    }

    public float a(final char c) {
        return (float) this.f.get(c).width;
    }

    public boolean d() {
        return this.d;
    }

    public boolean e() {
        return this.e;
    }

    public int f() {
        return this.b;
    }
}
