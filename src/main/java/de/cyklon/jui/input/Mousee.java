package de.cyklon.jui.input;

import de.cyklon.jui.App;
import de.cyklon.jui.tuple.Pair;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Mousee {

	private final App app;
	private final Map<Integer, Long> keyMap = new HashMap<>();
	private final Deque<Pair<Integer, Integer>> keyQueue = new ArrayDeque<>();

	public Mousee(App app) {
		this.app = app;
	}

	public boolean isPressed(int button) {
		return keyMap.containsKey(button) && keyMap.get(button)>=0;
	}

	public boolean isClicked(int button) {
		return keyMap.containsKey(button) && keyMap.get(button)==app.getCurrentFrame();
	}

	public boolean isReleased(int button) {
		return keyMap.containsKey(button) && (keyMap.get(button)*-1)==app.getCurrentFrame();
	}

	private void onFrame() {
		while (!keyQueue.isEmpty()) {
			Pair<Integer, Integer> pair = keyQueue.remove();
			keyMap.put(pair.first(), app.getCurrentFrame()*pair.second());
		}
	}
}
