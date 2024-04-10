package de.cyklon.jui.input;

import de.cyklon.jui.App;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Shortcut {

    public static Shortcut empty() {
        return new Shortcut();
    }

    public static Shortcut ofKeyboard(int... keys) {
        Shortcut sc = new Shortcut();
        for (int key : keys) sc.addKey(key);
        return sc;
    }

    public static Shortcut ofMouse(int... buttons) {
        Shortcut sc = new Shortcut();
        for (int button : buttons) sc.addButton(button);
        return sc;
    }

    private final Map<Integer, Type> keyMap = new HashMap<>();

    Shortcut() {}

    //add keyboard key
    public void addKey(int key) {
        keyMap.put(key, Type.KEYBOARD);
    }

    //add mouse button
    public void addButton(int button)  {
        keyMap.put(button, Type.MOUSE);
    }

    public boolean isPressed(App app) {
        AtomicBoolean pressed = new AtomicBoolean(true);

        Mouse mouse = app.getMouse();
        Keyboard keyboard = app.getKeyboard();

        keyMap.forEach((k, v) -> {
            if (!switch (v) {
                case MOUSE -> mouse.isPressed(k);
                case KEYBOARD -> keyboard.isPressed(k);
            }) pressed.set(false);
        });
        return pressed.get();
    }

    enum Type {
        MOUSE,
        KEYBOARD
    }

}
