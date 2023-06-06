package com.notestudios.buttons;

import java.awt.Color;

import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;

public class Settings_Button0 extends Button{

	public Settings_Button0(int x, int y, int width, int height, int type, String text) {
		super(x, y, width, height, type, text);
		//directory = "";
	}
	
	public void functions() {
		textOffsetX=-170;
		textOffsetY=-20;
		customFont = MainMenu.menuFont;
		customBackColor = new Color(66, 66, 66);
		customTextColor = Color.white;
		if(selected && clicked) {
			clicked = false;
			Game.transition = true;
			Game.gameState = "GJLogin";
		}
	}
	
}
