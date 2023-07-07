package com.notestudios.util;

import javax.swing.JOptionPane;

public class OptionPane {
	
	public static void okWindow(String title, String msg, String type) {
		switch(type) {
			case "error" -> {
				JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
			} case "alert" -> {
				JOptionPane.showMessageDialog(null, msg, title, JOptionPane.WARNING_MESSAGE);
			} case "info" -> {
				JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
			} default -> {
				JOptionPane.showMessageDialog(null, msg, title, JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
}
