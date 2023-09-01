package controller.playState;

import java.awt.Rectangle;
import java.util.ArrayList;

import controller.IController;
import controller.playState.entityController.EntityController;

import model.mappa.Map;
import model.mappa.Stanze;

import view.main.GamePanel;

public class Collisions {

	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	private IController control;
	
	public Collisions(IController c) {
		control = c;
	}
	
	public boolean canMoveLeft(Rectangle hitboxPlayer) {
		boolean canMove = true;
		int roomIndex = Stanze.stanzaAttuale.indiceMappa;
		
		//in quale quadrato della mappa si trova il punto in alto a sinistra della hitbox
		int playerCol = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int playerRow = hitboxPlayer.y/GamePanel.TILES_SIZE;
		
		//se si trova su una sola riga controlliamo solo il tile dove si trova
		if(hitboxPlayer.y + hitboxPlayer.height < (playerRow+1)*GamePanel.TILES_SIZE) {
			int tile = control.getModel().getMappa().getStrato(roomIndex, Map.TERZO_STRATO)[playerRow][playerCol];
			if(tile > 0) {		//se il tile è solido	
				
				Rectangle hitboxToCheck = getRectOfTile(tile, playerRow, playerCol);
				
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;		
			}
		}
		// se si trova a metà tra una riga e l'altra ci sono due tile da controllare
		else {
			int tile = control.getModel().getMappa().getStrato(roomIndex, Map.TERZO_STRATO)[playerRow][playerCol];
			int tileDown = control.getModel().getMappa().getStrato(roomIndex, Map.TERZO_STRATO)[playerRow + 1][playerCol];
			
			if(tile > 0 || tileDown > 0) {
		
				Rectangle hitboxToCheck1 = getRectOfTile(tile, playerRow, playerCol);
				Rectangle hitboxToCheck2 = getRectOfTile(tileDown, playerRow+1, playerCol);		
				
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}
		}	
		return canMove;
	}

	public boolean canMoveRight(Rectangle hitboxPlayer) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int roomIndex = Stanze.stanzaAttuale.indiceMappa;

		//in quale quadrato della mappa si trova il punto in alto a destra della hitbox
		int playerCol = (hitboxPlayer.x + hitboxPlayer.width)/GamePanel.TILES_SIZE;	
		int playerRow = hitboxPlayer.y/GamePanel.TILES_SIZE;
		
