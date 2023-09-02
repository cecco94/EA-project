package view.menu.mainMenu;

import static view.main.GamePanel.GAME_HEIGHT;
import static view.main.GamePanel.GAME_WIDTH;
import static view.main.GamePanel.SCALE;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import controller.main.Gamestate;

import view.IView;
import view.ViewUtils;
import view.menu.AbstractMenu;

//il menu che si vede all'inizio del gioco, con play, riprendi, opzioni...
public class MainMenu extends AbstractMenu {	
	private IView view;
	
	private int indexBackground, counterbackground, counterTitle;
	private int timer = 120*10;
	
	private String credits = "©GNgame Production";
	private BufferedImage[] animatedBackground;
	private BufferedImage title;
	private int titleX, titleY;	
	
	// questi servono per muoversi con i tsti direzionali. index indica il tasto selezionato
	// il tasto cioè dove si trova il cursore
	private final int PLAY = 0, LOAD = 1, OPTION = 2, EXIT = 3;
	private int buttonIndex = PLAY;
	
	public MainMenu(IView v) {
		view = v;
		loadBackgroundImages();
		loadTitle();
		loadButtons();
	}

	//sono le immagini che insieme formano l'animazione sullo sfondo
	private void loadBackgroundImages() {
		animatedBackground = new BufferedImage[24];
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-0.png"));
			animatedBackground[0] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-1.png"));
			animatedBackground[1] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-2.png"));
			animatedBackground[2] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-3.png"));
			animatedBackground[3] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-4.png"));
			animatedBackground[4] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-5.png"));
			animatedBackground[5] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-6.png"));
			animatedBackground[6] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-7.png"));
			animatedBackground[7] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-8.png"));
			animatedBackground[8] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-9.png"));
			animatedBackground[9] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-10.png"));
			animatedBackground[10] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-11.png"));
			animatedBackground[11] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-12.png"));
			animatedBackground[12] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-13.png"));
			animatedBackground[13] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-14.png"));
			animatedBackground[14] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-15.png"));
			animatedBackground[15] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-16.png"));
			animatedBackground[16] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-17.png"));
			animatedBackground[17] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-18.png"));
			animatedBackground[18] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-19.png"));
			animatedBackground[19] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-20.png"));
			animatedBackground[20] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-21.png"));
			animatedBackground[21] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-22.png"));
			animatedBackground[22] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
			temp = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/sfondo/sfondoMenuIniziale-23.png"));
			animatedBackground[23] = ViewUtils.scaleImage(temp, GAME_WIDTH, GAME_HEIGHT);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void loadTitle() {
		try {
			title = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/scritta_titolo.png"));	
			title = ViewUtils.scaleImage(title, title.getWidth() * SCALE, title.getHeight() * SCALE);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		titleX = ViewUtils.getCenteredXPos(title.getWidth());
		titleY = GAME_HEIGHT/4;
	}

	private void loadButtons() {
		buttons = new InitialMenuButton[4];
		String[] percorsoIscriviti = {"/menuiniziale/iscriviti1.png", "/menuiniziale/iscriviti2.png", "/menuiniziale/iscriviti3.png"};
		String[] percorsoRiprendi = {"/menuiniziale/riprendi1.png", "/menuiniziale/riprendi2.png", "/menuiniziale/riprendi3.png"};
		String[] percorsoOpzioni = {"/menuiniziale/opzioni1.png", "/menuiniziale/opzioni2.png", "/menuiniziale/opzioni3.png"};	
		String[] percorsoQuit = {"/menuiniziale/rinuncia1.png", "/menuiniziale/rinuncia2.png", "/menuiniziale/rinuncia3.png"};
		int buttonHight = (int)(250 * SCALE);
		int buttonDistance = (int)(30 * SCALE);
		buttons[0] = new InitialMenuButton(percorsoIscriviti, buttonHight, (int)(110*SCALE), (int)(16*SCALE), Gamestate.SELECT_AVATAR, view);
		buttons[1] = new InitialMenuButton(percorsoRiprendi, buttonHight + buttonDistance, (int)(300*SCALE), (int)(17*SCALE), Gamestate.LOAD_GAME, view);
		buttons[2] = new InitialMenuButton(percorsoOpzioni, buttonHight + buttonDistance*2, (int)(270*SCALE), (int)(16*SCALE), Gamestate.OPTIONS, view);
		buttons[3] = new InitialMenuButton(percorsoQuit, buttonHight + buttonDistance*3, (int)(250*SCALE), (int)(14*SCALE), Gamestate.QUIT, view);
	}	

	public void drawYourself(Graphics2D g2) {
		drawBackground(g2);
		drawCredits(g2);
		drawButtons(g2);
		drawTitle(g2);
	}

	//ogni tot centesimi di secondo, cambia l'immagine. in questo modo sembra che lo schermo sia in movimento
	public void drawBackground(Graphics2D g2) {
		counterbackground++;
		g2.drawImage(animatedBackground[indexBackground], 0, 0, null);
		if(counterbackground == 10) {
			indexBackground++;
			counterbackground = 0;
		}
		if(indexBackground == 23)
			indexBackground = 0;	
	}

	private void drawCredits(Graphics2D g2) {
		g2.setColor(Color.red);
		int x = ViewUtils.getXforCenterText(credits, g2);
		g2.drawString(credits, x, GAME_HEIGHT - (int)(2*SCALE));
	}
	
	// il titolo diventa piano piano meno trasparente, il valore alpha è quello che indica la trasparenza e varia tra 0 e 1
	private void drawTitle(Graphics2D g2) {
		counterTitle++;
		if(counterTitle < timer) {
			float alPhaValue = (float) counterTitle/(timer);	//quando il counter = timer il titolo è completamente visibile
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alPhaValue));	//setta il valore di trasparenza
		}
		g2.drawImage(title, titleX, titleY, null);
	}
	
