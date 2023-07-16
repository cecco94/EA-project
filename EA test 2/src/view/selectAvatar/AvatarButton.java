package view.selectAvatar;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Gamestate;
import view.ViewUtils;
import view.main.IView;
import view.menuIniziale.MenuButton;

public class AvatarButton extends MenuButton {
	
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
