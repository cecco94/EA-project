package view;

import static view.main.GamePanel.GAME_HEIGHT;
import static view.main.GamePanel.GAME_WIDTH;
import static view.main.GamePanel.SCALE;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.main.Gamestate;
import view.main.GamePanel;
import view.menu.AbstractMenu;
import view.menu.AbstractMenuButton;
import view.menu.mainMenu.InitialMenuButton;
import view.menu.optionMenu.SoundBar;

public class PauseScreen extends AbstractMenu {

	String percorsoHome = "/menuIniziale/bottoneHome.png";
	String percorsoRiprendi = "/menuIniziale/bottoneRiprendi.png";
	String percorsoScritta = "/menuIniziale/pausaCaffe.png";

	private BufferedImage titolo;
	private BufferedImage volumeMusica, se;
	
	private SoundBar musica, effetti;
	private PauseScreenButton home, riprendi;
	
	private int titleHeight = GAME_HEIGHT/4;
	private int musicHeight, seHeight;
	private int centeredXTitle, centeredXmusic, centeredXse;
	public final int maxBarWidth = GAME_WIDTH/4;
	private int soundbarsX = GAME_WIDTH/2 - maxBarWidth/2;
	
	private IView view;
	
	public PauseScreen(IView v) {
		view = v;
		
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
		
		centeredXTitle = ViewUtils.getCenteredXPos(titolo.getWidth());
		
		musicHeight = titleHeight + titolo.getHeight() + (int)(20*GamePanel.SCALE);
		centeredXmusic = ViewUtils.getCenteredXPos(volumeMusica.getWidth());
		
		Rectangle r1 = new Rectangle(soundbarsX, musicHeight + volumeMusica.getHeight() + (int)(20*GamePanel.SCALE), maxBarWidth, (int)(10*SCALE));
		musica = new SoundBar(this, r1, v, SoundBar.MUSIC);
		
		seHeight = r1.y + r1.height + (int)(20*GamePanel.SCALE);
		centeredXse = ViewUtils.getCenteredXPos(se.getWidth());
		
		Rectangle r2 = new Rectangle(soundbarsX, seHeight + se.getHeight() + (int)(20*GamePanel.SCALE), maxBarWidth, (int)(10*SCALE));
		effetti = new SoundBar(this, r2, v, SoundBar.SE);
		
		home = new PauseScreenButton(v, percorsoHome, soundbarsX + (int)(30*GamePanel.SCALE), r2.y + r2.height + (int)(20*GamePanel.SCALE), Gamestate.TRANSITION_STATE, true);
		riprendi = new PauseScreenButton(v, percorsoRiprendi, soundbarsX + (int)(90*GamePanel.SCALE) , r2.y + r2.height + (int)(20*GamePanel.SCALE), Gamestate.PLAYING, false);
		
		buttons = new AbstractMenuButton[4];
		buttons[0] = musica;
		buttons[1] = effetti;
		buttons[2] = home;
		buttons[3] = riprendi;
	}
	
	
	public void draw(Graphics2D g2) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		
		g2.drawImage(titolo, centeredXTitle, titleHeight, null);
		g2.drawImage(volumeMusica, centeredXmusic, musicHeight, null);
		g2.drawImage(se, centeredXse, seHeight, null);
		
		drawButtons(g2);
	}

}
