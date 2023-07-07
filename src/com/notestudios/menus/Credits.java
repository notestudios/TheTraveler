package com.notestudios.menus;

import java.awt.*;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.util.Sound;

public class Credits {
	
	public int creditsScroll = 0;
	public boolean backSelect = false;
	private int px = -15;
	
	public void tick() {
		if(!Game.cutsceneCredits) {
			if(creditsScroll < -300 && creditsScroll != -600) {
				creditsScroll = -300;
			} if(creditsScroll < -600) {
				creditsScroll = -600;
			} if(creditsScroll > 0) {
				creditsScroll = 0;
			}
		}
		
		if(backSelect && Game.creditsEnter) {
			Game.creditsEnter = false;
			backSelect = false;
			UI.menu.currentOption = 5;
			if(!Game.mute) {
				Sound.backMenu.play();
			}
			Game.downTransition = true;
			MainMenu.enter = false;
			Game.gameState = "Menu";
		}
		
	}
	
	public void render(Graphics2D g) {
		
		g.setColor(new Color(30, 30, 31));
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		
		g.setFont(MainMenu.aFont);
		g.setColor(Color.black);
		g.drawString("Game Programming "
				+ "and Game Arts:", 70+3+px, 190+2+creditsScroll);
		
		g.setFont(Game.noteLogoFont);
		g.drawString("Note", 120+3+px, 290+2+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 30));
		g.drawString("S T U D I O S", 190+3+px, 340+2+creditsScroll);
		
