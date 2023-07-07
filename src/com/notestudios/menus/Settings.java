package com.notestudios.menus;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.notestudios.entities.Entity;
import com.notestudios.main.Game;
import com.notestudios.util.Button;
import com.notestudios.util.Sound;

public class Settings {

	public String[] optOptions = { "currentLanguage", "graphics", "controls", "verInfo",
	"minimap", "mute", "gunTile", "Back", "AAliasing", "Nothing"};
	public int maxOption = optOptions.length-1;
	public static String curLanguage = "";
	public static boolean english = false;
	public static boolean portugues = false;
	public int optMax = optOptions.length - 1;
	public boolean downOpt;
	public boolean upOpt;
	public boolean nextPage = false;
	public int curPage = 0;
	public static int mute = 0;
	public boolean leftOpt;
	public boolean rightOpt;
	public int curOpt = 0;
	public boolean esc = false;
	public boolean versionInfoRequest = true;
	public boolean enter = false;
	public static boolean back = false;
	public static int minimap = 1;
	public static int AntiAliasing = 0;
	
	static class Settings_Button0 extends Button {
		public Settings_Button0(int x, int y, int width, int height, int type, String text) {
			super(x, y, width, height, type, text);
			//directory = "";
		}
		
		public void functions() {
			textOffsetX=-170;
			textOffsetY=-20;
			customFont = MainMenu.menuFont;
			customBackColor = new Color(66, 66, 66);
			customTextColor = Color.white;
			if(selected && clicked) {
				clicked = false;
				Game.downTransition = true;
				Game.gameState = "GJLogin";
			}
		}
	}
	
	
	public Settings_Button0 gjButton;
	
