package view.menu.avatarSelection;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Gamestate;
import view.ViewUtils;
import view.main.IView;
import view.menu.AbstractMenuButton;

// sarebbero le immagini con mario e peach
public class AvatarButton extends AbstractMenuButton {
	
	public AvatarButton(String percorsoIcona, int x, int y, int width, int height, IView v) {
		super.setBounds(x, y, width, height);	
		loadIcon(percorsoIcona);
		newState = Gamestate.PLAYING;
		view = v;
	}

	private void loadIcon(String percorsoIcona) {
		BufferedImage temp;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream(percorsoIcona));
			mouseOverImage = ViewUtils.scaleImage(temp, bounds.width, bounds.height);
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	// prima li disegna trasparenti, poi se ci passa sopra il mouse diventano del tutto visibili.
	//il valore alpha è un float che varia tra 0 e 1
	public void draw(Graphics2D g2) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g2.drawImage(mouseOverImage, bounds.x, bounds.y, null);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		if(mouseOver) {
			g2.drawImage(mouseOverImage, bounds.x, bounds.y, null);
		}				
	}

	@Override
	public void reactToMouse(MouseEvent e) {
		view.changeGameState(newState);
	}

}
