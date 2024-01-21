package com.notestudios.shop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import com.notestudios.graphics.UI;

public class ItemList {
	
	private int x, y;
	private int width, height;
	private List<Item> items;
	
	public ItemList(int x, int y, int width, int height, List<Item> items) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.items = items;
	}
	
	public void tick() {
		for(int i = 0; i < items.size(); i++) {
			Item itm = items.get(i);
			itm.functions();
			if(itm == items.get(0)) {
				itm.setX(x+6);
				itm.setY(y+6);
			} else {
				Item prevItem = items.get(i-1);
				itm.setX(prevItem.getX()+prevItem.getWidth()+6);
				itm.setY(prevItem.getY());
				if(itm.getX()+itm.getWidth()+6 > width) {
					itm.setX(x+6);
					itm.setY(prevItem.getY()+prevItem.getHeight()+6);
				}
			}
		}
	}
	
	public void render(Graphics2D g) {
		UI.useAntiAliasing(g);
		g.setColor(new Color(39, 39, 39, 220));
		g.fillRoundRect(x, y, width, height, 30, 30);
		g.setColor(Color.white);
		g.drawRoundRect(x, y, width, height, 30, 30);
		
		for(int i = 0; i < items.size(); i++) {
			Item itm = items.get(i);
			itm.render(g);
		}
	}
	
	
}
