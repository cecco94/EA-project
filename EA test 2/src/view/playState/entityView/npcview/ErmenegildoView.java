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

public class ErmenegildoView extends EntityView {
				
	protected static BufferedImage[][][][] animation;	
	
	public ErmenegildoView(IView v, int index) {
		
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
		
		ErmenegildoView.animation = new BufferedImage[1][2][][];		//un tipo di vecchio, due azioni

		ErmenegildoView.animation[0][IDLE] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		ErmenegildoView.animation[0][RUN] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		
		loadRunImages(image, temp);
		loadIdleImages();
	}

	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/fuoricorso.png"));
						
			int counter = 0;
			for(int direction = 0; direction < 4; direction++) {
				for(int index = 0; index < 3; index++) {
					temp = image.getSubimage(index*16 + 16*counter, 0, 16, 23);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
					ErmenegildoView.animation[0][RUN][direction][index] = temp;
				}
				counter += 3;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	
	}	
	
	public void loadIdleImages(){
		//prendi le immagini già caricate, prendi la seconda ogni tre		
		for(int direction = 0;  direction < 4; direction++)
			ErmenegildoView.animation[0][IDLE][direction][0] = ErmenegildoView.animation[0][RUN][direction][1]; 
		
	}
	
	private void setDialogs() {	
		dialogues = new String[11];
		dialogues[0] = "ciao, sei una matricola?";
		dialogues[1] = "che invidia, io sono qui da un po'...\n il prof Luke Crickets mi ha bocciato 100 volte";
		dialogues[2] = "lascia che ti dia qualche dritta";
		dialogues[3] = "per laurearti, ti servono 180 CFU, che puoi trovare vicino ai computer";
		
		dialogues[4] = "non ti fare influenzare dagli studenti nullafacenti, \n abbassano la tua concentrazione!";
		dialogues[5] = "siano maledetti.. \n tutti i loro giochi con le carte mi hanno fatto perdere tanto tempo";
		dialogues[6] = "usa il computer e gli appunti che trovi in giro per difenderti";

		dialogues[7] = "il caffè è un tuo alleato, \n prendi ogni tazzina disponibile se ti senti stanco";
		dialogues[8] = "conosco qualche scorciatoia per racimolare CFU più in fretta:";
		dialogues[9] = "parla con i tuoi colleghi, \n possono aiutarti negli esami in cambio di qualche favore";
		dialogues[10] = "se ti serve qualche altro suggerimento, mi trovi in biblioteca. \n Qui sei al sicuro, i nullafacenti non entrano mai";
		
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
			g2.drawImage(ErmenegildoView.animation[0][currentAction][currentDirection][numSprite], xPosOnScreen, yPosOnScreen, null);
			
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
	
	private void setAction() {
	//vede nel controller cosa fa il gatto e cambia currentAction
			currentAction = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getCurrentAction();
			
			//questo ci serve perchè così quando cambia azione si resetta il contatore delle sprite
			if(currentAction != previousAction) {
				numSprite = 0;
				previousAction = currentAction;
			}
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
	
	private int getAnimationLenght() {
		if(currentAction == IDLE)
			return 1;
		
		else if(currentAction == RUN)
			return 3;
		
		return 0;
	}
	
}
