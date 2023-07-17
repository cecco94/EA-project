package view.selectAvatar;

import static view.main.GamePanel.GAME_HEIGHT;
import static view.main.GamePanel.GAME_WIDTH;
import static view.main.GamePanel.SCALE;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import controller.Gamestate;
import view.ViewUtils;
import view.main.IView;
import view.menuIniziale.InitialMenuButton;
import view.menuIniziale.Menu;
import view.menuIniziale.MenuButton;

public class AvatarMenu extends Menu{
	private IView view;
	private int scrittaAvatarX, scrittaAvatarY;
	private BufferedImage scrittaAvatar;
	private final int RAGAZZO = 0, RAGAZZA = 1, INDIETRO = 2;
	private int buttonIndex = RAGAZZO;	
	private String[] caratteristichePersonaggi;
	
	public AvatarMenu(IView v) {
		view = v;
		loadAvatarChoiceText();
		loadButtons();
		loadCharacterSkills();
	}

	private void loadAvatarChoiceText() {
		try {
			scrittaAvatar = ImageIO.read(getClass().getResourceAsStream("/playerSprites/scritta1.png"));
			scrittaAvatar = ViewUtils.scaleImage(scrittaAvatar, scrittaAvatar.getWidth()*SCALE/1.5f, scrittaAvatar.getHeight()*SCALE/1.5f);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		scrittaAvatarX = ViewUtils.getCenteredXPos(scrittaAvatar.getWidth());
		scrittaAvatarY = (int)(30*SCALE);
	}
	
	public void loadButtons() {
		String percorsoRagazzo = "/playerSprites/boyAvatar1.png";
		String percorsoRagazza = "/playerSprites/girl.png";
		String[] percorsoIndietro = {"/menuiniziale/indietro1.png", "/menuiniziale/indietro2.png", "/menuiniziale/indietro3.png"};
		int y = GAME_HEIGHT/6;
		int x = GAME_WIDTH/2 + GAME_WIDTH/15;
		buttons = new MenuButton[3];
		buttons[0] = new AvatarButton(percorsoRagazza, x, y, (int)(200*SCALE), (int)(300*SCALE), view);
		x = GAME_WIDTH/2 - GAME_WIDTH/15 - (int)(200*SCALE);
		buttons[1] = new AvatarButton(percorsoRagazzo, x, y, (int)(200*SCALE), (int)(300*SCALE), view);
		y = GAME_HEIGHT - (int)(50*SCALE);
		buttons[2] = new InitialMenuButton(percorsoIndietro, y, (int)(100*SCALE), (int)(15*SCALE), Gamestate.MAIN_MENU, view);	
	}
	
	private void loadCharacterSkills() {
		caratteristichePersonaggi = new String[2];
		caratteristichePersonaggi[0] = "Mario, viene dallo scientifico, crede di sapare già tutto";
		caratteristichePersonaggi[1] = "Giulia, viene dal classico, la notte piange sempre";
	}

	public void drawAvatarMenu(Graphics2D g2) {
		drawBackground(g2);
		drawAvatarChoiceText(g2);
		drawCharacterSkills(g2);
		drawButtons(g2);
	}



	private void drawBackground(Graphics2D g2) {
		view.getMenu().drawBackground(g2);
	}
	
	private void drawAvatarChoiceText(Graphics2D g2) {
		g2.drawImage(scrittaAvatar, scrittaAvatarX, scrittaAvatarY, null);
	}
	
	private void drawCharacterSkills(Graphics2D g2) {
		if(buttons[1].getMouseOver() == true) {
			g2.setColor(Color.red);
			int x = ViewUtils.getXforCenterText(caratteristichePersonaggi[0], g2);
			g2.drawString(caratteristichePersonaggi[0], x, (int)(buttons[1].getBounds().getHeight()+buttons[1].getBounds().getY()+10*SCALE));
		}
		else if (buttons[0].getMouseOver() == true) {
			g2.setColor(Color.red);
			int x = ViewUtils.getXforCenterText(caratteristichePersonaggi[1], g2);
			g2.drawString(caratteristichePersonaggi[1], x, (int)(buttons[0].getBounds().getHeight()+buttons[0].getBounds().getY()+ 10*SCALE));
		}
	}
	
	
	public void keyReleased(int tasto) {
		
		switch(buttonIndex) {
		case RAGAZZO:
			comportamentoRagazzo(tasto);
			break;		
		case RAGAZZA:
			comportamentoRagazza(tasto);
			break;		
		case INDIETRO:
			comportamentoIndietro(tasto);
			break;	
		}	
	}

	private void comportamentoRagazzo(int tasto) {
		if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_LEFT) 
			view.setCursorPosition((int)(GAME_WIDTH/2 - GAME_WIDTH/4), GAME_HEIGHT/2);

		if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
			view.setCursorPosition(GAME_WIDTH/2, GAME_HEIGHT - (int)(50*SCALE));
			buttonIndex = INDIETRO;
			}
		if(tasto == KeyEvent.VK_D || tasto == KeyEvent.VK_RIGHT) {
			view.setCursorPosition((int)(GAME_WIDTH/2 + GAME_WIDTH/4), GAME_HEIGHT/2);
			buttonIndex = RAGAZZA;
			}
		if(tasto == KeyEvent.VK_ENTER) {
			view.stopMusic();
			resetButtons();
			view.changeGameState(buttons[1].getButtonState());
			}		
	}

	private void comportamentoRagazza(int tasto) {
		if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
			view.setCursorPosition(GAME_WIDTH/2, GAME_HEIGHT - (int)(50*SCALE));
			buttonIndex = INDIETRO;
			}
		if(tasto == KeyEvent.VK_A || tasto == KeyEvent.VK_LEFT) {
			view.setCursorPosition((int)(GAME_WIDTH/2 - GAME_WIDTH/4), GAME_HEIGHT/2);
			buttonIndex = RAGAZZO;
			}
		if(tasto == KeyEvent.VK_ENTER) {
			resetButtons();
			view.changeGameState(buttons[RAGAZZA].getButtonState());
			view.stopMusic();
			}		
	}

	private void comportamentoIndietro(int tasto) {
		if(tasto == KeyEvent.VK_A || tasto == KeyEvent.VK_LEFT) {
			view.setCursorPosition((int)(GAME_WIDTH/2 - GAME_WIDTH/4), GAME_HEIGHT/2);
			buttonIndex = RAGAZZO;
			}
		if(tasto == KeyEvent.VK_D || tasto == KeyEvent.VK_RIGHT) {
			view.setCursorPosition((int)(GAME_WIDTH/2 + GAME_WIDTH/4), GAME_HEIGHT/2);
			buttonIndex = RAGAZZA;
			}
		if(tasto == KeyEvent.VK_ENTER) {
			resetButtons();
			view.changeGameState(buttons[INDIETRO].getButtonState());
		}		
	}
	
}

