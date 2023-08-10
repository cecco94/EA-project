package view.playState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ViewUtils;
import view.main.GamePanel;
import view.main.IView;

public class PlayerView extends SortableElement{

	private BufferedImage down1;
	private int xOffset, yOffset;
	private IView view;
	
	public PlayerView(IView v) {
		view = v;
		try {
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/oldman_down_1.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		down1 = ViewUtils.scaleImage(down1, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
		xOffset = 0; //down1.getWidth()/2;
		yOffset = down1.getHeight()/2 - 10;
		
		//servono per ordinare il personaggio
		xPos = view.getController().getPlay().getPlayer().getHitbox().x - xOffset;
		yPos = view.getController().getPlay().getPlayer().getHitbox().y - yOffset;
		this.type = 5;
	}
	
	public void draw(Graphics2D g2) {	
		Rectangle hitbox = view.getController().getPlay().getPlayer().getHitbox();
		g2.drawImage(down1, xPos, yPos, null);
		
//		g2.setColor(Color.yellow);										//for debug
//		g2.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
//		g2.drawRect(xPos, yPos, down1.getWidth(), down1.getHeight());
	}
	
	public void setDrawPosition() {
		xPos = view.getController().getPlay().getPlayer().getHitbox().x - xOffset;
		yPos = view.getController().getPlay().getPlayer().getHitbox().y - yOffset;
	}
}
