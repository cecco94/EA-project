package view.menu.optionMenu;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.menu.AbstractMenuButton;

public class OptionButton extends AbstractMenuButton {

	private int difficolta;
	
	public OptionButton(String[] path, int y, int width, int height, int diff, IView v) {
		difficolta = diff;
		super.setBounds(ViewUtils.getCenteredXPos(width), y, width, height);
		loadIcons(path, width, height);
		view = v;
	}
	
	private void loadIcons(String[] percorsoIcone, int width, int height) {
		BufferedImage temp;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream(percorsoIcone[0]));
			mouseAwayImage = ViewUtils.scaleImage(temp, width, height);
			temp = ImageIO.read(getClass().getResourceAsStream(percorsoIcone[1]));
			mouseOverImage = ViewUtils.scaleImage(temp, width, height);
			temp = ImageIO.read(getClass().getResourceAsStream(percorsoIcone[2]));
			mousePressedImage = ViewUtils.scaleImage(temp, width, height);	
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
	
	public int getDifficolta() {
		return difficolta;
	}

	@Override
	public void reactToMouse(MouseEvent e) {
		view.getOptions().setFifficolta(difficolta);
		//chiedi a view di chiedere al model di settare la difficoltà
	}

}
