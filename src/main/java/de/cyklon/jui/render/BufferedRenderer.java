package de.cyklon.jui.render;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class BufferedRenderer {

	public BufferedImage image;
	public BufferedImage current = null;
	private Graphics2D g2d = null;
	private Shape mask = null;
	private double rotation = 0;
	private Point rotationPoint;
	private boolean autoReset;

	public BufferedRenderer(int width, int height) {
		this(width, height, true);
	}

	public BufferedRenderer(int width, int height, boolean autoReset) {
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.rotationPoint = new Point(width/2, height/2);
		this.autoReset = autoReset;
	}

	public boolean isStarted() {
		return current!=null;
	}

	public Graphics2D start() {
		if (isStarted()) throw new IllegalStateException("Render process is already started");
		return this.g2d = (this.current = new BufferedImage(image.getWidth(), image.getHeight(), image.getType())).createGraphics();
	}

	public FontRenderer startFontRenderer() {
		return new FontRenderer(start());
	}
	
	public void setMask(Shape mask) {
		this.mask = mask;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void setRotationPoint(Point rotationPoint) {
		this.rotationPoint = rotationPoint;
	}

	public void setSize(int width, int height) {
		if (isStarted()) throw new IllegalStateException("cannot setSize while render process is started");
		synchronized (this) {
			this.image = new BufferedImage(width, height, image.getType());
		}
	}

	public int getWidth() {
		return this.image.getWidth();
	}

	public int getHeight() {
		return this.image.getHeight();
	}

	public void finish() {
		if (!isStarted()) throw new IllegalStateException("Render process isn`t started yet");
		if (g2d!=null) g2d.dispose();
		draw();
		this.current = null;
		if (autoReset) {
			this.mask = null;
			this.rotation = 0;
			this.rotationPoint = new Point(image.getWidth()/2, image.getHeight()/2);
		}
	}

	private void draw() {
		for (int x = 0; x < current.getWidth(); x++) {
			for (int y = 0; y < current.getHeight(); y++) {
				int argb = current.getRGB(x, y);
				if (hasAlpha(argb)) {
					if (mask==null || mask.contains(x, y)) {
						synchronized (this) {
							this.image.setRGB(x, y, argb);
						}
					}
				}
			}
		}
	}

	public void render(int x, int y, Graphics g) {
		double radians = Math.toRadians(rotation);
		AffineTransform tx = AffineTransform.getRotateInstance(radians, rotationPoint.getX(), rotationPoint.getY());
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		g.drawImage(op.filter(image, null), x, y, null);
	}

	private boolean hasAlpha(int argb) {
		return ((argb >> 24) & 0xFF) > 0;
	}

}
