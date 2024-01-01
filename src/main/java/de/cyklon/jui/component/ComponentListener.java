package de.cyklon.jui.component;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

final class ComponentListener implements MouseMotionListener {

	private final UIComponent component;

	public ComponentListener(UIComponent component) {
		this.component = component;
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (component.isPointOver(e.getPoint())) {
			if (UIComponent.normalCursor==null) {
				//Component.normalCursor =
			}
		}
	}
}
