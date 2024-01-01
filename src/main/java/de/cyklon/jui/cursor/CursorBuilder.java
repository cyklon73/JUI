package de.cyklon.jui.cursor;

import java.awt.*;
import java.util.UUID;

final class CursorBuilder {

    private int type, typeLeftClick, typeRightClick;
    private Image icon, iconLefClick, iconRightClick;

    CursorBuilder() {

    }

    public CursorBuilder set(int type) {
        this.type = type;
        this.icon = null;
        return this;
    }

    public CursorBuilder set(Image icon) {
        this.icon = icon;
        this.type = -1;
        return this;
    }

    public CursorBuilder setLeftClick(int type) {
        this.typeLeftClick = type;
        this.iconLefClick = null;
        return this;
    }

    public CursorBuilder setLeftClick(Image icon) {
        this.iconLefClick = icon;
        this.typeLeftClick = -1;
        return this;
    }

    public CursorBuilder setRightClick(int type) {
        this.typeRightClick = type;
        this.iconRightClick = null;
        return this;
    }

    public CursorBuilder setRightClick(Image icon) {
        this.iconRightClick = icon;
        this.typeRightClick = -1;
        return this;
    }

    private java.awt.Cursor cursor(Image icon) {
        return Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(), UUID.randomUUID().toString());
    }

    public Cursor build() {
        java.awt.Cursor icon = type!=-1 ? new java.awt.Cursor(type) : cursor(this.icon);
        java.awt.Cursor lIcon = typeLeftClick!=-1 ? new java.awt.Cursor(typeLeftClick) : iconLefClick!=null ? cursor(iconLefClick) : icon;
        java.awt.Cursor rIcon = typeRightClick!=-1 ? new java.awt.Cursor(typeRightClick) : iconRightClick!=null ? cursor(iconRightClick) : icon;
        return new Cursor() {
            @Override
            public java.awt.Cursor getIcon() {
                return icon;
            }

            @Override
            public java.awt.Cursor getLeftClickIcon() {
                return lIcon;
            }

            @Override
            public java.awt.Cursor getRightClickIcon() {
                return rIcon;
            }
        };
    }
}
