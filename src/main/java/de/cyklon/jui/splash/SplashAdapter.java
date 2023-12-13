package de.cyklon.jui.splash;

import java.awt.*;

public abstract class SplashAdapter implements SplashScreen {

	protected SplashAdapter(int width, int height) {

	}

	protected SplashAdapter(Dimension dimension) {

	}

	@Override
	public Dimension getSize() {
		return null;
	}

	@Override
	public final void render(Graphics g) {
		renderSplash(g);
	}

	@Override
	public void close() {

	}

	protected abstract void renderSplash(Graphics g);
}
