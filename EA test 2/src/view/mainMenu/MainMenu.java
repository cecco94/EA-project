package view.mainMenu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Gamestate;
import view.GamePanel;
import view.IView;
import view.ViewUtils;
import view.selectAvatar.AvatarButton;

public class MainMenu {
	private int indexBackground, counterbackground;
	private MenuButton[] bottoni;
	private BufferedImage[] sfondoAnimato;
	private BufferedImage titolo;
	private int titoloX, titoloY;
	private int altezzaBottoni = 370, distanzaBottoni = 55;
	private IView view;
	
	public MainMenu(IView v) {
		view = v;
		loadBackgroundImages();
		loadTitle();
		loadButtons();
	}
	
	private void loadButtons() {
		String[] percorsoIscriviti = {"/menuiniziale/iscriviti1.png", "/menuiniziale/iscriviti2.png", "/menuiniziale/iscriviti3.png"};
		String[] percorsoRiprendi = {"/menuiniziale/riprendi1.png", "/menuiniziale/riprendi2.png", "/menuiniziale/riprendi3.png"};
		String[] percorsoOpzioni = {"/menuiniziale/opzioni1.png", "/menuiniziale/opzioni2.png", "/menuiniziale/opzioni3.png"};	
		String[] percorsoQuit = {"/menuiniziale/rinuncia1.png", "/menuiniziale/rinuncia2.png", "/menuiniziale/rinuncia3.png"};
		bottoni = new MenuButton[4];
		bottoni[0] = new MenuButton(percorsoIscriviti, altezzaBottoni, 150, 20, Gamestate.SELECT_AVATAR);
		bottoni[1] = new MenuButton(percorsoRiprendi, altezzaBottoni + distanzaBottoni, 400, 20, Gamestate.LOAD_GAME);
		bottoni[2] = new MenuButton(percorsoOpzioni, altezzaBottoni + distanzaBottoni*2, 350, 20, Gamestate.OPTIONS);
		bottoni[3] = new MenuButton(percorsoQuit, altezzaBottoni + distanzaBottoni*3, 350, 18, Gamestate.QUIT);
		
	}

	private void loadTitle() {
		try {
			titolo = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/scritta_titolo.png"));	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		titoloX = ViewUtils.getCenteredXPos(titolo.getWidth());
		titoloY = GamePanel.GAME_HEIGHT/4;
	}

	private void loadBackgroundImages() {
		sfondoAnimato = new BufferedImage[24];
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-0.png"));
			sfondoAnimato[0] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-1.png"));
			sfondoAnimato[1] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-2.png"));
			sfondoAnimato[2] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-3.png"));
			sfondoAnimato[3] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-4.png"));
			sfondoAnimato[4] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-5.png"));
			sfondoAnimato[5] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-6.png"));
			sfondoAnimato[6] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-7.png"));
			sfondoAnimato[7] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-8.png"));
			sfondoAnimato[8] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-9.png"));
			sfondoAnimato[9] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-10.png"));
			sfondoAnimato[10] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-11.png"));
			sfondoAnimato[11] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-12.png"));
			sfondoAnimato[12] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-13.png"));
			sfondoAnimato[13] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-14.png"));
			sfondoAnimato[14] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-15.png"));
			sfondoAnimato[15] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-16.png"));
			sfondoAnimato[16] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-17.png"));
			sfondoAnimato[17] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-18.png"));
			sfondoAnimato[18] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-19.png"));
			sfondoAnimato[19] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-20.png"));
			sfondoAnimato[20] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-21.png"));
			sfondoAnimato[21] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-22.png"));
			sfondoAnimato[22] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondoMenuIniziale-23.png"));
			sfondoAnimato[23] = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void drawYourself(Graphics2D g2) {
		drawBackground(g2);
		drawTitle(g2);
		drawCredits(g2);
		drawButtons(g2);	
	}

	private void drawCredits(Graphics2D g2) {
		g2.setColor(Color.red);
		String s = "© GNgame Production";
		g2.drawString(s, GamePanel.GAME_WIDTH/2 - 50, GamePanel.GAME_HEIGHT - 5);	
	}

	private void drawButtons(Graphics2D g2) {
		for (MenuButton mb : bottoni)
			mb.draw(g2);	
		}

	private void drawTitle(Graphics2D g2) {
		g2.drawImage(titolo, titoloX, titoloY, null);	
	}

	public void drawBackground(Graphics2D g2) {
		counterbackground++;
		g2.drawImage(sfondoAnimato[indexBackground], 0, 0, null);
		if(counterbackground == 10) {
			indexBackground++;
			counterbackground = 0;
		}
		if(indexBackground == 23)
			indexBackground = 0;	
	}
	
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : bottoni) {
			if (mb.checkIfMouseIsIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : bottoni) {
			if (mb.checkIfMouseIsIn(e, mb) && mb.isMousePressed()) {
				view.changeGameState(mb.getButtonState());
				//view.stopMusic();
				view.playSE();
				break;
			}
		}
		resetButtons();
	}

	private void resetButtons() {
		for (MenuButton mb : bottoni)
			mb.resetBooleans();
	}

	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb : bottoni)
			mb.setMouseOver(false);
		
		for (MenuButton mb : bottoni)
			if (mb.checkIfMouseIsIn(e, mb)) 
				mb.setMouseOver(true);		
	}

}
