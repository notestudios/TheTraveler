package com.notestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.gameapi.Trophies;
import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;
import com.notestudios.world.Camera;

public class Bomb extends Entity {

	public double curTime = 0;
	public double maxTime = 60*2;
	public double enemyDamage = 5, playerDamage = 20, bossDamage = 15;
	public static boolean exploded = true;
	private int particleAmount = 30;
	private Color particleColors[] = {Color.yellow, Color.orange, Color.black};
	
	private int feedbackTime, feedbackMaxTime = (int)(maxTime/3);
	
	public static int explodedEnemies = 0, maxEnemies = 10;
	public static boolean canTriggerAchievement = true;
	
	public BufferedImage aboutToExplode = Spritesheets.entities.getSprite(32, 80, 16, 16);
	
	public Bomb(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		Thread t = new Thread(new Runnable() {
			public void run() {
				if(Game.jolt.isLoggedIn && canTriggerAchievement) 
					canTriggerAchievement = !Game.jolt.getTrophy(Trophies.trophyList.get("kaBOOM!")).isAchieved();
			}
		});
		t.start();
	}
	
	public void tick() {
		curTime++;
		feedbackTime++;
		if(curTime == maxTime) {
			curTime = 0;
			exploded = true;
			for(int i = 0; i < Entity.entities.size(); i++) {
				Entity en = Entity.entities.get(i);
				if(calculateDistance(getX(), getY(), en.getX(), en.getY()) < 50) {
					if(en instanceof Enemy) {
						((Enemy) en).enemyLife-=enemyDamage;
						((Enemy) en).isDamaged = true;
						explodedEnemies++;
					} else if(en instanceof Player) {
						((Player) en).life-=playerDamage;
						((Player) en).isDamaged = true;
					} else if(en instanceof BigEnemy) {
						((BigEnemy) en).enemyLife-=bossDamage;
						((BigEnemy) en).isDamaged = true;
						explodedEnemies++;
					}
				}
			}
			for(int i = 0; i < particleColors.length; i++) 
				Game.world.generateParticles(particleAmount/3, getX(), getY(), 1, 1, particleColors[i]);
			
			Entity.entities.remove(this);
		}
		
	}
	
	public static void triggerAchievement() {
		if(Game.jolt.isLoggedIn && canTriggerAchievement) {
			Game.jolt.trophies.achieve(Trophies.trophyList.get("kaBOOM!"));
			canTriggerAchievement = false;
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, 100));
		g.fillOval(getX()+(width/2)-6 - Camera.x, getY()+height-4 - Camera.y, 12, 4);
		if(feedbackTime >= feedbackMaxTime) 
			g.drawImage(aboutToExplode, getX() - Camera.x, getY() - Camera.y, null);
		else 
			super.render(g);
		
		if(feedbackTime >= feedbackMaxTime) { feedbackTime = 0; }
	}
}
