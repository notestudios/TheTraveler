package com.notestudios.gameapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.gamejolt.GameJoltAPI;
import org.gamejolt.User;

import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;
import com.notestudios.util.OptionPane;

public class GameJolt extends GameJoltAPI {
	
	public static File gameCredentialsFile = new File(Game.gameCredentialsFile.getName());
	public static File originalCredentialsFile = new File(Game.gamejoltCredentialsFile.getName());
	public static User user;
	public static boolean isLoggingInFromFile = false;
	public static boolean loginSuccessful;
	public static boolean logoutSuccessful;
	public String APIVersion; //initialize when a ".gj-credentials" file exists (Version 0.2.1)
	public static boolean isLoggedIn = false;
	public static int[] trophiesIDs = {/*loginAch*/193422, /*SPAM*/193423, /*why he's so big*/193917, /*dev*/193424, 
			/*amogus*/198581};
	
	public GameJolt(int ID, String PrivateID) {
		super(ID, PrivateID);
		if(gameCredentialsFile.exists() || Game.gamejoltCredentialsFile.exists()) {
			isLoggingInFromFile = true;
		}
	}
	
	public void login(String username, String token) {
        verifyUser(username, token);
        loginSuccessful = sessionOpen();
        if (loginSuccessful) {
            user = getUser(username);
            System.out.println("Authentication successful: "+user.getName());
			GameJolt.isLoggedIn = true;
			if(!isLoggingInFromFile) { Game.downTransition = true; Game.gameState = "GJLogin"; }
			if(Game.devMode) {
				Trophies.achieve(GameJolt.trophiesIDs[3]);
			}
			return;
        } else {
			String msg, title;
			if(MainMenu.Por == 1 && MainMenu.portuguese) {
				msg = "Usuário ou Token inválidos!\nTente verificar o seu Token ou Usuário";
				title = "Ocorreu um erro do Game Jolt!";
			} else {
				msg = "Username or Token invalid! \nTry verifying your token or username.";
				title = "An Game Jolt error occurred";
			}
			OptionPane.jAlertWindow(title, msg, "error");
        }
    }
    public void logout() {
        logoutSuccessful = sessionClose();
        if (logoutSuccessful) {
        	Game.userToken = "";
        	GameJolt.isLoggedIn = false;
        	if(gameCredentialsFile.exists()) { gameCredentialsFile.delete(); }
        	if(originalCredentialsFile.exists()) { originalCredentialsFile.delete(); }
            System.out.println("Logout successful!");
			Game.downTransition = true;
			Game.gameState = "Menu";
			
			return;
        } else {
			String msg;
			String title;

			if(MainMenu.Por == 1 && MainMenu.portuguese) {
				msg = "Ocorreu um erro ao encerrar a sessão\n Tente novamente mais tarde";
				title = "Ocorreu um erro do Game Jolt!";
			} else {
				msg = "An error occurred during Log Out!\n Try again later.";
				title = "An Game Jolt error occurred";
			}
            OptionPane.jAlertWindow(title, msg, "error");
        }
    }
	
    public void fileSave(String fileName, String username, String token) {
		String encoding = "UTF-8";
		try {
			PrintWriter writer = new PrintWriter(fileName, encoding);
			writer.println(username);
			writer.println(token);
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
			OptionPane.jAlertWindow("Credentials Error!", "An error occurred with Game Jolt" , "error");
		}
	}
	
	public void autoLogin(File fileName) {
		String encoding = "UTF-8";
		String apiVersion = null;
		String username;
		String token;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), encoding));
			if(fileName.getName().equals(".gj-credentials")) {
				apiVersion = reader.readLine().trim().toString();
				username = reader.readLine().trim().toString();
				token = reader.readLine().trim().toString();
			} else {
				username = reader.readLine().trim().toString();
				token = reader.readLine().trim().toString();
			}
			reader.close();
			login(username, token);
			APIVersion = apiVersion;
		} catch(IOException e) {
			OptionPane.jAlertWindow("Credentials Error!", "An error occurred with Game Jolt" , "error");
			e.printStackTrace();
		}
	}
}
