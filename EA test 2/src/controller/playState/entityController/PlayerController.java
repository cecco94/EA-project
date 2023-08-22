package controller.playState.entityController;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import controller.IController;
import controller.main.Gamestate;
import controller.playState.Collisions;
import view.main.GamePanel;

public class PlayerController {

	private IController controller;
	private Rectangle hitbox, tempHitboxForCheck;
	private Collisions collisionCheck;

	private int speed = (int)(GamePanel.SCALE*1.3f);
	private boolean moving, attacking, parry, throwing, left, right, up, down;
	// ci serve per ricordare l'ultima direzione del giocatore per quando lancia gli appunti
	public static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	private int direction;
	
	//ci servono per non far iniziare un'altra animazione durante l'attacco
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
		direction = DOWN;
	}
	

	public void update() {
		updatePos();
		isAbovePassaggio();
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
				direction = LEFT;
				setMoving(true);
			}
		} 
		else if (right && !left && !parry) {
			tempHitboxForCheck.x = hitbox.x + speed;
			tempHitboxForCheck.y = hitbox.y;
			if(collisionCheck.canMoveRight(tempHitboxForCheck)) {
				hitbox.x += speed;
				direction = RIGHT;
				setMoving(true);
			}
		}
		if (up && !down  && !parry) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(collisionCheck.canMoveUp(tempHitboxForCheck)) {
				hitbox.y -= speed;
				direction = UP;
				setMoving(true);
			}
		} 
		else if (down && !up && !parry) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(collisionCheck.canMoveDown(tempHitboxForCheck)) {
				hitbox.y += speed;
				direction = DOWN;
				setMoving(true);
			}
		}
	}
	
	// durante l'attacco, il giocatore rallenta
	private void setPlayerSpeedDuringAttack() {
		if(isAttackAnimation) {
			attackCounter++;
			
			if(attackCounter < 100)
				speed = (int)(0.5f);
			
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
		setThrowing(false);
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
		if(attacking) 
			isAttackAnimation = true;	
	}
	
	public void isAbovePassaggio() {
		// vedi in che stanza sei, vedi la lista dei passaggi, controllali
		// se si, cambia mappa e posiz del player
			if(controller.getModel().checkPassaggio(hitbox) >= 0) {
				controller.getModel().memorizzaDatiNuovaStanza();
				
				controller.setGameState(Gamestate.TRANSITION_ROOM);
			}		
	}

	public void setParry(boolean b) {
		parry = b;	
	}

	public boolean isParring() {
		return parry;
	}

	public boolean isThrowing() {
		return throwing;
	}

	public void setThrowing(boolean throwing) {
		this.throwing = throwing;
	}	
	
	public String toString() {
		return "player ( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " + hitbox.height + " )";
	}

	public int getDirection() {
		return direction;
	}
}
