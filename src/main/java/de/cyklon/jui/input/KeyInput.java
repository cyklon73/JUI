package de.cyklon.jui.input;

public interface KeyInput {
	boolean isPressed(int button);

	boolean isClicked(int button);

	boolean isReleased(int button);
}
