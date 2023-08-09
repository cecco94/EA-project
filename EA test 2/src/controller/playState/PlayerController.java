package controller.playState;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import view.main.GamePanel;

public class PlayerController {

	private Rectangle hitbox, hitBoxPerControllareCollisione;
	private int speed = (int)(GamePanel.SCALE*1.5);
	private boolean moving, left, right, up, down;
	private Collisions collisionCheck;
	
	public PlayerController(Collisions collcheck) {
		hitbox = new Rectangle(100, 100, GamePanel.TILES_SIZE/2, GamePanel.TILES_SIZE/2);
		hitBoxPerControllareCollisione = new Rectangle(100, 100, GamePanel.TILES_SIZE/2, GamePanel.TILES_SIZE/2);
		collisionCheck = collcheck;
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
	
	void updatePos() {
		moving = false;

		if (left && !right) {
			hitBoxPerControllareCollisione.x -= speed;
			if(collisionCheck.canMoveHere(hitBoxPerControllareCollisione, Collisions.SINISTRA)) {
				hitbox.x -= speed;
				moving = true;
			}
			hitBoxPerControllareCollisione.x = hitbox.x;
		} 
		else if (right && !left) {
			
			hitbox.x += speed;
			moving = true;
		}
		if (up && !down) {
			
			hitbox.y -= speed;
			moving = true;	
		} 
		else if (down && !up) {
			
			hitbox.y += speed;
			moving = true;
		}
	}

	public void resetBooleans() {
		up = false;
		down = false;
		left = false;
		right = false;	
		moving = false;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
}
