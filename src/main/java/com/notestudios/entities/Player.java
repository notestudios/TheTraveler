package com.notestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.gameapi.Trophies;
import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;
import com.notestudios.objects.CollectibleBomb;
import com.notestudios.util.Sound;
import com.notestudios.world.Camera;

public class Player extends Entity {
	
	public int frames = 0, maxFrames = 6;
	public int index = 0, maxIndex = 3;

	public int damageFrames = 0;
	public int mx, my;
	public int ammo = 0, maxAmmo = 1000;
	public int bombs = 0, maxBombs = 99;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public int jumpFrames = 40;
	private int curSpamTime = 0;
	private int maxSpamTime = 60*3;

	private double jumpCur = 0;
	private double jumpSpeed = 3.5;
	public double life = 100, maxLife = 100;
	public double curSpeed = 1.0;
	public double defaultSpeed = 1.0;
	public double runSpeed = 1.4;

	public boolean right, up, left, down;
	public boolean isDamaged = false;
	public boolean keyShoot, mouseShoot;
	public boolean isShooting = false;
	public boolean moved = false;
	public boolean isRunning;
	public boolean weapon = false;
	public boolean jump, isJumping;
	private boolean jumpUp, jumpDown;
	public boolean renderWeapon = true;
	public boolean haveBomb = false;
	public boolean placeBomb = false;


	public BufferedImage[] rightPlayer;
	public BufferedImage[] leftPlayer;
	public BufferedImage playerDamage;
	public BufferedImage[] downPlayer;
	public BufferedImage[] upPlayer;
	
	public static BufferedImage GUN_DOWN = Spritesheets.entities.getSprite(32, 64, 16, 16);
	public static BufferedImage GUN_LEFT = Spritesheets.entities.getSprite(16, 64, 16, 16);
	public static BufferedImage GUN_RIGHT = Spritesheets.entities.getSprite(0, 64, 16, 16);
	public static BufferedImage GUN_DMG_RIGHT = Spritesheets.entities.getSprite(16, 80, 16, 16);
	public static BufferedImage GUN_DMG_LEFT = Spritesheets.entities.getSprite(0, 80, 16, 16);
	public static BufferedImage playerStop = Spritesheets.entities.getSprite(64, 16, 16, 16);

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		playerDamage = Spritesheets.entities.getSprite(64, 0, 16, 16);
		
		for (int i = 0; i < 4; i++) 
			rightPlayer[i] = Spritesheets.entities.getSprite((i * 16), 0, 16, 16);
		
		for(int i = 0; i < 4; i++) 
			upPlayer[i] = Spritesheets.entities.getSprite((i * 16), 32, 16, 16);
		
		for(int i = 0; i < 4; i++) 
			downPlayer[i] = Spritesheets.entities.getSprite((i * 16), 48, 16, 16);
		
