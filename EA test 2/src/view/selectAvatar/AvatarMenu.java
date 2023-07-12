package view.selectAvatar;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import controller.Gamestate;
import static view.GamePanel.SCALE;
import static view.GamePanel.GAME_WIDTH;
import static view.GamePanel.GAME_HEIGHT;

import view.IView;
import view.ViewUtils;
import view.mainMenu.GeneralButton;

public class AvatarMenu {
	private IView view;
	private int scrittaAvatarX, scrittaAvatarY;
	private BufferedImage scrittaAvatar;
	private AvatarButton ragazzo, ragazza;
	private GeneralButton indietro;
	
	public AvatarMenu(IView v) {
		view = v;
		loadAvatarChoiceText();
		loadButtons();
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
		ragazza = new AvatarButton(percorsoRagazza, x, y, (int)(200*SCALE), (int)(300*SCALE));
		x = GAME_WIDTH/2 - GAME_WIDTH/15 - (int)(200*SCALE);
		ragazzo = new AvatarButton(percorsoRagazzo, x, y, (int)(200*SCALE), (int)(300*SCALE));
		y = GAME_HEIGHT - (int)(50*SCALE);
		indietro = new GeneralButton(percorsoIndietro, y, (int)(100*SCALE), (int)(15*SCALE), Gamestate.MAIN_MENU);
	}

	public void drawAvatarMenu(Graphics2D g2) {
		drawBackground(g2);
		drawAvatarChoiceText(g2);
		indietro.draw(g2);
		ragazzo.draw(g2);
		ragazza.draw(g2);
	}
 
	private void drawBackground(Graphics2D g2) {
		view.getMenu().drawBackground(g2);
	}
	
	private void drawAvatarChoiceText(Graphics2D g2) {
		g2.drawImage(scrittaAvatar, scrittaAvatarX, scrittaAvatarY, null);
	}
	
	public void mousePressed(MouseEvent e) {
		if (ragazzo.checkIfMouseIsIn(e)) 
			ragazzo.setMousePressed(true);
		
		if(ragazza.checkIfMouseIsIn(e))
			ragazza.setMousePressed(true);
		
		if(indietro.checkIfMouseIsIn(e))
			indietro.setMousePressed(true);
		
	}

	public void mouseReleased(MouseEvent e) {
		
		if (indietro.checkIfMouseIsIn(e) && indietro.isMousePressed()) 
			view.changeGameState(indietro.getButtonState());
		
		else if (ragazzo.checkIfMouseIsIn(e) && ragazzo.isMousePressed()) {
			view.changeGameState(ragazzo.getButtonState());
			view.playSE();
		}
		
		else if (ragazza.checkIfMouseIsIn(e) && ragazza.isMousePressed()) {
			view.changeGameState(ragazza.getButtonState());
			view.playSE();
		}
		
		resetButtons();
	}

	private void resetButtons() {
		ragazzo.resetBooleans();
		ragazza.resetBooleans();
		indietro.resetBooleans();
	}

	public void mouseMoved(MouseEvent e) {
		ragazzo.setMouseOver(false);	
		ragazza.setMouseOver(false);
		indietro.setMouseOver(false);
		
		if(ragazzo.checkIfMouseIsIn(e)) 
			ragazzo.setMouseOver(true);
		
		if(ragazza.checkIfMouseIsIn(e)) 
			ragazza.setMouseOver(true);
		
		if(indietro.checkIfMouseIsIn(e))
			indietro.setMouseOver(true);
	}

}