	public Settings() {
		gjButton = new Settings_Button0(52*Game.SCALE+2, (96-45)*Game.SCALE,
				Game.defaultLargeOptionBg.getWidth()*Game.SCALE-34, Game.defaultLargeOptionBg.getHeight()*Game.SCALE-8,
				0, "Game Jolt Login Menu  >");
		Game.button.add(gjButton);
	}
	
	
	public void tick() {
		if (back) {
			back = false;
			Game.gameState = "Menu";
		}
		if (downOpt) {
			downOpt = false;
			if(!Game.mute)
			Sound.menuSelect.play();
			curOpt++;
			if (curOpt > optMax) {
				curOpt = 0;
			}
		}
		if (upOpt) {
			upOpt = false;
			if(!Game.mute)
			Sound.menuSelect.play();
			curOpt--;
			if (curOpt < 0) {
				curOpt = optMax;
			}
		}
		
		if(leftOpt) {
			leftOpt = false;
			if(!Game.mute)
			Sound.menuSelect.play();
			curOpt = 7;
		} if(rightOpt) {
			rightOpt = false;
			if(!Game.mute)
			Sound.menuSelect.play();
			curOpt = 0;
		}
		
		if (esc) {
			esc = false;
			if(!Game.mute) {
				Sound.backMenu.play();
			}
			Game.gameState = "Menu";
			Game.downTransition = true;
			Game.saveConfig = true;
		}
		if (esc && versionInfoRequest == false && Game.gameState.equals("Options")) {
			esc = false;
			Game.downTransition = true;
			Game.gameState = "Menu";
			if (enter) {
				enter = false;
			}
		}
		if (optOptions[curOpt].equals("currentLanguage")) {
			if (MainMenu.english) {
				MainMenu.curLanguage = "English";
				MainMenu.Eng = 1;
				MainMenu.Por = 0;
			} if (MainMenu.portuguese) {
				MainMenu.curLanguage = "Português";
				MainMenu.Eng = 0;
				MainMenu.Por = 1;
			}
			if(enter) {
				enter = false;
				if(!Game.mute) { Sound.menuEnter.play(); }
				Game.npc.curIndex = 0;
				if (MainMenu.english) {
					//english language -> portuguese launguage
					Game.downTransition = true;
					MainMenu.portuguese = true;
					MainMenu.english = false;
					MainMenu.curLanguage = "Português";
					Game.saveConfig = true;
				} else if (MainMenu.portuguese) {
					//portuguese launguage -> english language
					Game.downTransition = true;
					MainMenu.portuguese = false;
					MainMenu.english = true;
					MainMenu.curLanguage = "English";
					Game.saveConfig = true;
				}
			}
		}
		if (optOptions[curOpt].equals("graphics")) {
			if (enter && Game.graphics == 2) {
				enter = false;
				Game.graphics = 1;
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				Game.saveConfig = true;
			} else if (enter && Game.graphics == 1) {
				enter = false;
				Game.graphics = 2;
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				Game.saveConfig = true;
			}
		}
		if (optOptions[curOpt].equals("controls")) {
			if (enter) {
				enter = false;
				Game.gameState = "Controls";
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				Game.downTransition = true;
				Game.saveConfig = true;
			}
		}
		if (optOptions[curOpt].equals("verInfo")) {
			if (enter) {
				enter = false;
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				    try {
						Desktop.getDesktop().browse(new URI("https://gamejolt.com/games/ttraveler/796130"));//gamejolt://play/123456
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
				Game.saveConfig = true;
			}
		}
		if (optOptions[curOpt].equals("Back")) {
			if (enter) {
				enter = false;
				back = true;
				if(!Game.mute) {
					Sound.backMenu.play();
				}
				Game.downTransition = true;
				Game.saveConfig = true;
			}
		}
		
		if (optOptions[curOpt].equals("minimap")) {
			if (enter && !Game.minimapRender) {
				enter = false;
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				Game.minimapRender = true;
				Game.saveConfig = true;
				minimap = 1;
			} else if (enter && Game.minimapRender) {
				enter = false;
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				Game.minimapRender = false;
				Game.saveConfig = true;
				minimap = 0;
			}
		}
		if (optOptions[curOpt].equals("mute")) {
			if (enter && !Game.mute) {
				enter = false;
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				mute = 1;
				Game.mute = true;
				Game.saveConfig = true;
			} else if (enter && Game.mute) {
				enter = false;
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				mute = 0;
				Game.mute = false;
				Game.saveConfig = true;
			}
		}
		if (optOptions[curOpt].equals("gunTile")) {
			if (enter && !Game.arrSelectSprite && Game.graphics == 2) {
				enter = false;
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				Game.arrSelectSprite = true;
				MainMenu.arrSelect = 1;
				Game.saveConfig = true;
			} else if (enter && Game.arrSelectSprite  && Game.graphics == 2) {
				enter = false;
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				Game.arrSelectSprite = false;
				MainMenu.arrSelect = 0;
				Game.saveConfig = true;
			} else if(enter && Game.graphics == 1){
				enter = false;
				if(!Game.mute) {
					Sound.errorSound.play();
				}
			} else {
				enter = false;
			}
		}
		
		if(optOptions[curOpt] != "AAliasing" && optOptions[curOpt] != "Back" && optOptions[curOpt] != "Nothing") {
			nextPage = false;
		} else if(optOptions[curOpt] == "AAliasing") {
			nextPage = true;
		}
		
		if(optOptions[curOpt] == "AAliasing") {
			if(enter && Game.graphics == 1) {
				enter = false;
				if(!Game.mute) {
					Sound.errorSound.play();
				}
			} else if(enter && Game.graphics == 2) {
				enter = false;
				if(!Game.AAliasingEnabled) {
					enter = false;
					if(!Game.mute) {
						Sound.menuEnter.play();
					}
					Game.AAliasingEnabled = true;
					AntiAliasing = 1;
				} else if(Game.AAliasingEnabled) {
					enter = false;
					if(!Game.mute) {
						Sound.menuEnter.play();
					}
					Game.AAliasingEnabled = false;
					AntiAliasing = 0;
				}
				Game.saveConfig = true;
			}
		}
		if(nextPage) {
			gjButton.functions();
		}
	}
	
	
	
	public void render(Graphics2D g) {
		if (Game.gameState == "Options") {
			g.setColor(Color.white);
			if (MainMenu.english) {
				g.setFont(MainMenu.TheFont);
				g.setColor(Color.white);
				g.drawString("Settings", 30, 48);

				g.setFont(MainMenu.aFont);
				g.setColor(Color.white);
				g.drawString("< Back", 30, 106);
				g.setFont(new Font("Segoe UI", Font.BOLD, 12));
				g.drawString("(ESC)", 125, 90);
				if(!nextPage) {
					g.setFont(MainMenu.menuFont);
					g.drawString("Game Language < " + MainMenu.curLanguage + " >", 258, (360 - 180));
					if (Game.graphics == 2) {
						g.drawString("Graphics Quality < " + Game.showGraphics + " >", 278, (432 - 180));
					} else if (Game.graphics == 1) {
						g.drawString("Graphics Quality < " + Game.showGraphics + " >", 280, (432 - 180));
					}
					g.drawString("View Game Controls.", 316, (498 - 180));
					g.drawString("See Current Version Info", 270, (567 - 180));
					if (Game.minimapRender)
						g.drawString("Disable Minimap", 355, (567 + 65 - 178));
					else if (!Game.minimapRender) {
						g.drawString("Enable Minimap", 360, (567 + 65 - 178));
					}
					if (Game.mute) {
						g.drawString("Unmute Sounds", 355/*305*/, (567 + 135 - 180));
					} else if (!Game.mute) {
						g.drawString("Mute Sounds", 375/*325*/, (567 + 135 - 180));
					}
					if(Game.graphics == 2) {
						g.setColor(Color.white);
					} else {
						g.setColor(Color.lightGray);
					}
					if (Game.arrSelectSprite) {
						g.drawString("Disable ArrSelect", 340, (567 + 135 + 65 - 176));
					} else if (!Game.arrSelectSprite) {
						g.drawString("Enable ArrSelect", 342, (567 + 135 + 65 - 176));
					}
					//g.setColor(Color.white);
				} else if(nextPage) {
					gjButton.render(g);
					
					g.setFont(MainMenu.menuFont);
					if(Game.graphics == 2) {
						g.setColor(Color.white);
					} else {
						g.setColor(Color.lightGray);
					}
					g.drawString("Anti-Aliasing < " + Game.AAliasingEnabled + " >", 300, (360 - 180));
					
				}
				
			} else if (MainMenu.portuguese) {
				g.setFont(MainMenu.TheFont);
				g.setColor(Color.white);
				g.drawString("Config.", 48, 48);

				g.setFont(MainMenu.aFont);
				g.setColor(Color.white);
				g.drawString("< Voltar", 25, 106);
				g.setFont(new Font("Segoe UI", Font.BOLD, 12));
				g.drawString("(ESC)", 125, 90);
				
				if(!nextPage) {
					g.setFont(MainMenu.menuFont);
					g.drawString("Idioma do Jogo < " + MainMenu.curLanguage + " >", 231, (360 - 180));
					if (Game.graphics == 2) {
						g.drawString("Qualidade Gráfica < " + Game.showGraphics + " >", 265, ((432 - 2 - 180)));
					} else if (Game.graphics == 1) {
						g.drawString("Qualidade Gráfica < " + Game.showGraphics + " >", 255, ((432 - 2 - 180)));
					}
					g.drawString("Ver Controles do Jogo", 290, (498 - 178));
					g.drawString("Ver Info. da Versão Atual", 265, (567 - 180));
					if (Game.minimapRender)
						g.drawString("Dasativar Minimapa", 322, (567 + 65 - 178));
					else if (!Game.minimapRender) {
						g.drawString("Ativar Minimapa", 352, (567 + 65 - 178));
					}
					if (Game.mute) {
						g.drawString("Ativar os Sons", /*315*/355, (567 + 135 - 178));
					} else if (!Game.mute) {
						g.drawString("Desativar os Sons", /*285*/326, (567 + 135 - 178));
					}
					if(Game.graphics == 2) {
						g.setColor(Color.white);
					} else {
						g.setColor(Color.lightGray);
					}
					if (Game.arrSelectSprite) {
						g.drawString("Desativar o ArrSprite", 295, (567 + 135 + 65 - 176));
					} else if (!Game.arrSelectSprite) {
						g.drawString("Ativar o ArrSprite", 328, (567 + 135 + 65 - 176));
					}
					//g.setColor(Color.white);
				} else if(nextPage) {
					gjButton.render(g);
					g.setFont(MainMenu.menuFont);
					g.drawString("Anti-Aliasing < " + Game.AAliasingEnabled + " >", 300, (360 - 180));
				}
			}
			g.setColor(Color.white);
			if (optOptions[curOpt] == "currentLanguage") {
				if (Game.arrSelectSprite) {
					g.drawImage(Entity.GUN_SELECT, 140, 160, null);
				} else {
					g.drawRoundRect(211, 140, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
				}

			}
			if (back == false && optOptions[curOpt] == "Back") {
				if (Game.arrSelectSprite) {
					g.drawImage(Entity.GUN_SELECT, 220, 85, -48, 32, null);
				} else {
					g.drawRoundRect(-32, 68, 50 * Game.SCALE, 14 * Game.SCALE, 14, 14);
				}
			}
			if (optOptions[curOpt] == "graphics") {
				if (Game.arrSelectSprite) {
					g.drawImage(Entity.GUN_SELECT, 140, 230, null);
				} else {
					g.drawRoundRect(211, 140 + 68, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
				}
			}
			if (optOptions[curOpt] == "controls") {
				if (Game.arrSelectSprite) {
					g.drawImage(Entity.GUN_SELECT, 140, 295, null);
				} else {
					g.drawRoundRect(211, 140 + 68 + 68, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
				}
			}
			if (optOptions[curOpt] == "verInfo") {
				if (Game.arrSelectSprite) {
					g.drawImage(Entity.GUN_SELECT, 140, 360, null);
				} else {
					g.drawRoundRect(211, 140 + 68 + 68 + 68, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
				}

			}
			if (optOptions[curOpt] == "minimap") {
				if (Game.arrSelectSprite) {
					g.drawImage(Entity.GUN_SELECT, 140, 430, null);
				} else {
					g.drawRoundRect(211, 140 + 68 + 68 + 68 + 68, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
				}
			}
			if (optOptions[curOpt] == "mute") {
				if (Game.arrSelectSprite) {
					g.drawImage(Entity.GUN_SELECT, 140, 498, null);
				} else {
					g.drawRoundRect(211, 140 + 68 + 68 + 68 + 68 + 68, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
				}
			}
			if (optOptions[curOpt] == "gunTile") {
				if (Game.arrSelectSprite) {
					g.drawImage(Entity.GUN_SELECT, 140, 568, null);
				} else {
					g.drawRoundRect(211, 140 + 68 + 68 + 68 + 68 + 68 + 68, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
				}
			}
			if (optOptions[curOpt] == "AAliasing") {
				if (Game.arrSelectSprite) {
					g.drawImage(Entity.GUN_SELECT, 140, 160, null);
				} else {
					g.drawRoundRect(211, 140, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
				}
			}
			if (optOptions[curOpt] == "verInfo" && versionInfoRequest) {
				//JOptionPane.showMessageDialog(null, "(Info. text here)");
				g.setFont(MainMenu.aFont);
				g.setColor(Color.yellow);
				g.drawString("" + Game.currentVersion, 810, 375);
				g.setFont(new Font("Segoe UI", Font.BOLD, 16));
				g.setColor(Color.white);
				if(MainMenu.portuguese) {
					g.drawString(""+Game.lastUpdatePt, 810, 390);
					//g.drawString("You can now download the newest version clicking here!", 268, 420);
				} else if(MainMenu.english) {
					g.drawString(""+Game.lastUpdateEn, 810, 390);
					//g.drawString("You can now download the newest version clicking here!", 268, 420);
				}
			}
			
			if(nextPage) {
				g.setColor(Color.lightGray);
				g.fillRect(Game.WIDTH*Game.SCALE-8, 278/*28*/+7, 8, Game.HEIGHT*2);
				g.setColor(Color.white);
				g.fillRect(Game.WIDTH*Game.SCALE-8, 278, 8, Game.HEIGHT*2);
			} else if(!nextPage) {
				g.setColor(Color.lightGray);
				g.fillRect(Game.WIDTH*Game.SCALE-8, 40+7, 8, Game.HEIGHT*2);
				g.setColor(Color.white);
				g.fillRect(Game.WIDTH*Game.SCALE-8, 40, 8, Game.HEIGHT*2);
			}
			
			if(!nextPage) {
				g.setFont(new Font("Segoe UI", Font.BOLD, 16));
				g.setColor(Game.defaultBgColor);
				g.fillRoundRect(700, 545, 55, 20, 16, 16);
				g.setColor(Color.yellow);
				g.drawString("BETA", 709, 361+200);
				g.setColor(Color.white);
				g.drawRoundRect(700, 545, 55, 20, 16, 16);
			} else if(nextPage) {
				g.setFont(new Font("Segoe UI", Font.BOLD, 16));
				g.setColor(Game.defaultBgColor);
				g.fillRoundRect(700, 545-410, 55, 20, 16, 16);
				g.setColor(Color.yellow);
				g.drawString("Beta", 711, 361-210);
				g.setColor(Color.white);
				g.drawRoundRect(700, 545-410, 55, 20, 16, 16);
			}
		}
	}
	
	public static void applyCfgSave(String str) {
		String[] spl = str.split("/");
		for (int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch (spl2[0]) {
			//optOptions = { "currentLanguage", "graphics", "controls", "verInfo",
			//"minimap", "mute", "gunTile", "Back", "AAliasing", "Nothing"};
			case "english":
				MainMenu.Eng = Integer.parseInt(spl2[1]);
				break;
			case "portugues":
				MainMenu.Por = Integer.parseInt(spl2[1]);
				break;
			case "quality":
				Game.graphics = Integer.parseInt(spl2[1]);
				break;
			case "arrowSelect":
				MainMenu.arrSelect = Integer.parseInt(spl2[1]);
				break;
			case "mute":
				mute = Integer.parseInt(spl2[1]);
				break;
			case "minimap":
				minimap = Integer.parseInt(spl2[1]);
				break;
			case "anti-aliasing":
				AntiAliasing = Integer.parseInt(spl2[1]);
				break;
			case "showPopup":
				Game.showPopup = Integer.parseInt(spl2[1]);
				break;
			}
			
		}
	}

	public static String loadConfig() {
		String line = "";
		File save = new File("config.save");
		if (save.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("config.save"));
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

	public static void saveConfig(String[] val1, int[] val2) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("config.save"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";

			char[] value = Integer.toString(val2[i]).toCharArray();

			for (int n = 0; n < value.length; n++) {
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
	
}