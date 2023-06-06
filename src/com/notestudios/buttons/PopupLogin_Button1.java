package com.notestudios.buttons;


import com.notestudios.main.Game;

public class PopupLogin_Button1 extends Button{

	public PopupLogin_Button1(int x, int y, int width, int height, int type, String text) {
		super(x, y, width, height, type, text);
		// TODO Auto-generated constructor stub
	}
	
	public void functions() {
		if(selected && clicked) {
			clicked = false;
			Game.showPopup = 0;
			Game.saveConfig = true;
			Game.loginPopup = false;
		}
	}

}
