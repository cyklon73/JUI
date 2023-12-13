package de.cyklon.jui.event;

import de.cyklon.jevent.Event;
import de.cyklon.jui.app.App;

public class AppEvent extends Event {

	private final App app;

	public AppEvent(App app) {
		this.app = app;
	}

	public App getApp() {
		return app;
	}
}
