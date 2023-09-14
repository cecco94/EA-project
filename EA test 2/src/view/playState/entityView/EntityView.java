package view.playState.entityView;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import view.IView;
import view.main.GamePanel;
import view.playState.drawOrder.SortableElement;

public abstract class EntityView extends SortableElement {

	protected IView view;
	protected BufferedImage[][][][] animation;
	//questo indice deve essere uguale nella lista di entità del view e del controller
	protected int index;
	//campo 0 = tipo.. per es gatto bianco/nero, primo campo = azione, secondo = direzione, terzo = immagine
	// ogni oggetto avrebbe un array di immagini.. non possiamo caricare le immagini una volta dentro la classe indicandoli come statici?
	// per fare ciò ci servirebbe un costruttore statico, che in java non esiste.. c'è un modo elegante per farlo?
	
	public final static int IDLE = 0, MOVE = 1, ATTACK = 2, PARRY = 3, THROW = 4, DIE = 5, SLEEP = 6;
	public final static int DOWN = 0, RIGHT = 1, LEFT = 2, UP = 3;
	protected int currentAction = IDLE;
	protected int previousAction = MOVE;
	protected int currentDirection = DOWN;
	
	//per l'animazione
	protected int animationCounter = 0;
	protected int animationSpeed = 20;		//dopo che ha bevuto il caffè diventa 1
	protected int numSprite = 0;
	
	//ogni ente avrà una differenza tra dove si trova la hitbox e dove parte l'immagine
	protected int xOffset, yOffset;
	
	//posizione dell'entità nello schermo
	protected int xPosOnScreen, yPosOnScreen;

	
	public int getxPosOnScreen() {
		return xPosOnScreen;
	}

	public int getyPosOnScreen() {
		return yPosOnScreen;
	}

	//quando avremo finito con i rettangoli delle hitbox, possiamo mettere qui il metodo draw, che tanto è uguale per tutti
	public EntityView(IView v, int index) {
		typeElemtToSort = 4;		//elemento animato, da disegnare sopra la mappa
		view = v;
		this.index = index;
	}
	
	public boolean isInGameFrame(int posizPlayerX, int posizPlayerY, String type) {
		//controlla se l'oggetto è abbastanza vicino al giocatore da poter apparire sullo schermo
		Rectangle hitboxController = null;
		
		if(type.compareTo("npc") == 0)
			hitboxController = IView.fromHitboxToRectangle(view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getHitbox()); 
		
		else
			 hitboxController = IView.fromHitboxToRectangle(view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getEnemy().get(index).getHitbox()); 
	
		if(Math.abs(hitboxController.x - posizPlayerX)  > GamePanel.GAME_WIDTH/2)
			return false;
		if(Math.abs(hitboxController.y - posizPlayerY)  > GamePanel.GAME_HEIGHT/2)
			return false;
		//in caso affermativo, aggiorna i dati sulla sua posizione e ci aggiunge l'offset
		setMapPositionForSort(hitboxController);
		return true;
	}
	
	//ogni ente mobile deve capire dove si trova il personaggio per poterlo ordinare e disegnare
	public void setMapPositionForSort(Rectangle hitboxEntity) {
		xPosMapForSort = hitboxEntity.x - xOffset;
		yPosMapForSort = hitboxEntity.y - yOffset;
		
	}
	
	public static int getRun() {
		return MOVE;
	}

	public static int getIDLE() {
		return IDLE;
	}

	public static int getDOWN() {
		return DOWN;
	}
	
	protected void setDirection(boolean isNPC) {
	//vede nel controller la direzione del gatto e cambia currentDirection
		if(isNPC)
			currentDirection = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getCurrentDirection();
		
		else
			currentDirection = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getEnemy().get(index).getCurrentDirection();
		
		// questo ci serve perchè l'ordine delle sprite nell'immagine è down, left, right, up
		if(currentDirection == RIGHT)
			currentDirection = 1;
		
		else if(currentDirection == LEFT)
			currentDirection = 2;
		
		else if(currentDirection == DOWN)
			currentDirection = 0;
		
		else if(currentDirection == UP)
			currentDirection = 3;

	}
	
	protected void setAction(boolean isNPC) {
		//vede nel controller cosa fa il gatto e cambia currentAction
		if(isNPC)
			currentAction = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getCurrentAction();
		
		else
			currentAction = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getEnemy().get(index).getCurrentAction();
		
		//questo ci serve perchè così quando cambia azione si resetta il contatore delle sprite
		if(currentAction != previousAction) {
			numSprite = 0;
			previousAction = currentAction;
		}
		
	}
	
	

		
}
