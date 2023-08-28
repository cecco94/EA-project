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
	String percorsoDatiStudioProf = "/mappe/datiStudioProf.txt";
		
	
	public IModel(IController c) {
		controller = c;
		mappa = new Map();
		tiles = new TilesetModel();

		stanze = new RoomModel[Stanze.numStanze];
		stanze[Stanze.INDEX_BIBLIOTECA] = new RoomModel(percorsoDatiBiblioteca, this, Stanze.INDEX_BIBLIOTECA);		
		stanze[Stanze.INDEX_DORMITORIO] = new RoomModel(percorsoDatiDormitorio, this, Stanze.INDEX_DORMITORIO);
		stanze[Stanze.INDEX_AULA_STUDIO] = new RoomModel(percorsoDatiAlaStudio, this, Stanze.INDEX_AULA_STUDIO);		
		stanze[Stanze.INDEX_TENDA] = new RoomModel(percorsoDatiTenda, this, Stanze.INDEX_TENDA);
		stanze[Stanze.INDEX_LABORATORIO] = new RoomModel(percorsoDatiLaboratorio, this, Stanze.INDEX_LABORATORIO);
		stanze[Stanze.INDEX_STUDIO_PROF] = new RoomModel(percorsoDatiStudioProf, this, Stanze.INDEX_STUDIO_PROF);
		
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

	
}
