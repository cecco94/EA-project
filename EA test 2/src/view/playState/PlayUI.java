package view.playState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import controller.main.Gamestate;
import model.mappa.Stanze;
import view.main.GamePanel;

// classe che mostra a video le informazioni come il punteggio, la vita, le munizioni...
// si occupa inoltre di disegnare la transizione da una stanza all'altra
public class PlayUI {

	private PlayStateView play;
	private float counter = 0;	
	private final float transitionDuration = 240; //120 fps quindi sono 3 secondi
	private float opacity;
	
	private float volume;
	private float volumeBeforeTransition;
	private boolean volumeSaved = false;
	
	public PlayUI(PlayStateView p) {
		play = p;
	}
	
	//per due secondi diventa tutto sfocato e la musica diminuisce
	public void drawTransition(Graphics2D g2) {
		counter++;
		saveOldVolume();
		if (counter < 240) {
			opacity = counter/transitionDuration;
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			g2.setColor(Color.black);
			g2.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			
			volume = play.getView().getMusicVolume() - opacity + 0.01f;
			play.getView().setMusicVolume(volume);		
		}
		else {
			// riprendi il gioco usando i valori salvati nel model
			counter = 0;
			
			play.getView().getController().getPlay().resumeGameAfterTransition();
			play.getView().changeGameState(Gamestate.PLAYING);
			
			play.getView().stopMusic();
			play.getView().playMusic(Stanze.stanzaAttuale.indiceMusica);
			play.getView().setMusicVolume(volumeBeforeTransition);
			
		}
	}
	
	private void saveOldVolume() {
		if (!volumeSaved) {
			volumeBeforeTransition = play.getView().getMusicVolume();
			volumeSaved = true;
		}
	}
}
