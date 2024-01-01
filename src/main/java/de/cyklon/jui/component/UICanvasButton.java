package de.cyklon.jui.component;

import de.cyklon.jui.UICanvas;

public class UICanvasButton extends UIButton {
	public UICanvasButton(int x, int y, int width, int height, UICanvas canvas) {
		super(x, y, width, height, app -> app.setCanvas(canvas));
	}
}
