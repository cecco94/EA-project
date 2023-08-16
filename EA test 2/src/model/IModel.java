package model;

import java.awt.Rectangle;

import controller.Gamestate;
import model.mappa.Map;
import model.mappa.Room;
import model.mappa.TilesetModel;
import view.main.GamePanel;

public class IModel {

	//serve per memorizzare l'indice del passaggio nella mappa dove si trova il giocatore
	int indicePassaggioAttuale = -1;
	
	private Map mappa;
	private TilesetModel tiles;
	
	private Room[] stanze;
	public final int BIBLIOTECA = 0, DORMITORIO = 1;
	
	// memorizza dove andrà il giocatore dopo la transizione tra stanze
	private int newXPos;
	private int newYPos;
	private int newRoom;
	
	public IModel() {
		mappa = new Map();
		tiles = new TilesetModel();

		stanze = new Room[2];
		
		stanze[BIBLIOTECA] = new Room(this);
		stanze[BIBLIOTECA].addNewPassaggio(20, 8, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE + 10, 27, 23, Map.DORMITORIO);
		
		stanze[DORMITORIO] = new Room(this);
		stanze[DORMITORIO].addNewPassaggio(25, 23, GamePanel.TILES_SIZE/2, GamePanel.TILES_SIZE/2, 21, 10, Map.BIBLIOTECA);
	}

	public Map getMappa() {
		return mappa;
	}

	public TilesetModel getTilesetModel() {
		return tiles;
	}

	public int checkPassaggio(Rectangle hitbox) {
		indicePassaggioAttuale =  stanze[mappa.getMappaAttuale()].checkPassInRoom(hitbox);
		return indicePassaggioAttuale;
	}

	//salva i dati della posizione del giocatore dopo la transizione tra stanze
	public void memorizzaDatiNuovaStanza() {
		newXPos = stanze[mappa.getMappaAttuale()].passaggi.get(indicePassaggioAttuale).getNextX();
		newYPos = stanze[mappa.getMappaAttuale()].passaggi.get(indicePassaggioAttuale).getNextY();
		newRoom = stanze[mappa.getMappaAttuale()].passaggi.get(indicePassaggioAttuale).getNewMap();
	}
	
	public void setNewRoom() {
		mappa.setMappaAttuale(newRoom);
	}

	public int getNewXPos() {
		return newXPos;
	}

	public int getNewYPos() {
		return newYPos;
	}
	
	
	
}
