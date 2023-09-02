package view.menu.optionMenu;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.menu.AbstractMenuButton;

public class OptionButton extends AbstractMenuButton {

	private int difficulty;
	
	public OptionButton(String[] path, int y, int width, int height, int diff, IView v) {
		difficulty = diff;
		super.setBounds(ViewUtils.getCenteredXPos(width), y, width, height);
		loadIcons(path, width, height);
		view = v;
	}
	
	private void loadIcons(String[] iconsPath, int width, int height) {
		try {
			mouseAwayImage = ImageIO.read(getClass().getResourceAsStream(iconsPath[0]));
			mouseAwayImage = ViewUtils.scaleImage(mouseAwayImage, width, height);
			
			mouseOverImage = ImageIO.read(getClass().getResourceAsStream(iconsPath[1]));
			mouseOverImage = ViewUtils.scaleImage(mouseOverImage, width, height);
			
			mousePressedImage = ImageIO.read(getClass().getResourceAsStream(iconsPath[2]));
			mousePressedImage = ViewUtils.scaleImage(mousePressedImage, width, height);	
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
		return difficulty;
	}

	@Override
	public void reactToMouse(MouseEvent e) {
		view.getOptions().setDifficulty(difficulty);
		//chiedi a view di chiedere al model di settare la difficoltà
	}

	@Override
	public void reactToDrag(MouseEvent e) {
	
	}

}
