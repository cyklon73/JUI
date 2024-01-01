package de.cyklon.jui;

import de.cyklon.jui.event.window.WindowHideEvent;
import de.cyklon.jui.event.window.WindowMoveEvent;
import de.cyklon.jui.event.window.WindowResizeEvent;
import de.cyklon.jui.event.window.WindowShowEvent;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class EventDispatcher {

	static void addListeners(JFrame frame) {
		frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (e.getComponent() instanceof AppBuilder.AppImpl impl) {
					new WindowResizeEvent(impl.app).callEvent();
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				if (e.getComponent() instanceof AppBuilder.AppImpl impl) {
					new WindowMoveEvent(impl.app).callEvent();
				}
			}

			@Override
			public void componentShown(ComponentEvent e) {
				if (e.getComponent() instanceof AppBuilder.AppImpl impl) {
					new WindowShowEvent(impl.app).callEvent();
				}
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				if (e.getComponent() instanceof AppBuilder.AppImpl impl) {
					new WindowHideEvent(impl.app).callEvent();
				}
			}
		});
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {

			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		});
		frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		frame.addContainerListener(new ContainerListener() {
			@Override
			public void componentAdded(ContainerEvent e) {

			}

			@Override
			public void componentRemoved(ContainerEvent e) {

			}
		});
		frame.addHierarchyListener(new HierarchyListener() {
			@Override
			public void hierarchyChanged(HierarchyEvent e) {

			}
		});
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		frame.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {

			}
		});
		frame.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {

			}
		});
		frame.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

			}
		});
		frame.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}
		});
		frame.addInputMethodListener(new InputMethodListener() {
			@Override
			public void inputMethodTextChanged(InputMethodEvent event) {

			}

			@Override
			public void caretPositionChanged(InputMethodEvent event) {

			}
		});
		frame.addHierarchyBoundsListener(new HierarchyBoundsListener() {
			@Override
			public void ancestorMoved(HierarchyEvent e) {

			}

			@Override
			public void ancestorResized(HierarchyEvent e) {

			}
		});
		frame.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {

			}

			@Override
			public void windowLostFocus(WindowEvent e) {

			}
		});
		frame.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {

			}
		});
	}

}
