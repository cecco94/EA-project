package view.menu.pauseMenu;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.main.Gamestate;
import view.IView;
import view.main.GamePanel;
import view.menu.AbstractMenuButton;

public class PauseScreenButton extends AbstractMenuButton {

	boolean isHome;
	
	
	public PauseScreenButton(IView v, String iconsPath, int x, int y, Gamestate state, boolean home) {
		view = v;
		newState = state;
		loadIconFromFile(iconsPath);
		super.setBounds(x, y, (int)(32*GamePanel.SCALE), (int)(32*GamePanel.SCALE));
		isHome = home;
	}
	
	
	private void loadIconFromFile(String iconsPath) {
		BufferedImage temp;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream(iconsPath));
			mouseAwayImage = temp.getSubimage(0, 0, 56, 56);
			mouseOverImage = temp.getSubimage(56, 0, 56, 56);
			mousePressedImage = temp.getSubimage(56 + 56, 0, 56, 56);
		}
		catch (IOException e) {
			e.printStackTrace();
		}				
	}
	
	@Override
	public void draw(Graphics2D g2) {
		
		g2.drawImage(mouseAwayImage, (int)bounds.getX(), (int)bounds.getY(), null);
		if(mouseOver)
			g2.drawImage(mouseOverImage, (int)bounds.getX(), (int)bounds.getY(), null);
		if(mousePressed)
			g2.drawImage(mousePressedImage, (int)bounds.getX(), (int)bounds.getY(), null);		
	
	}

	@Override
	public void reactToMouse(MouseEvent e) {
		if(isHome) {
			//quando il giocatore torna al menu dal play state, i dati di gioco vengono salvati. 
			//il view chiede al controller e al model di fare un tostring di tutti i dati dentro le stanze
			view.startSaveData();
			view.getTransition().setNext(Gamestate.MAIN_MENU);
			view.getTransition().setPrev(Gamestate.PLAYING);
			view.changeGameState(Gamestate.TRANSITION_STATE);
		}
		else
			view.changeGameState(newState);
	}

	@Override
	public void reactToDrag(MouseEvent e) {

	}

}
