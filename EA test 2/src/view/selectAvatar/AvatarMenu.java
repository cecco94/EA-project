package view.selectAvatar;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.GamePanel;
import view.IView;
import view.ViewUtils;

public class AvatarMenu {
//	private int indexBackground, counterbackground;
	private BufferedImage[] sfondoAnimato;
	private IView view;
	private int scrittaAvatarX, scrittaAvatarY;
	private BufferedImage scrittaAvatar;
	private AvatarButton ragazzo, ragazza;
	
	public AvatarMenu(IView v) {
		view = v;
	//	loadBackgroundImages();
		loadAvatarChoiceText();
		loadAvatarChoiceButton();
	}

	public void loadAvatarChoiceButton() {
		String percorsoRagazzo = "/playerSprites/boyAvatar1.png";
		String percorsoRagazza = "/playerSprites/girl.png";
		ragazzo = new AvatarButton(percorsoRagazzo, 100);
		ragazza = new AvatarButton(percorsoRagazza, 100 + 300 + 70);
	}
	
	private void loadAvatarChoiceText() {
		try {
			scrittaAvatar = ImageIO.read(getClass().getResourceAsStream("/playerSprites/scritta1.png"));	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		scrittaAvatarX = ViewUtils.getCenteredXPos(scrittaAvatar.getWidth());
		scrittaAvatarY = GamePanel.GAME_HEIGHT - scrittaAvatar.getHeight() - 50;
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

	public void drawAvatarMenu(Graphics2D g2) {
		drawBackground(g2);
		drawAvatarChoiceText(g2);
		ragazzo.draw(g2);
		ragazza.draw(g2);
	}
 
	private void drawBackground(Graphics2D g2) {
	/*	counterbackground++;
		g2.drawImage(sfondoAnimato[indexBackground], 0, 0, null);
		if(counterbackground == 10) {
			indexBackground++;
			counterbackground = 0;
		}
		if(indexBackground == 23)
			indexBackground = 0;	*/
		view.getMenu().drawBackground(g2);
	}
	
	public void mousePressed(MouseEvent e) {
		if (ragazzo.checkIfMouseIsIn(e)) {
			ragazzo.setMousePressed(true);
		}	
	}

	public void mouseReleased(MouseEvent e) {
		resetButtons();
	}

	private void resetButtons() {
		ragazzo.resetBooleans();
	}

	public void mouseMoved(MouseEvent e) {
		ragazzo.setMouseOver(false);	
		ragazza.setMouseOver(false);
		
		if(ragazzo.checkIfMouseIsIn(e)) 
			ragazzo.setMouseOver(true);
		if(ragazza.checkIfMouseIsIn(e)) 
			ragazza.setMouseOver(true);
	}
	
	private void drawAvatarChoiceText(Graphics2D g2) {
		g2.drawImage(scrittaAvatar, scrittaAvatarX, scrittaAvatarY, null);
	}


}

