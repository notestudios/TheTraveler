package com.notestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;
import com.notestudios.world.Camera;
import com.notestudios.world.World;

public class Bullets extends Entity {
	private double dx;
	private double dy;

	private double spd = 7;

	public int life = 60, curLife = 0;

	public Bullets(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy, Color color) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		if (World.isFreeDynamic((int) (x + (dx * spd)), (int) (y + (dy * spd)), 3, 3)) {
			x += dx * spd;
			y += dy * spd;
		} else {
			Game.bullets.remove(this);
			if (Game.graphics == 2) {
				World.generateParticles(20, getX(), getY(), 1, 1, Color.yellow);
			}
			return;
		}
		curLife++;
		if (curLife == life) {
			Game.bullets.remove(this);
			return;
		}
	}

	public void render(Graphics g) {
		g.fillOval(getX() - Camera.x, getY() - Camera.y, width, height);
		g.setColor(Color.yellow);

	}
}