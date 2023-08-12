package view.playState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import model.IModel;
import model.mappa.Map;
import view.main.GamePanel;
import view.main.IView;
import view.mappa.TilesetView;

//si occuperà di disegnare tutto ciò che si trova nello stato play
public class PlayState {

	private IModel model;
	private TilesetView tileset;
	private PlayerView player;
	private IView view;
	
	//per disegnarli dallìalto verso il basso, mettiamo tutti gli elementi della mappa in una lista
	//che poi ordineremo
	private ArrayList<SortableElement> drawOrder;
	
	public PlayState(IModel m, TilesetView t, IView v) {
		view = v;
		model = m;
		tileset = t;
		player = new PlayerView(view);
		drawOrder = new ArrayList<>();
	}
	
	//prima disegna il pavimento, poi ci disegna sopra tutti gli oggetti partendo dall'alto, in modo il player che sta sotto
	//ad un tile sembri stare davanti a quel tile e viceversa. stiamo aggiungendo tridimensionalità al gioco
	public void draw(Graphics2D g2, int stanza) {
		//prendiamo la posizione del player nella mappa (in quale tile tile si trova il punto in alto a sinitra della hitbox)
		//contiamo a sinistra -10 e a destra +10, in su -7 e in giù +7 e prendiamo solo le parti della matrice con questi numeri
		int posizPlayerX = view.getController().getPlay().getPlayer().getHitbox().x/GamePanel.TILES_SIZE;
		int posizPlayerY = view.getController().getPlay().getPlayer().getHitbox().y/GamePanel.TILES_SIZE;
		//da dove iniziare a prendere i numeri della mappa nelle matrici
		int xMappaIniziale = posizPlayerX -10;
		int yMappaIniziale = posizPlayerY - 7;
		
		//il pavimento viene sempre disegnato sotto a tutto
		drawFloor(g2, stanza, xMappaIniziale, yMappaIniziale);
		
		//aggiungi nella lista gli elementi degli ultimi due strati
		addTilesToSortList(drawOrder, stanza, xMappaIniziale, yMappaIniziale);
		
		//cambia xpos e ypos del player e lo aggiunge
		addNPCandPlayer(drawOrder);
					
		//collections è una classe di utilità che implementa un algoritmo veloce di ordinamento
		Collections.sort(drawOrder);
		
		//richiama il metodo draw su ogni elemento in ordine	
		int xPlayerMap = view.getController().getPlay().getPlayer().getHitbox().x;
		int yPlayerMap = view.getController().getPlay().getPlayer().getHitbox().y;
		
		for(int i = 0; i < drawOrder.size(); i++)
			drawOrder.get(i).draw(g2, player.getXOnScreen(), player.getYOnScreen(), xPlayerMap, yPlayerMap);
		
		//svuota la lista per ricominciare il frame successivo
		drawOrder.clear();
		
	//	disegnaGriglia(g2, stanza);
	//	disegnaHitbox(g2, stanza);
		
	}
	
	private void addNPCandPlayer(ArrayList<SortableElement> drawOrder) {
		player.setDrawPosition();
		drawOrder.add(player);
	}

	public void drawFloor(Graphics2D g2, int stanza, int xMappaIniziale, int yMappaIniziale) {
		for (int layer = 0; layer < Map.TERZO_STRATO; layer++)	//disegna i primi due strati
			drawLayer(g2, stanza, layer, xMappaIniziale, yMappaIniziale);
	}

	private void addTilesToSortList(ArrayList<SortableElement> drawOrder, int stanza, int xMappaIniziale, int yMappaIniziale) {
		for(int layer = Map.TERZO_STRATO; layer <= Map.QUARTO_STRATO; layer++) {
			
			int[][] strato = model.getMappa().getStrato(stanza, layer);
			for(int riga = yMappaIniziale; riga <= yMappaIniziale + GamePanel.TILES_IN_HEIGHT; riga++) 
				for(int colonna = xMappaIniziale; colonna <= xMappaIniziale + GamePanel.TILES_IN_WIDTH; colonna++) {
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

	private void drawLayer(Graphics2D g2, int stanza, int layer, int xMappaIniziale, int yMappaIniziale) {
		int[][] strato = model.getMappa().getStrato(stanza, layer);
		for(int riga = yMappaIniziale; riga <= yMappaIniziale + GamePanel.TILES_IN_HEIGHT; riga++) 
			for(int colonna = xMappaIniziale; colonna <= xMappaIniziale + GamePanel.TILES_IN_WIDTH; colonna++) {
				int numeroTile = 0;
				try {
					numeroTile = strato[riga][colonna];
				}
				catch(ArrayIndexOutOfBoundsException obe) {
					numeroTile = 0;
				}
				if(numeroTile > 0) {
					int xPosOnScreen = colonna*GamePanel.TILES_SIZE - view.getController().getPlay().getPlayer().getHitbox().x + player.getXOnScreen();
					int yPosOnScreen = riga*GamePanel.TILES_SIZE - view.getController().getPlay().getPlayer().getHitbox().y + player.getYOnScreen();
					
					BufferedImage tileDaDisegnare = tileset.getTile(numeroTile).getImage();
					g2.drawImage(tileDaDisegnare, xPosOnScreen, yPosOnScreen, null);
				}
			}			
	}

	@SuppressWarnings("unused")
	private void disegnaHitbox(Graphics2D g2, int stanza) {			//for debug
		int[][] stratoDue = model.getMappa().getStrato(stanza, Map.TERZO_STRATO);
		for(int riga = 0; riga < stratoDue.length; riga++) 
			for(int colonna = 0; colonna < stratoDue[riga].length; colonna++) {
				int numeroTile = stratoDue[riga][colonna];
					if (numeroTile > 0) {
					Rectangle hitbox = model.getTilesetModel().getTile(numeroTile).getHitbox();
					g2.setColor(Color.red);
					g2.drawRect(colonna*GamePanel.TILES_SIZE + hitbox.x, riga*GamePanel.TILES_SIZE + hitbox.y, hitbox.width, hitbox.height);
					}
				}
	}
	
	@SuppressWarnings("unused")
	private void disegnaGriglia(Graphics2D g2, int stanza) {		//for debug
		g2.setColor(Color.black);
		int[][] stratoUno = model.getMappa().getStrato(stanza, 0);
		for(int riga = 0; riga < stratoUno.length; riga++)
			g2.drawLine(0, riga*GamePanel.TILES_SIZE, GamePanel.GAME_WIDTH, riga*GamePanel.TILES_SIZE);
		
		for(int colonna = 0; colonna < stratoUno[0].length; colonna++)
			g2.drawLine(colonna*GamePanel.TILES_SIZE, 0, colonna*GamePanel.TILES_SIZE, GamePanel.GAME_HEIGHT);
	}
		
	
}

	
