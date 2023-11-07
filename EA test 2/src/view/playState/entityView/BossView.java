package view.playState.entityView;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.main.Gamestate;
import view.IView;
import view.SoundManager;
import view.ViewUtils;
import view.main.GamePanel;

public class BossView extends EntityView {

	private Rectangle lifeRect;
	protected int dialogueIndex;
	protected String[] dialogues;

	public BossView(IView v, int index) {
		super(v, index);
		this.type = "enemy";
		
		loadImages();
		
		xOffset = (int)(0*GamePanel.SCALE); 
		yOffset = (int)(0*GamePanel.SCALE); 
		animationSpeed = 30;
		
		int lifeRectWidth = (int)(300*GamePanel.SCALE);
		int maxlifeHeight = (int)(5*GamePanel.SCALE);
		int maxlifeX = GamePanel.GAME_WIDTH/2 - lifeRectWidth/2;
		int maxlifeY = GamePanel.GAME_HEIGHT/4 - (int)(60*GamePanel.SCALE);
		lifeRect = new Rectangle(maxlifeX, maxlifeY, lifeRectWidth, maxlifeHeight);
		
		setDialogues();
		
	}

	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		animation = new BufferedImage[1][5][][];		//data la numerazione delle azioni, l'array ha dimensione 5 invece che 4	
		
		animation[0][MOVE] = new BufferedImage[4][3];	//ci sono 4 direzioni, ogni direzione ha 3 immagini
		animation[0][IDLE] = new BufferedImage[4][1];	//ci sono 4 direzioni, ogni direzione ha 1 immagine
		animation[0][THROW] = new BufferedImage[4][3];
		animation[0][ATTACK] = new BufferedImage[4][5];
		
