package com.notestudios.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.notestudios.entities.BigEnemy;
import com.notestudios.entities.Entity;
import com.notestudios.main.Game;
import com.notestudios.main.Window;
import com.notestudios.menus.Controls;
import com.notestudios.menus.Credits;
import com.notestudios.menus.GameOver;
import com.notestudios.menus.JoltLogin;
import com.notestudios.menus.LoadingScreen;
import com.notestudios.menus.MainMenu;
import com.notestudios.menus.Settings;
import com.notestudios.menus.Shop;
import com.notestudios.util.DialogBox;
import com.notestudios.util.Popup;
import com.notestudios.util.Transition;

public class UI {
	
	public Settings settings;
	public MainMenu menu;
	public Controls controls;
	public Credits credits;
	public Shop shop;
	public GameOver gameOver;
	public JoltLogin gjlogin;
	public LoadingScreen ls;
	public Minimap worldMinimap;
	public Transition trans;
	
	public static final Color defaultBgColor = new Color(40, 40, 40);
	private Color dark = new Color(40, 40, 40), light = new Color(245, 245, 245);
	public static boolean doTransition = false;
	private Color[] themes = {dark, light};
	private Color currentTheme = themes[0];
	public int layer = 0;
	
	private String curMenu = "MainMenu";
	
	public void reloadEssentials() {
		trans = new Transition();
		ls = new LoadingScreen();
		menu = new MainMenu();
		gjlogin = new JoltLogin();
	}
	
