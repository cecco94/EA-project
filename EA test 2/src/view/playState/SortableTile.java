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
		typeElemtToSort = strato;				//più è basso lo strato di questo tile, prima dovrà essere disegnato
		xPosMap = col*GamePanel.TILES_SIZE;		//posizione nella mappa = num colonna * grandezza quadratino
		yPosMap = row*GamePanel.TILES_SIZE;
	}
	
	@Override
	public void draw(Graphics2D g2, int playerScreenPositionX,  int playerScreenPositionY, int playerMapPositionX, int playerMapPositionY) {	
		
		int distanzaX = playerMapPositionX - xPosMap;
		int distanzaY = playerMapPositionY - yPosMap;
		
//		int xPosOnScreen = xPos + 10*GamePanel.TILES_SIZE;
//		int yPosOnScreen = yPos + 7*GamePanel.TILES_SIZE;
		
//		int xPosOnScreen = xPos - xOffsetRespectTheCenterOfScreen + 20;
//		int yPosOnScreen = yPos - yOffsetRespectTheCenterOfScreen + 15;
		
		BufferedImage img = tileset.getTile(tileCorrispondenteAlNumero).getImage();
		g2.drawImage(img, playerScreenPositionX - distanzaX, playerScreenPositionY - distanzaY, null);
	}

}
