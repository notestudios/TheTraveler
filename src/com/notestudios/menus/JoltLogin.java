package com.notestudios.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.notestudios.gameapi.Trophies;
import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.main.Window;
import com.notestudios.util.Button;

public class JoltLogin {
	
    public Button loginBtn = new Button(330, 380, 300, 40, "Log in") {
        public void functions() {
        	if(MainMenu.portuguese) text = "Entrar"; else text = "Login";
        	textOffsetY = 15;
        	unavailable = Game.jolt.isLoggedIn;
        	customTextColor = Color.black;
        	customBackColor = new Color(204, 255, 0);
            if(clicked) {
                clicked = false;
                if(!Game.jolt.isLoggedIn) {
                    Game.jolt.login(Game.jolt.userName, Game.jolt.userToken);
                    if(Game.jolt.loginSuccessful) {
                    	Game.jolt.trophies.achieve(Trophies.trophyList.get("welcome, idkmyname!"));
                        Game.saveLogin = true;
                    }
                }
            }
        }
        
        public void render(Graphics2D g) {
        	UI.useAntiAliasing(g);
    		if(selected) {
    			if(selectAlpha < 255) {
    				selectAlpha+=51;
    				wasSelected = true;
    			}
    		} else {
    			if(wasSelected && selectAlpha <= 255 && selectAlpha > 0) 
    				selectAlpha-=51;
    		}
	        g.setColor(new Color(204, 255, 0));
	        g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), arc, arc);
	        if(selected || selectAlpha > 0) {
	            g.setColor(new Color(180,210,0,selectAlpha));
	            g.fillRoundRect(getX(), getY(), getWidth()-1, getHeight()-1, arc, arc);
	        } else {
	            g.setColor(new Color(180, 200, 0, selectAlpha));
	            g.drawRoundRect((int)x, (int)y, (int)width-1, (int)height-1, arc, arc);
	        }
	        if(!unavailable) 
	            g.setColor(Color.black);
	         else 
	            g.setColor(Color.gray);
	        
