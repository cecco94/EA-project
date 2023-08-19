package controller.playState;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import controller.Gamestate;
import controller.IController;
import view.main.GamePanel;

public class PlayerController {

	private IController controller;
	private Rectangle hitbox, tempHitboxForCheck ;
	private int speed = (int)(GamePanel.SCALE*1.3);
	private boolean moving, attacking, parry, left, right, up, down;
	private Collisions collisionCheck;
	
	private boolean isAttackAnimation;
	private int attackCounter;
	
	
	public PlayerController(Collisions collcheck, IController c) {
		controller = c;
		int hitboxX = 12*GamePanel.TILES_SIZE;
		int hitboxY = 9*GamePanel.TILES_SIZE;
		
		int hitboxWidth = (int)(GamePanel.TILES_SIZE*0.75);
		int hitboxHeight = GamePanel.TILES_SIZE/2;
		
		hitbox = new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);	
		tempHitboxForCheck = new Rectangle(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		collisionCheck = collcheck;
	}
	
	void updatePos() {
			
		//durante l'intervallo dove attacca, la velocità del personaggio diminuisce
		setPlayerSpeedDuringAttack();
		
		
		setMoving(false);
		
		if (left && !right && !parry) {
			tempHitboxForCheck.x = hitbox.x - speed;
			tempHitboxForCheck.y = hitbox.y;
			if(collisionCheck.canMoveLeft(tempHitboxForCheck)) {
				hitbox.x -= speed;
				setMoving(true);
			}
		} 
		else if (right && !left && !parry) {
			tempHitboxForCheck.x = hitbox.x + speed;
			tempHitboxForCheck.y = hitbox.y;
			if(collisionCheck.canMoveRight(tempHitboxForCheck)) {
				hitbox.x += speed;
				setMoving(true);
			}
		}
		if (up && !down  && !parry) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(collisionCheck.canMoveUp(tempHitboxForCheck)) {
				hitbox.y -= speed;
				setMoving(true);
			}
		} 
		else if (down && !up && !parry) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(collisionCheck.canMoveDown(tempHitboxForCheck)) {
				hitbox.y += speed;
				setMoving(true);
			}
		}
	}

	private void setPlayerSpeedDuringAttack() {
		if(isAttackAnimation) {
			attackCounter++;
			
			if(attackCounter < 100)
				speed = (int)(0.5);
			
			else {
				speed = (int)(GamePanel.SCALE*1.3);
				isAttackAnimation = false;
				attackCounter = 0;
			}
		}
		
	}

	public void resetBooleans() {
		up = false;
		down = false;
		left = false;
		right = false;	
		setMoving(false);
		setAttacking(false);
		setParry(false);
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
	
	public boolean isRight() {
		return right;
	}
	
	public boolean isLeft() {
		return left;
	}
	
	public boolean isDown() {
		return down;
	}
	
	public boolean isUp() {
		return up;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
		if(attacking) {
			isAttackAnimation = true;
		}
	}

	public void isAbovePassaggio() {
		// vedi in che stanza sei, vedi la lista dei passaggi, controllali
		// se si, cambia mappa e posiz del player
			if(controller.getModel().checkPassaggio(hitbox) >= 0) {
				controller.getModel().memorizzaDatiNuovaStanza();
				
				controller.setGameState(Gamestate.TRANSITION_ROOM);
			}		
	}
	
	public String toString() {
		return "player ( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " + hitbox.height + " )";
	}

	public void setParry(boolean b) {
		parry = b;	
	}

	public boolean isParring() {
		return parry;
	}
	
}
