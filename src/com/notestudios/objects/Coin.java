package com.notestudios.objects;

import java.awt.image.BufferedImage;

import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;

public class Coin extends InteractibleObjects {

	public static BufferedImage COIN_EN = Spritesheets.tiles.getSprite(64, 32, 16, 16);

	public Coin(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	@Override
	public void tick() {
		checkCollision(this, Game.player);
	}

	@Override
	public void ifCollides() {
		Game.playerCoins++;
		InteractibleObjects.objects.remove(this);
	}

}