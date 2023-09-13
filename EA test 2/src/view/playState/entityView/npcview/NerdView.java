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

public class NerdView extends EntityView {
	
	protected BufferedImage[][][][] animation;

	public NerdView(IView v, int index) {
		super(v, index);
		
		setDialogs();
		loadImages();	
		
		xOffset = (int)(0*GamePanel.SCALE); //3;
		yOffset = (int)(0*GamePanel.SCALE); //3;
		animationSpeed = 40;
		
		
	}

	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		animation = new BufferedImage[1][2][][];		//un tipo di vecchio, due azioni

		animation[0][IDLE] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		animation[0][MOVE] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		
		loadRunImages(image, temp);
		loadIdleImages();
		
	}

	private void loadIdleImages() {
		//prendi le immagini già caricate, prendi la seconda ogni tre		
				for(int direction = 0;  direction < 4; direction++)
					animation[0][IDLE][direction][0] = animation[0][MOVE][direction][1]; 
		
	}

	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/NerdGiusto.png"));
						
			int counter = 0;
			for(int direction = 0; direction < 4; direction++) {
				for(int index = 0; index < 3; index++) {
					temp = image.getSubimage(index*16 + 16*counter, 0, 16, 27);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
					animation[0][MOVE][direction][index] = temp;
				}
				counter += 3;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setDialogs() {
		dialogues = new String[1];
		dialogues[0] = "ciao";
		
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
		
		else if(currentAction == MOVE)
			return 3;
		
		return 0;
	}

}
