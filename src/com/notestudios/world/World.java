package com.notestudios.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.notestudios.entities.*;
import com.notestudios.graphics.Spritesheet;
import com.notestudios.main.*;
import com.notestudios.objects.Bush;
import com.notestudios.objects.Flower;

public class World {
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	public BufferedImage defaultTileFloor;
	public BufferedImage map;
	public int[] pixels;

	public World(String path) {
		try {
			map = ImageIO.read(getClass().getResource(path));
			pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for (int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];

					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);

					if (pixelAtual == 0xFF000000) {
						// Chão
						if(Game.rand.nextInt(3) == 0) {
							defaultTileFloor = Tile.TILE_FLOOR;
						} else if(Game.rand.nextInt(3) == 1) {
							defaultTileFloor = Tile.TILE_FLOOR2;
						} else if(Game.rand.nextInt(3) == 2) {
							defaultTileFloor = Tile.TILE_FLOOR3;
						} else if(Game.rand.nextInt(3) == 3) {
							defaultTileFloor = Tile.TILE_FLOOR4;
						}
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, defaultTileFloor);
					}
					if (pixelAtual == 0xFFFFFFFF) {
						// Parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);

					}
					if (pixelAtual == 0xFFFF00DC) {
						// Arbusto
						Game.entities.add(new Bush(xx * 16, yy * 16, 48, 16, Tile.BUSH_TILE));
					}
					if (pixelAtual == 0xFFFF0000) {
						// Enemy
						Enemy en = new Enemy(xx * 16, yy * 16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					}
					if (pixelAtual == 0xFFFF7FB6) {
						// BigEnemy (Boss)
						BigEnemy bigEn = new BigEnemy(xx * 16, yy * 16, 32, 32, Entity.BigEnemy);
						Game.entities.add(bigEn);
						Game.bosses.add(bigEn);
						bigEn.setX(xx*16);
						bigEn.setY(yy*16);
					}
					if (pixelAtual == 0xFF0026FF) {
						// Player
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
					}
					if (pixelAtual == 0xFFFF6A00) {
						// Weapon
						Game.entities.add(new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPON_EN));
					}
					if (pixelAtual == 0xFFFF6868) {
						// Life Pack
						Game.entities.add(new LifePack(xx * 16, yy * 16, 16, 16, Entity.LIFEPACK_EN));
					}
					if (pixelAtual == 0xFFFFD800) {
						// Ammo
						Game.entities.add(new Ammo(xx * 16, yy * 16, 16, 16, Entity.BULLET_EN));
					}
					if (pixelAtual == 0xFFB6FF00) {
						// Coin
						Game.entities.add(new Coin(xx * 16, yy * 16, 16, 16, Entity.COIN_EN));
					}
					if (pixelAtual == 0xFFB23535) {
						// Flower
						Game.entities.add(new Flower(xx * 16, yy * 16, 16, 16, Tile.FLOWER_TILE));
					}
					if (pixelAtual == 0xFF7F92FF) {
						// NPC
						Game.npc.setX(xx*16);
						Game.npc.setY(yy*16);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void generateParticles(int amount, int x, int y, int w, int h, Color color) {
		for (int i = 0; i < amount; i++) {
			Game.entities.add(new Particle(x, y, w, h, color, null));
		}
	}

	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + width - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + height - 1) / TILE_SIZE;

		int x4 = (xnext + width - 1) / TILE_SIZE;
		int y4 = (ynext + height - 1) / TILE_SIZE;
		if (!(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile || (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile))) {
			return true;
		}
		return false;
	}

	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;
		if (!(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile || (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile))) {
			return true;
		}
		return false;
	}

	public static void restartGame(String level) {
		Game.enemies.clear();
		Game.bullets.clear();
		Game.entities.clear();
		Game.bosses.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.bosses = new ArrayList<BigEnemy>();
		Game.player = new Player(0, 0, 16, 16, Spritesheet.spritesheetPlayer.getSubimage(0, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.bossFight = false;
		Game.world = new World("/levels/" + level);
		return;
	}

	public void render(Graphics g) {
		int startX = Camera.x >> 4;
		int startY = Camera.y >> 4;
		
		/*if(!Game.fullScreen) {*/
			int finalX = startX + (Game.WIDTH >> 4);
			int finalY = startY + (Game.HEIGHT >> 4);
			
			for (int xx = startX; xx <= finalX; xx++) {
				for (int yy = startY; yy <= finalY; yy++) {
					if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
						continue;

					Tile tile = tiles[xx + (yy * WIDTH)];
					tile.render(g);
				}
			}
		/*} else {
			int finalX = startX + (Toolkit.getDefaultToolkit().getScreenSize().width >> 4);
			int finalY = startY + (Toolkit.getDefaultToolkit().getScreenSize().height >> 4);
			
			for (int xx = startX; xx <= finalX; xx++) {
				for (int yy = startY; yy <= finalY; yy++) {
					if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
						continue;

					Tile tile = tiles[xx + (yy * WIDTH)];
					tile.render(g);
				}
			}
		}*/
	}

}

/*
 * Mecanismo de mapa Rand�mico
 * 
 * Game.player.setX(224); Game.player.setY(144);
 * 
 * WIDTH = 40; HEIGHT = 40;
 * 
 * tiles = new Tile[WIDTH*HEIGHT];
 * 
 * for(int xx = 0; xx < WIDTH; xx++) { for(int yy = 0; yy < HEIGHT; yy++) {
 * //tiles[xx+yy*WIDTH] = new Tile(xx*16, yy*16, Tile.TILE_WALL); tiles[xx + (yy
 * * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL); } }
 * 
 * int dir = 0; int xx = 0; int yy = 0;
 * 
 * for(int i = 0; i < 400; i++) { if(dir == 0) { //direita if(xx < WIDTH) {
 * xx++; } }else if(dir == 1) { //esquerda if(xx > 0) { xx--; } }else if(dir ==
 * 2) { //baixo if(yy < HEIGHT) { yy++; } }else if(dir == 3) { //cima if(yy > 0)
 * { yy--; } }
 * 
 * if(Game.rand.nextInt(100) < 30) { dir = Game.rand.nextInt(4); }
 * 
 * //tiles[xx+yy*WIDTH] = new Tile(xx*16, yy*16, Tile.TILE_FLOOR); tiles[xx +
 * (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR); }
 */
/*
 * if(Game.rand.nextInt(100) < 30) { beach = false; }else { beach = true; }
 */