package com.notestudios.menus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.util.Button;
import com.notestudios.util.Popup;
import com.notestudios.util.Sound;

public class Settings {

	public List<Button> settingsButtons;
	public static String curLanguage = "";
	public boolean down;
	public boolean up;
	public static int mute = 0;
	public boolean left;
	public boolean right;
	public int curOpt = 0;
	public boolean esc = false;
	public static int minimap = 1;
	public static int antiAliasing = 0;
	private static File configSave = Game.configSave;
	
	public Button backButton = new Button(10, 50, 60, 50, "<  Settings") {
		@Override
		public void functions() {
			if(MainMenu.portuguese) text = "<  Config."; else text = "<  Settings";
			
			customFont = MainMenu.aFont;
			x = 10;
			width = 160;
			height = 50;
			if(clicked) {
				clicked = false;
				UI.doTransition = true;
				if(!Game.mute) Sound.backMenu.play();
				quitSettingsMenu("Menu");
			}
		}
	};
	
	public Button selectLanguage = new Button(150, 240, 300, 50, "Language: Default") {
		@Override
		public void functions() {
			customFont = MainMenu.aFont;
			if(MainMenu.english) text = "Lang.: English"; else if(MainMenu.portuguese) text = "Idioma: Português"; 
			
			if(clicked) {
				clicked = false;
				if(!Game.mute) Sound.menuEnter.play();
				UI.doTransition = true;
				if(MainMenu.english) { // English -> Portuguese
					MainMenu.portuguese = true;
					MainMenu.english = false;
					MainMenu.curLanguage = "Português";
					MainMenu.Eng = 0; MainMenu.Por = 1;
				} else if(MainMenu.portuguese) { // Portuguese -> English 
					MainMenu.portuguese = false;
					MainMenu.english = true;
					MainMenu.curLanguage = "English";
					MainMenu.Eng = 1; MainMenu.Por = 0;
				}
				Game.saveConfig = true;
			}
		}
	};
	
	public Button changeGraphicsPreset = new Button(60, 300, 340, 50, "Graphics: Default") {
		@Override
		public void functions() {
			if(MainMenu.portuguese) text = "Gráficos: "+Game.showGraphics; else text = "Graphics: "+Game.showGraphics;
			
			customFont = MainMenu.aFont;
			x = selectLanguage.getX() + selectLanguage.getWidth() + 6;
			y = selectLanguage.getY();
			if(clicked) {
				clicked = false;
				if (Game.graphicsQuality == 2) {
					Game.graphicsQuality = 1;
					if(!Game.mute) Sound.menuEnter.play();
					Game.saveConfig = true;
				} else if (Game.graphicsQuality == 1) {
					Game.graphicsQuality = 2;
					if(!Game.mute) Sound.menuEnter.play();
					Game.saveConfig = true;
				}
			}
		}
	};
	
	public Button gameControls = new Button(60, 450, 340, 50, "Game Controls >") {
		@Override
		public void functions() {
			if(MainMenu.portuguese) text = "Control. do Jogo >"; else text = "Game Controls >";
			
			customFont = MainMenu.aFont;
			x = selectLanguage.getX();
			y = selectLanguage.getY() + selectLanguage.getHeight() + 6;
			if(clicked) {
				clicked = false;
				quitSettingsMenu("Controls");
				if(!Game.mute) Sound.menuEnter.play();
				UI.doTransition = true;
			}
		}
	};
	
	public Button gameInfo = new Button(60, 600, 310, 50, "About >") {
		@Override
		public void functions() {
			if(MainMenu.portuguese) text = "Sobre >"; else text = "About >";
			
			customFont = MainMenu.aFont;
			x = useMinimap.getX() + useMinimap.getWidth() + 6;
			y = useMinimap.getY();
			if(clicked) {
				clicked = false;
				if(!Game.mute) Sound.menuEnter.play();
				new Popup("About", Arrays.asList("Game: The Traveler", "Version: "+Game.currentVersion, 
						"Last Updated: "+Game.lastUpdated, "OS: "+System.getProperty("os.name"), 
						"OS Version: " + System.getProperty("os.version")));
			}
		}
	};
	
