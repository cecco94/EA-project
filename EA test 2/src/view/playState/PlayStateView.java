package view.playState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import model.mappa.Map;
import model.mappa.Stanze;

import view.IView;
import view.main.GamePanel;
import view.mappa.TilesetView;
import view.playState.drawOrder.SortableElement;
import view.playState.drawOrder.SortableTile;
import view.playState.entityView.PlayerView;
import view.playState.entityView.ProjectileView;
import view.sound.SoundManager;

//si occuperà di disegnare tutto ciò che si trova nello stato play
public class PlayStateView {

	private TilesetView tileset;
	private PlayerView player;
	private IView view;
	
	//per disegnarli dall'alto verso il basso, mettiamo tutti gli elementi della mappa in una lista
	//che poi ordineremo
	private ArrayList<SortableElement> drawOrder;	
	private ArrayList<ProjectileView> appuntiLanciati;
	private RoomView[] stanzeView;
	
	private PlayUI ui;
	
	public PlayStateView(TilesetView t, IView v) {
		view = v;
		tileset = t;
		
		ui = new PlayUI(this);
		
		player = new PlayerView(view);
		drawOrder = new ArrayList<>();
		
		appuntiLanciati = new ArrayList<>();

		stanzeView = new RoomView[Stanze.numStanze];
		stanzeView[Stanze.BIBLIOTECA.indiceMappa] = new RoomView(this, Stanze.BIBLIOTECA.indiceMappa);
		stanzeView[Stanze.AULA_STUDIO.indiceMappa] = new RoomView(this, Stanze.AULA_STUDIO.indiceMappa);
		stanzeView[Stanze.DORMITORIO.indiceMappa] = new RoomView(this, Stanze.DORMITORIO.indiceMappa);
		stanzeView[Stanze.TENDA.indiceMappa] = new RoomView(this, Stanze.TENDA.indiceMappa);
		stanzeView[Stanze.LABORATORIO.indiceMappa] = new RoomView(this, Stanze.LABORATORIO.indiceMappa);
		stanzeView[Stanze.STUDIO_PROF.indiceMappa] = new RoomView(this, Stanze.STUDIO_PROF.indiceMappa);

	}
	
