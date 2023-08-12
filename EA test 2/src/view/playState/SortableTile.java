package view.playState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import view.main.GamePanel;
import view.mappa.TilesetView;

//è un oggetto che contiene il tipo di tile da disegnare e la sua posizione. Siccome contiene solo tre int
//è molto leggero è può essere istanziato/eliminato più velocemente di un tile normale
public class SortableTile extends SortableElement{

	private int tileCorrispondenteAlNumero;
	private TilesetView tileset;
	
	public SortableTile(TilesetView t, int tipoTyle, int strato, int col, int row) {
		tileset = t;
		tileCorrispondenteAlNumero = tipoTyle;
		typeElemtToSort = strato;						//più è basso lo strato di questo tile, prima dovrà essere disegnato
		xPos = col*GamePanel.TILES_SIZE;
		yPos = row*GamePanel.TILES_SIZE;
	}
	
	@Override
	public void draw(Graphics2D g2, int xOffsetRespectTheCenterOfScreen,  int yOffsetRespectTheCenterOfScreen) {	
		
		int xPosOnScreen = xPos + xOffsetRespectTheCenterOfScreen;
		int yPosOnScreen = yPos + yOffsetRespectTheCenterOfScreen;
		
		BufferedImage img = tileset.getTile(tileCorrispondenteAlNumero).getImage();
		g2.drawImage(img, xPosOnScreen, yPosOnScreen, null);
	//	g2.drawRect(xPos, yPos, img.getWidth(), img.getHeight());
	}

}
