package com.notestudios.objects;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.notestudios.entities.Entity;
import com.notestudios.main.Game;
import com.notestudios.world.Camera;

public class InteractibleObjects {
	
	public double x;
	public double y;
	protected int width;
	protected int height;
	public int depth = 0;
	BufferedImage sprite;
	
	protected int maskx, masky, mwidth, mheight;

	public InteractibleObjects(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}

	public void setX(int newX) {
		x = newX;
	}

	public void setY(int newY) {
		y = newY;
	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public static boolean isCollidding(Entity e1, InteractibleObjects io2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(io2.getX() + io2.maskx, io2.getY() + io2.masky, io2.mwidth, io2.mheight);
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
		
		if (!Game.debug) {
		} else if (Game.debug) {
			g.fillRect(getX() + maskx - Camera.x, getY() + masky - Camera.y, mwidth, mheight);
			g.setColor(new Color(0, 0, 255, 125));
		}
	}

}
