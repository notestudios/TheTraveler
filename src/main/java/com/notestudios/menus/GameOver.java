package com.notestudios.menus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.util.Button;
import com.notestudios.util.Sound;

public class GameOver {
	
	private int aniFrames = 0;
	public static int width = 400;
	public static int height = Game.window.getHeight() - 34;
	public static int defaultPauseX = 16;
	public static int x = -width;
	public static int y = 16;
	public static int defaultArc = 30;
	private List<String> randomPhrases = Arrays.asList("Shouldn't a life system be nice?", "RANDOM PEOPLE ARE\nFOLLOWING ME, HELP!", 
			"wHy DoEs It TaKe\nSo LoNg?!?!", "lol what just happened", "Try again! You can do it!", "*click* *click*...\n*CLICK* *CLICK*!",
			"So I'll start spamming E instead!");
	private String randomText = randomPhrases.get(Game.getRandom().nextInt(randomPhrases.size()));
	
	public List<Button> menuButtons;
	
	private boolean secondAni = false;
	public boolean closeMenu = false;
	public boolean openMenu = false;
	private boolean restartGame, finishGame;
	
	private double defaultSpeed = 20;
	private double closeSpeed = defaultSpeed;
	private boolean finishedCloseAni = false;
	
	private double openSpeed = defaultSpeed;
	private boolean finishedOpenAni = false;
	
	public Button playAgainButton = new Button(x+30, 290, 340, 70, "Play Again!!") {
		@Override
		public void functions() {
			textOffsetY = 10;
			customFont = MainMenu.aFont;
			customBackColor = new Color(220, 220, 0);
			customTextColor = Color.black;
			x = GameOver.x + (GameOver.width/2) - getWidth()/2;
			y = GameOver.y + 290;
			if(MainMenu.english) 
				text = "Play Again!";
			else 
				text = "Jogar Novamente!";
			if(clicked) {
				clicked = false;
				closeMenu = true;
				restartGame = true;
			}
		}
		
		@Override
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
			
			g.setFont(customFont);
			g.drawString(text, getX() + ((int) width / 2) - (g.getFontMetrics().stringWidth(text)/2) + textOffsetX,
					getY() + (g.getFontMetrics().getHeight()/2) + getHeight()/2 - textOffsetY);
		}
	};
	
	public Button finishGameButton = new Button(x+30, 290, 340, 50, "< Finish Game") {
		@Override
		public void functions() {
			if(MainMenu.portuguese) { text = "<  Voltar ao Menu"; } else { text = "<  Back to Main Menu"; }
			customBackColor = new Color(50, 40, 40);
			x = playAgainButton.getX();
			y = GameOver.height - 60;
			customFont = MainMenu.aFont;
			if(clicked) {
				clicked = false;
				closeMenu = true;
				finishGame = true;
			}
			
		}
	};
	
	public GameOver() {
		menuButtons = Arrays.asList(
			playAgainButton,
			finishGameButton
		);
		Button.buttons.addAll(menuButtons);
		openMenu = true;
	}
	
	public void tick() {
		
		for(Button b : menuButtons)
			b.functions();
		
		if(Game.ENTER) {
			Game.ENTER = false;
			Game.cutsceneState = Game.enterCutscene;
			closeMenu = true;
		}
		
		if(Game.QExitGame) {
			try {
				Game.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				Game.exit(0);
			}
		}
		if(aniFrames < 255 && !secondAni) 
			aniFrames += 5;
		if(secondAni && aniFrames > 0) 
			aniFrames-=5;
		if(aniFrames == 255) 
			secondAni = true;
		if(aniFrames == 0) 
			secondAni = false;
		
		if(openMenu) 
			open();
		if(closeMenu) 
			close();
		
		if(restartGame) {
			if(finishedCloseAni) {
				Game.world.resetLevel();
				if(!Game.mute) Sound.newGame.play();
				Game.ESC = false;
				quit();
			}
		} if(finishGame) {
			if(finishedCloseAni) {
				MainMenu.pauseMenu.pauseMode = false;
				UI.doTransition = true;
				Game.world.resetLevel();
				Game.gameState = "Menu";
				quit();
			}
		}
	}
	
	public void open() {
		if(x < defaultPauseX) {
			x+=(int)openSpeed;
			if(openSpeed > 2) 
				openSpeed-=0.5;
		} else {
			finishedOpenAni = true;
		} if(finishedOpenAni) {
			finishedOpenAni = false;
			openMenu = false;
			openSpeed = defaultSpeed;
		}
	}
	
	public void close() {
		if(x+width > 0) {
			x-=(int)closeSpeed;
			if(closeSpeed > 4) 
				closeSpeed-=0.5;
		} else finishedCloseAni = true;
		if(finishedCloseAni) {
			closeMenu = false;
			closeSpeed = defaultSpeed;
		}
	}
	
	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);
		g.setColor(new Color(39, 39, 39, 240));
		g.fillRoundRect(x, y, width, height, defaultArc, defaultArc);
		g.setColor(Color.white);
		g.drawRoundRect(x, y, width, height, defaultArc, defaultArc);
		
		g.setFont(Game.travelerLogoFont);
		g.setColor(Color.white);
		g.drawString("Game Over", x+(width/2)-(g.getFontMetrics().stringWidth("Game Over")/2)+6, 
				y+g.getFontMetrics().getHeight());
		
		g.setFont(MainMenu.DialogFont);
		g.setColor(Color.white);
		int textX = x + (width/2)-(g.getFontMetrics().stringWidth(randomText)/2);
		if(randomText.contains("\n")) textX = x + (width/2) - 
				(g.getFontMetrics().stringWidth(randomText)/4);
		UI.drawString(g, randomText, textX, y + 150);
		
		for(Button b : menuButtons) 
			b.render(g);
	}
	
	public void quit() {
		Game.ui.gameOver = null;
		Button.buttons.removeAll(menuButtons);
	}
}