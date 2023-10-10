package view.playState.entityView;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.main.GamePanel;

public class NullaFacenteView extends EntityView {
	
	//definiamo la costantre DIE = 3 per evitare il problema di out of bound 
	//perchè sennò eredita da entity DIE = 5 mentre il nostro array delle azioni è lungo 4
//	private final int DIE = 3;
	private Rectangle lifeRect;
	private boolean firstDeathSprite;

	
	public NullaFacenteView(IView v, int index) {
		super(v, index);
		this.type = "enemy";
		
		loadImages();
		
		xOffset = (int)(0*GamePanel.SCALE); 
		yOffset = (int)(0*GamePanel.SCALE); 
		animationSpeed = 30;
		lifeRect = new Rectangle(0,0, animation[0][0][0][0].getWidth(), (int)(2*GamePanel.SCALE));
			
	}
	
	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		animation = new BufferedImage[1][6][][];		
		
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
			image = ImageIO.read(getClass().getResourceAsStream("/entity/nullafacente.png"));
				
			for(int img = 0; img < 3; img++) {
				temp = image.getSubimage(img*16, 0, 16, 24);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
				animation[0][MOVE][DOWN][img] = temp;
				}
			
			for(int img = 0; img < 3; img++) {
				temp = image.getSubimage(img*16, 24, 16, 23);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
				animation[0][MOVE][RIGHT][img] = temp;
				}
			
			for(int img = 0; img < 3; img++) {
				temp = image.getSubimage(img*16, 24 + 23, 16, 23);
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
			image = ImageIO.read(getClass().getResourceAsStream("/entity/nullafacente.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		temp = image.getSubimage(0, 190, 15, 20);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][DOWN][0] = temp;
		animation[0][DIE][LEFT][0] = temp;
		
		temp = image.getSubimage(15, 190, 24, 20);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][DOWN][1] = temp;
		animation[0][DIE][LEFT][1] = temp;
		
		temp = image.getSubimage(0, 210, 15, 20);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][RIGHT][0] = temp;
		animation[0][DIE][UP][0] = temp;
		
		temp = image.getSubimage(15, 210, 24, 20);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][DIE][RIGHT][1] = temp;
		animation[0][DIE][UP][1] = temp;

	}
	
	
	private void loadAttackImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/nullafacente.png"));
		}
		catch (IOException e) {
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
		
		
		temp = image.getSubimage(0, 118 + 24 + 24, 16, 24);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][UP][0] = temp;

		temp = image.getSubimage(16, 118 + 24 + 24, 17, 24);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][UP][1] = temp;
			
	}


	@Override
	public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
		animationCounter++;
		setAction(this);
		setDirection(this);
		
		if (animationCounter > animationSpeed) {
			numSprite ++;	
			setDyingAniIndex();
			
			if(numSprite >= getAnimationLenght())
				numSprite = 0;	
			
			animationCounter = 0;
		}
		
		//distanza nella mappa tra il punto in alto a sinistra della hitbox 
		//del player ed il punto in alto a sinistra della hitbox del gatto
		int distanceX = xPlayerMap - xPosMapForSort + xOffset;
		int distanceY = yPlayerMap - yPosMapForSort + yOffset;
		
		//ci serve un offset perchè la distanza del gatto nella mappa rispetto al player è riferita al punto in
		//alto a sinistra della hitbox. Per mantenere la stessa distanza, dobbiamo aggiungere questo offset
		xPosOnScreen = PlayerView.xOnScreen - distanceX - xOffset + PlayerView.getXOffset();
		yPosOnScreen = PlayerView.yOnScreen - distanceY - yOffset + PlayerView.getYOffset();
		
		try {
			g2.drawImage(animation[0][currentAction][currentDirection][numSprite], xPosOnScreen, yPosOnScreen, null);
			
			
			
			//quadrato dove viene disegnato il gatto
			g2.setColor(Color.red);
			g2.drawRect(xPosOnScreen, yPosOnScreen, 48, 48);
			
			//quadrato della hitbox
			g2.setColor(Color.black);
			g2.drawRect(xPosOnScreen + 3*xOffset,
						yPosOnScreen + 3*yOffset,
						view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getHitbox().width,
						view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getHitbox().height);

		}
		catch (ArrayIndexOutOfBoundsException a) {
			a.printStackTrace();
			System.out.println("azione " + currentAction + " direzione " + currentDirection+ " sprite " + numSprite);
		}
		
	}
	
	private void setDyingAniIndex() {
//		System.out.println("metodo chiamato");
//		if(currentAction == EntityView.DIE)
//			currentAction = DIE;
		
		if(currentAction == DIE) {
			if(firstDeathSprite) {
				numSprite--;
				firstDeathSprite = false;
			}
			else {
				numSprite = getAnimationLenght() - 1;
			}
		}
		
	}
	
	private int getAnimationLenght() {
		if(currentAction == IDLE)
			return 1;
		
		else if(currentAction == MOVE)
			return 3;
		
		else if(currentAction == DIE)
			return 2;
		
		else if(currentAction == ATTACK)
			return 2;
		
		else
			return 0;
	}

}