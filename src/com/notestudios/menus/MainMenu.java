package com.notestudios.menus;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.notestudios.entities.Entity;
import com.notestudios.main.Game;
import com.notestudios.main.Sound;
import com.notestudios.world.World;

public class MainMenu {

	public String[] options = { "novo_jogo", "carregar_jogo", "options", "sair", "Credits", "nothing" };
	public static String curLanguage = "English";
	public static boolean english = true;
	public static boolean portugues = false;
	public boolean optNew = true;
	public boolean exitYes = false;
	public boolean exitNo = false;
	public boolean exitRequest = false;
	public static int arrSelect = 0;
	public static int Eng = 0;
	public static int Por = 0; 
	public static boolean esc;
	public boolean moreInfoReq = false;
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	public int curEncode = 0;
	public boolean pause;
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	public static boolean enter;
	public boolean exitYesSelect = false;
	public boolean exitNoSelect = false;
	
	static File save = new File("game.save");

	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font fontPixel;

	public InputStream streamFont = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font aFont;

	public InputStream inputFont = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font menuFont;

	public InputStream Font1 = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font UIFont;

	public InputStream Font2 = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font TheFont;

	public InputStream Font3 = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font RFont;
	
	public InputStream dFontStream = ClassLoader.getSystemClassLoader().getResourceAsStream(Game.AlterebroFontDir);
	public static Font DialogFont;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;

