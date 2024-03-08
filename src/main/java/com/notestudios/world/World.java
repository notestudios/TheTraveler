package com.notestudios.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.notestudios.entities.BigEnemy;
import com.notestudios.entities.Bullets;
import com.notestudios.entities.Enemy;
import com.notestudios.entities.Entity;
import com.notestudios.entities.Particle;
import com.notestudios.entities.Player;
import com.notestudios.graphics.Minimap;
import com.notestudios.main.Game;
import com.notestudios.main.Window;
import com.notestudios.objects.Ammo;
import com.notestudios.objects.Bush;
import com.notestudios.objects.Coin;
import com.notestudios.objects.Flower;
import com.notestudios.objects.InteractibleObjects;
import com.notestudios.objects.LifePack;
import com.notestudios.objects.Weapon;

public class World {
	public static Tile[] tiles;
	private static int WIDTH, HEIGHT;
	public final static int tileSize = 16;
	public static String mapsFolder = "/maps/";
	public BufferedImage defaultTileFloor;
	public BufferedImage map;
	public int[] pixels;
	public static int curLevel = 1, maxLevel = 14;
	public File saveFile = new File("game.save");
	
	public World(String path) {
		try {
			map = ImageIO.read(getClass().getResource(path));
			pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[map.getWidth() * map.getHeight()];
			World.WIDTH = map.getWidth();
			World.HEIGHT = map.getHeight();
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * World.WIDTH)] = new FloorTile(xx * tileSize, yy * tileSize, Tile.TILE_FLOOR);
					switch(pixelAtual) {
						case 0xFF000000: //Floor
							if(Game.getRandom().nextInt(3) == 0) {
								defaultTileFloor = Tile.TILE_FLOOR;
							} else if(Game.getRandom().nextInt(3) == 1) {
								defaultTileFloor = Tile.TILE_FLOOR2;
							} else if(Game.getRandom().nextInt(3) == 2) {
								defaultTileFloor = Tile.TILE_FLOOR3;
							} else if(Game.getRandom().nextInt(3) == 3) {
								defaultTileFloor = Tile.TILE_FLOOR4;
							}
							tiles[xx + (yy * WIDTH)] = new FloorTile(xx * tileSize, yy * tileSize, defaultTileFloor);
						break;
						case 0xFFFFFFFF: //Wall
							tiles[xx + (yy * WIDTH)] = new WallTile(xx * tileSize, yy * tileSize, Tile.TILE_WALL);
						break;
						case 0xFFFF00DC: //Bush
							InteractibleObjects.objects.add(new Bush(xx * tileSize, yy * tileSize, 48, 16, Tile.BUSH_TILE));
						break;
						case 0xFFFF0000: //Enemy
							Enemy en = new Enemy(xx * tileSize, yy * tileSize, 16, 16, Enemy.ENEMY_EN);
							Entity.entities.add(en);
							Enemy.enemies.add(en);
						break;
						case 0xFFFF7FB6: //BigEnemy (Boss)
							BigEnemy bigEn = new BigEnemy(xx * tileSize, yy * tileSize, 32, 32, BigEnemy.bigEnemyIdle);
							Entity.entities.add(bigEn);
							BigEnemy.bosses.add(bigEn);
							bigEn.setX(xx*tileSize);
							bigEn.setY(yy*tileSize);
						break;
						case 0xFF0026FF: //Player
							if(Game.player == null) 
								Player.init();
							
							Game.player.setX(xx * tileSize);
							Game.player.setY(yy * tileSize);
						break;
						case 0xFFFF6A00: //Weapon
							InteractibleObjects.objects.add(new Weapon(xx * tileSize, yy * tileSize, 16, 16, Weapon.WEAPON_EN));
						break;
						case 0xFFFF6868: //Life Pack
							InteractibleObjects.objects.add(new LifePack(xx * tileSize, yy * tileSize, 16, 16, LifePack.LIFEPACK_EN));
						break;
						case 0xFFFFD800: //Ammo
							InteractibleObjects.objects.add(new Ammo(xx * tileSize, yy * tileSize, 16, 16, Ammo.BULLET_EN));
						break;
						case 0xFFB6FF00: //Coin
							InteractibleObjects.objects.add(new Coin(xx * tileSize, yy * tileSize, 16, 16, Coin.COIN_EN));
						break;
						case 0xFFB23535: //Flower
							InteractibleObjects.objects.add(new Flower(xx * tileSize, yy * tileSize, 16, 16, Tile.FLOWER_TILE));
						break;
						case 0xFF7F92FF: //Npc
							Game.npc.setX(xx*tileSize);
							Game.npc.setY(yy*tileSize);
						break;
					}
				}
			}
		} catch (IOException e) {
			System.err.println("IOException occurred at the "+getClass().getSimpleName()+" constructor:");
			e.printStackTrace();
		}
	}

	public void generateParticles(int amount, int x, int y, int w, int h, Color color) {
		for (int i = 0; i < amount; i++) {
			Entity.entities.add(new Particle(x, y, w, h, color, null));
		}
	}

	public boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / tileSize;
		int y1 = ynext / tileSize;

		int x2 = (xnext + width - 1) / tileSize;
		int y2 = ynext / tileSize;

		int x3 = xnext / tileSize;
		int y3 = (ynext + height - 1) / tileSize;

		int x4 = (xnext + width - 1) / tileSize;
		int y4 = (ynext + height - 1) / tileSize;
		if (!(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile || (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile))) {
			return true;
		}
		return false;
	}

	public boolean isFree(int xnext, int ynext) {
		int x1 = xnext / tileSize;
		int y1 = ynext / tileSize;

		int x2 = (xnext + tileSize - 1) / tileSize;
		int y2 = ynext / tileSize;

		int x3 = xnext / tileSize;
		int y3 = (ynext + tileSize - 1) / tileSize;

		int x4 = (xnext + tileSize - 1) / tileSize;
		int y4 = (ynext + tileSize - 1) / tileSize;
		if (!(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile || (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile))) {
			return true;
		}
		return false;
	}
	
	public void clearObjects() {
		Entity.entities = new ArrayList<>();
		Enemy.enemies = new ArrayList<>();
		BigEnemy.bosses = new ArrayList<>();
		InteractibleObjects.objects = new ArrayList<>();
		Bullets.bullets = new ArrayList<>();
	}

	public void loadLevel(String levelName) {
		System.out.println("Traveling to: "+levelName);
		clearObjects();
		Player.init();
		BigEnemy.bossfight = false;
		Game.world = new World(mapsFolder + levelName + ".png");
		Minimap.initialize();
		Game.gameState = "Normal";
		return;
	}
	
	public void resetLevel() {
		System.out.println("Resetting current level: "+curLevel);
		clearObjects();
		Player.init();
		Game.world = new World(mapsFolder+"level"+curLevel+".png");
		Minimap.initialize();
		Game.gameState = "Normal";
		return;
	}
	
	public void tick() {
		if(Enemy.enemies.size() == 0 && BigEnemy.bosses.size() == 0) {
			curLevel++;
			if(curLevel > maxLevel) {
				curLevel = 1;
			}
			loadLevel("level" + curLevel);
		}
	}

	public void render(Graphics g) {
		int startX = Camera.x >> Window.SCALE;
		int startY = Camera.y >> Window.SCALE;
		int finalX = startX + (Window.WIDTH >> Window.SCALE);
		int finalY = startY + (Window.HEIGHT >> Window.SCALE);
		
		for(int xx = startX; xx <= finalX; xx++) {
			for(int yy = startY; yy <= finalY; yy++) {
				if (xx < 0 || yy < 0 || xx >= World.WIDTH || yy >= World.HEIGHT) 
					continue;
				Tile tile = tiles[xx + (yy * World.WIDTH)];
				tile.render(g);
			}
		}
		
	}
	
	public void applySave(String str) {
		String[] spl = str.split("/");
		for (int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch (spl2[0]) {
				case "level":
					Game.world.loadLevel("level" + spl2[1]);
					Game.gameState = "Normal";
				break;
				case "px":
					Game.player.setX(Integer.parseInt(spl2[1]));
				case "py":
					Game.player.setY(Integer.parseInt(spl2[1]));
				case "life":
					Game.player.life = Integer.parseInt(spl2[1]);
				case "coins":
					Game.playerCoins = Integer.parseInt(spl2[1]);
				case "ammo":
					Game.player.ammo = Integer.parseInt(spl2[1]);
			}
		}
	}

	public String loadGame(int encode) {
		File saveFile = new File("game.save");
		String line = "";
		if (saveFile.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader(saveFile));
				try {
					while ((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for (int i = 0; i < val.length; i++) {
							val[i] -= encode;
							trans[1] += val[i];
						}
						line += trans[0];
						line += ":";
						line += trans[1];
						line += "/";
					}
				} catch (IOException e) {
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return line;
	}

	public void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(saveFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";

			char[] value = Integer.toString(val2[i]).toCharArray();

			for (int n = 0; n < value.length; n++) {
				value[n] += encode;
				current += value[n];
			}
			try {
				writer.write(current);
				if (i < val1.length - 1)
					writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int getWidth() {
		return WIDTH;
	}
	
	public static int getHeight() {
		return HEIGHT;
	}
}