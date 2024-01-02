package com.notestudios.gameapi;

import java.util.HashMap;
import java.util.Map;

import com.notestudios.main.Game;

public class Trophies {
	
	public static Map<String, Integer> trophyList = new HashMap<>();
	static {
		Map<String, Integer> trphylst = new HashMap<>();
		trphylst.put("welcome, idkmyname!", 193422);
		trphylst.put("SPAAAAAMMMMMM", 193423);
		trphylst.put("why is he so big?", 193917);
		trphylst.put("omg you're the dev??!?!?!??!?", 193424);
		trphylst.put("sussus amogus", 198581);
		trphylst.put("kaBOOM!", 220464);
		trophyList.putAll(trphylst);
	}
	
	public static Map<Integer, Boolean> achievedTrophyList = new HashMap<Integer, Boolean>();
	
	private boolean isAchieved(int trophyID) {
		if(trophyID != 0 && !Game.jolt.getTrophy(trophyID).isAchieved()) 
			return false;
				
		return true;
	}
	
	public void achieve(int trophyID) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				if(Game.jolt.isLoggedIn) {
					if(!isAchieved(trophyID)) {
						Game.jolt.achieveTrophy(trophyID);
						System.out.println(Game.jolt.getUser().getName()+" has achieved "+Game.jolt.getTrophy(trophyID).getTitle());
					}
				} else { System.err.println("Impossible to trigger achievement, because player haven't signed in Game Jolt"); }
			}
		});
		t.start();
	}
}
