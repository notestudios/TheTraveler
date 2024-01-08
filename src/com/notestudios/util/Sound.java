package com.notestudios.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;

import com.notestudios.graphics.UI;
import com.notestudios.main.Game;
import com.notestudios.menus.MainMenu;

public class Sound {
	
	private static String soundsDir = "/sounds/", openSoundsDir = "/sounds/SoundsbyNoahKuehne/";
	private static String openMusicsDir = "/musics/";
	
	public static Clips secret = load(openSoundsDir+"SynthOrGlitch/Secret_CatDance.wav", 1);
	public static Clips loadSave = load(openSoundsDir+"Modern/V2_Modern_LOADSAVE.wav", 1);
	public static Clips savedGame = load(openSoundsDir+"Modern/V2_Modern_SAVEFILE.wav", 1);
	public static Clips pauseGame = load(openSoundsDir+"Modern/V2_Modern_PAUSE.wav", 1);
	public static Clips unpauseGame = load(openSoundsDir+"Modern/V2_Modern_UNPAUSE.wav", 1);
	public static Clips menuSelect = load(openSoundsDir+"SynthOrGlitch/V2_SoG_HOVER.wav", 1);
	public static Clips newGame = load(openSoundsDir+"SynthOrGlitch/V2_SoG_HOVER_HIGH.wav", 1);
	public static Clips errorSound = load(openSoundsDir+"Modern/V2_Modern_ERROR.wav", 1);
	public static Clips backMenu = load(openSoundsDir+"Modern/V2_Modern_BACKWARD.wav", 1);
	public static Clips menuEnter = load(openSoundsDir+"Modern/V2_Modern_FORWARD.wav", 1);
	public static Clips gameOverSoundEffect = load(soundsDir+"gameOver.wav", 1);
	public static Clips noteStudiosEffect = load(soundsDir+"NoteStudiosSoundEffect.wav", 1);
	
	public static Clips gameMusic = load(openMusicsDir+"Battle in the winter.wav", 1);
	public static Clips menuMusicloop = load(openMusicsDir+"Dawn of Derangements 2.wav", 1);
	public static Clips sussyMenuMusic = load(openMusicsDir+"freakyMenu.wav", 1);
	public static Clips creditsMusic = load(openMusicsDir+"credits.wav", 1);
	public static Clips optionsMenuMusic = load(openMusicsDir+"options menu.wav", 1);
	
	public static List<Sound> all = new ArrayList<Sound>();
	public static List<Sound> playing = new ArrayList<Sound>();
	
	public static void tick() {
		if(!Game.mute) {
			switch (Game.gameState) {
				case "Menu":
					gameMusic.pause();
					creditsMusic.pause();
					optionsMenuMusic.pause();
					if (!MainMenu.pauseMenu.pauseMode) 
						if (!UI.doTransition) menuMusicloop.loop();
					else 
						if (!UI.doTransition) menuMusicloop.loop();
				break;
				case "Normal":
					creditsMusic.pause();
					optionsMenuMusic.pause();
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.loop();
				break;
				case "Shop":
					creditsMusic.pause();
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.pause();
					optionsMenuMusic.loop();
				break;
				case "Settings":
					creditsMusic.pause();
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.pause();
					if (!UI.doTransition) optionsMenuMusic.loop();
				break;
				case "Credits":
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.pause();
					optionsMenuMusic.pause();
					if (!UI.doTransition) creditsMusic.loop();
				break;
				case "Game Over":
					creditsMusic.pause();
					optionsMenuMusic.pause();
					menuMusicloop.pause();
					sussyMenuMusic.pause();
					gameMusic.pause();
				break;
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
		private AudioFormat audioFormat;
		
		public Clips(byte[] buffer, int count)
				throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if (buffer == null)
				return;
			
			audioFormat = getAudioFormat(buffer);
			clips = new Clip[count];
			this.count = count;

			for (int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}
		
		private AudioFormat getAudioFormat(byte[] buffer) throws UnsupportedAudioFileException, IOException {
			try(ByteArrayInputStream bais = new ByteArrayInputStream(buffer)) {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bais); 
					return audioInputStream.getFormat();
			}
		}
		
		public AudioFormat getAudioFormat() {
			if(audioFormat != null)
				return audioFormat;
			
			return null;
		}

		public void play() {
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
			if(clips == null) {
				return;
			}
			clips[p].stop();
		}
		
		public void loop() {
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
	

	public static Clips load(String path, int count) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(path));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = dis.read(buffer)) >= 0) {
				baos.write(buffer, 0, read);
			}
			dis.close();
			byte[] data = baos.toByteArray();
			return new Clips(data, count);
		} catch (Exception e) {
			System.err.println("Unable to load '"+path.substring(0, 50)+"...' -> Clips.load()");
			System.out.println("The path '"+path+"' can be wrong, or the file doesn't exists.");
			try {
				return new Clips(null, 0);
			} catch (Exception ee) {
				return null;
			}
		}
	}

}
