package com.notestudios.objects;

import java.awt.image.BufferedImage;

import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;

public class LifePack extends InteractibleObjects {

	public static BufferedImage LIFEPACK_EN = Spritesheets.tiles.getSprite(0, 32, 16, 16);

	public LifePack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	@Override
	public void tick() {
		checkCollision(this, Game.player);
	}
	
	@Override
	public void ifCollides() {
		if(Game.player.life < Game.player.maxLife) {
			Game.player.life += 25;
			if (Game.player.life > Game.player.maxLife) {
				Game.player.life = 100;
			}
			InteractibleObjects.objects.remove(this);
		}
	}

}