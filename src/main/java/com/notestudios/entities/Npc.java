package com.notestudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import com.notestudios.graphics.Spritesheets;
import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;
import com.notestudios.util.DialogBox;

public class Npc extends Entity {
	public boolean right, left, up, down;
	public boolean showMessage = false;
	public int triggerDistance = 30;
	public boolean alreadyTriggered = false;
	
	private List<String> dialogs;
	public static BufferedImage defaultNPC = Spritesheets.entities.getSprite(16, 96, 16, 16);
	
	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public void tick() {
		showMessage = calculateDistance(Game.player.getX(), 
				Game.player.getY(), getX(), getY()) < triggerDistance;
		if(alreadyTriggered && !showMessage) { alreadyTriggered = false; }
		
		if(MainMenu.portuguese) {
			dialogs = Arrays.asList("Seja bem-vindo a primeira dungeon!\n" + 
					"Seu objetivo é: elimine todos os inimigos", 
					"Se precisar de ajuda com os Controles do Jogo, \n" + 
					"Pause o Jogo > Configurações > Controles!");
		} else {
			dialogs = Arrays.asList("Welcome to the first dungeon! Your\n" + 
					"first objective is: eliminate all enemies", 
					"If you need any help with the game controls, \n" +
					"Pause the Game > Settings > Controls!");
		}
		if(showMessage && !alreadyTriggered) { 
			new DialogBox("Dungeon Guy", dialogs, DialogBox.Orientation.BOTTOM);
			alreadyTriggered = true;
		}
	}

	public void render(Graphics g) {
		renderShadow(g, getWidth()-6, 4);
		super.render(g);
	}
}