	public Button useMinimap = new Button(60, 750, 330, 50, "Minimap: Default") {
		@Override
		public void functions() {
			if(MainMenu.portuguese) 
				if(Game.useMinimap) text = "Minimapa: Ligado"; else text = "Minimapa: Deslig.";
			else 
				if(Game.useMinimap)  text = "Minimap: ON"; else text = "Minimap: OFF";
			
			customFont = MainMenu.aFont;
			x = gameControls.getX();
			y = gameControls.getY() + gameControls.getHeight() + 6;
			if(clicked) {
				clicked = false;
				if(!Game.mute) Sound.menuEnter.play();
				
				if(!Game.useMinimap) {
					Game.useMinimap = true;
					minimap = 1;
				} else {
					Game.useMinimap = false;
					minimap = 0;
				}
				Game.saveConfig = true;
			}
		}
	};
	
	public Button muteAudio = new Button(60, 900, 300, 50, "Mute: N/A") {
		@Override
		public void functions() {
			if(MainMenu.portuguese) {
				if(Game.mute) text = "Deslig. Sons: Sim"; else text = "Deslig. Sons: Não";
			} else {
				if(Game.mute) text = "Mute: Yes"; else text = "Mute: No";
			}
			customFont = MainMenu.aFont;
			x = gameControls.getX() + gameControls.getWidth() + 6;
			y = gameControls.getY();
			if(clicked) {
				clicked = false;
				if(!Game.mute) Sound.menuEnter.play();
				
				if (!Game.mute) {
					Game.mute = true;
					mute = 1;
				} else {
					Game.mute = false;
					mute = 0;
				}
				Game.saveConfig = true;
			}
		}
	};
	
	public Button useAntiAliasing = new Button(60, 1050, 645, 50, "Anti-Alias.: Default") {
		@Override
		public void functions() {
			String state;
			if(MainMenu.portuguese) 
				if(Game.antiAliasingEnabled) state = "Ligado"; else state = "Desligado";
			else 
				if(Game.antiAliasingEnabled) state = "ON"; else state = "OFF";
			
			text = "Anti-Aliasing: "+state;
			customFont = MainMenu.aFont;
			width = 645;
			x = useMinimap.getX();
			y = useMinimap.getY() + useMinimap.getHeight() + 6;
			if(clicked) {
				clicked = false;
				if(Game.graphicsQuality == 1) {
					if(!Game.mute) Sound.errorSound.play();
				} else {
					if(!Game.mute) Sound.menuEnter.play();
					
					if(!Game.antiAliasingEnabled) {
						Game.antiAliasingEnabled = true;
						antiAliasing = 1;
					} else {
						Game.antiAliasingEnabled = false;
						antiAliasing = 0;
					}
					Game.saveConfig = true;
				}
			}
		}
	};
	
	public Button loginGameJolt = new Button(80, 300, 645, 50,
			"Game Jolt Login  >") {
		public void functions() {
			if(!Game.jolt.isLoggedIn)
				if(MainMenu.portuguese) text = "Fazer Login  >"; else text = "Login with Game Jolt  >";
			else 
				if(MainMenu.portuguese) text = "Opções do Game Jolt  >"; else text = "Game Jolt Account Options  >";
			
			customFont = MainMenu.aFont;
			if(!selected) {
				if(generalAlpha == 255 &&
						generalAlpha > 200) 
					generalAlpha-=5;
				else 
					generalAlpha = 200;
			} else {
				if(generalAlpha < 255)
					generalAlpha+=5;
			}
			customBackColor = new Color(220, 220, 0, generalAlpha);
			customTextColor = Color.black;
			x = useAntiAliasing.getX();
			y = useAntiAliasing.getY() + useAntiAliasing.getHeight() + 6;
			if(clicked) {
				clicked = false;
				UI.doTransition = true;
				quitSettingsMenu("GJLogin");
			}
		}
		
		public void render(Graphics2D g) {
        	UI.useAntiAliasing(g);
        	if(selected) {
				if(selectAlpha < 255) {
					selectAlpha+=51;
					wasSelected = true;
				}
			} else { 
				if(wasSelected && selectAlpha <= 255 && selectAlpha > 0) 
					selectAlpha-=51;
			}
	        g.setColor(new Color(204, 255, 0));
	        g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), arc, arc);
	        if(selected || selectAlpha > 0) {
	            g.setColor(new Color(180,210,0,selectAlpha));
	            g.fillRoundRect(getX(), getY(), getWidth()-1, getHeight()-1, arc, arc);
	            g.drawRoundRect(getX(), getY(), getWidth()-1, getHeight()-1, arc, arc);
	        } else {
	            g.setColor(new Color(180, 200, 0));
	            g.drawRoundRect((int)x, (int)y, (int)width-1, (int)height-1, arc, arc);
	        }
	        if(!unavailable) 
	            g.setColor(Color.black);
	         else 
	            g.setColor(Color.gray);
	        
