package model.mappa;

import java.awt.Rectangle;

import view.main.GamePanel;

public class Passaggio {

	private int prevX, prevY;	//posizione del passaggio
	private int nextX, nextY;	//new position of the player in the new room
	private Rectangle borders;
	//se la porta è chiusa, manda un messaggio mostrato dalla UI
	private boolean open;
	String messaggio = "";
	
	private Stanze nuovaStanza;
	
	public Passaggio(boolean pass, String s,int pX, int pY, int width, int height, int nX, int nY, Stanze newRoom) {
		setOpen(pass);
		messaggio = s;
		
		prevX = pX*GamePanel.TILES_SIZE;
		prevY = pY*GamePanel.TILES_SIZE;
		
		nextX = nX*GamePanel.TILES_SIZE;
		nextY = nY*GamePanel.TILES_SIZE;
		
		nuovaStanza = newRoom;			//il passaggio è grande quanto un quadratino o una frazione di quadratino
		borders = new Rectangle(prevX, prevY, (int)(GamePanel.SCALE*width), (int)(GamePanel.SCALE*height));
	}
	
	//controlla se il personaggio si trova sul passaggio
	public boolean checkPlayer(Rectangle hitboxPlayer) {
		return hitboxPlayer.intersects(borders);
	}

	public Stanze getNewMap() {
		return nuovaStanza;
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
	
	public String getScritta() {
		return messaggio;
	}
}
