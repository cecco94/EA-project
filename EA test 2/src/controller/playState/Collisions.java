package controller.playState;


import java.util.ArrayList;

import controller.IController;
import controller.playState.entityController.EntityController;
import controller.playState.entityController.enemyController.EnemyController;


public class Collisions {

	public final int RIGHT = 0;
	public final int LEFT = 1;
	public final int UP = 2;
	public final int DOWN = 3;
	
	private final int MAP_THIRD_LAYER = 2;
	
	private IController control;
	
	public Collisions(IController c) {
		control = c;
	}
	
	public boolean canMoveLeft(Hitbox hitboxPlayer) {
		boolean canMove = true;
		int roomIndex = control.getPlay().getCurrentroomIndex();
		
		//in quale quadrato della mappa si trova il punto in alto a sinistra della hitbox
		int playerCol = (int)(hitboxPlayer.x)/control.getTileSize();
		int playerRow = (int)(hitboxPlayer.y)/control.getTileSize();
		
		//se si trova su una sola riga controlliamo solo il tile dove si trova
		if(hitboxPlayer.y + hitboxPlayer.height < (playerRow+1)*control.getTileSize()) {
			int tile = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow][playerCol];
			if(tile > 0) {		//se il tile è solido	
				
				Hitbox hitboxToCheck = getRectOfTile(tile, playerRow, playerCol);
				
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;		
			}
		}
		// se si trova a metà tra una riga e l'altra ci sono due tile da controllare
		else {
			int tile = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow][playerCol];
			int tileDown = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow + 1][playerCol];
			
			if(tile > 0 || tileDown > 0) {
		
				Hitbox hitboxToCheck1 = getRectOfTile(tile, playerRow, playerCol);
				Hitbox hitboxToCheck2 = getRectOfTile(tileDown, playerRow+1, playerCol);		
				
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}
		}	
		return canMove;
	}

	public boolean canMoveRight(Hitbox hitboxEntity) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int roomIndex = control.getPlay().getCurrentroomIndex();

		//in quale quadrato della mappa si trova il punto in alto a destra della hitbox
		int playerCol = (int)((hitboxEntity.x) + hitboxEntity.width)/control.getTileSize();
		int playerRow = (int)(hitboxEntity.y)/control.getTileSize();
		
		//se si trova su una sola riga controlliamo solo il tile dove si trova il punto in alto a destra
		if(hitboxEntity.y + hitboxEntity.height < (playerRow + 1)*control.getTileSize()) { 
			int tile = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow][playerCol];
			if(tile > 0) {																				
				Hitbox hitboxToCheck = getRectOfTile(tile, playerRow, playerCol);	
				if(hitboxEntity.intersects(hitboxToCheck))
					canMove = false;		
			}
		}
		// se il rect si trova a metà tra una linea e l'altra ci sono due tile da controllare	
		else {
			int tile = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow][playerCol];
			int tileDown = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow + 1][playerCol];
			
			if(tile > 0 || tileDown > 0) {	
				Hitbox hitboxToCheck1 = getRectOfTile(tile, playerRow, playerCol);
				Hitbox hitboxToCheck2 = getRectOfTile(tileDown, playerRow+1, playerCol);	
				if(hitboxEntity.intersects(hitboxToCheck1) || hitboxEntity.intersects(hitboxToCheck2))
					canMove = false;				
			}
		}
		return canMove;
	}

	public boolean canMoveUp(Hitbox hitboxEntity) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int roomIndex = control.getPlay().getCurrentroomIndex();
		
		int playerCol = (int)(hitboxEntity.x)/control.getTileSize();
		int playerRow = (int)(hitboxEntity.y)/control.getTileSize();
		
		//se si trova su una sola colonna controlliamo solo il tile dove si trova
		if(hitboxEntity.x + hitboxEntity.width < (playerCol+1)*control.getTileSize()) {
			int tile = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow][playerCol];
			if(tile > 0) {																				
				Hitbox hitboxToCheck = getRectOfTile(tile, playerRow, playerCol);	
				if(hitboxEntity.intersects(hitboxToCheck))
					canMove = false;	
			}
		}
		
		else {
			int tile = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow][playerCol];
			int tileRight = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow][playerCol + 1];
			
			if(tile > 0 || tileRight > 0) {	
				Hitbox hitboxToCheck1 = getRectOfTile(tile, playerRow, playerCol);
				Hitbox hitboxToCheck2 = getRectOfTile(tileRight, playerRow, playerCol + 1);	
				if(hitboxEntity.intersects(hitboxToCheck1) || hitboxEntity.intersects(hitboxToCheck2))
					canMove = false;				
			}	
		}
		return canMove;
	
	}

	public boolean canMoveDown (Hitbox hitboxEntity) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int roomIndex = control.getPlay().getCurrentroomIndex();
		
		//in quale riga/colonna si trova il punto in basso a sinistra della hitbox
		int playerCol = (int)(hitboxEntity.x)/control.getTileSize();
		int playerRow = (int)((hitboxEntity.y) + hitboxEntity.height)/control.getTileSize();
		
		//se si trova su una sola colonna controlliamo solo il tile dove si trova
		if(hitboxEntity.x + hitboxEntity.width < (playerCol+1)*control.getTileSize()) {
			int tile = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow][playerCol];
			if(tile > 0) {																				
				Hitbox hitboxToCheck = getRectOfTile(tile, playerRow, playerCol);	
				if(hitboxEntity.intersects(hitboxToCheck))
					canMove = false;	
			}
		}
		
		else {
			int tile = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow][playerCol];
			int tileRight = control.getModel().getMap().getLayer(roomIndex, MAP_THIRD_LAYER)[playerRow][playerCol + 1];
			
			if(tile > 0 || tileRight > 0) {	
				Hitbox hitboxToCheck1 = getRectOfTile(tile, playerRow, playerCol);
				Hitbox hitboxToCheck2 = getRectOfTile(tileRight, playerRow, playerCol + 1);	
				if(hitboxEntity.intersects(hitboxToCheck1) || hitboxEntity.intersects(hitboxToCheck2))
					canMove = false;				
			}	
		}
		return canMove;
	
	}
	//prende la hitbox corrispondente al numero del tile e la trasla nella posizione dove si trova il tile
	private Hitbox getRectOfTile(int tile, int playerRow, int playerCol) {
		
		int x = (int)(control.getModel().getTilesetModel().getTile(tile).getHitbox().x);
		x += playerCol*control.getTileSize();	
		
		int y = (int)(control.getModel().getTilesetModel().getTile(tile).getHitbox().y);
		y += playerRow*control.getTileSize();
		
		int width = (int)(control.getModel().getTilesetModel().getTile(tile).getHitbox().width);
		int height = (int)(control.getModel().getTilesetModel().getTile(tile).getHitbox().height);
		
		Hitbox hitboxTile = new Hitbox(x, y, width, height);
		return hitboxTile;
	}

	//prende la lista degli npc e dei nemici e vede se collide con uno di loro, a parte se stesso
	public boolean isCollisionInEntityList(Hitbox hitboxEntity) {
		int roomIndex = control.getPlay().getCurrentroomIndex();
		int numCollision = 0;
		
		ArrayList<EntityController> npc = control.getPlay().getRoom(roomIndex).getNPC();
		for(int i = 0; i < npc.size(); i++) {
			if(hitboxEntity.intersects(npc.get(i).getHitbox()))
				numCollision++;
		}
		
		ArrayList<EnemyController> enemy = control.getPlay().getRoom(roomIndex).getEnemy();
		for(int i = 0; i < enemy.size(); i++) {
			if(hitboxEntity.intersects(enemy.get(i).getHitbox()))
				numCollision++;
		}
		
		//infine controlla anche se collide col player
		if(hitboxEntity.intersects(control.getPlay().getPlayer().getHitbox())) 
			numCollision++;				
		
		//se il numero di collisioni è uno, vuol dire che l'entità collide solo con se stessa quindi va bene
		//altrimenti vuol dire che collide
		if(numCollision > 1) 
			return true;
		
		return false;
	}
	
	//metodo usato dai proiettili per vedere se hanno colpito qualcuno
	public EntityController bulletHittedEntity(Hitbox hitboxEntity, EntityController owner) {
		int roomIndex = control.getPlay().getCurrentroomIndex();
		EntityController target = null;
		
		ArrayList<EntityController> npc = control.getPlay().getRoom(roomIndex).getNPC();
		for(int i = 0; i < npc.size(); i++) {
			if(hitboxEntity.intersects(npc.get(i).getHitbox()))
				if(npc.get(i) != owner)		
					target = npc.get(i);	
		}
		
		ArrayList<EnemyController> enemy = control.getPlay().getRoom(roomIndex).getEnemy();
		for(int i = 0; i < enemy.size(); i++) {
			if(hitboxEntity.intersects(enemy.get(i).getHitbox()))
				if(enemy.get(i) != owner) 
					target = enemy.get(i);
		}	
		
		if(hitboxEntity.intersects(control.getPlay().getPlayer().getHitbox())) 
			if(control.getPlay().getPlayer() != owner) 
				target = control.getPlay().getPlayer();
		
		return target;
	}
		
	//usato quando il giocatore attacca, ritorna il nemico colpito
	public EnemyController isCollisionDuringPlayerAttack(Hitbox attackHitbox) {
		int roomIndex = control.getPlay().getCurrentroomIndex();
		EnemyController target = null;
		
		ArrayList<EnemyController> enemy = control.getPlay().getRoom(roomIndex).getEnemy();
		for(int i = 0; i < enemy.size(); i++) {
			if(attackHitbox.intersects(enemy.get(i).getHitbox()))
				target = enemy.get(i);
		}	
		
		return target;
	}

}
