package view.playState.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ViewUtils;
import view.main.GamePanel;
import view.main.IView;
import view.playState.drawOrder.SortableElement;

public class CatView extends SortableElement {

	private static BufferedImage[][][][] catAnimation; // primo campo = colore gatto , secondo campo = azione ,terzo campo = direzione , quarto campo = num sprite
	private final static int IDLE = 0, RUN = 1, DOWN = 0, RIGHT = 1, LEFT = 2, UP = 3;
	private int currentAction = RUN;
//	private int previousAction = RUN;
	private int currentDirection = DOWN;
	public static final int BIANCO = 0, NERO = 1;
//	private IView view;
	
	private int animationCounter;
	private int animationSpeed = 40;
	private int numSprite;
	
//	private int directionCounter;
//	private int direzioni;


	public CatView(IView v) {
		
		this.typeElemtToSort = 4;		//elemento animato, da disegnare sopra la mappa
	//	view = v;
		
		loadImages();	

	}


	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		catAnimation = new BufferedImage[2][][][];
		catAnimation[BIANCO] = new BufferedImage[2][][];			//per ogni gatto per ora abbiamo due azioni
		catAnimation[NERO] = new BufferedImage[2][][];

		loadIdleImages(image, temp);
		loadRunImages(image, temp);	
		
	}


	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		catAnimation[BIANCO][RUN] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/cat/gattoBianco.png"));
						
			for(int direzione = 0; direzione < 4 ; direzione++) {
				for(int immagine = 0; immagine < 3; immagine++) {
					temp = image.getSubimage(immagine*32, direzione*32, 32, 32);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
					catAnimation[BIANCO][RUN][direzione][immagine] = temp ;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	private void loadIdleImages(BufferedImage image, BufferedImage temp) {
		catAnimation[BIANCO][IDLE] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 1 immaginE
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/cat/gattoBianco.png"));
			
				temp = image.getSubimage(32, 0, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				catAnimation[BIANCO][IDLE][DOWN][0] = temp ;
			
			
				temp = image.getSubimage(32, 64, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				catAnimation[BIANCO][IDLE][RIGHT][0] = temp ;
			
				temp = image.getSubimage(32, 32, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				catAnimation[BIANCO][IDLE][LEFT][0] = temp ;
			
				temp = image.getSubimage(32, 96, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*GamePanel.SCALE, temp.getHeight()*GamePanel.SCALE);
				catAnimation[BIANCO][IDLE][UP][0] = temp ;
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		/*playerAnimation[RAGAZZA][IDLE] = new BufferedImage[4][4];
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/IdleGirl.png"));
			
			for(int i = 0; i < 4; i++) {
				temp = image.getSubimage(i*23, 0, 23, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZA][IDLE][DOWN][i] = temp;
			}
			
			for(int i = 0; i < 4; i++) {
				temp = image.getSubimage(i*22, 32, 22, 31);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZA][IDLE][RIGHT][i] = temp;
			}
			
			for(int i = 0; i < 4; i++) {
				temp = image.getSubimage(i*22, 32 + 31, 22, 31);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZA][IDLE][LEFT][i] = temp;
			}
			
			for(int i = 0; i < 4; i++) {
				temp = image.getSubimage(i*22, 32 + 31 + 31, 22, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZA][IDLE][UP][i] = temp;
			}
		} 
	
		catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}


	@Override
	public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
		
		animationCounter++;
		//setDirection();

		if (animationCounter > animationSpeed) {
			numSprite ++;	
			if(numSprite >= getAnimationLenght())
				numSprite = 0;	
			
			animationCounter = 0;
		}
		g2.drawImage(catAnimation[BIANCO][RUN][currentDirection][numSprite], 0, 0, null);
		
	}


	private int getAnimationLenght() {
		if(currentAction == IDLE)
			return 1;
		else if(currentAction == RUN)
			return 3;
		
		return 0;
	}


//	private void setDirection() {
//		directionCounter++;
//		if (directionCounter > animationSpeed*3) {
//			direzioni ++;
//			currentDirection = direzioni;
//			if(direzioni > 3) {
//				direzioni = 0;
//			 	currentDirection = 0;
//			}
//			directionCounter = 0;
//		}	
//	}


}