	// in base a quale tasto è selezionato, cambia ciò che deve fare
	public void keyReleased(int key) { //key=tasto
		switch(buttonIndex) {
		case PLAY:
			keyboardInputsPlayButton(key);	
			break;
		case LOAD:
			keyboardInputsLoadButton(key);
			break;
		case OPTION:
			keyboardInputsOptionButton(key);
			break;	
		case EXIT:
			keyboardInputsExitButton(key);
			break;
		}
	}
	
	private void keyboardInputsPlayButton(int key) {
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
			view.setCursorPosition(buttons[PLAY].getBounds().x, buttons[PLAY].getBounds().y);
		
		else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[LOAD].getBounds().x, buttons[LOAD].getBounds().y);
			buttonIndex = LOAD;
		}
		else if(key == KeyEvent.VK_ENTER) {
			resetButtons();
			view.changeGameState(buttons[PLAY].getButtonState());
		}
	}
	
	private void keyboardInputsLoadButton(int key) {
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[OPTION].getBounds().x, buttons[OPTION].getBounds().y);
			buttonIndex = OPTION;
		}
		else if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			view.setCursorPosition(buttons[PLAY].getBounds().x, buttons[PLAY].getBounds().y);
			buttonIndex = PLAY;
		}
		else if(key == KeyEvent.VK_ENTER) {
			resetButtons();
			view.changeGameState(buttons[LOAD].getButtonState());
		}
	}

	private void keyboardInputsOptionButton(int key) {
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[EXIT].getBounds().x, buttons[EXIT].getBounds().y);
			buttonIndex = EXIT;
		}
		else if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			view.setCursorPosition(buttons[LOAD].getBounds().x, buttons[LOAD].getBounds().y);
			buttonIndex = LOAD;
			}
		else if(key == KeyEvent.VK_ENTER) {
			resetButtons();
			view.changeGameState(buttons[OPTION].getButtonState());
		}
	}

	private void keyboardInputsExitButton(int key) {
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			view.setCursorPosition(buttons[OPTION].getBounds().x, buttons[OPTION].getBounds().y);
			buttonIndex = OPTION;
			}
		else if(key == KeyEvent.VK_ENTER) {
			resetButtons();
			view.changeGameState(buttons[EXIT].getButtonState());	
		}
	}

	
	
}
