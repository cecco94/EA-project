package view.playState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import view.main.GamePanel;
import view.mappa.TilesetView;

public class SortableTile extends SortableElement{

	private int tileType;
	private TilesetView tileset;
	
	public SortableTile(TilesetView t, int tipoTyle, int strato, int col, int row) {
		tileset = t;
		tileType = tipoTyle;
		type = strato;
		xPos = col*GamePanel.TILES_SIZE;
		yPos = row*GamePanel.TILES_SIZE;
	}
	
	@Override
	public void draw(Graphics2D g2) {	
		BufferedImage img = tileset.getTile(tileType).getImage();
		g2.drawImage(img, xPos, yPos, null);
	//	g2.drawRect(xPos, yPos, img.getWidth(), img.getHeight());
	}

}
