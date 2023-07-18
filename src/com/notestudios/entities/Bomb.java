package com.notestudios.entities;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;
import com.notestudios.world.World;

public class Bomb extends Entity {

	public double curTime = 0;
	public double maxTime = 60*2;
	public double enemyDamage = 2;
	public double playerDamage = 25;
	public static boolean exploded = true;
	
	
	
	public Bomb(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		curTime++;
		if(curTime == maxTime) {
			curTime = 0;
			exploded = true;
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity en = Game.entities.get(i);
				if(calculateDistance(getX(), getY(), en.getX(), en.getY()) < 50) {
					if(en instanceof Enemy) {
						((Enemy) en).enemyLife-=enemyDamage;
					} else if(en instanceof Player) {
						((Player) en).life-=playerDamage;
					}
				}
			}
			Game.player.bombs-=1;
			World.generateParticles(14, getX(), getY(), 1, 1, Color.gray);
			Game.entities.remove(this);
		}
	}
	
}
