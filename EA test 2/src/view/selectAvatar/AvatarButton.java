package view.selectAvatar;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Gamestate;
import view.ViewUtils;

public class AvatarButton {

	private BufferedImage AvatarImage;
	private boolean mouseOver, mousePressed;
	private int x, y, width, height;
	private Rectangle bounds;
	private Gamestate avanti = Gamestate.PLAYING;
	
	public AvatarButton(String percorsoIcona, int x) {
		setDimensions(x);	
		loadIcon(percorsoIcona, width, height);
	}

	private void setDimensions(int x) {
		this.x = x;
		y = 100;
		width = 300;
		height = 400;
		bounds = new Rectangle(x, y, width, height);
	}

	private void loadIcon(String percorsoIcona, int width, int height) {
		BufferedImage temp;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream(percorsoIcona));
			AvatarImage = ViewUtils.scaleImage(temp, width, height);
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void draw(Graphics2D g2) {
		g2.drawRect(x, y, width, height);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g2.drawImage(AvatarImage, x, y, null);
		
		if(mouseOver) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			g2.drawImage(AvatarImage, x, y, null);
		}				
	}
	
	public boolean checkIfMouseIsIn(MouseEvent e) {
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
		return avanti;
	}
}
