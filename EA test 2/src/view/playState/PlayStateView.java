package view.playState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import model.mappa.Map;
import model.mappa.Rooms;

import view.IView;
import view.SoundManager;
import view.main.GamePanel;
import view.playState.drawOrder.SortableElement;
import view.playState.drawOrder.SortableTile;
import view.playState.entityView.PlayerView;
import view.playState.mappaView.TilesetView;
import view.playState.entityView.BulletView;

//si occuperà di disegnare tutto ciò che si trova nello stato play
public class PlayStateView {

	private TilesetView tileset;
	private PlayerView player;
	private IView view;
	
	//per disegnarli dall'alto verso il basso, mettiamo tutti gli elementi della mappa in una lista
	//che poi ordineremo
	private ArrayList<SortableElement> drawOrder;	
	private ArrayList<BulletView> bulletsInRoom;
	private RoomView[] roomsView;
	
	private PlayUI ui;
	
	public PlayStateView(TilesetView t, IView v) {
		view = v;
		tileset = t;
		
		ui = new PlayUI(this);
		
		player = new PlayerView(view);
		drawOrder = new ArrayList<>();
		
		bulletsInRoom = new ArrayList<>();

		roomsView = new RoomView[Rooms.numStanze];
		roomsView[Rooms.BIBLIOTECA.mapIndex] = new RoomView(this, Rooms.BIBLIOTECA.mapIndex);
		roomsView[Rooms.AULA_STUDIO.mapIndex] = new RoomView(this, Rooms.AULA_STUDIO.mapIndex);
		roomsView[Rooms.DORMITORIO.mapIndex] = new RoomView(this, Rooms.DORMITORIO.mapIndex);
		roomsView[Rooms.TENDA.mapIndex] = new RoomView(this, Rooms.TENDA.mapIndex);
		roomsView[Rooms.LABORATORIO.mapIndex] = new RoomView(this, Rooms.LABORATORIO.mapIndex);
		roomsView[Rooms.STUDIO_PROF.mapIndex] = new RoomView(this, Rooms.STUDIO_PROF.mapIndex);

	}
	
	//prima disegna il pavimento, poi ci disegna sopra tutti gli oggetti partendo dall'alto, in modo il player che sta sotto
	//ad un tile sembri stare davanti a quel tile e viceversa. stiamo aggiungendo tridimensionalità al gioco
	public void draw(Graphics2D g2) {
		int roomIndex = Rooms.currentRoom.mapIndex;
		//prendiamo la posizione del player nella mappa (in quale tile tile si trova il punto in alto a sinitra della hitbox)
		//contiamo a sinistra -10 e a destra +10, in su -7 e in giù +7 e prendiamo solo le parti della matrice con questi numeri
		int playerMapX = view.getController().getPlay().getPlayer().getHitbox().x;
		int playerMapY = view.getController().getPlay().getPlayer().getHitbox().y;
		//da dove iniziare a prendere i numeri della mappa nelle matrici
		int firstTileInFrameCol = playerMapX/GamePanel.TILES_SIZE - 10;
		int firstTileInFrameRow = playerMapY/GamePanel.TILES_SIZE - 8;
		
		//il pavimento viene sempre disegnato sotto a tutto
		drawFloor(g2, roomIndex, firstTileInFrameCol, firstTileInFrameRow, playerMapX, playerMapY);
		
		//disegna proiettili
		for(int index = 0; index < bulletsInRoom.size(); index++)
			bulletsInRoom.get(index).draw(g2, playerMapX, playerMapY);
		
		//aggiungi nella lista gli elementi degli ultimi due strati
		addTilesToSortList(drawOrder, roomIndex, firstTileInFrameCol, firstTileInFrameRow);
		
		//aggiorna xpos e ypos del player e lo aggiunge, poi aggiunge le entità nella stanza vicino al giocatore
		addNPCandPlayer(drawOrder, playerMapX, playerMapY);
					
		//collections è una classe di utilità che implementa un algoritmo veloce di ordinamento
		Collections.sort(drawOrder);
		
		//richiama il metodo draw su ogni elemento in ordine
		//per capire dove disegnare gli elementi nello schermo, ci serve la posizione del giocatore
		drawAllElementsAboveFloor(g2, playerMapX,  playerMapY);
		
		//svuota la lista per ricominciare il frame successivo
		drawOrder.clear();
		
		//disegna la ui sopra a tutto il resto
		ui.draw(g2);
	}			
		
	public void drawFloor(Graphics2D g2, int room, int initialMapX, int initialMapY, int playerMapX, int playerMapY) {
		for (int layer = 0; layer < Map.THIRD_LAYER; layer++)	//disegna i primi due strati
			drawLayer(g2, room, layer, initialMapX, initialMapY, playerMapX, playerMapY);
	}
	
