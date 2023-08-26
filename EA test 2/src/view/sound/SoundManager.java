package view.sound;


import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager {
	//il volume è un numero da 0 a 1
	private float musicVolume = 0.2f, seVolume = 0.2f;
	private Clip music, soundEffect;
	private URL soundURL[] = new URL[10];
	public static final int MENU_MUSIC = 0, AULA_STUDIO = 1, DORMITORIO = 2, BIBLIOTECA = 3,
							COLPO = 4, TENDA = 5, FUOCO = 6, LABORATORIO = 7;
	
	// tutti i percorsi dei file dei suoni vengono inseriti in un array
	public SoundManager() {
		soundURL[0] = getClass().getResource("/sound/menuLeggera.wav");
		soundURL[1] = getClass().getResource("/sound/salaStudioLeggera.wav");
		soundURL[2] = getClass().getResource("/sound/dormitorioLeggera.wav");
		soundURL[3] = getClass().getResource("/sound/biblioteca.wav");
		soundURL[4] = getClass().getResource("/sound/hitmonster.wav");
		soundURL[5] = getClass().getResource("/sound/tenda.wav");
		soundURL[6] = getClass().getResource("/sound/burning.wav");
		soundURL[7] = getClass().getResource("/sound/laboratorio.wav");	
		
		setMusic(MENU_MUSIC);
		setSE(COLPO);
	}
	
	// per suonare una musica, prima bisogna dire alla clip quale file prendere
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
	
	//idem per gli effetti
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
		setMusic(i);
		music.start();	
	}
	
	public void loopMusic(int i) {
		setMusic(i);
		music.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		setSE(i);
		soundEffect.start();
		setSEVolume(seVolume);
	}
	
	public void setSEVolume(float v) {
		if (v > 0f && v < 1f) {
			this.seVolume = v;
	    	
	    	FloatControl gainControl = (FloatControl) soundEffect.getControl(FloatControl.Type.MASTER_GAIN);   
	    	
	    	float controlValue = 20f * (float) Math.log10(v); // siccome il suono è in decibel, bisogna convertirlo in lineare
	    	
	    	if(controlValue > -80)		//per controllo, sennò viene un numero troppo basso
	    		gainControl.setValue(controlValue);	
	    	
	    	if(v < 0.015f)
	    		gainControl.setValue(gainControl.getMinimum());
	    }
	}
	
	// metodo che funziona bene
	public void setMusicVolume(float v) {
	    if (v > 0f && v < 1f) {
			this.musicVolume = v;
	    	
	    	FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);   
	    	
	    	float controlValue = 20f * (float) Math.log10(v); // siccome il suono è in decibel, bisogna convertirlo in lineare
	    	
	    	if(controlValue > -80)		//per controllo, sennò viene un numero troppo basso
	    		gainControl.setValue(controlValue);	
	    	
	    	if(v < 0.015f)
	    		gainControl.setValue(gainControl.getMinimum());
	    }
	}

	public float getMusicVolume() {
		return musicVolume;
	}
	
	public float getSEVolume() {
		return seVolume;
	}
	
}
