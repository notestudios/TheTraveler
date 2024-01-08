package com.notestudios.menus;

import java.awt.Color;
import java.awt.Graphics2D;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.main.Window;
import com.notestudios.util.Button;
import com.notestudios.util.Sound;

public class Controls {
	
	public Button backBtn = new Button(10, 50, 180, 50, "<  Controls") {
		@Override
		public void functions() {
			if(MainMenu.portuguese) text = "<  Controles"; else text = "<  Controls";
			customFont = MainMenu.aFont;
			if(clicked) {
				clicked = false;
				UI.doTransition = true;
				if(!Game.mute) Sound.backMenu.play();
				Game.gameState = "Settings";
				quit();
			}
		}
	};
	
	public Controls() {
		Button.buttons.add(backBtn);
	}
	
	public void tick() {
		if(Game.ESC) {
			Game.ESC = false;
			UI.doTransition = true;
			if(!Game.mute) Sound.backMenu.play();
			Game.gameState = "Settings";
		}
		backBtn.functions();
	}
	
	public void render(Graphics2D g) {
		g.setColor(new Color(39, 39, 39, 220));
		g.fillRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
		g.setColor(Color.white);
		g.drawRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
		g.setFont(MainMenu.aFont);
		if(MainMenu.portuguese) {
			g.drawString("(Key.) Mover Player = W, A, S, D", 20, 245);
			g.drawString("(Keyb.) Salvar Jogo = C", 20, 245 + 72);
			g.drawString("(Keyb.) Confirmar = Enter", 20, 245 + 72 + 72);
			g.drawString("(Keyb.) Pular = Espaço", 20, 247 + 72 + 72 + 72);
			g.drawString("(Keyb.) Disparada = Shift/Ctrl", 20, 247 + 72 + 72 + 72 + 72);

			g.drawString("(Mouse) Atirar = Click Esquerdo", 480, 245-(30*Window.SCALE));
			g.drawString("(Keyb.) Esc = Voltar / Pausar", 480, 245 + 72-(30*Window.SCALE));
			g.drawString("(Mouse) Click Direito = Pausar Jogo", 480, 245 + 72 + 72-(30*Window.SCALE));
			g.drawString("(Mouse) Roda do Mouse = N/A", 476, 245 + 72 + 72 + 72-(30*Window.SCALE));
			g.drawString("(Mouse) Selecionar Menu = Mouse", 480, 244 + 72 + 72 + 72 + 72-(30*Window.SCALE));
		
		} else if(MainMenu.english) {
			g.drawString("(Key.) Move Player = W, A, S, D", 20, 245);
			g.drawString("(Keyb.) Save Game = 'C'", 20, 245 + 72);
			g.drawString("(Keyb.) Confirm = Enter", 20, 245 + 72 + 72);
			g.drawString("(Keyb.) Jump = Space Bar", 20, 247 + 72 + 72 + 72);
			g.drawString("(Keyb.) Run = Shift/Ctrl", 20, 247 + 72 + 72 + 72 + 72);

			g.drawString("(Mouse) Shoot = Mouse Left Click", 480, 245-(30*Window.SCALE));
			g.drawString("(Keyb.) ESC = Back / Pause", 480, 245 + 72-(30*Window.SCALE));
			g.drawString("(Mouse) Right Click = Pause Game", 480, 245 + 72 + 72-(30*Window.SCALE));
			g.drawString("(Mouse) Mouse Wheel = N/A", 476, 245 + 72 + 72 + 72-(30*Window.SCALE));
			g.drawString("(Mouse) Select Option = Mouse", 480, 244 + 72 + 72 + 72 + 72-(30*Window.SCALE));
			g.drawString("(Keyb.) Place Bomb = 'Z'", 480, 244 + 72 + 72 + 72 + 72 + 72-(30*Window.SCALE));
		}
		
		backBtn.render(g);
	}
	
	public void quit() {
		Game.ui.controls = null;
		Button.buttons.remove(this.backBtn);
	}
}
