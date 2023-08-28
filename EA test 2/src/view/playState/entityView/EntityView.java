package view.playState.entityView;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import model.mappa.Stanze;
import view.IView;
import view.main.GamePanel;
import view.playState.drawOrder.SortableElement;

public abstract class EntityView extends SortableElement {

	protected IView view;
	
	//questo indice deve essere uguale nella lista di entità del view e del controller
	protected int index;
//campo 0 = tipo.. per es gatto bianco/nero, primo campo = azione, secondo = direzione, terzo = immagine
// ogni oggetto avrebbe un array di immagini.. non possiamo caricare le immagini una volta dentro la classe indicandoli come statici?
// per fare ciò ci servirebbe un costruttore statico, che in java non esiste.. c'è un modo elegante per farlo?
	protected static BufferedImage[][][][] animation;	
	public final static int IDLE = 0, RUN = 1, ATTACK = 2, PARRY = 3,THROW = 4,  DIE = 5, SLEEP = 6;
	public final static int DOWN = 0, RIGHT = 1, LEFT = 2, UP = 3;
	
	protected int currentAction = IDLE;
	protected int previousAction = RUN;
	protected int currentDirection = DOWN;
	
	//per l'animazione
	protected int animationCounter = 0;
	protected int animationSpeed = 20;		//dopo che ha bevuto il caffè diventa 1
	protected int numSprite = 0;
	
	//ogni ente avrà una differenza tra dove si trova la hitbox e dove parte l'immagine
	protected int xOffset, yOffset;
	
	public boolean isInGameFrame(int posizPlayerX, int posizPlayerY) {
		//controlla se l'oggetto è abbastanza vicino al giocatore da poter apparire sullo schermo
		Rectangle hitboxController = view.getController().getPlay().getRoom(Stanze.stanzaAttuale.indiceMappa).getNPC().get(index).getHitbox();
		
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
		return RUN;
	}

	public static int getIDLE() {
		return IDLE;
	}

	public static int getDOWN() {
		return DOWN;
	}
	
}
