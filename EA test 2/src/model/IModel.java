package model;

import java.awt.Rectangle;

import model.mappa.Map;
import model.mappa.RoomModel;
import model.mappa.Stanze;
import model.mappa.TilesetModel;

public class IModel {

	//serve per memorizzare l'indice del passaggio nella mappa dove si trova il giocatore
	int indicePassaggioAttuale; 
	// memorizza dove andrà il giocatore dopo la transizione tra stanze
	private int newXPos;
	private int newYPos;
	private Stanze newRoom;
	
	private Map mappa;
	private TilesetModel tiles;
	
	private RoomModel[] stanze;
		
	String percorsoDatiDormitorio = "/mappe/datiDormitorio.txt";
	String percorsoDatiBiblioteca = "/mappe/datiBiblioteca.txt";
	String percorsoDatiAlaStudio = "/mappe/datiAulaStudio.txt";
	String percorsoDatiTenda = "/mappe/datiTenda.txt";
		
	
	public IModel() {
		mappa = new Map();
		tiles = new TilesetModel();

		stanze = new RoomModel[Map.NUM_STANZE];
		
		stanze[Map.BIBLIOTECA] = new RoomModel(percorsoDatiBiblioteca);		
		stanze[Map.DORMITORIO] = new RoomModel(percorsoDatiDormitorio);
		stanze[Map.AULA_STUDIO] = new RoomModel(percorsoDatiAlaStudio);
		stanze[Map.TENDA] = new RoomModel(percorsoDatiTenda);
		
	}

	public Map getMappa() {
		return mappa;
	}

	public TilesetModel getTilesetModel() {
		return tiles;
	}

	public int checkPassaggio(Rectangle hitbox) {
		indicePassaggioAttuale =  stanze[Stanze.stanzaAttuale.indiceNellaMappa].checkPassInRoom(hitbox);
		return indicePassaggioAttuale;
	}

	//salva i dati della posizione del giocatore dopo la transizione tra stanze
	public void memorizzaDatiNuovaStanza() {
		newXPos = stanze[Stanze.stanzaAttuale.indiceNellaMappa].passaggi.get(indicePassaggioAttuale).getNextX();
		newYPos = stanze[Stanze.stanzaAttuale.indiceNellaMappa].passaggi.get(indicePassaggioAttuale).getNextY();
		newRoom = stanze[Stanze.stanzaAttuale.indiceNellaMappa].passaggi.get(indicePassaggioAttuale).getNewMap();
	}
	
	public void setNewRoom() {
		Stanze.stanzaAttuale = newRoom;
	}

	public int getNewXPos() {
		return newXPos;
	}

	public int getNewYPos() {
		return newYPos;
	}
	
	public Stanze getNewRoom() {
		return newRoom;
	}

	
}