	public static void reloadFonts() {
		try {
			Game.noteLogoFont = Font.createFont(Font.TRUETYPE_FONT, Game.stream4).deriveFont(120f);
			Game.titleFont = Font.createFont(Font.TRUETYPE_FONT, Game.stream3).deriveFont(200f);
			Game.menuFont2 = Font.createFont(Font.TRUETYPE_FONT, Game.stream2).deriveFont(35f);
			Game.optFont = Font.createFont(Font.TRUETYPE_FONT, Game.streamFont).deriveFont(140f);
			Game.warningFont = Font.createFont(Font.TRUETYPE_FONT, Game.stream5).deriveFont(20f);
			Game.travelerLogoFont = Font.createFont(Font.TRUETYPE_FONT, Game.stream6).deriveFont(140f);
			MainMenu.TheFont = Font.createFont(Font.TRUETYPE_FONT, MainMenu.Font2).deriveFont(80f);
			MainMenu.UIFont = Font.createFont(Font.TRUETYPE_FONT, MainMenu.Font1).deriveFont(55f);
			MainMenu.DialogFont = Font.createFont(Font.TRUETYPE_FONT, MainMenu.dFontStream).deriveFont(45f);
			MainMenu.aFont = Font.createFont(Font.TRUETYPE_FONT, MainMenu.streamFont).deriveFont(50f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occurred while loading fonts\n"+e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void tick() {
		switch(Game.gameState) {
			case "Menu":
				if(!MainMenu.pauseMenu.showPauseMenu) menu.tick();
				else MainMenu.pauseMenu.tick();
				
			break;
			case "Normal":
				if(Game.dialogMode) {
					for(int i = 0; i < DialogBox.dialogBoxes.size(); i++) {
						DialogBox dialogBox = DialogBox.dialogBoxes.get(i);
						dialogBox.tick();
					}
				}
			break;
			case "Settings":
				if(settings == null) 
					settings = new Settings();
				
				settings.tick();
			break;
			case "GJLogin":
				if(gjlogin == null)
					gjlogin = new JoltLogin();
				
				gjlogin.tick();
			break;
			case "Controls":
				if(controls == null)
					controls = new Controls();
				
				controls.tick();
			break;
			/*case "Shop":
				if(shop == null)
					shop = new Shop();
				
				shop.tick();
			break; being redesigned and optimized, check @retrozinndev on gamejolt for updates!*/
			case "Credits":
				if(credits == null)
					credits = new Credits();
				
				credits.tick();
			break;
			case "Game Over":
				if(gameOver == null)
					gameOver = new GameOver();
				
				gameOver.tick();
			break;
		} if(Game.useMinimap && Game.gameState.equals("Normal")) {
			worldMinimap.tick();
		} if(ls.loadingScreen) {
			ls.tick();
		}
		
		for(int i = 0; i < Popup.popups.size(); i++) {
			Popup pop = Popup.popups.get(i);
			pop.tick();
		}
	}
	
	public static void useAntiAliasing(Graphics2D g) {
		if(Game.antiAliasingEnabled) 
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		else 
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}

	public void render(Graphics2D g) {
		useAntiAliasing(g);
		
		switch(Game.gameState) {
			case "Menu":
				if(!MainMenu.pauseMenu.showPauseMenu) {
					if(menu == null)
						menu = new MainMenu();
					
					menu.render(g);
				} else if(MainMenu.pauseMenu.showPauseMenu || MainMenu.pauseMenu.openPauseMenu)
					MainMenu.pauseMenu.render(g);
			break;
			case "Normal":
				if(Game.dialogMode) {
					for(int i = 0; i < DialogBox.dialogBoxes.size(); i++) {
						DialogBox dialogBox = DialogBox.dialogBoxes.get(i);
						dialogBox.render(g);
					}
				}
			break;
			case "Settings":
				if(settings == null) 
					settings = new Settings();
				
				settings.render(g);
			break;
			case "GJLogin":
				if(gjlogin == null) 
					gjlogin = new JoltLogin();
				
				gjlogin.render(g);
			break;
			case "Controls":
				if(controls == null) 
					controls = new Controls();
				
				controls.render(g);
			break;
			case "Shop":
				if(shop == null) 
					shop = new Shop();
				
				shop.render(g);
			break;
			case "Credits":
				if(credits == null) 
					credits = new Credits();
				
				credits.render(g);
			break;
			case "Game Over":
				if(gameOver == null) 
					gameOver = new GameOver();
				
				gameOver.render(g);
			break;
		} if(Game.useMinimap && Game.gameState.equals("Normal") && !Game.dialogMode) {
			worldMinimap.render(g);
		} if(ls.loadingScreen) {
			ls.render(g);
		}
		if((!Game.dialogMode) && Game.gameState.equals("Normal") || Game.gameState.equals("Shop")) { 
			/* Player UI */
			int hpBarWidth = 320, hpBarHeight = 35;
			int hpBarX = (Game.window.getWidth()/2) - (hpBarWidth/2), hpBarY = 30;
			createHPBar(g, hpBarX, 30, hpBarWidth, 35, 16, Game.player.life, Game.player.maxLife, "");
			
			g.drawImage(Entity.BULLET_EN, hpBarX + Entity.BULLET_EN.getWidth() + 30, 
					hpBarY+hpBarHeight+6, 48, 48, null);
			g.drawImage(Entity.COIN_EN, hpBarX+hpBarWidth-110, 
					hpBarY+hpBarHeight+6, 48, 48, null);
			
			g.setFont(MainMenu.UIFont);
			g.drawString(Integer.toString(Game.player.ammo), hpBarX + Entity.BULLET_EN.getWidth()+Entity.BULLET_EN.getWidth() + 72, 
					hpBarY+hpBarHeight+g.getFontMetrics().getHeight()-2);
			
			g.drawString(Integer.toString(Game.playerCoins), hpBarX+hpBarWidth+Entity.COIN_EN.getWidth()+32-110, 
					hpBarY+hpBarHeight+g.getFontMetrics().getHeight()-2);
			
			/* Big Enemy HP Bar */
			if(BigEnemy.bossfight) 
				for(BigEnemy be : BigEnemy.bosses) 
					createHPBar(g, 400, Game.window.getHeight()-50, 400, 40, 30, be.enemyLife, be.maxLife, "Boss");
		}
		if(Popup.popups.size() > 0) {
			for(Popup pop : Popup.popups) 
				pop.render(g);
		}
		if(doTransition) 
			trans.makeTransition(g);
	}
	
	public void createHPBar(Graphics2D g, int x, int y, int scaleWidth, int height, int arc, double hp, double maxHP, String name) {
		String hpString = " "+(int)hp+"/"+(int)maxHP;
		
		g.setColor(new Color(200, 0, 0));
		g.fillRoundRect(x, y, scaleWidth, height, arc, arc);
		g.setColor(new Color(0, 195, 13));
		g.fillRoundRect(x, y, (int)((hp / maxHP) * scaleWidth), height, arc, arc);
		g.setColor(new Color(10, 10, 11));
		g.drawRoundRect(x, y, scaleWidth, height, arc, arc);
		
		g.setColor(Color.white);
		g.setFont(MainMenu.UIFont);
		String fullString = name+":"+hpString;
		if(name.equals("")) 
			fullString = hpString;
		
		drawString(g, fullString, x+(scaleWidth/2)-(g.getFontMetrics().stringWidth(name+hpString)/2), y+(height/2) - (g.getFontMetrics().getHeight()/2)-(height/3));
	}
	
	public void comingSoon(Graphics2D g) {
		g.setColor(UI.defaultBgColor);
		g.fillRect(0, 0, Window.WIDTH * Window.SCALE, Window.HEIGHT * Window.SCALE);
		g.setColor(Color.white);
		if(MainMenu.english) {
			g.setFont(Game.travelerLogoFont);
			g.drawString("Coming Soon...", (Game.window.getWidth() / 2) - (g.getFontMetrics().stringWidth("Em Breve...")/2), (Window.HEIGHT * Window.SCALE) / 2);
			g.setFont(MainMenu.aFont);
			g.drawString("This function isn't available for now!", (Window.WIDTH * Window.SCALE) / 2 - 225,
					(Game.window.getHeight()/2) + 50);
		} else if(MainMenu.portuguese) {
			g.setFont(Game.travelerLogoFont);
			g.drawString("Em Breve...", (Game.window.getWidth() / 2) - (g.getFontMetrics().stringWidth("Em Breve...")/2), Game.window.getHeight()/2);
			g.setFont(MainMenu.aFont);
			g.drawString("Esta função ainda não está disponível!", (Window.WIDTH * Window.SCALE) / 2 - 240,
					(Game.window.getHeight()/2) + 50);
		}
	
	}
	public static void drawString(Graphics2D g, String text, int x, int y) {
        for(String line : text.split("\n")) 
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
	public String getCurrentMenu() {
		return curMenu;
	}
	public void changeMenu(String newMenu) {
		curMenu = newMenu;
	}
	public Color getTheme() {
		return currentTheme;
	}
}