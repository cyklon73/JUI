package de.cyklon.jui.app;

import de.cyklon.jui.component.Component;
import de.cyklon.jui.task.RunnableTask;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AppBuilder {

	public static final double UNLIMITED = -1;
	public static final double VSYNC = -2;
	public static final double MANUAL_UPDATE = -3;


	private String title = "My JUI";
	private boolean undecorated = false;
	private double maxFPS = VSYNC;
	private Image icon = null;
	private Dimension size = new Dimension(500, 500);

	public AppBuilder() {

	}

	public AppBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public AppBuilder setUndecorated(boolean undecorated) {
		this.undecorated = undecorated;
		return this;
	}

	public AppBuilder setMaxFPS(double maxFPS) {
		this.maxFPS = maxFPS;
		return this;
	}

	public AppBuilder setIcon(Image icon) {
		this.icon = icon;
		return this;
	}

	public AppBuilder setSize(int width, int height) {
		this.size = new Dimension(width, height);
		return this;
	}

	public AppBuilder setSize(Dimension size) {
		this.size = size;
		return this;
	}

	public App build() {
		return new AppImpl(title, undecorated, maxFPS, icon, size).getApp();
	}

	private static final class ImmutableApp implements App {

		private final AppImpl app;

		public ImmutableApp(AppImpl app) {
			this.app = app;
		}

		@Override
		public void setTitle(String title) {
			app.setTitle(title);
		}

		@Override
		public String getTitle() {
			return app.getTitle();
		}

		@Override
		public void setResizable(boolean resizable) {
			app.setResizable(resizable);
		}

		@Override
		public boolean isResizable() {
			return app.isResizable();
		}

		@Override
		public Image getIcon() {
			return app.getIcon();
		}

		@Override
		public void setIcon(Image icon) {
			app.setIcon(icon);
		}

		@Override
		public void setIcons(List<? extends Image> icons) {
			app.setIcons(icons);
		}

		@Override
		public List<Image> getIcons() {
			return app.getIcons();
		}

		@Override
		public void setIcon(ImageIcon icon) {
			app.setIcon(icon);
		}

		@Override
		public boolean isVisible() {
			return app.isVisible();
		}

		@Override
		public void setVisible(boolean visible) {
			app.setVisible(visible);
		}

		@Override
		public boolean isUndecorated() {
			return app.isUndecorated();
		}

		@Override
		public void setOpacity(float opacity) {
			app.setOpacity(opacity);
		}

		@Override
		public void setBackgroundColor(Color color) {
			app.setBackgroundColor(color);
		}

		@Override
		public Color getBackgroundColor() {
			return app.getBackgroundColor();
		}

		@Override
		public void setForegroundColor(Color color) {
			app.setForegroundColor(color);
		}

		@Override
		public Color getForegroundColor() {
			return app.getForegroundColor();
		}

		@Override
		public void setSize(int width, int height) {
			app.setSize(width, height);
		}

		@Override
		public void setSize(Dimension dimension) {
			app.setSize(dimension);
		}

		@Override
		public Dimension getSize() {
			return app.getSize();
		}

		@Override
		public void setLocation(int x, int y) {
			app.setLocation(x, y);
		}

		@Override
		public void setLocation(Point point) {
			app.setLocation(point);
		}

		@Override
		public Point getLocation() {
			return app.getLocation();
		}

		@Override
		public void close() {
			app.close();
		}

		@Override
		public long getCurrentFrame() {
			return app.getCurrentFrame();
		}

		@Override
		public void update() {
			app.update();
		}

		@Override
		public double getDeltaTime() {
			return app.getDeltaTime();
		}

		@Override
		public double getFPS() {
			return app.getFPS();
		}

		@Override
		public int getIntFPS() {
			return app.getIntFPS();
		}

		@Override
		public void setMaxFPS(double maxFPS) {
			app.setMaxFPS(maxFPS);
		}

		@Override
		public double getMaxFPS() {
			return app.getMaxFPS();
		}

		@Override
		public GraphicsDevice getScreen() {
			return app.getScreen();
		}

		@Override
		public Collection<Component> getComponents() {
			return app.getComponentCollection();
		}

		@Override
		public void add(Component... component) {
			app.add(component);
		}

		@Override
		public void clearComponents() {
			app.clearComponents();
		}

		@Override
		public long runTask(Consumer<App> consumer) {
			return app.runTask(consumer);
		}

		@Override
		public long runTask(BiConsumer<App, Graphics> consumer) {
			return app.runTask(consumer);
		}

		@Override
		public long runTask(RunnableTask task) {
			return app.runTask(task);
		}

		@Override
		public void cancelTask(long id) {
			app.cancelTask(id);
		}

		@Override
		public void center() {
			app.center();
		}

		@Override
		public void dispose() {
			app.dispose();
		}
	}

	static final class AppImpl extends JFrame implements Runnable {


		private final App app;
		private final List<Component> components = new ArrayList<>();
		private final List<RunnableTask> tasks = new ArrayList<>();
		private final Queue<Long> markedRemoval = new ArrayDeque<>();
		private long currentFrame = 0;
		private final Panel panel;
		private Thread loopThread;
		private double maxFps;
		private double updateInterval;
		private double deltaTime;
		private boolean infinite;
		private boolean manual_update;
		private long lastUpdate = System.nanoTime();

		public AppImpl(String title, boolean undecorated, double maxFps, Image icon, Dimension size) {
			super();
			this.app = new ImmutableApp(this);
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

			setMaxFPS(maxFps);
			setTitle(title);
			add(panel = new Panel(this::onFrame));
			setUndecorated(undecorated);
			if (icon!=null) setIcon(icon);
			setSize(size);
			center();


			setVisible(true);
		}

		public App getApp() {
			return app;
		}

		private void checkThread() {
			if (manual_update) {
				loopThread = null;
			}
			else {
				if (loopThread==null) {
					loopThread = new Thread(this);
					loopThread.start();
				}
			}
		}

		private void onFrame(Graphics g) {
			currentFrame++;
			long current = System.nanoTime();
			deltaTime = current - lastUpdate;
			lastUpdate = current;
			if (!markedRemoval.isEmpty()) markedRemoval.forEach(id -> tasks.removeIf(c -> c.getTaskId()==id));
			tasks.forEach(t -> t.run(app, g));
			components.forEach(c -> c.render(g));;
			g.dispose();
			//System.gc();
		}

		@Override
		public void run() {
			while (!manual_update) {
				long current = System.nanoTime();
				long elapsed = current - lastUpdate;
				if (infinite || elapsed >= updateInterval) update();
			}
		}

		public Image getIcon() {
			return getIconImage();
		}

		public void setIcon(Image icon) {
			setIconImage(icon);
		}

		public void setIcons(List<? extends Image> icons) {
			setIconImages(icons);
		}

		public List<Image> getIcons() {
			return getIconImages();
		}

		public void setIcon(ImageIcon icon) {
			setIcon(icon.getImage());
		}

		public void setBackgroundColor(Color color) {
			setBackground(color);
		}

		public Color getBackgroundColor() {
			return getBackground();
		}

		public void setForegroundColor(Color color) {
			setForeground(color);
		}

		public Color getForegroundColor() {
			return getForeground();
		}

		public void close() {
			dispose();
			System.exit(0);
		}

		public long getCurrentFrame() {
			return currentFrame;
		}

		public void update() {
			repaint();
		}

		public double getDeltaTime() {
			return deltaTime;
		}

		public double getFPS() {
			return 1e+9/getDeltaTime();
		}

		public int getIntFPS() {
			return (int) Math.round(getFPS());
		}

		public void setMaxFPS(double maxFPS) {
			this.maxFps = maxFPS;
			infinite = maxFPS== UNLIMITED;
			manual_update = maxFPS==MANUAL_UPDATE;
			if (!manual_update) {
				GraphicsDevice screen = getScreen();
				double updateRate = maxFPS == VSYNC ? (screen == null ? maxFPS : screen.getDisplayMode().getRefreshRate()) : maxFPS;
				updateInterval = 1e+9 / updateRate;
			}
			checkThread();
		}

		public double getMaxFPS() {
			return maxFps;
		}

		public GraphicsDevice getScreen() {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] screens = ge.getScreenDevices();

			int x = getX();
			int y = getY();
			for (GraphicsDevice screen : screens) if (screen.getDefaultConfiguration().getBounds().contains(x, y)) return screen;
			return null;
		}

		public Collection<Component> getComponentCollection() {
			return Collections.unmodifiableCollection(components);
		}

		public void add(Component... component) {
			components.addAll(Arrays.asList(component));
		}

		public void clearComponents() {
			components.clear();
		}

		public long runTask(Consumer<App> consumer) {
			return runTask((app, graphics) -> consumer.accept(app));
		}

		public long runTask(BiConsumer<App, Graphics> consumer) {
			return runTask(new RunnableTask() {
				@Override
				public void run(App app, Graphics graphics) {
					consumer.accept(app, graphics);
				}
			});
		}

		public long runTask(RunnableTask task) {
			tasks.add(task);
			return task.getTaskId();
		}

		public void cancelTask(long id) {
			markedRemoval.add(id);
		}

		public void center() {
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(d.width/2-getWidth()/2, d.height/2-getHeight()/2);
		}

		@Override
		public void dispose() {
			manual_update = true;
			super.dispose();
		}
	}

	private static final class Panel extends JPanel {

		private final Consumer<Graphics> onFrame;

		public Panel(Consumer<Graphics> onFrame) {
			this.onFrame = onFrame;
		}

		@Override
		protected void paintComponent(Graphics g) {
			onFrame.accept(g);
		}
	}

}
