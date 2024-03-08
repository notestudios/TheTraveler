package com.notestudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
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

import com.notestudios.discord.Discord;
import com.notestudios.entities.BigEnemy;
import com.notestudios.entities.Bomb;
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
import com.notestudios.objects.InteractibleObjects;
import com.notestudios.util.Button;
import com.notestudios.util.Sound;
import com.notestudios.world.World;

public class Game implements Runnable, KeyListener, MouseListener,
MouseMotionListener, MouseWheelListener {
	
	public static final long serialVersionUID = 1L;

	private Thread thread;
	
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
	
	private BufferedImage img;
	public Spritesheets spritesheet;
	private File debugFile = new File("enableDebug.txt");
	private File licenseFile = new File("LICENSE.txt");
	private File devFile = new File("README.md");
	public static File configSave = new File("settings.save"),
	gameCredentialsFile = new File("gamejolt.save"),
	gamejoltCredentialsFile = new File(".gj-credentials");
	public File amogus = new File("amogus.txt");
	
	private List<String> words = Arrays.asList("christmas is approching fast!",
	"frontend or backend?", "hello there", "potato", "this is "+currentVersion+"!", "what's up?", 
	"fun fact: the developer likes coding ;)", "shop is available!!",
	"v5 when?", "finally the 'kaboom' title makes sense",
	"the new design is cool!", "SUS ඞ", "Pablo.", "bruh", "cool game", "kaboom", "3 months without any updates bro", 
	"here's your attention again.", "Got your attention haha ;)", "thread == null!", "this is a random title!",
	currentVersion+" is here!", "lmao", "I know your discord username haha", "hmm, achivements...", 
	"Indie Cross v2???", "finally the update has come!", "bug fixing is what i do most!");
	
	public static String showGraphics;
	public static String randomText;
	public static final String currentVersion = "HAPPY NEW YEAR! v4.5.1";
	private static final String day = "06", month = "01", year = "2024";
	public final static String lastUpdated = year+"/"+month+"/"+day;
	public static String gameState = "Menu";
	
	public static char charPressed;
	public static GameJolt jolt;
	private final Discord discord;
	public static Window window = new Window();
	
	/* This project is under the GNU GPLv3 License:
	 * License: <https://www.gnu.org/licenses/gpl-3.0.html>
	 * Dev: <https://www.github.com/retrozinndev>
	*/
	
	private Game() {
		System.out.println("Starting The Traveler...");
		window.addListeners(this); // input handlers will change in the future
		window.init();
		random = new Random();
		beta = currentVersion.endsWith("b");
		devMode = devFile.exists();
		debugMode = debugFile.exists();
		amogusSecret = amogus.exists();
		randomText = words.get(random.nextInt(words.size()));
		spritesheet = new Spritesheets();
		Settings.reloadSavedSettings();
		img = new BufferedImage(Window.WIDTH, Window.HEIGHT, BufferedImage.TYPE_INT_RGB);
		System.out.println("Generating window...");
		jolt = new GameJolt();
		reloadAllUI();
		Entity.entities = new ArrayList<Entity>();
		InteractibleObjects.objects = new ArrayList<InteractibleObjects>();
		Enemy.enemies = new ArrayList<Enemy>();
		BigEnemy.bosses = new ArrayList<BigEnemy>();
		Bullets.bullets = new ArrayList<Bullets>();
		npc = new Npc(32, 32, 16, 16, Npc.defaultNPC); Entity.entities.add(npc);
		world = new World(World.mapsFolder+"level"+World.curLevel+".png");
		Minimap.initialize();
		discord = new Discord();
		if(jolt.isLoggingInFromFile) {
			File loginFile = null;
			if(gamejoltCredentialsFile.exists()) { loginFile = gamejoltCredentialsFile; }
			else if(gameCredentialsFile.exists()) { loginFile = gameCredentialsFile; }
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
		if(Discord.isInstalled()) { discord.runPresence(); }
		Sound.tick();
		showLoginPopup = showPopup == 1;
		licenseOK = licenseFile.exists();
		player.updateCamera();
		if (gameState.equals("Menu") && cutsceneState == enterCutscene) {
			player.setX(64);
			player.setY(416);
			player.right = false;
		}
		if(MainMenu.pauseMenu.finishedOpenAni || MainMenu.pauseMenu.finishedCloseAni) {
			MainMenu.pauseMenu.timeLimit++;
			if(MainMenu.pauseMenu.timeLimit >= MainMenu.pauseMenu.maxTime) {
				MainMenu.pauseMenu.timeLimit = 0;
				MainMenu.pauseMenu.finishedOpenAni = false;
				MainMenu.pauseMenu.finishedCloseAni = false;
			}
		}
		if(MainMenu.pauseMenu.finishedCloseAni && ui.menu.loadGameSave) {
			ui.menu.loadGameSave = false;
			ui.menu.load();
		}
		if(amogusSecret) {
			if(!gameState.equals("Menu") && !gameState.equals("Normal") && !ui.ls.loadingScreen) {
				amogusSecret = false;
				if(!mute) Sound.secret.play();
			}
			if(jolt.isLoggedIn) { jolt.trophies.achieve(Trophies.trophyList.get("sussus amogus")); }
		}
		if(saveGame) {
			saveGame = false;
			if(!Game.mute) Sound.savedGame.play();
			String[] opt1 = { "level", "px", "py", "life", "coins", "ammo"};
			int[] opt2 = { World.curLevel, (int) player.getX(), (int) player.getY(), (int) player.life, playerCoins, player.ammo};
			world.saveGame(opt1, opt2, ui.menu.curEncode);
		} if(saveConfig) {
			saveConfig = false;
			String[] cfg1 = {"english", "portugues", "quality", "mute", "minimap", "anti-aliasing", "showPopup"};
			int[] cfg2 = {MainMenu.Eng, MainMenu.Por, graphicsQuality, Settings.mute, Settings.minimap, 
					Settings.antiAliasing, showPopup};
			ui.settings.saveConfig(cfg1, cfg2);
		} if(saveLogin) {
		    saveLogin = false;
		    if(!gamejoltCredentialsFile.exists()) 
		    	jolt.fileSave(gameCredentialsFile.getName(), jolt.userName, jolt.userToken);
		}
		if(f1Shop) {
			f1Shop = false;
			UI.doTransition = true;
			gameState = "Shop";
		}
		if((!window.hasFocus() && gameState.equals("Normal")) && !devMode) {
			MainMenu.pauseMenu.pauseMode = true;
			MainMenu.pauseMenu.openPauseMenu = true;
		}
		if(ESC) {
			ESC = false;
			if(gameState.equals("Credits")) {
				gameState = "Menu";
				UI.doTransition = true;
				if(!mute) Sound.backMenu.play();
			} if(MainMenu.esc && !MainMenu.pauseMenu.pauseMode && gameState.equals("Normal")) {
				gameState = "Menu";
				MainMenu.pauseMenu.pauseMode = true;
				MainMenu.pauseMenu.openPauseMenu = true;
				MainMenu.esc = false;
				if(!mute) Sound.pauseGame.play();
			}
		}
		if(gameState.equals("Normal")) {
			if(cutsceneState == finishCutscene && !dialogMode) {
				for(int i = 0; i < Entity.entities.size(); i++) {
					Entity.entities.get(i).tick();
				} for(int i = 0; i < InteractibleObjects.objects.size(); i++) {
					InteractibleObjects.objects.get(i).tick();
				} for(int i = 0; i < Bullets.bullets.size(); i++) {
					Bullets.bullets.get(i).tick();
				}
				world.tick();
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
		}
		if(Bomb.canTriggerAchievement && Bomb.explodedEnemies >= Bomb.maxEnemies && Bomb.explodedEnemies <= 25) {
			Bomb.canTriggerAchievement = false;
			Bomb.explodedEnemies = 0;
			Bomb.triggerAchievement();
		}
		ui.tick();
	}
	
	private void render() {
		BufferStrategy bs = window.getBufferStrategy();
		if (bs == null) {
			window.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) img.getGraphics();
		UI.useAntiAliasing(g);
		if((gameState.equals("Menu") && MainMenu.pauseMenu.pauseMode) || gameState.equals("Normal")) {
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
		g.drawImage(img, 0, 0, window.getWidth(), window.getHeight(), null);
		
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
				g.drawString("The Traveler " + Game.currentVersion + " | Where is the LICENSE file?", 5, window.getHeight()-8);
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
		window.requestFocus();
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
		} if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.placeBomb = true;
		} if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			ENTER = true;
			if (gameState.equals("Menu")) {
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
			player.up = true;
			if(gameState.equals("Menu")) {
				ui.menu.up = true;
			} if(gameState.equals("Settings")) {
				ui.settings.up = true;
			}
		} if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			if (gameState.equals("Menu")) {
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
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		} if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		} if(e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL) {
			player.isRunning = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!UI.doTransition) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (gameState.equals("Menu")) {
					ui.menu.enter = true;
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
			for(Button b : Button.buttons) 
				b.isPressing = b.selected && !b.unavailable;
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
