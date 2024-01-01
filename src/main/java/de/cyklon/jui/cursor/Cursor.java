package de.cyklon.jui.cursor;

import java.awt.*;

public interface Cursor {
    
    java.awt.Cursor getIcon();

    java.awt.Cursor getLeftClickIcon();

    java.awt.Cursor getRightClickIcon();

    private static CursorBuilder builder() {
        return new CursorBuilder();
    }

    public static Cursor getCursor(Image icon, Image iconLeftClick, Image iconRightClick) {
        return builder().set(icon).setLeftClick(iconLeftClick).setRightClick(iconRightClick).build();
    }

    public static Cursor getCursor(Image icon, Image iconLeftClick) {
        return builder().set(icon).setLeftClick(iconLeftClick).build();
    }

    public static Cursor getCursor(Image icon) {
        return builder().set(icon).build();
    }

    public static Cursor getCursor(int type) {
        return builder().set(type).build();
    }

    public static Cursor getCursor(int type, int typeLeftClick) {
        return builder().set(type).setLeftClick(typeLeftClick).build();
    }


    public static Cursor getCursor(Image icon, int typeLeftClick) {
        return builder().set(icon).setLeftClick(typeLeftClick).build();
    }

    public static Cursor getCursor(int type, Image iconLeftClick) {
        return builder().set(type).setLeftClick(iconLeftClick).build();
    }

    public static Cursor getCursor(int type, int typeLeftClick, int typeRightClick) {
        return builder().set(type).setLeftClick(typeLeftClick).setRightClick(typeRightClick).build();
    }

    public static Cursor getCursor(Image icon, int typeLeftClick, int typeRightClick) {
        return builder().set(icon).setLeftClick(typeLeftClick).setRightClick(typeRightClick).build();
    }

    public static Cursor getCursor(int type, Image iconLeftClick, int typeRightClick) {
        return builder().set(type).setLeftClick(iconLeftClick).setRightClick(typeRightClick).build();
    }
    public static Cursor getCursor(int type, int typeLeftClick, Image iconRightClick) {
        return builder().set(type).setLeftClick(typeLeftClick).setRightClick(iconRightClick).build();
    }

    public static Cursor getCursor(Image icon, Image iconLeftClick, int typeRightClick) {
        return builder().set(icon).setLeftClick(iconLeftClick).setRightClick(typeRightClick).build();
    }

    public static Cursor getCursor(Image icon, int typeLeftClick, Image iconRightClick) {
        return builder().set(icon).setLeftClick(typeLeftClick).setRightClick(iconRightClick).build();
    }

    public static Cursor getCursor(int type, Image iconLeftClick, Image iconRightClick) {
        return builder().set(type).setLeftClick(iconLeftClick).setRightClick(iconRightClick).build();
    }
}
