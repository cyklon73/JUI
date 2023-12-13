package de.cyklon.jui;

import de.cyklon.jui.app.App;
import de.cyklon.jui.app.AppBuilder;
import de.cyklon.jui.component.Label;
import de.cyklon.jui.task.RunnableTask;

import java.awt.*;

public class Main {
	public static void main(String[] args) {
		App app = new AppBuilder()
				.setMaxFPS(1)
				.setSize(500, 500)
				.build();

		Label label, delta, fps;
		app.add(label = new Label(20, 20, "Hallo welt 0"), delta = new Label(20, 40, "Delta: 0"), fps = new Label(20, 60, "FPS: 0"));
		long start = System.currentTimeMillis();
		app.runTask(new RunnableTask() {
			@Override
			public void run(App app, Graphics graphics) {
				label.setText("Hallo welt " + ((System.currentTimeMillis()-start)/1000));
				if (System.currentTimeMillis()-start>10000*1000) app.close();
			}
		});
		app.runTask(ap -> delta.setText("Delta: " + ap.getDeltaTime()));
		app.runTask(ap -> fps.setText("FPS: " + ap.getIntFPS()));
	}
}
