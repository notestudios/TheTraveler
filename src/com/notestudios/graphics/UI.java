package com.notestudios.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.notestudios.entities.BigEnemy;
import com.notestudios.main.Game;

public class UI {

	public static int posX = 20;
	public static int posY = 4;

	public void tick() {
		posX = 82;
		posY = 4;
	}

	public void render(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRoundRect(posX * Game.SCALE - 4, posY * Game.SCALE - 3, 72 * Game.SCALE, (10 * Game.SCALE)+1, 16, 16);

		g.setColor(new Color(200, 0, 0));
		g.fillRoundRect(posX * Game.SCALE, posY * Game.SCALE+1, 70 * Game.SCALE, 8 * Game.SCALE, 16, 16);

		g.setColor(new Color(0, 195, 13));
		g.fillRoundRect(posX * Game.SCALE, posY * Game.SCALE+1,
				(int) ((Game.player.life / Game.player.maxLife) * 70) * Game.SCALE, 8 * Game.SCALE, 16, 16);
		
		
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
}