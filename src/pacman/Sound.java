package pacman;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sound {
	
    private Clip clip;
    
    public void play(String filename){
    	
    	try {
    	    File yourFile = new File(filename);
    	    AudioInputStream stream;
    	    AudioFormat format;
    	    DataLine.Info info;

    	    stream = AudioSystem.getAudioInputStream(yourFile);
    	    format = stream.getFormat();
    	    info = new DataLine.Info(Clip.class, format);
    	    clip = (Clip) AudioSystem.getLine(info);
    	    clip.open(stream);
    	    clip.start();
    	}
    	catch (Exception e) {
//    		System.out.println("Sound error");
    	}
    }
    
    public void stop() {
    	clip.stop();
    }
}