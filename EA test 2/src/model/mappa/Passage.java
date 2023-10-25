package model.mappa;

import controller.playState.Hitbox;
import model.IModel;

public class Passage {

	private IModel model;
	
	private int prevX, prevY;	//posizione del passaggio
	private int nextX, nextY;	//nuova posizione del giocatore nella nuova stanza 
	private int cfuRequired;
	
	private Hitbox borders;	//bordo per passaggio, per capire se il giocatore è sopra al passaggio
	private Rooms nextRoom;

	//se la porta è chiusa, manda un messaggio mostrato dalla UI
	private String message = "";
	
	
	public Passage(IModel m, int cfuRequired, String s,int pX, int pY, int width, int height, int nX, int nY, Rooms newRoom) {

		model = m;
		message = s;
		
		prevX = pX*IModel.getTileSize();
		prevY = pY*IModel.getTileSize();
		
		nextX = nX*IModel.getTileSize();
		nextY = nY*IModel.getTileSize();
		
		nextRoom = newRoom;			//il passaggio è grande quanto un quadratino o una frazione di quadratino
		borders = new Hitbox(prevX, prevY, (int)(IModel.getGameScale()*width), (int)(IModel.getGameScale()*height));
		
		this.cfuRequired = cfuRequired;
		
	}
	
	public void handleRoomChanging(int playerCfu) {
		if(playerCfu >= cfuRequired) {
			//resetta le azioni del personaggio quando comabia stanza
			model.getController().getPlay().getPlayer().setPlayerActionInNewRoom();
			model.getController().getPlay().handlePassageTransition();
			
		}
		else 	
			//se non ha abbastanza cfu, il passaggio stesso restituisce una stringa che viene stampata a video
			model.getView().showMessageInUI(message);	
		
	}
	
	//controlla se il personaggio si trova sul passaggio
	public boolean checkPlayer(Hitbox hitbox) {
		return hitbox.intersects(borders);
	}

	public Rooms getNextRoom() {
		return nextRoom;
	}

	public int getNextX() {
		return nextX;
	}

	public int getNextY() {
		return nextY;
	}
	
	public String toString() {
		return 	"passaggio ( " + prevX + ", " + prevY + ", " + borders.width + ", " + borders.height + " )";
	}
	
	public String getMessage() {
		return message;
	}
}
