package view.mainMenu;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Gamestate;
import view.ViewUtils;

public class MenuButton{

	private BufferedImage mouseOverImage, mousePressedImage, mouseAwayImage;
	private boolean mouseOver, mousePressed;
	private int x, y;
	private Rectangle bounds;
	private Gamestate newState;
	
	public MenuButton(String[] percorsoIcone, int y, int width, int height, Gamestate state) {
		loadIcons(percorsoIcone, width, height);
		this.x = ViewUtils.getCenteredXPos(width);
		this.y = y;
		bounds = new Rectangle(x, y, width, height);
		newState = state;
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
	
	public void draw(Graphics2D g2) {
		g2.drawImage(mouseAwayImage, x, y, null);
		if(mouseOver)
			g2.drawImage(mouseOverImage, x, y, null);
		if(mousePressed)
			g2.drawImage(mousePressedImage, x, y, null);		
	}
	
	public boolean checkIfMouseIsIn(MouseEvent e, MenuButton mb) {
		return bounds.contains(e.getX(), e.getY());
	}
	
	public void resetBooleans(){
		mouseOver = false;
		mousePressed = false;
	}
	
	public void setMouseOver(Boolean b) {
		mouseOver = b;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public Gamestate getButtonState() {
		return newState;
	}
}
