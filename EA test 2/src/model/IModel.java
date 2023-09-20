package model;



import controller.IController;
import controller.playState.Hitbox;
import model.mappa.Map;
import model.mappa.RoomModel;
import model.mappa.Rooms;
import model.mappa.TilesetModel;
import view.IView;
import view.main.GamePanel;

public class IModel {

	private IController controller;
	private IView view;
	//serve per memorizzare l'indice del passaggio nella mappa dove si trova il giocatore
	private int indexCurrentPassage; 
	// memorizza dove andrà il giocatore dopo la transizione tra stanze
	private int newXPos;
	private int newYPos;
	private Rooms newRoom;
	
	private int eventIndex;
	
	private Map map;
	private TilesetModel tiles;
	
	private RoomModel[] rooms;
		
	String percorsoDatiDormitorio = "/mappe/datiDormitorio.txt";
	String percorsoDatiBiblioteca = "/mappe/datiBiblioteca.txt";
	String percorsoDatiAlaStudio = "/mappe/datiAulaStudio.txt";
	String percorsoDatiTenda = "/mappe/datiTenda.txt";
	String percorsoDatiLaboratorio = "/mappe/datiLaboratorio.txt";
	String percorsoDatiStudioProf = "/mappe/datiStudioProf.txt";
		
	
	public IModel(IController c) {
		controller = c;
		
		map = new Map();
		tiles = new TilesetModel();		
		
		rooms = new RoomModel[Rooms.numStanze];
		rooms[Rooms.INDEX_BIBLIOTECA] = new RoomModel(percorsoDatiBiblioteca, this, Rooms.INDEX_BIBLIOTECA);		
		rooms[Rooms.INDEX_DORMITORIO] = new RoomModel(percorsoDatiDormitorio, this, Rooms.INDEX_DORMITORIO);
		rooms[Rooms.INDEX_AULA_STUDIO] = new RoomModel(percorsoDatiAlaStudio, this, Rooms.INDEX_AULA_STUDIO);		
		rooms[Rooms.INDEX_TENDA] = new RoomModel(percorsoDatiTenda, this, Rooms.INDEX_TENDA);
		rooms[Rooms.INDEX_LABORATORIO] = new RoomModel(percorsoDatiLaboratorio, this, Rooms.INDEX_LABORATORIO);
		rooms[Rooms.INDEX_STUDIO_PROF] = new RoomModel(percorsoDatiStudioProf, this, Rooms.INDEX_STUDIO_PROF);
		
	//	controller.getPlay().printMatriceEntita();
	}
	
	public void addView(IView v) {
		view = v;
	}

	public Map getMap() {
		return map;
	}

	public TilesetModel getTilesetModel() {
		return tiles;
	}

	public int checkPassagge(Hitbox hitbox) {
		indexCurrentPassage = rooms[Rooms.currentRoom.mapIndex].checkPassInRoom(hitbox);
		return indexCurrentPassage;
	}
	
	public int checkEvent(Hitbox hitbox) {
		eventIndex = rooms[Rooms.currentRoom.mapIndex].checkEventInRoom(hitbox);
		return eventIndex;
	}

	//salva i dati della posizione del giocatore dopo la transizione tra stanze
	public void saveNewRoomData() {
		newXPos = rooms[Rooms.currentRoom.mapIndex].getPassaggi().get(indexCurrentPassage).getNextX();
		newYPos = rooms[Rooms.currentRoom.mapIndex].getPassaggi().get(indexCurrentPassage).getNextY();
		newRoom = rooms[Rooms.currentRoom.mapIndex].getPassaggi().get(indexCurrentPassage).getNewMap();
	}
	
	public void setNewRoom() {
		Rooms.currentRoom = newRoom;
	}

	public int getNewXPos() {
		return newXPos;
	}

	public int getNewYPos() {
		return newYPos;
	}
	
	public Rooms getNewRoom() {
		return newRoom;
	}

	public RoomModel getRoom(int i) {
		return rooms[i];
	}
	
	public IController getController() {
		return controller;
	}

	public IView getView() {
		return view;
	}

	public static int getTileSize() {
		return GamePanel.TILES_SIZE;
	}
	
	public static float getGameScale() {
		return GamePanel.SCALE;
	}
	
}
