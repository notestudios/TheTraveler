package com.notestudios.main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.gamejolt.GameJoltAPI;
import org.gamejolt.User;

import com.notestudios.buttons.Button;
import com.notestudios.entities.BigEnemy;
import com.notestudios.entities.Bullets;
import com.notestudios.entities.Enemy;
import com.notestudios.entities.Entity;
import com.notestudios.entities.Npc;
import com.notestudios.entities.Player;
import com.notestudios.graphics.Spritesheet;
import com.notestudios.graphics.UI;
import com.notestudios.menus.Controls;
import com.notestudios.menus.Credits;
import com.notestudios.menus.GJLogin;
import com.notestudios.menus.GameOver;
import com.notestudios.menus.MainMenu;
import com.notestudios.menus.PopupLogin;
import com.notestudios.menus.Settings;
import com.notestudios.menus.Shop;
import com.notestudios.menus.StartMenu;
import com.notestudios.world.WallTile;
import com.notestudios.world.World;

import net.arikia.dev.drpc.DiscordRPC;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener,
MouseMotionListener, MouseWheelListener, WindowListener {
	
	public static final long serialVersionUID = 1L;

	private Thread thread;

	int menuX = 0,  menuY = 0;
	int mx = 0, my = 0;
	int bgY = 0;
	int bgY2 = 160;
	public int bgSpd = 1;
	public int updPresenceTime = 0;
	public int maxUpdPresenceTime = (60*2);
	public static int[] minimapPixels;
	
	public static int gameOverFrames = 0;
	public static int curLevel = 1, maxLevel = 12;
	public static int playerCoins = 0;
	public static int WIDTH = 240;//960
	public static int HEIGHT = 160;//640
	public static int SCALE = 4; //res: 960x640
	public static int graphics = 2;
	public static int entrada = 1;
	public static int jogando = 2;
	public static int estadoCena = entrada;
	public static int minimapEnable = 1;
	public static int showPopup = 1;

	private boolean isRunning = true;
	
	public boolean secret = false;
	public boolean saveGame = false;
	public boolean loadingScreenFinish = false;
	public boolean saveGameScreen = false;
	public boolean loadingScreen = false;
	public boolean loadingScreenAni = false;
	public boolean passouDaTransicao = false;
	public boolean mouseExited = true;
	
	public static boolean AAEnabled = false;
	public static boolean EnterGameOver = false;
	public static boolean selectBackMenu = false;
	public static boolean QExitGame = false;
	public static boolean f1Shop = false;
	public static boolean backControlsEnter = false;
	public static boolean backControlsSelect = false;
	public static boolean isSelectingEnterGame = false;
	public static boolean ENTER = false;
	public static boolean cutsceneCredits = false;
	public static boolean saveConfig = false;
	public static boolean saveLogin = false;
	public static boolean boolCredits = false;
	public static boolean ESC;
	public static boolean minimapRender = true;
	public static boolean mute = false;
	public static boolean debug = false;
	public static boolean arrSelectSprite = false;
	public static boolean licenseOK = false;
	public static boolean loadGame = false;
	public static boolean beta = false;
	public static boolean creditsEnter = false;
	public static boolean pauseSelect = false;
	public static boolean shopSelect = false;
	public static boolean gameMenuEnter = false;
	public static boolean transition = false;
	public static boolean gameProcessStarted = false;
	public static boolean bossFight = false;
	public static boolean dev = false;
	public static boolean isPressingEnter = false;
	public static boolean loginPopup = false;

	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<BigEnemy> bosses;
	public static List<Bullets> bullets;
	public static List<Button> button;
    public static boolean loginSuccessful;
    public static boolean logoutSuccessful;
	
	public static Random rand;
	public static UI ui;
	public static MainMenu menu;
	public static Settings opt;
	public static Controls controls;
	public static Npc npc;
	public static Player player;
	public static World world;
	public static Credits credits;
	public static StartMenu startMenu;
	public static Shop shop;
	public static GameOver gameOver;
	public PopupLogin popupLogin;
	public UpdateRPC updRPC;
	
	public static Color defaultBgColor = new Color(39, 39, 39, 255); /*OLD 15,40,50 | NEW 0xFF1E1E1F*/

	public double logoTime = 0;
	public double logoMaxTime = (60 * 4);/*4 seconds is better*/
	public double saveGameInfoMaxTime = (60 * 4);
	public double curSaveGameTime = 0;
	public double saveGameSpeed = 2;
	public double x1 = 238/*238*/;
	public double opacity = 255;
	public double opacity2 = 255;
	public double opacity3 = 0;
	public double opacity4 = 255;
	public double noteStudiosLogoTime = 0;
	public double noteStudiosLogoMaxTime = (60 * 1.5);
	public static double positionX = 235;
	public static double transitionY = 0;
	public static double transitionSpd = 14.0;

	public static String AlterebroFontDir = "fonts/alterebro_pixel.ttf";
	public static String noteFontDir = "fonts/silkscreen.ttf";

	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font newFont;

	public InputStream stream5 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font warningFont;

	public InputStream streamFont = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font optFont;

	public InputStream stream1 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font fontPixel2;

	public InputStream stream3 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font fontPixel3;

	public InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font menuFont2;

	public InputStream stream4 = ClassLoader.getSystemClassLoader().getResourceAsStream(noteFontDir);
	public static Font noteLogoFont;
	
	public InputStream stream6 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font travelerLogoFont;
	
	private final BufferedImage image;
	public Spritesheet spritesheet;
	public BufferedImage GameBackground;
	public BufferedImage GameBackground2;
	public static BufferedImage minimap;

	File debugFile = new File("debugAccess.txt");
	File licenseFile = new File("LICENSE");
	File devFile = new File("README.md");
	public static File amogus = new File("amogus.txt");

	public static boolean isLoggedIn = false;
	int GAME_ID = 796130;
	private String GAME_PRIVATE_KEY = /***********************************************************************************************/"73979f3cebe0b2ce206ea1abbace9d22";
	public static String USER_NAME = "";
	public static String USER_TOKEN = "";
	public static int[] TROPHIES_IDs = {/*loginAch*/193422, /*SPAM*/193423, /*why he's so big*/193917, /*dev*/193424};
	
	String[] Words = {"this game doesn't have bugs! trust me", "i know, title screen is bugged",
	"oh, here is another bug", "look behind you.", "hey you!", "potatoes", "this is "+currentVersion+"!", "Hey you! Yeah, you!", 
	"fun fact: every 60 seconds on the loading screen, a minute passes. ;)", "'try{image = ImageIO.read(getClass().getResource(path));}catch(IOException e){e.printStackTrace}'",
	"what... just... happened...?", "sorry for the delay :'(", "Somehow Figure just turned into a ninja idk",
	"new menu style lol", "Figure is bugged", "when the impostor is SUS ඞ", "Seek is watching you!", "bruh", "cool",
	"kaboom", "coffee and tea.", "here's your attention again.", "spider-man goes crazy :0", "Got your attention haha ;)", "[...] thread == null!", "this is a random title!",
	currentVersion+" is on!", "lmao", "I know your discord username haha", "Some levels are still buggy, I'm fixing it", "Your Game Jolt login is now being saved locally!"};
	
	String[] tipsEn = {"Press 'C' to save the game! ", "is this a... tip?", "You can run pressing 'Shift'!", "idk whats the next tip bru", 
			"Move using W, A, S, D keys!", "There are maps that you can't use minimap!", "You can use the menus with the arrow keys!", "sorry for the delay.", 
			"Login with you Game Jolt Account!", "Config > Scroll Down > Game Jolt Login", "Use 'Shift' or 'Ctrl' to run!"};
	
	String[] tipsPt = {"Pressione 'C' para salvar o jogo!", "Você pode correr pressionando 'Shift'!", "Você também pode usar o menu com as setas!",
			"qual a próxima dica mesmo?", "Mova-se usando as teclas W, A, S, D!", "Há mapas que o minimapa não pode ser usado!", "me desculpem pela demora.", 
			"Faça login com sua conta Game Jolt!", "Config > Scroll Down > Game Jolt Login", "Use 'Shift' ou 'Ctrl' para correr!"};
	String randomTip;
	public static String showGraphics;
	String randomText;
	public static String currentVersion = "v4.4.2b";
	String newerVersion;
	static String day = "06", month = "06", year = "2023";
	public static String lastUpdateEn = month+"/"+day+"/"+year;
	public static String lastUpdatePt = day+"/"+month+"/"+year;
	public static String appID = "1087444872132313168";
	public static String gameState = "Start Menu";
	
	public static char charPressed;
	
	RPC rpc;
	GJLogin gjlogin;
	public static GameJoltAPI api;
	public static User gjUser;
	JFrame frame;
	
	/*
	 * Game States:
	 * 
	 * Game Over = Game Over screen (obvious);
	 * Menu = Menu mode / pause;
	 * Normal = normal game;
	 * Options = options Menu;
	 * Controls = show controls;
	 * Background = shows game Background;
	 * Shop = opens shop (available RIGHT NOW!);
	 * Credits = Shows the game credits;
	 * Start Menu = game start menu;
	 * GJLogin = Game Jolt login page;
	 * 
	 * NoteStudios GAMES INC.
	 */

	public Game() {
		rand = new Random();
		beta = currentVersion.endsWith("b");
		dev = devFile.exists();
		debug = debugFile.exists();
		secret = amogus.exists();
		randomText = Words[rand.nextInt(Words.length)];
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		initFrame();
		api = new GameJoltAPI(GAME_ID, GAME_PRIVATE_KEY);
		rpc = new RPC();
		ui = new UI();
		spritesheet = new Spritesheet();
		player = new Player(0, 0, 16, 16, Spritesheet.spritesheetPlayer.getSubimage(0, 0, 16, 16));
		try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(55f);
			noteLogoFont = Font.createFont(Font.TRUETYPE_FONT, stream4).deriveFont(120f);
			fontPixel2 = Font.createFont(Font.TRUETYPE_FONT, stream1).deriveFont(35f);
			fontPixel3 = Font.createFont(Font.TRUETYPE_FONT, stream3).deriveFont(200f);
			menuFont2 = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(35f);
			optFont = Font.createFont(Font.TRUETYPE_FONT, streamFont).deriveFont(140f);
			warningFont = Font.createFont(Font.TRUETYPE_FONT, stream5).deriveFont(20f);
			travelerLogoFont = Font.createFont(Font.TRUETYPE_FONT, stream6).deriveFont(160f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, JOptionPane.ERROR,
				"An error occurred while loading fonts\n"+e, JOptionPane.ERROR_MESSAGE);
		}
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bosses = new ArrayList<BigEnemy>();
		bullets = new ArrayList<Bullets>();
		button = new ArrayList<Button>();
		npc = new Npc(32, 32, 16, 16, Spritesheet.spritesheetPlayer.getSubimage(16, 96, 16, 16));
		world = new World("/levels/level"+curLevel+".png");
		entities.add(npc);
		entities.add(player);
		startMenu = new StartMenu();
		popupLogin = new PopupLogin();
		updRPC = new UpdateRPC();
		menu = new MainMenu();
		credits = new Credits();
		shop = new Shop();
		opt = new Settings();
		controls = new Controls();
		gameOver = new GameOver();
		
		gjlogin = new GJLogin();
		
		minimap = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		minimapPixels = ((DataBufferInt) minimap.getRaster().getDataBuffer()).getData();
		
		try {
			GameBackground = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/Background.png")));
			GameBackground2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/Background.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(loadingScreen) {
			Sound.noteStudiosEffect.play();
		}
		loginPopup = true;
		File cfg = new File("config.save");
		if (cfg.exists()) {
			String saver = Settings.loadConfig();
			Settings.applyCfgSave(saver);
		}
		
		File credentials = new File("GJ");
		if(credentials.exists()) {
			GJLogin.autoLogin("GJ");
			showPopup = 0;
			loginPopup = false;
		}
		
		mute = Settings.mute == 1;
		minimapRender = Settings.minimap == 1;
		AAEnabled = Settings.AntiAliasing == 1;
		
		if(MainMenu.Por == 1) {
			randomTip = tipsPt[rand.nextInt(tipsPt.length)];
		} else if(MainMenu.Eng == 1) {
			randomTip = tipsEn[rand.nextInt(tipsEn.length)];
		} else {
			randomTip = "Welcome! Use the Menu with you Keyoboard or mouse!";
		}
		
		gameProcessStarted = true;
		DiscordRPC.discordInitialize(appID, rpc, true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		try { thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace(); }
	}

	public void initFrame() {
		frame = new JFrame();
		frame.add(this);
		//frame.setUndecorated(!frame.isUndecorated()); Just on full screen(alpha)
		frame.setVisible(!frame.isVisible());
		frame.setTitle("The Traveler | " + randomText);
		frame.pack();
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit()
				.getImage(getClass().getResource("/cursors/cur.png")), new Point(0, 0), "img"));
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/Icon.png")));
		frame.setLocationRelativeTo(null);
		
		loadingScreen = true;
	}

	public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

	public static BufferedImage bigBackground;
	public static BufferedImage defaultShortOptBg;
	public static BufferedImage defaultLargeOptionBg;
	public static BufferedImage menuCreditsIcon;

	public static BufferedImage newGameOptionBg;
	public static BufferedImage loadGameOptionBg;
	public static BufferedImage settingsOptionBg;
	public static BufferedImage creditsOptionBg;
	public static BufferedImage quitGameOptionBg;
	
	public void tick() {
		if(gameState.equals("Settings") || gameState.equals("settings")
				|| gameState.equals("config")) { gameState = "Options"; }
		else if(gameState.equals("login") || gameState.equals("gamejolt"))
				{ gameState = "GJLogin"; }
		
		DiscordRPC.discordRunCallbacks();
		rpc.startup();
		updPresenceTime++;
		if(updPresenceTime == maxUpdPresenceTime) {
			updPresenceTime = 0;
			updRPC.tick();
		}
		
		if (graphics == 2) {
			if (MainMenu.portugues) {
				showGraphics = "Alta";
			} else if (MainMenu.english) {
				showGraphics = "High";
			}
		} else if (graphics == 1) {
			arrSelectSprite = false;
			if (MainMenu.portugues) {
				showGraphics = "Baixa";
			} else if (MainMenu.english) {
				showGraphics = "Low";
			}
		}
		
		if(showPopup == 0) {
			loginPopup = false;
		} else {
			loginPopup = true;
		}

		if(MainMenu.arrSelect == 1) {
			arrSelectSprite = true;
		} else if(MainMenu.arrSelect == 0) {
			arrSelectSprite = false;
		}
		player.updateCamera();
		if(boolCredits) {
			if(ESC) {
				ESC = false;
				boolCredits = false;
				gameState = "Menu";
				transition = true;
				if(!mute) {
					Sound.backMenu.play();
				}
			}
		}
		
		if (licenseFile.exists()) {
			licenseOK = true;
		} else if (!licenseFile.exists()) {
			licenseOK = false;
		}

		if (gameState.equals("Normal") && ESC) {
			ESC = false;
			menu.pause = true;
			if(!mute) {
				Sound.pauseGame.play();
			}
			gameState = "Menu";
		}
		if (gameState.equals("Menu") && estadoCena == entrada) {
			player.setX(64);
			player.setY(416);
			player.right = false;
		}
		
		/* mute game sound logic down below */
		if(!mute) {
			switch (gameState) {
				case "Start Menu" -> {
					Sound.creditsMusic.pause();
					Sound.optionsMenuMusic.pause();
					Sound.menuMusicloop.pause();
					Sound.sussyMenuMusic.pause();
					Sound.gameMusic.pause();
				}
				//Sound.startMenuMusic.loop();

				case "Menu" -> {
					Sound.menuMusicloop.setVolume(0.8f);//max is 2f
					Sound.gameMusic.pause();
					Sound.creditsMusic.pause();
					Sound.optionsMenuMusic.pause();
					if (!menu.pause) {
						if (!transition) {
							if (amogus.exists()) {
								Sound.sussyMenuMusic.loop();
							} else {
								Sound.menuMusicloop.loop();
							}
						}
					} else {
						//Sound.menuMusicloop.pause();
						//Sound.sussyMenuMusic.pause();
						if (!transition) {
							if (amogus.exists()) {
								Sound.sussyMenuMusic.loop();
							} else {
								Sound.menuMusicloop.loop();
							}
						}
						//pause menu music here
					}
				}
				case "Normal" -> {
					Sound.creditsMusic.pause();
					Sound.optionsMenuMusic.pause();
					Sound.menuMusicloop.pause();
					Sound.sussyMenuMusic.pause();
					Sound.gameMusic.loop();
				}
				case "Shop" -> {
					Sound.creditsMusic.pause();
					Sound.optionsMenuMusic.loop();
					Sound.menuMusicloop.pause();
					Sound.sussyMenuMusic.pause();
					Sound.gameMusic.pause();
				}
				case "Options" -> {
					Sound.creditsMusic.pause();
					Sound.menuMusicloop.pause();
					Sound.sussyMenuMusic.pause();
					Sound.gameMusic.pause();
					if (!transition) {
						Sound.optionsMenuMusic.loop();
					}
				}
				case "Credits" -> {
					Sound.menuMusicloop.pause();
					Sound.sussyMenuMusic.pause();
					Sound.gameMusic.pause();
					Sound.optionsMenuMusic.pause();
					if (!transition) {
						Sound.creditsMusic.loop();
					}
				}
				case "Game Over" -> {
					Sound.creditsMusic.pause();
					Sound.optionsMenuMusic.pause();
					Sound.menuMusicloop.pause();
					Sound.sussyMenuMusic.pause();
					Sound.gameMusic.pause();
					System.out.println("game-over music not found");
					//game-over music
				}
			}
		} else {
			Sound.creditsMusic.pause();
			Sound.optionsMenuMusic.pause();
			Sound.menuMusicloop.pause();
			Sound.sussyMenuMusic.pause();
			Sound.gameMusic.pause();
		}

		if(secret) {
			if(!gameState.equals("Menu") && !gameState.equals("Normal") && !loadingScreen) {
				secret = false;
				if(!mute) { Sound.secret.play(); }
			}
		}
		if (saveGame) {
			saveGame = false;
			if(!Game.mute) {
				Sound.savedGame.play(); }
			String[] opt1 = { "level", "px", "py", "life", "coins", "ammo"};
			int[] opt2 = { curLevel, (int) player.x, (int) player.y, (int) player.life, playerCoins, player.ammo};
			MainMenu.saveGame(opt1, opt2, /*encode*/menu.curEncode);
		} if (saveConfig) {
			saveConfig = false;
			String[] cfg1 = {"english", "portugues", "quality", "arrowSelect", "minimap", "mute", 
					"minimap", "anti-aliasing", "showPopup"};
			int[] cfg2 = {MainMenu.Eng, MainMenu.Por, graphics, MainMenu.arrSelect, minimapEnable, Settings.mute, Settings.minimap, 
					Settings.AntiAliasing, showPopup};
			Settings.saveConfig(cfg1, cfg2);
		} if (saveLogin) {
		    saveLogin = false;
		    GJLogin.fileSave("GJ", USER_NAME, USER_TOKEN);
		}
		if (gameState.equals("Options")) {
			opt.tick();
		}
		if (f1Shop) {
			gameState = "Shop";
			shop.tick();
		}
		if (gameState.equals("Normal")) {
			QExitGame = false;
			if (estadoCena == jogando) {
				for (int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					e.tick();
				}
				for (int i = 0; i < bullets.size(); i++) {
					bullets.get(i).tick();
				}
			} else if (estadoCena == entrada) {
				if (player.getX() < 162) {
					player.updateCamera();
					player.moved = true;
					player.tick();
					player.right = true;
				} else if (player.getX() >= 162) {
					player.right = false;
					player.updateCamera();
					player.moved = false;
					estadoCena = jogando;
				}
			}
			if (enemies.size() == 0 && bosses.size() == 0 && !bossFight) {
				curLevel++;
				if (curLevel > maxLevel) {
					curLevel = 1;
				}
				World.restartGame("level" + curLevel + ".png");
			}
		}
		if (gameState.equals("Game Over")) { gameOver.tick(); }
		if (gameState.equals("Menu")) {
			player.updateCamera();
			menu.tick();
			if(loginPopup) {
				popupLogin.tick();
			}
		}
		if (gameState.equals("Normal")) {
			ENTER = false;
			ui.tick();
		}
		if (saveGameScreen) {
			curSaveGameTime++;
			if (curSaveGameTime == saveGameInfoMaxTime) {
				curSaveGameTime = 0;
				saveGameScreen = false;
			}
		}
		if (boolCredits) {
			gameState = "Credits";
		}
		
		if(gameState.equals("Normal") && estadoCena == jogando) {
			if(pauseSelect && gameMenuEnter) {
				pauseSelect = false;
				if(!Game.mute) { Sound.pauseGame.play(); }
				gameMenuEnter = false;
				menu.pause = true;
				gameState = "Menu";
			} if(shopSelect && gameMenuEnter) {
				shopSelect = false;
				gameMenuEnter = false;
				if(!Game.mute) { Sound.menuEnter.play(); }
				menu.pause = true;
				f1Shop = true;
			}
		}
		
		if(gameState.equals("Controls")) {
			controls.tick();
		}
		
		if(gameState.equals("Menu") || gameState.equals("Background") 
				|| gameState.equals("Start Menu")) {
			bgY-=bgSpd;
			if(bgY+HEIGHT <= 0) {
				bgY = HEIGHT;
			} bgY2-=bgSpd;
			if(bgY2+HEIGHT <= 0) {
				bgY2 = HEIGHT;
			}
		}
		
		if(gameState.equals("Credits")) {
			credits.tick();
		} if(gameState.equals("Start Menu")) {
			startMenu.tick();
		}
		if(gameState.equals("GJLogin")) {
			gjlogin.tick();
		}

	}
	
	public void renderMiniMap() {
		Arrays.fill(minimapPixels, 0xFF009616);
		for (int xx = 0; xx < World.WIDTH; xx++) {
			for (int yy = 0; yy < World.HEIGHT; yy++) {
				if (World.tiles[xx + (yy * World.WIDTH)] instanceof WallTile) {
					minimapPixels[xx + (yy * World.WIDTH)] = 0xFFFFFFFF;
				}
			}
		}
		/* Player */
		int xPlayer = player.getX() / 16;
		int yPlayer = player.getY() / 16;

		minimapPixels[xPlayer + (yPlayer * World.WIDTH)] = 0xFF0026FF;

		/* NPC */
		int xNPC = npc.getX() / 16;
		int yNPC = npc.getY() / 16;

		minimapPixels[xNPC + (yNPC * World.WIDTH)] = 0xFF9352B6;
		
		/* ENEMY */

        for (Enemy en : enemies) {
            int xENE = en.getX() / 16;
            int yENE = en.getY() / 16;

            minimapPixels[xENE + (yENE * World.WIDTH)] = 0xFFFF0000;
        }
		
		/* Boss */
		
		for(int i = 0; i < bosses.size(); i++) {
			BigEnemy en = bosses.get(i);
			
			int xBENE = en.getX() / 16;
			int yBENE = en.getY() / 16;
			
			minimapPixels[xBENE + (yBENE * World.WIDTH)] = 0xFFFF7FB6;
		}
	}
	
	public static void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		
		if (AAEnabled) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		g.setColor(defaultBgColor);
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		if(!gameState.equals("GJLogin")) {
			world.render(g);
		}
		if(gameState.equals("Menu") || gameState.equals("Normal") || gameState.equals("")) {
			entities.sort(Entity.enSorter);
			for(Entity e : entities) {
				e.render(g);
			}
		}
		for (int i = 0; i < bullets.size(); i++) {
			Bullets bullet = bullets.get(i);
			bullet.render(g);
		}
		if (saveGameScreen && gameState.equals("Normal")) {
			if (MainMenu.portugues) {
				if (x1 > 155 && curSaveGameTime < saveGameInfoMaxTime/2) {
					x1-=saveGameSpeed;
				} else if (curSaveGameTime > saveGameInfoMaxTime/2) {
					x1+=saveGameSpeed;
				}
			} else if (MainMenu.english) {
				if (x1 > 150 && curSaveGameTime < saveGameInfoMaxTime/2) {
					x1-=saveGameSpeed;
				} else if (curSaveGameTime > saveGameInfoMaxTime/2) {
					x1+=saveGameSpeed;
				}
			}
			if(graphics == 2) {
				g.drawImage(defaultLargeOptionBg, (int) x1, 80, null);
			}
		}
		if(estadoCena == entrada && gameState.equals("Normal")) {
			g.setColor(defaultBgColor);
			g.fillRect(0, 0, WIDTH, HEIGHT/5);
			g.fillRect(0, HEIGHT-30, WIDTH, HEIGHT/5);
		}
		
		if(gameState.equals("Normal") && estadoCena == jogando) {
			if(graphics == 2) {
				g.drawImage(defaultShortOptBg, -62, 5, null);
				g.drawImage(defaultShortOptBg, -62, 19, null);
			}
			
			g.drawImage(Entity.UIPause, -2, 4, null);
			g.drawImage(Entity.UIShop, -2, 19, null);
			
		}
		if(gameState.equals("Background")) {
			//Inverted Background
			/*g.drawImage(GameBackground, 0, bgY, null);
				g.drawImage(GameBackground2, GameBackground2.getWidth(), bgY2,
				 -GameBackground2.getWidth(), GameBackground2.getHeight(), null);*/
			if(!menu.pause && graphics == 2) {
				g.drawImage(GameBackground, 0, bgY, null);
				g.drawImage(GameBackground2, 0, bgY2, null);
			}
		} if(gameState.equals("Start Menu")) {
			if(graphics == 2) {
				g.drawImage(GameBackground, 0, bgY, null);
				g.drawImage(GameBackground2, 0, bgY2, null);
				g.drawImage(bigBackground, 8, -20, bigBackground.getWidth()*2, bigBackground.getHeight()*2, null);
				g.drawImage(defaultLargeOptionBg, 26, 130, /*144*/186, 16, null);
			} else if(graphics == 1) {
				g.setColor(new Color(30, 30, 31));
				g.fillRect(0, 0, WIDTH, HEIGHT);
			}
		}
		if (gameState.equals("Menu")) {

			g.setColor(new Color(0, 0, 0, 60));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			if(!menu.pause && graphics == 2) {
				g.drawImage(GameBackground, 0, bgY, null);
				g.drawImage(GameBackground2, 0, bgY2, null);
			}
			g.setColor(defaultBgColor);
			g.rotate(-4.50);
			g.fillRect(-100, -135, WIDTH+100, HEIGHT);
			g.rotate(4.50);
			
			if (graphics == 2) {
				
				if(menu.exitRequest) {
					g.drawImage(bigBackground, -10, 4, 112+40, 50,null);
					//g.drawImage(bigBackground, -50, 5, 200, 50, null);
				}
				
				g.drawImage(defaultLargeOptionBg, -39, 87, null);
				g.drawImage(defaultLargeOptionBg, -39, 89, null);
				g.drawImage(newGameOptionBg, -40, 89, null);//TODO
				g.drawImage(defaultLargeOptionBg, -50, 104, null);
				if(menu.currentOption == 4) {
					if(MainMenu.english) {
						g.drawImage(defaultLargeOptionBg, -15, 119, null);	
					}else if(MainMenu.portugues) {
						g.drawImage(defaultLargeOptionBg, -10, 119, null);
					}
				} else {
					g.drawImage(defaultLargeOptionBg, -39, 119, null);
				}
				g.drawImage(defaultLargeOptionBg, -50, 119, null);
				g.drawImage(defaultLargeOptionBg, -50, 134, null);
				if (menu.options[menu.currentOption].equals("novo_jogo")) {
					if (positionX > 185) {
						positionX -= 1;
					} else if (positionX < 185) {
						positionX += 1;
					}
					g.drawImage(defaultLargeOptionBg, (int) positionX, 149, null);
				}
				if (menu.options[menu.currentOption].equals("carregar_jogo")) {
					if (MainMenu.english) {
						if (positionX > 172) {
							positionX -= 1;
						} else if (positionX < 172) {
							positionX += 1;
						}
					} else if (MainMenu.portugues) {
						if (positionX > 174) {
							positionX -= 1;
						} else if (positionX < 174) {
							positionX += 1;
						}
					}
					g.drawImage(defaultLargeOptionBg, (int) positionX, 149, null);
				}
				if (menu.options[menu.currentOption].equals("options")) {
					if (positionX > 188) {
						positionX -= 1;
					} else if (positionX < 188) {
						positionX += 1;
					}
					g.drawImage(defaultLargeOptionBg, (int) positionX, 149, null);
				}
				if (menu.options[menu.currentOption].equals("sair")) {
					if (positionX > 195) {
						positionX -= 1;
					} else if (positionX < 195) {
						positionX += 1;
					}
					g.drawImage(defaultLargeOptionBg, (int) positionX, 149, null);
				}
				if (menu.options[menu.currentOption].equals("Credits")) {
					if (positionX > 195) {
						positionX -= 1;
					} else if (positionX < 195) {
						positionX += 1;
					}
					g.drawImage(defaultLargeOptionBg, (int) positionX, 149, null);
				}
			}
		}

		if (gameState.equals("Options")) {
			g.setColor(defaultBgColor);
			g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			if (graphics == 2) {
				
				if (opt.optOptions[opt.curOpt].equals("verInfo") && opt.versionInfoRequest && gameState.equals("Options")) {
					g.drawImage(defaultLargeOptionBg, 178, 130 - 45, null);
				}
				g.drawImage(bigBackground, -50, -25, null);
				/* back */g.drawImage(defaultLargeOptionBg, -99, 16, null);
					
				if(!opt.nextPage) {
					g.drawImage(defaultLargeOptionBg, 47, 79 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 96 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 113 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 + 17 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 + 17 + 17 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 + 17 + 17 + 17 - 45, null);
				} else {
					g.drawImage(defaultLargeOptionBg, 47, 79 - 45, null);
					/*g.drawImage(defaultLargeOptionBg, 47, 96 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 113 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 + 17 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 + 17 + 17 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 + 17 + 17 + 17 - 45, null);*/
				}
			}
		}
		
		if (gameState.equals("Normal") && estadoCena == jogando) {
			g.drawImage(Entity.BULLET_EN, 85, 11, null);
			g.drawImage(Entity.COIN_EN, 124, 12, null);
		}

		if (gameState.equals("Controls")) {
			g.setColor(defaultBgColor);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			

			if (graphics == 2) {
				
				g.drawImage(bigBackground, -50, -25, null);

				/* back */g.drawImage(defaultLargeOptionBg, -99, 16, null);
				
				g.drawImage(defaultLargeOptionBg, -40, 50, null);
				g.drawImage(defaultLargeOptionBg, -40, 68, null);
				g.drawImage(defaultLargeOptionBg, -40, 68 + 18, null);
				g.drawImage(defaultLargeOptionBg, -40, 68 + 36, null);
				g.drawImage(defaultLargeOptionBg, -40, 68 + 36 + 18, null);
				
				g.drawImage(defaultLargeOptionBg, 108, 68 + 36 + 18 - 30, null);
				g.drawImage(defaultLargeOptionBg, 108, 68 + 36 - 30, null);
				g.drawImage(defaultLargeOptionBg, 108, 68 + 18 - 30, null);
				g.drawImage(defaultLargeOptionBg, 108, 68 - 30, null);
				g.drawImage(defaultLargeOptionBg, 108, 55 - 35, null);
				g.drawImage(defaultLargeOptionBg, 108, 20 + 90, null);
				g.drawImage(defaultLargeOptionBg, 108, 20 + 90 + 18, null);
				
			}

		}
		
		if(gameState.equals("Game Over")) {
			g.setColor(new Color(0, 0, 0, 120));
			g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			
			g.setColor(defaultBgColor);
			g.rotate(-4.50);
			g.fillRect(-100, -135, WIDTH+100, HEIGHT);
			g.rotate(4.50);//AQUI2
			if(graphics == 2) {
				g.drawImage(bigBackground, -6, 5, 132, 48, null);
				g.drawImage(defaultLargeOptionBg, -11, 85, 160, 16, null);
				g.drawImage(defaultLargeOptionBg, -16, 101, null);
				//g.drawImage(defaultLargeOptionBg, -22, 117, null);
				g.drawImage(defaultShortOptBg, 10, 149, null);
			}
			
		}
		
		g.dispose();
		g = (Graphics2D) bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		File saveFile = new File("game.save");
		if (saveGameScreen && gameState.equals("Normal")) {
			g.setFont(MainMenu.RFont);
			g.setColor(Color.white);
			if (!saveFile.exists()) {
				if (MainMenu.portugues)
					g.drawString("Seu jogo foi salvo!", (int) x1 * Game.SCALE + 53, 362);
				else if (MainMenu.english) {
					g.drawString("Your game is saved!", (int) x1 * Game.SCALE + 51, 362);
				}
			} else if (saveFile.exists()) {
				if (MainMenu.portugues)
					g.drawString("O Jogo foi Atualizado!", (int) x1 * Game.SCALE + 52, 362);
				else if (MainMenu.english) {
					g.drawString("Your save is updated!", (int) x1 * Game.SCALE + 50, 362);
				}
			}
		}
		if(gameState.equals("Normal") && estadoCena == jogando) {
			if(pauseSelect) {
				g.setColor(Color.white);
				g.drawRoundRect(-12, 23, 18 * SCALE, 13 * SCALE, 14, 14);
			} if(shopSelect) {
				g.setColor(Color.white);
				g.drawRoundRect(-12, 80, 18 * SCALE, 13 * SCALE, 14, 14);
			}
		}
		
		if (gameState.equals("Controls")) {
			controls.render(g);
		}
		
		if (npc.showMessage && gameState.equals("Normal")) {
			g.setColor(new Color(/*235, 240, 235*/10,10,11,255));
			g.fillRoundRect(32 + 152+50, 32 + 432, 752-70, 160-10, 16, 16);
			
			g.setColor(new Color(/*20, 10, 12*/240,235,235,255));
			g.drawRoundRect(32 + 152+50, 32 + 432, 752-70, 160-10, 16, 16);
			g.drawRoundRect(32 + 151+50, 32 + 433, 752-70, 158-10, 14, 14);
			
			g.setColor(Color.white);
			g.setFont(MainMenu.menuFont);
			g.drawString("- "+"???"+" -", ((int) (32 + 216) / 2)*SCALE, ((32 + 210) / 2)*SCALE);
			
			g.setFont(MainMenu.DialogFont);
			g.setColor(Color.white);
			drawString(g, npc.falas[0].substring(0, npc.curIndex), 270, 490);
			//g.drawString(npc.frases[1].substring(0, npc.curIndex), 250, 568);
		}
		//comingSoon(g);
		if (f1Shop) {
			shop.render(g);
		}
		if (gameState.equals("Normal") && estadoCena == jogando) {
			ui.render(g);
		}
		if (gameState.equals("Normal") && estadoCena == jogando) {
			g.setFont(MainMenu.aFont);
			g.setColor(Color.white);
			g.drawString(String.valueOf(player.ammo), 420/* 420 */, 92/* 43 */);
			g.drawString(String.valueOf(playerCoins), 570/* 570 */, 92/* 43 */);
		}
		if (gameState.equals("Normal") && estadoCena == jogando) {
			g.setColor(Color.white);
			g.setFont(newFont);
			if (player.life >= 100)
				g.drawString((int) player.life + "/" + (int) player.maxLife, 414/* 120 */, 44/* 44 */);
			else if (player.life < 100)
				g.drawString((int) player.life + "/" + (int) player.maxLife, 416/* 125 */, 44/* 44 */);
		}
		if (gameState.equals("Options")) {
			opt.render(g);
		}
		if (gameState.equals("Game Over")) {
			gameOver.render(g);
		} else if (gameState.equals("Menu")) {
			menu.render(g);
			if(loginPopup) {
				popupLogin.render(g);
			}
		}
		/*
		double angleMouse = Math.atan2(my - 200 + 25, mx - 200 + 25);
		if (mouseEasterEgg) {
			g2.rotate(angleMouse, 200 + 25, 200 + 25);
			g.setColor(Color.white);
			g.fillRect(200, 200, 50, 50);
		}*/
		if(minimapRender) {
			renderMiniMap();
		}
		//gameState = "Normal";
		if (gameState.equals("Normal") && minimapRender && estadoCena == jogando) { /* Minimap */
			if(licenseFile.exists()) {
				g.setColor(Color.white);
				g.fillRoundRect(6, 446, World.WIDTH * 6+8, World.HEIGHT * 6+8, 16, 16);
				g.drawImage(minimap, 10, 450, World.WIDTH * 6, World.HEIGHT * 6, null);
			} else {
				g.fillRoundRect(8, 448, World.WIDTH * 6, World.HEIGHT * 6, 14, 14);
				g.drawImage(minimap, 10, 430, World.WIDTH * 6, World.HEIGHT * 6, null);
			}
		}
		
		if(MainMenu.enter && menu.currentOption == 5) {
			MainMenu.enter = false;
		}
		
		if(gameState.equals("Start Menu")) {
			startMenu.render(g);
		}
		
		if (loadingScreen) {
			if (MainMenu.enter) {
				MainMenu.enter = false;
			}
			if (MainMenu.up) {
				MainMenu.up = false;
			}
			if (MainMenu.down) {
				MainMenu.down = false;
			}
			if (gameState.equals("Menu")) {
				UI.posX = 20;
				UI.posY = 4;
			}
			
			if (noteStudiosLogoTime < noteStudiosLogoMaxTime) {
				noteStudiosLogoTime++;
			}
			if (noteStudiosLogoTime == noteStudiosLogoMaxTime) {
				noteStudiosLogoTime = noteStudiosLogoMaxTime;
				opacity2 -= 5;
			}
			if (opacity2 <= 0) {
				opacity2 = 0;
				opacity3 += 5;
			}
			if (opacity3 >= 255) {
				opacity3 = 255;
			}
			if (opacity == 0) {
				loadingScreenFinish = true;
			}
			if (loadingScreenFinish && opacity4 == 0) {
				loadingScreen = false;
			}
			if (rand.nextInt(100) < 20) {
				logoTime += 1;
			} else if (rand.nextInt(100) < 10) {
				logoTime += 3;
			}
			if (logoTime >= logoMaxTime) {
				logoTime = logoMaxTime;
				/* AQUI! */loadingScreenAni = true;
			}
			if (loadingScreenAni && opacity > 0) {
				opacity -= 5;
			}
			if (loadingScreenFinish && opacity <= 255) {
				opacity4 -= 5;
			}
			if (opacity4 <= 0) {
				opacity4 = 0;
			}
			if (!loadingScreenFinish) {
				g.setColor(defaultBgColor);
			} else {
				g.setColor(new Color(30, 30, 31, (int) opacity4));
			} /* 0xFFEF313C */
			g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

			g.setFont(noteLogoFont);
			g.setColor(new Color(255, 255, 255, (int) opacity2));
			int x2 = 300;
			int y2 = 400;
			g.drawString("Note", x2 + 5, y2 - 40);
			g.setFont(new Font("Segoe UI", Font.BOLD, 30));
			g.drawString("S T U D I O S", x2 + 75, y2);

			g.setFont(fontPixel3);
			
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
			if(amogus.exists()) {
				g.drawString("          very sussy", 210, 250);
			} else {
				g.drawString("The", 428, 250);
			}
			g.setFont(fontPixel2);
			if(licenseFile.exists()) {
				if(!amogus.exists()) {
					if(MainMenu.portugues) {
						g.drawString("Dica: "+randomTip, 10, 630);
					} else if(MainMenu.english) {
						g.drawString("Tip: "+randomTip, 10, 630);
					}
				} else if(amogus.exists()) {
					g.drawString("susus moogus", 10, 630);
				}
			}

			if (!loadingScreenAni) {
				g.setColor(Color.white);
			} else {
				g.setColor(new Color(255, 255, 255, (int) opacity));
			}
			/*
			 * g2.rotate(-20, 30, 570); g.drawImage(Spritesheet.loadingImg, 30, 570, 48, 48,
			 * null); g2.rotate(20, 30, 570);
			 */
			//g.drawImage(loading, 50, 50, this);
		}
		
		if(gameState.equals("Credits")) {
			credits.render(g);
		} else {
			boolCredits = false;
		}
		
		if (!Game.licenseOK) {
			if (!Game.debug) {
				g.setFont(Game.menuFont2);
				g.setColor(new Color(255, 255, 0, 255));
				g.drawString("The Traveler " + Game.currentVersion + " | Where is the license file?", 5, 630);
			}
		}
		if(beta) {
			g.setFont(Game.menuFont2);
			g.setColor(defaultBgColor);
			if(gameState.equals("Menu") && !loadingScreen && menu.currentOption != 5) {
				g.fillRoundRect(WIDTH*SCALE-140, 565, 150, 35, 16, 16);
				g.setColor(Color.gray);
				g.drawRoundRect(WIDTH*SCALE-140, 565, 150, 42, 16, 16);
				g.setColor(new Color(255, 255, 0, 255));
				g.drawString(Game.currentVersion + " BETA", (WIDTH*SCALE)-125, 590);
			} else {
				g.fillRoundRect(WIDTH*SCALE-140, 605, 150, 42, 16, 16);
				g.setColor(Color.gray);
				g.drawRoundRect(WIDTH*SCALE-140, 605, 150, 42, 16, 16);
				g.setColor(new Color(255, 255, 0, 255));
				g.drawString(Game.currentVersion + " BETA", (WIDTH*SCALE)-125, 630);
			}
		}
		if(gameState.equals("GJLogin")) {
			gjlogin.render(g);
		}
		if(transition) {
			if(!passouDaTransicao) {
				g.setColor(defaultBgColor);
				g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			}
			
			g.setColor(new Color(10, 10, 11, 255));
			g.fillRect(0, 0, WIDTH*SCALE, (int)(transitionY));
		}
		if(transition && !passouDaTransicao) {
			transitionY+=transitionSpd;
			if(transitionY == (HEIGHT*SCALE+298)) {
				passouDaTransicao = true;
			}
		} if(passouDaTransicao) {
			transitionY-=transitionSpd;
		} if(transitionY == 0) {
			passouDaTransicao = false;
			transition = false;
		}
		
		//renderFakeMouseCursor(g);
		bs.show();
	}
	
	public void renderFakeMouseCursor(Graphics g) {
		URL cur1 = getClass().getResource("/cursors/cur.png");
		URL moogusCur = getClass().getResource("/cursors/amongususcur.png");
		URL normalCur = getClass().getResource("/cursors/normalCur.png");
		if(graphics == 2) {
			if(!amogus.exists()) {
				try {
					if(!mouseExited)
					g.drawImage(ImageIO.read(cur1), mx, my, 11*3, 11*3, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					if(!mouseExited)
					g.drawImage(ImageIO.read(moogusCur), mx, my, 19, 24, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				if(!mouseExited)
				g.drawImage(ImageIO.read(normalCur), mx, my, 16, 16, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void comingSoon(Graphics g) {
		g.setColor(defaultBgColor);
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		g.setColor(Color.white);
		if (MainMenu.english) {
			g.setFont(MainMenu.fontPixel);
			g.drawString("Coming Soon...", (WIDTH * SCALE) / 2 - 240, (HEIGHT * SCALE) / 2);
			g.setFont(MainMenu.aFont);
			g.drawString("This function isn't available for now!", (WIDTH * SCALE) / 2 - 225,
					(HEIGHT * SCALE) / 2 + 50);
		} else if (MainMenu.portugues) {
			g.setFont(MainMenu.fontPixel);
			g.drawString("Em Breve...", (WIDTH * SCALE) / 2 - 185, (HEIGHT * SCALE) / 2);
			g.setFont(MainMenu.aFont);
			g.drawString("Esta função ainda não está disponível!", (WIDTH * SCALE) / 2 - 240,
					(HEIGHT * SCALE) / 2 + 50);
		}

	}

	public void run() {
		long lastTime = System.nanoTime();
		int amountOfTicks = 60;
		double ns = (double) 1000000000 / amountOfTicks;
		double delta = 0;
		@SuppressWarnings("unused")
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				//if(hasFocus()) {
					tick();
					render();
				//}
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				frames = 0;
				timer += 1000;
			}
		}
	
		stop();
	}
	@Override
	public void keyTyped(KeyEvent e) {
        charPressed = e.getKeyChar();
		if(gameState.equals("GJLogin") && Game.charPressed != '\b' && charPressed != '\n'
				&& charPressed != KeyEvent.VK_ESCAPE) {
			if (GJLogin.curTextBox.equals("username") && USER_NAME.length() < 25) {
				USER_NAME += charPressed;
			} else if (GJLogin.curTextBox.equals("token") && USER_TOKEN.length() < 20) {
				USER_TOKEN += charPressed;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if(!loadingScreen) {
				ESC = true;
			}
			if(gameState.equals("Controls") && ESC) {
				transition = true;
			}
			if (menu.exitRequest) {
				menu.exitNo = true;
			}
			if (gameState.equals("Options")) {
				opt.esc = true;
			}
			if(gameState.equals("Menu")) {
				MainMenu.esc = true;
			}
		} if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
		} if (e.getKeyCode() == KeyEvent.VK_E) {
			player.shoot = true;
			player.isSpamming = true;
		} if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState.equals("Game Over") || gameState.equals("Start Menu")) {
				ENTER = true;
				isPressingEnter = true;
			} if(gameState.equals("Shop")) {
				Shop.shopEnter = true;
			} if (gameState.equals("Menu")) {
				MainMenu.enter = true;
			} if (gameState.equals("Options")) {
				opt.enter = true;
			} if (menu.exitRequest) {
				menu.exitYes = true;
			} if(gameState.equals("GJLogin")) {
				if(GJLogin.curTextBox.equals("token")) {
					gjlogin.loginbtn.selected = true;
					gjlogin.loginbtn.clicked = true;
				} else if(GJLogin.curTextBox.equals("username")) {
					GJLogin.curTextBox = "token";
				}
			}
		} if (e.getKeyCode() == KeyEvent.VK_Q) {
			if(gameState.equals("Game Over")) {
				QExitGame = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_F1) {
			if(gameState.equals("Normal")) {
				f1Shop = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL) {
			player.player_run = true;
		} if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
			if(gameState.equals("Options")) {
				opt.rightOpt = true;
			} if(gameState.equals("Menu")) {
				MainMenu.right = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
			if(gameState.equals("Options")) {
				opt.leftOpt = true;
			} if(gameState.equals("Menu")) {
				MainMenu.left = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if(gameState.equals("Normal")) {
				player.up = true;
			} if (gameState.equals("Menu")) {
				MainMenu.up = true;
			} if (gameState.equals("Options")) {
				opt.upOpt = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if(gameState.equals("Normal")) {
				player.down = true;
			} if (gameState.equals("Menu")) {
				MainMenu.down = true;
			} if (gameState.equals("Options")) {
				opt.downOpt = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_C) {
			if (gameState.equals("Normal")) {
				saveGame = true;
				saveGameScreen = true;
			}
		} if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if(gameState.equals("GJLogin")) {
				if (GJLogin.curTextBox.equals("username")) {
					if (USER_NAME != null && !USER_NAME.equals("")) {
						USER_NAME = USER_NAME.substring(0, USER_NAME.length() - 1);
					}
				} else if (GJLogin.curTextBox.equals("token")) {
					if (USER_TOKEN != null && !USER_TOKEN.equals("")) {
						USER_TOKEN = USER_TOKEN.substring(0, USER_TOKEN.length() - 1);
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL) {
			player.player_run = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		} if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			isPressingEnter = false;
			ENTER = false;
		} if(e.getKeyCode() == KeyEvent.VK_E) {
			player.isSpamming = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(!transition) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (gameState.equals("Menu")) {
					MainMenu.enter = true;
				} if (gameState.equals("Options") && opt.curOpt != opt.optOptions.length-1 ) {
					opt.enter = true;
				} if(gameState.equals("Shop")) {
					Shop.shopEnter = true;
				} if(gameState.equals("Normal")) {
					if(pauseSelect || shopSelect) {
						gameMenuEnter = true;
					}
				} else {
					gameMenuEnter = false;
				} if(gameState.equals("Controls") && backControlsSelect) {
					backControlsEnter = true;
				} else {
					backControlsEnter = false;
				} if (gameState.equals("Credits") && credits.backSelect) {
					creditsEnter = true;
				}else {
					creditsEnter = false;
				} if(gameState.equals("Game Over") && selectBackMenu) {
					EnterGameOver = true;
				} else {
					EnterGameOver = false;
				} if(gameState.equals("Start Menu")) {
					ENTER = true;
				} else {
					ENTER = false;
				}
				for(Button b : button) {
					if(b.selected) {
						b.clicked = true;
					}
				}

			}

			if(mx > gjlogin.usernameTextBoxX && mx < gjlogin.usernameTextBoxX+gjlogin.UsernameTextBoxWidth
			&& my > gjlogin.usernameTextBoxY && my < gjlogin.usernameTextBoxY+gjlogin.UsernameTextBoxHeight) {
				GJLogin.curTextBox = "username";
			} else if(mx > gjlogin.tokenTextBoxX && mx < gjlogin.tokenTextBoxX+gjlogin.tokenTextBoxWidth
			&& my > gjlogin.tokenTextBoxY && my < gjlogin.tokenTextBoxY+gjlogin.tokenTextBoxHeight) {
				GJLogin.curTextBox = "token";
			} else {
				GJLogin.curTextBox = "";
			}

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		/* Shoot with mouse: */
		if (e.getButton() == MouseEvent.BUTTON1) {
			if(gameState.equals("Normal")) {
				player.mouseShoot = true;
				//player.isShooting = true;
				player.mx = (e.getX() / SCALE);
				player.my = (e.getY() / SCALE);
			}
			
			if(gameState.equals("Start Menu")) {
				isPressingEnter = true;
			}

			for(Button b : button) {
				b.isPressing = true;
			}

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if(gameState.equals("Normal")) {
				player.mouseShoot = false;
				//player.isShooting = false;
				player.mx = (e.getX() / SCALE);
				player.my = (e.getY() / SCALE);
			}
			
			if(gameState.equals("Start Menu")) {
				isPressingEnter = false;
			}
			for(Button b : button) {
				b.isPressing = false;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseExited = false;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseExited = true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(gameProcessStarted) {
			mx = e.getX();
			my = e.getY();

            for(int i = 0; i < button.size(); i++) {
            	Button b = button.get(i);
                if(mx > b.getX() && mx < b.getX()+b.getWidth()
                        && my > b.getY() && my < b.getY()+b.getHeight()) {
                    b.selected = true;
                } else {
                    b.selected = false;
                }
            }

		}
		if(gameProcessStarted && !transition && !loadingScreen) {
			
			menuX = e.getX();
			menuY = e.getY();

			
			if(gameState.equals("Start Menu")) {
				if (menuX > 136 && menuX < 830 && menuY > 522 && menuY < 580) {
					isSelectingEnterGame = true;
				} else {
					isSelectingEnterGame = false;
				}
			}
			
			if (gameState.equals("Menu")) {
				
				if (menuX > 10 && menuX < 400 && menuY < 415 && menuY > 351) {
					menu.currentOption = 0;
				} else if (menuX > 0 && menuX < 366 && menuY < 476 && menuY > 425) {
					menu.currentOption = 1;
				} else if (menuX > 0 && menuX < 366 && menuY < 535 && menuY > 488) {
					menu.currentOption = 2;
				} else if (menuX > 0 && menuX < 366 && menuY < 595 && menuY > 546) {
					menu.currentOption = 3;
				} else if(menuX > 360 && menuX < 408 && menuY < 534 && menuY > 484) {
					menu.currentOption = 4;
				} else {
					menu.currentOption = menu.maxOption;
				}
				
				if(menu.exitRequest) {
					
					if (menuX > 188 && menuX < 255 && menuY > 130 && menuY < 158) {
						menu.exitYesSelect = true;
					} else {
						menu.exitYesSelect = false;
					} if (menuX > 332 && menuX < 430 && menuY > 130 && menuY < 156) {
						menu.exitNoSelect = true;
					} else {
						menu.exitNoSelect = false;
					}
					
				}
				
			}
			
			if (gameState.equals("Options")) {
				if(!opt.nextPage) {
					if (menuX > 216 && menuX < 750 && menuY > 144 && menuY < 195) { // 0
						opt.curOpt = 0;
					} else if (menuX > 216 && menuX < 750 && menuY > 208 && menuY < 262) { // 1
						opt.curOpt = 1;
					} else if (menuX > 216 && menuX < 750 && menuY > 276 && menuY < 332) { // 2
						opt.curOpt = 2;
					} else if (menuX > 216 && menuX < 750 && menuY > 345 && menuY < 398) { // 3
						opt.curOpt = 3;
					} else if (menuX > 216 && menuX < 750 && menuY > 412 && menuY < 468) { // 4
						opt.curOpt = 4;
					} else if (menuX > 216 && menuX < 750 && menuY > 480 && menuY < 535) { // 5
						opt.curOpt = 5;
					} else if (menuX > 216 && menuX < 750 && menuY > 548 && menuY < 602) { // 6
						opt.curOpt = 6;
					} else if (menuX > 2 && menuX < 168 && menuY > 72 && menuY < 123) { // 7
						opt.curOpt = 7;
					} else {
						opt.curOpt = opt.optMax; // Nothing (9)
					}
				} else {
					if (menuX > 215 && menuX < 751 && menuY > 142 && menuY < 196) { // 0
						opt.curOpt = 8;
					} else if (menuX > 2 && menuX < 168 && menuY > 72 && menuY < 123) { // 7
						opt.curOpt = 7;
					} else {
						opt.curOpt = opt.optMax; // Nothing (9)
					}
				}
			}
			
			if(gameState.equals("Shop")) {
				if (menuX > 220 && menuX < 444 && menuY > 235 && menuY < 458) {
					Shop.medKitSelect = true;
				} else {
					Shop.medKitSelect = false;
				} if (menuX > 490 && menuX < 714 && menuY > 235 && menuY < 458) {
					Shop.bulletPackSelect = true;
				} else {
					Shop.bulletPackSelect = false;
				} if(menuX > 0 && menuX < 168 && menuY > 68 && menuY < 125) {
					Shop.ShopBackSelect = true;
				} else {
					Shop.ShopBackSelect = false;
				}
			}
			
			if(gameState.equals("Credits")) {
				if(menuX > 2 && menuX < 168 && menuY > 72 && menuY < 123) {
					credits.backSelect = true;
				}else {
					credits.backSelect = false;
				}
			}
			
			if(gameState.equals("Normal") && estadoCena == jogando) {
				if(menuX > 0 && menuX < 59 && menuY > 24 && menuY < 75) {
					pauseSelect = true;
				} else {
					pauseSelect = false;
				} if(menuX > 0 && menuX < 60 && menuY > 80 && menuY < 132) {
					shopSelect = true;
				} else {
					shopSelect = false;
				}
			}
			
			if(gameState.equals("Controls")) {

				if(menuX > 2 && menuX < 168 && menuY > 72 && menuY < 123) {
					backControlsSelect = true;
				} else {
					backControlsSelect = false;
				}
				
			}
			
			if(gameState.equals("Game Over")) {
				if(menuX > 0 && menuX < 500 && menuY > 408 && menuY < 464) {
					selectBackMenu = true;
				} else {
					selectBackMenu = false;
				}
			}
			
		}
		
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//Down Scroll == +1
		//Up Scroll == -1
		if(e.getWheelRotation() == +1) {
			if(gameState.equals("Credits") && credits.creditsScroll == 0) {
				credits.creditsScroll-=300;
			} if(gameState.equals("Credits") && credits.creditsScroll == -300) {
				credits.creditsScroll-=600;
			} if(gameState.equals("Options")) {
				opt.nextPage = true;
			}
		} if(e.getWheelRotation() == -1) {
			if(gameState.equals("Credits")) {
				credits.creditsScroll+=300;
			} if(gameState.equals("Options")) {
				opt.nextPage = false;
			}
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		saveConfig = true;
		DiscordRPC.discordShutdown();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}
}