package controller.playState;

import java.awt.Rectangle;
import java.util.ArrayList;

import controller.IController;
import controller.playState.entityController.EntityController;
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
		int stanza = Stanze.stanzaAttuale.indiceMappa;
		
		//in quale quadrato della mappa si trova il punto in alto a sinistra della hitbox
		int colonnaPlayer = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int rigaPlayer = hitboxPlayer.y/GamePanel.TILES_SIZE;
		
		//se si trova su una sola riga controlliamo solo il tile dove si trova
		if(hitboxPlayer.y + hitboxPlayer.height < (rigaPlayer+1)*GamePanel.TILES_SIZE) {
			int tile = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			if(tile > 0) {		//se il tile è solido	
				
				Rectangle hitboxToCheck = getRectOfTile(tile, rigaPlayer, colonnaPlayer);
				
				if(hitboxPlayer.intersects(hitboxToCheck))
					canMove = false;		
			}
		}
		// se si trova a metà tra una riga e l'altra ci sono due tile da controllare
		else {
			int tile = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileSotto = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer + 1][colonnaPlayer];
			
			if(tile > 0 || tileSotto > 0) {
		
				Rectangle hitboxToCheck1 = getRectOfTile(tile, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileSotto, rigaPlayer+1, colonnaPlayer);		
				
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}
		}	
		return canMove;
	}

	public boolean canMoveRight(Rectangle hitboxPlayer) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int stanza = Stanze.stanzaAttuale.indiceMappa;

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
			int tile = control.getModel().getMappa().getStrato(stanza,Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileSotto = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer + 1][colonnaPlayer];
			
			if(tile > 0 || tileSotto > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tile, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileSotto, rigaPlayer+1, colonnaPlayer);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}
		}
		return canMove;
	}

	public boolean canMoveUp(Rectangle hitboxPlayer) throws ArrayIndexOutOfBoundsException {
		boolean canMove = true;
		int stanza = Stanze.stanzaAttuale.indiceMappa;
		
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
		int stanza = Stanze.stanzaAttuale.indiceMappa;
		
		//in quale riga/colonna si trova il punto in basso a sinistra della hitbox
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
			int tile = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer];
			int tileDestra = control.getModel().getMappa().getStrato(stanza, Map.TERZO_STRATO)[rigaPlayer][colonnaPlayer + 1];
			
			if(tile > 0 || tileDestra > 0) {	
				Rectangle hitboxToCheck1 = getRectOfTile(tile, rigaPlayer, colonnaPlayer);
				Rectangle hitboxToCheck2 = getRectOfTile(tileDestra, rigaPlayer, colonnaPlayer + 1);	
				if(hitboxPlayer.intersects(hitboxToCheck1) || hitboxPlayer.intersects(hitboxToCheck2))
					canMove = false;				
			}	
		}
		return canMove;
	
	}
	//prende la hitbox corrispondente al numero del tile e la trasla nella posizione dove si trova il tile
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

	//prende la lista degli npc e dei nemici e vede se collide con uno di loro, a parte se stesso
	public boolean checkCollisionInEntityList(Rectangle hitboxEntity) {
		int indiceStanza = Stanze.stanzaAttuale.indiceMappa;
		int numCollisioni = 0;
		
		ArrayList<EntityController> npc = control.getPlay().getRoom(indiceStanza).getNPC();
		for(int i = 0; i < npc.size(); i++) {
			if(hitboxEntity.intersects(npc.get(i).getHitbox()))
				numCollisioni++;
		}
		
		ArrayList<EntityController> enemy = control.getPlay().getRoom(indiceStanza).getEnemy();
		for(int i = 0; i < enemy.size(); i++) {
			if(hitboxEntity.intersects(enemy.get(i).getHitbox()))
				numCollisioni++;
		}
		
		//infine controlla anche se collide col player
		if(hitboxEntity.intersects(control.getPlay().getPlayer().getHitbox())) {
			numCollisioni++;			
		}		
		
		//se il numero di collisioni è uno, vuol dire che l'entità collide solo con se stessa quindi va bene
		//altrimenti vuol dire che collide
		if(numCollisioni > 1) {
			return true;
		}
		
		return false;
	}
	
	//metodo usato dai proiettili per vedere se ha colpito qualcuno
	public boolean hitEntity(Rectangle hitboxEntity, EntityController lanciatore) {
		int indiceStanza = Stanze.stanzaAttuale.indiceMappa;
		int numCollisioni = 0;
		
		ArrayList<EntityController> npc = control.getPlay().getRoom(indiceStanza).getNPC();
		for(int i = 0; i < npc.size(); i++) {
			if(hitboxEntity.intersects(npc.get(i).getHitbox()))
				if(npc.get(i) != lanciatore)		//se colpisce vhi ha lanciato il proiettile non succede nulla
					numCollisioni++;
		}
		
		ArrayList<EntityController> enemy = control.getPlay().getRoom(indiceStanza).getEnemy();
		for(int i = 0; i < enemy.size(); i++) {
			if(hitboxEntity.intersects(enemy.get(i).getHitbox()))
				if(npc.get(i) != lanciatore)
					numCollisioni++;
		}	
		
		if(hitboxEntity.intersects(control.getPlay().getPlayer().getHitbox())) 
			if(control.getPlay().getPlayer() != lanciatore)
				numCollisioni++;
		
		if(numCollisioni > 0) {
			return true;
		}
		
		return false;
	}
		

}



