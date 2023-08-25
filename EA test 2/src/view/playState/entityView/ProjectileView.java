package view.playState.entityView;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.main.GamePanel;

public class ProjectileView { 

	public int index;
	private IView view;
	private BufferedImage[] animazione;
	
	private int counter;
	private int animationIndex;
	private final int animationSpeed = 20;
	private int direction;
	private int xOffset;
	private int yOffset;
	
	int width;
	int height;
	
	public ProjectileView(int index, IView v) {
		this.index = index;
		view = v;
		direction = v.getController().getPlay().getAppuntiLanciati().get(index).getDirection();
		animazione = new BufferedImage[2];	//1 direzione, ciascuna con due immagini
		loadAnimation();
		width = view.getController().getPlay().getPlayer().getHitbox().width;
		height  = view.getController().getPlay().getPlayer().getHitbox().height;
	}
	
	private void loadAnimation() {
		BufferedImage image = null;	
		try {
			if(direction == 0) {
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_left_1.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animazione[0] = image;
				
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_left_2.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animazione[1] = image;
			}
			
			else if(direction == 1) {
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_right_1.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animazione[0] = image;
				
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_right_2.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animazione[1] = image;
			}
			
			else if(direction == 2) {
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_up_1.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animazione[0] = image;
				
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_up_2.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animazione[1] = image;
			}
			
			else if(direction == 3) {
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_down_1.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animazione[0] = image;
				
				image = ImageIO.read(getClass().getResourceAsStream("/entity/fireball_down_2.png"));
				image = ViewUtils.scaleImage(image, image.getWidth()*GamePanel.SCALE, image.getHeight()*GamePanel.SCALE);
				animazione[1] = image;
			}
	
		} 
		catch (IOException e) {
			e.printStackTrace();
			view.getPlay().getAppunti().clear();
			view.getController().getPlay().getAppuntiLanciati().clear();
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
			int xposInMap = view.getController().getPlay().getAppuntiLanciati().get(index).getHitbox().x;
			int yposInMap = view.getController().getPlay().getAppuntiLanciati().get(index).getHitbox().y;
			
			int distanzaX = playerx - xposInMap;
			int distanzaY = playery - yposInMap;
			
			int xPosOnScreen = PlayerView.xOnScreen - distanzaX + xOffset + PlayerView.getXOffset();
			int yPosOnScreen = PlayerView.yOnScreen - distanzaY + yOffset + PlayerView.getYOffset();
			
			g2.drawImage(animazione[animationIndex], xPosOnScreen, yPosOnScreen, null);
			g2.drawRect(xPosOnScreen + xOffset, yPosOnScreen + yOffset, width, height);
			
		}
		catch (IndexOutOfBoundsException obe) {
			//obe.printStackTrace();
			//System.out.println("problemi nel view projectile");
			//in caso di problemi elimina tutti gli appunti in giro
			view.getPlay().getAppunti().clear();
			view.getController().getPlay().getAppuntiLanciati().clear();
		}
			
	}
	
}
