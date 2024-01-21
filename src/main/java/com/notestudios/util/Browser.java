package com.notestudios.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Browser {
	
	public static final Desktop desktop = Desktop.getDesktop();
	
	public static final boolean canBrowse() {
		if(Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE))
			return true;
		
		return false;
	}
	
	public static void openInDefaultUserBrowser(String linkToOpen) {
		if(Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
		    try {
				desktop.browse(new URI(linkToOpen));
		    } catch(IOException e) {
				System.out.println("An I/O Exception occurred while trying to open URI on User default Web Browser: ");
				e.printStackTrace();
			} catch(URISyntaxException e) {
				System.out.println("An URI Syntax Exception occurred while trying to open URI on User default Web Browser, \n"
						+ "You can try checking the desired URI, it may be written incorrectly:");
				e.printStackTrace();
			}
		}
	}
}
