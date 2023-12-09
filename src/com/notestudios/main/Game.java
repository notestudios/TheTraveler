package com.notestudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.notestudios.discord.Discord;
import com.notestudios.entities.BigEnemy;
import com.notestudios.entities.Bullets;
import com.notestudios.entities.Enemy;
import com.notestudios.entities.Entity;
import com.notestudios.entities.Npc;
import com.notestudios.entities.Player;
import com.notestudios.gameapi.GameJolt;
import com.notestudios.gameapi.Trophies;
import com.notestudios.graphics.Minimap;
import com.notestudios.graphics.Spritesheets;
import com.notestudios.graphics.UI;
import com.notestudios.menus.JoltLogin;
import com.notestudios.menus.MainMenu;
import com.notestudios.menus.Settings;
import com.notestudios.menus.Shop;
import com.notestudios.objects.InteractibleObjects;
import com.notestudios.util.Button;
import com.notestudios.util.Popup;
import com.notestudios.util.Sound;
import com.notestudios.world.World;

import net.arikia.dev.drpc.DiscordRPC;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener,
MouseMotionListener, MouseWheelListener {
	
	public static final long serialVersionUID = 1L;

	private Thread thread;
	private static KeyEvent ke;
	private static MouseEvent me;
	
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	private final int maxUpdPresenceTime = (60*2);
	private int updPresenceTime = 0;
	public int fps = 0;
	
	public static int playerCoins = 0;
	public static final short enterCutscene = 1;
	public static final short finishCutscene = 2;
	public static short cutsceneState = enterCutscene;
	public static short graphicsQuality = 2;
	public static short enableMinimap = 1;
	public static short showPopup = 1;
	
	private boolean isRunning = true;
	public static boolean amogusSecret = false;
	public static boolean saveGame = false;
	public static boolean antiAliasingEnabled = false;
	public static boolean QExitGame = false;
	public static boolean f1Shop = false;
	public static boolean ENTER = false;
	public static boolean saveConfig = false;
	public static boolean saveLogin = false;
	public static boolean ESC;
	public static boolean useMinimap = true;
	public static boolean mute = false;
	public static boolean debugMode = false;
	public static boolean licenseOK = false;
	public static boolean beta = false;
	public static boolean devMode = false;
	public static boolean showLoginPopup = false;
	public static boolean dialogMode = false;

	private static Random random;
	public static UI ui;
	public static Npc npc;
	public static Player player;
	public static World world;
	
	private static final String silkscreenFont = "fonts/silkscreen.ttf";
	public static final String AlterebroFontDir = "fonts/alterebro_pixel.ttf";

	public static final InputStream stream5 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font warningFont;
	public static final InputStream streamFont = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font optFont;
	public static final InputStream stream3 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font titleFont;
	public static final InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font menuFont2;
	public static final InputStream stream4 = ClassLoader.getSystemClassLoader().getResourceAsStream(silkscreenFont);
	public static Font noteLogoFont;
	public static final InputStream stream6 = ClassLoader.getSystemClassLoader().getResourceAsStream(AlterebroFontDir);
	public static Font travelerLogoFont;
	
	private final BufferedImage image;
	public Spritesheets spritesheet;
	private File debugFile = new File("enableDebug.txt");
	private File licenseFile = new File("LICENSE.txt");
	private File devFile = new File("README.md");
	public static File configSave = new File("settings.save"),
	gameCredentialsFile = new File("gamejolt.save"),
	gamejoltCredentialsFile = new File(".gj-credentials");
	public File amogus = new File("amogus.txt");
	
	private List<String> words = Arrays.asList("finally, the main menu is not bugged anymore!",
	"frontend or backend?", "hello there", "potato", "this is "+currentVersion+"!", "what's up?", 
	"fun fact: the developer likes coding ;)", "shop is unavailable...",
	"v5 when?", "finally the 'kaboom' random title makes sense",
	"the new design doesn't match with the game, but it's cool", "SUS ඞ", "Pablo.", "bruh", "cool game",
	"kaboom", "3 months without any updates bro", "here's your attention again.", "Got your attention haha ;)", "thread == null!", "this is a random title!",
	currentVersion+" is here!", "lmao", "I know your discord username haha", "hmm, achivements...", "bro, VS Imposter Alternated is a mod of a mod :0", 
	"finally the update has come!");
	
	public static String showGraphics;
	public static String randomText;
	public static final String currentVersion = "v4.5.0";
	private static final String day = "09", month = "12", year = "2023";
	public final static String lastUpdated = year+"/"+month+"/"+day;
	public static String gameState = "Menu";
	
	public static char charPressed;
	public static GameJolt jolt;
	private final Discord discord;
	public static Window window = new Window();
	public static JFrame frame = new JFrame();
	
	private final Cursor gameCursor = toolkit.createCustomCursor(toolkit.createImage(getClass().getResource("/cursors/curBig.png")), new Point(0, 0), "cur");
	private final Image gameIcon = toolkit.createImage(getClass().getResource("/icons/Icon.png"));
	
	/* This project is under the GNU GPLv3 License:
	 * License: <https://www.gnu.org/licenses/gpl-3.0.html>
	 * Dev: <https://www.github.com/retrozinndev>
	*/
	
	public Game() {
		System.out.println("Starting The Traveler...");
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		random = new Random();
		beta = currentVersion.endsWith("b");
		devMode = devFile.exists();
		debugMode = debugFile.exists();
		amogusSecret = amogus.exists();
		randomText = words.get(random.nextInt(words.size()));
		Settings.reloadSavedSettings();
		{
			//TODO: Remove this code on next update!
			File oldFile = new File("config.save");
			if(oldFile.exists()) {
				oldFile.delete();
				System.out.println("Game has deleted the old settings file");
			}
		}
		setPreferredSize(new Dimension(Window.WIDTH * Window.SCALE, Window.HEIGHT * Window.SCALE));
		image = new BufferedImage(Window.WIDTH, Window.HEIGHT, BufferedImage.TYPE_INT_RGB);
		System.out.println("Generating window...");
		window.createFrame(frame, "The Traveler", false, false, this, gameIcon, gameCursor);
		jolt = new GameJolt(GameJolt.getID(), GameJolt.getPrivateID());
		reloadAllUI();
		spritesheet = new Spritesheets();
		Entity.entities = new ArrayList<Entity>();
		InteractibleObjects.objects = new ArrayList<InteractibleObjects>();
		Enemy.enemies = new ArrayList<Enemy>();
		BigEnemy.bosses = new ArrayList<BigEnemy>();
		Bullets.bullets = new ArrayList<Bullets>();
		Player.initialize();
		npc = new Npc(32, 32, 16, 16, Entity.DefaultNPC_EN); Entity.entities.add(npc);
		world = new World(World.mapsFolder+"level"+World.curLevel+".png");
		Minimap.initialize();
		discord = new Discord();
		if(jolt.isLoggingInFromFile) {
			File loginFile = null;
			if(gamejoltCredentialsFile.exists()) { loginFile = gamejoltCredentialsFile; }
			else if(gameCredentialsFile.exists()) { loginFile = gameCredentialsFile; }
			else if(gameCredentialsFile.exists() && gamejoltCredentialsFile.exists()) { loginFile = gamejoltCredentialsFile; } 
			jolt.autoLogin(loginFile);
			showPopup = 0;
			showLoginPopup = false;
		}
		if(!mute) Sound.noteStudiosEffect.play();
		
        if(Discord.isInstalled()) discord.startup();
		
		ui.ls.loadingScreen = !devMode;
	}
	
	public static void reloadAllUI() {
		ui = new UI();
		ui.reloadEssentials();
		UI.reloadFonts();
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		try { 
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	private void tick() {
		if(Discord.isInstalled()) {
			updPresenceTime++;
			if(updPresenceTime == maxUpdPresenceTime) {
				updPresenceTime = 0;
				DiscordRPC.discordRunCallbacks();
				discord.richPresence.update();
			}
		}
		Sound.tick();
		showLoginPopup = showPopup == 1;
		licenseOK = licenseFile.exists();
		
		if (graphicsQuality == 2) {
			if(MainMenu.portuguese)
				showGraphics = "Alta";
			else 
				showGraphics = "High";
		} else if (graphicsQuality == 1) {
			if (MainMenu.portuguese)
				showGraphics = "Baixa";
			else 
				showGraphics = "Low";
		}
		player.updateCamera();
		if (gameState.equals("Menu") && cutsceneState == enterCutscene) {
			player.setX(64);
			player.setY(416);
			player.right = false;
		}
		if(amogusSecret) {
			if(!gameState.equals("Menu") && !gameState.equals("Normal") && !ui.ls.loadingScreen) {
				amogusSecret = false;
				if(!mute) Sound.secret.play();
			}
			if(jolt.isLoggedIn) 
				jolt.trophies.achieve(Trophies.trophyList.get("sussus amogus"));
		}
		if(saveGame) {
			saveGame = false;
			if(!Game.mute) Sound.savedGame.play();
			String[] opt1 = { "level", "px", "py", "life", "coins", "ammo"};
			int[] opt2 = { World.curLevel, (int) player.x, (int) player.y, (int) player.life, playerCoins, player.ammo};
			world.saveGame(opt1, opt2, /*encode*/ui.menu.curEncode);
		} if(saveConfig) {
			saveConfig = false;
			String[] cfg1 = {"english", "portugues", "quality", "mute", "minimap", "anti-aliasing", "showPopup"};
			int[] cfg2 = {MainMenu.Eng, MainMenu.Por, graphicsQuality, Settings.mute, Settings.minimap, 
					Settings.antiAliasing, showPopup};
			ui.settings.saveConfig(cfg1, cfg2);
		} if(saveLogin) {
		    saveLogin = false;
		    if(!gamejoltCredentialsFile.exists()) 
		    	jolt.fileSave(jolt.gameCredentialsFile.getName(), jolt.userName, jolt.userToken);
		}
		if(gameState.equals("Settings")) { ui.settings.tick(); }
		if(f1Shop) {
			if(gameState.equals("Normal")) {
				gameState = "Menu";
				MainMenu.pauseMenu.showPauseMenu = true;
				MainMenu.pauseMenu.openPauseMenu = true;
			}
			new Popup("Work in Progress", Arrays.asList("The Shop is currently unavailable due to", 
					"the UI redesign and optimization work.", "Stay tuned on Game Jolt (@retrozinndev) for updates!"));
			f1Shop = false;
		}
		
		if(ESC) {
			ESC = false;
			if(gameState.equals("Credits")) {
				gameState = "Menu";
				UI.doTransition = true;
				if(!mute) Sound.backMenu.play();
			} if(MainMenu.esc && !MainMenu.pauseMenu.showPauseMenu && gameState.equals("Normal")) {
				gameState = "Menu";
				MainMenu.pauseMenu.showPauseMenu = true;
				MainMenu.pauseMenu.openPauseMenu = true;
				MainMenu.esc = false;
				if(!mute) Sound.pauseGame.play();
			}
		}
		if(gameState.equals("Normal")) {
			QExitGame = false;
			if(cutsceneState == finishCutscene && !dialogMode) {
				for (int i = 0; i < Entity.entities.size(); i++) {
					Entity e = Entity.entities.get(i);
					e.tick();
				}
				for (int i = 0; i < Bullets.bullets.size(); i++) {
					Bullets.bullets.get(i).tick();
				}
			} else if(cutsceneState == enterCutscene) {
				if (player.getX() < 162) {
					player.updateCamera();
					player.moved = true;
					player.tick();
					player.right = true;
				} else if(player.getX() >= 162) {
					player.right = false;
					player.updateCamera();
					player.moved = false;
					cutsceneState = finishCutscene;
				}
			}
			if (Enemy.enemies.size() == 0 && BigEnemy.bosses.size() == 0) {
				World.curLevel++;
				if (World.curLevel > World.maxLevel) {
					World.curLevel = 1;
				}
				world.loadLevel("level" + World.curLevel);
			}
		}
		ui.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) image.getGraphics();
		UI.useAntiAliasing(g);
		if((gameState.equals("Menu") && MainMenu.pauseMenu.showPauseMenu) || gameState.equals("Normal")) {
			world.render(g);
			for(int i = 0; i < InteractibleObjects.objects.size(); i++) {
				InteractibleObjects io = InteractibleObjects.objects.get(i);
				io.render(g);
			} for(int i = 0; i < Bullets.bullets.size(); i++) {
				Bullets bullet = Bullets.bullets.get(i);
				bullet.render(g);
			}
			Entity.entities.sort(Entity.enSorter);
			for(int i = 0; i < Entity.entities.size(); i++) {
				Entity e = Entity.entities.get(i);
				e.render(g);
			}
		}
		if(cutsceneState == enterCutscene && gameState.equals("Normal")) {
			g.setColor(UI.defaultBgColor);
			g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT/5);
			g.fillRect(0, Window.HEIGHT-30, Window.WIDTH, Window.HEIGHT/5);
		}
		
		g.dispose();
		g = (Graphics2D) bs.getDrawGraphics();
		g.drawImage(image, 0, 0, window.getWidth(), window.getHeight(), null);
		
		ui.render(g);
		if(beta) {
			g.setFont(Game.menuFont2);
			int w = g.getFontMetrics().stringWidth(currentVersion)+ 12, h = 35;
			int x = window.getWidth()-w - 12, y = window.getHeight()-h-12;
			g.setColor(UI.defaultBgColor);
			g.fillRoundRect(x, y, w, h, 30, 30);
			g.setColor(Color.gray);
			g.drawRoundRect(x, y, w, h, 30, 30);
			g.setColor(new Color(255, 255, 0));
			g.drawString(currentVersion, window.getWidth()-g.getFontMetrics().stringWidth(currentVersion)-16, window.getHeight() - g.getFontMetrics().getHeight() + 8);
		}
		if (!Game.licenseOK) {
			if (!Game.debugMode) {
				g.setFont(Game.menuFont2);
				g.setColor(new Color(255, 255, 0));
				g.drawString("The Traveler " + Game.currentVersion + " | Where is the LICENSE file?", 5, Window.HEIGHT*Window.SCALE-8);
			}
		}
		bs.show();
	}
	
	public static Random getRandom() {
		return random;
	}
	public static void sleep(long millis) throws InterruptedException {
		Thread.sleep(millis);
	}
	public static void exit(int exitCode) {
		System.out.println("Game exiting with code "+exitCode);
		System.exit(exitCode);
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		int amountOfTicks = 60;
		double ns = (double) 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				fps = frames;
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}
	
	public static int getKeyReleased() {
		return ke.getKeyCode();
	}
	public static int getKeyTyped() {
		return ke.getKeyChar();
	}
	public static int getKeyPressed() {
		return ke.getKeyCode();
	}
	public static int getMouseClicked() {
		return me.getButton();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
        charPressed = e.getKeyChar();
		if(gameState.equals("GJLogin") && Game.charPressed != '\b' && charPressed != '\n'
				&& charPressed != KeyEvent.VK_ESCAPE) {
			if (JoltLogin.curTextBox.equals("username") && jolt.userName.length() < 25) {
				jolt.userName += charPressed;
			} else if (JoltLogin.curTextBox.equals("token") && jolt.userToken.length() < 20) {
				jolt.userToken += charPressed;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		ke = e;
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if(!dialogMode) {
				ESC = true;
				MainMenu.esc = !MainMenu.pauseMenu.openPauseMenu;
				if (gameState.equals("Settings")) {
					ui.settings.esc = true;
				}
			}
		} if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
		} if (e.getKeyCode() == KeyEvent.VK_E) {
			player.keyShoot = true;
			player.isSpamming = true;
		} if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			ENTER = true;
			if(gameState.equals("Shop")) {
				Shop.shopEnter = true;
			} if (gameState.equals("Menu")) {
				ui.menu.enter = true;
			} if(gameState.equals("GJLogin")) {
				if(JoltLogin.curTextBox.equals("token")) {
					ui.gjlogin.loginBtn.selected = true;
					ui.gjlogin.loginBtn.clicked = true;
				} else if(JoltLogin.curTextBox.equals("username")) 
					JoltLogin.curTextBox = "token";
			}
		} if (e.getKeyCode() == KeyEvent.VK_Q) {
			if(gameState.equals("Game Over")) 
				QExitGame = true;
		} if (e.getKeyCode() == KeyEvent.VK_F1) {
			if(gameState.equals("Normal")) 
				f1Shop = true;
		} if (e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL) {
			player.isRunning = true;
		} if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
			if(gameState.equals("Settings")) {
				ui.settings.right = true;
			} if(gameState.equals("Menu")) {
				ui.menu.right = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
			if(gameState.equals("Settings")) {
				ui.settings.left = true;
			} if(gameState.equals("Menu")) {
				ui.menu.left = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if(gameState.equals("Normal")) {
				player.up = true;
			} if (gameState.equals("Menu")) {
				ui.menu.up = true;
			} if (gameState.equals("Settings")) {
				ui.settings.up = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if(gameState.equals("Normal")) {
				player.down = true;
			} if (gameState.equals("Menu")) {
				ui.menu.down = true;
			} if (gameState.equals("Settings")) {
				ui.settings.down = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_C) {
			if (gameState.equals("Normal")) 
				saveGame = true;
		} if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if(gameState.equals("GJLogin")) {
				if (JoltLogin.curTextBox.equals("username")) {
					if (jolt.userName != null && !jolt.userName.equals("")) {
						jolt.userName = jolt.userName.substring(0, jolt.userName.length() - 1);
					}
				} else if (JoltLogin.curTextBox.equals("token")) {
					if (jolt.userToken != null && !jolt.userToken.equals("")) {
						jolt.userToken = jolt.userToken.substring(0, jolt.userToken.length() - 1);
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL) {
			player.isRunning = false;
		}if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		} if(e.getKeyCode() == KeyEvent.VK_E) {
			player.isSpamming = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!UI.doTransition) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (gameState.equals("Menu")) {
					ui.menu.enter = true;
				} if(gameState.equals("Shop")) {
					Shop.shopEnter = true;
				}
			}
			if(Window.getMouseX() > ui.gjlogin.usernameTextBoxX && Window.getMouseX() < ui.gjlogin.usernameTextBoxX+ui.gjlogin.UsernameTextBoxWidth
			&& Window.getMouseY() > ui.gjlogin.usernameTextBoxY && Window.getMouseY() < ui.gjlogin.usernameTextBoxY+ui.gjlogin.UsernameTextBoxHeight) {
				JoltLogin.curTextBox = "username";
			} else if(Window.getMouseX() > ui.gjlogin.tokenTextBoxX && Window.getMouseX() < ui.gjlogin.tokenTextBoxX+ui.gjlogin.tokenTextBoxWidth
			&& Window.getMouseY() > ui.gjlogin.tokenTextBoxY && Window.getMouseY() < ui.gjlogin.tokenTextBoxY+ui.gjlogin.tokenTextBoxHeight) {
				JoltLogin.curTextBox = "token";
			} else {
				JoltLogin.curTextBox = "";
			}
			for(Button b : Button.buttons) {
				b.clicked = b.selected && !b.unavailable && ui.layer == b.layer;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if(gameState.equals("Normal")) {
				player.mouseShoot = true;
				player.mx = (e.getX() / Window.SCALE);
				player.my = (e.getY() / Window.SCALE);
			}
			for(Button b : Button.buttons) {
				b.isPressing = b.selected && !b.unavailable;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if(gameState.equals("Normal")) {
				player.mouseShoot = false;
				player.mx = (e.getX() / Window.SCALE);
				player.my = (e.getY() / Window.SCALE);
			}
			for(Button b : Button.buttons) {
				b.isPressing = false;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Window.mouseExited = false;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Window.mouseExited = true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(isRunning) {
			window.setMouseX(e.getX());
			window.setMouseY(e.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(isRunning) {
			window.setMouseX(e.getX());
			window.setMouseY(e.getY());
            for(Button b : Button.buttons) {
                if(Window.getMouseX() > b.getX() && Window.getMouseX() < b.getX()+b.getWidth()
                        && Window.getMouseY() > b.getY() && Window.getMouseY() < b.getY()+b.getHeight()
                        && ui.layer == b.layer) {
                    b.selected = true;
                	b.wasClicked = true;
            	} else 
                    b.selected = false;
            }
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() == -1) {
			if(gameState.equals("Credits")) Game.ui.credits.creditsScroll+=300;
		} else if(e.getWheelRotation() == +1) {
			if(gameState.equals("Credits")) Game.ui.credits.creditsScroll-=300;
		}
	}
}