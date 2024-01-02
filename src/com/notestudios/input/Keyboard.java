package com.notestudios.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public final class Keyboard implements KeyListener {
	
	private static Keyboard inst;
	private KeyEvent keyTyped, keyPressed, keyReleased;
	
	private Keyboard() {
		init();
	}
	
	public static final Keyboard getKeyboard() {
		if(inst == null) 
			inst = new Keyboard();
		
		return inst;
	}
	
	public void init() {
		//addKeyListener( canvas here );
	}
	
	public int getKeyPressed() {
		if(keyPressed != null)
			return keyPressed.getKeyCode();
		
		return 0;
	}
	
	public int getKeyReleased() {
		if(keyReleased != null)
			return keyReleased.getKeyCode();
		
		return 0;
		
	}
	
	public char getKeyTyped() {
		if(keyTyped != null)
			return keyTyped.getKeyChar();
		
		return 0;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		keyTyped = e;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed = e;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyReleased = e;
	}

}
