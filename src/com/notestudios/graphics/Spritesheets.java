package com.notestudios.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.notestudios.main.Game;

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
			//spritesheet = ImageIO.read(getClass().getResource(path));
			spritesheetPlayer = ImageIO.read(getClass().getResource("/spritesheets/Player Spritesheet.png"));
			spritesheetTiles = ImageIO.read(getClass().getResource("/spritesheets/Tiles Spritesheet.png"));
			spritesheetUI = ImageIO.read(getClass().getResource("/spritesheets/UI Spritesheet.png"));
			Game.GameBackground = ImageIO.read(getClass().getResource("/images/Background.png"));
			Game.GameBackground2 = ImageIO.read(getClass().getResource("/images/Background.png"));
			
			Game.bigBackground = Spritesheets.spritesheetUI.getSubimage(0, 0, 112, 48);
			Game.defaultShortOptBg = Spritesheets.spritesheetUI.getSubimage(0, 48, 80, 16);
			Game.defaultLargeOptionBg = Spritesheets.spritesheetUI.getSubimage(0, 64, 144, 16);
			Game.menuCreditsIcon = Spritesheets.spritesheetUI.getSubimage(96, 96, 16, 16);
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, JOptionPane.ERROR, "An error ocurred while loading the spritesheets.\n"+e, 0);
		}
	}
	
	public static BufferedImage getSprite(BufferedImage spritesheet, int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
}