package com.notestudios.objects;

import java.awt.image.BufferedImage;

import com.notestudios.entities.Entity;

public class Bush extends Entity {

	public Bush(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		depth = 0;
	}

}