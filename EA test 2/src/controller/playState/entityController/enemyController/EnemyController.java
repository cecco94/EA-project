package controller.playState.entityController.enemyController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public class EnemyController extends EntityController{

	protected int life, attack, defense;
	public static final int NORMAL_STATE = 0, CHASING_STATE = 1, MISSED_PLAYER = 2;
	protected int actualState = NORMAL_STATE;
	protected int chasingCounter;
	
	public EnemyController(int ind, String type, Hitbox r, PlayStateController p) {
		super(ind, type, r, p);
	}

	@Override
	public void update() {

		float xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
		float yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
		
		if(xDistance < play.getController().getTileSize()*2 && yDistance < play.getController().getTileSize()*2) {
			if(actualState == NORMAL_STATE) {
				actualState = CHASING_STATE;
				play.getController().getView().getPlay().getUI().activeExclamation(index);
			}
		}
		
		switch(actualState) {
		case NORMAL_STATE:
			randomMove();
			break;
		case CHASING_STATE:
			int goalCol = 0;
			int goalRow = 0;
			
			
			
			updateChasing();
			break;
		case MISSED_PLAYER:
			updateMissedPlayer();
			break;
		}
		
		
	}
	
	private void updateMissedPlayer() {
		chasingCounter++;
		if(chasingCounter >= 400) {
			chasingCounter = 0;
			actualState = NORMAL_STATE;
		}
		
	}

	private void updateChasing() {
		chasingCounter++;
		
		
		
		
		if(chasingCounter >= 400) {
			chasingCounter = 0;
			actualState = MISSED_PLAYER;
		}
			
	}

	public void colpito() {
		life -= play.getPlayer().getAttack() - defense;
	}
	
	public void colpisci() {

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
