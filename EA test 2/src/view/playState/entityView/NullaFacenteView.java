package view.playState.entityView;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.main.GamePanel;

public class NullaFacenteView extends EntityView {
	

	private final int DIE = 2;
	private Rectangle lifeRect;
	
	public NullaFacenteView(IView v, int index) {
		super(v, index);
		
		loadImages();
		
		xOffset = (int)(0*GamePanel.SCALE); 
		yOffset = (int)(0*GamePanel.SCALE); 
		animationSpeed = 30;
		lifeRect = new Rectangle(0,0, animation[0][0][0][0].getWidth(), (int)(2*GamePanel.SCALE));
			
	}
	
	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		animation = new BufferedImage[1][3][][];		
		
		animation[0][MOVE] = new BufferedImage[4][3];	//ci sono 4 direzioni, ogni direzione ha 3 immagini
		animation[0][IDLE] = new BufferedImage[4][1];	//ci sono 4 direzioni, ogni direzione ha 1 immagine
		animation[0][DIE] = new BufferedImage[4][2];
		animation[0][ATTACK] = new BufferedImage[4][2];
		
		loadRunImages(image, temp);	
		loadIdleImages();
		loadDieImages(image, temp);
		loadAttackImages(image, temp);
		
	}
	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/enemy1.png"));
				
			for(int img = 0; img < 3; img++) {
				temp = image.getSubimage(img*16, 0, 16, 24);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
				animation[0][MOVE][DOWN][img] = temp;
				}
			
			for(int img = 0; img < 3; img++) {
				temp = image.getSubimage(img*15, 24, 15, 23);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
				animation[0][MOVE][RIGHT][img] = temp;
				}
			
			for(int img = 0; img < 3; img++) {
				temp = image.getSubimage(img*15, 24 + 23, 15, 23);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
				animation[0][MOVE][LEFT][img] = temp;
				}
			
			for(int img = 0; img < 3; img++) {
				temp = image.getSubimage(img*16, 24 + 23 + 23, 16, 24);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
				animation[0][MOVE][UP][img] = temp;
				}	
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void loadIdleImages() {
		animation[0][IDLE][DOWN][0] = animation[0][MOVE][DOWN][0];
		animation[0][IDLE][RIGHT][0] = animation[0][MOVE][RIGHT][1];
		animation[0][IDLE][LEFT][0] = animation[0][MOVE][LEFT][1];
		animation[0][IDLE][UP][0] = animation[0][MOVE][UP][0];
		
	}
	
	
	private void loadDieImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/enemy1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temp = image.getSubimage(0*15, 166, 15, 20);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][DOWN][0] = temp;
		animation[0][DIE][RIGHT][0] = temp;
		
		temp = image.getSubimage(0*15, 166 + 20, 15, 20);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][LEFT][0] = temp;
		animation[0][DIE][UP][0] = temp;
		
		temp = image.getSubimage(1*24, 166, 24, 20);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][DOWN][1] = temp;
		animation[0][DIE][RIGHT][1] = temp;

		temp = image.getSubimage(1*24, 166 + 20 , 24, 20);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][LEFT][1] = temp;
		animation[0][DIE][UP][1] = temp;
	}
	
	
	private void loadAttackImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/enemy1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int img = 0; img < 2; img++) {
			temp = image.getSubimage(img*16, 94, 16, 24);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
			animation[0][ATTACK][DOWN][img] = temp;
		}
		
		temp = image.getSubimage(0, 118, 12, 24);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][RIGHT][0] = temp;
		
		temp = image.getSubimage(12, 118, 16, 23);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][RIGHT][1] = temp;
		

		
		temp = image.getSubimage(0, 118 + 24, 12, 24);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][LEFT][0] = temp;
		
		temp = image.getSubimage(12, 118 + 24, 16, 23);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][LEFT][1] = temp;
			
	}

	

	@Override
	public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
		g2.drawImage(animation[0][IDLE][RIGHT][0], 0, 0, null);
	}

}