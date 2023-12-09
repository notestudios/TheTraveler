package com.notestudios.gameapi;

import java.util.HashMap;
import java.util.Map;

import com.notestudios.main.Game;

public class Trophies {

	static boolean alreadyAchieved;
	public static Map<String, Integer> trophyList = new HashMap<>();
	static {
		Map<String, Integer> trphylst = new HashMap<>();
		trphylst.put("welcome, idkmyname!", 193422);
		trphylst.put("SPAAAAAMMMMMM", 193423);
		trphylst.put("why is he so big?", 193917);
		trphylst.put("omg you're the dev??!?!?!??!?", 193424);
		trphylst.put("sussus amogus", 198581);
		trophyList.putAll(trphylst);
	}
	public static Map<Integer, Boolean> achievedTrophyList = new HashMap<Integer, Boolean>();
	
	
	private boolean verifyIfAchieved(int trophyID) {
		if(trophyID != 0) {
			if(Game.jolt.getTrophy(trophyID).isAchieved()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean achieve(int trophyID) {
		if(verifyIfAchieved(trophyID)) {
			try {
				Game.jolt.achieveTrophy(trophyID);
			} catch(Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	/*
	public void unlockAchivementAnim(Graphics2D g, BufferedImage trophyImage, String trophyName, String trophyDescription, boolean animate) {
		int x;
		int xAnim = Window.WIDTH*Window.SCALE+(Game.bigBackground.getWidth()*4);
		int y = -16;
		if(animate) {
			x = xAnim;
		} else {
			x = Window.WIDTH*Window.SCALE-(Game.bigBackground.getWidth()*4);
		}
		g.drawImage(Game.bigBackground, x, y, Game.bigBackground.getWidth()*4, Game.bigBackground.getHeight()*4, null);
		g.drawImage(trophyImage, x+35, y+50, trophyImage.getWidth()/3, trophyImage.getHeight()/3, null);
		g.setFont(MainMenu.aFont);
		g.setColor(Color.white);
		g.drawString(trophyName, x+132+(trophyImage.getWidth()/3)-g.getFontMetrics().stringWidth(trophyName), y+80);
		g.setFont(MainMenu.aFont);
		UI.drawString(g, trophyDescription, x+62+(trophyImage.getWidth()/3), y+85);
	}*/
}
