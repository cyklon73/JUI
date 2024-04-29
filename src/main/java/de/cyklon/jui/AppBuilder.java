package de.cyklon.jui;

import de.cyklon.jui.component.UIComponent;
import de.cyklon.jui.component.UILabel;
import de.cyklon.jui.cursor.Cursor;
import de.cyklon.jui.input.Keyboard;
import de.cyklon.jui.input.Mouse;
import de.cyklon.jui.input.MouseInfo;
import de.cyklon.jui.render.BufferedRenderer;
import de.cyklon.jui.task.RunnableTask;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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
		return new AppImpl(title, undecorated, maxFPS, icon, size).app;
	}

	private record ImmutableApp(AppImpl app) implements App {

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
			return app.currentFrame;
		}

		@Override
		public void update() {
			app.update();
		}

		@Override
		public double getDeltaTime() {
			return app.deltaTime;
		}

		@Override
		public double getFPS() {
			return 1e+9 / getDeltaTime();
		}

		@Override
		public int getIntFPS() {
			return (int) Math.round(getFPS());
		}

		@Override
		public void setMaxFPS(double maxFPS) {
			app.setMaxFPS(maxFPS);
		}

		@Override
		public double getMaxFPS() {
			return app.maxFps;
		}

		@Override
		public GraphicsDevice getScreen() {
			return app.getScreen();
		}

		@Override
		public UICanvas getCanvas() {
			return app.canvas;
		}

		@Override
		public void setCanvas(UICanvas canvas) {
			if (app.canvas!=null) {
				app.removeMouseListener(app.canvas);
				app.removeMouseWheelListener(app.canvas);
				app.removeMouseMotionListener(app.canvas);
				app.removeKeyListener(app.canvas);
			}
			app.canvas = canvas;
			app.addMouseListener(canvas);
			app.addMouseWheelListener(canvas);
			app.addMouseMotionListener(canvas);
			app.addKeyListener(canvas);
		}

		@Override
		public long runTask(Consumer<App> consumer) {
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
		public void setCursor(Cursor cursor) {
			app.cursor = cursor;
		}

		@Override
		public Cursor getCursor() {
			return app.cursor;
		}

		@Override
		public Mouse getMouse() {
			return app.mouse;
		}

		@Override
		public MouseInfo getMouseInfo() {
			return app.mouseInfo;
		}

		@Override
		public Keyboard getKeyboard() {
			return app.keyboard;
		}

		@Override
		public JFrame getInternal() {
			return app;
		}

		@Override
		public void dispose() {
			app.dispose();
		}
	}

	static final class AppImpl extends JFrame implements Runnable {


		final App app;
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


		private Cursor cursor;
		private final MouseImpl mouse;
		private final MouseInfo mouseInfo;
		private final KeyboardImpl keyboard;
		private UICanvas canvas;

		public AppImpl(String title, boolean undecorated, double maxFps, Image icon, Dimension size) {
			super();
			this.app = new ImmutableApp(this);

			this.cursor = Cursor.getCursor(0);
			this.mouse = new MouseImpl(app);
			MouseInfo mi = MouseInfo.getInfo();
			this.mouseInfo = new MouseInfo() {
				@Override
				public Point getPosition() {
					Point p = mi.getPosition();
					return new Point(p.x-getX()-8, p.y-getY()-30);
				}

				@Override
				public GraphicsDevice getScreen() {
					return mi.getScreen();
				}
			};

			mouse.registerListener(this);

			this.keyboard = new KeyboardImpl(app);
			keyboard.registerListener(this);

			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			setMaxFPS(maxFps);
			setTitle(title);
			add(panel = new Panel(this::onFrame));
			setUndecorated(undecorated);
			if (icon!=null) setIcon(icon);
			setSize(size);
			center();

			setVisible(true);
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
			this.mouse.onFrame();
			this.keyboard.onFrame();
			if (!markedRemoval.isEmpty()) markedRemoval.forEach(id -> tasks.removeIf(c -> c.getTaskId()==id));
			tasks.forEach(t -> t.run(app));
			if (canvas!=null) canvas.draw(app, g);
			g.dispose();
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

		public void update() {
			repaint();
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

		public GraphicsDevice getScreen() {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] screens = ge.getScreenDevices();

			int x = getX();
			int y = getY();
			for (GraphicsDevice screen : screens) if (screen.getDefaultConfiguration().getBounds().contains(x, y)) return screen;
			return null;
		}

		public long runTask(Consumer<App> consumer) {
			return runTask(new RunnableTask() {
				@Override
				public void run(App app) {
					consumer.accept(app);
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

		private final UIComponent component;
		private final BufferedRenderer renderer = new BufferedRenderer(100, 100);
		private final BufferedImage bi;
		public Panel(Consumer<Graphics> onFrame) {
			this.onFrame = onFrame;
			this.component = new UILabel(20, 20, "test", UILabel.Filter.forString());
			renderer.startFontRenderer().drawString("test123", 20, 20);
			bi = renderer.current;
			renderer.finish();
		}

		@Override
		protected void paintComponent(Graphics g) {
			onFrame.accept(g);
			//component.draw(null, g);
			//renderer.render(0, 0, g);
			//g.drawImage(bi, 0, 0, this);
		}
	}

}
