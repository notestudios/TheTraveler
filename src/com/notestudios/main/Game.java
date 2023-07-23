package com.notestudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

import com.notestudios.discord.RPC;
import com.notestudios.discord.UpdateRPC;
import com.notestudios.entities.BigEnemy;
import com.notestudios.entities.Bullets;
import com.notestudios.entities.Enemy;
import com.notestudios.entities.Entity;
import com.notestudios.entities.Npc;
import com.notestudios.entities.Player;
import com.notestudios.gameapi.GameJolt;
import com.notestudios.gameapi.Trophies;
import com.notestudios.graphics.Spritesheets;
import com.notestudios.graphics.UI;
import com.notestudios.menus.JoltLogin;
import com.notestudios.menus.MainMenu;
import com.notestudios.menus.Settings;
import com.notestudios.menus.Shop;
import com.notestudios.objects.InteractibleObjects;
import com.notestudios.util.Button;
import com.notestudios.util.Sound;
import com.notestudios.world.World;

import net.arikia.dev.drpc.DiscordRPC;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener,
MouseMotionListener, MouseWheelListener, WindowListener {
	
	public static final long serialVersionUID = 1L;

	private Thread thread;

	int menuX = 0, menuY = 0;
	int mx = 0, my = 0;
	int bgY = 0;
	int bgY2 = 160;
	int bgSpd = 1;
	int updPresenceTime = 0;
	int maxUpdPresenceTime = (60*2);
	public static int gameOverFrames = 0;
	public static int curLevel = 1, maxLevel = 12;
	public static int playerCoins = 0;
	public static int WIDTH = 240;//960
	public static int HEIGHT = 160;//640
	public static int SCALE = 4; //res: 960x640
	public static int graphics = 2;
	public static int enterCutscene = 1;
	public static int finishCutscene = 2;
	public static int cutsceneState = enterCutscene;
	public static int enableMinimap = 1;
	public static int showPopup = 1;
	private boolean isRunning = true;
	
	public static boolean secret = false;
	public static boolean saveGame = false;
	public static boolean loadingScreenFinish = false;
	boolean saveGameScreen = false;
	public static boolean loadingScreen = false;
	boolean loadingScreenAni = false;
	public static boolean upTransition = false;
	public static boolean mouseExited = true;
	public static boolean AAliasingEnabled = false;
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
	public static boolean downTransition = false;
	public static boolean gameProcessStarted = false;
	public static boolean bossFight = false;
	public static boolean devMode = false;
	public static boolean isPressingEnter = false;
	public static boolean loginPopup = false;

	public static List<Entity> entities;
	public static List<InteractibleObjects> objects;
	public static List<Enemy> enemies;
	public static List<BigEnemy> bosses;
	public static List<Bullets> bullets;
	public static List<Button> button;
	
	public static Random rand;
	public static UI ui;
	public static Npc npc;
	public static Player player;
	public static World world;
	
	public static final Color defaultBgColor = new Color(39, 39, 39, 255); //old rgb(15,40,50)

	public double logoTime = 0;
	public double logoMaxTime = (60 * 4);/*default value: 4*/
	public double saveGameInfoMaxTime = (60 * 4);
	public double curSaveGameTime = 0;
	public double saveGameSpeed = 2;
	public double x1 = 238;
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

	public static InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font newFont;

	public static InputStream stream5 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font warningFont;

	public static InputStream streamFont = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font optFont;

	public static InputStream stream1 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font titleFont2;

	public static InputStream stream3 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font titleFont;

	public static InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font menuFont2;

	public static InputStream stream4 = ClassLoader.getSystemClassLoader().getResourceAsStream(noteFontDir);
	public static Font noteLogoFont;
	
	public static InputStream stream6 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font travelerLogoFont;
	
	private final BufferedImage image;
	public Spritesheets spritesheet;
	public static BufferedImage GameBackground, GameBackground2;
	File debugFile = new File("debugAccess.txt");
	File licenseFile = new File("LICENSE");
	File devFile = new File("README.md");
	File configSave = new File("config.save");
	public static File gameCredentialsFile = new File("gamejolt.save");
	public static File gamejoltCredentialsFile = new File(".gj-credentials");
	public static File amogus = new File("amogus.txt");
	int gameID = 796130;
	private String gamePrivateID = "I'm not giving that info too easily";
	public static String userName = "";
	public static String userToken = "";
	
	String[] Words = {"got some new buttons yea", "i know, title screen is bugged",
	"frontend or backend? 🤔", "sheeeeesh", "hey you!", "potato", "this is "+currentVersion+"!", "Hey you! Yeah, you!", 
	"fun fact: the developer likes coding ;)", "'try{image = ImageIO.read(getClass().getResource(path));}catch(IOException e){e.printStackTrace}'",
	"what... just... happened...?", "New things are coming in the next update to this game! Like... a BOMB", "if you like linux, use winget on windows!",
	"new menu style lol", "Figure is bugged", "when the impostor is SUS ඞ", "brooooooooooooooooooooooo", "bruh", "cool",
	"kaboom", "hi", "here's your attention again.", "spider-man goes crazy :0", "Got your attention haha ;)", "[...] thread == null!", "this is a random title!",
	currentVersion+" is on!", "lmao", "I know your discord username haha", "hmm, achivements...", "bro, VS Imposter Alternated is a mod of a mod :0"};
	
	String[] tipsEn = {"Press 'C' to save the game! ", "cool tip", "You can run pressing 'Shift'!", "idk whats the next tip bru", 
			"Move using W, A, S, D keys!", "There are maps that you can't use minimap!", "You can use the menus with the arrow keys!", "spam pressing E", 
			"Login with you Game Jolt Account!", "Config > Scroll Down > Game Jolt Login", "Use 'Shift' or 'Ctrl' to run!", "Unlock new Achivements with Game Jolt!"};
	
	String[] tipsPt = {"Pressione 'C' para salvar o jogo!", "Você pode correr pressionando 'Shift'!", "Você também pode usar o menu com as setas!",
			"qual a próxima dica mesmo?", "Mova-se usando as teclas W, A, S, D!", "Há mapas que o minimapa não pode ser usado!", "spame bullets segurando E", 
			"Faça login com sua conta Game Jolt!", "Config > Scroll Down > Game Jolt Login", "Use 'Shift' ou 'Ctrl' para correr!", "Desbloqueie novas conquistas com o Game Jolt!"};
	String randomTip;
	public static String showGraphics;
	String randomText;
	public static String currentVersion = "v4.4.10";
	String newerVersion;
	static String day = "19", month = "07", year = "2023";
	public static String lastUpdateEn = month+"/"+day+"/"+year, lastUpdatePt = day+"/"+month+"/"+year;
	public static String DiscordAppID = "1087444872132313168";
	public static String gameState = "Start Menu";
	public static char charPressed;
	public static GameJolt jolt;
	RPC rpc;
	public static JFrame frame;
	
	/* Game States:
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
	 * GJLogin = Game Jolt login page.
	 * 
	 * NoteStudios GAMES INC.
	 */

	public Game() {
		rand = new Random();
		beta = currentVersion.endsWith("b");
		devMode = devFile.exists();
		debug = debugFile.exists();
		secret = amogus.exists();
		//TODO: remove this code in version 4.6.0!
		File oldCredentials = new File("GJ");
		if(oldCredentials.exists()) { oldCredentials.renameTo(gameCredentialsFile); }
		/**/
		randomText = Words[rand.nextInt(Words.length)];
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT * SCALE));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		createFrame();
		spritesheet = new Spritesheets();
		jolt = new GameJolt(gameID, gamePrivateID);
		player = new Player(0, 0, 16, 16, Entity.playerStop);
		entities = new ArrayList<Entity>();
		objects = new ArrayList<InteractibleObjects>();
		enemies = new ArrayList<Enemy>();
		bosses = new ArrayList<BigEnemy>();
		bullets = new ArrayList<Bullets>();
		npc = new Npc(32, 32, 16, 16, Entity.DefaultNPC_EN);
		entities.add(player);
		entities.add(npc);
		world = new World(World.mapsFolder+"level"+curLevel+".png");
		World.initializeMinimap();
		reloadAllUI();
		rpc = new RPC();
		loginPopup = true;
		if (configSave.exists()) {
			String saver = Settings.loadConfig();
			Settings.applyCfgSave(saver);
			mute = Settings.mute == 1;
			minimapRender = Settings.minimap == 1;
			AAliasingEnabled = Settings.AntiAliasing == 1;
		}
		if(MainMenu.english) {
			randomTip = tipsEn[rand.nextInt(tipsEn.length)];
		} else if(MainMenu.portuguese) {
			randomTip = tipsPt[rand.nextInt(tipsPt.length)];
		} else {
			randomTip = "Use Mouse/Arrows";
		}
		if(gameCredentialsFile.exists() || gamejoltCredentialsFile.exists()) {
			if(gamejoltCredentialsFile.exists()) {
				jolt.autoLogin(gamejoltCredentialsFile);
			} else if(gameCredentialsFile.exists()) {
				jolt.autoLogin(gameCredentialsFile);
			} else if(gameCredentialsFile.exists() && gamejoltCredentialsFile.exists()) {
				jolt.autoLogin(gamejoltCredentialsFile);
			}
			showPopup = 0;
			loginPopup = false;
		}
		if(loadingScreen && !mute) { Sound.noteStudiosEffect.play(); }
		gameProcessStarted = true;
		DiscordRPC.discordInitialize(DiscordAppID, rpc, true);
		loadingScreen = true;
	}
	
	
	public static void reloadAllUI() {
		button = new ArrayList<Button>();
		ui = new UI();
		UI.reloadUI();
		UI.reloadFonts();
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		try { thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void createFrame() {
		frame = new JFrame();
		frame.add(this);
		//frame.setUndecorated(!frame.isUndecorated()); Just on full screen(super-alpha)
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
	}

	public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

	public static BufferedImage bigBackground;
	public static BufferedImage defaultShortOptBg;
	public static BufferedImage defaultLargeOptionBg;
	public static BufferedImage menuCreditsIcon;
	
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
			UpdateRPC.tick();
		}
		Sound.tick();
		loginPopup = showPopup == 1;
		arrSelectSprite = MainMenu.arrSelect == 1;
		licenseOK = licenseFile.exists();
		
		if (graphics == 2) {
			if (MainMenu.portuguese) {
				showGraphics = "Alta";
			} else { showGraphics = "High"; }
		} else if (graphics == 1) {
			arrSelectSprite = false;
			if (MainMenu.portuguese) {
				showGraphics = "Baixa";
			} else { showGraphics = "Low"; }
		}
		player.updateCamera();
		if(gameState.equals("Credits")) {
			if(ESC) {
				ESC = false;
				gameState = "Menu";
				downTransition = true;
				if(!mute) { Sound.backMenu.play(); }
			}
		}

		if (gameState.equals("Normal") && ESC) {
			ESC = false;
			UI.menu.pause = true;
			if(!mute) { Sound.pauseGame.play(); }
			gameState = "Menu";
		}
		if (gameState.equals("Menu") && cutsceneState == enterCutscene) {
			player.setX(64);
			player.setY(416);
			player.right = false;
		}
		;
		if(secret) {
			if(!gameState.equals("Menu") && !gameState.equals("Normal") && !loadingScreen) {
				secret = false;
				if(!mute) { Sound.secret.play(); }
				if(GameJolt.isLoggedIn) {
					Trophies.achieve(GameJolt.trophiesIDs[4]);
				}
			}
		}
		if (saveGame) {
			saveGame = false;
			if(!Game.mute) {
				Sound.savedGame.play(); }
			String[] opt1 = { "level", "px", "py", "life", "coins", "ammo"};
			int[] opt2 = { curLevel, (int) player.x, (int) player.y, (int) player.life, playerCoins, player.ammo};
			MainMenu.saveGame(opt1, opt2, /*encode*/UI.menu.curEncode);
		} if (saveConfig) {
			saveConfig = false;
			String[] cfg1 = {"english", "portugues", "quality", "arrowSelect", "minimap", "mute", 
					"minimap", "anti-aliasing", "showPopup"};
			int[] cfg2 = {MainMenu.Eng, MainMenu.Por, graphics, MainMenu.arrSelect, enableMinimap, Settings.mute, Settings.minimap, 
					Settings.AntiAliasing, showPopup};
			Settings.saveConfig(cfg1, cfg2);
		} if (saveLogin) {
		    saveLogin = false;
		    if(!gamejoltCredentialsFile.exists()) {
		    	jolt.fileSave(GameJolt.gameCredentialsFile.getName(), userName, userToken);
		    }
		}
		if (gameState.equals("Options")) {
			UI.opt.tick();
		}
		if (f1Shop) {
			gameState = "Shop";
			UI.shop.tick();
		}
		if (gameState.equals("Normal")) {
			QExitGame = false;
			if (cutsceneState == finishCutscene) {
				for (int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					e.tick();
				}
				for (int i = 0; i < bullets.size(); i++) {
					bullets.get(i).tick();
				}
			} else if (cutsceneState == enterCutscene) {
				if (player.getX() < 162) {
					player.updateCamera();
					player.moved = true;
					player.tick();
					player.right = true;
				} else if (player.getX() >= 162) {
					player.right = false;
					player.updateCamera();
					player.moved = false;
					cutsceneState = finishCutscene;
				}
			}
			if (enemies.size() == 0 && bosses.size() == 0 && !bossFight) {
				curLevel++;
				if (curLevel > maxLevel) {
					curLevel = 1;
				}
				World.nextLevel("level" + curLevel + ".png");
			}
		}
		if (gameState.equals("Game Over")) { UI.gameOver.tick(); }
		if (gameState.equals("Menu")) {
			player.updateCamera();
			UI.menu.tick();
			if(loginPopup) { UI.popupLogin.tick(); }
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
		
		if(gameState.equals("Normal") && cutsceneState == finishCutscene) {
			if(pauseSelect && gameMenuEnter) {
				pauseSelect = false;
				if(!Game.mute) { Sound.pauseGame.play(); }
				gameMenuEnter = false;
				UI.menu.pause = true;
				gameState = "Menu";
			} if(shopSelect && gameMenuEnter) {
				shopSelect = false;
				gameMenuEnter = false;
				if(!Game.mute) { Sound.menuEnter.play(); }
				UI.menu.pause = true;
				f1Shop = true;
			}
		}
		
		if(gameState.equals("Controls")) {
			UI.controls.tick();
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
			UI.credits.tick();
		} if(gameState.equals("Start Menu")) {
			UI.startMenu.tick();
		}
		if(gameState.equals("GJLogin")) {
			UI.gjlogin.tick();
		}

	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) image.getGraphics();
		UI.useAntiAliasing(g);
		g.setColor(defaultBgColor);
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		if(gameState.equals("Menu") || gameState.equals("Normal") || gameState.equals("")) {
			world.render(g);
			for(int i = 0; i < objects.size(); i++) {
				InteractibleObjects io = objects.get(i);
				io.render(g);
			}
			entities.sort(Entity.enSorter);
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.render(g);
			}
		}
		for (int i = 0; i < bullets.size(); i++) {
			Bullets bullet = bullets.get(i);
			bullet.render(g);
		}
		if (saveGameScreen && gameState.equals("Normal")) {
			if (MainMenu.portuguese) {
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
		if(cutsceneState == enterCutscene && gameState.equals("Normal")) {
			g.setColor(defaultBgColor);
			g.fillRect(0, 0, WIDTH, HEIGHT/5);
			g.fillRect(0, HEIGHT-30, WIDTH, HEIGHT/5);
		}
		
		if(gameState.equals("Normal") && cutsceneState == finishCutscene) {
			if(graphics == 2) {
				g.drawImage(defaultShortOptBg, -62, 5, null);
				g.drawImage(defaultShortOptBg, -62, 19, null);
			}
			
			g.drawImage(Entity.UIPause, -2, 4, null);
			g.drawImage(Entity.UIShop, -2, 19, null);
			
		}
		if(gameState.equals("Background")) {
			if(!UI.menu.pause && graphics == 2) {
				g.drawImage(GameBackground, 0, bgY, null);
				g.drawImage(GameBackground2, 0, bgY2, null);
			}
		} if(gameState.equals("Start Menu")) {
			if(graphics == 2) {
				g.drawImage(GameBackground, 0, bgY, null);
				g.drawImage(GameBackground2, 0, bgY2, null);
				g.drawImage(bigBackground, 8, -20, bigBackground.getWidth()*2, bigBackground.getHeight()*2, null);
				g.drawImage(defaultLargeOptionBg, 26, 130, 186, 16, null);
			} else if(graphics == 1) {
				g.setColor(new Color(30, 30, 31));
				g.fillRect(0, 0, WIDTH, HEIGHT);
			}
		}
		if (gameState.equals("Menu")) {

			g.setColor(new Color(0, 0, 0, 60));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(defaultBgColor);
			g.rotate(-4.55);
			g.fillRect(-100, -135, WIDTH+100, HEIGHT);
			g.rotate(4.55);
			
			if (graphics == 2) {
				
				if(UI.menu.exitRequest) {
					g.drawImage(bigBackground, -10, 4, 112+40, 50,null);
				}
				
				g.drawImage(defaultLargeOptionBg, -39, 87, null);
				g.drawImage(defaultLargeOptionBg, -39, 89, null);
				g.drawImage(defaultLargeOptionBg, -50, 104, null);
				if(UI.menu.currentOption == 4) {
					if(MainMenu.english) {
						g.drawImage(defaultLargeOptionBg, -15, 119, null);	
					}else if(MainMenu.portuguese) {
						g.drawImage(defaultLargeOptionBg, -10, 119, null);
					}
				} else {
					g.drawImage(defaultLargeOptionBg, -39, 119, null);
				}
				g.drawImage(defaultLargeOptionBg, -50, 119, null);
				g.drawImage(defaultLargeOptionBg, -50, 134, null);
				if (UI.menu.options[UI.menu.currentOption].equals("novo_jogo")) {
					if (positionX > 185) {
						positionX -= 1;
					} else if (positionX < 185) {
						positionX += 1;
					}
					g.drawImage(defaultLargeOptionBg, (int) positionX, 149, null);
				}
				if (UI.menu.options[UI.menu.currentOption].equals("carregar_jogo")) {
					if (MainMenu.english) {
						if (positionX > 172) {
							positionX -= 1;
						} else if (positionX < 172) {
							positionX += 1;
						}
					} else if (MainMenu.portuguese) {
						if (positionX > 174) {
							positionX -= 1;
						} else if (positionX < 174) {
							positionX += 1;
						}
					}
					g.drawImage(defaultLargeOptionBg, (int) positionX, 149, null);
				}
				if (UI.menu.options[UI.menu.currentOption].equals("options")) {
					if (positionX > 188) {
						positionX -= 1;
					} else if (positionX < 188) {
						positionX += 1;
					}
					g.drawImage(defaultLargeOptionBg, (int) positionX, 149, null);
				}
				if (UI.menu.options[UI.menu.currentOption].equals("sair")) {
					if (positionX > 195) {
						positionX -= 1;
					} else if (positionX < 195) {
						positionX += 1;
					}
					g.drawImage(defaultLargeOptionBg, (int) positionX, 149, null);
				}
				if (UI.menu.options[UI.menu.currentOption].equals("Credits")) {
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
				
				if (UI.opt.optOptions[UI.opt.curOpt].equals("verInfo") && UI.opt.versionInfoRequest && gameState.equals("Options")) {
					g.drawImage(defaultLargeOptionBg, 178, 130 - 45, null);
				}
				g.drawImage(bigBackground, -50, -25, null);
				/* back */g.drawImage(defaultLargeOptionBg, -99, 16, null);
					
				if(!UI.opt.nextPage) {
					g.drawImage(defaultLargeOptionBg, 47, 79 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 96 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 113 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 + 17 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 + 17 + 17 - 45, null);
					g.drawImage(defaultLargeOptionBg, 47, 130 + 17 + 17 + 17 - 45, null);
				} else {
					g.drawImage(defaultLargeOptionBg, 47, 79 - 45, null);
				}
			}
		}
		
		if (gameState.equals("Normal") && cutsceneState == finishCutscene) {
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
				if (MainMenu.portuguese)
					g.drawString("Seu jogo foi salvo!", (int) x1 * Game.SCALE + 53, 362);
				else if (MainMenu.english) {
					g.drawString("Your game is saved!", (int) x1 * Game.SCALE + 51, 362);
				}
			} else if (saveFile.exists()) {
				if (MainMenu.portuguese)
					g.drawString("O Jogo foi Atualizado!", (int) x1 * Game.SCALE + 52, 362);
				else if (MainMenu.english) {
					g.drawString("Your save is updated!", (int) x1 * Game.SCALE + 50, 362);
				}
			}
		}
		if(gameState.equals("Normal") && cutsceneState == finishCutscene) {
			if(pauseSelect) {
				g.setColor(Color.white);
				g.drawRoundRect(-12, 23, 18 * SCALE, 13 * SCALE, 14, 14);
			} if(shopSelect) {
				g.setColor(Color.white);
				g.drawRoundRect(-12, 80, 18 * SCALE, 13 * SCALE, 14, 14);
			}
		}
		
		if (gameState.equals("Controls")) {
			UI.controls.render(g);
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
			UI.drawString(g, npc.falas[0].substring(0, npc.curIndex), 270, 490);
		}
		//comingSoon(g);
		if (f1Shop) {
			UI.shop.render(g);
		}
		if (gameState.equals("Normal") && cutsceneState == finishCutscene) {
			ui.render(g);
		}
		if (gameState.equals("Normal") && cutsceneState == finishCutscene) {
			g.setFont(MainMenu.aFont);
			g.setColor(Color.white);
			g.drawString(String.valueOf(player.ammo), 420/* 420 */, 92/* 43 */);
			g.drawString(String.valueOf(playerCoins), 570/* 570 */, 92/* 43 */);
		}
		if (gameState.equals("Normal") && cutsceneState == finishCutscene) {
			g.setColor(Color.white);
			g.setFont(newFont);
			if (player.life >= 100)
				g.drawString((int) player.life + "/" + (int) player.maxLife, 414/* 120 */, 44/* 44 */);
			else if (player.life < 100)
				g.drawString((int) player.life + "/" + (int) player.maxLife, 416/* 125 */, 44/* 44 */);
		}
		if (gameState.equals("Options")) {
			UI.opt.render(g);
		}
		if (gameState.equals("Game Over")) {
			UI.gameOver.render(g);
		} else if (gameState.equals("Menu")) {
			UI.menu.render(g);
			if(loginPopup) {
				UI.popupLogin.render(g);
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
			world.renderMiniMap();
		}
		//gameState = "Normal";
		if(gameState.equals("Normal") && minimapRender && cutsceneState == finishCutscene) { /* Minimap */
			if(licenseOK) {
				g.setColor(Color.white);
				g.fillRoundRect(6, 446, World.WIDTH * 6+8, World.HEIGHT * 6+8, 16, 16);
				g.drawImage(World.minimap, 10, 450, World.WIDTH * 6, World.HEIGHT * 6, null);
			} else {
				g.fillRoundRect(8, 448, World.WIDTH * 6, World.HEIGHT * 6, 14, 14);
				g.drawImage(World.minimap, 10, 430, World.WIDTH * 6, World.HEIGHT * 6, null);
			}
		}
		
		if(MainMenu.enter && UI.menu.currentOption == 5) {
			MainMenu.enter = false;
		}
		
		if(gameState.equals("Start Menu")) {
			UI.startMenu.render(g);
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

			g.setFont(titleFont);
			
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
			if(secret) {
				g.drawString("very sussy", 280, 250);
			} else {
				g.drawString("The", 428, 250);
			}
			g.setFont(titleFont2);
			if(licenseOK) {
				if(!amogus.exists()) {
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
		
		if(gameState.equals("Credits")) { UI.credits.render(g); }
		if(beta) {
			g.setFont(Game.menuFont2);
			g.setColor(defaultBgColor);
			if(gameState.equals("Menu") && UI.menu.currentOption != 5) {
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
			UI.gjlogin.render(g);
		}
		
		if (!Game.licenseOK) {
			if (!Game.debug) {
				g.setFont(Game.menuFont2);
				g.setColor(new Color(255, 255, 0));
				g.drawString("The Traveler " + Game.currentVersion + " | Where is the LICENSE file?", 5, HEIGHT*SCALE-8);
			}
		}
		
		if(downTransition) {
			if(!upTransition) {
				g.setColor(defaultBgColor);
				g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			}
			
			g.setColor(new Color(10, 10, 11, 255));
			g.fillRect(0, 0, WIDTH*SCALE, (int)(transitionY));
		}
		if(downTransition && !upTransition) {
			transitionY+=transitionSpd;
			if(transitionY == (HEIGHT*SCALE+298)) {
				upTransition = true;
			}
		} if(upTransition) {
			transitionY-=transitionSpd;
		} if(transitionY == 0) {
			upTransition = false;
			downTransition = false;
		}
		
		//renderFakeMouseCursor(g);
		/* Achievement notify Debug Code:
		BufferedImage achivIcon = null;
		try {
			achivIcon = ImageIO.read(getClass().getResource("/achivements/why-is-he-so-big.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		ui.unlockAchivementAnim(g, achivIcon, "???", "Loading trophy description...\nThis is an achivement!", false);
		*/
		bs.show();
	}
	
	/*private void renderFakeMouseCursor(Graphics g) {
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
		
	}*/
	
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
		} else if (MainMenu.portuguese) {
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
			if (JoltLogin.curTextBox.equals("username") && userName.length() < 25) {
				userName += charPressed;
			} else if (JoltLogin.curTextBox.equals("token") && userToken.length() < 20) {
				userToken += charPressed;
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
				downTransition = true;
			}
			if (UI.menu.exitRequest) {
				UI.menu.exitNo = true;
			}
			if (gameState.equals("Options")) {
				UI.opt.esc = true;
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
				UI.opt.enter = true;
			} if (UI.menu.exitRequest) {
				UI.menu.exitYes = true;
			} if(gameState.equals("GJLogin")) {
				if(JoltLogin.curTextBox.equals("token")) {
					UI.gjlogin.loginbtn.selected = true;
					UI.gjlogin.loginbtn.clicked = true;
				} else if(JoltLogin.curTextBox.equals("username")) {
					JoltLogin.curTextBox = "token";
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
			player.playerRun = true;
		} if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
			if(gameState.equals("Options")) {
				UI.opt.rightOpt = true;
			} if(gameState.equals("Menu")) {
				MainMenu.right = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
			if(gameState.equals("Options")) {
				UI.opt.leftOpt = true;
			} if(gameState.equals("Menu")) {
				MainMenu.left = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if(gameState.equals("Normal")) {
				player.up = true;
			} if (gameState.equals("Menu")) {
				MainMenu.up = true;
			} if (gameState.equals("Options")) {
				UI.opt.upOpt = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if(gameState.equals("Normal")) {
				player.down = true;
			} if (gameState.equals("Menu")) {
				MainMenu.down = true;
			} if (gameState.equals("Options")) {
				UI.opt.downOpt = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_C) {
			if (gameState.equals("Normal")) {
				saveGame = true;
				saveGameScreen = true;
			}
		} if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if(gameState.equals("GJLogin")) {
				if (JoltLogin.curTextBox.equals("username")) {
					if (userName != null && !userName.equals("")) {
						userName = userName.substring(0, userName.length() - 1);
					}
				} else if (JoltLogin.curTextBox.equals("token")) {
					if (userToken != null && !userToken.equals("")) {
						userToken = userToken.substring(0, userToken.length() - 1);
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL) {
			player.playerRun = false;
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
		
		if(!downTransition) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (gameState.equals("Menu")) {
					MainMenu.enter = true;
				} if (gameState.equals("Options") && UI.opt.curOpt != UI.opt.optOptions.length-1 ) {
					UI.opt.enter = true;
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
				} if (gameState.equals("Credits") && UI.credits.backSelect) {
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

			if(mx > UI.gjlogin.usernameTextBoxX && mx < UI.gjlogin.usernameTextBoxX+UI.gjlogin.UsernameTextBoxWidth
			&& my > UI.gjlogin.usernameTextBoxY && my < UI.gjlogin.usernameTextBoxY+UI.gjlogin.UsernameTextBoxHeight) {
				JoltLogin.curTextBox = "username";
			} else if(mx > UI.gjlogin.tokenTextBoxX && mx < UI.gjlogin.tokenTextBoxX+UI.gjlogin.tokenTextBoxWidth
			&& my > UI.gjlogin.tokenTextBoxY && my < UI.gjlogin.tokenTextBoxY+UI.gjlogin.tokenTextBoxHeight) {
				JoltLogin.curTextBox = "token";
			} else {
				JoltLogin.curTextBox = "";
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

			for(int i = 0; i < button.size(); i++) {
				Button b = button.get(i);
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
		if(gameProcessStarted && !downTransition && !loadingScreen) {
			
			menuX = e.getX();
			menuY = e.getY();

			switch(gameState) {
				case "Start Menu" -> {
					if (menuX > 136 && menuX < 830 && menuY > 522 && menuY < 580) {
						isSelectingEnterGame = true;
					} else {
						isSelectingEnterGame = false;
					}
				}
				
				case "Menu" -> {
					if (menuX > 10 && menuX < 400 && menuY < 415 && menuY > 351) {
						UI.menu.currentOption = 0;
					} else if (menuX > 0 && menuX < 366 && menuY < 476 && menuY > 425) {
						UI.menu.currentOption = 1;
					} else if (menuX > 0 && menuX < 366 && menuY < 535 && menuY > 488) {
						UI.menu.currentOption = 2;
					} else if (menuX > 0 && menuX < 366 && menuY < 595 && menuY > 546) {
						UI.menu.currentOption = 3;
					} else if(menuX > 360 && menuX < 408 && menuY < 534 && menuY > 484) {
						UI.menu.currentOption = 4;
					} else {
						UI.menu.currentOption = UI.menu.maxOption;
					}
					
					if(UI.menu.exitRequest) {
						
						if (menuX > 188 && menuX < 255 && menuY > 130 && menuY < 158) {
							UI.menu.exitYesSelect = true;
						} else {
							UI.menu.exitYesSelect = false;
						} if (menuX > 332 && menuX < 430 && menuY > 130 && menuY < 156) {
							UI.menu.exitNoSelect = true;
						} else {
							UI.menu.exitNoSelect = false;
						}
						
					}
				}
				
				case "Options" -> {
					if(!UI.opt.nextPage) {
						if (menuX > 216 && menuX < 750 && menuY > 144 && menuY < 195) { // 0
							UI.opt.curOpt = 0;
						} else if (menuX > 216 && menuX < 750 && menuY > 208 && menuY < 262) { // 1
							UI.opt.curOpt = 1;
						} else if (menuX > 216 && menuX < 750 && menuY > 276 && menuY < 332) { // 2
							UI.opt.curOpt = 2;
						} else if (menuX > 216 && menuX < 750 && menuY > 345 && menuY < 398) { // 3
							UI.opt.curOpt = 3;
						} else if (menuX > 216 && menuX < 750 && menuY > 412 && menuY < 468) { // 4
							UI.opt.curOpt = 4;
						} else if (menuX > 216 && menuX < 750 && menuY > 480 && menuY < 535) { // 5
							UI.opt.curOpt = 5;
						} else if (menuX > 216 && menuX < 750 && menuY > 548 && menuY < 602) { // 6
							UI.opt.curOpt = 6;
						} else if (menuX > 2 && menuX < 168 && menuY > 72 && menuY < 123) { // 7
							UI.opt.curOpt = 7;
						} else {
							UI.opt.curOpt = UI.opt.optMax; // Nothing (9)
						}
					} else {
						if (menuX > 215 && menuX < 751 && menuY > 142 && menuY < 196) { // 0
							UI.opt.curOpt = 8;
						} else if (menuX > 2 && menuX < 168 && menuY > 72 && menuY < 123) { // 7
							UI.opt.curOpt = 7;
						} else {
							UI.opt.curOpt = UI.opt.optMax; // Nothing (9)
						}
					}
				}
				
				case "Shop" -> {
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
				
				case "Credits" -> {
					if(menuX > 2 && menuX < 168 && menuY > 72 && menuY < 123) {
						UI.credits.backSelect = true;
					} else {
						UI.credits.backSelect = false;
					}
				}
				
				case "Normal" -> {
					if(cutsceneState == finishCutscene) {
						pauseSelect = menuX > 0 && menuX < 59 && menuY > 24 && menuY < 75;
						shopSelect = menuX > 0 && menuX < 60 && menuY > 80 && menuY < 132;
					}
				}
				
				case "Controls" -> {
					if(menuX > 2 && menuX < 168 && menuY > 72 && menuY < 123) {
						backControlsSelect = true;
					} else {
						backControlsSelect = false;
					}
				}
				
				case "Game Over" -> {
					if(menuX > 0 && menuX < 500 && menuY > 408 && menuY < 464) {
						selectBackMenu = true;
					} else {
						selectBackMenu = false;
					}
				}
			}
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//Down Scroll == +1
		//Up Scroll == -1
		if(e.getWheelRotation() == +1) {
			if(gameState.equals("Credits") && UI.credits.creditsScroll == 0) {
				UI.credits.creditsScroll-=300;
			} if(gameState.equals("Credits") && UI.credits.creditsScroll == -300) {
				UI.credits.creditsScroll-=600;
			} if(gameState.equals("Options")) {
				UI.opt.nextPage = true;
			}
		} if(e.getWheelRotation() == -1) {
			if(gameState.equals("Credits")) {
				UI.credits.creditsScroll+=300;
			} if(gameState.equals("Options")) {
				UI.opt.nextPage = false;
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
