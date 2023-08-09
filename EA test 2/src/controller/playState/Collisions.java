package controller.playState;

import java.awt.Rectangle;

import controller.IController;
import model.mappa.Map;
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
		//in quale quadrato della mappa si trova il punto in alto a sinistra della hitbox
		int colonnaPlayer = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int rigaPlayer = hitboxPlayer.y/GamePanel.TILES_SIZE;
		
		//se si trova su una sola riga controlliamo solo il tile dove si trova
		if(hitboxPlayer.y + hitboxPlayer.height < (rigaPlayer+1)*GamePanel.TILES_SIZE) {
			int tile = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
			if(tile > 0) {										//se il tile è solido	
				
				Rectangle hitboxToCheck = getRectOfTile(tile, rigaPlayer, colonnaPlayer);
				
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;		
			}
		}
		// se si trova a metà tra una linea e l'altra ci sono due tile da controllare
		else {
			int tileSopra = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileSotto = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer + 1][colonnaPlayer];
			
			if(tileSopra > 0 || tileSotto > 0) {
		
				Rectangle hitboxToCheck1 = getRectOfTile(tileSopra, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileSotto, rigaPlayer+1, colonnaPlayer);		
				
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}
		}	
		return canMove;
	}

	public boolean canMoveRight(Rectangle hitboxPlayer) {
		boolean canMove = true;
		//in quale quadrato della mappa si trova il punto in alto a destra della hitbox
		int colonnaPlayer = (hitboxPlayer.x + hitboxPlayer.width)/GamePanel.TILES_SIZE;	
		int rigaPlayer = hitboxPlayer.y/GamePanel.TILES_SIZE;
		
		//se si trova su una sola riga controlliamo solo il tile dove si trova il punto in alto a destra
		if(hitboxPlayer.y + hitboxPlayer.height < (rigaPlayer+1)*GamePanel.TILES_SIZE) { 
			int tile = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
			if(tile > 0) {																				
				Rectangle hitboxToCheck = getRectOfTile(tile, rigaPlayer, colonnaPlayer);	
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;		
			}
		}
		// se il rect si trova a metà tra una linea e l'altra ci sono due tile da controllare	
		else {
			int tileSopra = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileSotto = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer + 1][colonnaPlayer];
			
			if(tileSopra > 0 || tileSotto > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tileSopra, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileSotto, rigaPlayer+1, colonnaPlayer);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}
		}
		return canMove;
	}

	public boolean canMoveUp(Rectangle hitboxPlayer) {
		boolean canMove = true;
		
		int colonnaPlayer = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int rigaPlayer = hitboxPlayer.y/GamePanel.TILES_SIZE;
		
		//se si trova su una sola colonna controlliamo solo il tile dove si trova
		if(hitboxPlayer.x + hitboxPlayer.width < (colonnaPlayer+1)*GamePanel.TILES_SIZE) {
			int tile = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
			if(tile > 0) {																				
				Rectangle hitboxToCheck = getRectOfTile(tile, rigaPlayer, colonnaPlayer);	
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;	
			}
		}
		
		else {
			int tileSinistra = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileDestra = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer + 1];
			
			if(tileSinistra > 0 || tileDestra > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tileSinistra, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileDestra, rigaPlayer, colonnaPlayer + 1);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}	
		}
		return canMove;
	
	}

	public boolean canMoveDown(Rectangle hitboxPlayer) {
boolean canMove = true;
		
		int colonnaPlayer = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int rigaPlayer = (hitboxPlayer.y + hitboxPlayer.height)/GamePanel.TILES_SIZE;
		
		//se si trova su una sola colonna controlliamo solo il tile dove si trova
		if(hitboxPlayer.x + hitboxPlayer.width < (colonnaPlayer+1)*GamePanel.TILES_SIZE) {
			int tile = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
			if(tile > 0) {																				
				Rectangle hitboxToCheck = getRectOfTile(tile, rigaPlayer, colonnaPlayer);	
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;	
			}
		}
		
		else {
			int tileSinistra = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileDestra = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer + 1];
			
			if(tileSinistra > 0 || tileDestra > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tileSinistra, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileDestra, rigaPlayer, colonnaPlayer + 1);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}	
		}
		return canMove;
	
	}
	
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

