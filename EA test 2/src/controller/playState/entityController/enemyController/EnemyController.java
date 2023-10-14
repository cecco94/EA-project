package controller.playState.entityController.enemyController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public abstract class EnemyController extends EntityController{

	protected int life, attack, defense;
	protected final int maxLife = 100;
	public static final int KO_STATE = 3, HITTED = 4, PLAYER_IN_RANGE = 5;
	protected int bulletCounter, dyingCounter, hittedCounter;
	protected int stateBeforeHitted, velocitaAllontanamentoDopoColpo = 2, timeOfBlockBeforeHitted = 100;
	
	public EnemyController(int ind, String type, Hitbox r, PlayStateController p) {
		super(ind, type, r, p);
		typeOfTarget = EntityController.ENEMY;
	}
	
	//il nemico reagisce diversamente in base al tipo di attacco, ravvicinato o meno
	public void hitted(int damage, int direction, boolean isNearAttack) {
		//se viene colpito due volte, anche lo stato prima di essere colpito diventa hitted, per evitarlo, usiamo questo if
		if(currentState != HITTED) {
			stateBeforeHitted = currentState;
			currentState = HITTED;
		}
		int realDamage = damage - defense;
		if(realDamage > 0)
			life -= realDamage;
		
		if(isNearAttack) {
			velocitaAllontanamentoDopoColpo = 10;
			timeOfBlockBeforeHitted = 200;
		}
		
		else {
			velocitaAllontanamentoDopoColpo = 4;
			timeOfBlockBeforeHitted = 100;
		}
		
		allontanatiDopoColpito(direction);
		
		
	}
		
	private void allontanatiDopoColpito(int direction) {
		//quando viene colpito, si sposta leggermente nella direzione del colpo
		if(direction == UP) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - velocitaAllontanamentoDopoColpo*speed;
			if(play.getCollisionChecker().canMoveUp(tempHitboxForCheck) &&
			   !play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.y = tempHitboxForCheck.y;
		}
		
		else if(direction == DOWN) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + velocitaAllontanamentoDopoColpo*speed;
			if(play.getCollisionChecker().canMoveDown(tempHitboxForCheck) &&
			   !play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.y = tempHitboxForCheck.y;
		}
		
		else if (direction == RIGHT) {
			tempHitboxForCheck.y = hitbox.y;
			tempHitboxForCheck.x = hitbox.x + velocitaAllontanamentoDopoColpo*speed;
			if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck) &&
			   !play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.x = tempHitboxForCheck.x;
		}
		
		else if (direction == LEFT) {
			tempHitboxForCheck.y = hitbox.y;
			tempHitboxForCheck.x = hitbox.x - velocitaAllontanamentoDopoColpo*speed;
			if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck) &&
			   !play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.x = tempHitboxForCheck.x;
		}

	}
	//il metodo rimuove dalla due liste (view e controller) il nemico di indicce indicato
	public void die() {
		play.getController().getView().getPlay().removeEnemy(index);
		play.removeEnemy(index);
	}
	
	protected void searchPathToPlayer(float playerX, float playerY) {	
		//il nemico insegue il giocatore, quindi la riga e colonna di arrivo coincidono con la posizione del giocatore
		int playerCol = (int)(playerX)/play.getController().getTileSize();
		int playerRow = (int)(playerY)/play.getController().getTileSize();
		searchThePath(playerCol, playerRow);
	}
	
	public void decreaseIndexInList() {
		this.index--;
		
	}

	public int getLife() {
		return life;
	}
	
	public boolean isHitted() {
		if(currentState == HITTED)
			return true;
		else 
			return false;
	}
}
