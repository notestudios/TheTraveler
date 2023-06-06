package com.notestudios.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.notestudios.main.Game;
import com.notestudios.main.Sound;

public class Controls {
	
	public void tick() {
		
		if (Game.ESC) {
			Game.ESC = false;
			Game.transition = true;
			if(!Game.mute) {
				Sound.backMenu.play();
			}
			Game.gameState = "Options";
		}
		
		if(Game.backControlsSelect && Game.backControlsEnter) {
			Game.backControlsSelect = false;
			Game.backControlsEnter = false;
			Game.transition = true;
			if(!Game.mute) {
				Sound.backMenu.play();
			}
			Game.gameState = "Options";
		}
		
	}
	
	public void render(Graphics g) {
		
		if(Game.backControlsSelect) {
			g.setColor(Color.white);
			g.drawRoundRect(-32, 68, 50 * Game.SCALE, 14 * Game.SCALE, 14, 14);
		}
		
		if (MainMenu.portugues) {
			g.setFont(MainMenu.aFont);
			g.setColor(Color.white);//controls
			g.drawString("< Voltar", 20, 106);
			g.setFont(new Font("Segoe UI", Font.BOLD, 12));
			g.drawString("(ESC)", 125, 90);
			g.setFont(MainMenu.TheFont);
			g.setColor(Color.white);
			g.drawString("Controles", 20, 50);
			
			g.setFont(MainMenu.aFont);
			g.drawString("(Key.) Mover Player = W, A, S, D", 10, 245);
			g.drawString("(Keyb.) Salvar Jogo = 'C'", 10, 245 + 72);
			g.drawString("(Keyb.) Confirmar = Enter", 10, 245 + 72 + 72);
			g.drawString("(Keyb.) Pular = Espaço", 10, 247 + 72 + 72 + 72);
			g.drawString("(Keyb.) Disparada = Shift/Ctrl", 10, 247 + 72 + 72 + 72 + 72);

			g.drawString("(Mouse) Atirar = Click Esquerdo", 480, 245-(30*Game.SCALE));
			g.drawString("(Keyb.) Esc = Voltar / Pausar", 480, 245 + 72-(30*Game.SCALE));
			g.drawString("(Mouse) Click Direito = Pausar Jogo", 480, 245 + 72 + 72-(30*Game.SCALE));
			g.drawString("(Mouse) Roda do Mouse = (N/A)", 476, 245 + 72 + 72 + 72-(30*Game.SCALE));
			g.drawString("(M/K) Selecionar Menu = Mouse/Setas", 480, 244 + 72 + 72 + 72 + 72-(30*Game.SCALE));
			g.drawString("(Não Associado)", 480, 244 + 72 + 72 + 72 + 72 + 72-(30*Game.SCALE));
			g.drawString("(Não Associado)", 480, 244 + 72 + 72 + 72 + 72 + 72 + 72-(30*Game.SCALE));
		
		} else if (MainMenu.english) {
			
			g.setFont(MainMenu.aFont);
			g.setColor(Color.white);//controls
			g.drawString("< Back", 30, 106);
			g.setFont(new Font("Segoe UI", Font.BOLD, 12));
			g.drawString("(ESC)", 125, 90);
			g.setFont(MainMenu.TheFont);
			g.setColor(Color.white);
			g.drawString("Controls", 30, 50);

			g.setFont(MainMenu.aFont);
			g.drawString("(Key.) Move Player = W, A, S, D", 10, 245);
			g.drawString("(Keyb.) Save Game = 'C'", 10, 245 + 72);
			g.drawString("(Keyb.) Confirm = Enter", 10, 245 + 72 + 72);
			g.drawString("(Keyb.) Jump = Space Bar", 10, 247 + 72 + 72 + 72);
			g.drawString("(Keyb.) Run = Shift/Ctrl", 10, 247 + 72 + 72 + 72 + 72);

			g.drawString("(Mouse) Shoot = Mouse Left Click", 480, 245-(30*Game.SCALE));
			g.drawString("(Keyb.) ESC = Back / Pause", 480, 245 + 72-(30*Game.SCALE));
			g.drawString("(Mouse) Right Click = Pause Game", 480, 245 + 72 + 72-(30*Game.SCALE));
			g.drawString("(Mouse) Mouse Wheel = Page Up/Down", 476, 245 + 72 + 72 + 72-(30*Game.SCALE));
			g.drawString("(M/K) Select Option = Mouse/Arrows", 480, 244 + 72 + 72 + 72 + 72-(30*Game.SCALE));
			g.drawString("(Not Assigned)", 480, 244 + 72 + 72 + 72 + 72 + 72-(30*Game.SCALE));
			g.drawString("(Not Assigned)", 480, 244 + 72 + 72 + 72 + 72 + 72 + 72-(30*Game.SCALE));
		}
		
	}
	
}
