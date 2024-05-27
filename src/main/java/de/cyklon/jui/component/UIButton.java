package de.cyklon.jui.component;

import de.cyklon.jui.App;
import de.cyklon.jui.cursor.Cursor;
import de.cyklon.jui.input.Shortcut;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class UIButton extends UIComponent {

    private Color color = new Color(40, 40, 40);
    private Color hoverColor = new Color(20, 20, 20);

    private final List<Shortcut> shortcuts = new ArrayList<>();
    private final List<BiConsumer<App, UIButton>> leftClickListeners = new ArrayList<>();
    private final List<BiConsumer<App, UIButton>> rightClickListeners = new ArrayList<>();

    public UIButton(int x, int y, int width, int height, BiConsumer<App, UIButton> leftClickListener, BiConsumer<App, UIButton> rightClickListener) {
        this(x, y, width, height, leftClickListener);
        this.rightClickListeners.add(rightClickListener);
    }

    public UIButton(int x, int y, int width, int height, BiConsumer<App, UIButton> leftClickListener, BiConsumer<App, UIButton> rightClickListener, Cursor hoverCursor) {
        this(x, y, width, height, leftClickListener, hoverCursor);
        this.rightClickListeners.add(rightClickListener);
    }

    public UIButton(int x, int y, int width, int height, BiConsumer<App, UIButton> leftClickListener) {
        this(x, y, width, height);
        this.leftClickListeners.add(leftClickListener);
    }

    public UIButton(int x, int y, int width, int height, BiConsumer<App, UIButton> leftClickListener, Cursor hoverCursor) {
        this(x, y, width, height, hoverCursor);
        this.leftClickListeners.add(leftClickListener);
    }

    public UIButton(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public UIButton(int x, int y, int width, int height, Cursor hoverCursor) {
        super(x, y, width, height, hoverCursor);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void addShortcut(Shortcut shortcut) {
        shortcuts.add(shortcut);
    }

    @SafeVarargs
    public final UIButton addLeftClickListeners(BiConsumer<App, UIButton>... listeners) {
        this.leftClickListeners.addAll(Arrays.asList(listeners));
        return this;
    }

    @SafeVarargs
    public final UIButton addRightClickListeners(BiConsumer<App, UIButton>... listeners) {
        this.rightClickListeners.addAll(Arrays.asList(listeners));
        return this;
    }

    @Override
    protected void render(App app, Graphics g) {
        boolean shortcutClicked = false;
        for (Shortcut shortcut : shortcuts) if (shortcut.isClicked(app)) shortcutClicked = true;
        if ((app.getMouse().isClicked(MouseEvent.BUTTON1) && isHover(app)) || shortcutClicked) this.leftClickListeners.forEach(l -> l.accept(app, this));
        if (app.getMouse().isClicked(MouseEvent.BUTTON3) && isHover(app)) this.rightClickListeners.forEach(l -> l.accept(app, this));
        renderGraphic(app, g);
    }

    protected void renderGraphic(App app, Graphics g) {
        //Graphics2D g2d = renderer.start();
        g.setColor(isHover(app) ? hoverColor : color);
        g.fillRoundRect(x, y, width, height, 10, 15);
        //renderer.finish();
    }

    public static class Builder extends UIComponent.Builder<UIButton> implements ComponentBuilder<UIButton> {

        private final List<Shortcut> shortcuts = new ArrayList<>();
        private final List<BiConsumer<App, UIButton>> leftClickListeners = new ArrayList<>(), rightClickListeners = new ArrayList<>();
        private Color color = null, hoverColor = null;

        public Builder(int x, int y, int width, int height) {
            super(x, y, width, height);
        }

        public Builder addShortcuts(Shortcut... shortcut) {
            shortcuts.addAll(Arrays.asList(shortcut));
            return this;
        }

        public Builder addLeftClickListeners(BiConsumer<App, UIButton>... listeners) {
            leftClickListeners.addAll(Arrays.asList(listeners));
            return this;
        }

        public Builder addRightClickListeners(BiConsumer<App, UIButton>... listeners) {
            rightClickListeners.addAll(Arrays.asList(listeners));
            return this;
        }

        public Builder setColor(Color color) {
            this.color = color;
            return this;
        }

        public Builder setHoverColor(Color color) {
            this.hoverColor = color;
            return this;
        }

        @Override
        public UIButton build() {
            UIButton btn = new UIButton(x, y, width, height);
            btn.shortcuts.addAll(shortcuts);
            btn.leftClickListeners.addAll(leftClickListeners);
            btn.rightClickListeners.addAll(rightClickListeners);
            if (color!=null) btn.color = color;
            if (hoverColor!=null) btn.hoverColor = hoverColor;
            return btn;
        }
    }
}
