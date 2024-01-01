package de.cyklon.jui.input;

public interface Mouse extends MouseInfo {

	boolean isPressed(int button);

	boolean isClicked(int button);

	boolean isReleased(int button);
}
