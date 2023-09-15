package controller.playState.entityController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class BulletController {

	private boolean hit = false;
	private int indexInList;
	private int direction;
	private Hitbox hitbox;
	private float speed;
	private PlayStateController play;
	private EntityController owner;  //il proiettile non colpisce chi lo ha lanciato 
	
	public BulletController(PlayStateController p, int index, EntityController e) {
		play = p;
		setHitbox(e);
		speed = play.getController().getGameScale()*1.3f;
		indexInList = index;
		
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
		updatePosition();
	}

	private void checkCollision() {
		try {
			switch(direction) {
			case EntityController.LEFT: 
				if(!play.getCollisionChecker().canMoveLeft(hitbox) || 
						play.getCollisionChecker().bulletHittedEntity(hitbox, owner))  
					hit = true;
				break;
			case EntityController.RIGHT:
				if(!play.getCollisionChecker().canMoveRight(hitbox) || 
						play.getCollisionChecker().bulletHittedEntity(hitbox, owner)) 
					hit = true;
				break;
			case EntityController.UP:
				if(!play.getCollisionChecker().canMoveUp(hitbox) || 
						play.getCollisionChecker().bulletHittedEntity(hitbox, owner))  
					hit = true;
				break;
			case EntityController.DOWN:
				if(!play.getCollisionChecker().canMoveDown(hitbox)|| 
						play.getCollisionChecker().bulletHittedEntity(hitbox, owner))  
					hit = true;
				break;
			}		
		}
		catch(ArrayIndexOutOfBoundsException obe) {
			play.getController().getView().getPlay().removeBullet(indexInList);
			play.removeBullets(indexInList);
			System.out.println("fuori dai bordi");
		}
							
		if(hit) {
			play.getController().getView().getPlay().removeBullet(indexInList);
			play.removeBullets(indexInList);
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