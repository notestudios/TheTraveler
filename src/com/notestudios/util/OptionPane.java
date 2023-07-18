package com.notestudios.util;

import javax.swing.JOptionPane;

public class OptionPane {
	
	public static String error = "error";
	public static String warning = "alert";
	public static String info = "info";
	
	public static void jAlertWindow(String title, String msg, String type) {
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
