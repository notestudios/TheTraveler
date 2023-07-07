package com.notestudios.discord;

import com.notestudios.main.Game;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.DisconnectedCallback;
import net.arikia.dev.drpc.callbacks.ErroredCallback;
import net.arikia.dev.drpc.callbacks.JoinGameCallback;
import net.arikia.dev.drpc.callbacks.JoinRequestCallback;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.arikia.dev.drpc.callbacks.SpectateGameCallback;

public class RPC extends DiscordEventHandlers implements DisconnectedCallback, ErroredCallback, JoinGameCallback, JoinRequestCallback, ReadyCallback, SpectateGameCallback {
	
	public static String appID = Game.DiscordAppID;
	
	@Override
	public void apply(DiscordUser arg0) {}

	@Override
	public void apply(String arg0) {}

	@Override
	public void apply(int arg0, String arg1) {}
	
	public void startup() {
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
			if(user.discriminator.equals("0")) {
				System.out.println("Welcome " + user.username + "#" + user.discriminator + "!");
			} else {
				System.out.println("Welcome @" + user.username + "!");
			}
		}).build();
		DiscordRPC.discordInitialize(appID, handlers, true);
	}
	
}
