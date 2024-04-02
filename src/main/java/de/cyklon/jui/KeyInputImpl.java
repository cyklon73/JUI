package de.cyklon.jui;

import de.cyklon.jui.input.KeyInput;
import de.cyklon.jui.tuple.Pair;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

abstract class KeyInputImpl implements KeyInput {
	private final App app;
	private final Map<Integer, Long> keyMap = new HashMap<>();
	protected final Deque<Pair<Integer, Integer>> keyQueue = new ArrayDeque<>();

	KeyInputImpl(App app) {
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

	void onFrame() {
		while (!keyQueue.isEmpty()) {
			Pair<Integer, Integer> pair = keyQueue.remove();
			keyMap.put(pair.first(), app.getCurrentFrame()*pair.second());
		}
	}

	abstract void registerListener(Component component);

}
