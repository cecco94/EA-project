package view.playState.mappe;

import view.main.IView;
import view.sound.SoundManager;

public class Dormitorio {

	private IView view;
//	private PlayState play;
	
	public Dormitorio(IView v) {
		view = v;
	}
	
	
	public void playMusic() {
		view.playMusic(SoundManager.PLAY_MUSIC);
	}
	
	public void draw() {
		
	}
}
