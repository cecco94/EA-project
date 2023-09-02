package view.playState.entityView;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.main.GamePanel;
import view.sound.SoundManager;

//classe che contiene la parte grafica del giocatore
public class PlayerView extends EntityView {
		
	//campo 0 = tipo(ragazzo-ragazza), primo campo = azione, secondo = direzione, terzo = immagine
	//la classa estente Entity e quindi eredita l'array di animazioni vuoto
	private static  BufferedImage[][][][] playerAnimation = PlayerView.animation;
	
	private boolean endAttackAnimation = true;
	private boolean firstParry = true;
	
	public static final int RAGAZZO = 0, RAGAZZA = 1;
	private int avatarType = RAGAZZO;
	
	//per capire la posizione del player usiamo la hitbox, per capire se sta davanti o dietro qualcosa ci servono due offset
	//perchè la hitbox non coincide con la dimensione completa del personaggio, essendo la prima più piccola
	//difference between xitbox and image of player
	private static int xOffset = (int)(GamePanel.TILES_SIZE*0.10); // = 4, perchè la hitboxX è il 75% di un tile
	private static int yOffset = GamePanel.TILES_SIZE/2;  		// = 24, perchè la hitboxY è metà di un tile		
	
	//la posizione del player è sempre al centro della finestra di gioco
	public static final int xOnScreenOriginal = GamePanel.GAME_WIDTH/2 - GamePanel.TILES_SIZE/2;
	public static final int yOnScreen = GamePanel.GAME_HEIGHT/2 - GamePanel.TILES_SIZE/2;
	public static int xOnScreen = xOnScreenOriginal;	//alcune animazioni hanno offset diversi
	
	
	public PlayerView(IView v) {
		view = v;
		
		//servono per poter comparare il player con gli altri elementi grafici da ordinare
		xPosMapForSort = view.getController().getPlay().getPlayer().getHitbox().x - xOffset;
		yPosMapForSort = view.getController().getPlay().getPlayer().getHitbox().y - yOffset;
		this.typeElemtToSort = 4;		//elemento animato, da disegnare sopra la mappa
		
		loadImages();	

	}
	
	//mettiamo tutte le animazioni un una tabella. prima selezioniamo ogni sottoimmagine poi le scaliamo.
	//ogni azione ha un array di immagini che mettiamo in loop una dopo l'altra facendo aumentare un indice
	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		playerAnimation = new BufferedImage[2][][][];			//due avatar
		playerAnimation [RAGAZZO] = new BufferedImage[7][][];	//sette azioni
		playerAnimation [RAGAZZA] = new BufferedImage[7][][];
		
