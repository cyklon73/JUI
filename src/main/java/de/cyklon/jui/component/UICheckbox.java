package de.cyklon.jui.component;

import de.cyklon.jui.App;
import de.cyklon.jui.cursor.Cursor;

import java.awt.*;

public class UICheckbox extends UIButton {

	private boolean pressed = false;
	private Color boxColor = Color.GRAY, checkColor = Color.GREEN;

	public UICheckbox(int x, int y, int width, int height) {
		super(x, y, width, height);
		addLeftClickListeners(app -> this.pressed = !this.pressed);
	}

	public UICheckbox(int x, int y, int width, int height, Cursor hoverCursor) {
		super(x, y, width, height, hoverCursor);
		addLeftClickListeners(app -> this.pressed = !this.pressed);
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public Color getBoxColor() {
		return boxColor;
	}

	public void setBoxColor(Color boxColor) {
		this.boxColor = boxColor;
	}

	public Color getCheckColor() {
		return checkColor;
	}

	public void setCheckColor(Color checkColor) {
		this.checkColor = checkColor;
	}

	@Override
	protected void renderGraphic(App app, Graphics g) {
		if (pressed) {
			g.setColor(checkColor);
			g.drawLine(x, y, x+width, y+height);
			g.drawLine(x+width, y, x, y+height);
		}
		g.setColor(boxColor);
		g.drawRect(x, y, width, height);
	}
}
