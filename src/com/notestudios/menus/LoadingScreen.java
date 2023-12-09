package com.notestudios.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.main.Window;

public class LoadingScreen {
	private double logoTime = 0;
	private double logoMaxTime = (60 * 4);/*default value: 4*/
	private double opacity = 255;
	private double opacity2 = 255;
	private double opacity3 = 0;
	private double opacity4 = 255;
	private double noteStudiosLogoTime = 0;
	private double noteStudiosLogoMaxTime = (60 * 1.5);
	private boolean loadingScreenFinish = false;
	public boolean loadingScreen = false;
	private boolean loadingScreenAni = false;
	
	private List<String> tips = Collections.unmodifiableList(Arrays.asList("Press 'C' to save the game! ", "cool tip", "You can run pressing 'Shift'!", "idk whats the next tip bru", 
			"Move using W, A, S, D keys!", "There are maps that you can't use minimap!", "You can use the menus with the arrow keys!", "spam pressing E", 
			"Login with you Game Jolt Account!", "Config > Scroll Down > Game Jolt Login Menu", "Use 'Shift' or 'Ctrl' to run!", "Unlock new Achivements with Game Jolt!"));
	
	private List<String> tipsPt = Collections.unmodifiableList(Arrays.asList("Pressione 'C' para salvar o jogo!", "Você pode correr pressionando 'Shift'!", "Você também pode usar o menu com as setas!",
			"qual a próxima dica mesmo?", "Mova-se usando as teclas W, A, S, D!", "Há mapas que o minimapa não pode ser usado!", "spame bullets segurando E", 
			"Faça login com sua conta Game Jolt!", "Config > Baixo > Game Jolt Login Menu", "Use 'Shift' ou 'Ctrl' para correr!", "Desbloqueie novas conquistas com o Game Jolt!"));
	
	private String randomTip = "";
	
	public LoadingScreen() {
		if(MainMenu.portuguese)
			randomTip = tipsPt.get(Game.getRandom().nextInt(tipsPt.size()));
		else 
			randomTip = tips.get(Game.getRandom().nextInt(tips.size()));
	}
	
	public void tick() {
		if(loadingScreen) {
			if(Game.ui.menu.enter) {
				Game.ui.menu.enter = false;
			} if(Game.ui.menu.up) {
				Game.ui.menu.up = false;
			} if(Game.ui.menu.down) {
				Game.ui.menu.down = false;
			} if (noteStudiosLogoTime < noteStudiosLogoMaxTime) {
				noteStudiosLogoTime++;
			} if (noteStudiosLogoTime == noteStudiosLogoMaxTime) {
				noteStudiosLogoTime = noteStudiosLogoMaxTime;
				opacity2 -= 5;
			} if (opacity2 <= 0) {
				opacity2 = 0;
				opacity3 += 5;
			} if (opacity3 >= 255) {
				opacity3 = 255;
			} if (opacity == 0) {
				loadingScreenFinish = true;
			} if (loadingScreenFinish && opacity4 == 0) {
				loadingScreen = false;
			} if (Game.getRandom().nextInt(100) < 20) {
				logoTime += 1;
			} else if (Game.getRandom().nextInt(100) < 10) {
				logoTime += 3;
			} if (logoTime >= logoMaxTime) {
				logoTime = logoMaxTime;
				loadingScreenAni = true;
			} if (loadingScreenAni && opacity > 0) {
				opacity -= 5;
			} if (loadingScreenFinish && opacity <= 255) {
				opacity4 -= 5;
			} if (opacity4 <= 0) {
				opacity4 = 0;
			}
		}
	}
	
	public void render(Graphics2D g) {
		if(loadingScreen) {
			if (!loadingScreenFinish) {
				g.setColor(UI.defaultBgColor);
			} else {
				g.setColor(new Color(30, 30, 31, (int) opacity4));
			} /* 0xFFEF313C */
			g.fillRect(0, 0, Window.WIDTH * Window.SCALE, Window.HEIGHT * Window.SCALE);
	
			g.setFont(Game.noteLogoFont);
			g.setColor(new Color(255, 255, 255, (int) opacity2));
			int x2 = 300;
			int y2 = 400;
			g.drawString("Note", x2 + 5, y2 - 40);
			g.setFont(new Font("Segoe UI", Font.BOLD, 30));
			g.drawString("S T U D I O S", x2 + 75, y2);
	
			g.setFont(Game.titleFont);
			
			if (!loadingScreenFinish) {
				g.setColor(new Color(153, 153, 153, (int) opacity3));
			} else {
				g.setColor(new Color(153, 153, 153, (int) opacity4));
			}
			
			g.drawString("TRAVELER", 218+5, 360+5);
			
			if (!loadingScreenFinish) {
				g.setColor(new Color(255, 255, 0, (int) opacity3));
			} else {
				g.setColor(new Color(255, 255, 0, (int) opacity4));
			}
			
			g.drawString("TRAVELER", 218, 360);
	
			g.setFont(MainMenu.TheFont);
			if (!loadingScreenFinish) {
				g.setColor(new Color(255, 255, 255, (int) opacity3));
			} else {
				g.setColor(new Color(255, 255, 255, (int) opacity4));
			}
			if(Game.amogusSecret) {
				g.drawString("very sussy", 280, 250);
			} else {
				g.drawString("The", 428, 250);
			}
			g.setFont(Game.menuFont2);
			if(Game.licenseOK) {
				if(!Game.amogusSecret) {
					if(MainMenu.english) {
						g.drawString("Tip: "+randomTip, 10, 630);
					} else if(MainMenu.portuguese) {
						g.drawString("Dica: "+randomTip, 10, 630);
					}
				} else {
					g.drawString("sussus moogus", 10, 630);
				}
			}
		}
	}
}
