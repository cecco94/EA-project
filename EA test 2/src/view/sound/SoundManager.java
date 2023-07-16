package view.sound;


import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager {
	private float volume = 0.5f;
	private Clip music, soundEffect;
	private URL soundURL[] = new URL[10];
	private int musicId;
	public static final int MENU_MUSIC = 0;
	public static final int COIN = 1;
	
	public SoundManager() {
		soundURL[0] = getClass().getResource("/sound/tema.wav");
		soundURL[1] = getClass().getResource("/sound/coin.wav");
	/*	soundURL[2] = getClass().getResource("/sound/coin.wav");
		soundURL[3] = getClass().getResource("/sound/unlock.wav");
		soundURL[4] = getClass().getResource("/sound/powerup.wav");
		soundURL[5] = getClass().getResource("/sound/blocked.wav");
		soundURL[6] = getClass().getResource("/sound/fanfare.wav");
		soundURL[7] = getClass().getResource("/sound/cursor.wav");
		soundURL[8] = getClass().getResource("/sound/receivedamage.wav");	*/
		
	}
	
	public void setMusic(int i) {
		try {								
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			music = AudioSystem.getClip();
			music.open(ais);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setSE(int i) {
		try {								
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			soundEffect = AudioSystem.getClip();
			soundEffect.open(ais);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playMusic(int i) {
		musicId = i;
		setMusic(i);
		music.start();	
	}
	
	public void loopMusic(int i) {
		musicId = i;
		setMusic(i);
		music.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		setSE(i);
		soundEffect.start();
	}
	
	public void setMusicVolume(float v) {	  //v in percentuale da 0 a 1
		volume = v;
		FloatControl control = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
		float range  = control.getMaximum() - control.getMinimum();
		float gain = (range * volume) + control.getMinimum();
		control.setValue(gain);
	}
	
	public void setSEVolume(float v) {
		volume = v;
		FloatControl control = (FloatControl) soundEffect.getControl(FloatControl.Type.MASTER_GAIN);
		float range  = control.getMaximum() - control.getMinimum();
		float gain = (range * volume) + control.getMinimum();
		control.setValue(gain);
	}
	
	
}
