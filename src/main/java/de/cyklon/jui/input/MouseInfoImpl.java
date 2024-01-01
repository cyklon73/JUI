package de.cyklon.jui.input;

import java.awt.*;

class MouseInfoImpl implements MouseInfo {
	static final MouseInfo INFO = new MouseInfoImpl();


	private PointerInfo getInfo() {
		return java.awt.MouseInfo.getPointerInfo();
	}

	@Override
	public Point getPosition() {
		return getInfo().getLocation();
	}

	@Override
	public GraphicsDevice getScreen() {
		return getInfo().getDevice();
	}
}
