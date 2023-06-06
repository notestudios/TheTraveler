package com.notestudios.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.notestudios.entities.Entity;
import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.main.Sound;

public class Shop {
	
	public static boolean medKitSelect = false;
	public static boolean bulletPackSelect = false;
	public static boolean shopEnter = false;
	public static boolean ShopBackSelect = false;
	public static int medKitPrice = 5;
	public static int bulletPackPrice = 3;
	
	public void tick() {
		
		if(Shop.medKitSelect && Shop.shopEnter) {
			Shop.shopEnter = false;
			if(Game.playerCoins >= Shop.medKitPrice && (int)Game.player.life < Game.player.maxLife) {
				Game.playerCoins-=Shop.medKitPrice;
				Game.player.life+=25;
				if(Game.player.life > Game.player.maxLife) {
					Game.player.life = Game.player.maxLife;
				}
			}
		} if(Shop.bulletPackSelect && Shop.shopEnter) {
			Shop.shopEnter = false;
			if(Game.playerCoins >= Shop.bulletPackPrice && Game.player.ammo < Game.player.maxAmmo) {
				Game.playerCoins-=Shop.bulletPackPrice;
				Game.player.ammo+=64;
				if(Game.player.ammo > Game.player.maxAmmo) {
					Game.player.ammo = Game.player.maxAmmo;
				}
			}
		}
		
		if(ShopBackSelect && shopEnter) {
			shopEnter = false;
			Game.f1Shop = false;
			if(!Game.mute) {
				Sound.backMenu.play();
			}
			Game.gameState = "Normal";
		}
		
		if (Game.ESC) {
			Game.ESC = false;
			Game.f1Shop = false;
			Game.gameState = "Normal";
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(30,30,31));
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		if(Game.graphics == 2) {
			g.drawImage(Game.bigBackground, -55*Game.SCALE, -25*Game.SCALE, 112*Game.SCALE, 48*Game.SCALE, null);
			g.drawImage(Game.defaultLargeOptionBg, -99*Game.SCALE, 16*Game.SCALE, 144*Game.SCALE, 16*Game.SCALE, null); //back button
		}
		
		g.setFont(MainMenu.TheFont);
		g.setColor(Color.white);
		if(MainMenu.english)
			g.drawString("Shop", 55, 48);
		else if(MainMenu.portugues)
			g.drawString("Loja", 60, 48);

		g.setFont(MainMenu.aFont);
		g.setColor(Color.white);//shopop
		g.drawString("< Back", 30, 106);
		g.setFont(new Font("Segoe UI", Font.BOLD, 12));
		g.drawString("(ESC)", 125, 90);
		
		g.setColor(Color.BLACK);
		g.fillRoundRect(322 - 4, UI.posY * Game.SCALE - 3, 72 * Game.SCALE, 10 * Game.SCALE, 15, 15);

		g.setColor(new Color(200, 0, 0));
		g.fillRoundRect(322, UI.posY * Game.SCALE, 70 * Game.SCALE, 8 * Game.SCALE, 15, 15);

		g.setColor(new Color(0, 195, 13));
		g.fillRoundRect(322, UI.posY * Game.SCALE,
				(int) ((Game.player.life / Game.player.maxLife) * 70) * Game.SCALE, 8 * Game.SCALE, 15, 15);
		
		g.drawImage(Entity.BULLET_EN, 85*Game.SCALE, 11*Game.SCALE, 16*Game.SCALE, 16*Game.SCALE, null);
		g.drawImage(Entity.COIN_EN, 124*Game.SCALE, 12*Game.SCALE, 16*Game.SCALE, 16*Game.SCALE, null);
		
		g.setFont(MainMenu.aFont);
		g.setColor(Color.white);
		g.drawString(Game.player.ammo + "", 420/* 420 */, 92/* 43 */);
		g.drawString(Game.playerCoins + "", 570/* 570 */, 92/* 43 */);
		
		if(Game.graphics == 2) {
			g.drawImage(Entity.ShopItemSelect, 210, 220, 48*5+5, 48*5+2, null);
			g.drawImage(Entity.ShopItemSelect, 480, 220, 48*5+5, 48*5+2, null);
		}
		
		g.drawImage(Entity.LIFEPACK_EN, 285, 270, 16*6, 16*6, null);
		g.drawImage(Entity.BULLET_EN, 555, 270, 16*6, 16*6, null);
		
		g.drawImage(Entity.COIN_EN, 275, 375, 16*Game.SCALE, 16*Game.SCALE, null);
		g.drawImage(Entity.COIN_EN, 545, 375, 16*Game.SCALE, 16*Game.SCALE, null);
		
		g.setFont(MainMenu.UIFont);
		g.setColor(Color.white);
		g.drawString(""+medKitPrice, 345, 420);
		
		g.setFont(MainMenu.UIFont);
		g.setColor(Color.white);
		g.drawString(""+bulletPackPrice, 615, 420);
		
		if(medKitSelect) {
			g.setColor(Color.white);
			g.drawRoundRect(220, 235, 48*4+32, 48*4+30, 16, 16);
		} if(bulletPackSelect) {
			g.setColor(Color.white);
			g.drawRoundRect(490, 235, 48*4+32, 48*4+30, 16, 16);
		}
		
		if(ShopBackSelect) {
			g.setColor(Color.white);
			g.drawRoundRect(-32, 68, 50 * Game.SCALE, 14 * Game.SCALE, 14, 14);
		}
		
		g.setColor(Color.white);
		g.setFont(Game.newFont);
		if (Game.player.life >= 100)
			g.drawString((int) Game.player.life + "/" + (int) Game.player.maxLife, 414/* 120 */, 44/* 44 */);
		else if (Game.player.life < 100)
			g.drawString((int) Game.player.life + "/" + (int) Game.player.maxLife, 416/* 125 */, 44/* 44 */);
	}
	
	
}
