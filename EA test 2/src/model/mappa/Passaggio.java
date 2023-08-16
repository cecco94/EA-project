package model;

import java.awt.Rectangle;

import view.main.GamePanel;

public class Passaggio {

	private int prevX, prevY;	//posizione del passaggio
	private int nextX, nextY;	//new position of the player in the new room
	private Rectangle borders;
	
	private int newMap;
	
	public Passaggio(int pX, int pY, int width, int height, int nX, int nY, int nM) {
		prevX = pX*GamePanel.TILES_SIZE;
		prevY = pY*GamePanel.TILES_SIZE;
		
		nextX = nX*GamePanel.TILES_SIZE;
		nextY = nY*GamePanel.TILES_SIZE;
		
		newMap = nM;
		borders = new Rectangle(prevX, prevY, width, height);
	}
	
	//controlla se il personaggio si trova sul passaggio
	public boolean checkPlayer(Rectangle hitboxPlayer) {
		return hitboxPlayer.intersects(borders);
	}

	public int getNewMap() {
		return newMap;
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
}
