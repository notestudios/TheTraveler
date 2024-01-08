package com.notestudios.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private static Mouse inst;
	private MouseWheelEvent mouseWheelMoved;
	private MouseEvent mouseDragged, mouseMoved, mouseClicked, mousePressed, 
		mouseReleased;
	private boolean mouseEntered, mouseExited;
	
	private Mouse() {
		init();
	}
	
	public static Mouse getMouse() {
		if(inst == null) 
			inst = new Mouse();
		
		return inst;
	}
	
	public void init() {
		//addMouseListener(this);
		//addMouseMotionListener(this);
		//addMouseWheelListener(this);
	}
	
	public MouseWheelEvent getMouseWheel() {
		return mouseWheelMoved;
	}
	
	public MouseEvent getMouseDragged() {
		return mouseDragged;
	}
	
	public MouseEvent getMouseMoved() {
		return mouseMoved;
	}
	
	public MouseEvent getMouseClicked() {
		return mouseClicked;
	}
	
	public MouseEvent getMousePressed() {
		return mousePressed;
	}
	
	public MouseEvent getMouseReleased() {
		return mouseReleased;
	}
	
	public boolean getMouseEntered() {
		return mouseEntered;
	}
	
	public boolean getMouseExited() {
		return mouseExited;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelMoved = e;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseDragged = e;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMoved = e;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClicked = e;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = e;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseReleased = e;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseExited = false;
		mouseEntered = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseEntered = false;
		mouseExited = true;
	}

}
