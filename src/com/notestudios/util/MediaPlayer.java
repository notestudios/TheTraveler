package com.notestudios.util;

import java.io.File;

public class MediaPlayer {
	
	public Media mediaExampleThatICreatedMyself = Media.getMedia("retrozinndev.mp4"); 
	
    public MediaPlayer() {
    	//TODO: add an Media Player library (I'm thinking of vlcj, but I'm not sure)
    	//This is a part of the code, it's not done yet.
    }
    
    public static class Media {
    	public Media(File media) {
    		
    	}
    	public static Media getMedia(String mediaPath) {
    		try {
    			return new Media(new File(mediaPath));
    		} catch(Exception e) {
    			e.printStackTrace();
    			return null;
    		}
    	}
    }
    
    public class Player {
    	public void play(Media mediaToPlay) {
    		
    	}
    	public void pause() {
    		
    	}
    	public void stop() {
    		
    	}
    	public void setVolume() {
    		
    	}
    }

}
