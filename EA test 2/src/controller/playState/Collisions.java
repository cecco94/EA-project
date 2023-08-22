package controller.playState;

import java.awt.Rectangle;

import controller.IController;
import model.mappa.Map;
import model.mappa.Stanze;
import view.main.GamePanel;

public class Collisions {

	public static final int DESTRA = 0;
	public static final int SINISTRA = 1;
	public static final int SU = 2;
	public static final int SOTTO = 3;
	private IController control;
	
	public Collisions(IController c) {
		control = c;
	}
	
	public boolean canMoveLeft(Rectangle hitboxPlayer) {
		boolean canMove = true;
		int stanza = Stanze.stanzaAttuale.indiceNellaMappa;
		//in quale quadrato della mappa si trova il punto in alto a sinistra della hitbox
		int colonnaPlayer = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int rigaPlayer = hitboxPlayer.y/GamePanel.TILES_SIZE;
		
		//se si trova su una sola riga controlliamo solo il tile dove si trova
		if(hitboxPlayer.y + hitboxPlayer.height < (rigaPlayer+1)*GamePanel.TILES_SIZE) {
			int tile = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			if(tile > 0) {										//se il tile è solido	
				
				Rectangle hitboxToCheck = getRectOfTile(tile, rigaPlayer, colonnaPlayer);
				
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;		
			}
		}
		// se si trova a metà tra una linea e l'altra ci sono due tile da controllare
		else {
			int tileSopra = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileSotto = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer + 1][colonnaPlayer];
			
			if(tileSopra > 0 || tileSotto > 0) {
		
				Rectangle hitboxToCheck1 = getRectOfTile(tileSopra, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileSotto, rigaPlayer+1, colonnaPlayer);		
				
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}
		}	
		return canMove;
	}

	public boolean canMoveRight(Rectangle hitboxPlayer) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int stanza = Stanze.stanzaAttuale.indiceNellaMappa;

		//in quale quadrato della mappa si trova il punto in alto a destra della hitbox
		int colonnaPlayer = (hitboxPlayer.x + hitboxPlayer.width)/GamePanel.TILES_SIZE;	
		int rigaPlayer = hitboxPlayer.y/GamePanel.TILES_SIZE;
		
		//se si trova su una sola riga controlliamo solo il tile dove si trova il punto in alto a destra
		if(hitboxPlayer.y + hitboxPlayer.height < (rigaPlayer+1)*GamePanel.TILES_SIZE) { 
			int tile = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			if(tile > 0) {																				
				Rectangle hitboxToCheck = getRectOfTile(tile, rigaPlayer, colonnaPlayer);	
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;		
			}
		}
		// se il rect si trova a metà tra una linea e l'altra ci sono due tile da controllare	
		else {
			int tileSopra = control.getModel().getMappa().getStrato(stanza,Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileSotto = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer + 1][colonnaPlayer];
			
			if(tileSopra > 0 || tileSotto > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tileSopra, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileSotto, rigaPlayer+1, colonnaPlayer);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}
		}
		return canMove;
	}

	public boolean canMoveUp(Rectangle hitboxPlayer) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int stanza = Stanze.stanzaAttuale.indiceNellaMappa;
		
		int colonnaPlayer = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int rigaPlayer = hitboxPlayer.y/GamePanel.TILES_SIZE;
		
		//se si trova su una sola colonna controlliamo solo il tile dove si trova
		if(hitboxPlayer.x + hitboxPlayer.width < (colonnaPlayer+1)*GamePanel.TILES_SIZE) {
			int tile = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			if(tile > 0) {																				
				Rectangle hitboxToCheck = getRectOfTile(tile, rigaPlayer, colonnaPlayer);	
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;	
			}
		}
		
		else {
			int tileSinistra = control.getModel().getMappa().getStrato(stanza,Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileDestra = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer + 1];
			
			if(tileSinistra > 0 || tileDestra > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tileSinistra, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileDestra, rigaPlayer, colonnaPlayer + 1);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}	
		}
		return canMove;
	
	}

	public boolean canMoveDown (Rectangle hitboxPlayer) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int stanza = Stanze.stanzaAttuale.indiceNellaMappa;
		
		int colonnaPlayer = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int rigaPlayer = (hitboxPlayer.y + hitboxPlayer.height)/GamePanel.TILES_SIZE;
		
		//se si trova su una sola colonna controlliamo solo il tile dove si trova
		if(hitboxPlayer.x + hitboxPlayer.width < (colonnaPlayer+1)*GamePanel.TILES_SIZE) {
			int tile = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			if(tile > 0) {																				
				Rectangle hitboxToCheck = getRectOfTile(tile, rigaPlayer, colonnaPlayer);	
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;	
			}
		}
		
		else {
			int tileSinistra = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileDestra = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer + 1];
			
			if(tileSinistra > 0 || tileDestra > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tileSinistra, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileDestra, rigaPlayer, colonnaPlayer + 1);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}	
		}
		return canMove;
	
	}
	//prende la hitbox corrispondente al tile e la trasla nella posizione dove si trova il tile
	private Rectangle getRectOfTile(int tile, int rigaPlayer, int colonnaPlayer) {
		int x = control.getModel().getTilesetModel().getTile(tile).getHitbox().x;
		x += colonnaPlayer*GamePanel.TILES_SIZE;	
		int y = control.getModel().getTilesetModel().getTile(tile).getHitbox().y;
		y += rigaPlayer*GamePanel.TILES_SIZE;
		int width = control.getModel().getTilesetModel().getTile(tile).getHitbox().width;
		int height = control.getModel().getTilesetModel().getTile(tile).getHitbox().height;
		Rectangle hitboxTile = new Rectangle(x, y, width, height);
		return hitboxTile;
	}
	
}
