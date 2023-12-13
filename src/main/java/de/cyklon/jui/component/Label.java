package de.cyklon.jui.component;

import java.awt.*;

public class Label extends Component {

	private String text;

	public Label(int x, int y, String text) {
		super(x, y);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void render(Graphics g) {
		g.drawString(text, x, y);
	}
}
