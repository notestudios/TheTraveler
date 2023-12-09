package com.notestudios.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.main.Window;
import com.notestudios.util.Button;
import com.notestudios.util.Sound;

public class Credits {
	
	public int creditsScroll = 0;
	public boolean backSelect = false;
	private int px = -15;
	
	public Credits() {
		Button.buttons.add(this.backButton);
	}
	
	public Button backButton = new Button(10, 50, 180, 50, "<  Credits") {
		@Override
		public void functions() {
			if(MainMenu.portuguese) text = "<  Créditos"; else text = "<  Credits";
			customFont = MainMenu.aFont;
			if(clicked) {
				clicked = false;
				UI.doTransition = true;
				if(!Game.mute) Sound.backMenu.play();
				Game.gameState = "Menu";
				quit();
			}
		}
	};
	
	public void tick() {
		backButton.functions();
		if(creditsScroll < -300 && creditsScroll != -600) {
			creditsScroll = -300;
		} if(creditsScroll < -600) {
			creditsScroll = -600;
		} if(creditsScroll > 0) {
			creditsScroll = 0;
		}
	}
	
	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);
		g.setColor(new Color(39, 39, 39, 220));
		g.fillRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
		g.setColor(Color.white);
		g.drawRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
		
		g.setFont(MainMenu.aFont);
		g.setColor(Color.white);
		g.drawString("Game Programming "
				+ "and Game Arts:", 70+px, 170+creditsScroll);
		
		g.setFont(Game.noteLogoFont);
		g.drawString("Note", 120+px, 270+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 30));
		g.drawString("S T U D I O S", 190+px, 315+creditsScroll);
		
		g.setFont(MainMenu.DialogFont);
		g.drawString("github.com/retrozinndev", 135+px, 350+creditsScroll);
		
		g.setFont(MainMenu.aFont);
		g.drawString("Dawn of the Derangements"
				+ " (Menu Music):", 30+px, 410+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 20));
		g.drawString("MegaPixelMusic (OpenGameArt.org)", 110+px, 450+creditsScroll);
		
		g.setFont(MainMenu.aFont);
		g.drawString("Battle in the Winter"
				+ " (Game Music):", 80+px, 520+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 20));
		g.drawString("Johan Brodd (OpenGameArt.org)", 130+px, 570+creditsScroll);
		
		g.fillRect(545, 0, 5, Window.HEIGHT*Window.SCALE);
		
		g.setFont(MainMenu.aFont);
		g.drawString("Peachtea - Somewhere in", 610+px, 60+creditsScroll);
		g.drawString("the Elevator (Settings Menu)", 590+px, 95+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 20));
		g.drawString("You're Perfect Studio", 670+px, 150+creditsScroll);
		g.drawString("(OpenGameArt.org)", 678+px, 175+creditsScroll);
		
		g.setFont(MainMenu.aFont);
		g.drawString("Time to Think - The Mnk", 620+px, 250+creditsScroll);
		g.drawString("(Credits Menu) > This Music <", 585+px, 285+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 20));
		g.drawString("Juan Pablo Campuzano Serna", 630+px, 330+creditsScroll);
		g.drawString("(OpenGameArt.org)", 678+px, 355+creditsScroll);
		
		if(Game.amogusSecret) {
			g.setColor(Color.white);
			g.setFont(MainMenu.aFont);
			g.drawString("Susus moogus Menu Music", 600+px, 420+creditsScroll);
			g.drawString("(Vs Impostor Music):", 645+px, 460+creditsScroll);
			g.setFont(new Font("Segoe UI", Font.BOLD, 20));
			g.drawString("Innersloth & Impostorm", 654+px, 510+creditsScroll);
			g.drawString("(Friday Night Funkin' Vs Impostor MOD)", 575+px, 540+creditsScroll);
			g.drawString("innersloth.com | vsimpostor.com", 615+px, 585+creditsScroll);
			
			g.setColor(Color.white);
			g.setFont(MainMenu.aFont);
			g.drawString("Menu Sounds", 690+px, 420+245+creditsScroll);
			g.drawString("(Effects and some Musics)", 600+px, 460+245+creditsScroll);
			g.setFont(new Font("Segoe UI", Font.BOLD, 20));
			g.drawString("By NoahKuehne", 690+px, 510+245+creditsScroll);
			g.drawString("(Menu Sounds V2 byNoahKuehne)", 610+px, 540+245+creditsScroll);
			g.drawString("(No link provided)", 680+px, 585+245+creditsScroll);
			
		} else {
			g.setColor(Color.white);
			g.setFont(MainMenu.aFont);
			g.drawString("Menu Sounds", 690+px, 420+creditsScroll);
			g.drawString("(Effects and some Musics)", 600+px, 460+creditsScroll);
			g.setFont(new Font("Segoe UI", Font.BOLD, 20));
			g.drawString("By NoahKuehne", 690+px, 510+creditsScroll);
			g.drawString("(Menu Sounds V2 byNoahKuehne)", 610+px, 540+creditsScroll);
			g.drawString("(No link provided)", 680+px, 585+creditsScroll);
		}
		
		//g.drawImage(Game.bigBackground, -3, -22, WIDTH+5, HEIGHT+45, null);
		/*
		if(Game.graphicsQuality == 2) {
			g.drawImage(Game.bigBackground, -55*Window.SCALE, -25*Window.SCALE, 112*Window.SCALE, 48*Window.SCALE, null);
			g.drawImage(Game.defaultLargeOptionBg, -99*Window.SCALE, 16*Window.SCALE, 144*Window.SCALE, 16*Window.SCALE, null);//back button
		}
		*/
		
		backButton.render(g);
		
		if(creditsScroll == -300) {
			g.setColor(Color.lightGray);
			g.fillRect(Window.WIDTH*Window.SCALE-8, 278+7, 8, Window.HEIGHT*2);
			g.setColor(Color.white);
			g.fillRect(Window.WIDTH*Window.SCALE-8, 278, 8, Window.HEIGHT*2);
		} else if(creditsScroll == 0) {
			g.setColor(Color.lightGray);
			g.fillRect(Window.WIDTH*Window.SCALE-8, 40+7, 8, Window.HEIGHT*2);
			g.setColor(Color.white);
			g.fillRect(Window.WIDTH*Window.SCALE-8, 40, 8, Window.HEIGHT*2);
		}
		
	}
	
	public void quit() {
		Game.ui.credits = null;
		Button.buttons.remove(this.backButton);
	}
	
}
