package de.cyklon.jui.event.window;

import de.cyklon.jui.App;
import de.cyklon.jui.event.CancellableAppEvent;

public class WindowCloseEvent extends CancellableAppEvent {
	public WindowCloseEvent(App app) {
		super(app);
	}
}