		loadRunImages(image, temp);	
		loadIdleImages();
		loadAttackImages(image, temp);
		loadThrowImages(image, temp);
		
	}

	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/bossTrasparente.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		temp = image.getSubimage(0, 35, 28, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][MOVE][DOWN][0] = temp;
		
		temp = image.getSubimage(30, 35, 29, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][MOVE][DOWN][1] = temp;
		
		temp = image.getSubimage(61, 35, 28, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][MOVE][DOWN][2] = temp;
			
		
		for(int img = 0; img < 3; img++) {
			temp = image.getSubimage(img*21, 70, 21, 34);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
			animation[0][MOVE][RIGHT][img] = temp;
			}
		
		for(int img = 0; img < 3; img++) {
			temp = image.getSubimage(img*22, 104, 22, 34);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
			animation[0][MOVE][LEFT][img] = temp;
			}
		
		
		
		for(int img = 0; img < 3; img++) {
			temp = image.getSubimage(img*30, 138, 30, 35);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
			animation[0][MOVE][UP][img] = temp;
			}	
		} 


	private void loadIdleImages() {
		animation[0][IDLE][DOWN][0] = animation[0][MOVE][DOWN][1];
		animation[0][IDLE][RIGHT][0] = animation[0][MOVE][RIGHT][1];
		animation[0][IDLE][LEFT][0] = animation[0][MOVE][LEFT][1];
		animation[0][IDLE][UP][0] = animation[0][MOVE][UP][1];
				
	}
	
	
	private void loadAttackImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/bossTrasparente.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		temp = image.getSubimage(0, 173, 27, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][DOWN][0] = temp;
		
		temp = image.getSubimage(30, 173, 32, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][DOWN][1] = temp;
		
		temp = image.getSubimage(65, 173, 35, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][DOWN][2] = temp;
		
		temp = image.getSubimage(102, 173, 37, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][DOWN][3] = temp;
		
		temp = image.getSubimage(141, 173, 29, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][DOWN][4] = temp;
		

		for(int img = 0; img < 5; img++) {
			temp = image.getSubimage(img*34, 208, 34, 35);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
			animation[0][ATTACK][RIGHT][img] = temp;
		}
		
		
		for(int img = 0; img < 5; img++) {
			temp = image.getSubimage(img*28, 244, 28, 34);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
			animation[0][ATTACK][LEFT][img] = temp;
		}
		
		
		temp = image.getSubimage(0, 278, 27, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][UP][0] = temp;
		
		temp = image.getSubimage(33, 278, 29, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][UP][1] = temp;
		
		temp = image.getSubimage(65, 278, 35, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][UP][2] = temp;
		
		temp = image.getSubimage(102, 278, 37, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][UP][3] = temp;
		
		temp = image.getSubimage(141, 278, 29, 35);
		temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
		animation[0][ATTACK][UP][4] = temp;
		
		
	}
	
	private void loadThrowImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/bossTrasparente.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int img = 0; img < 3; img++) {
			temp = image.getSubimage(img*37, 313, 37, 35);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
			animation[0][THROW][DOWN][img] = temp;
		}
		
		for(int img = 0; img < 3; img++) {
			temp = image.getSubimage(img*24, 313 + 35, 24, 35);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
			animation[0][THROW][RIGHT][img] = temp;
		}
		
		for(int img = 0; img < 3; img++) {
			temp = image.getSubimage(img*24, 348 + 35, 24, 35);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
			animation[0][THROW][LEFT][img] = temp;
		}
		
		for(int img = 0; img < 3; img++) {
			temp = image.getSubimage(img*37, 348 + 35 + 35, 37, 35);
			temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
			animation[0][THROW][UP][img] = temp;
		}
	}
		
	@Override
	public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
		if(view.getController().isEnemyHitted(index))
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
		animationCounter++;
		setAction(this);
		setDirection(this);
		
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
		xPosOnScreen = PlayerView.xOnScreen - distanceX - xOffset + PlayerView.getXOffset();
		yPosOnScreen = PlayerView.yOnScreen - distanceY - yOffset + PlayerView.getYOffset();
		
		try {
			g2.drawImage(animation[0][currentAction][currentDirection][numSprite], xPosOnScreen, yPosOnScreen, null);
			if(Gamestate.state != Gamestate.BOSS_CUTSCENE)
				drawLife(g2);			

		}
		catch (ArrayIndexOutOfBoundsException a) {
			a.printStackTrace();
			System.out.println("azione " + currentAction + " direzione " + currentDirection+ " sprite " + numSprite);
		}
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
	}

	private void drawLife(Graphics2D g2) {
		int life = view.getController().getEnemyLife(index);
		view.getPlay().getUI().drawBossLife(g2, lifeRect, life);
	}

	private int getAnimationLenght() {
		if(currentAction == IDLE)
			return 1;
		
		else if(currentAction == MOVE)
			return 3;
		
		else if(currentAction == THROW)
			return 3;
		
		else if(currentAction == ATTACK)
			return 5;
		
		else
			return 0;
	}
	
	public String getCurrentDialogue() {
		return dialogues[dialogueIndex];
	}
	
	// dopo che ha detto una frase, va alla frase successiva, se sono finite le frasi esce dallo stato dialogue
	public void nextDialogueLine() {
		view.playSE(SoundManager.CAFFE);

		dialogueIndex++;
		if(dialogueIndex >= dialogues.length) {
			dialogueIndex--;
			view.changeGameState(Gamestate.PLAYING);
			view.getController().resetPlayerBooleans();
			view.stopMusic();
			view.playMusic(SoundManager.BOSS_SECOND_PHASE);
		}
	}

	protected void setDialogues() {
		dialogues = new String[4];
		dialogues[0] = "Ti stavo aspettando";
		dialogues[1] = "Sono il famigerato Professor Luke Crickets";
		dialogues[2] = "Pensi veramente di essere in grado di sconfiggermi?";
		dialogues[3] = "Prima dovrai assaggiare la potenza del mio framework!";
		
	}
}
