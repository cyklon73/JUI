package de.cyklon.jui.component;

import de.cyklon.jui.App;
import de.cyklon.jui.cursor.Cursor;
import de.cyklon.jui.render.BufferedRenderer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class UIButton extends UIComponent {

    private final static Color color = new Color(40, 40, 40);
    private final static Color hoverColor = new Color(20, 20, 20);


    private final List<Consumer<App>> leftClickListeners = new ArrayList<>();
    private final List<Consumer<App>> rightClickListeners = new ArrayList<>();

    public UIButton(int x, int y, int width, int height, Consumer<App> leftClickListener, Consumer<App> rightClickListener) {
        this(x, y, width, height, leftClickListener);
        this.rightClickListeners.add(rightClickListener);
    }

    public UIButton(int x, int y, int width, int height, Consumer<App> leftClickListener, Consumer<App> rightClickListener, Cursor hoverCursor) {
        this(x, y, width, height, leftClickListener, hoverCursor);
        this.rightClickListeners.add(rightClickListener);
    }

    public UIButton(int x, int y, int width, int height, Consumer<App> leftClickListener) {
        this(x, y, width, height);
        this.leftClickListeners.add(leftClickListener);
    }

    public UIButton(int x, int y, int width, int height, Consumer<App> leftClickListener, Cursor hoverCursor) {
        this(x, y, width, height, hoverCursor);
        this.leftClickListeners.add(leftClickListener);
    }

    public UIButton(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public UIButton(int x, int y, int width, int height, Cursor hoverCursor) {
        super(x, y, width, height, hoverCursor);
    }

    @SafeVarargs
    public final void addLeftClickListeners(Consumer<App>... listeners) {
        this.leftClickListeners.addAll(Arrays.asList(listeners));
    }

    @SafeVarargs
    public final void addRightClickListeners(Consumer<App>... listeners) {
        this.rightClickListeners.addAll(Arrays.asList(listeners));
    }

    @Override
    protected void render(App app, Graphics g) {
        if (app.getMouse().isClicked(MouseEvent.BUTTON1) && isHover(app)) this.leftClickListeners.forEach(l -> l.accept(app));
        if (app.getMouse().isClicked(MouseEvent.BUTTON3) && isHover(app)) this.rightClickListeners.forEach(l -> l.accept(app));
        renderGraphic(app, g);
    }

    protected void renderGraphic(App app, Graphics g) {
        //Graphics2D g2d = renderer.start();
        g.setColor(isHover(app) ? hoverColor : color);
        g.fillRoundRect(x, y, width, height, 10, 15);
        //renderer.finish();
    }
}
