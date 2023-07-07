package com.notestudios.menus;

import java.awt.*;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.world.World;

public class GameOver {
	
	public void tick() {
		if (Game.ENTER) {
			Game.ENTER = false;
			Game.QExitGame = false;
			Game.cutsceneState = Game.enterCutscene;
			Game.gameState = "Normal";
			World.resetLevel();
		}
		if (Game.QExitGame == true) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}

		Game.gameOverFrames+=5;
		if (Game.gameOverFrames == 255) {
			Game.gameOverFrames = 0;
		}
		
		if(Game.selectBackMenu && Game.EnterGameOver) {
			Game.selectBackMenu = false;
			Game.EnterGameOver = false;
			Game.downTransition = true;
			World.resetLevel();
			Game.gameState = "Menu";
			UI.menu.pause = false;
		}
	}
	
	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);
		g.setFont(MainMenu.fontPixel);
		g.setColor(Color.white);
		g.drawString("Game Over!", (Game.WIDTH * Game.SCALE / 2) - 430, (Game.HEIGHT * Game.SCALE / 2) - 175);
		
		g.setFont(MainMenu.menuFont);
		if(Game.graphics == 2) {
			g.setColor(new Color(255, 255, 255, Game.gameOverFrames));
		} else if(Game.graphics == 1) {
			g.setColor(Color.white);
		}
		if (MainMenu.english)
			g.drawString("Press ENTER to play again!", (Game.WIDTH * Game.SCALE / 2) - 420, (Game.HEIGHT * Game.SCALE / 2) + 70);
		else if (MainMenu.portuguese) {
			g.setFont(MainMenu.aFont);
			g.drawString("Pressione ENTER para tentar novamente!", (Game.WIDTH * Game.SCALE / 2) - 450,
					(Game.HEIGHT * Game.SCALE / 2) + 65);
		}
		g.setFont(MainMenu.menuFont);
		g.setColor(Color.white);
		if(MainMenu.english) {
			g.drawString("< Back to Menu", 100, 450);
		} else if(MainMenu.portuguese) {
			g.drawString("< Voltar ao Menu", 80, 450);
		}
		
		if(Game.selectBackMenu) {
			g.setColor(Color.white);
			g.drawRoundRect(-5, 408, 505, 56, 14, 14);
		}
		
		g.setFont(new Font("Segoe UI", Font.BOLD, 22));
		g.setColor(Color.white);
		if (MainMenu.english)
			g.drawString("Press Q to quit", (Game.WIDTH * Game.SCALE / 2) - 360, (Game.HEIGHT * Game.SCALE / 2) + 312);
		else if (MainMenu.portuguese)
			g.drawString("Pressione Q para sair", (Game.WIDTH * Game.SCALE / 2) - 390, (Game.HEIGHT * Game.SCALE / 2) + 312);
	}
	
}
