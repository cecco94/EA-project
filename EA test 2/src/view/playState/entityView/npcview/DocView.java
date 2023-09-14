package view.playState.entityView.npcview;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.main.GamePanel;
import view.playState.entityView.PlayerView;

public class DocView extends NPCView {
	
	
	public DocView(IView v, int index) {
		super(v, index);
		
		loadImages();	
		
		xOffset = (int)(0*GamePanel.SCALE); 
		yOffset = (int)(6*GamePanel.SCALE); 
		animationSpeed = 40;

	}
	
	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		animation = new BufferedImage[1][2][][];		
		
		animation[0][MOVE] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		animation[0][IDLE] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 1 immagine

		loadRunImages(image, temp);	
		loadIdleImages(image, temp);
		
	}

	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/ProfLab.png"));
						
			for(int direction = 0; direction < 4 ; direction++) {
				for(int img = 0; img < 3; img++) {
					temp = image.getSubimage(img*16, direction*24, 16, 24);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.8f*GamePanel.SCALE, temp.getHeight()*1.8f*GamePanel.SCALE);
					animation[0][MOVE][direction][img] = temp;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void loadIdleImages(BufferedImage image, BufferedImage temp) {
		
		animation[0][IDLE][DOWN][0] = animation[0][MOVE][DOWN][0];
		animation[0][IDLE][RIGHT][0] = animation[0][MOVE][RIGHT][0];
		animation[0][IDLE][LEFT][0] = animation[0][MOVE][LEFT][0];
		animation[0][IDLE][UP][0] = animation[0][MOVE][UP][0];
		
	}

	protected void setDialogues() {
		dialogues = new String[6];
		dialogues[0] = "hey tu, ti prego aiutami! \n non sembri una matricola scanzafatiche come le altre";
		dialogues[1] = "sono un assistente di Paul Bags, \n il professore di robotica della facoltà";
		dialogues[2] = "stavo modificando un turtlebot, ma è impazzito \n e ora vuole uccidere tutti gli umani!";
		dialogues[3] = "so che sembra un film, ma chi scrive \n i miei dialoghi non ha molta fantasia...";
		dialogues[4] = "se riesci a fermarlo, ti aiuterò con l'esame sistemi di controllo";
		dialogues[5] = "buona fortuna";
		
	}

	@Override
	public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
		animationCounter++;
		setAction(true);
		setDirection(true);
		
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
			g2.drawRect(xPosOnScreen, yPosOnScreen, (int)(16*1.8*GamePanel.SCALE), (int)(24*1.8*GamePanel.SCALE));
			
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
		
		else if(currentAction == MOVE)
			return 3;
		
		return 0;
	}

	

}
