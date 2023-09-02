package view.playState.entityView;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import model.mappa.Rooms;
import view.IView;
import view.ViewUtils;
import view.main.GamePanel;

public class CatView extends EntityView {

	public static final int BIANCO = 0, NERO = 1;
	//siccome tutti i gatti condividono lo stesso array di animazioni, per non 
	//caricare le immagini ogni volta che creiamo un gatto, usiamo questo boolean
	//e le immagini verranno caricate solo una volta
	public static boolean firstCat = true;
	private int color;

	public CatView(IView v, int index) {
		
		typeElemtToSort = 4;		//elemento animato, da disegnare sopra la mappa
		view = v;
		this.index = index;

		loadImages();	
		
		xOffset = (int)(1*GamePanel.SCALE); //3;
		yOffset = (int)(4*GamePanel.SCALE); //3;
		animationSpeed = 40;
		
		selectColor();
	}

	//ogni volta che crea un gatto sceglie il colore a caso
	private void selectColor() {
		Random r = new Random();
		color = r.nextInt(2);	
	}

	private void loadImages() {
		if(firstCat) {
			BufferedImage image = null;
			BufferedImage temp = null;
			
			CatView.animation = new BufferedImage[2][][][];		//due tipi di gatto
			CatView.animation[BIANCO] = new BufferedImage[2][][];	//per ogni gatto per ora abbiamo due azioni
			CatView.animation[NERO] = new BufferedImage[2][][];
	
			loadIdleImages(image, temp);
			loadRunImages(image, temp);	
			firstCat = false;
			
		}
	}

	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		CatView.animation[BIANCO][RUN] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/gattoBianco.png"));
						
			for(int direction = 0; direction < 4 ; direction++) {
				for(int img = 0; img < 3; img++) {
					temp = image.getSubimage(img*32, direction*32, 32, 32);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
					CatView.animation[BIANCO][RUN][direction][img] = temp ;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		CatView.animation[NERO][RUN] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/gattoNero.png"));
						
			for(int direction = 0; direction < 4 ; direction++) {
				for(int img = 0; img < 3; img++) {
					temp = image.getSubimage(img*32, direction*32, 32, 32);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
					CatView.animation[NERO][RUN][direction][img] = temp ;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void loadIdleImages(BufferedImage image, BufferedImage temp) {
		CatView.animation[BIANCO][IDLE] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 1 immaginE
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/gattoBianco.png"));
			
				temp = image.getSubimage(32, 0, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				CatView.animation[BIANCO][IDLE][DOWN][0] = temp ;
			
			
				temp = image.getSubimage(32, 64, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				CatView.animation[BIANCO][IDLE][RIGHT][0] = temp ;
			
				temp = image.getSubimage(32, 32, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				CatView.animation[BIANCO][IDLE][LEFT][0] = temp ;
			
				temp = image.getSubimage(32, 96, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				CatView.animation[BIANCO][IDLE][UP][0] = temp ;
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		CatView.animation[NERO][IDLE] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 1 immaginE
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/gattoNero.png"));
			
				temp = image.getSubimage(32, 0, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				CatView.animation[NERO][IDLE][DOWN][0] = temp ;
			
			
				temp = image.getSubimage(32, 64, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				CatView.animation[NERO][IDLE][RIGHT][0] = temp ;
			
				temp = image.getSubimage(32, 32, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				CatView.animation[NERO][IDLE][LEFT][0] = temp ;
			
				temp = image.getSubimage(32, 96, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				CatView.animation[NERO][IDLE][UP][0] = temp ;
			
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
		
		//distanza nella mappa tra il punto in alto a sinistra della hitbox 
		//del player ed il punto in alto a sinistra della hitbox del gatto
		int distanceX = xPlayerMap - xPosMapForSort + xOffset;
		int distanceY = yPlayerMap - yPosMapForSort + yOffset;
		
		//ci serve un offset perchè la distanza del gatto nella mappa rispetto al player è riferita al punto in
		//alto a sinistra della hitbox. Per mantenere la stessa distanza, dobbiamo aggiungere questo offset
		int xPosOnScreen = PlayerView.xOnScreen - distanceX - xOffset + PlayerView.getXOffset();
		int yPosOnScreen = PlayerView.yOnScreen - distanceY - yOffset + PlayerView.getYOffset();
		
		try {
			g2.drawImage(CatView.animation[color][currentAction][currentDirection][numSprite], xPosOnScreen, yPosOnScreen, null);
			
			//quadrato dove viene disegnato il gatto
			g2.setColor(Color.red);
			g2.drawRect(xPosOnScreen, yPosOnScreen, 48, 48);
			
			//quadrato della hitbox
			g2.setColor(Color.black);
			g2.drawRect(xPosOnScreen + 3*xOffset,
						yPosOnScreen + 3*yOffset,
						view.getController().getPlay().getRoom(Rooms.currentRoom.mapIndex).getNPC().get(index).getHitbox().width,
						view.getController().getPlay().getRoom(Rooms.currentRoom.mapIndex).getNPC().get(index).getHitbox().height);

		}
		catch (ArrayIndexOutOfBoundsException a) {
			a.printStackTrace();
		//	System.out.println("azione " + currentAction + " direzione " + currentDirection+ " sprite " + numSprite);
		}
		
	}

	private void setAction() {
		//vede nel controller cosa fa il gatto e cambia currentAction
		currentAction = view.getController().getPlay().getRoom(Rooms.currentRoom.mapIndex).
															getNPC().get(index).getCurrentAction();
		
		//questo ci serve perchè così quando cambia azione si resetta il contatore delle sprite
		if(currentAction != previousAction) {
			numSprite = 0;
			previousAction = currentAction;
		}
	}
	
	private void setDirection() {
		//vede nel controller la direzione del gatto e cambia currentDirection
		currentDirection = view.getController().getPlay().getRoom(Rooms.currentRoom.mapIndex).
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