//public boolean notEntityCollisionUp(Rectangle hitboxEntity) {
//	boolean canMove = true;
//	int indiceStanza = Stanze.stanzaAttuale.indiceMappa;
//			
//	//prendiamo come riferimento il punto in alto a sinistra
//	int colonnaEntity = hitboxEntity.x/GamePanel.TILES_SIZE;
//	int rigaEntity = hitboxEntity.y/GamePanel.TILES_SIZE;
//	
//	//se si trova su una sola colonna controlliamo solo il tile dove si trova
//	if(hitboxEntity.x + hitboxEntity.width < (colonnaEntity+1)*GamePanel.TILES_SIZE) {			
//		int numEntitaInTile = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity, colonnaEntity);
//					
//		if(numEntitaInTile > 1) {			//elmeno due perchè uno è sicuro, essendoci l'entità	
//			if(siSchiantaConQualcosaNellaLista(hitboxEntity, indiceStanza))	
//				canMove = false;
//		}
//	}
//	
//	else {
//		int numEntitaInTile = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity, colonnaEntity);
//		int numEntitaTileDestra = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity, colonnaEntity + 1);
//		
//		if(numEntitaInTile > 1 || numEntitaTileDestra > 0) {	
//			if(siSchiantaConQualcosaNellaLista(hitboxEntity, indiceStanza))	
//				canMove = false;			
//		}	
//	}		
//	
//	return canMove;
//
//}
//
//public boolean notEntityCollisionDown(Rectangle hitboxEntity) {
//	boolean canMove = true;
//	int indiceStanza = Stanze.stanzaAttuale.indiceMappa;
//	
//	//prendiamo come riferimento il punto in basso a sinistra
//	int colonnaEntity = hitboxEntity.x/GamePanel.TILES_SIZE;
//	int rigaEntity = (hitboxEntity.y + hitboxEntity.height)/GamePanel.TILES_SIZE;
//	
//	//se si trova su una sola colonna controlliamo solo il tile dove si trova
//	if(hitboxEntity.x + hitboxEntity.width < (colonnaEntity+1)*GamePanel.TILES_SIZE) {
//		int numEntitaInTile = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity, colonnaEntity);
//		
//		if(numEntitaInTile > 0 && hitboxEntity.y < rigaEntity*GamePanel.TILES_SIZE) {																				
//			if(siSchiantaConQualcosaNellaLista(hitboxEntity, indiceStanza))	
//				canMove = false;	
//			}
//	}
//	
//	else {
//		int numEntitaInTileSinistra = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity, colonnaEntity);
//		int numEntitaTileDestra = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity, colonnaEntity + 1);
//		
//		if((numEntitaInTileSinistra > 0 || numEntitaTileDestra > 0) && hitboxEntity.y < rigaEntity*GamePanel.TILES_SIZE) {	
//			if(siSchiantaConQualcosaNellaLista(hitboxEntity, indiceStanza))	
//				canMove = false;	
//			}	
//	}			
//	
//	return canMove;
//
//}
//
//public boolean notEntityCollisionLeft(Rectangle hitboxEntity) {
//	boolean canMove = true;
//	int indiceStanza = Stanze.stanzaAttuale.indiceMappa;
//	
//	int colonnaEntity = hitboxEntity.x/GamePanel.TILES_SIZE;
//	int rigaEntity = hitboxEntity.y/GamePanel.TILES_SIZE;
//			
//	//se si trova su una sola colonna controlliamo solo il tile dove si trova
//	if(hitboxEntity.y + hitboxEntity.height < (rigaEntity+1)*GamePanel.TILES_SIZE) {
//		int numEntitaInTile = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity, colonnaEntity);
//		if(numEntitaInTile > 1) {																				
//			if(siSchiantaConQualcosaNellaLista(hitboxEntity, indiceStanza))	
//				canMove = false;
//		}
//	}
//	// se si trova su due righe, deve controllare sopra e sotto
//	else {
//		int numEntitaInTileSopra = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity, colonnaEntity);
//		int numEntitaTileSotto = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity + 1, colonnaEntity);
//		
//		if(numEntitaInTileSopra > 1 || numEntitaTileSotto > 0) {	
//			if(siSchiantaConQualcosaNellaLista(hitboxEntity, indiceStanza))	
//				canMove = false;			
//		}	
//	}				
//	
//	return canMove;
//}
//
//public boolean notEntityCollisionRight(Rectangle hitboxEntity) {
//	boolean canMove = true;
//	int indiceStanza = Stanze.stanzaAttuale.indiceMappa;
//	
//	//prendiamo come riferimento il punto della hitbox in alto a destra
//	int colonnaEntity = (hitboxEntity.x + hitboxEntity.width)/GamePanel.TILES_SIZE;
//	int rigaEntity = hitboxEntity.y /GamePanel.TILES_SIZE;
//			
//	//se si trova su una sola colonna controlliamo solo il tile dove si trova
//	if(hitboxEntity.y + hitboxEntity.height < (rigaEntity+1)*GamePanel.TILES_SIZE) { 
//		int numEntitaInTile = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity, colonnaEntity);			
//		if(numEntitaInTile > 0 && hitboxEntity.x < colonnaEntity*GamePanel.TILES_SIZE) {	
//			//zero, perchè l'entità può essere nel tile di sinistra
//			//ogni tanto la hitbox del personaggio entra completamente nel tile, in quel caso numenttitaTile diventa 1 
//			//e fa il controllo inutilmente, perchè l'entità nel tile è il personaggio
//			if(siSchiantaConQualcosaNellaLista(hitboxEntity, indiceStanza))	
//				canMove = false;
//		}
//	}
//	
//	else {
//		int numEntitaInTileSopra = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity, colonnaEntity);
//		int numEntitaTileSotto = control.getModel().getStanza(indiceStanza).getNumEntityIntile(rigaEntity + 1, colonnaEntity);
//		
//		if((numEntitaInTileSopra > 0 || numEntitaTileSotto > 0) && hitboxEntity.x < colonnaEntity*GamePanel.TILES_SIZE) {	
//			if(siSchiantaConQualcosaNellaLista(hitboxEntity, indiceStanza))	
//				canMove = false;	
//		}	
//	}		
//	
//	return canMove;
//}
//
