package com.notestudios.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;

public class Sound {
	
	public static Clips secret = load("/sounds/Menu Sounds byNoahKuehne DIST/SynthOrGlitch/Secret_CatDance.wav", 1);
	public static Clips loadSave = load("/sounds/Menu Sounds byNoahKuehne DIST/Modern/V2_Modern_LOADSAVE.wav", 1);
	public static Clips savedGame = load("/sounds/Menu Sounds byNoahKuehne DIST/Modern/V2_Modern_SAVEFILE.wav", 1);
	public static Clips pauseGame = load("/sounds/Menu Sounds byNoahKuehne DIST/Modern/V2_Modern_PAUSE.wav", 1);
	public static Clips unpauseGame = load( "/sounds/Menu Sounds byNoahKuehne DIST/Modern/V2_Modern_UNPAUSE.wav", 1);
	public static Clips menuSelect = load("/sounds/Menu Sounds byNoahKuehne DIST/SynthOrGlitch/V2_SoG_HOVER.wav", 1);
	public static Clips newGame = load("/sounds/Menu Sounds byNoahKuehne DIST/SynthOrGlitch/V2_SoG_HOVER_HIGH.wav", 1);
	public static Clips errorSound = load("/sounds/Menu Sounds byNoahKuehne DIST/Modern/V2_Modern_ERROR.wav", 1);
	public static Clips backMenu = load("/sounds/Menu Sounds byNoahKuehne DIST/Modern/V2_Modern_BACKWARD.wav", 1);
	public static Clips menuEnter = load("/sounds/Menu Sounds byNoahKuehne DIST/Modern/V2_Modern_FORWARD.wav", 1);
	public static Clips gameOverSoundEffect = load("/sounds/gameOver.wav", 1);
	public static Clips noteStudiosEffect = load("/sounds/NoteStudiosSoundEffect.wav", 1);
	
	public static Clips gameMusic = load("/musics/Battle in the winter.wav", 1);
	public static Clips menuMusicloop = load("/musics/Dawn of Derangements 2.wav", 1);
	public static Clips sussyMenuMusic = load("/musics/freakyMenu.wav", 1);
	public static Clips creditsMusic = load("/musics/credits.wav", 1);
	public static Clips optionsMenuMusic = load("/musics/options menu.wav", 1);
	//TODO: Start Menu Music
	
	public static List<Sound> allSounds = new ArrayList<Sound>();
	public static List<Sound> playingSounds = new ArrayList<Sound>();
	
	public static void tick() {
		if(!Game.mute) {
			switch (Game.gameState) {
				case "Start Menu" -> {
					creditsMusic.pause();
					optionsMenuMusic.pause();
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.pause();
				}

				case "Menu" -> {
					//menuMusicloop.setVolume(0.8f);
					gameMusic.pause();
					creditsMusic.pause();
					optionsMenuMusic.pause();
					if (!UI.menu.pause) {
						if (!Game.downTransition) {
							if (Game.secret) { sussyMenuMusic.loop();
							} else { menuMusicloop.loop(); }
						}
					} else {
						//menuMusicloop.pause();
						//sussyMenuMusic.pause();
						if (!Game.downTransition) {
							if (Game.secret) {
								sussyMenuMusic.loop();
							} else {
								menuMusicloop.loop();
							}
						}
						//pause menu music here
					}
				}
				case "Normal" -> {
					creditsMusic.pause();
					optionsMenuMusic.pause();
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.loop();
				}
				case "Shop" -> {
					creditsMusic.pause();
					optionsMenuMusic.loop();
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.pause();
				}
				case "Options" -> {
					creditsMusic.pause();
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.pause();
					if (!Game.downTransition) {
						optionsMenuMusic.loop();
					}
				}
				case "Credits" -> {
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.pause();
					optionsMenuMusic.pause();
					if (!Game.downTransition) {
						creditsMusic.loop();
					}
				}
				case "Game Over" -> {
					creditsMusic.pause();
					optionsMenuMusic.pause();
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.pause();
					System.out.println("game-over doesn't have a music yet");
					//game-over music
				}
			}
		} else {
			creditsMusic.pause();
			optionsMenuMusic.pause();
			menuMusicloop.pause();
			sussyMenuMusic.pause();
			gameMusic.pause();
		}
	}
	
	public static class Clips {
		public Clip[] clips;
		private int p;
		private int count;
		public static List<Clips> playingSounds = new ArrayList<Clips>();
		public static List<Clips> allSounds = new ArrayList<Clips>();
		
		public Clips(byte[] buffer, int count)
				throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if (buffer == null)
				return;

			clips = new Clip[count];
			this.count = count;

			for (int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}

		public void play() {
			//plays the sound
			if (clips == null)
				return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if (p >= count)
				p = 0;
		}

		public void stop() {
			//stops the sound
			if (clips == null)
				return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].stop();
			p--;
			if (p <= count)
				p = 0;
		}
		
		public void pause() {
			//pauses the sound
			if(clips == null) {
				return;
			}
			clips[p].stop();
		}
		
		public void loop() {
			//loops the sound
			if (clips == null)
				return;
			clips[p].loop(300);
		}
		
		public void setVolume(float volume) {
		    if (clips == null) {
		        return;
		    } for (int i = 0; i < count; i++) {
		    	if(volume <= 2f) {
			        FloatControl gainControl = (FloatControl) clips[i].getControl(FloatControl.Type.MASTER_GAIN);
			        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
			        gainControl.setValue(dB);
		    	} else {
		    		volume = 1f;
		    	}
		    }
		}

	}
	

	public static Clips load(String name, int count) {
		//loads the sound
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = dis.read(buffer)) >= 0) {
				baos.write(buffer, 0, read);
			}
			dis.close();
			byte[] data = baos.toByteArray();
			return new Clips(data, count);
		} catch (Exception e) {
			System.out.println("Impossible to load the sound! An error occurred. Sound: "+name);
			try {
				return new Clips(null, 0);
			} catch (Exception ee) {
				return null;
			}
		}
	}

}
