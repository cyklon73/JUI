package de.cyklon.jui.event;

import de.cyklon.jevent.Cancellable;
import de.cyklon.jui.App;

public class CancellableAppEvent extends AppEvent implements Cancellable {

	private boolean cancelled = false;

	public CancellableAppEvent(App app) {
		super(app);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
