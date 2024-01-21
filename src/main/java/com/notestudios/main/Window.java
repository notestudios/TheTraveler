package com.notestudios.main;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window extends Canvas {

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private static double mx = 0, my = 0;
	public static boolean mouseExited = true;
	public static int WIDTH = 240;
	public static int HEIGHT = 160;
	public static int SCALE = 4;
	public static JFrame frame = new JFrame();
	private final Cursor gameCursor = toolkit.createCustomCursor(toolkit.createImage(getClass().getResource("/cursors/curBig.png")), new Point(0, 0), "cur");
	private final Image gameIcon = toolkit.createImage(getClass().getResource("/icons/Icon.png"));
	
	public void init() {
		setPreferredSize(new Dimension(getWidth(), getHeight()));
		createFrame(frame, "The Traveler", false, false, this, gameIcon, gameCursor);
	}

	public void addListeners(Game listenerGame) {
		addKeyListener(listenerGame);
		addMouseListener(listenerGame);
		addMouseWheelListener(listenerGame);
		addMouseMotionListener(listenerGame);
	}

	public Canvas getCanvas() {
		return this;
	}

	public final class WindowInfo {
		public JFrame frame;
		public WindowInfo(JFrame frame) {
			this.frame = frame;
		}
		public final WindowInfo setTitle(String title) {
			frame.setTitle(title);
			return this;
		}
		public final WindowInfo setIcon(Image icon) {
			frame.setIconImage(icon);
			return this;
		}
		public final WindowInfo setResizable(boolean isResizable) {
			frame.setResizable(isResizable);
			return this;
		}
		public final WindowInfo setVisible(boolean isVisible) {
			frame.setVisible(isVisible);
			return this;
		}
		public final WindowInfo setCursor(Cursor cur) {
			frame.setCursor(cur);
			return this;
		}
		public final String getTitle() {
			return frame.getTitle();
		}
		public final Image getIcon() {
			return frame.getIconImage();
		}
		public final boolean isResizable() {
			return frame.isResizable();
		}
		public final boolean isVisible() {
			return frame.isVisible();
		}
		public final Cursor getCursor() {
			return frame.getCursor();
		}
	}
	public WindowInfo info(JFrame frame) {
		return new WindowInfo(frame);
	}
	
	public final void createFrame(JFrame frame, String title, boolean fullscreen, boolean resizable,
			Component comp, Image icon, Cursor cur) {
		frame.setTitle(title);
		frame.add(comp);
		frame.setUndecorated(!frame.isUndecorated() && fullscreen);
		frame.setVisible(!frame.isVisible());
		frame.pack();
		frame.setCursor(cur);
		frame.getContentPane().add(comp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(resizable);
		frame.setIconImage(icon);
		frame.setLocationRelativeTo(null);
	}
	
	public final void useFakeMouseCursor(Graphics2D g) {
		File gunCur = new File("/cursors/cur.png");
		File moogusCur = new File("/cursors/amongususcur.png");
		File normalCur = new File("/cursors/normalCur.png");
		if(Game.graphicsQuality == 2) {
			if(!Game.amogusSecret) {
				try {
					if(!Window.mouseExited)
					g.drawImage(ImageIO.read(gunCur), getMouseX(), getMouseY(), 11*3, 11*3, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					if(!Window.mouseExited)
					g.drawImage(ImageIO.read(moogusCur), getMouseX(), getMouseY(), 19, 24, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				if(!Window.mouseExited)
				g.drawImage(ImageIO.read(normalCur), getMouseX(), getMouseY(), 16, 16, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public final static int getMouseX() {
		return (int)mx;
	}
	public final static int getMouseY() {
		return (int)my;
	}
	
	public final void setMouseX(int newMouseX) {
		mx = newMouseX;
	}
	public final void setMouseY(int newMouseY) {
		my = newMouseY;
	}
	
	public int getWidth() {
		return WIDTH*SCALE;
	}
	public int getHeight() {
		return HEIGHT*SCALE;
	}
}
