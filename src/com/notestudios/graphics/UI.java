package com.notestudios.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.notestudios.entities.BigEnemy;
import com.notestudios.main.Game;
import com.notestudios.menus.Controls;
import com.notestudios.menus.Credits;
import com.notestudios.menus.JoltLogin;
import com.notestudios.menus.GameOver;
import com.notestudios.menus.MainMenu;
import com.notestudios.menus.PopupLogin;
import com.notestudios.menus.Settings;
import com.notestudios.menus.Shop;
import com.notestudios.menus.StartMenu;
import com.notestudios.util.OptionPane;

public class UI {

	public static int posX = 20;
	public static int posY = 4;
	
	public static boolean hasUnlockedTrophy;
	
	public static MainMenu menu;
	public static Settings opt;
	public static Controls controls;
	public static Credits credits;
	public static StartMenu startMenu;
	public static Shop shop;
	public static GameOver gameOver;
	public static PopupLogin popupLogin;
	public static JoltLogin gjlogin;
	
	public static void reloadUI() {
		menu = new MainMenu();
		opt = new Settings();
		controls = new Controls();
		credits = new Credits();
		startMenu = new StartMenu();
		shop = new Shop();
		gameOver = new GameOver();
		popupLogin = new PopupLogin();
		gjlogin = new JoltLogin();
	}
	
	public static void reloadFonts() {
		try {
			Game.newFont = Font.createFont(Font.TRUETYPE_FONT, Game.stream).deriveFont(55f);
			Game.noteLogoFont = Font.createFont(Font.TRUETYPE_FONT, Game.stream4).deriveFont(120f);
			Game.titleFont2 = Font.createFont(Font.TRUETYPE_FONT, Game.stream1).deriveFont(35f);
			Game.titleFont = Font.createFont(Font.TRUETYPE_FONT, Game.stream3).deriveFont(200f);
			Game.menuFont2 = Font.createFont(Font.TRUETYPE_FONT, Game.stream2).deriveFont(35f);
			Game.optFont = Font.createFont(Font.TRUETYPE_FONT, Game.streamFont).deriveFont(140f);
			Game.warningFont = Font.createFont(Font.TRUETYPE_FONT, Game.stream5).deriveFont(20f);
			Game.travelerLogoFont = Font.createFont(Font.TRUETYPE_FONT, Game.stream6).deriveFont(160f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			OptionPane.okWindow("Error", "An error occurred while loading fonts\n"+e, "error");
		}
	}
	
	public void unlockAchivementAnim(Graphics2D g, BufferedImage trophyImage, String trophyName, String trophyDescription, boolean animate) {
		hasUnlockedTrophy = true;
		int x;
		int xAnim = Game.WIDTH*Game.SCALE+(Game.bigBackground.getWidth()*4);
		int y = -16;
		if(animate) {
			x = xAnim;
		} else {
			x = Game.WIDTH*Game.SCALE-(Game.bigBackground.getWidth()*4);
		}
		g.drawImage(Game.bigBackground, x, y, Game.bigBackground.getWidth()*4, Game.bigBackground.getHeight()*4, null);
		g.drawImage(trophyImage, x+35, y+50, trophyImage.getWidth()/3, trophyImage.getHeight()/3, null);
		g.setFont(MainMenu.aFont);
		g.setColor(Color.white);
		g.drawString(trophyName, x+132+(trophyImage.getWidth()/3)-g.getFontMetrics().stringWidth(trophyName), y+80);
		g.setFont(Game.titleFont2);
		drawString(g, trophyDescription, x+62+(trophyImage.getWidth()/3), y+85);
	}
	
	public void tick() {
		posX = 82;
		posY = 4;
	}
	
	public static void useAntiAliasing(Graphics2D g2D) {
		if(Game.AAliasingEnabled) {
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
	}

	public void render(Graphics2D g) {
		
		if(Game.AAliasingEnabled) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		
		/* Player Life Bar */
		g.setColor(Color.BLACK);
		g.fillRoundRect(posX * Game.SCALE - 4, posY * Game.SCALE - 3, 72 * Game.SCALE, (10 * Game.SCALE)+1, 16, 16);

		g.setColor(new Color(200, 0, 0));
		g.fillRoundRect(posX * Game.SCALE, posY * Game.SCALE+1, 70 * Game.SCALE, 8 * Game.SCALE, 16, 16);

		g.setColor(new Color(0, 195, 13));
		g.fillRoundRect(posX * Game.SCALE, posY * Game.SCALE+1,
				(int) ((Game.player.life / Game.player.maxLife) * 70) * Game.SCALE, 8 * Game.SCALE, 16, 16);
		/***/
		
		/* Boss Life */
		if(Game.bossFight) {
			for(int i = 0; i < Game.bosses.size(); i++) {
				BigEnemy be = Game.bosses.get(i);
				
				g.setColor(Color.BLACK);
				g.fillRoundRect(250, 580, (int)be.maxLife * Game.SCALE+48, 10 * Game.SCALE, 15, 15);

				g.setColor(new Color(200, 0, 0));
				g.fillRoundRect(254, 584, (int)((be.maxLife * Game.SCALE))+40, 8 * Game.SCALE, 15, 15);
				
				g.setColor(new Color(0, 195, 13));
				g.fillRoundRect(254, 584, (int) ((be.enemyLife / be.maxLife) * 120) * Game.SCALE, 8 * Game.SCALE, 15, 15);
				
				g.setColor(Color.black);
				g.setFont(new Font("Segoe UI", Font.BOLD, 26));
				g.drawString((int)(be.enemyLife)+"/"+(int)(be.maxLife), 448, 610);
				g.setColor(Color.white);
				g.setFont(new Font("Segoe UI", Font.BOLD, 26));
				g.drawString((int)(be.enemyLife)+"/"+(int)(be.maxLife), 445, 608);
				
				g.setColor(Color.black);
				g.setFont(new Font("Segoe UI", Font.BOLD, 22));
				g.drawString("Boss:", 458, 572);
				g.setColor(Color.white);
				g.setFont(new Font("Segoe UI", Font.BOLD, 22));
				g.drawString("Boss:", 455, 570);
			}
		}
		/***/
	}
	
	public static void drawString(Graphics2D g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
}