	private void drawLayer(Graphics2D g2, int room, int layerNumber, int initialMapX, int initialMapY, int playerMapX, int playerMapY) {
		int[][] layer = view.getModel().getMap().getLayer(room, layerNumber);
		for(int row = initialMapY; row <= initialMapY + GamePanel.TILES_IN_HEIGHT + 1; row++) 
			for(int col = initialMapX; col <= initialMapX + GamePanel.TILES_IN_WIDTH + 1; col++) {
				int tileNumber = 0;
				try {
					tileNumber = layer[row][col];
				}
				catch(ArrayIndexOutOfBoundsException obe) {
					tileNumber = 0;
				}
				if(tileNumber > 0) {
					int distanceX = playerMapX - col*GamePanel.TILES_SIZE;
					int distanceY = playerMapY - row*GamePanel.TILES_SIZE;
					
					int xScreenTile = PlayerView.xOnScreen - distanceX + PlayerView.getXOffset();
					int yScreenTile = PlayerView.yOnScreen - distanceY + PlayerView.getYOffset();
							
					BufferedImage tileToDraw = tileset.getTile(tileNumber).getImage();
					g2.drawImage(tileToDraw, xScreenTile, yScreenTile, null);
					}
			}			
	}

	private void addTilesToSortList(ArrayList<SortableElement> drawOrder, int roomIndex, int initialMapX, int initialMapY) {
		for(int layerNumber = Map.THIRD_LAYER; layerNumber <= Map.FOURTH_LAYER; layerNumber++) {
			
			int[][] layer = view.getModel().getMap().getLayer(roomIndex, layerNumber);
			for(int row = initialMapY; row <= initialMapY + GamePanel.TILES_IN_HEIGHT + 1; row++) 
				for(int col = initialMapX; col <= initialMapX + GamePanel.TILES_IN_WIDTH + 1; col++) {
					int tileNumber = 0;
					try {
						tileNumber = layer[row][col];
					}
					catch(ArrayIndexOutOfBoundsException obe) {
						tileNumber = 0;
					}	
					if(tileNumber > 0) 
						drawOrder.add(new SortableTile(tileset, tileNumber, layerNumber, col, row));			
				}
		}
		
	}

	private void addNPCandPlayer(ArrayList<SortableElement> drawOrder, int xPlayerPos, int yPlayerPos) {
		
		//aggiungiamo solo gli npc e i nemici nella stanza vicino al giocatore
		switch(Rooms.currentRoom) {
		case AULA_STUDIO:
			roomsView[Rooms.AULA_STUDIO.mapIndex].addEntitiesInFrameForSort(xPlayerPos, yPlayerPos, drawOrder);
			break;
		case BIBLIOTECA:
			roomsView[Rooms.BIBLIOTECA.mapIndex].addEntitiesInFrameForSort(xPlayerPos, yPlayerPos, drawOrder);
			break;
		case TENDA:
			roomsView[Rooms.TENDA.mapIndex].addEntitiesInFrameForSort(xPlayerPos, yPlayerPos, drawOrder);
			break;
		case LABORATORIO:
			roomsView[Rooms.LABORATORIO.mapIndex].addEntitiesInFrameForSort(xPlayerPos, yPlayerPos, drawOrder);
			break;
		case STUDIO_PROF:
			roomsView[Rooms.STUDIO_PROF.mapIndex].addEntitiesInFrameForSort(xPlayerPos, yPlayerPos, drawOrder);
			break;
		case DORMITORIO:
			roomsView[Rooms.DORMITORIO.mapIndex].addEntitiesInFrameForSort(xPlayerPos, yPlayerPos, drawOrder);
			break;
		}
		
		player.setMapPositionForSort(xPlayerPos, yPlayerPos);
		drawOrder.add(player);
	}

	private void drawAllElementsAboveFloor(Graphics2D g2, int xPlayerPos, int yPlayerPos) {
		
		for(int i = 0; i < drawOrder.size(); i++)
			drawOrder.get(i).draw(g2, xPlayerPos, yPlayerPos);
		
	}
	
	public PlayerView getPlayer() {
		return player;
	}
	
	public IView getView() {
		return view;
	}
	
	public PlayUI getUI() {
		return ui;
	}
	
	public void addBullet() {
		bulletsInRoom.add(new BulletView(bulletsInRoom.size(), view));
		view.playSE(SoundManager.FUOCO);
	}
	
	public void removeBullet(int indexRemove) {
		for(int i = indexRemove; i < bulletsInRoom.size(); i++)
			bulletsInRoom.get(i).index--;
		try {
		bulletsInRoom.remove(indexRemove);
		}
		catch(IndexOutOfBoundsException iobe) {
			iobe.printStackTrace();
			System.out.println("lista appunti finita nel view");
			bulletsInRoom.clear();
			view.getController().getPlay().getBulletsInRoom().clear();
		}
	}
	
	public ArrayList<BulletView> getBullets() {
		return bulletsInRoom;
	}

	public RoomView getRoom(int index) {
		return roomsView[index];
		
	}
}

	