////se si trova su un solo tile controlliamo solo il tile dove sta		
//if(hitboxPlayer.y + hitboxPlayer.height < (rigaPlayer+1)*GamePanel.TILES_SIZE && 
//					hitboxPlayer.x + hitboxPlayer.width < (colonnaPlayer)*GamePanel.TILES_SIZE) {	
//	int tile = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
//	if(tile > 0) {										//se il tile è solido	
//		int x = control.getModel().getTilesetModel().getTile(tile).getHitbox().x;
//		x += colonnaPlayer*GamePanel.TILES_SIZE;	
//		int y = control.getModel().getTilesetModel().getTile(tile).getHitbox().y;
//		y += rigaPlayer*GamePanel.TILES_SIZE;
//		int width = control.getModel().getTilesetModel().getTile(tile).getHitbox().width;
//		int height = control.getModel().getTilesetModel().getTile(tile).getHitbox().height;
//		Rectangle hitboxToCheck = new Rectangle(x, y, width, height);
//		
//		if(hitboxPlayer.intersects(hitboxToCheck))
//			canMove = false;		
//	}
//}
////se si trova su una riga tra due colonne bisogna controllare il tile successivo
//else if(hitboxPlayer.y + hitboxPlayer.height < (rigaPlayer+1)*GamePanel.TILES_SIZE && 
//						 hitboxPlayer.x + hitboxPlayer.width > (colonnaPlayer+1)*GamePanel.TILES_SIZE) {
//	int tile = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer + 1];
//
//	if(tile > 0) {										//se il tile è solido	
//		int x = control.getModel().getTilesetModel().getTile(tile).getHitbox().x;
//		x += (colonnaPlayer + 1)*GamePanel.TILES_SIZE;	
//		int y = control.getModel().getTilesetModel().getTile(tile).getHitbox().y;
//		y += rigaPlayer*GamePanel.TILES_SIZE;
//		int width = control.getModel().getTilesetModel().getTile(tile).getHitbox().width;
//		int height = control.getModel().getTilesetModel().getTile(tile).getHitbox().height;
//		Rectangle hitboxToCheck = new Rectangle(x, y, width, height);
//		
//		if(hitboxPlayer.intersects(hitboxToCheck))
//			canMove = false;		
//	}
//}
//// se si trova in una colonna ma tra due righe bisogna controllare il tile dove si trova e quello sotto
//else if(hitboxPlayer.y + hitboxPlayer.height > (rigaPlayer+1)*GamePanel.TILES_SIZE &&
//						 hitboxPlayer.x + hitboxPlayer.width < (colonnaPlayer)*GamePanel.TILES_SIZE) {
//	
//	int tileSopra = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
//	int tileSotto = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer + 1][colonnaPlayer];
//	
//	if(tileSopra > 0 || tileSotto > 0) {
//		int x1 = control.getModel().getTilesetModel().getTile(tileSopra).getHitbox().x;
//		x1 += colonnaPlayer*GamePanel.TILES_SIZE;	
//		int y1 = control.getModel().getTilesetModel().getTile(tileSopra).getHitbox().y;
//		y1 += rigaPlayer*GamePanel.TILES_SIZE;
//		int width1 = control.getModel().getTilesetModel().getTile(tileSopra).getHitbox().width;
//		int height1 = control.getModel().getTilesetModel().getTile(tileSopra).getHitbox().height;
//		Rectangle hitboxToCheck1 = new Rectangle(x1, y1, width1, height1);
//		
//		int x2 = control.getModel().getTilesetModel().getTile(tileSotto).getHitbox().x;
//		x2 += colonnaPlayer*GamePanel.TILES_SIZE;	
//		int y2 = control.getModel().getTilesetModel().getTile(tileSotto).getHitbox().y;
//		y2 += (rigaPlayer + 1)*GamePanel.TILES_SIZE;
//		int width2 = control.getModel().getTilesetModel().getTile(tileSotto).getHitbox().width;
//		int height2 = control.getModel().getTilesetModel().getTile(tileSotto).getHitbox().height;
//		Rectangle hitboxToCheck2 = new Rectangle(x2, y2, width2, height2);
//		
//		if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
//			canMove = false;				
//	}
//	
//}


//else {
//	int tileSopra = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer][colonnaPlayer];
//	int tileSotto = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, Map.SECONDO_STRATO)[rigaPlayer + 1][colonnaPlayer];
//	
//	if(tileSopra > 0 || tileSotto > 0) {
//		int x1 = control.getModel().getTilesetModel().getTile(tileSopra).getHitbox().x;
//		x1 += colonnaPlayer*GamePanel.TILES_SIZE;	
//		int y1 = control.getModel().getTilesetModel().getTile(tileSopra).getHitbox().y;
//		y1 += rigaPlayer*GamePanel.TILES_SIZE;
//		int width1 = control.getModel().getTilesetModel().getTile(tileSopra).getHitbox().width;
//		int height1 = control.getModel().getTilesetModel().getTile(tileSopra).getHitbox().height;
//		Rectangle hitboxToCheck1 = new Rectangle(x1, y1, width1, height1);
//		
//		int x2 = control.getModel().getTilesetModel().getTile(tileSotto).getHitbox().x;
//		x2 += colonnaPlayer*GamePanel.TILES_SIZE;	
//		int y2 = control.getModel().getTilesetModel().getTile(tileSotto).getHitbox().y;
//		y2 += (rigaPlayer + 1)*GamePanel.TILES_SIZE;
//		int width2 = control.getModel().getTilesetModel().getTile(tileSotto).getHitbox().width;
//		int height2 = control.getModel().getTilesetModel().getTile(tileSotto).getHitbox().height;
//		Rectangle hitboxToCheck2 = new Rectangle(x2, y2, width2, height2);
//		
//		if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
//			canMove = false;				
//	}
//}	





