package com.notestudios.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

import com.notestudios.gameapi.GameJolt;
import com.notestudios.main.Game;
import com.notestudios.util.Button;

public class JoltLogin {
    
    public static class LoginButton extends Button {
    	public LoginButton(int x, int y, int width, int height, int type, String text) {
            super(x, y, width, height, type, text);
        }

        public void functions() {
        	if(GameJolt.isLoggedIn) {
        		text = "Already Logged In!";
        	} else {
        		textOffsetX = 25;
        		textOffsetY = 4;
        		text = "Login";
        	}
        	customTextColor = Color.black;
        	customBackColor = new Color(204, 255, 0);
        	customFont = new Font("Arial", Font.BOLD, 20);
            if(selected && clicked) {
                clicked = false;
                if(!GameJolt.isLoggedIn) {
                    Game.jolt.login(Game.userName, Game.userToken);
                    if (GameJolt.loginSuccessful) {
                        if (!Game.jolt.getTrophy(GameJolt.trophiesIDs[0]).isAchieved()) {
                            Game.jolt.achieveTrophy(GameJolt.trophiesIDs[0]);
                        }
                        Game.saveLogin = true;
                    }
                }
            } if(selected) {
            	if(opacity < 255) {
            		opacity+=51;
            	}
            } else {
            	opacity = 0;
            }
        }
        
        public void render(Graphics2D g) {
        	int arc = 16;
			if(Game.AAliasingEnabled) {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			} else { g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF); }
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
	                g.drawString("Login", getX() + ((int)width / 2) - g.getFontMetrics().stringWidth("Login") + textOffsetX, getY() + g.getFontMetrics().getHeight() + textOffsetY);
	            } else {
	                g.drawString("Already Logged!", getX() + ((int)width / 2) - g.getFontMetrics().stringWidth("Already Logged!") + textOffsetX, getY() + g.getFontMetrics().getHeight() + textOffsetY);
	            }
	        }
        }
    }
    
    public static class BackButton extends Button {
    	public BackButton(int x, int y, int width, int height, int type, String text) {
    		super(x, y, width, height, type, text);
    	}
    	public void functions() {
    		//Back / Close Button
    		if(!GameJolt.isLoggedIn) {
    			width = 300;
    		} else {
    			width = 145;
    		}
    		if(!GameJolt.isLoggedIn) {
    			textOffsetX=-2;
    			if(selected && clicked) {
    				clicked = false;
    				if(!GameJolt.isLoggedIn) {
    					Game.userName = "";
    					Game.userToken = "";
    				}
    				Game.downTransition = true;
    				Game.gameState = "Menu";
    			}
    		} else {
    			text = "Back";
    			textOffsetX=7;
    			if(selected && clicked) {
    				clicked = false;
    				Game.downTransition = true;
    				Game.gameState = "Settings";
    			}
    		}
    	}
    }
    
    public static class LogoutButton extends Button {
    	public LogoutButton(int x, int y, int width, int height, int type, String text) {
    		super(x, y, width, height, type, text);
    	}
    	public void functions() {
    		//Logout button
    		textOffsetX=-5;
    		if(selected && clicked) {
    			clicked = false;
    			Game.jolt.logout();
    		}
    	}
    }
    
    public BufferedImage gjLogo;
    public BufferedImage travelerLogo;
    public LoginButton loginbtn;
    public BackButton cancelbtn;
    public LogoutButton logoutbtn;
    public static String curTextBox = "username";
    
    public JoltLogin() {
        try {
            gjLogo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/gj.png")));
            travelerLogo = ImageIO.read(getClass().getResource("/images/TRAVELER logo mini.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginbtn = new LoginButton(330, 380, 300, 40, 0, "Login");
        cancelbtn = new BackButton(330, 430, 145, 40, 0, "Cancel");
        logoutbtn = new LogoutButton(330+150, 430, 149, 40, 0, "Logout");
        Game.button.add(loginbtn);
        Game.button.add(cancelbtn);
        Game.button.add(logoutbtn);
    }

    public int usernameTextBoxX = 330;
    public int usernameTextBoxY = 230;
    public int UsernameTextBoxWidth = 300;
    public int UsernameTextBoxHeight = 50;

    public int tokenTextBoxX = 330;
    public int tokenTextBoxY = 300;
    public int tokenTextBoxWidth = 300;
    public int tokenTextBoxHeight = 50;
    
    
    public void tick() {
        loginbtn.functions();
        cancelbtn.functions();
        if(GameJolt.isLoggedIn) {
        	logoutbtn.functions();
        }
    }

    public void render(Graphics2D g) {

    	if(Game.AAliasingEnabled) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
        //g.drawImage(noteLogo, Game.WIDTH+20, Game.HEIGHT*Game.SCALE-130, 160, 160, null);
        g.drawImage(gjLogo, 425, -65, 360, 360, null);
        g.drawImage(travelerLogo, Game.WIDTH*Game.SCALE-700, 15, 200, 200, null);

        loginbtn.render(g);
        cancelbtn.render(g);
        if(GameJolt.isLoggedIn) {
        	logoutbtn.render(g);
        }

        //username
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(new Color(40, 40, 42));
        g.fillRoundRect(usernameTextBoxX, usernameTextBoxY, UsernameTextBoxWidth, UsernameTextBoxHeight, 40, 40);
        if(!curTextBox.equals("username")) {
            g.setColor(Color.white);
        } else {
            g.setColor(new Color(204, 255, 0));
        }
        g.drawRoundRect(usernameTextBoxX, usernameTextBoxY, UsernameTextBoxWidth, UsernameTextBoxHeight, 40, 40);
        g.setColor(Color.white);
        String usr = "User: "+Game.userName;
        g.drawString(usr, usernameTextBoxX + 10, 230 + loginbtn.getHeight() / 2 + 12);

        //token
        g.setColor(new Color(40, 40, 42));
        g.fillRoundRect(tokenTextBoxX, tokenTextBoxY, tokenTextBoxWidth, tokenTextBoxHeight, 40, 40);
        if(!curTextBox.equals("token")) {
            g.setColor(Color.white);
        } else {
            g.setColor(new Color(204, 255, 0));
        }
        g.drawRoundRect(tokenTextBoxX, tokenTextBoxY, tokenTextBoxWidth, tokenTextBoxHeight, 40, 40);
        g.setColor(Color.white);
        String tkn = "Token: "+Game.userToken;
        g.drawString(tkn, tokenTextBoxX + 10, 300 + loginbtn.getHeight() / 2 + 12);
    }
}
