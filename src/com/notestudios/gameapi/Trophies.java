package com.notestudios.gameapi;

import com.notestudios.main.Game;

public class Trophies {
	static boolean alreadyAchieved;
	
	private static boolean verifyIfAchieved(int trophyID) {
		if(trophyID != 0) {
			if(Game.jolt.getTrophy(trophyID).isAchieved()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean achieve(int trophyID) {
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
}
