package de.cyklon.jui;

import de.cyklon.jui.cursor.Cursor;
import de.cyklon.jui.input.Mouse;
import de.cyklon.jui.input.MouseInfo;
import de.cyklon.jui.task.RunnableTask;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public interface App extends Disposable {

	void setTitle(String title);

	String getTitle();

	void setResizable(boolean resizable);

	boolean isResizable();

	Image getIcon();

	void setIcon(Image icon);

	void setIcons(List<? extends Image> icons);

	List<Image> getIcons();

	void setIcon(ImageIcon icon);

	boolean isVisible();

	void setVisible(boolean visible);

	boolean isUndecorated();

	void setOpacity(float opacity);

	void setBackgroundColor(Color color);

	Color getBackgroundColor();

	void setForegroundColor(Color color);

	Color getForegroundColor();

	void setSize(int width, int height);

	void setSize(Dimension dimension);

	Dimension getSize();

	void setLocation(int x, int y);

	void setLocation(Point point);

	Point getLocation();

	void close();

	long getCurrentFrame();

	void update();

	double getDeltaTime();
	double getFPS();
	int getIntFPS();
	void setMaxFPS(double maxFPS);
	double getMaxFPS();

	GraphicsDevice getScreen();

	UICanvas getCanvas();

	void setCanvas(UICanvas canvas);

	long runTask(Consumer<App> consumer);

	long runTask(RunnableTask task);

	void cancelTask(long id);

	void center();

	void setCursor(Cursor cursor);

	Cursor getCursor();

	Mouse getMouse();

	MouseInfo getMouseInfo();

}