		loadIdleImages(image, temp);
		loadRunImages(image, temp);	
		loadAttackImages(image, temp);
		loadDeathImages(image, temp);
		loadSleepImages(image, temp);
		loadParryImages(image, temp);
		loadThrowImages(image, temp);
		
	}
	
	private void loadThrowImages(BufferedImage image, BufferedImage temp) {
		playerAnimation [RAGAZZO][THROW] = new BufferedImage[4][2];		//ci sono 4 direzioni, ogni direzione ha 2 immagini	
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/SpearBoy.png"));
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*25, 0, 25, 38);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][THROW][DOWN][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*32, 38, 32, 34);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][THROW][RIGHT][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*32, 38 + 34, 32, 34);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZO][THROW][LEFT][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*26, 38 + 34 + 34, 26, 34);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZO][THROW][UP][i] = temp;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		playerAnimation [RAGAZZA][THROW] = new BufferedImage[4][2];
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/SpellGirl.png"));
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*28, 0, 28, 30);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][THROW][DOWN][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*25, 30, 25, 29);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][THROW][RIGHT][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*25, 30 + 29, 25, 29);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][THROW][LEFT][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*24, 30 + 29 + 29, 24, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][THROW][UP][i] = temp;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
	
		}
	}
	
	private void loadParryImages(BufferedImage image, BufferedImage temp) {
		playerAnimation [RAGAZZO][PARRY] = new BufferedImage[4][2];		//ci sono 1 direzioni, ogni direzione ha 6 immagini	
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/BowBoy.png"));
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*25, 0, 25, 34);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][PARRY][DOWN][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*28, 34, 28, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][PARRY][RIGHT][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*28, 34 + 33, 28, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][PARRY][LEFT][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*32, 34 + 33 + 33, 32, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][PARRY][UP][i] = temp;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		playerAnimation [RAGAZZA][PARRY] = new BufferedImage[4][2];
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/BowGirl.png"));
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*26, 0, 26, 34);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][PARRY][DOWN][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*32, 34, 32, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][PARRY][RIGHT][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*32, 34 + 33, 32, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][PARRY][LEFT][i] = temp;
			}
			
			for(int i = 0; i < 2; i++) {
				temp = image.getSubimage(i*31, 34 + 33 + 33, 31, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][PARRY][UP][i] = temp;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}		

	private void loadSleepImages(BufferedImage image, BufferedImage temp) {
		playerAnimation [RAGAZZO][SLEEP] = new BufferedImage[1][6];		//ci sono 1 direzioni, ogni direzione ha 6 immagini	
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/SleepingBoy.png"));
	
			for(int i = 0; i < 6; i++) {
				temp = image.getSubimage(i*31, 0, 31, 37);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][SLEEP][DOWN][i] = temp;
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		playerAnimation [RAGAZZA][SLEEP] = new BufferedImage[1][6];		//ci sono 1 direzioni, ogni direzione ha 6 immagini	
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/SleepingGirl.png"));
	
			for(int i = 0; i < 6; i++) {
				temp = image.getSubimage(i*28, 0, 28, 36);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][SLEEP][DOWN][i] = temp;
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void loadDeathImages(BufferedImage image, BufferedImage temp) {
		playerAnimation [RAGAZZO][DIE] = new BufferedImage[2][9];		//ci sono 4 direzioni, ogni direzione ha 5 immagini	
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/deathBoyCorr.png"));
	
			for(int i = 0; i < 9; i++) {
				temp = image.getSubimage(i*36, 0, 36, 37);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][DIE][0][i] = temp;
			}
			
			for(int i = 0; i < 9; i++) {
				temp = image.getSubimage(i*36, 37, 36, 37);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][DIE][1][i] = temp;
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		playerAnimation [RAGAZZA][DIE] = new BufferedImage[2][9];
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/deadGirl.png"));
			
			for(int i = 0; i < 9; i++) {
				temp = image.getSubimage(i*43, 0, 43, 38);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][DIE][0][i] = temp;
			}
			
			for(int i = 0; i < 9; i++) {
				temp = image.getSubimage(i*43, 38, 43, 38);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][DIE][1][i] = temp;
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	private void loadAttackImages(BufferedImage image, BufferedImage temp) {
		playerAnimation [RAGAZZO][ATTACK] = new BufferedImage[4][5];		//ci sono 4 direzioni, ogni direzione ha 5 immagini	
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/ThrowBoy.png"));
			
			for(int i = 0; i < 5; i++) {
				temp = image.getSubimage(i*26, 0, 26, 40);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][ATTACK][DOWN][i] = temp;
			}
			
			for(int i = 0; i < 5; i++) {
				temp = image.getSubimage(i*35, 40, 35, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][ATTACK][RIGHT][i] = temp;
			}
			
			for(int i = 0; i < 5; i++) {
				temp = image.getSubimage(i*35, 40 + 33, 35, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][ATTACK][LEFT][i] = temp;
			}
			
			for(int i = 0; i < 5; i++) {
				temp = image.getSubimage(i*24, 40 + 33 + 33, 24, 36);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][ATTACK][UP][i] = temp;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		playerAnimation [RAGAZZA][ATTACK] = new BufferedImage[4][5];
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/ThrowGirl.png"));
			
			for(int i = 0; i < 5; i++) {
				temp = image.getSubimage(i*25, 0, 25, 41);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][ATTACK][DOWN][i] = temp;
			}
			
			for(int i = 0; i < 5; i++) {
				temp = image.getSubimage(i*37, 41, 37, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][ATTACK][RIGHT][i] = temp;
			}
			
			for(int i = 0; i < 5; i++) {
				temp = image.getSubimage(i*37, 41 + 32, 37, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][ATTACK][LEFT][i] = temp;
			}
			
			for(int i = 0; i < 5; i++) {
				temp = image.getSubimage(i*29, 41 + 32 + 32, 29, 36);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZA][ATTACK][UP][i] = temp;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void loadIdleImages(BufferedImage image, BufferedImage temp) {
		playerAnimation [RAGAZZO][IDLE] = new BufferedImage[4][4];		//ci sono 4 direzioni, ogni direzione ha 4 immagini
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/idleSpriteBoy.png"));
			
			for(int i = 0; i < 4; i++) {
				temp = image.getSubimage(i*24, 0, 24, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][IDLE][DOWN][i] = temp;
			}
			
			for(int i = 0; i < 4; i++) {
				temp = image.getSubimage(i*21, 33, 21, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][IDLE][RIGHT][i] = temp;
			}
			
			for(int i = 0; i < 4; i++) {
				temp = image.getSubimage(i*21, 33 + 32, 21, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation [RAGAZZO][IDLE][LEFT][i] = temp;
			}
			
			for(int i = 0; i < 4; i++) {
				temp = image.getSubimage(i*22, 33 + 32 + 32, 22, 32);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZO][IDLE][UP][i] = temp;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		playerAnimation[RAGAZZA][IDLE] = new BufferedImage[4][4];
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
		}

	}
		
	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		playerAnimation[RAGAZZO][RUN] = new BufferedImage[4][6];		//ci sono 4 direzioni, ogni direzione ha 6 immagini

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/walkingSpritesBoyCorr.png"));
			
			for(int i = 0; i < 6; i++) {
				temp = image.getSubimage(i*23, 0, 23, 35);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZO][RUN][DOWN][i] = temp;
			}
			
			for(int i = 0; i < 6; i++) {
				temp = image.getSubimage(i*24, 35, 24, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZO][RUN][RIGHT][i] = temp;
			}
			
			for(int i = 0; i < 6; i++) {
				temp = image.getSubimage(i*24, 35 + 33, 24, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZO][RUN][LEFT][i] = temp;
			}
			
			for(int i = 0; i < 6; i++) {
				temp = image.getSubimage(i*26, 35 + 33 + 33, 26, 36);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZO][RUN][UP][i] = temp;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		playerAnimation[RAGAZZA][RUN] = new BufferedImage[4][6];		//ci sono 4 direzioni, ogni direzione ha 6 immagini
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/MoveGirl.png"));
			
			for(int i = 0; i < 6; i++) {
				temp = image.getSubimage(i*26, 0, 26, 34);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZA][RUN][DOWN][i] = temp;
			}
			
			for(int i = 0; i < 6; i++) {
				temp = image.getSubimage(i*24, 34, 24, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZA][RUN][RIGHT][i] = temp;
			}
			
			for(int i = 0; i < 6; i++) {
				temp = image.getSubimage(i*24, 34 + 33, 24, 33);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZA][RUN][LEFT][i] = temp;
			}
			
			for(int i = 0; i < 6; i++) {
				temp = image.getSubimage(i*31, 34 + 33 + 33, 31, 34);
				temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.2f*GamePanel.SCALE, temp.getHeight()*1.2f*GamePanel.SCALE);
				playerAnimation[RAGAZZA][RUN][UP][i] = temp;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void setMapPositionForSort(int posizPlayerX, int posizPlayerY) {
		xPosMapForSort = posizPlayerX - xOffset;
		yPosMapForSort = posizPlayerY - yOffset;
	}

	public static int getXOffset() {
		return xOffset;
	}
	
	public static int getYOffset() {
		return yOffset;
	}
	
	@Override
	public void draw(Graphics2D g2, int x, int y) {	
		animationCounter++;

		setAction();		
		setDirection();
		
		if (animationCounter > animationSpeed) {
			numSprite++;
			// perchè la prima slide si deve vedere solo una volta
			setParryAniIndex();
			
			if(numSprite >= getAnimationLenght() && currentAction != PARRY) {
				numSprite = 0;	
				endAttackAnimation = true;
			}				
			animationCounter = 0;
		}
		
		//alcune slide sono un pò sfasate
		fixOutPhasedSlides();	
		
		g2.drawImage(playerAnimation[avatarType][currentAction][currentDirection][numSprite], xOnScreen, yOnScreen, null);
		//se una slide era sfasata, resettiamo il valore dell'offset
		xOnScreen = xOnScreenOriginal;
		
		//disegna l'area dove viene disegnato il personaggio
		g2.setColor(Color.red);
		g2.drawRect(xOnScreen, yOnScreen, 42, 54);
		
		//disegna la hitbox
		g2.setColor(Color.black);
		g2.drawRect(xOnScreen + xOffset,
					yOnScreen + yOffset, 
					view.getController().getPlay().getPlayer().getHitbox().width, 
					view.getController().getPlay().getPlayer().getHitbox().height);
	}

	// in questo modo solleva lo scudo e lo tiene alzato finchè il giocatore non toglie il dito
	private void setParryAniIndex() {
		if(currentAction == PARRY) {
			if(firstParry) {
				numSprite--;
				firstParry = false;
			}
			else {
				numSprite = getAnimationLenght() - 1;
			}
		}
		
	}

	public void setAction() {
		// se il giocatore preme il mouse, l'animazione di attacco continua fino alla fine
		// anche se il giocatore ha lasciato il mouse
		if(view.getController().getPlay().getPlayer().isAttacking() && endAttackAnimation) {
			currentAction = ATTACK;
			animationSpeed = 12;
			endAttackAnimation = false;
			view.playSE(SoundManager.COLPO);
		}
		
		else if(view.getController().getPlay().getPlayer().isMoving() && endAttackAnimation) {
			currentAction = RUN;
			animationSpeed = 20;
		}
		
		else if(view.getController().getPlay().getPlayer().isParring() && endAttackAnimation) {
			currentAction = PARRY;
			animationSpeed = 10;
		}
		
		else if(view.getController().getPlay().getPlayer().isThrowing() && endAttackAnimation) {
			currentAction = THROW;
			animationSpeed = 18;
		}
		
		else if(endAttackAnimation){
			currentAction = IDLE;
			animationSpeed = 20;
		}
				
		//questo ci serve perchè così quando cambia azione si resetta il contatore delle sprite
		if(currentAction != previousAction) {
			numSprite = 0;
			previousAction = currentAction;
			}
	}
	
	public void setDirection() {
		if(view.getController().getPlay().getPlayer().isLeft())
			currentDirection = LEFT;
		
		else if(view.getController().getPlay().getPlayer().isRight())
			currentDirection = RIGHT;
		
		else if(view.getController().getPlay().getPlayer().isDown())
			currentDirection = DOWN;
		
		else if(view.getController().getPlay().getPlayer().isUp())
			currentDirection = UP;	
	}
	
	public int getAnimationLenght() {
		if(currentAction == IDLE)
			return 4;
		else if(currentAction == RUN)
			return 6;
		else if(currentAction == ATTACK)
			return 5;
		else if(currentAction == DIE)
			return 9;
		else if(currentAction == PARRY)
			return 2;
		else if(currentAction == THROW)
			return 2;
			
		return 0;
	}
	
	private void fixOutPhasedSlides() {
		if(currentAction == ATTACK && currentDirection == LEFT)
			xOnScreen -= (int)GamePanel.SCALE*20;	
		
		else if(avatarType == RAGAZZA && currentAction == RUN && currentDirection == UP) 
			xOnScreen -= (int)GamePanel.SCALE*10;
		
		else if(avatarType == RAGAZZO && currentAction == THROW && currentDirection == LEFT)
			xOnScreen -= (int)GamePanel.SCALE*10;
	}
	
	//for avatar menu
	public static BufferedImage getAnimationForMenu(int avatar, int action, int direction, int index) {
		if (avatar == RAGAZZA)
			return playerAnimation [RAGAZZA][action][direction][index];
		else
			return  playerAnimation [RAGAZZO][action][direction][index];
	}

	public void setAvatarType(int type) {
		if (type == RAGAZZA)
			avatarType = RAGAZZA;
		else 
			avatarType = RAGAZZO;
	}
	
	public void resetParry() {
		firstParry = true;
	}
	
	public void resetAnimation() {		//potrebbe servire
		animationCounter = 0;
		numSprite = 0;
		currentAction = IDLE;
		currentDirection = DOWN;
		endAttackAnimation = true;
	//	endThrowAnimation = true;
		firstParry = false;
	}


}













