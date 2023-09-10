package view.playState.entityView;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.main.GamePanel;

public class BulletView { 

	public int index;
	private IView view;
	private BufferedImage[] animation;
	
	private int counter;
	private int animationIndex;
	private final int animationSpeed = 20;
	private int direction;
	private int xOffset;
	private int yOffset;
	
	int width;
	int height;
	
	public BulletView(int index, IView v) {
		this.index = index;
		view = v;
		direction = v.getController().getPlay().getBulletsInRoom().get(index).getDirection();
		animation = new BufferedImage[2];	//1 direzione, ciascuna con due immagini
		loadAnimation();
		width = view.getController().getPlay().getPlayer().getHitbox().width;
		height  = view.getController().getPlay().getPlayer().getHitbox().height;
	}
	
	private void loadAnimation() {
		BufferedImage image = null;	
		try {
			if(direction == EntityView.LEFT) {
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_left_1.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animation[0] = image;
				
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_left_2.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animation[1] = image;
			}
			
			else if(direction == EntityView.RIGHT) {
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_right_1.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animation[0] = image;
				
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_right_2.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animation[1] = image;
			}
			
			else if(direction == EntityView.UP) {
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_up_1.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animation[0] = image;
				
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_up_2.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animation[1] = image;
			}
			
			else if(direction == EntityView.DOWN) {
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_down_1.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animation[0] = image;
				
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_down_2.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animation[1] = image;
			}
	
		} 
		catch (IOException e) {
			e.printStackTrace();
			view.getPlay().getBullets().clear();
			view.getController().getPlay().getBulletsInRoom().clear();
		}
	}
		

	public void draw(Graphics2D g2, int playerx, int playery) {
		counter++;
		//decidiamo quando disegnare
		if(counter >= animationSpeed) {
			animationIndex++;
			
			//decidiamo cosa disegnare
			if(animationIndex > 1) {
				animationIndex = 0;
			}
			
			counter = 0;
		}
		
		try {
			//decidiamo dove disegnarlo
			int xposInMap = view.getController().getPlay().getBulletsInRoom().get(index).getHitbox().x;
			int yposInMap = view.getController().getPlay().getBulletsInRoom().get(index).getHitbox().y;
			
			int distanzaX = playerx - xposInMap;
			int distanzaY = playery - yposInMap;
			
			int xPosOnScreen = PlayerView.xOnScreen - distanzaX + xOffset + PlayerView.getXOffset();
			int yPosOnScreen = PlayerView.yOnScreen - distanzaY + yOffset + PlayerView.getYOffset();
			
			g2.drawImage(animation[animationIndex], xPosOnScreen, yPosOnScreen, null);
			g2.drawRect(xPosOnScreen + xOffset, yPosOnScreen + yOffset, width, height);
			
		}
		catch (IndexOutOfBoundsException obe) {
			//obe.printStackTrace();
			//System.out.println("problemi nel view projectile");
			//in caso di problemi elimina tutti gli appunti in giro
			view.getPlay().getBullets().clear();
			view.getController().getPlay().getBulletsInRoom().clear();
		}
			
	}
	
}
