package com.notestudios.objects;

import java.awt.image.BufferedImage;

import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;

public class CollectibleBomb extends InteractibleObjects {
	
	public static BufferedImage bomb = Spritesheets.tiles.getSprite(80, 32, 16, 16);

	public CollectibleBomb(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	@Override
	public void tick() {
		checkCollision(this, Game.player);
	}

	@Override
	public void ifCollides() {
		if(Game.player.bombs < Game.player.maxBombs) Game.player.bombs+=1;
	}

}
