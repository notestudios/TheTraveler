package com.notestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import com.notestudios.main.Game;
import com.notestudios.world.Camera;

public class Bullets extends Entity {
	private double dx;
	private double dy;

	private double spd = 7;

	public int life = 60, curLife = 0;
	public static List<Bullets> bullets;

	public Bullets(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy, Color color) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		if (Game.world.isFreeDynamic((int) (x + (dx * spd)), (int) (y + (dy * spd)), 3, 3)) {
			x += dx * spd;
			y += dy * spd;
		} else {
			Bullets.bullets.remove(this);
			if (Game.graphicsQuality == 2) {
				Game.world.generateParticles(20, getX(), getY(), 1, 1, Color.yellow);
			}
			return;
		}
		curLife++;
		if (curLife == life) {
			Bullets.bullets.remove(this);
			return;
		}
	}

	public void render(Graphics g) {
		g.fillOval(getX() - Camera.x, getY() - Camera.y, width, height);
		g.setColor(Color.yellow);

	}
}