		//se si trova su una sola riga controlliamo solo il tile dove si trova il punto in alto a destra
		if(hitboxPlayer.y + hitboxPlayer.height < (playerRow+1)*GamePanel.TILES_SIZE) { 
			int tile = control.getModel().getMappa().getStrato(roomIndex, Map.TERZO_STRATO)[playerRow][playerCol];
			if(tile > 0) {																				
				Rectangle hitboxToCheck = getRectOfTile(tile, playerRow, playerCol);	
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;		
			}
		}
		// se il rect si trova a metà tra una linea e l'altra ci sono due tile da controllare	
		else {
			int tile = control.getModel().getMappa().getStrato(roomIndex,Map.TERZO_STRATO)[playerRow][playerCol];
			int tileDown = control.getModel().getMappa().getStrato(roomIndex, Map.TERZO_STRATO)[playerRow + 1][playerCol];
			
			if(tile > 0 || tileDown > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tile, playerRow, playerCol);
				Rectangle hitboxToCheck2 = getRectOfTile(tileDown, playerRow+1, playerCol);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}
		}
		return canMove;
	}

	public boolean canMoveUp(Rectangle hitboxPlayer) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int roomIndex = Stanze.stanzaAttuale.indiceMappa;
		
		int playerCol = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int playerRow = hitboxPlayer.y/GamePanel.TILES_SIZE;
		
		//se si trova su una sola colonna controlliamo solo il tile dove si trova
		if(hitboxPlayer.x + hitboxPlayer.width < (playerCol+1)*GamePanel.TILES_SIZE) {
			int tile = control.getModel().getMappa().getStrato(roomIndex, Map.TERZO_STRATO)[playerRow][playerCol];
			if(tile > 0) {																				
				Rectangle hitboxToCheck = getRectOfTile(tile, playerRow, playerCol);	
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;	
			}
		}
		
		else {
			int tile = control.getModel().getMappa().getStrato(roomIndex,Map.TERZO_STRATO)[playerRow][playerCol];
			int tileRight = control.getModel().getMappa().getStrato(roomIndex, Map.TERZO_STRATO)[playerRow][playerCol + 1];
			
			if(tile > 0 || tileRight > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tile, playerRow, playerCol);
				Rectangle hitboxToCheck2 = getRectOfTile(tileRight, playerRow, playerCol + 1);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}	
		}
		return canMove;
	
	}

	public boolean canMoveDown (Rectangle hitboxPlayer) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int roomIndex = Stanze.stanzaAttuale.indiceMappa;
		
		//in quale riga/colonna si trova il punto in basso a sinistra della hitbox
		int playerCol = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int playerRow = (hitboxPlayer.y + hitboxPlayer.height)/GamePanel.TILES_SIZE;
		
		//se si trova su una sola colonna controlliamo solo il tile dove si trova
		if(hitboxPlayer.x + hitboxPlayer.width < (playerCol+1)*GamePanel.TILES_SIZE) {
			int tile = control.getModel().getMappa().getStrato(roomIndex, Map.TERZO_STRATO)[playerRow][playerCol];
			if(tile > 0) {																				
				Rectangle hitboxToCheck = getRectOfTile(tile, playerRow, playerCol);	
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;	
			}
		}
		
		else {
			int tile = control.getModel().getMappa().getStrato(roomIndex, Map.TERZO_STRATO)[playerRow][playerCol];
			int tileRight = control.getModel().getMappa().getStrato(roomIndex, Map.TERZO_STRATO)[playerRow][playerCol + 1];
			
			if(tile > 0 || tileRight > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tile, playerRow, playerCol);
				Rectangle hitboxToCheck2 = getRectOfTile(tileRight, playerRow, playerCol + 1);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}	
		}
		return canMove;
	
	}
	//prende la hitbox corrispondente al numero del tile e la trasla nella posizione dove si trova il tile
	private Rectangle getRectOfTile(int tile, int playerRow, int playerCol) {
		
		int x = control.getModel().getTilesetModel().getTile(tile).getHitbox().x;
		x += playerCol*GamePanel.TILES_SIZE;	
		
		int y = control.getModel().getTilesetModel().getTile(tile).getHitbox().y;
		y += playerRow*GamePanel.TILES_SIZE;
		
		int width = control.getModel().getTilesetModel().getTile(tile).getHitbox().width;
		int height = control.getModel().getTilesetModel().getTile(tile).getHitbox().height;
		
		Rectangle hitboxTile = new Rectangle(x, y, width, height);
		return hitboxTile;
	}

	//prende la lista degli npc e dei nemici e vede se collide con uno di loro, a parte se stesso
	public boolean checkCollisionInEntityList(Rectangle hitboxEntity) {
		int roomIndex = Stanze.stanzaAttuale.indiceMappa;
		int numCollision = 0;
		
		ArrayList<EntityController> npc = control.getPlay().getRoom(roomIndex).getNPC();
		for(int i = 0; i < npc.size(); i++) {
			if(hitboxEntity.intersects(npc.get(i).getHitbox()))
				numCollision++;
		}
		
		ArrayList<EntityController> enemy = control.getPlay().getRoom(roomIndex).getEnemy();
		for(int i = 0; i < enemy.size(); i++) {
			if(hitboxEntity.intersects(enemy.get(i).getHitbox()))
				numCollision++;
		}
		
		//infine controlla anche se collide col player
		if(hitboxEntity.intersects(control.getPlay().getPlayer().getHitbox())) {
			numCollision++;			
		}		
		
		//se il numero di collisioni è uno, vuol dire che l'entità collide solo con se stessa quindi va bene
		//altrimenti vuol dire che collide
		if(numCollision > 1) {
			return true;
		}
		
		return false;
	}
	
	//metodo usato dai proiettili per vedere se ha colpito qualcuno
	public boolean hitEntity(Rectangle hitboxEntity, EntityController owner) {
		int roomIndex = Stanze.stanzaAttuale.indiceMappa;
		int numCollision = 0;
		
		ArrayList<EntityController> npc = control.getPlay().getRoom(roomIndex).getNPC();
		for(int i = 0; i < npc.size(); i++) {
			if(hitboxEntity.intersects(npc.get(i).getHitbox()))
				if(npc.get(i) != owner)		//se colpisce vhi ha lanciato il proiettile non succede nulla
					numCollision++;
		}
		
		ArrayList<EntityController> enemy = control.getPlay().getRoom(roomIndex).getEnemy();
		for(int i = 0; i < enemy.size(); i++) {
			if(hitboxEntity.intersects(enemy.get(i).getHitbox()))
				if(npc.get(i) != owner)
					numCollision++;
		}	
		
		if(hitboxEntity.intersects(control.getPlay().getPlayer().getHitbox())) 
			if(control.getPlay().getPlayer() != owner)
				numCollision++;
		
		if(numCollision > 0) {
			return true;
		}
		
		return false;
	}
		

}
