package de.cyklon.jui.component;

import de.cyklon.jui.UICanvas;

import java.util.function.Supplier;

public class UICanvasButton extends UIButton {

	public UICanvasButton(int x, int y, int width, int height, UICanvas canvas) {
		this(x, y, width, height, () -> canvas);
	}
	public UICanvasButton(int x, int y, int width, int height, Supplier<UICanvas> canvas) {
		super(x, y, width, height, (app, btn) -> app.setCanvas(canvas.get()));
	}

}
