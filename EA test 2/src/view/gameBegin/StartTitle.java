package view.gameBegin;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.main.Gamestate;
import view.IView;
import view.ViewUtils;
import view.main.GamePanel;

// sarebbero le due scritte iniziali, "gngames" e "presenta"
public class StartTitle {
	
	private BufferedImage[] titoli;
	private int timer = 120*10;
	private int timerPrimaScritta = 120*5;
	private int counter;
	private int scritta1X, scritta1Y, scritta2X, scritta2Y;
	private float alPhaValue;
	private IView view;
	
	public StartTitle(IView v) {
		view = v;
		titoli = new BufferedImage[2];
		getImages();
		setImgDimensions();
		scritta1X = GamePanel.GAME_WIDTH/2 - titoli[0].getWidth()/2;
		scritta1Y = GamePanel.GAME_HEIGHT/2 - titoli[0].getHeight()/2;
		scritta2X = GamePanel.GAME_WIDTH/2 - titoli[1].getWidth()/2;
		scritta2Y = GamePanel.GAME_HEIGHT/2 - titoli[1].getHeight()/2;
		
	}

	private void setImgDimensions() {
		titoli[0] = ViewUtils.scaleImage(titoli[0], titoli[0].getWidth()*GamePanel.SCALE, titoli[0].getHeight()*GamePanel.SCALE);
		titoli[1] = ViewUtils.scaleImage(titoli[1], titoli[1].getWidth()*GamePanel.SCALE, titoli[0].getHeight()*GamePanel.SCALE/1.5f);
	}

	private void getImages() {
		try {
			titoli[0] = ImageIO.read(getClass().getResourceAsStream("/startTitle/gngames.png"));
			titoli[1] = ImageIO.read(getClass().getResourceAsStream("/startTitle/presenta.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void drawYourself(Graphics2D g2) {
		counter++;

		if(counter > timer) {
			skipTitle();
			return;
		}
		
		if(counter < timerPrimaScritta) {
			alPhaValue = (float) counter/(timerPrimaScritta);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alPhaValue));
			g2.drawImage(titoli[0], scritta1X, scritta1Y, null);
		}
		
		else if(counter >= timerPrimaScritta){
			alPhaValue = (float) (counter - timerPrimaScritta)/timerPrimaScritta;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alPhaValue));
			g2.drawImage(titoli[1], scritta2X, scritta2Y, null);
		}	
	}
	
	public void skipTitle() {
		view.changeGameState(Gamestate.MAIN_MENU);
	}
	
	

}
