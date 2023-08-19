package view.playState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import controller.Gamestate;
import controller.playState.Stanze;
import view.main.GamePanel;

public class PlayUI {

	private PlayStateView play;
	private float counter = 0;	
	private final float transitionDuration = 240; //120 fps quindi sono 3 secondi
	private float opacity;
	private float volume;
	private float volumeBeforeTransition;
	private boolean saved = false;
	
	public PlayUI(PlayStateView p) {
		play = p;
	}
	
	public void drawTransition(Graphics2D g2) {
		counter++;
		saveOldVolume();
		if (counter < 240) {
			opacity = counter/transitionDuration;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			
			g2.setColor(Color.black);
			g2.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			
			volume = play.getView().getMusicVolume() - counter/transitionDuration + 0.01f;
			play.getView().setMusicVolume(volume);		
		}
		else {
			// riprendi il gioco usando i valori salvati nel player
			counter = 0;
			play.getView().getController().getPlay().resumeGameAfterTransition();
			
			play.getView().changeGameState(Gamestate.PLAYING);
			
			play.getView().stopMusic();
			play.getView().playMusic(Stanze.stanzaAttuale.indiceMusica);
			play.getView().setMusicVolume(volumeBeforeTransition);
			
		}
	}
	
	private void saveOldVolume() {
		if (!saved) {
			volumeBeforeTransition = play.getView().getMusicVolume();
			saved = true;
		}
	}
}
