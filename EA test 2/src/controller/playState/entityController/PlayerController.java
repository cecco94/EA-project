package controller.playState.entityController;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import controller.main.Gamestate;
import controller.playState.PlayStateController;
import model.mappa.Stanze;
import view.main.GamePanel;

public class PlayerController extends EntityController {

	private int speed = (int)(GamePanel.SCALE*1.3f);
	private boolean  parry, throwing, interacting;

	//ci servono per non far iniziare un'altra animazione durante l'attacco
	private boolean isAttackAnimation;
	private int attackCounter;
	
	
	public PlayerController(Rectangle r, PlayStateController p) {
		super(r, p);
		
		int hitboxWidth = (int)(GamePanel.TILES_SIZE*0.75);
		int hitboxHeight = GamePanel.TILES_SIZE/2;
		
		super.setBounds(r.x, r.y, hitboxWidth, hitboxHeight);
	}
	

	public void update() {
		updatePos();
		isAbovePassaggio();
		isNearEvent();
	}

	private void updatePos() {
	
	//	System.out.println("player colonna " + hitbox.x/GamePanel.TILES_SIZE + " riga " + hitbox.y/GamePanel.TILES_SIZE);
	//	System.out.println("player x " + hitbox.x+ " y " + hitbox.y);
	
		//durante l'intervallo dove attacca, la velocità del personaggio diminuisce
		setPlayerSpeedDuringAttack();
		
		setMoving(false);
		
		if (left && !right && !parry) {
			tempHitboxForCheck.x = hitbox.x - speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().checkCollisionInEntityList(tempHitboxForCheck)) {
					hitbox.x -= speed;
					direction = LEFT;
					setMoving(true);
				}
			}
		} 
		else if (right && !left && !parry) {
			tempHitboxForCheck.x = hitbox.x + speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().checkCollisionInEntityList(tempHitboxForCheck)) {
					hitbox.x += speed;
					direction = RIGHT;
					setMoving(true);
				}
			}
		}
		if (up && !down  && !parry) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(play.getCollisionChecker().canMoveUp(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().checkCollisionInEntityList(tempHitboxForCheck)) {
					hitbox.y -= speed;
					direction = UP;
					setMoving(true);
				}
			}
		} 
		else if (down && !up && !parry) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().checkCollisionInEntityList(tempHitboxForCheck)) {
					hitbox.y += speed;
					direction = DOWN;
					setMoving(true);
				}
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
		super.resetBooleans();
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
	
	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean a) {
		attacking = a;
		if(attacking) 
			isAttackAnimation = true;	
	}
	
	public void isAbovePassaggio() {
		// vedi in che stanza sei, vedi la lista dei passaggi, controllali
		// se si, cambia mappa e posiz del player
		int indicePassaggio = play.getController().getModel().checkPassaggio(hitbox);
		
		if(indicePassaggio >= 0) {
			if(play.getController().getModel().getStanza(Stanze.stanzaAttuale.indiceMappa).getPassaggi().get(indicePassaggio).isOpen()) {
				play.getController().getModel().memorizzaDatiNuovaStanza();
				play.getController().setGameState(Gamestate.TRANSITION_ROOM);
			}
			else {
				String s = play.getController().getModel().getStanza(Stanze.stanzaAttuale.indiceMappa).getPassaggi().get(indicePassaggio).getScritta();
				play.getController().getView().getPlay().getUI().setScritta(s);
				play.getController().getView().getPlay().getUI().setShowMessage(true);
			}
		}		
	}
	
	public void isNearEvent() {
		//vedi in che stanza sei, vedi la lista degli eventi, controllali
		//se sei vicino ad un evento, deve apparire il messaggio
		//se preme E parte l'interazione
		int indiceEvento = play.getController().getModel().checkEvent(hitbox);
		
		if(indiceEvento >= 0) {
			boolean giaInteragito = play.getController().getModel().getStanza(Stanze.stanzaAttuale.indiceMappa).getEventi().get(indiceEvento).isEndInteraction();
			if(!giaInteragito) {
				play.getController().getView().getPlay().getUI().setScritta("premi E per interagire");
				play.getController().getView().getPlay().getUI().setShowMessage(true);
				if(interacting) {
					play.getController().getModel().getStanza(Stanze.stanzaAttuale.indiceMappa).getEventi().get(indiceEvento).Interact();
				}
			}
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


	public void setInteracting(boolean b) {
		interacting = b;	
	}
	
	public boolean isInteracting() {
		return interacting;
	}
	
	
}
