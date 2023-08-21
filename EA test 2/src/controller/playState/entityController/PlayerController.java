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
	private int speed = (int)(GamePanel.SCALE*1.3f);
	private boolean moving, attacking, parry, throwing, left, right, up, down;
	private Collisions collisionCheck;
	
	private boolean isAttackAnimation;
	private int attackCounter;
	
	private Projectile[] appuntiVolanti;
	public static int proiettiliInGiro;
	
	
	public PlayerController(Collisions collcheck, IController c) {
		controller = c;
		int hitboxX = 12*GamePanel.TILES_SIZE;
		int hitboxY = 9*GamePanel.TILES_SIZE;
		
		int hitboxWidth = (int)(GamePanel.TILES_SIZE*0.75);
		int hitboxHeight = GamePanel.TILES_SIZE/2;
		
		hitbox = new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);	
		tempHitboxForCheck = new Rectangle(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		collisionCheck = collcheck;
		
		appuntiVolanti = new Projectile[5];
	}
	

	public void update() {
		updatePos();
		isAbovePassaggio();
		checkProjectile();
	}
	
	
	private void checkProjectile() {
		//se abbiamo 2 proiettili volanti, i va da 0 a 1
		for(int i = 0; i < proiettiliInGiro; i++)
			appuntiVolanti[i].update(this);
		
		System.out.println(proiettiliInGiro);
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
	
	public void addProjectile() {
		if(proiettiliInGiro < appuntiVolanti.length) {
			//se ci sono già 2 proiettili volanti, il terzo viene messo nella casella 2 = proiettiliInGiro
			appuntiVolanti[proiettiliInGiro] = new Projectile(hitbox, controller.getPlay(), proiettiliInGiro);
			proiettiliInGiro++;			
		}
	}
	
	public void removeProjectile(int indexElementToRemove) {
		if(proiettiliInGiro > 0) {			
			for(int i = indexElementToRemove; i < proiettiliInGiro; i++) {
				//bisogna anche aggiornare l'indice dentro al proiettile
				if(i == proiettiliInGiro - 1)
					appuntiVolanti[i] = null;
				else {
				appuntiVolanti[i  + 1].indexInList--;		
				appuntiVolanti[i] = appuntiVolanti[i + 1];
				}
			}
			
			proiettiliInGiro--;
		}
	}
	
	
	public String toString() {
		return "player ( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " + hitbox.height + " )";
	}


	public Projectile[] getAppunti() {
		return appuntiVolanti;
	}

}
