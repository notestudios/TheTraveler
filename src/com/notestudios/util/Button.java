package com.notestudios.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.main.Window;
import com.notestudios.menus.MainMenu;

public class Button {
	
	public static List<Button> buttons = new ArrayList<>();
	
	protected BufferedImage buttonBgDesign;
	
	protected double x;
	protected double y;
	protected int width;//optional
	protected int height;//optional
	protected String text = "Button"+buttons.size()+"Text";
	public int layer = 0;
	
	protected int timeSelected = 0;
	protected int maxTimeSelected = 60*2;
	
	public boolean selected = false;
	public boolean clicked = false;
	public boolean unavailable = false;
	public boolean wasSelected = false;
	public boolean wasClicked = false;
	public boolean selectAni = true;
	
	protected String customFontString = "Arial";
	protected Font customFont = Game.menuFont2;
	protected int fontSize = 20;
	protected int arc = 30;
	
	protected int generalAlpha = 255;
	protected Color customBackColor = new Color(40, 40, 40, generalAlpha);
	protected Color customTextColor = new Color(255, 255, 255, generalAlpha);

	protected int textOffsetY = 15, textOffsetX = 0;
	protected int imageOffsetX = 0, imageOffsetY = 0;
	
	protected int customImageW = 1;
	protected int customImageH = 1;
	protected BufferedImage buttonImage;
	protected int selectAlpha;
	protected String description = "";
	public boolean isPressing = false;

	public Button(int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}

	public void functions() {
		//code here
	}
	
	public void render(Graphics2D g) {
		customBackColor = new Color(this.customBackColor.getRed(), this.customBackColor.getGreen(), this.customBackColor.getBlue(), this.generalAlpha);
		customTextColor = new Color(this.customTextColor.getRed(), this.customTextColor.getGreen(), this.customTextColor.getBlue(), this.generalAlpha);
		UI.useAntiAliasing(g);
		/* Select animation */
		if(this.selectAni) {
			if(selected) {
				if(selectAlpha < 255) {
					selectAlpha+=51;
					wasSelected = true;
				}
			} else { 
				if(wasSelected && selectAlpha <= 255 && selectAlpha > 0) 
					selectAlpha-=51;
			}
		}
		
		g.setColor(customBackColor);
		g.fillRoundRect((int)x, (int)y, getWidth(), getHeight(), this.arc, this.arc);
		
		if(selected || selectAlpha > 0) {
			g.setColor(new Color(255, 255, 255, selectAlpha));
		} else {
			g.setColor(new Color(50, 50, 50, generalAlpha));
		}
		g.drawRoundRect((int)x, (int)y, (int)width, (int)height, this.arc, this.arc);
		
		if(!unavailable) 
			g.setColor(this.customTextColor);
		else 
			g.setColor(Color.gray);
		
		if(buttonImage != null) {
			if(customImageW != 1) {
				g.drawImage(buttonImage, getX() + (width/2) - ((buttonImage.getWidth() + (customImageW/2))/2) + imageOffsetX, 
						getY() + (height/2) - ((buttonImage.getWidth() + (customImageH/2))/2) + imageOffsetY, customImageW, customImageH, null);
			} else {
				g.drawImage(buttonImage, getX() + (width/2) - (buttonImage.getWidth()/2) + imageOffsetX, 
						getY() + (height/2) - (buttonImage.getWidth()/2) + imageOffsetY, null);
			}
		} else {
			if(customFont == Game.menuFont2) 
				g.setFont(new Font(this.customFontString, Font.BOLD, fontSize));
			else {
				g.setFont(this.customFont);
			}
			int xCenter;
			if(g.getFontMetrics().stringWidth(text) > getWidth()) 
				xCenter = this.getX() + this.textOffsetX + 6;
			else 
				xCenter = this.getX() + (this.width / 2) - (g.getFontMetrics().stringWidth(this.text)/2) + textOffsetX;
			
			g.drawString(this.text,  xCenter, getY() + (g.getFontMetrics().getHeight()/2) + textOffsetY);
		}
		/* TODO: (Incomplete) Show a description about the selected button
		if(!this.description.equals("")) 
			showDescription(g);*/
	}
	
	protected void showDescription(Graphics2D g) {
		if(selected && timeSelected < maxTimeSelected) 
			timeSelected++;
		else if(!selected) 
			timeSelected = 0;
		
		if(timeSelected == maxTimeSelected) {
			g.setFont(MainMenu.aFont);
			g.setColor(Color.black);
			g.fillRoundRect(Window.getMouseX() + 50, Window.getMouseY() + 30, g.getFontMetrics().stringWidth(this.description) + 15,
					g.getFontMetrics().getHeight() + 10, 30, 30);
			g.setColor(Color.white);
			g.drawRoundRect(Window.getMouseX() + 50, Window.getMouseY() + 30, g.getFontMetrics().stringWidth(this.description) + 15,
					g.getFontMetrics().getHeight() + 10, 30, 30);
			g.setColor(Color.white);
			g.drawString(this.description, Window.getMouseX() + 60, Window.getMouseY() + 65);
		}
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public int getWidth() {
		return (int)width;
	}
	
	public int getHeight() {
		return (int)height;
	}
	
}
