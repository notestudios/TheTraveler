package com.notestudios.gameapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.gamejolt.GameJoltAPI;
import org.gamejolt.User;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;

public class GameJolt extends GameJoltAPI {
	
	public final File gameCredentialsFile = Game.gameCredentialsFile;
	public final File originalCredentialsFile = Game.gamejoltCredentialsFile;
	private static final int gameID = 796130;
	private static final String gamePrivateID = "";// Create a game on <https://gamejolt.com/> and get one private api key and paste it here!
	private User user;
	public Trophies trophies;
	public boolean isLoggingInFromFile = false;
	public boolean loginSuccessful;
	public boolean logoutSuccessful;
	public String APIVersion; //initializes when a ".gj-credentials" file exists
	public String userToken = "";
	public String userName = "";
	public boolean isLoggedIn = false;
	
	public GameJolt(int ID, String PrivateID) {
		super(ID, PrivateID);
		if(!PrivateID.isEmpty()) 
			System.out.println("Initializing Game Jolt API...");
		else 
			System.out.println("GameJolt: API Key is null!\nTip: Get one by creating a new game on Game Jolt!"
					+ "\n Create here: <https://gamejolt.com/dashboard/games/add>");
		trophies = new Trophies();
		isLoggingInFromFile = gameCredentialsFile.exists() || Game.gamejoltCredentialsFile.exists();
	}
	
	public void login(String username, String token) {
        verifyUser(username, token);
        loginSuccessful = sessionOpen();
        if(loginSuccessful) {
            user = getUser(username);
            System.out.println("Authentication successful: "+user.getName());
			isLoggedIn = true;
			if(!isLoggingInFromFile) { UI.doTransition = true; Game.gameState = "GJLogin"; }
			if(Game.devMode) {
				trophies.achieve(Trophies.trophyList.get("omg you're the dev??!?!?!??!?"));
			}
			return;
        } else {
			String msg, title;
			if(MainMenu.portuguese) {
				msg = "Usuário ou Token inválidos!\nTente verificar o seu Token ou Usuário";
				title = "Ocorreu um erro do Game Jolt!";
			} else {
				msg = "Username or Token invalid! \nTry verifying your token or username.";
				title = "An Game Jolt error occurred";
			}
			JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
        }
    }
    public void logout() {
        logoutSuccessful = sessionClose();
        if (logoutSuccessful) {
        	userToken = "";
        	isLoggedIn = false;
        	if(gameCredentialsFile.exists()) { gameCredentialsFile.delete(); }
        	if(originalCredentialsFile.exists()) { originalCredentialsFile.delete(); }
            System.out.println("Logout successful!");
			UI.doTransition = true;
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
			JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "An error occurred with Game Jolt", "Credentials Error!", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "An error occurred with Game Jolt", "Credentials Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public static int getID() {
		return gameID;
	}
	
	public static String getPrivateID() {
		return gamePrivateID;
	}
	
}
