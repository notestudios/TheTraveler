package com.notestudios.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;

public final class Popup {
	
	public static List<Popup> popups = new ArrayList<>();
	
	private int x, y;
	private int width = 80, height = 60;
	private String biggerString;
	private String title;
	private List<String> textList = new ArrayList<>();
	private String textString;
	private boolean useListInstead = false;
	private int backOpacity, maxBackOpacity = 190;
	public int opacity, maxOpacity = 255;
	private int defaultAddictiveY = 150, addictiveY = defaultAddictiveY;
	private double defaultScrollSpd = 20, scrollSpd = defaultScrollSpd;
	private boolean createAni, removeAni;
	
	public Button closeButton = new Button(x, y, width, 35, "Ok") {
		@Override
		public void functions() {
			generalAlpha = opacity;
			text = "Ok";
			textOffsetY = 10;
			customFont = MainMenu.DialogFont;
			if(clicked) {
				clicked = false;
				if(!Game.mute) Sound.backMenu.play();
				removeAni = true;
				selectAlpha = 0;
				selectAni = false;
			}
		}
	};
	
	public Popup(String title, List<String> text) {
		this.title = title;
		this.textList = text;
		this.useListInstead = true;
		Game.ui.layer+=1;
		closeButton.layer = 1;
		popups.add(this);
		Button.buttons.add(this.closeButton);
		biggerString = text.stream().max(Comparator.comparingInt(String::length)).get();
		createAni = true;
	}
	
	public Popup(String title, String text) {
		this.title = title;
		this.textString = text;
		Game.ui.layer+=1;
		closeButton.layer = 1;
		popups.add(this);
		Button.buttons.add(this.closeButton);
		textList.add(title); textList.add(text);
		biggerString = this.textList.stream().max(Comparator.comparingInt(String::length)).get();
		textList.remove(title); textList.remove(text);
		createAni = true;
	}
	
	public void tick() {
		closeButton.functions();
		
		if(createAni && !removeAni) {
			if(backOpacity < maxBackOpacity) 
				backOpacity+=5;
			if(opacity < maxOpacity)  
				opacity+=51;
			if(backOpacity == maxBackOpacity && opacity == maxOpacity) 
				createAni = false;
			
			if(addictiveY > 0) {
				addictiveY-=(int)scrollSpd;
				if(scrollSpd > 1)
					scrollSpd-=1.5;
			}
		} if(!createAni) 
			scrollSpd = defaultScrollSpd;
		
		if(removeAni) {
			if(backOpacity > 0) 
				backOpacity-=5;
			if(opacity > 0) 
				opacity-=51;
			if(backOpacity == 0 && opacity == 0) { 
				removeAni = false;
				removePopup();
			} if(addictiveY < defaultAddictiveY) {
				addictiveY+=(int)scrollSpd;
				if(scrollSpd > 1)
					scrollSpd-=1.5;
			}
		}
	}
	
	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);
		
		g.setColor(new Color(0, 0, 0, backOpacity));
		g.fillRect(0, 0, Game.window.getWidth(), Game.window.getHeight());
		
		g.setFont(MainMenu.aFont);
		g.setColor(new Color(255, 255, 255, opacity));
		if(g.getFontMetrics().stringWidth(title) > g.getFontMetrics().stringWidth(biggerString)) 
			width = g.getFontMetrics().stringWidth(title)+12 + 30;
		else 
			width = g.getFontMetrics().stringWidth(biggerString)+12 + 30;
		
		if(!useListInstead) {
			height = g.getFontMetrics().getHeight()+110;
		} else {
			height = g.getFontMetrics().getHeight()*(textList.size()) + 100;
		}
		
		x = (Game.window.getWidth()/2) - (width/2);
		y = (Game.window.getHeight()/2) - (height/2);
		closeButton.width = width - 12;
		closeButton.setX(x+6);
		closeButton.setY(((y+height-6)-closeButton.height)+addictiveY);
		
		g.setColor(new Color(30, 30, 30, opacity));
		g.fillRoundRect(x, y+addictiveY, width, height, 32, 32);
		g.setColor(new Color(255, 255, 255, opacity));
		g.drawRoundRect(x, y+addictiveY, width, height, 32, 32);
		
		g.drawString(title, x+(width/2)-(g.getFontMetrics().stringWidth(title)/2),
				y+addictiveY+g.getFontMetrics().getHeight()-6);
		
		g.drawLine(x, y+addictiveY+g.getFontMetrics().getHeight()+6, x+width, y+addictiveY+g.getFontMetrics().getHeight()+6);
		
		g.setFont(Game.menuFont2);
		g.setColor(new Color(255, 255, 255, opacity));
		if(useListInstead) {
			for(int i = 0; i < textList.size(); i++) 
				g.drawString(textList.get(i), x+(width/2)-(g.getFontMetrics().stringWidth(biggerString)/2), 
						y+(g.getFontMetrics().getHeight()*2)+addictiveY+(i*g.getFontMetrics().getHeight())+32);
		} else {
			g.drawString(textString, x+(width/2)-(g.getFontMetrics().stringWidth(biggerString)/2), 
					y+(g.getFontMetrics().getHeight()*2)+addictiveY+32);
		}
		closeButton.render(g);
	}
	
	public void removePopup() {
		if(popups.contains(this))
			popups.remove(this);
		
		Game.ui.layer-=1;
		Button.buttons.remove(this.closeButton);
	}
}
