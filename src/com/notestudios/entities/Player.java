package com.notestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.gameapi.GameJolt;
import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;
import com.notestudios.objects.Ammo;
import com.notestudios.objects.Coin;
import com.notestudios.objects.CollectibleBomb;
import com.notestudios.objects.InteractibleObjects;
import com.notestudios.objects.LifePack;
import com.notestudios.util.Sound;
import com.notestudios.world.Camera;
import com.notestudios.world.World;

public class Player extends Entity {

	public int frames = 0;
	public int maxFrames = 6;
	public int index = 0;
	public int maxIndex = 3;

	public int damageFrames = 0;
	public int mx, my;
	public int ammo = 0;
	public int maxAmmo = 1000;
	public int bombs = 0;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public int jumpFrames = 40;
	int curSpamTime = 0;
	int maxSpamTime = 60*2;

	double jumpCur = 0;
	public double life = 100;
	public double maxLife = 100;
	public double curSpeed = 1.0;
	public double defaultSpeed = 1.0;
	public double runSpeed = 1.4;
	double jumpSpeed = 3.5;

	public boolean right, up, left, down;
	public boolean isDamaged = false;
	public boolean shoot = false;
	public boolean isSpamming = false;
	public boolean mouseShoot = false;
	public boolean moved = false;
	public boolean playerRun;
	public boolean weapon = false;
	public boolean jump;
	public boolean isJumping;
	boolean jumpUp;
	boolean jumpDown;
	public boolean renderWeapon = true;
	public boolean isStopped = false;
	public boolean isShooting = false;
	public boolean haveBomb = false;

