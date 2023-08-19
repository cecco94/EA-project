package view.playState.mappe;

import view.IView;
import view.sound.SoundManager;

public class Dormitorio {

	private IView view;
//	private PlayState play;
	
	public Dormitorio(IView v) {
		view = v;
	}
	
	
	public void playMusic() {
		view.playMusic(SoundManager.SALA_STUDIO);
	}
	
	public void draw() {
		
	}
}
