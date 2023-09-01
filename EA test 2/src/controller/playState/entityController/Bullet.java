package controller.playState.entityController;

import java.awt.Rectangle;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public class Bullet {

	private boolean hit = false;
	private int indexInList;
	private int direction;
	private Rectangle hitbox;
	private int speed;
	private PlayStateController play;
	private EntityController owner;  //il proiettile non colpisce chi lo ha lanciato 
	
	public Bullet(PlayStateController p, int index, EntityController e) {
		play = p;
		setHitbox(e);
		speed = (int)(GamePanel.SCALE*1.3f);
		indexInList = index;
		
		direction = e.direction;
		owner = e;
	}

	private void setHitbox(EntityController e) {
		
		int width = e.getHitbox().width;
		int height = e.getHitbox().height;
		int x = e.getHitbox().x;
		int y = e.getHitbox().y;
		
		hitbox = new Rectangle(x, y, width, height);
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
						play.getCollisionChecker().hitEntity(hitbox, owner))  
					hit = true;
				break;
			case EntityController.RIGHT:
				if(!play.getCollisionChecker().canMoveRight(hitbox) || 
						play.getCollisionChecker().hitEntity(hitbox, owner)) 
					hit = true;
				break;
			case EntityController.UP:
				if(!play.getCollisionChecker().canMoveUp(hitbox) || 
						play.getCollisionChecker().hitEntity(hitbox, owner))  
					hit = true;
				break;
			case EntityController.DOWN:
				if(!play.getCollisionChecker().canMoveDown(hitbox)|| 
						play.getCollisionChecker().hitEntity(hitbox, owner))  
					hit = true;
				break;
			}		
		}
		catch(ArrayIndexOutOfBoundsException obe) {
			play.getController().getView().getPlay().removeProjectile(indexInList);
			play.removeBullets(indexInList);
			System.out.println("fuori dai bordi");
		}
							
		if(hit) {
			play.getController().getView().getPlay().removeProjectile(indexInList);
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

	public Rectangle getHitbox() {
		return hitbox;
	}
	

	public int getDirection() {
		return direction;
	}

	public void decreaseIndexInList() {
		indexInList--;	
	}
}