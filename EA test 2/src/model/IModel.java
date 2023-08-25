package model;

import java.awt.Rectangle;

import controller.IController;
import model.mappa.Map;
import model.mappa.RoomModel;
import model.mappa.Stanze;
import model.mappa.TilesetModel;

public class IModel {

	private IController controller;
	//serve per memorizzare l'indice del passaggio nella mappa dove si trova il giocatore
	private int indicePassaggioAttuale; 
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
	String percorsoDatiLaboratorio = "/mappe/datiLaboratorio.txt";

		
	
	public IModel(IController c) {
		controller = c;
		mappa = new Map();
		tiles = new TilesetModel();

		stanze = new RoomModel[Map.NUM_STANZE];
		stanze[Map.BIBLIOTECA] = new RoomModel(percorsoDatiBiblioteca, this, Map.BIBLIOTECA);		
		stanze[Map.DORMITORIO] = new RoomModel(percorsoDatiDormitorio, this, Map.DORMITORIO);
		stanze[Map.AULA_STUDIO] = new RoomModel(percorsoDatiAlaStudio, this, Map.AULA_STUDIO);		
		stanze[Map.TENDA] = new RoomModel(percorsoDatiTenda, this, Map.TENDA);
		stanze[Map.LABORATORIO] = new RoomModel(percorsoDatiLaboratorio, this, Map.LABORATORIO);

		
	}

	public Map getMappa() {
		return mappa;
	}

	public TilesetModel getTilesetModel() {
		return tiles;
	}

	public int checkPassaggio(Rectangle hitbox) {
		indicePassaggioAttuale =  stanze[Stanze.stanzaAttuale.indiceMappa].checkPassInRoom(hitbox);
		return indicePassaggioAttuale;
	}

	//salva i dati della posizione del giocatore dopo la transizione tra stanze
	public void memorizzaDatiNuovaStanza() {
		newXPos = stanze[Stanze.stanzaAttuale.indiceMappa].getPassaggi().get(indicePassaggioAttuale).getNextX();
		newYPos = stanze[Stanze.stanzaAttuale.indiceMappa].getPassaggi().get(indicePassaggioAttuale).getNextY();
		newRoom = stanze[Stanze.stanzaAttuale.indiceMappa].getPassaggi().get(indicePassaggioAttuale).getNewMap();
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

	public RoomModel getStanza(int i) {
		return stanze[i];
	}
	
	public IController getController() {
		return controller;
	}
//	Rectangle r = new Rectangle(23, 9, 32, 32);
//	stanze[Map.AULA_STUDIO].addNPC(new CatController(r, controller.getPlay()));
	
}
