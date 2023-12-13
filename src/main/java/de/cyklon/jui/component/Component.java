package de.cyklon.jui.component;

import java.awt.*;

public abstract class Component {

	protected int x, y;

	protected Component(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public abstract void render(Graphics g);

}
