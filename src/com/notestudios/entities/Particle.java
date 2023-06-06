package com.notestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.notestudios.main.Game;
import com.notestudios.world.Camera;

public class Particle extends Entity {

	public int lifeTime = 50;
	public int curLife = 0;
	public int spd = 1;
	public double dx = 0;
	public double dy = 0;
	public Color color = Color.yellow;

	public Particle(int x, int y, int width, int height, Color color, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.color = color;
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
	}

	public void tick() {
		x += dx * spd;
		y += dy * spd;
		curLife++;
		if (lifeTime == curLife) {
			Game.entities.remove(this);
		}
	}

	public void render(Graphics g) {
		if (Game.graphics == 2) {
			g.fillRect(getX() - Camera.x, getY() - Camera.y, width, height);
			g.setColor(color);
		}
	}

}