package view.playState.entityView;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import view.IView;
import view.main.GamePanel;
import view.playState.drawOrder.SortableElement;

public abstract class EntityView extends SortableElement {

	protected IView view;
	//tipo,azione,direzione,sprite
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

	//per distinguere tra npc e enemy
	protected String type;

	//quando avremo finito con i rettangoli delle hitbox, possiamo mettere qui il metodo draw, che tanto è uguale per tutti
	public EntityView(IView v, int index) {
		typeElemtToSort = 4;		//elemento animato, da disegnare sopra la mappa
		view = v;
		this.index = index;
	}
	
	//controlla se l'oggetto è abbastanza vicino al giocatore da poter apparire sullo schermo
	public boolean isInGameFrame(int posizPlayerX, int posizPlayerY, String type) {
		//prende la hitbox dell'entità
		Rectangle hitboxController = null;
		if(type.compareTo("npc") == 0)
			hitboxController = IView.fromHitboxToRectangle(view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getHitbox()); 
		else
			 hitboxController = IView.fromHitboxToRectangle(view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getEnemy().get(index).getHitbox()); 
	
		//controlla la distanza dal giocatore
		if(Math.abs(hitboxController.x - posizPlayerX)  > GamePanel.GAME_WIDTH/2)
			return false;
		if(Math.abs(hitboxController.y - posizPlayerY)  > GamePanel.GAME_HEIGHT/2)
			return false;
		
		//in caso affermativo, aggiorna i dati sulla sua posizione e ci aggiunge l'offset
		setMapPositionForSort(hitboxController);
		return true;
	}
	
	//ogni ente deve capire dove si trova per poter essere ordinato e disegnato
	public void setMapPositionForSort(Rectangle hitboxEntity) {
		xPosMapForSort = hitboxEntity.x - xOffset;
		yPosMapForSort = hitboxEntity.y - yOffset;
	}
	
	//vede nel controller la direzione dell'entità e cambia currentDirection
	protected void setDirection(EntityView entity) {
		if(entity.type.compareTo("npc") == 0)
			currentDirection = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getCurrentDirection();
		else
			currentDirection = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getEnemy().get(index).getCurrentDirection();

	}
	
	//vede nel controller cosa fa l'entità e cambia currentAction
	protected void setAction(EntityView entity) {
		if(entity.type.compareTo("npc") == 0)
			currentAction = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getNPC().get(index).getCurrentAction();
		else
			currentAction = view.getController().getPlay().getRoom(view.getCurrentRoomIndex()).getEnemy().get(index).getCurrentAction();
		
		//questo ci serve perchè così quando cambia azione si resetta il contatore delle sprite
		if(currentAction != previousAction) {
			numSprite = 0;
			previousAction = currentAction;
		}
		
	}

	public void decreaseIndexInList() {
		this.index--;
		
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

	public int getxPosOnScreen() {
		return xPosOnScreen;
	}

	public int getyPosOnScreen() {
		return yPosOnScreen;
	}
		
}
