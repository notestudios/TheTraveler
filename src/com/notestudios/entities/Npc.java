package com.notestudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;

public class Npc extends Entity {
	public String[] falas = new String[2];
	public boolean showMessage = false;
	public boolean showMessage1 = false;
	public boolean showMessage2 = false;
	public int curIndex = 0;
	
	
	public boolean right, left, up, down;
	
	

	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

	}

	public void tick() {
		
		if (MainMenu.portuguese) {
			falas[0] = "Olá, Seja bem-vindo ao Jogo! :), para Ajuda, abra o\n"
					+ "Menu(ESC), entre em opções e selecione 'Controles'!";
			/*falas[1] = "Estou em fase de desenvolvimento, ou seja, estou em\n"
					+ "acesso antecipado. Por isso preciso de seu feedback!";*/
		} else if (MainMenu.english) {
			falas[0] = "Hello! Welcome Traveler! If you need some help,\n"
					+ "open 'Pause Menu(ESC) > Settings > Controls'!";/*
			frases[1] = "Menu(ESC) go to Options, and select Controls.";*/
		}
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();
		int xNpc = getX();
		int yNpc = getY();

		if (Math.abs(xPlayer - xNpc) < 30 && Math.abs(yPlayer - yNpc) < 30) {
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

	public void render(Graphics g) {
		super.render(g);
		if (showMessage && Game.gameState == "Normal") {
			
			//g.setColor(new Color(/*235, 240, 235*/10,10,11,255));
			//g.fillRoundRect(/*getX() +*/ 152+50, /*getY() + */432, 752-70, 160-10, 16, 16);
			
			//g.setColor(new Color(/*20, 10, 12*/240,235,235,255));
			//g.drawRoundRect(/*getX() +*/ 152+50, /*getY() +*/ 432, 752-70, 160-10, 16, 16);
			//g.drawRoundRect(/*getX() + */151+50, /*getY() +*/ 433, 752-70, 158-10, 14, 14);
			
			//g.setColor(Color.white);
			//g.setFont(Menu.menuFont);
			//g.drawString("- "+"???"+" -", ((int) (/*getX() +*/ 216) / 2)*Game.SCALE, ((/*getY() + */210) / 2)*Game.SCALE);
			
			//g.setFont(Menu.DialogFont);
			//g.setColor(Color.white);
			//Game.drawString(g, falas[0].substring(0, curIndex), 270, 490);
			//g.setColor(Color.white);
			//g.setFont(new Font("Segoe UI", Font.BOLD, 11));
			//g.drawString("- ??? -", (int) (x + 220) / 2, (int) (y + 208) / 2);
			// g.drawString(frases[0], (int)x+2, (int)y-5+32+1);
		}
	}

}