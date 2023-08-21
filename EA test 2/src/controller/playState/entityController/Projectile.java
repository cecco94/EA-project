package controller.playState.entityController;

import java.awt.Rectangle;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public class Projectile extends EntityController {

	private boolean hit = false;
	private int indexInList;
	private int direction;
	private final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	
	public Projectile(Rectangle r, PlayStateController p, int i) {
		super(r, p);
		speed = (int)(GamePanel.SCALE);
		indexInList = i;
		setDirection();
	}

	private void setDirection() {
		if(play.getPlayer().getDirection().x == -1) {
			direction = LEFT;
		}
		else if(play.getPlayer().getDirection().x == 1) {
			direction = RIGHT;
		//	hitbox.x += hitbox.width;
		}
		else if(play.getPlayer().getDirection().y == -1) {
			direction = UP;
		}
		else {
			direction = DOWN;	
		//	hitbox.y += hitbox.height;
		}
	}

	@Override
	public void update(PlayerController player) {
		checkSolid();
		updatePosition();
	}

	private void checkSolid() {
		switch(direction) {
		case LEFT: 
			if(!play.getCollisionChecker().canMoveLeft(hitbox)) 
				hit = true;
			break;
		case RIGHT:
			if(!play.getCollisionChecker().canMoveRight(hitbox)) 
				hit = true;
			break;
		case UP:
			if(!play.getCollisionChecker().canMoveUp(hitbox)) 
				hit = true;
			break;
		case DOWN:
			if(!play.getCollisionChecker().canMoveDown(hitbox)) 
				hit = true;
			break;
			}		
		
		if(hit) {
			play.getController().getView().getPlay().removeProjectile(indexInList);
			play.removeProjectile(indexInList);
		}		
	}

	private void updatePosition() {
		switch(direction) {
		case LEFT:
			hitbox.x -= speed;
			break;
		case RIGHT:
			hitbox.x += speed;
			break;
		case UP:
			hitbox.y -= speed;
			break;
		case DOWN:
			hitbox.y += speed;
			break;
		}
		 	
	}

	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public void abbassaIndice() {
		indexInList--;
	}

	public int getDirection() {
		return direction;
	}
}
