package de.cyklon.jui.component;

import de.cyklon.jui.App;
import de.cyklon.jui.ComponentHolder;
import de.cyklon.jui.Drawable;
import de.cyklon.jui.cursor.Cursor;
import de.cyklon.jui.render.BufferedRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class UIComponent implements Drawable, ComponentHolder {

	static final Cursor normalCursor = null;
	private static long cId = 0;

	private final long id;
	protected int x, y, width, height;
	private final Cursor hoverCursor;
	private final BufferedRenderer renderer;

	private final List<UIComponent> components = new ArrayList<>();

	protected UIComponent(int x, int y, int width, int height) {
		this(x, y, width, height, null);
	}

	protected UIComponent(int x, int y, int width, int height, Cursor hoverCursor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.renderer = new BufferedRenderer(width, height);
		this.hoverCursor = hoverCursor;
		synchronized (this) {
			this.id = cId++;
		}
	}

	@Override
	public void addComponents(@NotNull UIComponent... components) {
		this.components.addAll(Arrays.asList(components));
	}

	@Override
	public void clearComponents() {
		this.components.clear();
	}

	@Override
	public @Unmodifiable Collection<UIComponent> getComponents() {
		return Collections.unmodifiableCollection(components);
	}

	@Override
	public @Nullable UIComponent getComponent(long id) {
		return this.components
				.stream()
				.filter(c -> c.getId()==id)
				.findFirst()
				.orElse(null);
	}

	@Override
	public void removeComponent(long id) {
		this.components.removeIf(c -> c.getId()==id);
	}

	public long getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public Cursor getHoverCursor() {
		return hoverCursor;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public final void draw(App app, Graphics g) {
		render(app, g);
		//this.renderer.render(0, 0, g);
		Graphics gr = new RelativGraphic(g, x, y);
		components.forEach(c -> c.draw(app, gr));
	}

	protected abstract void render(App app, Graphics g);

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	protected void setWidth(int width) {
		this.width = width;
		this.renderer.setSize(width, this.height);
	}

	protected void setHeight(int height) {
		this.height = height;
		this.renderer.setSize(this.width, height);
	}

	protected boolean isHover(App app) {
		return isPointOver(app.getMouseInfo().getPosition());
	}

	public boolean isPointOver(Point point) {
		return !(point.getX()<x || point.getY()<y || point.getX()>x+getWidth() || point.getY() > y + getHeight());
	}

}
