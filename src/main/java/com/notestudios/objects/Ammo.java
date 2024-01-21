package com.notestudios.objects;

import java.awt.image.BufferedImage;

import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;

public class Ammo extends InteractibleObjects {

	public static BufferedImage BULLET_EN = Spritesheets.tiles.getSprite(16, 32, 16, 16);

	public Ammo(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	@Override
	public void tick() {
		checkCollision(this, Game.player);
	}

	@Override
	public void ifCollides() {
		if(Game.player.ammo < Game.player.maxAmmo) {
			Game.player.ammo += 50;
			if (Game.player.ammo > Game.player.maxAmmo) {
				Game.player.ammo = Game.player.maxAmmo;
			}
			InteractibleObjects.objects.remove(this);
		}
	}

}
