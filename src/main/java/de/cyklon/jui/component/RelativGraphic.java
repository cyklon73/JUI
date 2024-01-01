package de.cyklon.jui.component;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

class RelativGraphic extends Graphics {


	private final Graphics graphics;
	private final int x, y;

	public RelativGraphic(Graphics graphics, int x, int y) {
		this.graphics = graphics;
		this.x = x;
		this.y = y;
	}

	@Override
	public Graphics create() {
		return graphics.create();
	}

	@Override
	public void translate(int x, int y) {
		graphics.translate(x, y);
	}

	@Override
	public Color getColor() {
		return graphics.getColor();
	}

	@Override
	public void setColor(Color c) {
		graphics.setColor(c);
	}

	@Override
	public void setPaintMode() {
		graphics.setPaintMode();
	}

	@Override
	public void setXORMode(Color c1) {
		graphics.setXORMode(c1);
	}

	@Override
	public Font getFont() {
		return graphics.getFont();
	}

	@Override
	public void setFont(Font font) {
		graphics.setFont(font);
	}

	@Override
	public FontMetrics getFontMetrics(Font f) {
		return graphics.getFontMetrics(f);
	}

	@Override
	public Rectangle getClipBounds() {
		return graphics.getClipBounds();
	}

	@Override
	public void clipRect(int x, int y, int width, int height) {
		graphics.clipRect(x+this.x, y+this.y, width, height);
	}

	@Override
	public void setClip(int x, int y, int width, int height) {
		graphics.setClip(x+this.x, y+this.y, width, height);
	}

	@Override
	public Shape getClip() {
		return graphics.getClip();
	}

	@Override
	public void setClip(Shape clip) {
		graphics.setClip(clip);
	}

	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		graphics.copyArea(x+this.x, y+this.y, width, height, dx, dy);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		graphics.drawLine(x1+this.x, y1+this.y, x2+this.x, y2+this.y);
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		graphics.fillRect(x+this.x, y+this.y, width, height);
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		graphics.clearRect(x+this.x, y+this.y, width, height);
	}

	@Override
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		graphics.drawRoundRect(x+this.x, y+this.y, width, height, arcWidth, arcHeight);
	}

	@Override
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		graphics.fillRoundRect(x+this.x, y+this.y, width, height, arcWidth, arcHeight);
	}

	@Override
	public void drawOval(int x, int y, int width, int height) {
		graphics.drawOval(x+this.x, y+this.y, width, height);
	}

	@Override
	public void fillOval(int x, int y, int width, int height) {
		graphics.fillOval(x+this.x, y+this.y, width, height);
	}

	@Override
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		graphics.drawArc(x+this.x, y+this.y, width, height, startAngle, arcAngle);
	}

	@Override
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		graphics.fillArc(x+this.x, y+this.y, width, height, startAngle, arcAngle);
	}

	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i] = xPoints[i]+this.x;
		}
		for (int i = 0; i < yPoints.length; i++) {
			yPoints[i] = yPoints[i]+this.y;
		}
		graphics.drawPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i] = xPoints[i]+this.x;
		}
		for (int i = 0; i < yPoints.length; i++) {
			yPoints[i] = yPoints[i]+this.y;
		}
		graphics.drawPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i] = xPoints[i]+this.x;
		}
		for (int i = 0; i < yPoints.length; i++) {
			yPoints[i] = yPoints[i]+this.y;
		}
		graphics.fillPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void drawString(@NotNull String str, int x, int y) {
		graphics.drawString(str, x+this.x, y+this.y);
	}

	@Override
	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		graphics.drawString(iterator, x+this.x, y+this.y);
	}

	@Override
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		return graphics.drawImage(img, x+this.x, y+this.y, observer);
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
		return graphics.drawImage(img, x+this.x, y+this.y, width, height, observer);
	}

	@Override
	public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
		return graphics.drawImage(img, x+this.x, y+this.y, bgcolor, observer);
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
		return graphics.drawImage(img, x+this.x, y+this.y, width, height, bgcolor, observer);
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
		return graphics.drawImage(img, dx1+this.x, dy1+this.y, dx2+this.x, dy2+this.y, sx1+this.x, sy1+this.y, sx2+this.x, sy2+this.y, observer);
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
		return graphics.drawImage(img, dx1+this.x, dy1+this.y, dx2+this.x, dy2+this.y, sx1+this.x, sy1+this.y, sx2+this.x, sy2+this.y, bgcolor, observer);
	}

	@Override
	public void dispose() {
		graphics.dispose();
	}
}