		for(int i = 0; i < 4; i++) 
			leftPlayer[i] = Spritesheets.entities.getSprite((i * 16), 16, 16, 16);
		
	}

	public void tick() {
		depth = 2;
		moved = false;
		haveBomb = bombs > 0;
		
		if(isShooting) {
			if(Game.jolt.isLoggedIn && Game.jolt.getTrophy(Trophies.trophyList.get("SPAAAAAMMMMMM")).isAchieved()) {
				curSpamTime++;
				if(curSpamTime == maxSpamTime) {
					curSpamTime = 0;
					Game.jolt.trophies.achieve(Trophies.trophyList.get("SPAAAAAMMMMMM"));
				}
			}
		}
		if(placeBomb) {
			placeBomb = false;
			placeBomb();
		}
		if(right && Game.world.isFree((int) (x + curSpeed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += curSpeed;
			if(isJumping && !isRunning && Game.world.isFree(getX() + 1, getY())) 
				x += 1;
		} else if(left && Game.world.isFree((int) (x - curSpeed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= curSpeed;
			if(isJumping && !isRunning && Game.world.isFree(getX() - 1, getY())) 
				x -= 1;
		} if(up && Game.world.isFree(this.getX(), (int) (y - curSpeed))) {
			moved = true;
			dir = up_dir;
			y -= curSpeed;
			if (isJumping && !isRunning && Game.world.isFree(getX() - 1, getY())) 
				x -= 1;
		} else if(down && Game.world.isFree(this.getX(), (int) (y + curSpeed))) {
			moved = true;
			dir = down_dir;
			y += curSpeed;
			if (isJumping && !isRunning && Game.world.isFree(getX() + 1, getY())) 
				x += 1;
		}

		if(Game.cutsceneState == Game.enterCutscene) {
			if (up || down || left) {
				if (right) {
					right = false;
					Game.cutsceneState = Game.finishCutscene;
				}
			}
		}
		if(jump) {
			if (isJumping == false) {
				jump = false;
				jumpDown = false;
				jumpUp = true;
				isJumping = true;
			}
		}
		if(isJumping) {
			if(jumpUp) 
				jumpCur += jumpSpeed;
			else if(jumpDown) {
				jumpCur -= jumpSpeed + 1.5;
				if (jumpCur <= 0) {
					isJumping = false;
					jumpDown = false;
					jumpUp = false;
				}
			}
			z = jumpCur;
			if(jumpCur >= jumpFrames) {
				jumpUp = false;
				jumpDown = true;
			}
		}

		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) 
					index = 0;
			}
		} else {
			frames = 0;
			index = 0;
		}

		if(isRunning) 
			curSpeed = runSpeed;
		else 
			curSpeed = defaultSpeed;

		if(isDamaged) {
			damageFrames++;
			if (damageFrames == 10) {
				damageFrames = 0;
				isDamaged = false;
			}
		}
		if(keyShoot) {
			keyShoot = false;
			mouseShoot = false;
			if(weapon && ammo > 0) {
				ammo--;
				int dx = 0, dy = 0, px = 0, py = 7;
				if(dir == right_dir) {
					dx = 1;
					px = 16;
				} else if(dir == left_dir) {
					dx = -1;
					px = -3;
				} if(dir == up_dir) {
					dx = 0; dy = -1;
					px = 6; py = 0;
				} else if(dir == down_dir) {
					dx = 0; dy = 1;
					px = 6; py = 10;
				}
				Bullets bullet = new Bullets(getX() + px, getY() + py, 3, 3, null, dx, dy, Color.yellow);
				Bullets.bullets.add(bullet);
			}
		}
		if(mouseShoot) {
			keyShoot = false;
			if(!jump) {
				mouseShoot = false;
				if(weapon && ammo > 0) {
					ammo--;
					int px = 0, py = 8;
					double angle = 0;
					if (dir == right_dir) {
						px = 18;
						angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
					} else if (dir == left_dir) {
						px = -8;
						angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
					} if (dir == down_dir) {
						px = +6; py = 14;
						angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
					} else if (dir == up_dir) {
						px = +6; py = -2;
						angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
					}
					double dx = Math.cos(angle), dy = Math.sin(angle);
					Bullets bullet = new Bullets(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy, Color.yellow);
					Bullets.bullets.add(bullet);
				}
			}
		}
		if(life <= 0) {
			life = 0;
			Game.world.generateParticles(10, getX()+(getWidth()/2), getY()+(getHeight()/2), 1, 1, Color.white);
			Game.gameState = "Game Over";
			if(!Game.mute) Sound.gameOverSoundEffect.play();
		}
		if(!isJumping) 
			z = 0;
		
		updateCamera();
	}
	
	public static void init() {
		Game.player = new Player(0, 0, 16, 16, Player.playerStop);
		entities.add(Game.player);
	}
	
	private void placeBomb() {
		if(bombs > 0) {
			bombs-=1;
			Entity.entities.add(new Bomb(getX(), getY(), 16, 16, CollectibleBomb.bomb));
		}
	}

	public void render(Graphics g) {
		renderShadow(g, getWidth()-6, 4);
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], getX() - Camera.x, getY() - Camera.y - (int) z, null);
				if(weapon && renderWeapon) 
					g.drawImage(Player.GUN_RIGHT, getX() + 9 - Camera.x, getY() + 1 - Camera.y - (int) z, null);
			} else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], getX() - Camera.x, getY() - Camera.y - (int) z, null);
				if (weapon && renderWeapon) 
					g.drawImage(Player.GUN_LEFT, getX() - 9 - Camera.x, getY() + 1 - Camera.y - (int) z, null);
			}
			if(dir == up_dir) 
				g.drawImage(upPlayer[index], getX() - Camera.x, getY() - Camera.y - (int) z, null);
			else if(dir == down_dir) {
				g.drawImage(downPlayer[index], getX() - Camera.x, getY() - Camera.y - (int) z, null);
				if(weapon && renderWeapon) 
					g.drawImage(Player.GUN_DOWN, getX() - Camera.x, getY() + 1 - Camera.y - (int) z, null);
			}
		} else if(isDamaged) {
			g.drawImage(playerDamage, getX() - Camera.x, getY() - Camera.y - (int) z, null);
			if(weapon && renderWeapon) {
				if(dir == left_dir) 
					g.drawImage(Player.GUN_DMG_LEFT, getX() - 9 - Camera.x, getY() + 1 - Camera.y - (int) z, null);
				else if(dir == right_dir) 
					g.drawImage(Player.GUN_DMG_RIGHT, getX() + 9 - Camera.x, getY() + 1 - Camera.y - (int) z, null);
			}
		}
	}
}