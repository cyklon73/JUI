package de.cyklon.jui.component;

import de.cyklon.jresource.Resource;
import de.cyklon.jui.App;
import de.cyklon.jui.render.FontRenderer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class UILabel<R> extends UIComponent {

	private String text;
	private int width = 1, height = 1;
	private boolean blank;
	private boolean editable = false, focused = true;
	private long lastBlink = 0;
	private final Filter<R> filter;

	private Color color = Color.BLACK;

	private Font font = new Font("Arial", Font.PLAIN, 12);

	private FontRenderer.Alignment alignment = FontRenderer.Alignment.LEFT;

	public UILabel(int x, int y, Filter<R> filter) {
		this(x, y, "", filter);
	}

	public UILabel(int x, int y, @NotNull Resource resource, Filter<R> filter) {
		this(x, y, new String(resource.getBytes()), filter);
	}
	public UILabel(int x, int y, @NotNull String text, Filter<R> filter) {
		super(x, y, 1, 1);
		this.text = text;
		this.blank = text.isBlank();
        this.filter = filter;
    }

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public R getValue() {
		return filter.map(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.blank = text.isBlank();
		this.width = 1;
	}

	public void setText(@NotNull Resource resource) {
		setText(new String(resource.getBytes()));
	}

	public FontRenderer.Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(FontRenderer.Alignment alignment) {
		this.alignment = alignment;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return editable;
	}

	public boolean isFocused() {
		return focused;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Font getFont() {
		return font;
	}

	private boolean isPrintableChar(char c ) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		return (!Character.isISOControl(c)) &&
				c != KeyEvent.CHAR_UNDEFINED &&
				block != null &&
				block != Character.UnicodeBlock.SPECIALS;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (!focused || !editable || (!isPrintableChar(e.getKeyChar()) && ((int)e.getKeyChar())!=8)) return;
		String s = getText() + e.getKeyChar();;
		if (((int)e.getKeyChar())==8) setText(text.isEmpty() ? "" : text.substring(0, text.length()-1));
		else if (filter.filter(s, e.getKeyChar())) {
			setText(s);
		}
	}

	@Override
	protected void render(App app, Graphics g) {
		if (isHover(app) && app.getMouse().isClicked(MouseEvent.BUTTON1)) focused = true;
		//if (app.getKeyboard().isClicked(KeyEvent.VK_ESCAPE)) focused = false;
		if (!blank) {
			//FontRenderer fr = renderer.startFontRenderer();
			FontRenderer fr = new FontRenderer(g);
			fr.setFont(font);
			if (width==1) width = Math.max(10, fr.stringWidth(text));
			if (height==1) height = Math.max(5, fr.stringHeight(text));
			fr.setColor(color);
			fr.setAlignment(alignment);
			fr.drawString(text, x, y);
			//renderer.finish();
		}
		if (focused && editable) {
			if (lastBlink+120 < app.getCurrentFrame()) {
				lastBlink = app.getCurrentFrame();
			} else if (lastBlink+60 < app.getCurrentFrame()) {
				g.setColor(color);
				g.drawLine(getX()+width+2, getY(), getX()+width+2, getY()-height);
			}
		}
	}

	public interface Filter<R> {

		static Filter<String> forString() {
			return new Filter<>() {
                @Override
                public boolean filter(String s, char c) {
                    return true;
                }

                @Override
                public String map(String s) {
                    return s;
                }
            };
		}

		static Filter<Integer> forInteger() {
			return new Filter<>() {
				@Override
				public boolean filter(String s, char c) {
					if (Character.isAlphabetic(c) || Character.isWhitespace(c)) return false;
					try {
						Integer.parseInt(s);
						return true;
					} catch (NumberFormatException e) {
						return false;
					}
				}

				@Override
				public Integer map(String s) {
					return s.isBlank() ? 0 : Integer.parseInt(s);
				}
			};
		}

		static Filter<Long> forLong() {
			return new Filter<>() {
				@Override
				public boolean filter(String s, char c) {
					if (Character.isAlphabetic(c) || Character.isWhitespace(c)) return false;
					try {
						Long.parseLong(s);
						return true;
					} catch (NumberFormatException e) {
						return false;
					}
				}

				@Override
				public Long map(String s) {
					return s.isBlank() ? 0 : Long.parseLong(s);
				}
			};
		}


		static Filter<Double> forDouble() {
			return new Filter<>() {
				@Override
				public boolean filter(String s, char c) {
					if (Character.isAlphabetic(c) || Character.isWhitespace(c)) return false;
					try {
						Double.parseDouble(s);
						return true;
					} catch (NumberFormatException e) {
						return false;
					}
				}

				@Override
				public Double map(String s) {
					return s.isBlank() ? 0 : Double.parseDouble(s);
				}
			};
		}

		boolean filter(String s, char c);

		R map(String s);

	}
}
