package com.notestudios.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;

public class Button {
	
	protected BufferedImage buttonBgDesign;
	
	public double x;
	public double y;
	public int width;//optional
	public int height;//optional
	
	public String text = "Button"+Game.button.size();
	
	protected int buttonType = 1;
	
	public boolean selected = false;
	public boolean clicked = false;
	public boolean unavailable = false;
	public static boolean wasSelected = false;
	
	protected String customFontString = "Arial";
	protected Font customFont = Game.menuFont2;
	protected int fontSize = 20;
	
	protected int generalAlpha = 255;
	protected Color customBackColor = new Color(40, 40, 40, generalAlpha);
	protected Color customTextColor = new Color(255, 255, 255, generalAlpha);
	
	protected int textOffsetX = 0;
	protected int textOffsetY = 0;
	
	protected int customImageW = 1;
	protected int customImageH = 1;
	protected BufferedImage image;
	protected int opacity = 0;
	protected String description = "";
	public boolean isPressing = false;
	public int animType = 0;

	public Button(int x, int y, int width, int height, int type, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonType = type;
		this.text = text;
	}

	public void functions() {
		//code here
	}
	
	
	public void render(Graphics2D g) {
		if(Game.AAliasingEnabled) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		//select animation
		if(selected) {
			if(opacity < 255) {
				opacity+=51;
			}
		} else {
			if(opacity == 255) {
				wasSelected = true;
			}
			if(opacity <= 255 && opacity > 0) {
				opacity-=51;
			}
		}
		
		switch(buttonType) {
			case 0 -> {
				g.setColor(customBackColor);
				g.fillRoundRect((int)x, (int)y, getWidth(), getHeight(), 16, 16);
				
				if(selected) {
					g.setColor(new Color(255, 255, 255, opacity));
				} else {
					g.setColor(new Color(50, 50, 50, generalAlpha));
				}
				g.drawRoundRect((int)x, (int)y, (int)width, (int)height, 16, 16);
				
				if(!unavailable) {
					g.setColor(customTextColor);
				} else {
					g.setColor(Color.gray);
				}
				if(image != null) {
					g.drawImage(image, getX(), getY(), null);
				} else {
					if(customFont == Game.menuFont2) {
					g.setFont(new Font(customFontString, Font.BOLD, fontSize));} else {
						g.setFont(customFont); }
					g.drawString(text,  getX() + ((int) width / 2) - 30/*length here*/+textOffsetX, getY() + g.getFontMetrics().getHeight()+4+textOffsetY);
				}
			} case 1 -> {
				width = 260; height = 45;
				g.setColor(new Color(40, 40, 40));
				g.fillRoundRect((int)x, (int)y, 260, 45, 16, 16);
				if(selected) {
					g.setColor(new Color(255,255,255,opacity));
				} else {
					g.setColor(new Color(50, 50, 50));
				}
				g.drawRoundRect((int)x, (int)y, 260, 45, 16, 16);
				if(!unavailable) {
					g.setColor(new Color(255, 255, 255, generalAlpha));
				} else {
					g.setColor(Color.gray);
				}
				
				if(image != null) {
					g.drawImage(image, getX(), getY(), (int)width, (int)height, null);
				} else {
					g.setFont(new Font("Segoe UI Bold", Font.BOLD, 30));
					g.drawString(text,  getX() + ((int) width / 2) - 30/*length here*/, getY() + g.getFontMetrics().getHeight() + 4);
				}
			} case 2 -> {
				width = 50; height = 45;
				g.setColor(new Color(40, 40, 40));
				g.fillRoundRect((int)x, (int)y, (int)width, (int)height, 16, 16);
				if(selected) {
					g.setColor(new Color(255,255,255,opacity));
				} else {
					g.setColor(new Color(50, 50, 50));
				}
				g.drawRoundRect((int)x, (int)y, (int)width, (int)height, 16, 16);
				if(!unavailable) {
					g.setColor(Color.white);
				} else {
					g.setColor(Color.gray);
				}
				if(image != null) {
					g.drawImage(image, getX(), getY(), (int)width, (int)height, null);
				} else {
					g.setFont(new Font("Segoe UI Bold", Font.BOLD, 20));
					g.drawString(text,  getX() + ((int) width / 2) - 30/*length here*/, getY() + g.getFontMetrics().getHeight() + 4);
				}
			} case 3 -> { //clickable text (just doesn't have the background)
				if(selected) {
					g.setColor(new Color(255, 255, 255, opacity));
				} else {
					g.setColor(new Color(50, 50, 50, generalAlpha));
				}
				g.drawRoundRect((int)x, (int)y, (int)width, (int)height, 16, 16);
				
				if(!unavailable) {
					g.setColor(customTextColor);
				} else {
					g.setColor(Color.gray);
				}
				if(customFont == Game.menuFont2) {
					g.setFont(new Font(customFontString, Font.BOLD, fontSize));
				} else {
					g.setFont(customFont);
				}
				g.drawString(text, getX() + ((int) width / 2) - 30/*length here*/+textOffsetX, getY() + g.getFontMetrics().getHeight()+4+textOffsetY);
				
				width = g.getFontMetrics().stringWidth(text);
				height = g.getFontMetrics().getHeight();
			}
			default -> {
				if(buttonType>3) {
					buttonType = 1;
				}
			}
		}
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
