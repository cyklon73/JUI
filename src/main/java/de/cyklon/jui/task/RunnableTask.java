package de.cyklon.jui.task;

import de.cyklon.jui.App;

public abstract class RunnableTask {

	private final long id = System.nanoTime();

	public RunnableTask() {

	}


	public abstract void run(App app);

	public void cancel(App app) {
		app.cancelTask(id);
	}

	public long getTaskId() {
		return id;
	}

}
