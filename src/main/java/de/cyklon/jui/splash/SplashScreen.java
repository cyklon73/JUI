package de.cyklon.jui.splash;

import java.awt.*;

public interface SplashScreen {

	Dimension getSize();

	void render(Graphics g);

	void close();

}
