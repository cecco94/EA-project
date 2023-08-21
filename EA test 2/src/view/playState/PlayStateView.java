package view.playState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import model.IModel;
import model.mappa.Map;
import model.mappa.Stanze;
import view.IView;
import view.ViewUtils;
import view.main.GamePanel;
import view.mappa.TilesetView;
import view.playState.drawOrder.SortableElement;
import view.playState.drawOrder.SortableTile;
import view.playState.entity.CatView;
import view.playState.player.PlayerView;

//si occuperà di disegnare tutto ciò che si trova nello stato play
public class PlayStateView {

	private IModel model;
	private TilesetView tileset;
	private PlayerView player;
	private IView view;
	private CatView cat;
	
	//per disegnarli dall'alto verso il basso, mettiamo tutti gli elementi della mappa in una lista
	//che poi ordineremo
	private ArrayList<SortableElement> drawOrder;
	
	private BufferedImage effettoBuio;
	private PlayUI ui;
	
	public PlayStateView(IModel m, TilesetView t, IView v) {
		view = v;
		model = m;
		tileset = t;
		
		cat = new CatView(v);
		
		ui = new PlayUI(this);
		
		player = new PlayerView(view);
		drawOrder = new ArrayList<>();
		
		
		try {
			effettoBuio = ImageIO.read(getClass().getResourceAsStream("/mappe/effettoBuio2.png"));
			effettoBuio = ViewUtils.scaleImage(effettoBuio, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//prima disegna il pavimento, poi ci disegna sopra tutti gli oggetti partendo dall'alto, in modo il player che sta sotto
	//ad un tile sembri stare davanti a quel tile e viceversa. stiamo aggiungendo tridimensionalità al gioco
	public void draw(Graphics2D g2) {
		int stanza = Stanze.stanzaAttuale.indiceNellaMappa;
		//prendiamo la posizione del player nella mappa (in quale tile tile si trova il punto in alto a sinitra della hitbox)
		//contiamo a sinistra -10 e a destra +10, in su -7 e in giù +7 e prendiamo solo le parti della matrice con questi numeri
		int playerMapX = view.getController().getPlay().getPlayer().getHitbox().x;
		int playerMapY = view.getController().getPlay().getPlayer().getHitbox().y;
		//da dove iniziare a prendere i numeri della mappa nelle matrici
		int firstTileInFrameCol = playerMapX/GamePanel.TILES_SIZE - 10;
		int firstTileInFrameRow = playerMapY/GamePanel.TILES_SIZE - 8;
		
		//il pavimento viene sempre disegnato sotto a tutto
		drawFloor(g2, stanza, firstTileInFrameCol, firstTileInFrameRow, playerMapX, playerMapY);
		
		//aggiungi nella lista gli elementi degli ultimi due strati
		addTilesToSortList(drawOrder, stanza, firstTileInFrameCol, firstTileInFrameRow);
		
		//cambia xpos e ypos del player e lo aggiunge
		addNPCandPlayer(drawOrder, playerMapX, playerMapY);
					
		//collections è una classe di utilità che implementa un algoritmo veloce di ordinamento
		Collections.sort(drawOrder);
		
		//richiama il metodo draw su ogni elemento in ordine
		//per capire dove disegnare gli elementi nello schermo, ci serve la posizione del giocatore
		drawAllElementsAboveFloor(g2,playerMapX,  playerMapY);
		
		//svuota la lista per ricominciare il frame successivo
		drawOrder.clear();
		
		
	//	g2.drawImage(effettoBuio, 0, 0, null);
		
		cat.draw(g2, playerMapX, playerMapY);
		
	//disegna proiettili
		player.drawProjectiles(g2, playerMapX, playerMapY);
	}			
		

	public void drawFloor(Graphics2D g2, int stanza, int xMappaIniziale, int yMappaIniziale, int playerMapX, int playerMapY) {
		for (int layer = 0; layer < Map.TERZO_STRATO; layer++)	//disegna i primi due strati
			drawLayer(g2, stanza, layer, xMappaIniziale, yMappaIniziale, playerMapX, playerMapY);
	}
	
	private void drawLayer(Graphics2D g2, int stanza, int layer, int xMappaIniziale, int yMappaIniziale, int playerMapX, int playerMapY) {
		int[][] strato = model.getMappa().getStrato(stanza, layer);
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
					
					int xScreenTile = PlayerView.xOnScreen - distanzaX + player.getXOffset();
					int yScreenTile = PlayerView.yOnScreen - distanzaY + player.getYOffset();
							
					BufferedImage tileDaDisegnare = tileset.getTile(numeroTile).getImage();
					g2.drawImage(tileDaDisegnare, xScreenTile, yScreenTile, null);
					}
			}			
	}

	private void addTilesToSortList(ArrayList<SortableElement> drawOrder, int stanza, int xMappaIniziale, int yMappaIniziale) {
		for(int layer = Map.TERZO_STRATO; layer <= Map.QUARTO_STRATO; layer++) {
			
			int[][] strato = model.getMappa().getStrato(stanza, layer);
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
	
}

	
