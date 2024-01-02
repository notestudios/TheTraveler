package com.notestudios.util;

import java.awt.Color;
import java.awt.Graphics2D;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;

public class Transition {
	
	private double x = 0, width = 0;
	private int height = Game.window.getHeight();
	private double defaultSpd = 60, transitionSpd = 60, transitionSpd2 = 60;
	public boolean stopTransition = false;
	
	public void makeTransition(Graphics2D g) {
		if(UI.doTransition && !stopTransition) {
			g.setColor(Color.black);
			g.fillRect(0, 0, Game.window.getWidth(), Game.window.getHeight());
			g.setColor(new Color(39, 39, 39, 255));
			g.fillRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
			g.setColor(Color.white);
			g.drawRoundRect(5, 5, Game.window.getWidth() - 10, Game.window.getHeight() - 10, 30, 30);
			
			run();
		}
		
		g.setColor(new Color(10, 10, 11, 255));
		g.fillRect((int)x, 0, (int)width, height);
		
		stop();
	}
	
	private void run() {
		if(width < Game.window.getWidth()) {
			width+=transitionSpd;
			if(transitionSpd > 4) 
				transitionSpd-=1.5;
		} else {
			stopTransition = true;
			transitionSpd = defaultSpd;
		}
	}
	
	private void stop() {
		if(stopTransition) {
			x+=transitionSpd2;
			width-=transitionSpd2;
			if(transitionSpd2 > 4) 
				transitionSpd2-=1.5;
		} if(x >= Game.window.getWidth()) {
			transitionSpd = defaultSpd;
			transitionSpd2 = defaultSpd;
			x = 0;
			width = 0;
			stopTransition = false;
			UI.doTransition = false;
		}
	}
	
}
