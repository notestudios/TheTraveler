package com.notestudios.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.notestudios.input.Keyboard;
import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;
import static com.notestudios.graphics.UI.drawString;

public class DialogBox {
	public static enum Orientation {
		BOTTOM, TOP, MIDDLE
	}
	
	public static List<DialogBox> dialogBoxes = new ArrayList<>(); //NOTE: This is the list of the active Dialog Boxes
	
	private int xBox, yBox, boxWidth, boxHeight;
	private Orientation dialogOrientation = Orientation.BOTTOM;
	
	private List<String> dialogs;
	private String name;
	private int curDialogIndex = 0, animationIndex = 0;
	private int nameAnimationIndex = 0;
	
	
	private Button continueButton = new Button(0, 0, 150, 50, "Continue >") {
		@Override
		public void functions() {
			x = xBox+boxWidth-continueButton.getWidth();
			y = yBox+boxHeight-continueButton.getHeight();
			arc = 40;
			customFont = MainMenu.aFont;
			if(clicked) {
				clicked = false;
				continueDialog();
			}
		}
	};
	
	public DialogBox(String name, List<String> dialogsList, Orientation dialogOrientation) {
		this.dialogs = dialogsList;
		this.name = name;
		if(dialogOrientation != null) { this.dialogOrientation = dialogOrientation; }
		boxHeight = 180;
		boxWidth = Game.window.getWidth()-140;
		xBox = (Game.window.getWidth()/2) - (boxWidth/2);
		Game.dialogMode = true;
		dialogBoxes.add(this);
		Button.buttons.add(continueButton);
		//Adjust the Dialog String lines using the '\n' character on your dialogs list.
	}
	
	public void continueDialog() {
		if(curDialogIndex+1 < dialogs.size()) {
			animationIndex = 0;
			curDialogIndex+=1;
		} else if(curDialogIndex+1 >= dialogs.size()) {
			Game.dialogMode = false;
			dialogBoxes.remove(this);
			Button.buttons.remove(continueButton);
		}
	}
	
	public void tick() {
		continueButton.functions();
		if(animationIndex < dialogs.get(curDialogIndex).length()) {
			animationIndex++;
		} if(nameAnimationIndex < name.length()) {
			nameAnimationIndex++;
		}
		if(Keyboard.getKeyboard().getKeyPressed() == KeyEvent.VK_ESCAPE) {
			dialogBoxes.remove(this);
			Game.dialogMode = false;
		}
		if(Keyboard.getKeyboard().getKeyPressed() == KeyEvent.VK_ENTER) {
			continueDialog();
		}
		switch(dialogOrientation) {
			case TOP:
				yBox = 16;
			break;
			case MIDDLE:
				yBox = Game.window.getHeight()-(boxHeight/2);
			break;
			case BOTTOM:
				yBox = Game.window.getHeight()-boxHeight-16;
			break;
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(Game.ui.getTheme());
		g.fillRoundRect(xBox, yBox, boxWidth, boxHeight, 60, 60);
		g.setColor(Game.ui.getTheme());
		g.drawRoundRect(xBox, yBox, boxWidth, boxHeight, 60, 60);
		
		g.setColor(Color.white);
		g.setFont(MainMenu.TheFont);
		int nameY;
		if(dialogOrientation == Orientation.TOP) { nameY = yBox+(g.getFontMetrics().getHeight()/2); }
		else { nameY = yBox+(g.getFontMetrics().getHeight()/2); }
		g.drawString("- "+name.substring(0, nameAnimationIndex)+" -", xBox+(boxWidth/2)-(g.getFontMetrics().stringWidth("- "+name+" -")/2), nameY);
		
		g.setFont(MainMenu.DialogFont);
		g.setColor(Color.white);
		drawString(g, dialogs.get(curDialogIndex).substring(0, animationIndex), xBox+16, 
				(int)(yBox+g.getFontMetrics(MainMenu.TheFont).getHeight()/(dialogs.get(curDialogIndex).chars().filter(num -> num == '\n').count())));
		continueButton.render(g);
	}
	
	public List<String> getDialogs() {
		return dialogs;
	}
}
