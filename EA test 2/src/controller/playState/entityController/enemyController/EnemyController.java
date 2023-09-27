package controller.playState.entityController.enemyController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public abstract class EnemyController extends EntityController{

	protected int life, attack, defense;
	protected final int maxLife = 100;
	public static final int KO_STATE = 3, HITTED = 4;
	protected int bulletCounter, dyingCounter, hittedCounter;
	protected int stateBeforeHitted;
	
	public EnemyController(int ind, String type, Hitbox r, PlayStateController p) {
		super(ind, type, r, p);
		this.typeOfTarget = "enemy";
	}
	
	public abstract void update();

	//il nemico reagisce diversamente in base al tipo di attacco, ravvicinato o meno
	public void hitted(int damage, int direction, boolean isNearAttack) {
		stateBeforeHitted = currentState;
		currentState = HITTED;
		int realDamage = damage - defense;
		if(realDamage > 0)
			life -= realDamage;
		
		//quando viene colpito, si sposta leggermente nella direzione del colpo
		if(direction == UP) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - 2*speed;
			if(play.getCollisionChecker().canMoveUp(tempHitboxForCheck) &&
			   !play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.y = tempHitboxForCheck.y;
		}
		
		else if(direction == DOWN) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + 2*speed;
			if(play.getCollisionChecker().canMoveDown(tempHitboxForCheck) &&
			   !play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.y = tempHitboxForCheck.y;
		}
		
		else if (direction == RIGHT) {
			tempHitboxForCheck.y = hitbox.y;
			tempHitboxForCheck.x = hitbox.x + 2*speed;
			if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck) &&
			   !play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.x = tempHitboxForCheck.x;
		}
		
		else if (direction == LEFT) {
			tempHitboxForCheck.y = hitbox.y;
			tempHitboxForCheck.x = hitbox.x - 2*speed;
			if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck) &&
			   !play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.x = tempHitboxForCheck.x;
		}
	}
		
	protected void goToPlayerPosition() {	
		//il nemico insegue il giocatore, quindi la riga e colonna di arrivo coincidono con la posizione del giocatore
		int playerCol = (int)(play.getPlayer().getHitbox().x)/play.getController().getTileSize();
		int playerRow = (int)(play.getPlayer().getHitbox().y)/play.getController().getTileSize();
		goToYourDestination(playerCol, playerRow, true);
	}
	
	public void decreaseIndexInList() {
		this.index--;
		
	}

	public void hit() {

	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

}
