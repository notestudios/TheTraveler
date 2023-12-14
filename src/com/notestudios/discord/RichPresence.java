package com.notestudios.discord;

import java.util.Objects;

import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;
import com.notestudios.world.World;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class RichPresence extends DiscordRichPresence {
	
	private String presenceEN;
	private String presencePT;
	
	public final void update() {
		switch(Game.gameState) {
			case "Menu":
				if(!MainMenu.pauseMenu.showPauseMenu) {
					presencePT = "Nos Menus";
					presenceEN = "In the Menus";
				} else {
					presencePT = "Pausado - No level: ".concat(Integer.toString(World.curLevel));
					presenceEN = "Paused - At Level: ".concat(Integer.toString(World.curLevel));
				}
			break;
			case "Normal":
				presencePT = "Jogando";
				presenceEN = "Playing";
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
				presencePT = "No Menu de Login do Game Jolt";
				presenceEN = "In Game Jolt Login Menu";
			break;
			default:
				presencePT = "Carregando...";
				presenceEN = "Loading...";
			break;
		}
		applyNewPresence();
	}
	
	private void applyNewPresence() {
		DiscordRichPresence rich = new DiscordRichPresence.Builder("Loading").setDetails("Loading")
				.setBigImage("game_logo", "Loading").setSmallImage("notestudios_logo", "Note Studios").build();
		if(!Game.devMode) {
			if(!Objects.equals(Game.gameState, "Normal")) {
				if(MainMenu.english) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails(presenceEN).setBigImage("game_logo", "Playing The Traveler")
							.setSmallImage("notestudios_logo", "Note Studios").build();
				} else if(MainMenu.portuguese) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails(presencePT).setBigImage("game_logo", "Jogando The Traveler")
							.setSmallImage("notestudios_logo", "Note Studios").build();
				}
			} if(Game.gameState.equals("Normal")) {
				if(MainMenu.english) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails("Playing - At Level: "+World.curLevel)
							.setBigImage("game_logo", "Playing The Traveler").setSmallImage("notestudios_logo", 
									"Note Studios").build();
				} else if(MainMenu.portuguese) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails("Jogando - No Level: "+World.curLevel)
							.setBigImage("game_logo", "Jogando The Traveler").setSmallImage("notestudios_logo",
									"Note Studios").build();
				}
			}
		} else {
			rich = new DiscordRichPresence.Builder("devMode=true!").setDetails(presenceEN).setBigImage("game_logo", 
					"Playing The Traveler").setSmallImage("terminal_logo", "dev mode on!").build();
		}
		DiscordRPC.discordUpdatePresence(rich);
	}
	
}
