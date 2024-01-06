package com.notestudios.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.main.Window;
import com.notestudios.util.Button;
import com.notestudios.util.Sound;

public class MainMenu {
	
	public static String curLanguage = "English";
	public static boolean english = true;
	public static boolean portuguese = false;
	public boolean exitRequest = false;
	public static PauseMenu pauseMenu;
	public static int Eng = 0;
	public static int Por = 0;
	public static boolean esc;
	public short curEncode = 0;
	public static BufferedImage menuCreditsIcon;
	public boolean up, down, left, right, enter;
	public boolean loadGameSave = false;
	
	public static InputStream streamFont = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font aFont;
	public static InputStream Font1 = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font UIFont;
	public static InputStream Font2 = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font TheFont;
	public static InputStream dFontStream = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font DialogFont;
	
	public Button playBtn = new Button(PauseMenu.x + 30, 290, 340, 50, "Play") {
		
		public void functions() {
			customBackColor = new Color(220, 220, 0);
			customTextColor = Color.black;
			if(!pauseMenu.pauseMode) {
				x = (Game.window.getWidth()/2) - (getWidth()/2);
				y = 290;
			} else {
				x = PauseMenu.x + 30;
				y = 290;
			}
			if(english) {
				if(pauseMenu.pauseMode) 
					text = "Resume";
				else
					text = "Play Game";
			} else {
				if(pauseMenu.pauseMode)
					text = "Continuar Jogo";
				else
					text = "Novo Jogo";
			}
			if(clicked) {
				clicked = false;
				if(pauseMenu.pauseMode) 
					pauseMenu.closePauseMenu = true;
				else 
					Game.gameState = "Normal";
				
				if(!Game.mute) {
					if(!pauseMenu.pauseMode) 
						Sound.newGame.play();
					else 
						Sound.unpauseGame.play();
				}
				Game.ui.menu.exitRequest = false;
				Game.ESC = false;
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
			g.setColor(customBackColor);
			g.fillRoundRect((int)x, (int)y, getWidth(), getHeight(), arc, arc);
			if(selected || selectAlpha > 0) {
				g.setColor(new Color(customBackColor.getRed() - 20, customBackColor.getGreen() - 20, 0, selectAlpha));
	            g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), arc, arc);
	        }
			if(!unavailable) 
				g.setColor(customTextColor);
			else 
				g.setColor(Color.gray);
			if(customFont == Game.menuFont2) 
				g.setFont(new Font(customFontString, Font.BOLD, fontSize));
			else {
				g.setFont(customFont);
			}
			g.drawString(text, getX() + ((int) width / 2) - (g.getFontMetrics().stringWidth(text)/2) + textOffsetX,
					getY() + (g.getFontMetrics().getHeight()/2)+textOffsetY);
		}
	};
	
	public Button loadBtn = new Button(PauseMenu.x + 30, 50, 340, 50, "Load Game") {
		public void functions() {
			if(!pauseMenu.pauseMode) {
				x = (Game.window.getWidth()/2) - (getWidth()/2);
				y = playBtn.getY() + playBtn.getHeight() + 6;
			} else {
				x = PauseMenu.x + 30;
				y = playBtn.getY() + playBtn.getHeight() + 6;
			}
			if(english)
				text = "Load Game";
			else 
				text = "Carregar um Jogo";
			
			if(clicked) {
				clicked = false;
				if(Game.world.saveFile.exists()) {
					if(!Game.mute) Sound.loadSave.play();
					UI.doTransition = true;
					loadGameSave = pauseMenu.pauseMode;
					if(!pauseMenu.pauseMode) { load(); }
				} else { if(!Game.mute) Sound.errorSound.play(); }
			}
			if(loadGameSave) {
				pauseMenu.closePauseMenu = true;
			}
		}
	};
	
	public void load() {
		String saver = Game.world.loadGame(curEncode);
		Game.world.applySave(saver);
		Game.cutsceneState = Game.finishCutscene;
	}
	
	public Button settingsBtn = new Button(PauseMenu.x + 30, 50, 340, 50, "Settings") {
		
		public void functions() {
			if(!pauseMenu.pauseMode) {
				x = loadBtn.getX();
				y = loadBtn.getY() + loadBtn.getHeight() + 6;
			} else {
				x = PauseMenu.x + 30;
				y = loadBtn.getY() + loadBtn.getHeight() + 6;
			}
			width = 340 - creditsBtn.getWidth() - 6;
			if(english) 
				text = "Settings";
			else 
				text = "Configurações";
			
			if(clicked) {
				clicked = false;
				if(!Game.mute) Sound.menuEnter.play();
				Game.gameState = "Settings";
				Game.ui.menu.exitRequest = false;
				UI.doTransition = true;
			}
		}
		
	};
	
	public Button creditsBtn = new Button(50, 50, 50, 50, "Credits") {
		
		public void functions() {
			text = "Credits";
			buttonImage = MainMenu.menuCreditsIcon;
			imageOffsetX = 1;
			imageOffsetY = 1;
			
			x = settingsBtn.getX() + settingsBtn.getWidth() + 6;
			y = loadBtn.getY() + loadBtn.getHeight() + 6;
			customImageW = buttonImage.getWidth() * 2;
			customImageH = buttonImage.getHeight() * 2;
			if(selected && clicked) {
				clicked = false;
				if(!Game.mute) Sound.menuEnter.play();
				UI.doTransition = true;
				Game.gameState = "Credits";
			}
		}
		
	};
	
	public Button exitBtn = new Button(PauseMenu.x + 30, 50, 340, 50, "<  Quit") {
		
		public void functions() {
			customBackColor = new Color(50, 40, 40);
			if(!pauseMenu.pauseMode) {
				x = (Game.window.getWidth()/2) - (getWidth()/2);
				y = Game.window.getHeight() - 90;
			} else {
				x = PauseMenu.x + 30;
				y = PauseMenu.height - 60;
			}
			
			if(english)
				text = "<  Quit Game";
			else
				text = "<  Sair do Jogo";
			if(clicked) {
				clicked = false;
				if(Game.ui.menu.exitRequest) {
					Game.ui.menu.exitRequest = false;
				} else if(!Game.ui.menu.exitRequest) {
					Game.ui.menu.exitRequest = true;
				}
			}
		}
		
	};
	
	public Button yesBtn = new Button(PauseMenu.x + 30, -70, 70, 40, "Yes") {
		public void functions() {
			if(!exitRequest)
				unavailable = true;
			else
				unavailable = false;
			
			textOffsetY = 10;
			width = 90;
			height = 40;
			if(english)
				text = "Yes";
			else
				text = "Sim";
			if(!pauseMenu.pauseMode) {
				x = ((Game.window.getWidth()/2)) - (width/2) - (cancelBtn.getWidth()/2);
				y = PauseMenu.y + 100;
			} else {
				x = PauseMenu.x + (PauseMenu.width/2) - (width/2) - 60;
				y = PauseMenu.y + 100;
			}
			if(clicked) {
				clicked = false;
				try {
					Game.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					JOptionPane.showInternalMessageDialog(null, "An error occurred:\n"+e);
				} finally {
					Game.exit(0);
				}
			}
		}
	};
	
	public Button cancelBtn = new Button(PauseMenu.x + 30, 50, 90, 40, "Cancel") {
		
		public void functions() {
			if(!exitRequest)
				unavailable = true;
			else
				unavailable = false;
			
			textOffsetY = 10;
			if(english)
				text = "Cancel";
			else
				text = "Cancelar";
			x = yesBtn.getX() + yesBtn.getWidth() + 6;
			y = yesBtn.getY();
			width = 130;
			height = 40;
			if(clicked) {
				clicked = false;
				Game.ui.menu.exitRequest = false;
			}
		}
	};
	
	
	public static List<Button> menuButtons;
	
	public MainMenu() {
		Game.showLoginPopup = !Game.jolt.isLoggedIn && !Game.gamejoltCredentialsFile.exists()
				&& !Game.gameCredentialsFile.exists();
		
		Game.window.info(Window.frame).setTitle("The Traveler | "+Game.randomText);
		
		menuButtons = Collections.unmodifiableList(Arrays.asList(
				playBtn,
				loadBtn,
				settingsBtn,
				creditsBtn,
				exitBtn,
				yesBtn,
				cancelBtn
		));
		Button.buttons.addAll(menuButtons);
		pauseMenu = new PauseMenu();
	}
	
	public void tick() {
		if(Game.devMode) {
			curEncode = 0;
		} else if(!Game.devMode) {
			curEncode = 5;
		}
		if(Eng == 1 && Por == 0) {
			english = true;
			portuguese = false;
		} else if(Por == 1 && Eng == 0) {
			english = false;
			portuguese = true;
		}

		for(Button b : MainMenu.menuButtons) {
			boolean isPopupButton = b.equals(Game.ui.menu.yesBtn) || b.equals(Game.ui.menu.cancelBtn);
			if(isPopupButton && Game.ui.menu.exitRequest) {
				b.functions();
			} else if(!isPopupButton) {
				b.functions();
			}
				
		}
	}

	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);

		g.setColor(new Color(39, 39, 39, 220));
		g.fillRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
		g.setColor(Color.white);
		g.drawRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);

		if(!exitRequest) {
			g.setFont(aFont);
			g.setColor(Color.gray);
			g.drawString("The", (Game.window.getWidth()/2) - (g.getFontMetrics().stringWidth("The")/2)+2, 82);
			g.setColor(Color.white);
			g.drawString("The", (Game.window.getWidth()/2) - (g.getFontMetrics().stringWidth("The")/2), 80);
			
			g.setFont(Game.travelerLogoFont);
			g.setColor(Color.gray);
			g.drawString("TRAVELER", (Game.window.getWidth()/2) - (g.getFontMetrics().stringWidth("TRAVELER")/2) + 5, 170);
			g.setColor(Color.yellow);
			g.drawString("TRAVELER", (Game.window.getWidth()/2) - (g.getFontMetrics().stringWidth("TRAVELER")/2), 165);
			
		} if(exitRequest) {
			g.setFont(MainMenu.DialogFont);
			g.setColor(Color.white);
			String exitText;
			String exitWarningText;
			if(MainMenu.english) {
				exitText = "Do you really want to quit?";
				exitWarningText = "(All unsaved data will be lost)";
			} else {
				exitText = "Você realmente deseja sair?";
				exitWarningText = "(Dados não salvos serão perdidos)";
			}
			g.drawString(exitText, (Game.window.getWidth()/2) - 
					(g.getFontMetrics().stringWidth(exitText)/2), 60);
			g.setFont(Game.menuFont2);
			g.drawString(exitWarningText, (Game.window.getWidth()/2) - (g.getFontMetrics().stringWidth(exitWarningText)/2),
					90);
		}

		g.setFont(aFont);
		for(Button b : MainMenu.menuButtons) {
			boolean isPopupButton = b.equals(Game.ui.menu.yesBtn) || b.equals(Game.ui.menu.cancelBtn);
			if(isPopupButton && Game.ui.menu.exitRequest) 
				b.render(g);
			else if(!isPopupButton)
				b.render(g);
		}

		if(Game.debugMode) {
			g.setFont(aFont);
			g.setColor(Color.yellow);
			g.drawString(Game.currentVersion + " | Debug Mode", 10, 630);
			g.setFont(TheFont);
			g.setColor(Color.orange);
			g.drawString("Debug", 430, 90);
		}
	}
}