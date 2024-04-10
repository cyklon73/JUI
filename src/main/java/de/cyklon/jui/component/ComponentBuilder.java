package de.cyklon.jui.component;

import de.cyklon.jui.cursor.Cursor;

public interface ComponentBuilder<T extends UIComponent> {

    ComponentBuilder<T> setHoverCursor(Cursor cursor);

    ComponentBuilder<T> addComponent(UIComponent component);

    T build();

}
