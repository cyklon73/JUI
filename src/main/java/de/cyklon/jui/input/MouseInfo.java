package de.cyklon.jui.input;

import java.awt.*;

public interface MouseInfo {

	public static MouseInfo getInfo() {
		return MouseInfoImpl.INFO;
	}

	Point getPosition();

	/**
	 * @return the screen, the mouse is on
	 */
	GraphicsDevice getScreen();

}
