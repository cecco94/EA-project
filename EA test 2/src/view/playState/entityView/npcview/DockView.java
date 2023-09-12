package view.playState.entityView.npcview;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.main.GamePanel;
import view.playState.entityView.EntityView;
import view.playState.entityView.PlayerView;

public class DockView extends EntityView {
	
	private BufferedImage[][][][] animation;

	public DockView(IView v, int index) {
		typeElemtToSort = 4;		//elemento animato, da disegnare sopra la mappa
		view = v;
		this.index = index;
		
		setDialogs();
		loadImages();	
		
		xOffset = (int)(0*GamePanel.SCALE); //3;
		yOffset = (int)(0*GamePanel.SCALE); //3;
		animationSpeed = 40;
		
		currentAction = IDLE;
		currentDirection = DOWN;
	}
	
	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		animation = new BufferedImage[1][2][][];		//due tipi di gatto
		
		loadRunImages(image, temp);	
		loadIdleImages(image, temp);
		
	}

	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		animation[0][RUN] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/ProfLab.png"));
						
			for(int direction = 0; direction < 4 ; direction++) {
				for(int img = 0; img < 3; img++) {
					temp = image.getSubimage(img*16, direction*24, 16, 24);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
					animation[0][RUN][direction][img] = temp;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void loadIdleImages(BufferedImage image, BufferedImage temp) {
		animation[0][IDLE] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 1 immagine
		
		animation[0][IDLE][DOWN][0] = animation[0][RUN][DOWN][0];
		animation[0][IDLE][RIGHT][0] = animation[0][RUN][RIGHT][0];
		animation[0][IDLE][LEFT][0] = animation[0][RUN][LEFT][0];
		animation[0][IDLE][UP][0] = animation[0][RUN][UP][0];
		
	}

	private void setDialogs() {
		// TODO Auto-generated method stub
		
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
		
		//distanza nella mappa tra il punto in alto a sinistra della hitbox 
		//del player ed il punto in alto a sinistra della hitbox del gatto
		int distanceX = xPlayerMap - xPosMapForSort + xOffset;
		int distanceY = yPlayerMap - yPosMapForSort + yOffset;
		
		//ci serve un offset perchè la distanza del gatto nella mappa rispetto al player è riferita al punto in
		//alto a sinistra della hitbox. Per mantenere la stessa distanza, dobbiamo aggiungere questo offset
		int xPosOnScreen = PlayerView.xOnScreen - distanceX - xOffset + PlayerView.getXOffset();
		int yPosOnScreen = PlayerView.yOnScreen - distanceY - yOffset + PlayerView.getYOffset();
		
		try {
			g2.drawImage(animation[0][currentAction][currentDirection][numSprite], xPosOnScreen, yPosOnScreen, null);
			
			//quadrato dove viene disegnato il gatto
			g2.setColor(Color.red);
			g2.drawRect(xPosOnScreen, yPosOnScreen, (int)(24*1.5), (int)(34*1.5));
			
			//quadrato della hitbox
			g2.setColor(Color.black);
			g2.drawRect(xPosOnScreen + 3*xOffset,
						yPosOnScreen + 3*yOffset,
						view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getHitbox().width,
						view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getHitbox().height);

		}
		catch (ArrayIndexOutOfBoundsException a) {
			a.printStackTrace();
		}
		
	}

	private int getAnimationLenght() {
		if(currentAction == IDLE)
			return 1;
		
		else if(currentAction == RUN)
			return 3;
		
		return 0;
	}

	private void setDirection() {
		//vede nel controller la direzione del gatto e cambia currentDirection
				currentDirection = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getCurrentDirection();
				
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

	private void setAction() {
		//vede nel controller cosa fa il gatto e cambia currentAction
		currentAction = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getCurrentAction();
		
		//questo ci serve perchè così quando cambia azione si resetta il contatore delle sprite
		if(currentAction != previousAction) {
			numSprite = 0;
			previousAction = currentAction;
		}
		
	}	

}
