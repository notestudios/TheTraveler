package com.notestudios.main;

import com.notestudios.menus.MainMenu;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

import java.util.Objects;

public class UpdateRPC extends DiscordRichPresence{
	
	public static String showCurGameStateEN = "";
	public static String showCurGameStatePT = "";
	
	public void tick() {
		if(Game.gameState.equals("Menu")) {
			if(!Game.menu.pause) {
				showCurGameStatePT = "Nos Menus";
				showCurGameStateEN = "In the Menus";
			} else {
				showCurGameStatePT = "Pausado - No level: "+Game.curLevel;
				showCurGameStateEN = "Paused - At Level: "+Game.curLevel;
			}
		} else if(Game.gameState.equals("Options")) {
			showCurGameStatePT = "Nas Configurações";
			showCurGameStateEN = "In the Settings Menu";
		} else if(Game.gameState.equals("Controls")) {
			showCurGameStatePT = "Vendo os Controles do Jogo";
			showCurGameStateEN = "Taking a look at the Controls";
		} else if(Game.gameState.equals("Credits")) {
			showCurGameStatePT = "Vendo os Créditos do Jogo";
			showCurGameStateEN = "Taking a look at the Credits";
		} else if(Game.gameState.equals("Shop")) {
			showCurGameStatePT = "Pensando no que comprar";
			showCurGameStateEN = "Taking a look at the Shop";
		} else if(Game.gameState.equals("Start Menu")) {
			showCurGameStatePT = "Na tela inicial";
			showCurGameStateEN = "In the Start Menu";
		} else if(Game.gameState.equals("Game Over")) {
			showCurGameStatePT = "Na tela de Game Over";
			showCurGameStateEN = "In the Game Over screen";
		} else if(Game.gameState.equals("Normal")) {
			showCurGameStatePT = "Jogando";
			showCurGameStateEN = "Playing the Game";
		} 
		
		createNewPresence();
	}
	
	public void createNewPresence() {
		DiscordRichPresence rich;
		if(!Game.dev) {
			if(!Objects.equals(Game.gameState, "Normal")) {
				if(MainMenu.english) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails(showCurGameStateEN).setBigImage("game_logo", "Playing The Traveler")
							.setSmallImage("notestudios_logo", "Note Studios").build();
					DiscordRPC.discordUpdatePresence(rich);
				} else if(MainMenu.portugues) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails(showCurGameStatePT).setBigImage("game_logo", "Jogando The Traveler")
							.setSmallImage("notestudios_logo", "Note Studios").build();
					DiscordRPC.discordUpdatePresence(rich);
				}
			} if(Game.gameState.equals("Normal")) {
				if(MainMenu.english) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails("Playing - At Level: "+Game.curLevel).setBigImage("game_logo", "Playing The Traveler")
							.setSmallImage("notestudios_logo", "Note Studios").build();
					DiscordRPC.discordUpdatePresence(rich);
				} else if(MainMenu.portugues) {
					rich = new DiscordRichPresence.Builder(Game.gameState).setDetails("Jogando - No Level: "+Game.curLevel).setBigImage("game_logo", "Jogando The Traveler")
							.setSmallImage("notestudios_logo", "Note Studios").build();
					DiscordRPC.discordUpdatePresence(rich);
				}
			}
		} else {
			rich = new DiscordRichPresence.Builder("In Developer mode!").setDetails(showCurGameStateEN).setBigImage("game_logo", "Playing The Traveler")
					.setSmallImage("terminal_logo", "dev mode on!").build();
			DiscordRPC.discordUpdatePresence(rich);
		}
	}
	
}
