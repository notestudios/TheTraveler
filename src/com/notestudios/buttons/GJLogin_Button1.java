package com.notestudios.buttons;

import com.notestudios.main.Game;

public class GJLogin_Button1 extends Button {

	public GJLogin_Button1(int x, int y, int width, int height, int type, String text) {
		super(x, y, width, height, type, text);
	}
	
	public void functions() {
		//Back / Close Button
		if(!Game.isLoggedIn) {
			width = 300;
		} else {
			width = 145;
		}
		if(!Game.isLoggedIn) {
			textOffsetX=-2;
			if(selected && clicked) {
				clicked = false;
				if(!Game.isLoggedIn) {
					Game.USER_NAME = "";
					Game.USER_TOKEN = "";
				}
				Game.transition = true;
				Game.gameState = "Menu";
			}
		} else {
			text = "Back";
			textOffsetX=7;
			if(selected && clicked) {
				clicked = false;
				Game.transition = true;
				Game.gameState = "Settings";
			}
		}
	}
}
