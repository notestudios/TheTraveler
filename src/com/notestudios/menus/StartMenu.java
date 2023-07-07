package com.notestudios.menus;

import java.awt.Color;
import java.awt.Graphics2D;

import com.notestudios.main.Game;
import com.notestudios.util.Sound;

public class StartMenu {

	public int playGameTextAlpha = 0;
	public int maxPlayGameTextAlpha = 255;
	public boolean animationPartTwo = false;
	
	public void tick() {
		if(Game.gameState.equals("Start Menu")) {
			if(Game.ENTER) {
				Game.ENTER = false;
				Game.downTransition = true;
				if(!Game.mute) {
					Sound.newGame.play();
				}
				Game.gameState = "Menu";
			}
			if(!animationPartTwo) {
				playGameTextAlpha += 5;
			} if(animationPartTwo){
				playGameTextAlpha-=5;
			} if (playGameTextAlpha == maxPlayGameTextAlpha && !animationPartTwo) {
				animationPartTwo = true;
			} if(playGameTextAlpha == 0) {
				animationPartTwo = false;
			}
		}
		
	}
	
	public void render(Graphics2D g) {
		
		g.setFont(MainMenu.TheFont);
		
		g.setColor(Color.gray);
		g.drawString("The", 440+4, 80+3);
		g.setColor(Color.white);
		g.drawString("The", 440, 80);
		
		g.setFont(Game.titleFont);
		g.setColor(Color.lightGray);
		g.drawString("TRAVELER", 220+5, 360-165);
		
		g.setColor(new Color(250, 250, 0));
		g.drawString("TRAVELER", 220, 360-170);
		g.setFont(MainMenu.aFont);
		g.setColor(new Color(255, 255, 255, playGameTextAlpha));
		if(MainMenu.Eng == 1 && MainMenu.Por == 0) {
			g.drawString("Press Enter to Start", 350, 562);
		} else if(MainMenu.Por == 1 && MainMenu.Eng == 0) {
			g.drawString("Pressione Enter para começar", 300, 562);
		} else if(MainMenu.Por == 0 && MainMenu.Eng == 0) {
			g.drawString("Press Enter to Start", 350, 562);
		}
		if(Game.isPressingEnter) {
			g.setColor(new Color(255,255,255,160));
			g.fillRoundRect(135, 524, 697, 57, 16, 16);
		} if(Game.isSelectingEnterGame) {
			g.setColor(Color.white);
			g.drawRoundRect(136, 523, 695, 57, 16, 16);
		}
	}
	
	
}
