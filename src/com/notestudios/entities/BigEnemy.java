package com.notestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import com.notestudios.gameapi.Trophies;
import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;
import com.notestudios.world.AStar;
import com.notestudios.world.Camera;
import com.notestudios.world.Vector2i;

public class BigEnemy extends Entity{
	
	public double maxLife = 110;

	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;

	private BufferedImage[] sprites;

	public double enemyLife = maxLife;
	public boolean enemyHurt = false;
	private boolean isDamaged;
	int damageFrames = 10, curDamage = 0;
	public double speed = 0.5;
	public static boolean bossfight = false;
	public boolean followPlayer = false;

	public static List<BigEnemy> bosses;

	public BigEnemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		sprites = new BufferedImage[2];
		sprites[0] = Spritesheets.spritesheetPlayer.getSubimage(144, 96, 32, 32);
		sprites[1] = Spritesheets.spritesheetPlayer.getSubimage(176, 96, 32, 32);
	}

	public void tick() {

		mwidth = 32;
		mheight = 32;
		width = 32;
		height = 32;
		depth = 1;
		bossfight = true;
		if (!isColliddingWithPlayer()/* && followPlayer*/) {
			
			if (path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
				Vector2i end = new Vector2i(((int) (Game.player.x / 16)), ((int) (Game.player.y / 16)));
				path = AStar.findPath(Game.world, start, end);

			}
			/* Old movements
			if (getX() < Game.player.getX() && World.isFreeDynamic((int) (getX() + speed), this.getY(), 32, 32) && !isCollidding((int) (getX() + speed), this.getY())) {
				moved = true;
				x += speed; 
			} else if (getX() > Game.player.getX() && World.isFreeDynamic((int) (getX() - speed), this.getY(), 32, 32) && !isCollidding((int) (getX() - speed), this.getY())) {
				moved = true;
				x -= speed;
			} if (getY() < Game.player.getY() && World.isFreeDynamic(this.getX(), (int) (getY() + speed), 32, 32) && !isCollidding(this.getX(), (int) (getY() + speed))) {
				moved = true;
				y += speed;
			} else if (getY() > Game.player.getY() && World.isFreeDynamic(this.getX(), (int) (getY() - speed), 32, 32) && !isCollidding(this.getX(), (int) (getY() - speed))) {
				moved = true;
				y -= speed;
			}
			*/
		} else {
			Game.player.isDamaged = true;
			if (!Game.player.isJumping) {
				Game.getRandom().nextInt(10);
				Game.player.life -= Game.getRandom().nextInt(6);
			}
		}
		if (Game.getRandom().nextInt(100) < 50) {
			followPath(path);
		}
		
		if (x % 16 == 0 && y % 16 == 0) {
			if (new Random().nextInt(100) < 10) {
				Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
				Vector2i end = new Vector2i(((int) (Game.player.x / 16)), ((int) (Game.player.y / 16)));
				path = AStar.findPath(Game.world, start, end);
			}
		}
		/* Animation */
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
			bossfight = false;
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
			Game.world.generateParticles(40, getX()+8, getY()+8, 2, 2, Color.blue);
		}

		bossfight = false;
		
		/*if(Game.gameState.equals("Normal")) {
			UI.menu.pause = false;
			World.nextLevel("level"+Game.curLevel+".png");
			Game.gameState = "Credits";
			Game.cutsceneState = Game.enterCutscene;
			Game.downTransition = true;
		}*/
		if(Game.jolt.isLoggedIn) {
			Game.jolt.trophies.achieve(Trophies.trophyList.get("why is he so big?"));
		}
		Entity.entities.remove(this);
		bosses.remove(this);
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
		for (int i = 0; i < bosses.size(); i++) {
			BigEnemy e = bosses.get(i);
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
			g.drawImage(BigEnemyDMG, getX() - Camera.x, getY() - Camera.y, getWidth(), getHeight(), null);
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
