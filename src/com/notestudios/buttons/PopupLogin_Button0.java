package com.notestudios.buttons;

import java.awt.*;

import com.notestudios.main.Game;

public class PopupLogin_Button0 extends Button{

	public PopupLogin_Button0(int x, int y, int width, int height, int type, String text) {
		super(x, y, width, height, type, text);
	}
	
	public void functions() {
		if(selected && clicked) {
			clicked = false;
			if(!Game.isLoggedIn) {
				Game.transition = true;
				Game.gameState = "GJLogin";
			}
		}
	}
	
	public void render(Graphics2D g) {
		int arc = 16;
		if(Game.AAEnabled) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else { g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF); }
        buttonAnimations();
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
            if(!Game.isLoggedIn) {
                g.drawString("Login", getX() + ((int) width / 2) - 30/*length here*/, getY() + g.getFontMetrics().getHeight() + 4);
            } else {
                g.drawString("Already Logged!", getX() + ((int) width / 2) - 80, getY() + g.getFontMetrics().getHeight() + 4);
            }
        }
	}

}
