package de.cyklon.jui.component;

import de.cyklon.jui.App;
import de.cyklon.jui.cursor.Cursor;

import java.awt.*;

public class UIProgressbar extends UIComponent {

	private int value, minValue, maxValue;
	private Color color = Color.GREEN, bgColor = Color.GRAY;


	public UIProgressbar(int x, int y, int width, int height, int minValue, int maxValue) {
		this(x, y, width, height, minValue, minValue, maxValue);
	}

	public UIProgressbar(int x, int y, int width, int height, int minValue, int maxValue, Cursor hoverCursor) {
		this(x, y, width, height, minValue, minValue, maxValue, hoverCursor);
	}

	public UIProgressbar(int x, int y, int width, int height, int currentValue, int minValue, int maxValue) {
		super(x, y, width, height);
		this.value = currentValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		checkMin(minValue);
		checkMax(maxValue);
		checkVal(currentValue);
	}

	public UIProgressbar(int x, int y, int width, int height, int currentValue, int minValue, int maxValue, Cursor hoverCursor) {
		super(x, y, width, height, hoverCursor);
		this.value = currentValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		checkMin(minValue);
		checkMax(maxValue);
		checkVal(currentValue);
	}

	private void checkMin(int min) {
		if (min>maxValue) throw new IllegalArgumentException("minValue cannot be greater than maxValue");
	}

	private void checkMax(int max) {
		if (max<minValue) throw new IllegalArgumentException("maxValue cannot be smaller than minValue");
	}

	private void checkVal(int val) {
		if (val<minValue) throw new IllegalArgumentException("Value cannot be less than minValue");
		if (val>maxValue) throw new IllegalArgumentException("Value cannot be greater than maxValue");
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		checkVal(value);
		this.value = value;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		checkMin(minValue);
		this.minValue = minValue;
		if (minValue>value) value = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		checkMax(maxValue);
		this.maxValue = maxValue;
		if (maxValue<value) value = maxValue;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	@Override
	protected void render(App app, Graphics g) {
		g.setColor(bgColor);
		g.fillRoundRect(x, y, width, height, 12, 12);
		g.setColor(color);
		int maxWidth = width-2;
		float percentage = (float) value/maxValue;
		g.fillRoundRect(x+1, y+1, Math.round(maxWidth*percentage), height-2, 12, 12);
	}
}
