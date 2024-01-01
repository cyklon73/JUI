package de.cyklon.jui;

import de.cyklon.jui.component.UIComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class UICanvas implements Drawable, ComponentHolder {

    private final List<UIComponent> components = new ArrayList<>();
    private Color bgColor = new Color(0, 0, 0, 0);

    public UICanvas() {

    }

    public UICanvas(@NotNull UIComponent... components) {
        addComponents(components);
    }

    public UICanvas(@NotNull Color bgColor) {
        this.bgColor = bgColor;
    }

    public UICanvas(@NotNull Color bgColor, @NotNull UIComponent... components) {
        this(bgColor);
        addComponents(components);
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

    @Override
    public final void draw(App app, Graphics g) {
        Dimension d = app.getSize();
        g.setColor(bgColor);
        g.fillRect(0, 0, d.width, d.height);
        components.forEach(c -> c.draw(app, g));
    }

    @NotNull
    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(@NotNull Color bgColor) {
        this.bgColor = bgColor;
    }
}
