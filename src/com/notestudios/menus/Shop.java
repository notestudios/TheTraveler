package com.notestudios.menus;

import java.awt.Color;
import java.awt.Graphics2D;

import com.notestudios.entities.Entity;
import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.main.Window;
import com.notestudios.util.Button;
import com.notestudios.util.Sound;

public class Shop {
	
	public static boolean medKitSelect = false;
	public static boolean bulletPackSelect = false;
	public static boolean shopEnter = false;
	public static int medKitPrice = 5;
	public static int bulletPackPrice = 3;
	
	public Button backButton = new Button(10, 50, 140, 50, "<  Shop") {
		@Override
		public void functions() {
			if(MainMenu.portuguese) text = "<  Loja"; else text = "<  Shop";
			customFont = MainMenu.aFont;
			if(clicked) {
				clicked = false;
				UI.doTransition = true;
				if(!Game.mute) Sound.backMenu.play();
				Game.gameState = "Normal";
				Game.f1Shop = false;
				quit();
			}
		}
	};
	
	public Shop() {
		Button.buttons.add(backButton);
	}
	
	public void tick() {
		if(Shop.medKitSelect && Shop.shopEnter) {
			Shop.shopEnter = false;
			if(Game.playerCoins >= Shop.medKitPrice && (int)Game.player.life < Game.player.maxLife) {
				Game.playerCoins-=Shop.medKitPrice;
				Game.player.life+=25;
				if(Game.player.life > Game.player.maxLife) 
					Game.player.life = Game.player.maxLife;
			}
		} if(Shop.bulletPackSelect && Shop.shopEnter) {
			Shop.shopEnter = false;
			if(Game.playerCoins >= Shop.bulletPackPrice && Game.player.ammo < Game.player.maxAmmo) {
				Game.playerCoins-=Shop.bulletPackPrice;
				Game.player.ammo+=64;
				if(Game.player.ammo > Game.player.maxAmmo) 
					Game.player.ammo = Game.player.maxAmmo;
			}
		}
		
		backButton.functions();
		
		if(Game.ESC) {
			Game.ESC = false;
			Game.f1Shop = false;
			Game.gameState = "Normal";
		}
		
	}
	
	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);
		g.setColor(new Color(39, 39, 39, 220));
		g.fillRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
		g.setColor(Color.white);
		g.drawRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
		
		if(Game.graphicsQuality == 2) {
			g.drawImage(Entity.ShopItemSelect, 210, 220, 48*5+5, 48*5+2, null);
			g.drawImage(Entity.ShopItemSelect, 480, 220, 48*5+5, 48*5+2, null);
		}
		
		g.drawImage(Entity.LIFEPACK_EN, 285, 270, 16*6, 16*6, null);
		g.drawImage(Entity.BULLET_EN, 555, 270, 16*6, 16*6, null);
		
		g.drawImage(Entity.COIN_EN, 275, 375, 16*Window.SCALE, 16*Window.SCALE, null);
		g.drawImage(Entity.COIN_EN, 545, 375, 16*Window.SCALE, 16*Window.SCALE, null);
		
		g.setFont(MainMenu.UIFont);
		g.setColor(Color.white);
		g.drawString(Integer.toString(medKitPrice), 345, 420);
		
		g.setFont(MainMenu.UIFont);
		g.setColor(Color.white);
		g.drawString(Integer.toString(bulletPackPrice), 615, 420);
		
		if(medKitSelect) {
			g.setColor(Color.white);
			g.drawRoundRect(220, 235, 48*4+32, 48*4+30, 16, 16);
		} if(bulletPackSelect) {
			g.setColor(Color.white);
			g.drawRoundRect(490, 235, 48*4+32, 48*4+30, 16, 16);
		}
		
		backButton.render(g);
	}
	
	public void quit() {
		Game.ui.shop = null;
		Button.buttons.remove(this.backButton);
	}
}
