package model.mappa;

import controller.playState.Hitbox;
import model.IModel;

public class Passage {

	private int prevX, prevY;	//posizione del passaggio
	private int nextX, nextY;	//nuova posizione del giocatore nella nuova stanza 
	private Hitbox borders;	//bordo per passaggio, per capire se il giocatore è sopra al passaggio
	//se la porta è chiusa, manda un messaggio mostrato dalla UI
	private boolean open;
	private String message = "";
	
	private Rooms nextRoom;
	
	
	public Passage(boolean pass, String s,int pX, int pY, int width, int height, int nX, int nY, Rooms newRoom) {
		setOpen(pass);
		message = s;
		
		prevX = pX*IModel.getTileSize();
		prevY = pY*IModel.getTileSize();
		
		nextX = nX*IModel.getTileSize();
		nextY = nY*IModel.getTileSize();
		
		nextRoom = newRoom;			//il passaggio è grande quanto un quadratino o una frazione di quadratino
		borders = new Hitbox(prevX, prevY, (int)(IModel.getGameScale()*width), (int)(IModel.getGameScale()*height));
	}
	
	//controlla se il personaggio si trova sul passaggio
	public boolean checkPlayer(Hitbox hitbox) {
		return hitbox.intersects(borders);
	}

	public Rooms getNewMap() {
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

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public String getMessage() {
		return message;
	}
}
