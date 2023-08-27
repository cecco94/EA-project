package view.playState.entityView;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.mappa.Stanze;
import view.IView;
import view.ViewUtils;
import view.main.GamePanel;

public class CatView extends EntityView {

	public static final int BIANCO = 0, NERO = 1;

	public CatView(IView v, int index) {
		
		typeElemtToSort = 4;		//elemento animato, da disegnare sopra la mappa
		view = v;
		loadImages();	
		xOffset = 0; //3;
		yOffset = 0; //3;
		this.index = index;
	}


	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		animationSpeed = 40;
		
		animation = new BufferedImage[2][][][];
		animation[BIANCO] = new BufferedImage[2][][];	//per ogni gatto per ora abbiamo due azioni
		animation[NERO] = new BufferedImage[2][][];

		loadIdleImages(image, temp);
		loadRunImages(image, temp);	
		
	}

	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		animation[BIANCO][RUN] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/gattoBianco.png"));
						
			for(int direzione = 0; direzione < 4 ; direzione++) {
				for(int immagine = 0; immagine < 3; immagine++) {
					temp = image.getSubimage(immagine*32, direzione*32, 32, 32);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
					animation[BIANCO][RUN][direzione][immagine] = temp ;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	private void loadIdleImages(BufferedImage image, BufferedImage temp) {
		animation[BIANCO][IDLE] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 1 immaginE
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/gattoBianco.png"));
			
				temp = image.getSubimage(32, 0, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				animation[BIANCO][IDLE][DOWN][0] = temp ;
			
			
				temp = image.getSubimage(32, 64, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				animation[BIANCO][IDLE][RIGHT][0] = temp ;
			
				temp = image.getSubimage(32, 32, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				animation[BIANCO][IDLE][LEFT][0] = temp ;
			
				temp = image.getSubimage(32, 96, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				animation[BIANCO][IDLE][UP][0] = temp ;
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
		
		animationCounter++;
		setAction();
		setDirection();
		
		if (animationCounter > animationSpeed) {
			numSprite ++;	
			
			if(numSprite >= getAnimationLenght())
				numSprite = 0;	
			
			animationCounter = 0;
		}
		
		int distanzaX = xPlayerMap - xPosMapForSort;
		int distanzaY = yPlayerMap - yPosMapForSort;
		
		//ci serve un offset perchè la distanza del gatto nella mappa rispetto al player è riferita al punto in
		//alto a sinistra della hitbox. Per mantenere la stessa distanza, dobbiamo aggiungere questo offset
		int xPosOnScreen = PlayerView.xOnScreen - distanzaX + xOffset + PlayerView.getXOffset();
		int yPosOnScreen = PlayerView.yOnScreen - distanzaY + yOffset + PlayerView.getYOffset();
		
		try {
			g2.drawImage(animation[BIANCO][currentAction][currentDirection][numSprite], xPosOnScreen, yPosOnScreen, null);
			g2.drawRect(xPosOnScreen + xOffset,
						yPosOnScreen + yOffset,
						view.getController().getPlay().getRoom(Stanze.stanzaAttuale.indiceMappa).getNPC().get(index).getHitbox().width,
						view.getController().getPlay().getRoom(Stanze.stanzaAttuale.indiceMappa).getNPC().get(index).getHitbox().height);

		}
		catch (ArrayIndexOutOfBoundsException a) {
		//	System.out.println("azione " + currentAction + " direzione " + currentDirection+ " sprite " + numSprite);
		}
		
	}

	private void setAction() {
		//vede nel controller cosa fa il gatto e cambia currentAction
		currentAction = view.getController().getPlay().getRoom(Stanze.stanzaAttuale.indiceMappa).
															getNPC().get(index).getCurrentAction();
		
		//questo ci serve perchè così quando cambia azione si resetta il contatore delle sprite
		if(currentAction != previousAction) {
			numSprite = 0;
			previousAction = currentAction;
		}
	}
	
	private void setDirection() {
		//vede nel controller la direzione del gatto e cambia currentDirection
		currentDirection = view.getController().getPlay().getRoom(Stanze.stanzaAttuale.indiceMappa).
															getNPC().get(index).getCurrentDirection();
		
		// questo ci serve perchè l'ordine delle sprite nell'immagine è down, left, right, up
		if(currentDirection == RIGHT)
			currentDirection = 2;
		
		else if(currentDirection == LEFT)
			currentDirection = 1;
		
		else if(currentDirection == DOWN)
			currentDirection = 0;
		
		else if(currentDirection == UP)
			currentDirection = 3;
	}

	private int getAnimationLenght() {
		if(currentAction == IDLE)
			return 1;
		
		else if(currentAction == RUN)
			return 3;
		
		return 0;
	}


}
