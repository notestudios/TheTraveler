package com.notestudios.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;

public class Npc extends Entity {
	public String[] falas = new String[2];
	public boolean showMessage = false;
	public int curIndex = 0;
	public int distanceOfPlayer = 30;
	
	
	public boolean right, left, up, down;
	
	

	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

	}

	public void tick() {
		
		if (MainMenu.portuguese) {
			falas[0] = "Olá, Seja bem-vindo ao Jogo! :), para Ajuda, abra o\n"
					+ "Menu(ESC), entre em opções e selecione 'Controles'!";
			/*falas[1] = "Estou em fase de desenvolvimento, ou seja, preciso \n"
					+ "do seu feedback!";*/
		} else if (MainMenu.english) {
			falas[0] = "Hello! Welcome Traveler! If you need some help,\n"
					+ "open 'Pause Menu(ESC) > Settings > Controls'!";/*
			frases[1] = "Get the game better giving your feedback!";*/
		}
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();
		int xNpc = getX();
		int yNpc = getY();

		if (calculateDistance(xPlayer, yPlayer, xNpc, yNpc) < distanceOfPlayer) {
			showMessage = true;
		} else {
			curIndex = 0;
			showMessage = false;
		}
		if (showMessage) {
			if (curIndex < falas[0].length()) {
				curIndex++;
			}
		}
	}

	public void render(Graphics2D g) {
		super.render(g);
	}

}