package com.notestudios.buttons;

import com.notestudios.menus.GJLogin;

public class GJLogin_Button2 extends Button {

	public GJLogin_Button2(int x, int y, int width, int height, int type, String text) {
		super(x, y, width, height, type, text);
	}
	
	public void functions() {
		//Logout button
		textOffsetX=-5;
		if(selected && clicked) {
			clicked = false;
			GJLogin.logoutGameJolt();
		}
	}

}
