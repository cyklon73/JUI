module JUI {
	requires org.jetbrains.annotations;
	requires JEvent;
	requires JResource;
	requires java.desktop;

	exports de.cyklon.jui;
	exports de.cyklon.jui.component;
	exports de.cyklon.jui.cursor;
	exports de.cyklon.jui.event;
	exports de.cyklon.jui.event.window;
	exports de.cyklon.jui.input;
	exports de.cyklon.jui.render;
	exports de.cyklon.jui.splash;
	exports de.cyklon.jui.task;
	exports de.cyklon.jui.tuple;

}