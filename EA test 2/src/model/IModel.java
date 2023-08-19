package model;

import java.awt.Rectangle;

import controller.playState.Stanze;
import model.mappa.Map;
import model.mappa.RoomModel;
import model.mappa.TilesetModel;
import view.main.GamePanel;

public class IModel {

	//serve per memorizzare l'indice del passaggio nella mappa dove si trova il giocatore
	int indicePassaggioAttuale = -1;
	
	private Map mappa;
	private TilesetModel tiles;
	
	private RoomModel[] stanze;
	public final int BIBLIOTECA = 0, DORMITORIO = 1, AULA_STUDIO = 2;
	
	// memorizza dove andrà il giocatore dopo la transizione tra stanze
	private int newXPos;
	private int newYPos;
	private Stanze newRoom;
	String percorsoDatiDormitorio = "/mappe/datiDormitorio.txt";
	String percorsoDatiBiblioteca = "/mappe/datiBiblioteca.txt";
	String percorsoDatiAlaStudio = "/mappe/datiAulaStudio.txt";
	
	public IModel() {
		mappa = new Map();
		tiles = new TilesetModel();

		stanze = new RoomModel[Map.NUM_STANZE];
		
		stanze[BIBLIOTECA] = new RoomModel(percorsoDatiBiblioteca);		
		stanze[DORMITORIO] = new RoomModel(percorsoDatiDormitorio);
		stanze[AULA_STUDIO] = new RoomModel(percorsoDatiAlaStudio);
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
		mappa.setMappaAttuale(newRoom.indiceNellaMappa);
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
	
	public static Stanze getStanzaAssociataAlNumero(int i) {
		if(i == Map.BIBLIOTECA)
			return Stanze.BIBLIOTECA;
		else if(i == Map.AULA_STUDIO)
			return Stanze.AULA_STUDIO;
		else
			return Stanze.DORMITORIO;
	}
	
}
