package com.notestudios.entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;
import com.notestudios.world.Camera;
import com.notestudios.world.Node;
import com.notestudios.world.Vector2i;
import com.notestudios.world.World;

public class Entity {
	public double x;
	public double y;
	public double z;
	protected int width;
	protected int height;
	protected boolean moved = false;
	public int depth = 0;

	protected List<Node> path;

	private BufferedImage sprite;
	public static BufferedImage LIFEPACK_EN = Spritesheets.spritesheetTiles.getSubimage(0, 32, 16, 16);
	public static BufferedImage WEAPON_EN = Spritesheets.spritesheetTiles.getSubimage(32, 32, 16, 16);
	public static BufferedImage playerStop = Spritesheets.spritesheetPlayer.getSubimage(64, 16, 16, 16);
	public static BufferedImage BULLET_EN = Spritesheets.spritesheetTiles.getSubimage(16, 32, 16, 16);
	public static BufferedImage ENEMY_EN = Spritesheets.spritesheetPlayer.getSubimage(193, 0, 16, 16);
	public static BufferedImage GUN_RIGHT = Spritesheets.spritesheetPlayer.getSubimage(0, 64, 16, 16);
	public static BufferedImage GUN_LEFT = Spritesheets.spritesheetPlayer.getSubimage(16, 64, 16, 16);
	public static BufferedImage GUN_SELECT = Spritesheets.spritesheetUI.getSubimage(112, 0, 48, 32);
	public static BufferedImage GUN_DMG_LEFT = Spritesheets.spritesheetPlayer.getSubimage(0, 80, 16, 16);
	public static BufferedImage GUN_DMG_RIGHT = Spritesheets.spritesheetPlayer.getSubimage(16, 80, 16, 16);
	public static BufferedImage GUN_DOWN = Spritesheets.spritesheetPlayer.getSubimage(32, 64, 16, 16);
	public static BufferedImage ENEMY_DMG = Spritesheets.spritesheetPlayer.getSubimage(224, 0, 16, 16);
	public static BufferedImage UIPause = Spritesheets.spritesheetUI.getSubimage(96, 80, 16, 16);
	public static BufferedImage UIShop = Spritesheets.spritesheetUI.getSubimage(112, 80, 16, 16);
	public static BufferedImage ShopItemSelect = Spritesheets.spritesheetUI.getSubimage(48, 80, 48, 48);
	public static BufferedImage DefaultNPC_EN = Spritesheets.spritesheetPlayer.getSubimage(16, 96, 16, 16);
	public static BufferedImage COIN_EN = Spritesheets.spritesheetTiles.getSubimage(64, 32, 16, 16);
	public static BufferedImage BigEnemy = Spritesheets.spritesheetPlayer.getSubimage(208, 96, 32, 32);
	public static BufferedImage BigEnemyDMG = Spritesheets.spritesheetPlayer.getSubimage(112, 96, 32, 32);
	
	public int maskx, masky, mwidth, mheight;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}

	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}

	public void setX(int newX) {
		x = newX;
	}

	public void setY(int newY) {
		y = newY;
	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
	}

	public static Comparator<Entity> enSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0, Entity n1) {
			if (n1.depth < n0.depth)
				return +1;
			if (n1.depth > n0.depth)
				return -1;
			return 0;
		}
	};

	public void tick() {
		
	}

	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	// 80,64
	protected void followPath(List<Node> path) {
		if (path != null) {
			if (path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				/* X */
				if (x < target.x * 16) {
					moved = true;
					x++;
				} else if (x > target.x * 16) {
					moved = true;
					x--;
				}
				/* Y */
				if (y < target.y * 16) {
					moved = true;
					y++;
				} else if (y > target.y * 16) {
					moved = true;
					y--;
				}
				if (x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}

			}
		}
	}

	public static boolean isCollidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);
		return e1Mask.intersects(e2Mask);
	}

	public void render(Graphics g) {
		g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
		
		if (!Game.debug) {
		} else if (Game.debug) {
			g.fillRect(getX() + maskx - Camera.x, getY() + masky - Camera.y, mwidth, mheight);
			g.setColor(new Color(0, 0, 255, 125));
		}
	}
}
