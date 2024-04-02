package de.cyklon.jui;

import de.cyklon.jui.input.Keyboard;
import de.cyklon.jui.tuple.Pair;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class KeyboardImpl extends KeyInputImpl implements Keyboard {

	KeyboardImpl(App app) {
		super(app);
	}

	@Override
	void registerListener(Component component) {
		component.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				keyQueue.add(new Pair<>(e.getKeyCode(), 1));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keyQueue.add(new Pair<>(e.getKeyCode(), -1));
			}
		});
	}
}
