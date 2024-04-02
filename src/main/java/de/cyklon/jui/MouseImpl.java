package de.cyklon.jui;

import de.cyklon.jui.input.Mouse;
import de.cyklon.jui.tuple.Pair;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class MouseImpl extends KeyInputImpl implements Mouse {

	MouseImpl(App app) {
		super(app);
	}

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

	@Override
	void registerListener(Component component) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keyQueue.add(new Pair<>(e.getButton(), 1));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				keyQueue.add(new Pair<>(e.getButton(), -1));
			}
		});
	}
}
