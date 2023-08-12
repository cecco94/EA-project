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
	//per capire la posizione del player usiamo la hitbox, per capire se sta davanti o dietro qualcosa ci servono due offset
	//perchè la hitbox non coincide con la dimensione completa del personaggio, essendo più piccola
	private int xOffset, yOffset;	
	private IView view;
	
	private int xOnScreen, yOnScreen;
	
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
		this.typeElemtToSort = 4;
		
		xOnScreen = GamePanel.GAME_WIDTH/2 - GamePanel.TILES_SIZE/2;
		yOnScreen = GamePanel.GAME_HEIGHT/2 - GamePanel.TILES_SIZE/2;
	}
	
	public void setDrawPosition() {
		xPos = view.getController().getPlay().getPlayer().getHitbox().x - xOffset;
		yPos = view.getController().getPlay().getPlayer().getHitbox().y - yOffset;
	}
	
	public int getXOnScreen() {
		return xOnScreen;
	}
	
	public int getYOnScreen() {
		return yOnScreen;
	}

	@Override
	public void draw(Graphics2D g2, int xOffsetRespectTheCenterOfScreen, int yOffsetRespectTheCenterOfScreen) {
		Rectangle hitbox = view.getController().getPlay().getPlayer().getHitbox();
		g2.drawImage(down1, xOnScreen, yOnScreen, null);
		
		g2.setColor(Color.yellow);										//for debug
		g2.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		g2.drawRect(xOnScreen, yOnScreen, down1.getWidth(), down1.getHeight());
	}
	
}
