package controller.playState;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import view.main.GamePanel;

public class PlayerController {

	private Rectangle hitbox, tempHitboxForCheck ;
	private int speed = (int)(GamePanel.SCALE*1.3);
	private boolean moving, left, right, up, down;
	private Collisions collisionCheck;
	
	public PlayerController(Collisions collcheck) {
		
		hitbox = new Rectangle(2*GamePanel.TILES_SIZE, 2*GamePanel.TILES_SIZE, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE/2);	
		tempHitboxForCheck = new Rectangle(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		collisionCheck = collcheck;
	}
	
	void updatePos(int stanza) {
		setMoving(false);
		
		if (left && !right) {
			tempHitboxForCheck.x = hitbox.x - speed;
			tempHitboxForCheck.y = hitbox.y;
			if(collisionCheck.canMoveLeft(tempHitboxForCheck, stanza)) {
				hitbox.x -= speed;
				setMoving(true);
			}
		} 
		else if (right && !left) {
			tempHitboxForCheck.x = hitbox.x + speed;
			tempHitboxForCheck.y = hitbox.y;
			if(collisionCheck.canMoveRight(tempHitboxForCheck, stanza)) {
				hitbox.x += speed;
				setMoving(true);
			}
		}
		if (up && !down) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(collisionCheck.canMoveUp(tempHitboxForCheck, stanza)) {
				hitbox.y -= speed;
				setMoving(true);
			}
		} 
		else if (down && !up) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(collisionCheck.canMoveDown(tempHitboxForCheck, stanza)) {
				hitbox.y += speed;
				setMoving(true);
			}
		}
	}

	public void resetBooleans() {
		up = false;
		down = false;
		left = false;
		right = false;	
		setMoving(false);
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}

	public void choiceDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		}
	}
	
	public void resetDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_S:
			down = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		}	
	}

	
	public boolean isMoving() {
		return moving;
	}

	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	
}
