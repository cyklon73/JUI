package de.cyklon.jui.input;

import de.cyklon.jui.App;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

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
        return check(app, KeyInput::isPressed);
    }

    public boolean isClicked(App app) {
        return check(app, KeyInput::isClicked);
    }

    public boolean check(App app, BiFunction<KeyInput, Integer, Boolean> check) {
        AtomicBoolean pressed = new AtomicBoolean(true);

        Mouse mouse = app.getMouse();
        Keyboard keyboard = app.getKeyboard();

        keyMap.forEach((k, v) -> {
            if (!switch (v) {
                case MOUSE -> check.apply(mouse, k);
                case KEYBOARD -> check.apply(keyboard, k);
            }) pressed.set(false);
        });
        return pressed.get();
    }


    enum Type {
        MOUSE,
        KEYBOARD
    }

}
