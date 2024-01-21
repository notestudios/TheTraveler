package com.notestudios.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import com.notestudios.entities.BigEnemy;
import com.notestudios.entities.Enemy;
import com.notestudios.entities.Entity;
import com.notestudios.entities.Npc;
import com.notestudios.main.Game;
import com.notestudios.world.WallTile;
import com.notestudios.world.World;

public class Minimap {
	
	public BufferedImage minimap;
	public int[] minimapPixels;
	private static int x = 10, y = 450, width, height;
	
	public Minimap() {
		initializeMinimap();
	}
	
	public static void initialize() {
		Game.ui.worldMinimap = new Minimap();
		width = World.getWidth()*6;
		height = World.getHeight()*6;
		y = Game.window.getHeight()-height-10;
	}
	
	private void initializeMinimap() {
		minimap = new BufferedImage(World.getWidth(), World.getHeight(), BufferedImage.TYPE_INT_RGB);
		minimapPixels = ((DataBufferInt) minimap.getRaster().getDataBuffer()).getData();
	}
	
	public void tick() {
		Arrays.fill(minimapPixels, 0xFF009616);
		for (int xx = 0; xx < World.getWidth(); xx++) {
			for (int yy = 0; yy < World.getHeight(); yy++) {
				if (World.tiles[xx + (yy * World.getWidth())] instanceof WallTile) {
					minimapPixels[xx + (yy * World.getWidth())] = 0xFFFFFFFF;
				}
			}
		}
		/* Player */
		
		int xPlayer = Game.player.getX() / 16;
		int yPlayer = Game.player.getY() / 16;
		minimapPixels[xPlayer + (yPlayer * World.getWidth())] = 0xFF0026FF;
		/* NPC */
		
		for(Entity en : Entity.entities) {
			if(en instanceof Npc) {
				minimapPixels[(en.getX()/16) + ((en.getY()/16) * World.getWidth())] = 0xFF9352B6;
			}
		}
		/* ENEMY */
		
	    for (Enemy en : Enemy.enemies) {
	        int xENE = en.getX() / 16;
	        int yENE = en.getY() / 16;
	
	        minimapPixels[xENE + (yENE * World.getWidth())] = 0xFFFF0000;
	    }
		/* Boss */
	    
		for(int i = 0; i < BigEnemy.bosses.size(); i++) {
			BigEnemy en = BigEnemy.bosses.get(i);
			
			int xBENE = en.getX() / 16;
			int yBENE = en.getY() / 16;
			
			minimapPixels[xBENE + (yBENE * World.getWidth())] = 0xFFFF7FB6;
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRoundRect(x-4, y-4, width+8, height+8, 12, 12);
		g.drawImage(minimap, x, y, width, height, null);
	}
	
	public static int getX() {
		return x;
	}
	public static int getY() {
		return y;
	}
	public static int getWidth() {
		return width;
	}
	public static int getHeight() {
		return height;
	}
}
