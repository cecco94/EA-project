package view.menu.avatarSelection;

import static view.main.GamePanel.GAME_HEIGHT;
import static view.main.GamePanel.GAME_WIDTH;
import static view.main.GamePanel.SCALE;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import controller.main.Gamestate;

import view.IView;
import view.ViewUtils;
import view.menu.AbstractMenu;
import view.menu.AbstractMenuButton;
import view.menu.mainMenu.InitialMenuButton;
import view.playState.entityView.PlayerView;

//menu dove scegli l'avatar
public class AvatarMenu extends AbstractMenu{
	private IView view;
	private int titleAvatarX, titleAvatarY;
	private BufferedImage titleAvatar;
	private String[] characterSkills;
	
	//servono per usare i tasti per muoversi nel menu
	private final int RAGAZZO = 2, RAGAZZA = 1, INDIETRO = 0;
	private int buttonIndex = RAGAZZO;	

	public AvatarMenu(IView v) {
		view = v;
		loadAvatarChoiceText();
		loadButtons();
		loadCharacterSkills();
	}

	// è la scritta "scegli il tuo avatar"
	private void loadAvatarChoiceText() {
		try {
			titleAvatar = ImageIO.read(getClass().getResourceAsStream("/avatarSelection/scritta1.png"));
			titleAvatar = ViewUtils.scaleImage(titleAvatar, titleAvatar.getWidth()*SCALE/1.5f, titleAvatar.getHeight()*SCALE/1.5f);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		titleAvatarX = ViewUtils.getCenteredXPos(titleAvatar.getWidth());
		titleAvatarY = (int)(30*SCALE);
	}
	
	public void loadButtons() {
		int x = GAME_WIDTH/2 + GAME_WIDTH/15;
		int y = GAME_HEIGHT - (int)(50*SCALE); 	
		buttons = new AbstractMenuButton[3];
		
		String[] goBackButtonImagePath = {"/menuiniziale/indietro1.png", "/menuiniziale/indietro2.png", "/menuiniziale/indietro3.png"};
		buttons[0] = new InitialMenuButton(goBackButtonImagePath, y, (int)(100*SCALE), (int)(15*SCALE), Gamestate.MAIN_MENU, view);
		
		y = GAME_HEIGHT/3;
		Rectangle dimensions = new Rectangle(x, y, (int)(100*SCALE), (int)(150*SCALE));
		buttons[1] = new AvatarButton(PlayerView.RAGAZZA, dimensions, view);
		
		dimensions.x = GAME_WIDTH/2 - GAME_WIDTH/15 - (int)(100*SCALE);
		buttons[2] = new AvatarButton(PlayerView.RAGAZZO, dimensions, view);	
	}
	
	//sarebbero le scritte che appaiono se passi sopra a un personaggio
	private void loadCharacterSkills() {
		characterSkills = new String[2];
		characterSkills[0] = "Sara, viene dal classico, ha più concentrazione";
		characterSkills[1] = "Mario, viene dallo scientifico, ha più appunti";
	}

	public void drawAvatarMenu(Graphics2D g2) {
		drawBackground(g2);
		drawAvatarChoiceText(g2);
		drawCharacterSkills(g2);
		drawButtons(g2);
	}

	// chiede al menu principale di disegnare il background, senza dover ricaricare le immagini dello sfondo
	private void drawBackground(Graphics2D g2) {
		view.getMenu().drawBackground(g2);
	}
	
	private void drawAvatarChoiceText(Graphics2D g2) {
		g2.drawImage(titleAvatar, titleAvatarX, titleAvatarY, null);
	}
	
	private void drawCharacterSkills(Graphics2D g2) {
		if(buttons[RAGAZZO].getMouseOver() == true) {
			g2.setColor(Color.red);
			int x = ViewUtils.getXforCenterText(characterSkills[RAGAZZO -1], g2);
			int y = (int)(buttons[RAGAZZO].getBounds().getHeight() + buttons[RAGAZZO].getBounds().getY() + 30*SCALE);
			g2.drawString(characterSkills[RAGAZZO -1], x, y);
		}
		else if (buttons[RAGAZZA].getMouseOver() == true) {
			g2.setColor(Color.red);
			int x = ViewUtils.getXforCenterText(characterSkills[RAGAZZA -1], g2);
			int y = (int)(buttons[RAGAZZA].getBounds().getHeight() + buttons[RAGAZZA].getBounds().getY() + 30*SCALE);
			g2.drawString(characterSkills[RAGAZZA -1], x, y);
		}
	}
	
	public void keyReleased(int tasto) {
		
		switch(buttonIndex) {
		case RAGAZZO:
			keyboardInputsBoyButton(tasto);
			break;		
		case RAGAZZA:
			keyboardInputsGirlButton(tasto);
			break;		
		case INDIETRO:
			keyboardInputsBackButton(tasto);
			break;	
		}	
	}

	//metedo che serve per muoversi nel menù con la tastiera (WASD-frecce)
	private void keyboardInputsBoyButton(int tasto) {
		if(tasto == KeyEvent.VK_A || tasto == KeyEvent.VK_LEFT) 
			view.setCursorPosition(buttons[RAGAZZO].getBounds().x, buttons[RAGAZZO].getBounds().y);

		if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[INDIETRO].getBounds().x, buttons[INDIETRO].getBounds().y);
			buttonIndex = INDIETRO;
			}
		if(tasto == KeyEvent.VK_D || tasto == KeyEvent.VK_RIGHT) {
			view.setCursorPosition(buttons[RAGAZZA].getBounds().x, buttons[RAGAZZA].getBounds().y);
			buttonIndex = RAGAZZA;
			}
		if(tasto == KeyEvent.VK_ENTER) {
			resetButtons();
			buttons[RAGAZZO].reactToMouse(null);
			}		
	}

	private void keyboardInputsGirlButton(int tasto) {
		if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[INDIETRO].getBounds().x, buttons[INDIETRO].getBounds().y);
			buttonIndex = INDIETRO;
			}
		if(tasto == KeyEvent.VK_A || tasto == KeyEvent.VK_LEFT) {
			view.setCursorPosition(buttons[RAGAZZO].getBounds().x, buttons[RAGAZZO].getBounds().y);
			buttonIndex = RAGAZZO;
			}
		if(tasto == KeyEvent.VK_ENTER) {
			resetButtons();
			buttons[RAGAZZA].reactToMouse(null);
			}		
	}

	private void keyboardInputsBackButton(int tasto) {
		if(tasto == KeyEvent.VK_A || tasto == KeyEvent.VK_LEFT) {
			view.setCursorPosition(buttons[RAGAZZO].getBounds().x, buttons[RAGAZZO].getBounds().y);
			buttonIndex = RAGAZZO;
			}
		if(tasto == KeyEvent.VK_D || tasto == KeyEvent.VK_RIGHT) {
			view.setCursorPosition(buttons[RAGAZZA].getBounds().x, buttons[RAGAZZA].getBounds().y);
			buttonIndex = RAGAZZA;
			}
		if(tasto == KeyEvent.VK_ENTER) {
			resetButtons();
			view.changeGameState(buttons[INDIETRO].getButtonState());
		}		
	}
	
}

