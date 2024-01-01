package de.cyklon.jui.component;

import de.cyklon.jresource.Resource;
import de.cyklon.jui.App;
import de.cyklon.jui.render.BufferedRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UIImage extends UIComponent {

    private static BufferedImage read(Resource resource) {
        try {
            return ImageIO.read(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage image;
    private Color backgroundColor;

    public UIImage(int x, int y, @NotNull Resource resource) {
        this(x, y, read(resource));
    }

    public UIImage(int x, int y, @NotNull BufferedImage image) {
        this(x, y, image.getWidth(), image.getHeight(), image);
    }

    public UIImage(int x, int y, int width, int height, @NotNull Resource resource) {
        this(x, y, width, height, read(resource));
    }

    public UIImage(int x, int y, int width, int height, @NotNull BufferedImage image) {
        super(x, y, width, height);
        this.image = image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setBackgroundColor(@Nullable Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setWidth(@Range(from = 0, to = Integer.MAX_VALUE) int width) {
        this.width = width;
    }

    @Override
    public void setHeight(@Range(from = 0, to = Integer.MAX_VALUE) int height) {
        this.height = height;
    }

    @Override
    protected void render(App app, Graphics g) {
        //if (width!=renderer.getWidth() || height!=renderer.getHeight()) renderer.setSize(width, height);
        //Graphics2D g2d = renderer.start();
        if (backgroundColor==null) g.drawImage(image, x, y, width, height, null);
        else g.drawImage(image, x, y, width, height, backgroundColor, null);
        //renderer.finish();
    }
}
