package com.notestudios.objects;

import java.awt.image.BufferedImage;

import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;

public class Weapon extends InteractibleObjects {

	public static BufferedImage WEAPON_EN = Spritesheets.tiles.getSprite(32, 32, 16, 16);
	
	public Weapon(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	@Override
	public void tick() {
		checkCollision(this, Game.player);
	}
	
	@Override
	public void ifCollides() {
		if(Game.player.ammo != Game.player.maxAmmo || !Game.player.weapon) {
			if(!Game.player.weapon) {
				Game.player.weapon = true;
			}
			Game.player.ammo += 25;
			if (Game.player.ammo > Game.player.maxAmmo) {
				Game.player.ammo = Game.player.maxAmmo;
			}
			InteractibleObjects.objects.remove(this);
		}
	}
	
}
