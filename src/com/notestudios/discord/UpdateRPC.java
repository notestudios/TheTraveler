package com.notestudios.discord;

import java.util.Objects;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class UpdateRPC extends DiscordRichPresence{
	
	public static String showCurGameStateEN = "";
	public static String showCurGameStatePT = "";
	
	public static void tick() {
		switch(Game.gameState) {
			case "Menu" -> {
				if(!UI.menu.pause) {
					showCurGameStatePT = "Nos Menus";
					showCurGameStateEN = "In the Menus";
				} else {
					showCurGameStatePT = "Pausado - No level: "+Game.curLevel;
					showCurGameStateEN = "Paused - At Level: "+Game.curLevel;
				}
			}
			case "Normal" -> {
				showCurGameStatePT = "Jogando";
				showCurGameStateEN = "Playing the Game";
			}
			case "Settings" -> {
				showCurGameStatePT = "Nas Configurações";
				showCurGameStateEN = "In the Settings Menu";
			}
			case "Controls" -> {
				showCurGameStatePT = "Vendo os Controles do Jogo";
				showCurGameStateEN = "Taking a look at the Controls";
			}
			case "Credits" -> {
				showCurGameStatePT = "Vendo os Créditos do Jogo";
				showCurGameStateEN = "Taking a look at the Credits";
			}
			case "Shop" -> {
				showCurGameStatePT = "Pensando no que comprar";
				showCurGameStateEN = "Taking a look at the Shop";
			}
			case "Start Menu" -> {
				showCurGameStatePT = "Na tela inicial";
				showCurGameStateEN = "In the Start Menu";
			}
			case "Game Over" -> {
				showCurGameStatePT = "Na tela de Game Over";
				showCurGameStateEN = "In the Game Over screen";
			}
			case "GJLogin" -> {
				showCurGameStatePT = "No Menu de Login do Game Jolt";
				showCurGameStateEN = "In Game Jolt Login Menu";
			}
		}
		createNewPresence();
	}
	
	static void createNewPresence() {
		DiscordRichPresence rich = new DiscordRichPresence.Builder("Loading").setDetails("Loading")
				.setBigImage("game_logo", "Loading").setSmallImage("notestudios_logo", "Note Studios").build();
		if(!Game.devMode) {
			if(!Objects.equals(Game.gameState, "Normal")) {
				if(MainMenu.english) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails(showCurGameStateEN).setBigImage("game_logo", "Playing The Traveler")
							.setSmallImage("notestudios_logo", "Note Studios").build();
					DiscordRPC.discordUpdatePresence(rich);
				} else if(MainMenu.portuguese) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails(showCurGameStatePT).setBigImage("game_logo", "Jogando The Traveler")
							.setSmallImage("notestudios_logo", "Note Studios").build();
					DiscordRPC.discordUpdatePresence(rich);
				}
			} if(Game.gameState.equals("Normal")) {
				if(MainMenu.english) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails("Playing - At Level: "+Game.curLevel)
							.setBigImage("game_logo", "Playing The Traveler").setSmallImage("notestudios_logo", 
									"Note Studios").build();
					DiscordRPC.discordUpdatePresence(rich);
				} else if(MainMenu.portuguese) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails("Jogando - No Level: "+Game.curLevel)
							.setBigImage("game_logo", "Jogando The Traveler").setSmallImage("notestudios_logo",
									"Note Studios").build();
					DiscordRPC.discordUpdatePresence(rich);
				}
			}
		} else {
			rich = new DiscordRichPresence.Builder("devMode=true!").setDetails(showCurGameStateEN).setBigImage("game_logo", 
					"Playing The Traveler").setSmallImage("terminal_logo", "dev mode on!").build();
			DiscordRPC.discordUpdatePresence(rich);
		}
	}
	
}
