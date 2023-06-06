package com.notestudios.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.notestudios.buttons.GJLogin_Button0;
import com.notestudios.buttons.GJLogin_Button1;
import com.notestudios.buttons.GJLogin_Button2;
import com.notestudios.main.Game;

public class GJLogin {

    public BufferedImage gjLogo;
    public BufferedImage noteLogo;
    public BufferedImage travelerLogo;
    public GJLogin_Button0 loginbtn;
    public GJLogin_Button1 cancelbtn;
    public GJLogin_Button2 logoutbtn;
    public static String curTextBox = "";
    static File credentials = new File("GJ");

    public GJLogin() {
        try {
            gjLogo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/gj.png")));
            noteLogo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/note.png")));
            travelerLogo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/TRAVELER logo 200x200.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginbtn = new GJLogin_Button0(330, 380, 300, 40, 0, "Login");
        cancelbtn = new GJLogin_Button1(330, 430, 145, 40, 0, "Cancel");
        logoutbtn = new GJLogin_Button2(330+150, 430, 149, 40, 0, "Logout");
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
        if(Game.isLoggedIn) {
        	logoutbtn.functions();
        }
    }

    public void render(Graphics2D g) {

    	if(Game.AAEnabled) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
        //g.drawImage(noteLogo, Game.WIDTH+20, Game.HEIGHT*Game.SCALE-130, 160, 160, null);
        g.drawImage(gjLogo, 440, -30, 360, 360, null);
        g.drawImage(travelerLogo, Game.WIDTH*Game.SCALE-700, 15, 200, 200, null);

        loginbtn.render(g);
        cancelbtn.render(g);
        if(Game.isLoggedIn) {
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
        String usr = "User: "+Game.USER_NAME;
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
        String tkn = "Token: "+Game.USER_TOKEN;
        g.drawString(tkn, tokenTextBoxX + 10, 300 + loginbtn.getHeight() / 2 + 12);
    }
    
    public static void loginGameJolt(String username, String token) {
        Game.api.verifyUser(username, token);
        Game.loginSuccessful = Game.api.sessionOpen();
        if (Game.loginSuccessful) {
            Game.gjUser = Game.api.getUser(username);
            System.out.println("Authentication successful: "+Game.gjUser.getName());
			Game.isLoggedIn = true;
			if(!credentials.exists()) { Game.transition = true; Game.gameState = "GJLogin"; }
			if(Game.dev && !Game.api.getTrophy(Game.TROPHIES_IDs[3]).isAchieved()) {
				Game.api.achieveTrophy(Game.TROPHIES_IDs[3]); }
			return;
        } else {
			String msg;
			String title;
			
			if(MainMenu.Por == 1 && MainMenu.portugues) {
				msg = "Usuário ou Token inválidos!\nTente verificar o seu Token ou Usuário";
				title = "Ocorreu um erro do Game Jolt!";
			} else {
				msg = "Username or Token invalid! \n Try verifying your token or username.";
				title = "An Game Jolt error occurred";
			}
            JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void logoutGameJolt() {
        Game.logoutSuccessful = Game.api.sessionClose();
        if (Game.logoutSuccessful) {
        	Game.USER_TOKEN = "";
        	Game.isLoggedIn = false;
        	if(credentials.exists()) { credentials.delete(); }
            System.out.println("Logout successful!");
			Game.transition = true;
			Game.gameState = "Menu";
			
			return;
        } else {
			String msg;
			String title;

			if(MainMenu.Por == 1 && MainMenu.portugues) {
				msg = "Ocorreu um erro ao encerrar a sessão\n Tente novamente mais tarde";
				title = "Ocorreu um erro do Game Jolt!";
			} else {
				msg = "An error occurred during Log Out!\n Try again later.";
				title = "An Game Jolt error occurred";
			}

            JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    //Save user credentials to next time
    public static void fileSave(String fileName, String username, String token) {
		String encoding = "UTF-8";
		try {
			PrintWriter writer = new PrintWriter(fileName, encoding);
			writer.println(username);
			writer.println(token);
			writer.close();
			//salvo com sucesso!
		} catch(IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occurred with Game Jolt", "Credentials Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void autoLogin(String fileName) {
		String encoding = "UTF-8";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
			String username = reader.readLine().trim().toString();
			String token = reader.readLine().trim().toString();
			reader.close();
			loginGameJolt(username, token);
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "An error occurred with Game Jolt", "Credentials Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
