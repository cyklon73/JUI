package de.cyklon.jui.render;

import java.awt.*;

public final class FontRenderer {

	private final Graphics graphics;
	private Color color, shadowColor, strikethroughColor, underlineColor;
	private Alignment alignment;
	private Font font;
	private int width;

	public FontRenderer(Graphics graphics) {
		this.graphics = graphics;
		this.color = null;
		this.shadowColor = null;
		this.strikethroughColor = null;
		this.underlineColor = null;
		this.alignment = Alignment.LEFT;
		this.font = null;
		this.width = 0;
	}

	public FontRenderer setColor(Color color) {
		this.color = color;
		return this;
	}

	public FontRenderer setShadowColor(Color shadowColor) {
		this.shadowColor = shadowColor;
		return this;
	}

	public FontRenderer setStrikethroughColor(Color strikethroughColor) {
		this.strikethroughColor = strikethroughColor;
		return this;
	}

	public FontRenderer setUnderlineColor(Color underlineColor) {
		this.underlineColor = underlineColor;
		return this;
	}

	public FontRenderer setAlignment(Alignment alignment) {
		this.alignment = alignment;
		return this;
	}

	public FontRenderer setFont(Font font) {
		this.font = font;
		return this;
	}

	public FontRenderer setWidth(int width) {
		this.width = width;
		return this;
	}

	public void drawString(String text, int x, int y) {
		if (font!=null) graphics.setFont(font);
		int stringWidth = stringWidth(text);
		x += switch (alignment) {
			case LEFT -> 0;
			case CENTERED -> width/2 - stringWidth/2;
			case RIGHT -> width-stringWidth;
		};
		int fontSize = graphics.getFont().getSize();
		int factor = fontSize/16;
		if (shadowColor!=null) {
			graphics.setColor(shadowColor);
			graphics.drawString(text, x+factor, y+factor);
		}
		if (color!=null) graphics.setColor(color);
		graphics.drawString(text, x, y);

		int start = x;
		int end = x+stringWidth;

		if (underlineColor!=null) {
			graphics.setColor(underlineColor);
			graphics.fillRect(start, y+4, end-start, fontSize/10);
		}
		if (strikethroughColor!=null) {
			graphics.setColor(strikethroughColor);
			graphics.fillRect(start, (int) (y-fontSize/2.4f), end-start, fontSize/10);
		}

	}

	public int stringWidth(String text) {
		return graphics.getFontMetrics(font==null ? graphics.getFont() : font).stringWidth(text);
	}

	public static enum Alignment {
		LEFT,
		CENTERED,
		RIGHT
	}

}