	public BufferedImage[] rightPlayer;
	public BufferedImage[] leftPlayer;
	public BufferedImage playerDamage;
	public BufferedImage[] downPlayer;
	public BufferedImage[] upPlayer;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		playerDamage = Spritesheets.spritesheetPlayer.getSubimage(64, 0, 16, 16);
		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Spritesheets.spritesheetPlayer.getSubimage((i * 16), 0, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			upPlayer[i] = Spritesheets.spritesheetPlayer.getSubimage((i * 16), 32, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			downPlayer[i] = Spritesheets.spritesheetPlayer.getSubimage((i * 16), 48, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Spritesheets.spritesheetPlayer.getSubimage((i * 16), 16, 16, 16);
		}
	}

	public void tick() {

		if(isSpamming) {
			if(GameJolt.isLoggedIn && !Game.jolt.getTrophy(GameJolt.trophiesIDs[1]).isAchieved()) {
				if(curSpamTime == maxSpamTime) {
					Game.jolt.achieveTrophy(GameJolt.trophiesIDs[1]);
				} else {
					curSpamTime++;
				}
			}
		}
		
		depth = 2;
		moved = false;
		if(right && World.isFree((int) (x + curSpeed), this.getY())) {
			moved = true;
			//isStopped = false;
			dir = right_dir;
			x += curSpeed;
			if(isJumping && !playerRun && World.isFree(getX() + 1, getY())) {
				x += 1;
			}
		} else if(left && World.isFree((int) (x - curSpeed), this.getY())) {
			moved = true;
			//isStopped = false;
			dir = left_dir;
			x -= curSpeed;
			if(isJumping && !playerRun && World.isFree(getX() - 1, getY())) {
				x -= 1;
			}
		}
		if(up && World.isFree(this.getX(), (int) (y - curSpeed))) {
			moved = true;
			//isStopped = false;
			dir = up_dir;
			y -= curSpeed;
			if (isJumping && !playerRun && World.isFree(getX() - 1, getY())) {
				x -= 1;
			}
		} else if(down && World.isFree(this.getX(), (int) (y + curSpeed))) {
			moved = true;
			//isStopped = false;
			dir = down_dir;
			y += curSpeed;
			if (isJumping && !playerRun && World.isFree(getX() + 1, getY())) {
				x += 1;
			}
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
			if (jumpUp) {
				jumpCur += jumpSpeed;
			} else if(jumpDown) {
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
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}

		this.checkCollisionLifePack();
		this.checkCollisionAmmo();
		this.checkCollisionGun();
		this.checkCollisionCoin();

		if (playerRun) {
			curSpeed = runSpeed;
		} else {
			curSpeed = defaultSpeed;
		}

		if (isDamaged) {
			damageFrames++;
			if (damageFrames == 10) {
				damageFrames = 0;
				isDamaged = false;
			}
		}
		if (shoot && !Game.pauseSelect && !Game.shopSelect) {
			shoot = false;
			mouseShoot = false;
			if (weapon && ammo > 0) {
				ammo--;
				int dx = 0;
				int dy = 0;
				int px = 0;
				int py = 7;
				if (dir == right_dir) {
					dx = 1;
					px = 12;
				} else if (dir == left_dir) {
					dx = -1;
					px = -12;
				}
				if (dir == up_dir) {
					dx = 0;
					dy = -1;
					px = 6;
					py = 0;
				} else if (dir == down_dir) {
					dx = 0;
					dy = 1;
					px = 6;
					py = 15;
				}
				Bullets bullet = new Bullets(getX() + px, getY() + py, 3, 3, null, dx, dy, Color.yellow);
				Game.bullets.add(bullet);
			}
		}

		if (mouseShoot && !Game.pauseSelect && !Game.shopSelect) {
			shoot = false;
			if (!jump) {
				if(!isShooting) {
					mouseShoot = false;
				}

				if (weapon && ammo > 0) {
					ammo--;
					int px = 0;
					int py = 8;
					double angle = 0;
					if (dir == right_dir) {
						px = 18;
						angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
					} else if (dir == left_dir) {
						px = -8;

						angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
					}
					if (dir == down_dir) {
						px = +6;
						py = 14;
						angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
					} else if (dir == up_dir) {
						px = +6;
						py = -2;
						angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
					}
					double dx = Math.cos(angle);
					double dy = Math.sin(angle);
					Bullets bullet = new Bullets(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy,
							Color.yellow);
					Game.bullets.add(bullet);
				}
			}

		}
		if (life <= 0) {
			life = 0;
			Game.gameState = "Game Over";
			if(!Game.mute) {
				Sound.gameOverSoundEffect.play();
			}
		}
		if (!isJumping) {
			z = 0;
		}
		updateCamera();
	}

	public void checkCollisionGun() {
		if (Game.cutsceneState == Game.finishCutscene) {
			for (int i = 0; i < Game.entities.size(); i++) {
				Entity atual = Game.entities.get(i);
				if(atual instanceof Weapon) {
					if (Entity.isCollidding(this, atual)) {
						if(ammo != maxAmmo || !weapon) {
							if(!weapon) {
								weapon = true;
							}
							ammo += 25;
							if (ammo > maxAmmo) {
								ammo = maxAmmo;
							}
							Game.entities.remove(atual);
						}
					}
				}
			}
		}
	}
	
	public void checkCollisionBomb() {
		if(Game.cutsceneState == Game.finishCutscene) {
			for(int i = 0; i < Game.objects.size(); i++) {
				InteractibleObjects current = Game.objects.get(i);
				if(current instanceof CollectibleBomb) {
					if(InteractibleObjects.isCollidding(this, current)) {
						
					}
				}
			}
		}
	}

	public void checkCollisionAmmo() {
		if (Game.cutsceneState == Game.finishCutscene) {
			for (int i = 0; i < Game.objects.size(); i++) {
				InteractibleObjects atual = Game.objects.get(i);
				if (atual instanceof Ammo) {
					if (InteractibleObjects.isCollidding(this, atual)) {
						if(ammo < maxAmmo) {
							ammo += 50;
							if (ammo > maxAmmo) {
								ammo = maxAmmo;
							}
							Game.objects.remove(atual);
						}
					}
				}
			}
		}
	}

	public void checkCollisionCoin() {
		if (Game.cutsceneState == Game.finishCutscene) {
			for (int i = 0; i < Game.objects.size(); i++) {
				InteractibleObjects atual = Game.objects.get(i);
				if (atual instanceof Coin) {
					if (InteractibleObjects.isCollidding(this, atual)) {
						Game.playerCoins+=1;
						Game.objects.remove(atual);
					}
				}
			}
		}
	}

	public void checkCollisionLifePack() {
		if (Game.cutsceneState == Game.finishCutscene) {
			for (int i = 0; i < Game.objects.size(); i++) {
			InteractibleObjects atual = Game.objects.get(i);
			if (atual instanceof LifePack) {
				if(InteractibleObjects.isCollidding(this, atual)) {
					if(life < maxLife) {
						life += 25;
						if (life > maxLife) {
							life = 100;
						}
						Game.objects.remove(atual);
					}
				}
			}
			}
		}
	}

	public void render(Graphics g) {
		
		g.setColor(new Color(0, 0, 0, 80));
		g.fillOval(getX() - Camera.x + 4, getY() - Camera.y + 13, 9, 4);
		
		if (!isDamaged) {
			if (dir == right_dir) {
				g.drawImage(rightPlayer[index], getX() - Camera.x, getY() - Camera.y - (int) z, null);
				if (weapon && renderWeapon) {
					g.drawImage(GUN_RIGHT, getX() + 9 - Camera.x, getY() + 1 - Camera.y - (int) z, null);
				}
			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], getX() - Camera.x, getY() - Camera.y - (int) z, null);
				if (weapon && renderWeapon) {
					g.drawImage(GUN_LEFT, getX() - 9 - Camera.x, getY() + 1 - Camera.y - (int) z, null);
				}
			}
			if (dir == up_dir) {
				g.drawImage(upPlayer[index], getX() - Camera.x, getY() - Camera.y - (int) z, null);
			} else if (dir == down_dir) {
				g.drawImage(downPlayer[index], getX() - Camera.x, getY() - Camera.y - (int) z, null);
				if (weapon && renderWeapon) {
					g.drawImage(GUN_DOWN, getX() - Camera.x, getY() + 1 - Camera.y - (int) z, null);
				}
			}
		} else if (isDamaged) {
			g.drawImage(playerDamage, getX() - Camera.x, getY() - Camera.y - (int) z, null);
			if (weapon && renderWeapon) {
				if (dir == left_dir) {
					g.drawImage(GUN_DMG_LEFT, getX() - 9 - Camera.x, getY() + 1 - Camera.y - (int) z, null);
				} else if (dir == right_dir) {
					g.drawImage(GUN_DMG_RIGHT, getX() + 9 - Camera.x, getY() + 1 - Camera.y - (int) z, null);
				}

			}
		}
	}
}