	public MainMenu() {
		try {
			fontPixel = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(140f);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			TheFont = Font.createFont(Font.TRUETYPE_FONT, Font3).deriveFont(80f);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			UIFont = Font.createFont(Font.TRUETYPE_FONT, Font1).deriveFont(55f);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		try {
			DialogFont = Font.createFont(Font.TRUETYPE_FONT, dFontStream).deriveFont(45f);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		try {
			aFont = Font.createFont(Font.TRUETYPE_FONT, streamFont).deriveFont(50f);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			RFont = Font.createFont(Font.TRUETYPE_FONT, Font2).deriveFont(50f);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			menuFont = Font.createFont(Font.TRUETYPE_FONT, inputFont).deriveFont(68f);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void tick() {
		
		if(Game.dev) {
			curEncode = 0;
		} else if(!Game.dev) {
			curEncode = 5;
		}
		
		if(Eng == 1 && Por == 0) {
			english = true;
			portugues = false;
		} else if(Por == 1 && Eng == 0) {
			english = false;
			portugues = true;
		}

		if (save.exists()) {
			saveExists = true;
		} else {
			saveExists = false;
		}

		if (up) {
			up = false;
			currentOption--;
			if (currentOption < 0) {
				currentOption = maxOption;
			} if(!Game.mute) {
				Sound.menuSelect.play();
			}
		}

		if (down) {
			down = false;
			currentOption++;
			if (currentOption > maxOption) {
				currentOption = 0;
			} if(!Game.mute) {
				Sound.menuSelect.play();
			}
		}
		
		if (right) {
			right = false;
			currentOption = 4;
			if(!Game.mute) {
				Sound.menuSelect.play();
			}
		}
		
		if (left) {
			left = false;
			currentOption = 2;
			if(!Game.mute) {
				Sound.menuSelect.play();
			}
		}
		
		if (options[currentOption] == "Credits") {
			if (enter) {
				enter = false;
				Game.boolCredits = true;
				if(!Game.mute) {
					Sound.menuEnter.play();
				}
				Game.gameState = "Credits";
				Game.transition = true;
			}
		}
		
		if(esc && Game.gameState == "Menu" && !pause && !Game.ESC) {
			esc = false;
			Game.transition = true;
			Game.gameState = "Start Menu";
		}
		
		if (enter) {
			if(!Game.mute && currentOption != maxOption && options[currentOption] 
					!= "carregar_jogo" && options[currentOption] != "novo_jogo") {
				Sound.menuEnter.play();
			}
			if (options[currentOption] == "novo_jogo") {
				enter = false;
				Game.gameState = "Normal";
				if(!Game.mute) {
					if(!pause)
						Sound.newGame.play();
					else if(pause){
						Sound.unpauseGame.play();
					}
				}
				exitRequest = false;
				exitYes = false;
				exitNo = false;
				pause = false;
				//Game.transition = true;

			} else if (options[currentOption] == "sair") {
				enter = false;
				if(exitRequest) {
					exitRequest = false;
				} else if(!exitRequest) {
					exitRequest = true;
				}
			}
			if (options[currentOption] == "carregar_jogo") {
				enter = false;
				exitRequest = false;
				exitYes = false;
				exitNo = false;
				if (save.exists()) {
					Game.loadGame = true;
					String saver = loadGame(/*encode*/curEncode);
					applySave(saver);
					if(!Game.mute) {
						Sound.loadSave.play();
					}
				}
				Game.estadoCena = Game.jogando;
			} else if (options[currentOption] == "options") {
				Game.gameState = "Options";
				optNew = false;
				enter = false;
				exitRequest = false;
				exitYes = false;
				exitNo = false;
				Game.transition = true;
			}
		}
		if (exitRequest) {
			if (exitYes) {
				exitRequest = false;
				exitYes = false;
				exitNo = false;
				
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Runtime.getRuntime().exit(0);
				
			}
			if (exitNo) {
				exitYes = false;
				exitRequest = false;
				exitNo = false;
			}
		}
		
		if(exitRequest) {
			if(exitYesSelect && enter) {
				exitYesSelect = false;
				enter = false;
				exitYes = true;
			} if(exitNoSelect && enter) {
				exitNoSelect = false;
				enter = false;
				exitNo = true;
			}
		}
		
	}

	public static void applySave(String str) {
		String[] spl = str.split("/");
		for (int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch (spl2[0]) {
			case "level":
				World.restartGame("level" + spl2[1] + ".png");
				Game.gameState = "Normal";
				break;

			case "px":
				Game.player.x = Integer.parseInt(spl2[1]);
				break;

			case "py":
				Game.player.y = Integer.parseInt(spl2[1]);
				break;

			case "life":
				Game.player.life = Integer.parseInt(spl2[1]);
				break;

			case "coins":
				Game.playerCoins = Integer.parseInt(spl2[1]);
				break;

			case "ammo":
				Game.player.ammo = Integer.parseInt(spl2[1]);
				break;
			}
		}
	}

	public static String loadGame(int encode) {
		String line = "";
		if (save.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("game.save"));
				try {
					while ((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for (int i = 0; i < val.length; i++) {
							val[i] -= encode;
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

	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("game.save"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";

			char[] value = Integer.toString(val2[i]).toCharArray();

			for (int n = 0; n < value.length; n++) {
				value[n] += encode;
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

	public void render(Graphics2D g) {
		/*
		g.setFont(RFont);
		g.setColor(Color.white);
		g.drawString("Ok...", 580, 320);*/
		/*if(Game.firstTimeInGame) {
			
			g.setColor(Color.white);
			g.setFont(Game.fontPixel2);
			Game.drawString(g, "It seems that it's your first time\n"
					+ "playing this game, i recommend to check\n"
					+ "the controls on 'Settings > Game Controls'!\n"
					+ "This is the early access of the game, so\n"
					+ "if you can, please send feedback to me\n"
					+ "for improvements! DM me: Retrozinn#6868", 480, 150);
			
		}*/
		
		if (!Game.licenseOK) {
			if (!Game.debug) {
				g.setFont(Game.menuFont2);
				g.setColor(new Color(255, 255, 0));
				g.drawString("The Traveler " + Game.currentVersion + " | Where is the license file?", 5, 630);
			}
		} if(Game.isLoggedIn) {
			String currentUser = Game.gjUser.getName();
			g.setFont(Game.fontPixel2);
			g.setColor(Color.white);
			g.drawString("User: @"+currentUser, 5, 630);
		}

		
		/* carregar jogo */
		if (options[currentOption] == "carregar_jogo") {
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 18));
			g.setColor(Color.white);
			if (save.exists()) {
				if (english && curLanguage == "English") {
					g.drawString("An game already exist!", 753, 631);
					g.setColor(Color.green);
					g.fillOval(728, 620, 10, 10);
					g.setColor(Color.black);
					g.drawOval(728, 620, 10, 10);
				} else if (portugues) {
					g.setColor(Color.green);
					g.fillOval(720 + 30, 620, 10, 10);
					g.setColor(Color.black);
					g.drawOval(720 + 30, 620, 10, 10);
					g.setColor(Color.white);
					g.drawString("O arquivo já existe!", 770, 631);
				}
			} else {
				if (english && curLanguage == "English") {
					g.setColor(Color.red);
					g.fillOval(718 + 14, 619, 10, 10);
					g.setColor(Color.black);
					g.drawOval(718 + 14, 619, 10, 10);
					g.setColor(Color.white);
					g.drawString("The file doesn't exist!", 750, 630);
				} else if (portugues) {
					g.setColor(Color.red);
					g.fillOval(718 + 22, 618, 10, 10);
					g.setColor(Color.black);
					g.drawOval(718 + 22, 618, 10, 10);
					g.setColor(Color.white);
					g.drawString("O arquivo não existe!", 760, 630);
				}
			}

		}
		/***/

		/* sair */
		if (options[currentOption].equals("sair")) {
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 18));
			if (english && curLanguage == "English") {
				g.drawString("Exit game", 845, 630);
			} else if (portugues) {
				g.drawString("Sair do Jogo", 837, 630);
			}
		}
		/***/
		/* novo jogo */
		if (options[currentOption] == "novo_jogo") {
			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 18));
			if (english && curLanguage == "English") {
				if (pause) {
					g.drawString("Return to Game", 800, 630);
				} else {
					g.drawString("Starts the Game", 805, 630);
				}
			} else if (portugues) {
				if (pause) {
					g.drawString("Retorna ao Jogo", 800, 630);
				} else {
					g.drawString("Inicia o Jogo", 815, 630);
				}
			}
		}
		/***/
		/* options */
		if (options[currentOption] == "options") {

			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 18));
			if (english) {
				g.drawString("Change Settings", 805, 630);
			} else if (portugues) {
				g.drawString("Opções do Jogo", 804, 630);
			}
		}
		/***/
		/*Credits*/
		if (options[currentOption] == "Credits") {

			g.setColor(Color.white);
			g.setFont(new Font("Segoe UI", Font.BOLD, 18));
			if (english) {
				g.drawString("See Credits", 840, 630);
			} else if (portugues) {
				g.drawString("Ver Créditos", 835, 630);
			}
		}
		/***/
		if (!exitRequest) {
			
			g.setFont(Game.newFont);
			g.setColor(Color.white);
			g.drawString("The", 220-15, 80);

			g.setFont(Game.travelerLogoFont);
			
			g.setColor(Color.gray);
			g.drawString("TRAVELER", 80-35, 170);
			
			g.setColor(Color.yellow);
			g.drawString("TRAVELER", 80-40, 165);
			
			/*
			g.drawImage(Spritesheet.titleScreenLogo, -45, 20, 300*2, 100*2, null);*/
		}

		if (!Game.debug) {
		} else if (Game.debug && !exitRequest) {
			g.setFont(new Font("Segoe UI", Font.BOLD, 17));
			g.setColor(Color.yellow);
			g.drawString(Game.currentVersion + " | DEBUG", 10, 630);
			g.setFont(TheFont);
			g.setColor(Color.orange);
			g.drawString("Debug", 430, 90);
		}

		/* novo jogo */
		g.setFont(menuFont);
		if(currentOption == 0) {
			g.setColor(Color.white);	
		} else {
			g.setColor(Color.lightGray);
		}
		if (english) {
			if (pause) {
				g.drawString("Resume Game", 85, 400);
			} else {
				g.drawString("Play Game", 102, 402);
			}
		} else if (portugues) {
			if (pause) {
				g.drawString("Voltar ao Jogo", 75, 404);
			} else {
				g.drawString("Novo Jogo", 105, 404);
			}
		}
		/***/

		/* carregar jogo */
		if (!save.exists() || currentOption != 1) {
			g.setColor(Color.lightGray);
		} else {
			g.setColor(Color.white);
		}
		if (english) {
			g.drawString("Load Game", 88, 462);
		} else if (portugues) {
			g.drawString("Carregar", 105, 464);
		}
		/***/
		if(currentOption == 2) {
			g.setColor(Color.white);	
		} else {
			g.setColor(Color.lightGray);
		}
		/* options */
		if (english)
			g.drawString("Settings", 102, 522);
		else if (portugues)
			g.drawString("Config.", 120, 524);
		/***/
		g.setFont(aFont);
		g.setColor(Color.white);
		/*Credits*/
		/*IDEIA: Fazer o botão créditos se estender
		 * mostrando a palavra completa com o menu
		 * estendido.*/
		g.drawImage(Game.menuCreditsIcon, 358, 485, 16*3, 16*3, null);
		if(currentOption == 4) {
			if(english) {
				g.drawString("Credits", 400, 520);	
			}else if(portugues) {
				g.drawString("Créditos", 401, 520);
			}
		} else {
			//g.drawString("Cr", 365, 520);
		}
		/***/
		g.setFont(menuFont);
		if(currentOption == 3) {
			g.setColor(Color.white);
		} else {
			g.setColor(Color.lightGray);
		}
		/* sair */
		if (english)
			g.drawString("Quit Game", 95, 580);
		else if (portugues)
			g.drawString("Sair do Jogo", 72, 584);
		/***/
		g.setColor(Color.white);
		/* option indicator */
		if (options[currentOption] == "novo_jogo") {
			if (Game.arrSelectSprite) {
				g.drawImage(Entity.GUN_SELECT, 470, 370, -48, 32, null);
			} else {
				g.setColor(Color.white);
				g.drawRoundRect(-132, 351, 135 * Game.SCALE, 16 * Game.SCALE, 14, 14);
			}
		} else if (options[currentOption] == "carregar_jogo") {
			if (Game.arrSelectSprite) {
				g.drawImage(Entity.GUN_SELECT, 430, 435, -48, 32, null);
			} else {
				g.setColor(Color.white);
				g.drawRoundRect(-177, 420, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
			}
		}
		if (options[currentOption] == "options") {
			if (Game.arrSelectSprite) {
				g.drawImage(Entity.GUN_SELECT, 450, 490, -48, 32, null);
			} else {
				g.setColor(Color.white);
				g.drawRoundRect(-177, 480, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
			}
		} else if (options[currentOption] == "sair") {
			if (Game.arrSelectSprite) {
				g.drawImage(Entity.GUN_SELECT, 430, 550, -48, 32, null);
			} else {
				g.setColor(Color.white);
				g.drawRoundRect(-177, 480 + 60, 135 * Game.SCALE, 14 * Game.SCALE, 14, 14);
			}
		} if (options[currentOption] == "Credits") {
			if (Game.arrSelectSprite) {
				g.drawImage(Entity.GUN_SELECT, 525, 490, -48, 32, null);
			} else {
				g.setColor(Color.white);
				if(portugues) {
					g.drawRoundRect(356, 480, 42 * Game.SCALE, 14 * Game.SCALE -1, 16, 16);
				}else if(english) {
					g.drawRoundRect(356, 480, 37 * Game.SCALE -1, 14 * Game.SCALE -1, 16, 16);
				}
			}
		}
		if (pause) {
			
			g.drawImage(Entity.UIPause, 80, 185, 16*8, 16*8, null);
			
			g.setColor(Color.white);
			g.setFont(TheFont);
			if (portugues)
				g.drawString("Pausado", 220, 272);
			else if (english)
				g.drawString("Paused", 220, 272);
		}

		if (exitRequest) {
			if(Game.gameState != "Menu") {
				exitRequest = false;
			}
			g.drawImage(Entity.JoaoNPC_EN, 40, 85, 16*Game.SCALE, 16*Game.SCALE, null);
			g.setFont(RFont);
			g.drawString("?", 90, 105);
			
			if (english) {
				g.setFont(aFont);
				g.drawString("Are you sure you want to quit?", 130, 85);
				
				g.setFont(new Font("Segoe UI", Font.BOLD, 16));
				g.drawString("(All unsaved data will be lost)", 210, 105);
				
				g.setFont(aFont);
				if(exitYesSelect) {
					g.setColor(Color.white);
					g.drawString("Yes", 200, 155);
				}else {
					g.setColor(Color.lightGray);
					g.drawString("Yes", 200, 155);
				}
				if(exitNoSelect) {
					g.setColor(Color.white);
					g.drawString("Cancel", 340, 155);
				} else {
					g.setColor(Color.lightGray);
					g.drawString("Cancel", 340, 155);
				}
				
				if(exitYesSelect) {
					g.setColor(Color.white);
					g.drawRoundRect(190, 120, 68, 50, 14, 14);
				} if(exitNoSelect) {
					g.setColor(Color.white);
					g.drawRoundRect(330, 120, 100, 50, 14, 14);
				}
			} else if (portugues) {
				g.setFont(aFont);
				g.drawString("Tem certeza que quer sair?", 140, 85);
				
				g.setFont(new Font("Segoe UI", Font.BOLD, 16));
				g.drawString("(Dados não salvos serão perdidos)", 190, 105);
				
				g.setFont(aFont);
				if(exitYesSelect) {
					g.setColor(Color.white);
					g.drawString("Sim", 200, 155);
					
					g.drawRoundRect(188, 120, 62, 50, 14, 14);
				} else {
					g.setColor(Color.lightGray);
					g.drawString("Sim", 200, 155);
					
				} if(exitNoSelect) {
					g.setColor(Color.white);
					g.drawString("Cancelar", 320, 155);
					g.drawRoundRect(308, 120, 128, 50, 14, 14);
				} else {
					g.setColor(Color.lightGray);
					g.drawString("Cancelar", 320, 155);
				}
			}
		}
		if (optNew) {
			g.setColor(new Color(0xFF2500));
			g.fillOval(342, 490, 10, 10);
			g.setColor(Color.black);
			g.drawOval(342, 490, 10, 10);
		}
		
	}
}