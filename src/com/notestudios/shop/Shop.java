package com.notestudios.shop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import com.notestudios.graphics.Spritesheets;
import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;
import com.notestudios.objects.Ammo;
import com.notestudios.objects.CollectibleBomb;
import com.notestudios.objects.LifePack;
import com.notestudios.util.Button;
import com.notestudios.util.Popup;
import com.notestudios.util.Sound;

public class Shop {
	
	private Item medkitItem = new Item(100, 100, 150, 150, "MedKit", LifePack.LIFEPACK_EN, 5) {
		private int hpAmount = 25;
		@Override
		public void onPurchase() {
			if(Game.player.life < Game.player.maxLife) {
				Game.playerCoins-=price;
				Game.player.life+=hpAmount;
				if(Game.player.life > Game.player.maxLife) Game.player.life = Game.player.maxLife;
			} else {
				if(MainMenu.portuguese) { new Popup("A vida está cheia", "Sua HP/Vida já está cheia, suas moedas não foram gastas."); }
				else { new Popup("HP is already full", "Your HP/Life is already full, your coins haven't been used."); }
			}
		}
	};
	
	private Item ammoItem = new Item(100, 100, 150, 150, "Ammo", Ammo.BULLET_EN, 3) {
		private int ammoAmount = 64;
		@Override
		public void onPurchase() {
			if(Game.player.ammo < Game.player.maxAmmo) {
				Game.playerCoins-=price;
				Game.player.ammo+=ammoAmount;
				if(Game.player.ammo > Game.player.maxAmmo) Game.player.ammo = Game.player.maxAmmo;
			} else {
				if(MainMenu.portuguese) { new Popup("Munições estão cheias", "Sua munição já está cheia, suas moedas não foram gastas."); }
				else { new Popup("Ammo is already full", "Your ammo is already full, your coins haven't been used."); }
			}
		}
	};
	
	private Item bombItem = new Item(100, 100, 150, 150, "Bomb", CollectibleBomb.bomb, 6) {
		@Override
		public void onPurchase() {
			if(Game.player.bombs < Game.player.maxBombs) {
				Game.playerCoins-=price;
				Game.player.bombs+=1;
			} else {
				if(MainMenu.portuguese) { new Popup("Quantidade máxima atingida", Arrays.asList("Bomba atingiu sua quantidade máxima("+Game.player.maxBombs+").", "Suas moedas não foram gastas.")); }
				else { new Popup("Bomb has exceeded limit", Arrays.asList("Bomb has reached it's maximum capacity.", "Your coins haven't been used.")); }
			}
		}
	};
	
	private List<Item> items = Arrays.asList(medkitItem, ammoItem, bombItem);
	
	private ItemList itmList = new ItemList(5, 150+10, Game.window.getWidth() - 10, Game.window.getHeight()-(155+10), items);
	
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

	public static BufferedImage UIShop = Spritesheets.ui.getSprite(112, 80, 16, 16);
	
	public Shop() {
		Button.buttons.add(backButton);
		Button.buttons.addAll(items);
	}
	
	public void tick() {
		backButton.functions();
		itmList.tick();
		
		if(Game.ESC) {
			Game.ESC = false;
			backButton.clicked = true;
		}
	}
	
	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);
		
		g.setColor(new Color(39, 39, 39, 220));
		g.fillRoundRect(5, 5, Game.window.getWidth() - 10, 150, 30, 30);
		g.setColor(Color.white);
		g.drawRoundRect(5, 5, Game.window.getWidth() - 10, 150, 30, 30);
		
		itmList.render(g);
		
		backButton.render(g);
	}
	
	public void quit() {
		Game.ui.shop = null;
		Button.buttons.remove(this.backButton);
		Button.buttons.removeAll(this.items);
	}
}