	//prima disegna il pavimento, poi ci disegna sopra tutti gli oggetti partendo dall'alto, in modo il player che sta sotto
	//ad un tile sembri stare davanti a quel tile e viceversa. stiamo aggiungendo tridimensionalità al gioco
	public void draw(Graphics2D g2) {
		int stanza = Stanze.stanzaAttuale.indiceMappa;
		//prendiamo la posizione del player nella mappa (in quale tile tile si trova il punto in alto a sinitra della hitbox)
		//contiamo a sinistra -10 e a destra +10, in su -7 e in giù +7 e prendiamo solo le parti della matrice con questi numeri
		int playerMapX = view.getController().getPlay().getPlayer().getHitbox().x;
		int playerMapY = view.getController().getPlay().getPlayer().getHitbox().y;
		//da dove iniziare a prendere i numeri della mappa nelle matrici
		int firstTileInFrameCol = playerMapX/GamePanel.TILES_SIZE - 10;
		int firstTileInFrameRow = playerMapY/GamePanel.TILES_SIZE - 8;
		
		//il pavimento viene sempre disegnato sotto a tutto
		drawFloor(g2, stanza, firstTileInFrameCol, firstTileInFrameRow, playerMapX, playerMapY);
		
		//disegna proiettili
		for(int index = 0; index < appuntiLanciati.size(); index++)
			appuntiLanciati.get(index).draw(g2, playerMapX, playerMapY);
		
		//aggiungi nella lista gli elementi degli ultimi due strati
		addTilesToSortList(drawOrder, stanza, firstTileInFrameCol, firstTileInFrameRow);
		
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
		
	public void drawFloor(Graphics2D g2, int stanza, int xMappaIniziale, int yMappaIniziale, int playerMapX, int playerMapY) {
		for (int layer = 0; layer < Map.TERZO_STRATO; layer++)	//disegna i primi due strati
			drawLayer(g2, stanza, layer, xMappaIniziale, yMappaIniziale, playerMapX, playerMapY);
	}
	
	private void drawLayer(Graphics2D g2, int stanza, int layer, int xMappaIniziale, int yMappaIniziale, int playerMapX, int playerMapY) {
		int[][] strato = view.getModel().getMappa().getStrato(stanza, layer);
		for(int riga = yMappaIniziale; riga <= yMappaIniziale + GamePanel.TILES_IN_HEIGHT + 1; riga++) 
			for(int colonna = xMappaIniziale; colonna <= xMappaIniziale + GamePanel.TILES_IN_WIDTH + 1; colonna++) {
				int numeroTile = 0;
				try {
					numeroTile = strato[riga][colonna];
				}
				catch(ArrayIndexOutOfBoundsException obe) {
					numeroTile = 0;
				}
				if(numeroTile > 0) {
					int distanzaX = playerMapX - colonna*GamePanel.TILES_SIZE;
					int distanzaY = playerMapY - riga*GamePanel.TILES_SIZE;
					
					int xScreenTile = PlayerView.xOnScreen - distanzaX + PlayerView.getXOffset();
					int yScreenTile = PlayerView.yOnScreen - distanzaY + PlayerView.getYOffset();
							
					BufferedImage tileDaDisegnare = tileset.getTile(numeroTile).getImage();
					g2.drawImage(tileDaDisegnare, xScreenTile, yScreenTile, null);
					}
			}			
	}

	private void addTilesToSortList(ArrayList<SortableElement> drawOrder, int stanza, int xMappaIniziale, int yMappaIniziale) {
		for(int layer = Map.TERZO_STRATO; layer <= Map.QUARTO_STRATO; layer++) {
			
			int[][] strato = view.getModel().getMappa().getStrato(stanza, layer);
			for(int riga = yMappaIniziale; riga <= yMappaIniziale + GamePanel.TILES_IN_HEIGHT + 1; riga++) 
				for(int colonna = xMappaIniziale; colonna <= xMappaIniziale + GamePanel.TILES_IN_WIDTH + 1; colonna++) {
					int numeroTile = 0;
					try {
						numeroTile = strato[riga][colonna];
					}
					catch(ArrayIndexOutOfBoundsException obe) {
						numeroTile = 0;
					}	
					if(numeroTile > 0) 
						drawOrder.add(new SortableTile(tileset, numeroTile, layer, colonna, riga));			
				}
		}
		
	}

	private void addNPCandPlayer(ArrayList<SortableElement> drawOrder, int posizPlayerX, int posizPlayerY) {
		
		//aggiungiamo solo gli npc e i nemici nella stanza vicino al giocatore
		switch(Stanze.stanzaAttuale) {
		case AULA_STUDIO:
			stanzeView[Stanze.AULA_STUDIO.indiceMappa].addEntitiesInFrameForSort(posizPlayerX, posizPlayerY, drawOrder);
			break;
		case BIBLIOTECA:
			stanzeView[Stanze.BIBLIOTECA.indiceMappa].addEntitiesInFrameForSort(posizPlayerX, posizPlayerY, drawOrder);
			break;
		case TENDA:
			stanzeView[Stanze.TENDA.indiceMappa].addEntitiesInFrameForSort(posizPlayerX, posizPlayerY, drawOrder);
			break;
		case LABORATORIO:
			stanzeView[Stanze.LABORATORIO.indiceMappa].addEntitiesInFrameForSort(posizPlayerX, posizPlayerY, drawOrder);
			break;
		case STUDIO_PROF:
			stanzeView[Stanze.STUDIO_PROF.indiceMappa].addEntitiesInFrameForSort(posizPlayerX, posizPlayerY, drawOrder);
			break;
		case DORMITORIO:
			stanzeView[Stanze.DORMITORIO.indiceMappa].addEntitiesInFrameForSort(posizPlayerX, posizPlayerY, drawOrder);
			break;
		}
		
		player.setMapPositionForSort(posizPlayerX, posizPlayerY);
		drawOrder.add(player);
	}

	private void drawAllElementsAboveFloor(Graphics2D g2, int posizPlayerX, int posizPlayerY) {
		
		for(int i = 0; i < drawOrder.size(); i++)
			drawOrder.get(i).draw(g2, posizPlayerX, posizPlayerY);
		
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
	
	public void addProjectile() {
		appuntiLanciati.add(new ProjectileView(appuntiLanciati.size(), view));
		view.playSE(SoundManager.FUOCO);
	}
	
	public void removeProjectile(int indexRemove) {
		for(int i = indexRemove; i < appuntiLanciati.size(); i++)
			appuntiLanciati.get(i).index--;
		try {
		appuntiLanciati.remove(indexRemove);
		}
		catch(IndexOutOfBoundsException iobe) {
			iobe.printStackTrace();
			System.out.println("lista appunti finita nel view");
			appuntiLanciati.clear();
			view.getController().getPlay().getBulletsInRoom().clear();
		}
	}
	
	public ArrayList<ProjectileView> getAppunti() {
		return appuntiLanciati;
	}

	public RoomView getRoom(int index) {
		return stanzeView[index];
		
	}
}

	
