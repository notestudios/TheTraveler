package com.notestudios.gameapi;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.gamejolt.GameJoltAPI;
import org.gamejolt.User;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;
import com.notestudios.util.Popup;

public final class GameJolt extends GameJoltAPI {
	
	private final File gameCredentialsFile = Game.gameCredentialsFile;
	private final File originalCredentialsFile = Game.gamejoltCredentialsFile;
	
	// API Keys:
	private static final int gameID = 796130;
	private static final String gamePrivateID = "b3fdf3fdf75fc16b5a08e33e6446834d";
	// Create a game on <https://gamejolt.com/>, get your Game ID and Private Game ID then paste it here!
	
	private User user;
	private BufferedImage usrAvatar;
	public Trophies trophies;
	public boolean isLoggingInFromFile = false;
	public boolean loginSuccessful;
	public boolean logoutSuccessful;
	public String APIVersion; //initializes when a ".gj-credentials" file exists
	public String userToken = "";
	public String userName = "";
	public boolean isLoggedIn = false;
	
	public GameJolt() {
		super(gameID, gamePrivateID);
		if(!gamePrivateID.isEmpty()) {
			System.out.println("Initializing Game Jolt API...");
		} else {
			System.err.println("GameJolt: API Key is null! -> "+this.getClass().getSimpleName()+".java lines: 20..30");
			System.out.println("Tip: Get one by creating a new game on Game Jolt "
					+ "\nhere https://gamejolt.com/dashboard/games/add");
		}
		trophies = new Trophies();
		isLoggingInFromFile = gameCredentialsFile.exists() || Game.gamejoltCredentialsFile.exists();
	}
	
	public void login(String username, String token) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				verifyUser(username, token);
		        loginSuccessful = sessionOpen();
		        if(loginSuccessful) {
		            user = getUser(username);
		            try {
						usrAvatar = ImageIO.read(new URI(Game.jolt.getUser().getAvatarURL()).toURL());
					} catch (IOException | URISyntaxException e) {
						e.printStackTrace();
					}
		            System.out.println("Authentication successful: "+user.getName());
					isLoggedIn = true;
					if(!isLoggingInFromFile) { UI.doTransition = true; Game.gameState = "GJLogin"; }
					if(Game.devMode) { trophies.achieve(Trophies.trophyList.get("omg you're the dev??!?!?!??!?")); }
		        } else {
					final String msg, title;
					if(MainMenu.portuguese) {
						msg = "Usuário ou Token inválidos!\nTente verificar o seu Token ou Usuário";
						title = "Ocorreu um erro do Game Jolt!";
					} else {
						msg = "Username or Token invalid! \nTry verifying your token or username.";
						title = "An Game Jolt error occurred";
					}
					if(!isLoggingInFromFile) { new Popup(title, msg); }
					else { JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE); }
		        }
			}
        });
		t.start();
    }
    public void logout() {
    	Thread t = new Thread(new Runnable() {
			public void run() {
		        logoutSuccessful = sessionClose();
		        if(logoutSuccessful) {
		        	userToken = "";
		        	user = null;
		        	usrAvatar = null;
		        	isLoggedIn = false;
		        	if(gameCredentialsFile.exists()) { gameCredentialsFile.delete(); }
		        	if(originalCredentialsFile.exists()) { originalCredentialsFile.delete(); }
		            System.out.println("Logout successful!");
					UI.doTransition = true;
					
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
					new Popup(title, msg);
					//JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
		        }
			}
    	});
    	t.start();
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
		Thread t = new Thread(new Runnable() {
			public void run() {
				String apiVersion = null, username, token;
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
					if(fileName.getName().equals(".gj-credentials")) {
						apiVersion = reader.readLine().trim().toString();
						username = reader.readLine().trim().toString();
						token = reader.readLine().trim().toString();
					} else {
						username = reader.readLine().trim().toString();
						token = reader.readLine().trim().toString();
					}
					reader.close();
					userName = username;
					login(username, token);
					APIVersion = apiVersion;
				} catch(IOException e) {
					JOptionPane.showMessageDialog(null, "An error occurred with Game Jolt", "Credentials Error!", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	public User getUser() {
		return user;
	}
	
	public BufferedImage getUserAvatarImage() {
		return usrAvatar;
	}
	
}
