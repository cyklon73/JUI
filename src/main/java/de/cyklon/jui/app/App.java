package de.cyklon.jui.app;

import de.cyklon.jui.Disposable;
import de.cyklon.jui.component.Component;
import de.cyklon.jui.task.RunnableTask;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
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

	Collection<Component> getComponents();

	void add(Component... component);

	void clearComponents();

	long runTask(Consumer<App> consumer);

	long runTask(BiConsumer<App, Graphics> consumer);

	long runTask(RunnableTask task);

	void cancelTask(long id);

	void center();

}
