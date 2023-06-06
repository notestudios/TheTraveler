package com.notestudios.menus;

import java.awt.*;

import com.notestudios.buttons.*;
import com.notestudios.main.Game;

public class PopupLogin {
	
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
		if(Game.AAEnabled) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		g.drawImage(Game.bigBackground, 500, 200, Game.bigBackground.getWidth()*6, Game.bigBackground.getHeight()*6, null);
		
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.setColor(Color.white);
		
		if(MainMenu.portugues) {
			Game.drawString(g, "Olá! Seja bem-vindo(a) ao The Traveler! Para\n desbloquear conquistas, você precisa fazer\n"
					+ "           Login com sua conta Game Jolt.", 540, 260);
		} else if(MainMenu.english) {
			Game.drawString(g, "Hello, and welcome to The Traveler! To unlock\n    Achivements, you need to login with your\n"
					+ "   Game Jolt account. Press 'login' to proceed\n                or 'Close' to close this pop up", 540, 260);
		}
		
		loginPopupButton.render(g);
		closePopupButton.render(g);
	}

}
