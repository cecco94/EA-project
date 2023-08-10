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
	private ArrayList<SortableElement> drawOrder;
	
	public PlayState(IModel m, TilesetView t, IView v) {
		view = v;
		model = m;
		tileset = t;
		player = new PlayerView(view);
		drawOrder = new ArrayList<>();
	}
	
	public void draw(Graphics2D g2, int stanza) {
		drawFloor(g2, stanza);
		//aggiungi nella lista gli elementi degli ultimi due strati, i personaggi e il player
		addTilesToSortList(drawOrder, stanza);
		//cambia xpos e ypos del personaggio
		addNPCandPlayer(drawOrder);
					
		Collections.sort(drawOrder);
		
		for(int i = 0; i < drawOrder.size(); i++)
			drawOrder.get(i).draw(g2);
		
		drawOrder.clear();
		
	//	disegnaGriglia(g2, stanza);
	//	disegnaHitbox(g2, stanza);
		
	}
	
	private void addNPCandPlayer(ArrayList<SortableElement> drawOrder) {
		player.setDrawPosition();
		drawOrder.add(player);
	}

	public void drawFloor(Graphics2D g2, int stanza) {
		for (int layer = 0; layer < Map.TERZO_STRATO; layer++)	//disegna i primi due strati
			drawLayer(g2, stanza, layer);
	}

	private void addTilesToSortList(ArrayList<SortableElement> drawOrder, int stanza) {
		for(int layer = Map.TERZO_STRATO; layer <= Map.QUARTO_STRATO; layer++) {
			
			int[][] strato = model.getMappa().getStrato(stanza, layer);
			for(int riga = 0; riga < strato.length; riga++) 
				for(int colonna = 0; colonna < strato[riga].length; colonna++) {
					int numeroTile = strato[riga][colonna];
					if(numeroTile > 0) 
						drawOrder.add(new SortableTile(tileset, numeroTile, layer, colonna, riga)); 
				}
		}
		
	}

	private void drawLayer(Graphics2D g2, int stanza, int layer) {
		int[][] strato = model.getMappa().getStrato(stanza, layer);
		for(int riga = 0; riga < strato.length; riga++) 
			for(int colonna = 0; colonna < strato[riga].length; colonna++) {
				int numeroTile = strato[riga][colonna];
				if(numeroTile > 0) {
					BufferedImage tileDaDisegnare = tileset.getTile(numeroTile).getImage();
					g2.drawImage(tileDaDisegnare, colonna* GamePanel.TILES_SIZE, riga*GamePanel.TILES_SIZE, null);
				}
			}			
	}

//	private void disegnaHitbox(Graphics2D g2, int stanza) {
//		int[][] stratoDue = model.getMappa().getStrato(stanza, Map.TERZO_STRATO);
//		for(int riga = 0; riga < stratoDue.length; riga++) 
//			for(int colonna = 0; colonna < stratoDue[riga].length; colonna++) {
//				int numeroTile = stratoDue[riga][colonna];
//					if (numeroTile > 0) {
//					Rectangle hitbox = model.getTilesetModel().getTile(numeroTile).getHitbox();
//					g2.setColor(Color.red);
//					g2.drawRect(colonna*GamePanel.TILES_SIZE + hitbox.x, riga*GamePanel.TILES_SIZE + hitbox.y, hitbox.width, hitbox.height);
//					}
//				}
//	}
	
//	private void disegnaGriglia(Graphics2D g2, int stanza) {
//		int[][] stratoUno = model.getMappa().getStrato(stanza, 0);
//		for(int riga = 0; riga < stratoUno.length; riga++)
//			g2.drawLine(0, riga*GamePanel.TILES_SIZE, GamePanel.GAME_WIDTH, riga*GamePanel.TILES_SIZE);
//		
//		for(int colonna = 0; colonna < stratoUno[0].length; colonna++)
//			g2.drawLine(colonna*GamePanel.TILES_SIZE, 0, colonna*GamePanel.TILES_SIZE, GamePanel.GAME_HEIGHT);
//	}
		
	
}

	
