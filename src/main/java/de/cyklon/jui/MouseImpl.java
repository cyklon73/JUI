package de.cyklon.jui;

import de.cyklon.jui.input.Mouse;
import de.cyklon.jui.tuple.Pair;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

class MouseImpl implements Mouse {

	private final App app;
	private final Map<Integer, Long> keyMap = new HashMap<>();
	private final Deque<Pair<Integer, Integer>> keyQueue = new ArrayDeque<>();

	MouseImpl(App app) {
		this.app = app;
	}

	@Override
	public boolean isPressed(int button) {
		return keyMap.containsKey(button) && keyMap.get(button)>=0;
	}

	@Override
	public boolean isClicked(int button) {
		return keyMap.containsKey(button) && keyMap.get(button)==app.getCurrentFrame();
	}

	@Override
	public boolean isReleased(int button) {
		return keyMap.containsKey(button) && (keyMap.get(button)*-1)==app.getCurrentFrame();
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

	void onFrame() {
		while (!keyQueue.isEmpty()) {
			Pair<Integer, Integer> pair = keyQueue.remove();
			keyMap.put(pair.first(), app.getCurrentFrame()*pair.second());
		}
	}

	MouseListener listener() {
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				keyQueue.add(new Pair<>(e.getButton(), 1));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				keyQueue.add(new Pair<>(e.getButton(), -1));
			}
		};
	}
}
