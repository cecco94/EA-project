package view.playState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.main.Gamestate;

import model.mappa.Stanze;
import view.ViewUtils;
import view.main.GamePanel;

// classe che mostra a video le informazioni come il punteggio, la vita, le munizioni...
// si occupa inoltre di disegnare la transizione da una stanza all'altra
public class PlayUI {

	private Font fontDisplay = new Font("Arial", Font.PLAIN, (int)(20*GamePanel.SCALE));
	private BufferedImage effettoBuio;
	private boolean buio = true;
	
	private PlayStateView play;
	private int counter = 0;
	private int counterScritta = 0;
	private boolean showMessage;

	private final float transitionDuration = 240; //120 fps quindi sono 3 secondi
	private float opacity;
	
	private float volume;
	private float volumeBeforeTransition;
	private boolean volumeSaved = false;
	
	private String messaggio = "";
	
	public PlayUI(PlayStateView p) {
		play = p;
		
		try {
			effettoBuio = ImageIO.read(getClass().getResourceAsStream("/effettoBuioFinale.png"));
			effettoBuio = ViewUtils.scaleImage(effettoBuio, effettoBuio.getWidth()*GamePanel.SCALE, effettoBuio.getHeight()*GamePanel.SCALE);
		} 
		catch (IOException e) {	
			e.printStackTrace();
		}
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
	
	public void draw(Graphics2D g2) {
		//disegna vita, punteggio, munizioni
		disegnaMessaggio(g2);
		disegnaBuio(g2);
	}
	
	private void disegnaBuio(Graphics2D g2) {
		if (Stanze.stanzaAttuale == Stanze.DORMITORIO && buio)
			g2.drawImage(effettoBuio, 0, 0, null);
	}

	private void disegnaMessaggio(Graphics2D g2) {
		if(showMessage) {
			
			counterScritta++;
			if(counterScritta < 360) {
				
				g2.setFont(fontDisplay);
				int x = ViewUtils.getXforCenterText(messaggio, g2);
				g2.setColor(Color.red);
				g2.drawString(messaggio, x, GamePanel.GAME_HEIGHT/2 + (int)(50*GamePanel.SCALE));
				g2.setColor(Color.black);	
			}
			
			else {
				counterScritta = 0;
				showMessage = false;
			}
		}
		
	}

	public void setScritta(String s) {
		messaggio = s;
	}
	
	public void setShowMessage(boolean showMessage) {
		this.showMessage = showMessage;
	}
	
	public void setBuio(boolean b) {
		buio = b;
	}
	
	
}
