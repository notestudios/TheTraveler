package com.notestudios.discord;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordUser;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.callbacks.DisconnectedCallback;
import net.arikia.dev.drpc.callbacks.ErroredCallback;
import net.arikia.dev.drpc.callbacks.JoinGameCallback;
import net.arikia.dev.drpc.callbacks.JoinRequestCallback;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.arikia.dev.drpc.callbacks.SpectateGameCallback;

public class Discord extends DiscordEventHandlers implements DisconnectedCallback, 
ErroredCallback, JoinGameCallback, JoinRequestCallback, ReadyCallback, SpectateGameCallback {
	
	private String appID = "1087444872132313168";
	public RichPresence richPresence;
	
	public Discord() {
		System.out.println("Loading Discord RPC...");
		richPresence = new RichPresence();
	}
	
	@Override
	public void apply(DiscordUser arg0) {
		
	}

	@Override
	public void apply(String arg0) {
		
	}

	@Override
	public void apply(int arg0, String arg1) {
		
	}
	
	public static boolean isInstalled() {
		String os = System.getProperty("os.name");
		File homeDir = FileSystemView.getFileSystemView().getHomeDirectory();
		File settingsFile;
		
		switch(os.toLowerCase()) {
			case "linux":
				settingsFile = new File(homeDir+"/.config/discord");
			return settingsFile.isDirectory();
			
			case "windows":
				settingsFile = new File(System.getProperty("user.home")+"/AppData/Roaming/discord");
			return settingsFile.isDirectory();
			// I Don't have a mac to test the game :(
		}
		
		return false;
	}
	
	public void startup() {
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
			final String welcomeMessage = "Discord RPC loaded with ";
			if(user.discriminator.trim().equals("0")) {
				System.out.println(welcomeMessage + "@" + user.username + "!");
			} else {
				System.out.println(welcomeMessage + user.username + "#" + user.discriminator + "!");
			}
		}).build();
		DiscordRPC.discordInitialize(appID, handlers, true);
	}
	
	public String getAppID() {
		return appID;
	}
}
