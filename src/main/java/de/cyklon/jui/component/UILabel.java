package de.cyklon.jui.component;

import de.cyklon.jresource.Resource;
import de.cyklon.jui.App;
import de.cyklon.jui.render.FontRenderer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class UILabel extends UIComponent {

	private String text;
	private int width = -1, height = -1;
	private boolean blank;

	private Color color = Color.BLACK;

	private Alignment alignment;

	public UILabel(int x, int y) {
		this(x, y, "");
	}

	public UILabel(int x, int y, @NotNull Resource resource) {
		this(x, y, new String(resource.getBytes()));
	}
	public UILabel(int x, int y, @NotNull String text) {
		super(x, y, 1, 1);
		this.text = text;
		this.blank = text.isBlank();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.blank = text.isBlank();
		this.width = 1;
	}

	public void setText(@NotNull Resource resource) {
		setText(new String(resource.getBytes()));
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	@Override
	protected void render(App app, Graphics g) {
		if (!blank) {
			//FontRenderer fr = renderer.startFontRenderer();
			FontRenderer fr = new FontRenderer(g);
			if (width==1) width = Math.max(2, fr.stringWidth(text));
			if (height==1) height = Math.max(2, fr.stringHeight(text));
			fr.setColor(color);
			int xOffset = switch (alignment) {
				case LEFT -> 0;
				case CENTER -> width/2;
				case RIGHT -> width;
			};
			fr.drawString(text, x-xOffset, y);
			//renderer.finish();
		}
	}

	public enum Alignment {
		LEFT,
		CENTER,
		RIGHT
	}
}
