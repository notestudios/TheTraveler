package com.notestudios.menus;

import java.awt.Color;
import java.awt.Graphics2D;

import com.notestudios.entities.Entity;
import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.main.Window;
import com.notestudios.util.Button;

public class PauseMenu {
	
	public String[] menuOptions = {"Resume", "Load", "Settings", "Credits", "Quit"};
	public int currentOption = 0;
	public final int maxOptions = menuOptions.length - 1;
	public boolean showPauseMenu = false;
	public boolean closePauseMenu = false;
	public boolean openPauseMenu = false;
	public static int width = 400;
	public static int height = Window.HEIGHT*Window.SCALE - 34;
	public static int defaultPauseX = 16;
	public static int x = -width;
	public static int y = 16;
	public static int defaultArc = 30;
	
	private double defaultSpeed = 20;
	private double closeSpeed = defaultSpeed;
	private boolean finishedCloseAni = false;
	
	private double openSpeed = defaultSpeed;
	private boolean finishedOpenAni = false;
	
	public void tick() {
		for(Button b : MainMenu.menuButtons) 
			b.functions();
		
		if(MainMenu.esc && showPauseMenu) {
			MainMenu.esc = false;
			closePauseMenu = true;
		}
		
		if(openPauseMenu) 
			open();
		if(closePauseMenu) 
			close();
	}
	
	private void open() {
		if(x < defaultPauseX) {
			x+=(int)openSpeed;
			if(openSpeed > 2) 
				openSpeed-=0.5;
		} else {
			finishedOpenAni = true;
		} if(finishedOpenAni) {
			finishedOpenAni = false;
			openPauseMenu = false;
			Game.gameState = "Menu";
			openSpeed = defaultSpeed;
		}
	}
	
	private void close() {
		if(x+width > 0) {
			x-=(int)closeSpeed;
			if(closeSpeed > 4) 
				closeSpeed-=0.5;
		} else 
			finishedCloseAni = true;
		if(finishedCloseAni) {
			finishedCloseAni = false;
			showPauseMenu = false;
			closePauseMenu = false;
			Game.gameState = "Normal";
			closeSpeed = defaultSpeed;
		}
	}
	
	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);
		
		g.setColor(new Color(39, 39, 39, 240));
		g.fillRoundRect(PauseMenu.x, PauseMenu.y, PauseMenu.width, PauseMenu.height, PauseMenu.defaultArc, PauseMenu.defaultArc);
		g.setColor(Color.white);
		g.drawRoundRect(PauseMenu.x, PauseMenu.y, PauseMenu.width, PauseMenu.height, PauseMenu.defaultArc, PauseMenu.defaultArc);
		
		// Pause indicator
		g.drawImage(Entity.UIPause, x + 100, y + 175, 16*5, 16*5, null);
		g.setColor(Color.white);
		g.setFont(MainMenu.aFont);
		if (MainMenu.portuguese)
			g.drawString("Pausado", x + 100 + Entity.UIPause.getWidth()*5, y + 230);
		else if (MainMenu.english)
			g.drawString("Paused", x + 100 + Entity.UIPause.getWidth()*5, y + 230);
		
		// The Traveler
		if(!Game.ui.menu.exitRequest) {
			g.setFont(MainMenu.aFont);
			g.setColor(Color.white);
			g.drawString("The", PauseMenu.x + (PauseMenu.width/2) - (g.getFontMetrics().stringWidth("The")/2), 80);
			
			g.setFont(Game.travelerLogoFont);
			g.setColor(Color.gray);
			g.drawString("TRAVELER", PauseMenu.x + (PauseMenu.width/2) - (g.getFontMetrics().stringWidth("TRAVELER")/2) + 5, 170);
			g.setColor(Color.yellow);
			g.drawString("TRAVELER", PauseMenu.x + (PauseMenu.width/2) - (g.getFontMetrics().stringWidth("TRAVELER")/2), 165);
		}
		
		if (Game.ui.menu.exitRequest) {
			g.setFont(MainMenu.DialogFont);
			g.setColor(Color.white);
			String exitText;
			String exitWarningText;
			if(MainMenu.english) {
				exitText = "Do you really want to quit?";
				exitWarningText = "(All unsaved data will be lost)";
			} else {
				exitText = "Você realmente deseja sair?";
				exitWarningText = "(Dados não salvos serão perdidos)";
			}
			g.drawString(exitText, PauseMenu.x + (PauseMenu.width/2) - 
					(g.getFontMetrics().stringWidth(exitText)/2), PauseMenu.y + 50);
			g.setFont(Game.menuFont2);
			g.drawString(exitWarningText, PauseMenu.x + (PauseMenu.width/2) - (g.getFontMetrics().stringWidth(exitWarningText)/2),
					PauseMenu.y + 80);
		}
		
		// Buttons
		g.setFont(MainMenu.aFont);
		for(Button b : MainMenu.menuButtons) {
			boolean isRequestButton = b.equals(Game.ui.menu.yesBtn) || b.equals(Game.ui.menu.cancelBtn);
			if(isRequestButton && Game.ui.menu.exitRequest) {
				b.render(g);
			} else if(!isRequestButton) {
				b.render(g);
			}
		}
	}
}
