package view.playState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

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
	
	public PlayState(IModel m, TilesetView t, IView v) {
		view = v;
		model = m;
		tileset = t;
		player = new PlayerView(view);
	}
	
	public void draw(Graphics2D g2, int stanza) {
		drawMap(g2, stanza);
	}
	
	public void drawMap(Graphics2D g2, int stanza) {
		for (int layer = 0; layer < Map.NUM_STRATI; layer++)
			drawLayer(g2, stanza, layer);
		
		disegnaHitbox(g2, stanza);
		player.draw(g2);
	}

	private void drawLayer(Graphics2D g2, int stanza, int layer) {
		int[][] strato = model.getMappa().getStrato(stanza, layer);
		for(int riga = 0; riga < strato.length; riga++) 
			for(int colonna = 0; colonna < strato[riga].length; colonna++) {
				int numeroTile = strato[riga][colonna];
				if(numeroTile > 0) {
					BufferedImage tileDaDisegnare = tileset.getTile(numeroTile -1).getImage();
					g2.drawImage(tileDaDisegnare, colonna* GamePanel.TILES_SIZE, riga*GamePanel.TILES_SIZE, null);
				}
			}			
	}

	private void disegnaHitbox(Graphics2D g2, int stanza) {
		int[][] stratoDue = model.getMappa().getStrato(stanza, 1);
		for(int riga = 0; riga < stratoDue.length; riga++) 
			for(int colonna = 0; colonna < stratoDue[riga].length; colonna++) {
				int numeroTile = stratoDue[riga][colonna];
					if (numeroTile > 0) {
					Rectangle hitbox = model.getTilesetModel().getTile(numeroTile - 1).getHitbox();
					g2.setColor(Color.red);
					g2.drawRect(colonna*GamePanel.TILES_SIZE + hitbox.x, riga*GamePanel.TILES_SIZE + hitbox.y, hitbox.width, hitbox.height);
					}
				}
	}
		
	
}

	