		g.setFont(MainMenu.aFont);
		g.drawString("Dawn of the Derangements"
				+ " (Menu Music):", 30+3+px, 410+2+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 20));
		g.drawString("MegaPixelMusic (OpenGameArt.org)", 110+3+px, 450+2+creditsScroll);
		
		g.setFont(MainMenu.aFont);
		g.drawString("Battle in the Winter"
				+ " (Game Music):", 80+3+px, 520+2+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 20));
		g.drawString("Johan Brodd (OpenGameArt.org)", 130+3+px, 570+2+creditsScroll);
		
		g.setFont(MainMenu.aFont);
		g.drawString("Peachtea - Somewhere in", 610+3+px, 60+2+creditsScroll);
		g.drawString("the Elevator (Settings Menu)", 590+3+px, 95+2+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 20));
		g.drawString("You're Perfect Studio", 670+3+px, 150+2+creditsScroll);
		g.drawString("(OpenGameArt.org)", 678+3+px, 175+2+creditsScroll);
		
		g.setFont(MainMenu.aFont);
		g.drawString("Time to Think - The Mnk", 620+3+px, 250+2+creditsScroll);
		g.drawString("(Credits Menu) > This Music <", 585+3+px, 285+2+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 20));
		g.drawString("Juan Pablo Campuzano Serna", 630+3+px, 330+2+creditsScroll);
		g.drawString("(OpenGameArt.org)", 678+3+px, 355+2+creditsScroll);
		
		/***/
		
		g.setFont(MainMenu.aFont);
		g.setColor(Color.white);
		g.drawString("Game Programming "
				+ "and Game Arts:", 70+px, 190+creditsScroll);
		
		g.setFont(Game.noteLogoFont);
		g.drawString("Note", 120+px, 290+creditsScroll);
		g.setFont(new Font("Segoe UI", Font.BOLD, 30));
		g.drawString("S T U D I O S", 190+px, 340+creditsScroll);
		
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
		
		g.fillRect(545, 0, 5, Game.HEIGHT*Game.SCALE);
		
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
		
		if(Game.amogus.exists()) {
			
			g.setFont(MainMenu.aFont);
			g.setColor(Color.black);
			g.drawString("Susus moogus Menu Music", 603+px, 422+creditsScroll);
			g.drawString("(Vs Impostor Music):", 648+px, 462+creditsScroll);
			g.setFont(new Font("Segoe UI", Font.BOLD, 20));
			g.drawString("Innersloth & Impostorm", 657+px, 512+creditsScroll);
			g.drawString("(Friday Night Funkin' Vs Impostor MOD)", 576+px, 543+creditsScroll);
			g.drawString("innersloth.com | vsimpostor.com", 618+px, 587+creditsScroll);
			
			g.setColor(Color.white);
			g.setFont(MainMenu.aFont);
			g.drawString("Susus moogus Menu Music", 600+px, 420+creditsScroll);
			g.drawString("(Vs Impostor Music):", 645+px, 460+creditsScroll);
			g.setFont(new Font("Segoe UI", Font.BOLD, 20));
			g.drawString("Innersloth & Impostorm", 654+px, 510+creditsScroll);
			g.drawString("(Friday Night Funkin' Vs Impostor MOD)", 575+px, 540+creditsScroll);
			g.drawString("innersloth.com | vsimpostor.com", 615+px, 585+creditsScroll);
			
			g.setColor(Color.black);
			g.setFont(MainMenu.aFont);
			g.drawString("Menu Sounds", 693+px, 423+245+creditsScroll);
			g.drawString("(Effects and some Musics)", 603+px, 463+245+creditsScroll);
			g.setFont(new Font("Segoe UI", Font.BOLD, 20));
			g.drawString("By NoahKuehne", 693+px, 510+245+creditsScroll);
			g.drawString("(Menu Sounds V2 byNoahKuehne)", 613+px, 543+245+creditsScroll);
			g.drawString("(No link provided)", 683+px, 588+245+creditsScroll);
			
			g.setColor(Color.white);
			g.setFont(MainMenu.aFont);
			g.drawString("Menu Sounds", 690+px, 420+245+creditsScroll);
			g.drawString("(Effects and some Musics)", 600+px, 460+245+creditsScroll);
			g.setFont(new Font("Segoe UI", Font.BOLD, 20));
			g.drawString("By NoahKuehne", 690+px, 510+245+creditsScroll);
			g.drawString("(Menu Sounds V2 byNoahKuehne)", 610+px, 540+245+creditsScroll);
			g.drawString("(No link provided)", 680+px, 585+245+creditsScroll);
			
		} else {
			g.setColor(Color.black);
			g.setFont(MainMenu.aFont);
			g.drawString("Menu Sounds", 693+px, 423+creditsScroll);
			g.drawString("(Effects and some Musics)", 603+px, 463+creditsScroll);
			g.setFont(new Font("Segoe UI", Font.BOLD, 20));
			g.drawString("By NoahKuehne", 693+px, 510+creditsScroll);
			g.drawString("(Menu Sounds V2 byNoahKuehne)", 613+px, 543+creditsScroll);
			g.drawString("(No link provided)", 683+px, 588+creditsScroll);
			
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
		if(Game.graphics == 2) {
			g.drawImage(Game.bigBackground, -55*Game.SCALE, -25*Game.SCALE, 112*Game.SCALE, 48*Game.SCALE, null);
			g.drawImage(Game.defaultLargeOptionBg, -99*Game.SCALE, 16*Game.SCALE, 144*Game.SCALE, 16*Game.SCALE, null);//back button
		}
		
		
		g.setFont(MainMenu.TheFont);
		g.setColor(Color.white);
		if(MainMenu.english) {
			g.drawString("Credits", 30, 48);
		} else if(MainMenu.portuguese) {
			g.drawString("Créditos", 22, 48);
		}
		g.setFont(MainMenu.aFont);
		g.setColor(Color.white);
		if(MainMenu.english) {
			g.drawString("< Back", 30, 106);
		} else if(MainMenu.portuguese) {
			g.drawString("< Voltar", 20, 106);
		}
		g.setFont(new Font("Segoe UI", Font.BOLD, 12));
		g.drawString("(ESC)", 125, 90);
		
		if(creditsScroll == -300) {
			g.setColor(Color.lightGray);
			g.fillRect(Game.WIDTH*Game.SCALE-8, 278+7, 8, Game.HEIGHT*2);
			g.setColor(Color.white);
			g.fillRect(Game.WIDTH*Game.SCALE-8, 278, 8, Game.HEIGHT*2);
		} else if(creditsScroll == 0) {
			g.setColor(Color.lightGray);
			g.fillRect(Game.WIDTH*Game.SCALE-8, 40+7, 8, Game.HEIGHT*2);
			g.setColor(Color.white);
			g.fillRect(Game.WIDTH*Game.SCALE-8, 40, 8, Game.HEIGHT*2);
		}
		if(backSelect) {
			g.setColor(Color.white);
			g.drawRoundRect(-32, 68, 50 * Game.SCALE, 14 * Game.SCALE, 14, 14);
		}
		
	}
	
	
}
