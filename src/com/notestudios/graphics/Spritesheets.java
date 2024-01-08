package com.notestudios.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.notestudios.menus.MainMenu;

public class Spritesheets {
	
	public class SpriteSheet {
		
		private BufferedImage sheet;
		
		public SpriteSheet(BufferedImage sheet) {
			this.sheet = sheet;
		}
		public BufferedImage getSprite(int x, int y, int width, int height) {
			return sheet.getSubimage(x, y, width, height);
		}
		public int getMaxWidth() {
			return sheet.getWidth();
		}
		public int getMaxHeight() {
			return sheet.getHeight();
		}
	}
	
	public BufferedImage spritesheet;
	public static SpriteSheet entities;
	public static SpriteSheet tiles;
	public static SpriteSheet ui;

	public Spritesheets() {
		reloadImages();
	}
	
	public void reloadImages() {
		try {
			entities = new SpriteSheet(ImageIO.read(getClass().getResourceAsStream("/spritesheets/Player Spritesheet.png")));
			tiles = new SpriteSheet(ImageIO.read(getClass().getResourceAsStream("/spritesheets/Tiles Spritesheet.png")));
			ui = new SpriteSheet(ImageIO.read(getClass().getResourceAsStream("/spritesheets/UI Spritesheet.png")));
			MainMenu.menuCreditsIcon = ui.getSprite(96, 96, 16, 16);
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, JOptionPane.ERROR, "An error ocurred while loading sprite sheets.\n"+e, 0);
		}
	}
}