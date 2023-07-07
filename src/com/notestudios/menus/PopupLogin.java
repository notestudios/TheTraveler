package com.notestudios.menus;

import java.awt.*;

import com.notestudios.main.Game;
import com.notestudios.util.Button;
import com.notestudios.gameapi.GameJolt;
import com.notestudios.graphics.UI;

public class PopupLogin {
	
	class PopupLogin_Button0 extends Button {
		public PopupLogin_Button0(int x, int y, int width, int height, int type, String text) {
			super(x, y, width, height, type, text);
		}
		
		public void functions() {
			if(selected && clicked) {
				clicked = false;
				if(!GameJolt.isLoggedIn) {
					Game.downTransition = true;
					Game.gameState = "GJLogin";
				}
			}
		}
		
		public void render(Graphics2D g) {
			int arc = 16;
			if(Game.AAliasingEnabled) {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			} else { g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF); }
	        buttonAnimation(0);
	        g.setColor(new Color(204, 255, 0));
	        g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), arc, arc);
	        if(selected) {
	            g.setColor(new Color(180,210,0,opacity));
	            g.fillRoundRect(getX(), getY(), getWidth()-1, getHeight()-1, arc, arc);
	            g.drawRoundRect(getX(), getY(), getWidth()-1, getHeight()-1, arc, arc);
	        } else {
	            g.setColor(new Color(180, 200, 0));
	            g.drawRoundRect((int)x, (int)y, (int)width-1, (int)height-1, arc, arc);
	        }

	        if(!unavailable) {
	            g.setColor(Color.black);
	        } else {
	            g.setColor(Color.gray);
	        }
	        if(image != null) {
	            g.drawImage(image, getX(), getY(), null);
	        } else {
	            g.setFont(new Font(customFontString, Font.BOLD, fontSize));
	            if(!GameJolt.isLoggedIn) {
	                g.drawString("Login", getX() + ((int) width / 2) - 30/*length here*/, getY() + g.getFontMetrics().getHeight() + 4);
	            } else {
	                g.drawString("Already Logged!", getX() + ((int) width / 2) - 80, getY() + g.getFontMetrics().getHeight() + 4);
	            }
	        }
		}
	}
	
	class PopupLogin_Button1 extends Button {
		public PopupLogin_Button1(int x, int y, int width, int height, int type, String text) {
			super(x, y, width, height, type, text);
			// TODO Auto-generated constructor stub
		}
		public void functions() {
			if(selected && clicked) {
				clicked = false;
				Game.showPopup = 0;
				Game.saveConfig = true;
				Game.loginPopup = false;
			}
		}
	}
	
	public PopupLogin_Button0 loginPopupButton = new PopupLogin_Button0(0, 50, 300, 40, 0, "Login");
	public PopupLogin_Button1 closePopupButton = new PopupLogin_Button1(0, 110, 300, 40, 0, "Close");
	
	public PopupLogin() {
		Game.button.add(loginPopupButton);
		Game.button.add(closePopupButton);
		
		loginPopupButton.x = 580;
		loginPopupButton.y = 380;
		loginPopupButton.width = 160;
		
		closePopupButton.x = 580+160+5;
		closePopupButton.y = 380;
		closePopupButton.width = 160;
	}
	
	public void tick() {
		loginPopupButton.functions();
		closePopupButton.functions();
	}
	
	public void render(Graphics2D g) {
		if(Game.AAliasingEnabled) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		g.drawImage(Game.bigBackground, 500, 200, Game.bigBackground.getWidth()*6, Game.bigBackground.getHeight()*6, null);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.setColor(Color.white);
		if(MainMenu.portuguese) {
			UI.drawString(g, "Olá! Seja bem-vindo(a) ao The Traveler! Para\n desbloquear conquistas, você precisa fazer\n"
					+ "           Login com sua conta Game Jolt.", 540, 260);
		} else if(MainMenu.english) {
			UI.drawString(g, "Hello, and welcome to The Traveler! To unlock\n    Achivements, you need to login with your\n"
					+ "   Game Jolt account. Press 'login' to proceed\n                or 'Close' to close this pop up", 540, 260);
		}
		loginPopupButton.render(g);
		closePopupButton.render(g);
	}

}
