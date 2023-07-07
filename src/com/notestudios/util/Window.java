package com.notestudios.util;

import java.awt.Image;
import javax.swing.JFrame;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	public static JFrame f;
	public static class SetInfo {
		
		public static void setTitle(JFrame frame, String title) {
			frame.setTitle(title);
		}
		public static void setIcon(JFrame frame, Image icon) {
			frame.setIconImage(icon);
		}
		public static void setResizable(JFrame frame, boolean isResizable) {
			frame.setResizable(isResizable);
		}
		public static void setVisible(JFrame frame, boolean isVisible) {
			frame.setVisible(isVisible);
		}
	}
	
}
