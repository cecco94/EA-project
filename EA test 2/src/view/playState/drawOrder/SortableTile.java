package view.playState.drawOrder;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import view.main.GamePanel;
import view.playState.entityView.PlayerView;
import view.playState.mappaView.TilesetView;

//è un oggetto che contiene il tipo di tile da disegnare e la sua posizione. Siccome contiene solo tre int
//è molto leggero è può essere istanziato/eliminato più velocemente di un tile normale
public class SortableTile extends SortableElement{

	private int numberInTileset;
	private TilesetView tileset;
	
	public SortableTile(TilesetView t, int tipoTyle, int strato, int col, int row) {
		tileset = t;
		numberInTileset = tipoTyle;
		
		typeElemtToSort = strato;				//più è basso lo strato di questo tile, prima dovrà essere disegnato
		
		xPosMapForSort = col*GamePanel.TILES_SIZE;		//posizione nella mappa = num colonna * grandezza quadratino
		yPosMapForSort = row*GamePanel.TILES_SIZE;
	}
	
	@Override
	public void draw(Graphics2D g2, int playerMapPositionX, int playerMapPositionY) {	
		
		//la distanza tra il player e il tile è la stessa, sia nella mappa che nella finestra di gioco
		//prendendo la distanza nella mappa rispetto al giocatore, possiamo capire dove disegnare il tile
		//rispetto alla posizione del giocatore nella finestra (che è sempre al centro)
		int distanceX = playerMapPositionX - xPosMapForSort;
		int distanceY = playerMapPositionY - yPosMapForSort;
		
		//ci serve un offset perchè la distanza del tile nella mappa rispetto al player è riferita al punto in
		//alto a sinistra della hitbox. Per mantenere la stessa distanza, dobbiamo aggiungere questo offset
		int xPosOnScreen = PlayerView.xOnScreen - distanceX + PlayerView.getXOffset();	
		int yPosOnScreen = PlayerView.yOnScreen - distanceY + PlayerView.getYOffset();
		
		BufferedImage img = tileset.getTile(numberInTileset).getImage();
		g2.drawImage(img, xPosOnScreen, yPosOnScreen, null);
	}

}
