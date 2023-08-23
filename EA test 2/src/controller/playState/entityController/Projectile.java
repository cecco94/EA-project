package controller.playState.entityController;

import java.awt.Rectangle;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public class Projectile {

	private boolean hit = false;
	private int indexInList;
	private int direction;
	private Rectangle hitbox;
	private int speed;
	private PlayStateController play;
	
	public Projectile(PlayStateController p, int index) {
		play = p;
		direction = p.getPlayer().getDirection();
		setHitbox();
		speed = (int)(GamePanel.SCALE*1.3f);
		indexInList = index;
	}

	private void setHitbox() {
		
		int width = play.getPlayer().getHitbox().width;
		int height = play.getPlayer().getHitbox().height;
		int x = play.getPlayer().getHitbox().x;
		int y = play.getPlayer().getHitbox().y;
		
		if(direction == PlayerController.RIGHT)
			x += width;
		else if(direction == PlayerController.DOWN)
			y += height;
		
		hitbox = new Rectangle(x, y, width, height);
	}

	public void update(PlayerController player) {
		checkSolid();
		updatePosition();
	}

	private void checkSolid() {
		try {
			switch(direction) {
			case PlayerController.LEFT: 
				if(!play.getCollisionChecker().canMoveLeft(hitbox)) 
					hit = true;
				break;
			case PlayerController.RIGHT:
				if(!play.getCollisionChecker().canMoveRight(hitbox)) 
					hit = true;
				break;
			case PlayerController.UP:
				if(!play.getCollisionChecker().canMoveUp(hitbox)) 
					hit = true;
				break;
			case PlayerController.DOWN:
				if(!play.getCollisionChecker().canMoveDown(hitbox)) 
					hit = true;
				break;
			}		
		}
		catch(ArrayIndexOutOfBoundsException obe) {
			play.getController().getView().getPlay().removeProjectile(indexInList);
			play.removeProjectile(indexInList);
		//	System.out.println("fuori dai bordi");
		}
		
		if(hit) {
			play.getController().getView().getPlay().removeProjectile(indexInList);
			play.removeProjectile(indexInList);
		}		
	}

	private void updatePosition() {
		switch(direction) {
		case PlayerController.LEFT:
			hitbox.x -= speed;
			break;
		case PlayerController.RIGHT:
			hitbox.x += speed;
			break;
		case PlayerController.UP:
			hitbox.y -= speed;
			break;
		case PlayerController.DOWN:
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
