package com.notestudios.discord;

import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;
import com.notestudios.world.World;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class RichPresence extends DiscordRichPresence {
	
	private String presenceEN;
	private String presencePT;
	private String gameLogo = "game_logo", notestudiosLogo = "notestudios_logo", terminalIcon = "terminal_logo";
	
	public final void update() {
		switch(Game.gameState) {
			case "Menu":
				if(!MainMenu.pauseMenu.pauseMode) {
					presencePT = "Nos Menus";
					presenceEN = "In the Menus";
				} else {
					presencePT = "Pausado - No level: ".concat(Integer.toString(World.curLevel));
					presenceEN = "Paused - At Level: ".concat(Integer.toString(World.curLevel));
				}
			break;
			case "Normal":
				presencePT = "Jogando - Level "+World.curLevel;
				presenceEN = "Playing - Level "+World.curLevel;
			break;
			case "Settings":
				presencePT = "Nas Configurações";
				presenceEN = "In the Settings Menu";
			break;
			case "Controls":
				presencePT = "Vendo os Controles do Jogo";
				presenceEN = "Taking a look at the Controls";
			break;
			case "Credits":
				presencePT = "Vendo os Créditos";
				presenceEN = "Taking a look at the Credits";
			break;
			case "Shop":
				presencePT = "Pensando no que comprar";
				presenceEN = "Taking a look at the Shop";
			break;
			case "Start Menu":
				presencePT = "Na tela inicial";
				presenceEN = "In the Start Menu";
			break;
			case "Game Over":
				presencePT = "No Game Over";
				presenceEN = "In the Game Over screen";
			break;
			case "GJLogin":
				presencePT = "Na tela de Login";
				presenceEN = "Logging In";
			break;
			default:
				presencePT = "Vazio";
				presenceEN = "Void";
			break;
		}
		applyNewPresence();
	}
	
	private void applyNewPresence() {
		DiscordRichPresence rich = new DiscordRichPresence.Builder("Playing").build();
		if(!Game.devMode) {
			if(Game.gameState.equals("Normal")) {
				if(MainMenu.english) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails(presenceEN).setBigImage(gameLogo, "Playing The Traveler")
							.setSmallImage(notestudiosLogo, "Note Studios").build();
				} else if(MainMenu.portuguese) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails(presencePT).setBigImage(gameLogo, "Jogando The Traveler")
							.setSmallImage(notestudiosLogo, "Note Studios").build();
				}
			}
		} else {
			rich = new DiscordRichPresence.Builder("dev").setDetails(presenceEN).setBigImage(gameLogo, 
					"Playing The Traveler").setSmallImage(terminalIcon, "dev mode on!").build();
		}
		DiscordRPC.discordUpdatePresence(rich);
	}
	
}
