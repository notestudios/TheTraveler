package com.notestudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.notestudios.main.Game;
import com.notestudios.main.Window;
import com.notestudios.world.Camera;
import com.notestudios.world.Node;
import com.notestudios.world.Vector2i;
import com.notestudios.world.World;

interface MainFunctions {
	public void tick();
}

public class Entity implements MainFunctions {

	protected double x, y, z;
	protected int width, height;
	protected boolean moved = false;
	public double speed = 1;
	public int depth = 0;

	protected List<Node> path;
	public static List<Entity> entities;

	private BufferedImage sprite;
	public int maskx = 0, masky = 0, mwidth, mheight;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		if(Game.devMode && !getClass().getSimpleName().equals(Particle.class.getSimpleName())) 
			System.out.println("Creating "+getClass().getSimpleName()+"("+entities.size()+") entity...");
		
		initConstructor(x, y, width, height, sprite);
	}

	public Entity(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		if(Game.devMode && !getClass().getSimpleName().equals(Particle.class.getSimpleName())) 
			System.out.println("Creating "+getClass().getSimpleName()+"("+entities.size()+") entity...");
		
		initConstructor(x, y, width, height, sprite);
		this.speed = speed;
	}

	private void initConstructor(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.sprite = sprite;

		this.mwidth = width;
		this.mheight = height;
	}

	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}

	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Window.WIDTH / 2), 0, World.getWidth() * 16 - Window.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Window.HEIGHT / 2), 0, World.getHeight() * 16 - Window.HEIGHT);
	}

	public static Comparator<Entity> enSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0, Entity n1) {
			if (n1.depth < n0.depth)
				return +1;
			if (n1.depth > n0.depth)
				return -1;
			return 0;
		}
	};
	
	
	public void tick() {}

	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	protected void followPath(List<Node> path) {
		if (path != null) {
			if (path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				if (x < target.x * 16) {
					moved = true;
					x++;
				} else if (x > target.x * 16) {
					moved = true;
					x--;
				} if (y < target.y * 16) {
					moved = true;
					y++;
				} else if (y > target.y * 16) {
					moved = true;
					y--;
				} if (x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
			}
		}
	}

	public static boolean isCollidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);
		return e1Mask.intersects(e2Mask);
	}

	public void renderShadow(Graphics g, int shadowWidth, int shadowHeight) {
	    int halfShadow = getHeight()-(shadowHeight/2);
		g.setColor(new Color(0, 0, 0, 100));
		g.fillOval(getX() + (getWidth()/2) - (shadowWidth/2) - Camera.x, getY() + halfShadow - Camera.y, shadowWidth, shadowHeight);
	}

	public void render(Graphics g) {
		g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
		if(Game.debugMode) {
			g.fillRect(getX() + maskx - Camera.x, getY() + masky - Camera.y, mwidth, mheight);
			g.setColor(new Color(0, 0, 255, 125));
		}
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
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
}
