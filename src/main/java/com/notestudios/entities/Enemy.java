package com.notestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;
import com.notestudios.world.AStar;
import com.notestudios.world.Camera;
import com.notestudios.world.Vector2i;

public class Enemy extends Entity {

	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;

	private BufferedImage[] sprites;

	public int maxLife = 5, enemyLife = maxLife;
	public boolean isDamaged;
	private int damageFrames = 10, curDamage = 0;

	public static BufferedImage ENEMY_DMG = Spritesheets.entities.getSprite(224, 0, 16, 16);

	public static BufferedImage ENEMY_EN = Spritesheets.entities.getSprite(193, 0, 16, 16);

	public static List<Enemy> enemies;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		sprites = new BufferedImage[2];
		sprites[0] = Spritesheets.entities.getSprite(192, 0, 16, 16);
		sprites[1] = Spritesheets.entities.getSprite(192 + 16, 0, 16, 16);
	}

	public void tick() {

		mwidth = 16;
		mheight = 16;
		depth = 1;
		
		if (!isColliddingWithPlayer()) {
			if (path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
				Vector2i end = new Vector2i(((int) (Game.player.x / 16)), ((int) (Game.player.y / 16)));
				path = AStar.findPath(Game.world, start, end);

			}

		} else {
			Game.player.isDamaged = true;
			if (!Game.player.isJumping && Game.getRandom().nextInt(10) < 8) 
				Game.player.life-=Game.getRandom().nextInt(2);
		}
		if (Game.getRandom().nextInt(100) < 50) {
			followPath(path);
		}
		if (x % 16 == 0 && y % 16 == 0) {
			if (Game.getRandom().nextInt(100) < 10) {
				Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
				Vector2i end = new Vector2i(((int) (Game.player.x / 16)), ((int) (Game.player.y / 16)));
				path = AStar.findPath(Game.world, start, end);
			}
		}
		/* Animação */
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
		colliddingBullet();

		if (enemyLife <= 0) {
			selfDestroy();
			return;
		}
		if (isDamaged) {
			curDamage++;
			if (curDamage == damageFrames) {
				curDamage = 0;
				isDamaged = false;
			}
		}
	}

	public void selfDestroy() {
		if (Game.graphicsQuality == 2) {
			Game.world.generateParticles(10, getX(), getY(), 1, 1, Color.red);
		}
		Enemy.enemies.remove(this);
		Entity.entities.remove(this);
		return;
	}

	public void colliddingBullet() {
		for (int i = 0; i < Bullets.bullets.size(); i++) {
			Entity e = Bullets.bullets.get(i);
			if (e instanceof Bullets) {
				if (Entity.isCollidding(this, e)) {
					isDamaged = true;
					enemyLife--;
					Bullets.bullets.remove(i);
					return;
				}
			}
		}
	}

	public boolean isCollidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, mwidth, mheight);
		for (int i = 0; i < Enemy.enemies.size(); i++) {
			Enemy e = Enemy.enemies.get(i);
			if (e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, mwidth, mheight);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}

		return false;
	}

	public boolean isColliddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return enemyCurrent.intersects(player);
	}

	public void render(Graphics g) {
		if (moved) {
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		if (isDamaged) {
			g.drawImage(Enemy.ENEMY_DMG, getX() - Camera.x, getY() - Camera.y, getWidth(), getHeight(), null);
		} else if (!isDamaged) {
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

		if (!Game.debugMode) {
		} else if (Game.debugMode) {
			g.fillRect(getX() + maskx - Camera.x, getY() + masky - Camera.y, mwidth, mheight);
			g.setColor(Color.blue);
		}

	}
}

//Old Enemy AI
/*
 * if (x < Game.player.getX() && World.isFree((int) (x + speed), this.getY()) &&
 * !isCollidding((int) (x + speed), this.getY())) { moved = true; x += speed; }
 * else if (x > Game.player.getX() && World.isFree((int) (x - speed),
 * this.getY()) && !isCollidding((int) (x - speed), this.getY())) { moved =
 * true; x -= speed; }
 * 
 * if (y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed)) &&
 * !isCollidding(this.getX(), (int) (y + speed))) { moved = true; y += speed; }
 * else if (y > Game.player.getY() && World.isFree(this.getX(), (int) (y -
 * speed)) && !isCollidding(this.getX(), (int) (y - speed))) { moved = true; y
 * -= speed; }
 */