            g.setFont(customFont);
            g.drawString(text, getX() + (getWidth() / 2) - (g.getFontMetrics().stringWidth(text)/2) + textOffsetX, getY() + (g.getFontMetrics().getHeight()/2) + textOffsetY);
        }
    };
    
    public Button backBtn = new Button(330, 430, 145, 40, "Back") {
    	public void functions() {
    		if(MainMenu.portuguese) text = "Voltar"; else text = "Back";
    		textOffsetY = 15;
    		if(!Game.jolt.isLoggedIn) 
    			width = 300;
    		else 
    			width = 145;
    		
    		if(!Game.jolt.isLoggedIn) {
    			if(selected && clicked) {
    				clicked = false;
    				if(!Game.jolt.isLoggedIn) {
    					Game.jolt.userName = "";
    					Game.jolt.userToken = "";
    				}
    				UI.doTransition = true;
    				Game.gameState = "Menu";
    			}
    		} else {
    			text = "Back";
    			if(selected && clicked) {
    				clicked = false;
    				UI.doTransition = true;
    				Game.gameState = "Menu";
    			}
    		}
    	}
    };
    
    public Button logoutBtn = new Button(330+150, 430, 149, 40, "Log out") {
    	public void functions() {
    		if(MainMenu.portuguese) text = "Final. Sessão"; else text = "Logout";
    		textOffsetY = 15;
    		if(clicked) {
    			clicked = false;
    			Game.jolt.logout();
    		}
    	}
    };
    
    public BufferedImage gjLogo;
    public BufferedImage travelerLogo;
    public static String curTextBox = "username";
    private int textIndicatorAlpha = 0;
    private boolean typeIndicatorAni2 = false;
    
    public JoltLogin() {
        try {
            gjLogo = ImageIO.read(getClass().getResourceAsStream("/icons/gj.png"));
            travelerLogo = ImageIO.read(getClass().getResourceAsStream("/images/TRAVELER logo mini.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button.buttons.add(loginBtn);
        Button.buttons.add(backBtn);
        Button.buttons.add(logoutBtn);
    }

    public int usernameTextBoxX = 330;
    public int usernameTextBoxY = 240;
    public int UsernameTextBoxWidth = 300;
    public int UsernameTextBoxHeight = 50;

    public int tokenTextBoxX = 330;
    public int tokenTextBoxY = 310;
    public int tokenTextBoxWidth = 300;
    public int tokenTextBoxHeight = 50;
    
    
    public void tick() {
        loginBtn.functions();
        backBtn.functions();
        if(Game.jolt.isLoggedIn) {
        	logoutBtn.functions();
        }
        if(textIndicatorAlpha < 255 && !typeIndicatorAni2) 
			textIndicatorAlpha += 5;
		if(typeIndicatorAni2 && textIndicatorAlpha > 0) 
			textIndicatorAlpha-=5;
		if (textIndicatorAlpha == 255) 
			typeIndicatorAni2 = true;
		if(textIndicatorAlpha == 0) 
			typeIndicatorAni2 = false;
    }

    public void render(Graphics2D g) {
    	UI.useAntiAliasing(g);
    	g.setColor(UI.defaultBgColor);
    	g.fillRect(0, 0, Game.window.getWidth(), Game.window.getHeight());
    	
        g.drawImage(gjLogo, 425, -65, 360, 360, null);
        g.drawImage(travelerLogo, Window.WIDTH*Window.SCALE-700, 15, 200, 200, null);

        g.setFont(MainMenu.aFont);
        loginBtn.render(g);
        backBtn.render(g);
        if(Game.jolt.isLoggedIn) {
        	logoutBtn.render(g);
        }

        //username
        String usr = "User: "+Game.jolt.userName;
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(new Color(40, 40, 42));
        g.fillRoundRect(usernameTextBoxX, usernameTextBoxY, UsernameTextBoxWidth, UsernameTextBoxHeight, 40, 40);
        if(!curTextBox.equals("username")) 
            g.setColor(Color.white);
        else {
        	g.setColor(new Color(255, 255, 255, textIndicatorAlpha));
        	g.fillRect(usernameTextBoxX + 12 + g.getFontMetrics().stringWidth(usr), usernameTextBoxY + (g.getFontMetrics().getHeight()/2),
        			2, g.getFontMetrics().getHeight());
            g.setColor(new Color(204, 255, 0));
        }
        g.drawRoundRect(usernameTextBoxX, usernameTextBoxY, UsernameTextBoxWidth, UsernameTextBoxHeight, 40, 40);
        g.setColor(Color.white);
        g.drawString(usr, usernameTextBoxX + 12,  usernameTextBoxY + (UsernameTextBoxHeight/2) - (g.getFontMetrics().getHeight()/2) +20);

        //token
        String tkn = "Token: "+Game.jolt.userToken;
        g.setColor(new Color(40, 40, 42));
        g.fillRoundRect(tokenTextBoxX, tokenTextBoxY, tokenTextBoxWidth, tokenTextBoxHeight, 40, 40);
        if(!curTextBox.equals("token")) 
            g.setColor(Color.white);
        else {
        	g.setColor(new Color(255, 255, 255, textIndicatorAlpha));
        	g.fillRect(tokenTextBoxX + g.getFontMetrics().stringWidth(tkn) + 12, tokenTextBoxY + (g.getFontMetrics().getHeight()/2),
        			2, g.getFontMetrics().getHeight());
            g.setColor(new Color(204, 255, 0));
        }
        g.drawRoundRect(tokenTextBoxX, tokenTextBoxY, tokenTextBoxWidth, tokenTextBoxHeight, 40, 40);
        g.setColor(Color.white);
        g.drawString(tkn, tokenTextBoxX + 12, tokenTextBoxY + (tokenTextBoxHeight/2) - (g.getFontMetrics().getHeight()/2) +20);
    }
}
