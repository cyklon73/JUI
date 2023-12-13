package de.cyklon.jui;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * On Windows 7, demonstrate the use of the ITaskbarList3 interface to control the taskbar progression visual indicator.
 * @author Olivier
 */
public class TaskbarListDemo extends JFrame implements ActionListener, ChangeListener {

	Taskbar taskbar = Taskbar.getTaskbar();
	int min = 0, max = 100, val = (min + max / 2);
	JSlider slider;


	public TaskbarListDemo() throws ClassNotFoundException {
		super("TaskbarList Demo");


		getContentPane().add("Center", new JLabel("Hello Native Windows 7 World !"));
		Box box = Box.createVerticalBox();

		slider = new JSlider(min, max, val);
		slider.addChangeListener(this);
		box.add(slider);

		ButtonGroup group = new ButtonGroup();
		for (Taskbar.State state : Taskbar.State.values()) {
			JRadioButton cb = new JRadioButton(state.name());
			group.add(cb);
			cb.putClientProperty(Taskbar.State.class, state);
			cb.setSelected(state == Taskbar.State.NORMAL);
			cb.addActionListener(this);
			box.add(cb);
		}
		getContentPane().add("South", box);

	}

	private void updateValue() {
		taskbar.setProgressValue(slider.getValue());
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		updateValue();
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		updateValue();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JRadioButton button = ((JRadioButton)e.getSource());
		if (button.isSelected()) {
			taskbar.setWindowProgressState(this, (Taskbar.State)button.getClientProperty(Taskbar.State.class));
		}
	}

	public static void main(String[] args) throws Exception {
		try {

			TaskbarListDemo f = new TaskbarListDemo();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.pack();
			f.setVisible(true);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
}