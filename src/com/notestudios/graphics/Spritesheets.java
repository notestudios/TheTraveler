package com.notestudios.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.notestudios.menus.MainMenu;

public class Spritesheets {
	public BufferedImage spritesheet;
	public static BufferedImage spritesheetPlayer;
	public static BufferedImage spritesheetTiles;
	public static BufferedImage spritesheetUI;

	public Spritesheets() {
		reloadImages();
	}
	
	public void reloadImages() {
		try {
			spritesheetPlayer = ImageIO.read(getClass().getResourceAsStream("/spritesheets/Player Spritesheet.png"));
			spritesheetTiles = ImageIO.read(getClass().getResourceAsStream("/spritesheets/Tiles Spritesheet.png"));
			spritesheetUI = ImageIO.read(getClass().getResourceAsStream("/spritesheets/UI Spritesheet.png"));
			MainMenu.menuCreditsIcon = Spritesheets.spritesheetUI.getSubimage(96, 96, 16, 16);
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, JOptionPane.ERROR, "An error ocurred while loading sprite sheets.\n"+e, 0);
		}
	}
	
	public static BufferedImage getSprite(BufferedImage spritesheet, int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
}