package com.notestudios.shop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;
import com.notestudios.objects.Coin;
import com.notestudios.util.Button;
import com.notestudios.util.Popup;

public class Item extends Button {
	
	public static List<Item> shopItems = new ArrayList<>();
	
	protected int price;
	protected String name;
	private BufferedImage icon;
	
	public Item(int x, int y, int width, int height, String name, BufferedImage icon, int price) {
		super(x, y, width, height, null);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.price = price;
		this.icon = icon;
		this.name = name;
	}
	
	private String errorTitle = "Warning", errorMessage = "You don't have the sufficient coins to buy this item.";
	
	public void onPurchase() {
		new Popup("Error", "Item is not registered or unavailable");
	}
	
	@Override
	public void functions() {
		if(MainMenu.english) {
			errorTitle = "Not enough coins";
			errorMessage = "You don't have the sufficient amount of coins to buy this item. You need "+
					(price-Game.playerCoins)+" more coins to buy it.";
		} else {
			errorTitle = "Moedas Insuficientes";
			errorMessage = "Você não tem moedas suficientes para comprar esse item. Você precisa de mais "+
					(price-Game.playerCoins)+" moedas para comprá-lo"; 
		}
		if(clicked) {
			clicked = false;
			if(Game.playerCoins >= this.price) {
				this.onPurchase();
			} else {
				new Popup(errorTitle, Arrays.asList(errorMessage.substring(0, errorMessage.length()/2), errorMessage.substring(errorMessage.length()/2, errorMessage.length())));
			}
		}
		
		if(this.selectAni) {
			if(selected) {
				if(addictiveSizeAnimation > 0) addictiveSizeAnimation--;
				if(selectAlpha < 255) {
					selectAlpha+=51;
					wasSelected = true;
				}
			} else { 
				if(wasSelected && selectAlpha <= 255 && selectAlpha > 0) { 
					selectAlpha-=51;
				}
				if(addictiveSizeAnimation < maxAddictiveSizeAnimation) addictiveSizeAnimation++;
			}
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);
		
		g.setColor(customBackColor);
		g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), this.arc, this.arc);
		
		int selectAnimationPos;
		int selectSizeAnimation;
		if(selected || selectAlpha > 0) {
			selectAnimationPos = (addictiveSizeAnimation/2); selectSizeAnimation = addictiveSizeAnimation;
			g.setColor(new Color(255, 255, 255, selectAlpha));
		} else {
			selectAnimationPos = 0; selectSizeAnimation = 0;
			g.setColor(new Color(50, 50, 50, generalAlpha));
		}
		g.drawRoundRect(getX()-selectAnimationPos, getY()-selectAnimationPos, getWidth()+selectSizeAnimation, getHeight()+selectSizeAnimation, this.arc, this.arc);
		
		if(!unavailable) 
			g.setColor(this.customTextColor);
		else 
			g.setColor(Color.gray);
		
		g.setFont(MainMenu.DialogFont);
		
		g.drawImage(icon, getX()+(getWidth()/2)-((icon.getWidth()*4)/2), getY()+1, icon.getWidth()*4, icon.getHeight()*4, null);
		g.drawString(this.name, getX()+(getWidth()/2)-(g.getFontMetrics().stringWidth(this.name)/2), getY()+(icon.getHeight()*5));
		
		int spaceWidth = (getWidth()-24), spaceHeight = (getHeight()/4);
		g.setColor(Color.darkGray);
		g.drawRoundRect(getX()+(getWidth()/2)-(spaceWidth/2), getY()+getHeight()-spaceHeight-12, spaceWidth, spaceHeight, 20, 20);
		
		int coinX = getX()+(getWidth()/2)-(spaceWidth/2)+((Coin.COIN_EN.getWidth()*3)/2), 
				coinY = getY()+getHeight()-spaceHeight-18, coinWidth = Coin.COIN_EN.getWidth()*3, 
				coinHeight = Coin.COIN_EN.getHeight()*3;
		
		g.setFont(MainMenu.UIFont);
		g.setColor(Color.white);
		g.drawString(Integer.toString(price), coinX+coinWidth-5, 
				getY()+getHeight()-(spaceWidth/2)+45);
		
		g.drawImage(Coin.COIN_EN, coinX, coinY, coinWidth, coinHeight, null);
	}
}
