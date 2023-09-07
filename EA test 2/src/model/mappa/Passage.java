package model.mappa;



import controller.playState.Hitbox;
import view.main.GamePanel;

public class Passage {

	private int prevX, prevY;	//posizione del passaggio
	private int nextX, nextY;	//nuova posizione del giocatore nella nuova stanza 
	private Hitbox borders;	//bordo per passaggio, per capire se il giocatore è sopra al passaggio
	//se la porta è chiusa, manda un messaggio mostrato dalla UI
	private boolean open;
	String message = "";
	
	private Rooms nextRoom;
	
	
	public Passage(boolean pass, String s,int pX, int pY, int width, int height, int nX, int nY, Rooms newRoom) {
		setOpen(pass);
		message = s;
		
		prevX = pX*GamePanel.TILES_SIZE;
		prevY = pY*GamePanel.TILES_SIZE;
		
		nextX = nX*GamePanel.TILES_SIZE;
		nextY = nY*GamePanel.TILES_SIZE;
		
		nextRoom = newRoom;			//il passaggio è grande quanto un quadratino o una frazione di quadratino
		borders = new Hitbox(prevX, prevY, (int)(GamePanel.SCALE*width), (int)(GamePanel.SCALE*height));
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
