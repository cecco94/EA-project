package view.playState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import model.IModel;
import model.mappa.Map;
import view.main.GamePanel;
import view.main.IView;
import view.mappa.Tileset;

//si occuperà di disegnare tutto ciò che si trova nello stato play
public class PlayState {

	private IView view;
	private IModel model;
	private Tileset tileset;
	
	public PlayState(IView v, IModel m, Tileset t) {
		view = v;
		model = m;
		tileset = t;
	}
	
	public void draw(Graphics2D g2, int stanza) {
		drawMap(g2, stanza);
	}
	
	
	public void drawMap(Graphics2D g2, int stanza) {
		for (int layer = 0; layer < Map.NUM_STRATI; layer++)
			drawLayer(g2, stanza, layer);
	}
	
	private void drawLayer(Graphics2D g2, int stanza, int layer) {
		int[][] strato = model.getMappa().getStrato(stanza, layer);
		for(int riga = 0; riga < strato.length; riga++) 
			for(int colonna = 0; colonna < strato[riga].length; colonna++) {
				int numeroTile = strato[riga][colonna];
				if(numeroTile > 0) {
					BufferedImage tileDaDisegnare = tileset.getTile(numeroTile -1).getImage();
					g2.drawImage(tileDaDisegnare, colonna* GamePanel.TILES_SIZE , riga*GamePanel.TILES_SIZE, null);
				}
			}			
	}

	
	
	
	
}
