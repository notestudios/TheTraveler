package com.notestudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.graphics.Spritesheets;

public class Tile {
	public static BufferedImage TILE_FLOOR = Spritesheets.spritesheetTiles.getSubimage(0, 0, 16, 16);
	public static BufferedImage TILE_FLOOR2 = Spritesheets.spritesheetTiles.getSubimage(16, 0, 16, 16);
	public static BufferedImage TILE_FLOOR3 = Spritesheets.spritesheetTiles.getSubimage(32, 0, 16, 16);
	public static BufferedImage TILE_FLOOR4 = Spritesheets.spritesheetTiles.getSubimage(48, 0, 16, 16);
	public static BufferedImage TILE_WALL = Spritesheets.spritesheetTiles.getSubimage(0, 16, 16, 16);
	public static BufferedImage FLOWER_TILE = Spritesheets.spritesheetTiles.getSubimage(96, 0, 16, 16);
	public static BufferedImage BUSH_TILE = Spritesheets.spritesheetTiles.getSubimage(64, 0, 32, 16);
	
	private BufferedImage sprite;
	private int x, y;

	public void tick() {

	}

	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
