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
	private String messaggio = "";
	
	private BufferedImage effettoBuio, noteIcon, cfuIcon;
	private BufferedImage[] vita;
	private String dataToShow = "";	
	private int yPosPlayerData = (int)(5*GamePanel.SCALE);
	private int xPosPlayerData = (int)(20*GamePanel.SCALE);
	
	private boolean buio = true;
	
	private PlayStateView play;
	private int counter = 0;
	private int counterScritta = 0;
	private boolean showMessage;

	private final float transitionDuration = 240; //120 fps quindi sono 2 secondi
	private float opacity;
	
	private float volume;
	private float volumeBeforeTransition;
	private boolean volumeSaved = false;
	
	
	public PlayUI(PlayStateView p) {
		play = p;
		loadImages();
		
	}
	
	private void loadImages() {
		try {
			effettoBuio = ImageIO.read(getClass().getResourceAsStream("/ui/effettoBuioFinale.png"));
			effettoBuio = ViewUtils.scaleImage(effettoBuio, effettoBuio.getWidth()*GamePanel.SCALE, effettoBuio.getHeight()*GamePanel.SCALE);
			
			cfuIcon = ImageIO.read(getClass().getResourceAsStream("/ui/punteggioPiccolo.png"));
			cfuIcon = ViewUtils.scaleImage(cfuIcon, cfuIcon.getWidth()*GamePanel.SCALE, cfuIcon.getHeight()*GamePanel.SCALE);

			vita = new BufferedImage[4];
			BufferedImage temp = ImageIO.read(getClass().getResourceAsStream("/ui/vitaPiccola.png"));
			
			for(int i = 0; i < 4; i++) 
				vita[i] = temp.getSubimage(45*i, 0, 45, 32);
			
			for(int i = 0; i < 4; i++)
				vita[i] = ViewUtils.scaleImage(vita[i], vita[i].getWidth()*GamePanel.SCALE, vita[i].getHeight()*GamePanel.SCALE);

			noteIcon = ImageIO.read(getClass().getResourceAsStream("/ui/appuntiPiccoli.png"));
			noteIcon = ViewUtils.scaleImage(noteIcon, noteIcon.getWidth()*GamePanel.SCALE, noteIcon.getHeight()*GamePanel.SCALE);

		} 
		catch (IOException e) {	
			e.printStackTrace();
		}
		
	}

	//per due secondi diventa tutto sfocato e la musica diminuisce
	public void drawTransition(Graphics2D g2) {
		counter++;
		saveOldVolume();
		if (counter < transitionDuration) {
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
		
		drawPlayerData(g2);
		disegnaBuio(g2);
		disegnaMessaggio(g2);
	}
	
	private void drawPlayerData(Graphics2D g2) {
		drawBackGround(g2);
		drawLife(g2, dataToShow);
		drawNotes(g2, dataToShow);	
		drawPoints(g2, dataToShow);
		//resetta il valore della x
		xPosPlayerData = (int)(20*GamePanel.SCALE);
	}

	private void drawBackGround(Graphics2D g2) {
		g2.getColor().getAlpha();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g2.setColor(Color.yellow);
		g2.fillRoundRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT/12, 40, 40);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g2.setColor(Color.black);
		
	}

	private void drawLife(Graphics2D g2, String s) {
		int life = play.getView().getController().getPlay().getPlayer().getLife();
		s = life + " %";

		if(life > 75)
			g2.drawImage(vita[0], xPosPlayerData, yPosPlayerData, null);
		else if(life <=75 && life > 50)
			g2.drawImage(vita[1], xPosPlayerData, yPosPlayerData, null);
		else if(life <=50 && life > 25)
			g2.drawImage(vita[2], xPosPlayerData, yPosPlayerData, null);
		else if(life <= 25)
			g2.drawImage(vita[3], xPosPlayerData, yPosPlayerData, null);
		
		g2.setFont(fontDisplay);
		xPosPlayerData += vita[0].getWidth() + (int)(10*GamePanel.SCALE);
		g2.drawString(s, xPosPlayerData, yPosPlayerData + (int)(25*GamePanel.SCALE));
		
	}
	
	private void drawPoints(Graphics2D g2, String s) {
		int cfu = play.getView().getController().getPlay().getPlayer().getCfu();
		s = "" + cfu;

		xPosPlayerData = GamePanel.GAME_WIDTH - cfuIcon.getWidth() - (int)(60*GamePanel.SCALE);
		g2.drawImage(cfuIcon, xPosPlayerData, yPosPlayerData, null);
		
		xPosPlayerData += cfuIcon.getWidth() + (int)(10*GamePanel.SCALE);
		g2.drawString(s, xPosPlayerData, yPosPlayerData + (int)(25*GamePanel.SCALE));
	}

	private void drawNotes(Graphics2D g2, String s) {
		int notes = play.getView().getController().getPlay().getPlayer().getNotes();
		s = "" + notes;

		xPosPlayerData = ViewUtils.getCenteredXPos(noteIcon.getWidth());
		g2.drawImage(noteIcon, xPosPlayerData, yPosPlayerData, null);
		
		xPosPlayerData += noteIcon.getWidth() + (int)(10*GamePanel.SCALE);
		g2.drawString(s, xPosPlayerData, yPosPlayerData + (int)(25*GamePanel.SCALE));
		
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
				int y = GamePanel.GAME_HEIGHT/2 + (int)(20*GamePanel.SCALE);
				int width = ViewUtils.getStringLenght(messaggio, g2);
				int height = ViewUtils.getStringHeight(messaggio, g2);
				
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
				g2.setColor(Color.yellow);
				g2.fillRoundRect(x, y, width + (int)(3*GamePanel.SCALE), height + (int)(4*GamePanel.SCALE), 30, 30);
				
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

				g2.setColor(Color.red);
				g2.drawString(messaggio, x, y + height);
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
