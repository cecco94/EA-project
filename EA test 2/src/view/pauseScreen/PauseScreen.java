package view.pauseScreen;

import static view.main.GamePanel.GAME_HEIGHT;
import static view.main.GamePanel.GAME_WIDTH;
import static view.main.GamePanel.SCALE;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.main.Gamestate;
import view.IView;
import view.ViewUtils;
import view.main.GamePanel;
import view.menu.AbstractMenu;
import view.menu.AbstractMenuButton;
import view.menu.optionMenu.SoundBar;

public class PauseScreen extends AbstractMenu {

	String percorsoHome = "/menuiniziale/bottoneHome.png";
	String percorsoRiprendi = "/menuiniziale/bottoneRiprendi.png";
	String percorsoScritta =  "/menuiniziale/pausa.png";

	private BufferedImage titolo;
	private BufferedImage volumeMusica, se;
	
	private SoundBar musica, effetti;
	private PauseScreenButton home, riprendi;
	
	private int titleHeight = GAME_HEIGHT/4;
	private int musicHeight, seHeight;
	private int centeredXTitle, centeredXmusic, centeredXse;
	public final int maxBarWidth = GAME_WIDTH/4, barHeight = (int)(10*SCALE);
	private int soundbarsX = GAME_WIDTH/2 - maxBarWidth/2;
	
	private IView view;
		
	public PauseScreen(IView v) {
		view = v;
		loadImages();
		centeredXTitle = ViewUtils.getCenteredXPos(titolo.getWidth());
		createSoundBars(v);
		createHomeButtons(v);
		
		buttons = new AbstractMenuButton[4];
		buttons[0] = musica;
		buttons[1] = effetti;
		buttons[2] = home;
		buttons[3] = riprendi;
	}

	private void createHomeButtons(IView v) {
		int homeY = effetti.getBounds().y + effetti.getBounds().height + (int)(20*GamePanel.SCALE);	
		home = new PauseScreenButton(v, percorsoHome, soundbarsX + (int)(30*GamePanel.SCALE), homeY, Gamestate.TRANSITION_STATE, true);
		
		int riprendiY = effetti.getBounds().y + effetti.getBounds().height + (int)(20*GamePanel.SCALE);
		riprendi = new PauseScreenButton(v, percorsoRiprendi, soundbarsX + (int)(90*GamePanel.SCALE) , riprendiY, Gamestate.PLAYING, false);
		
	}

	private void createSoundBars(IView v) {
		musicHeight = titleHeight + titolo.getHeight() + (int)(20*GamePanel.SCALE);
		centeredXmusic = ViewUtils.getCenteredXPos(volumeMusica.getWidth());
		
		Rectangle r1 = new Rectangle(soundbarsX, musicHeight + volumeMusica.getHeight() + (int)(20*GamePanel.SCALE), maxBarWidth, barHeight);
		musica = new SoundBar(this, r1, v, SoundBar.MUSIC);
		
		seHeight = r1.y + r1.height + (int)(20*GamePanel.SCALE);
		centeredXse = ViewUtils.getCenteredXPos(se.getWidth());
		
		Rectangle r2 = new Rectangle(soundbarsX, seHeight + se.getHeight() + (int)(20*GamePanel.SCALE), maxBarWidth, barHeight);
		effetti = new SoundBar(this, r2, v, SoundBar.SE);
		
	}

	private void loadImages() {
		try {
			titolo = ImageIO.read(getClass().getResourceAsStream(percorsoScritta));
			int widht = titolo.getWidth();
			int height = titolo.getHeight();
			titolo = ViewUtils.scaleImage(titolo, widht*GamePanel.SCALE/2, height*GamePanel.SCALE/2);
			
			volumeMusica = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/opzioni/volumemusica.png"));	
			volumeMusica = ViewUtils.scaleImage(volumeMusica, volumeMusica.getWidth()/4 * SCALE, volumeMusica.getHeight()/4 * SCALE);
			
			se = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/opzioni/volumeeffetti.png"));	
			se = ViewUtils.scaleImage(se, se.getWidth()/4 * SCALE, se.getHeight()/4 * SCALE);
		} 
		catch (IOException e) {	
			e.printStackTrace();
		}
		
	}

	public void draw(Graphics2D g2) {
		drawOpaqueRect(g2);
		drawScritte(g2);
		drawButtons(g2);
	}

	private void drawScritte(Graphics2D g2) {
		g2.drawImage(titolo, centeredXTitle, titleHeight, null);
		g2.drawImage(volumeMusica, centeredXmusic, musicHeight, null);
		g2.drawImage(se, centeredXse, seHeight, null);
		
	}

	private void drawOpaqueRect(Graphics2D g2) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));		
	}

	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_ESCAPE)
			view.changeGameState(Gamestate.PLAYING);		
	}

}