            g.setFont(customFont);
            g.drawString(text, getX() + (getWidth() / 2) - (g.getFontMetrics().stringWidth(text)/2) + textOffsetX, getY() + (g.getFontMetrics().getHeight()/2) + textOffsetY);
        }
	};
	
	public Settings() {
		reloadSavedSettings();
		settingsButtons = Collections.unmodifiableList(Arrays.asList(
			backButton,
			selectLanguage,
			changeGraphicsPreset,
			gameControls,
			gameInfo,
			useMinimap,
			muteAudio,
			useAntiAliasing,
			loginGameJolt
		));
		
		Button.buttons.addAll(settingsButtons);
	}
	
	
	public void tick() {
		for(Button b : settingsButtons) b.functions();
		
		if(esc) {
			esc = false;
			if(!Game.mute) Sound.backMenu.play();
			
			Game.gameState = "Menu";
			UI.doTransition = true;
			Game.saveConfig = true;
		}
		if(esc && Game.gameState.equals("Settings")) {
			esc = false;
			UI.doTransition = true;
			Game.gameState = "Menu";
		}
	}
	
	
	
	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);
		g.setColor(new Color(39, 39, 39, 220));
		g.fillRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
		g.setColor(Color.white);
		g.drawRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
		
		for(Button b : settingsButtons) 
			b.render(g);
	}
	
	private static void applyCfgSave(String str) {
		String[] spl = str.split("/");
		for (int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch (spl2[0]) {
			case "english":
				MainMenu.Eng = Integer.parseInt(spl2[1]);
				break;
			case "portugues":
				MainMenu.Por = Integer.parseInt(spl2[1]);
				break;
			case "quality":
				Game.graphicsQuality = Short.parseShort(spl2[1]);
				break;
			case "mute":
				mute = Integer.parseInt(spl2[1]);
				break;
			case "minimap":
				minimap = Integer.parseInt(spl2[1]);
				break;
			case "anti-aliasing":
				antiAliasing = Integer.parseInt(spl2[1]);
				break;
			case "showPopup":
				Game.showPopup = Short.parseShort(spl2[1]);
				break;
			}
			
		}
	}

	private static String loadConfig() {
		String line = "";
		File save = new File("settings.save");
		if (save.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("settings.save"));
				try {
					while ((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for (int i = 0; i < val.length; i++) {
							trans[1] += val[i];
						}
						line += trans[0];
						line += ":";
						line += trans[1];
						line += "/";
					}
				} catch (IOException e) {
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return line;
	}

	public void saveConfig(String[] val1, int[] val2) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("settings.save"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++) {
				current += value[n];
			}
			try {
				write.write(current);
				if (i < val1.length - 1)
					write.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				write.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void reloadSavedSettings() {
		if(configSave.exists()) {
			applyCfgSave(loadConfig());
			Game.mute = mute == 1;
			Game.useMinimap = minimap == 1;
			Game.antiAliasingEnabled = antiAliasing == 1;
		}
	}
	
	public void quitSettingsMenu(String nextState) {
		Button.buttons.removeAll(settingsButtons);
		Game.gameState = nextState;
		Game.ui.settings = null;
	}
}