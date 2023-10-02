package controller.playState.entityController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.enemyController.EnemyController;

public class BulletController {

	private boolean hit = false;
	private int indexInList;
	private int direction;
	private int damage;
	private Hitbox hitbox;
	private float speed;
	private PlayStateController play;
	//il proiettile non colpisce chi lo ha lanciato, target è la entità che viene colpita
	private EntityController owner, target;  
	private int distanzaPercorsa;
	
	public BulletController(PlayStateController p, int index, EntityController e) {
		play = p;
		setHitbox(e);
		speed = play.getController().getGameScale()*1.3f;
		indexInList = index;
		damage = 10;
		direction = e.currentDirection;
		owner = e;
	}

	private void setHitbox(EntityController e) {
		
		int width = e.getHitbox().width;
		int height = e.getHitbox().height;
		float x = e.getHitbox().x;
		float y = e.getHitbox().y;
		
		hitbox = new Hitbox((int)x, (int)y, width, height);
	}

	public void update() {
		checkCollision();
		checkHit();
		updatePosition();
	}

	private void checkHit() {
		if(hit) {
			//se si è schiantato contro una entità, se non è un npc, abbassa la sua vita
			if(target != null) {
				if(target.typeOfTarget == EntityController.ENEMY && owner.typeOfTarget == EntityController.PLAYER) {
					EnemyController enemy = (EnemyController)target;
					enemy.hitted(damage, direction, false);
				}
				
				else if(target.typeOfTarget == EntityController.PLAYER) {
					play.getPlayer().hitted(damage, direction);
				}
			}
			
			play.getController().getView().getPlay().removeBullet(indexInList);
			play.removeBullets(indexInList);
		}		
		
	}

	private void checkCollision() {
		try {
			switch(direction) {
			case EntityController.LEFT:
				//prima controlla se si schianta contro una parete
				if(!play.getCollisionChecker().canMoveLeft(hitbox)) {
					hit = true;
					target = null;
				}
				else {
					//se non si schianta contro una parete, vede se si schianta contro una entità
					target = play.getCollisionChecker().bulletHittedEntity(hitbox, owner);
					if(target != null) {
						//in caso positivo, si salva il riferimento a quella entità
						hit = true;
					}
				}	  	
				break;
				
			case EntityController.UP: 
				if(!play.getCollisionChecker().canMoveUp(hitbox)) {
					hit = true;
					target = null;
				}
				else {
					target = play.getCollisionChecker().bulletHittedEntity(hitbox, owner);
					if(target != null) {
						hit = true;
					}
				}	  	
				break;
				
			case EntityController.DOWN: 
				if(!play.getCollisionChecker().canMoveDown(hitbox)) {
					hit = true;
					target = null;
				}
				else {
					target = play.getCollisionChecker().bulletHittedEntity(hitbox, owner);
					if(target != null) {
						hit = true;
					}
				}	  	
				break;
				
			case EntityController.RIGHT: 
				if(!play.getCollisionChecker().canMoveRight(hitbox)) {
					hit = true;
					target = null;
				}
				else {
					target = play.getCollisionChecker().bulletHittedEntity(hitbox, owner);
					if(target != null) {
						hit = true;
					}
				}	  	
				break;
			}		
		}
		catch(ArrayIndexOutOfBoundsException obe) {
			play.getController().getView().getPlay().removeBullet(indexInList);
			play.removeBullets(indexInList);
			System.out.println("fuori dai bordi");
		}
							
	}

	private void updatePosition() {
		switch(direction) {
		case EntityController.LEFT:
			hitbox.x -= speed;
			break;
		case EntityController.RIGHT:
			hitbox.x += speed;
			break;
		case EntityController.UP:
			hitbox.y -= speed;
			break;
		case EntityController.DOWN:
			hitbox.y += speed;
			break;
		}
		//il proiettile dopo qualche metro si ferma
		distanzaPercorsa += speed; 
		if(distanzaPercorsa >= 3*play.getController().getTileSize()) {
			play.getController().getView().getPlay().removeBullet(indexInList);
			play.removeBullets(indexInList);
		}
			
	}

	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public int getDirection() {
		return direction;
	}

	public void decreaseIndexInList() {
		indexInList--;	
	}
}