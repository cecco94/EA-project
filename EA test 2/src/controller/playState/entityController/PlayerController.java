package controller.playState.entityController;

import controller.main.Gamestate;
import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class PlayerController extends EntityController {

	public static final int INTERACT = 10;

	private int RAGAZZO = 0 , RAGAZZA = 1;
	
	private int attack = 20;
	private int defense = 2;
	private int life = 70;
	private int cfu = 0;
	private int notes = 10;

	//quando il player parla con qualcuno, si salva l'indice nella lista di quell'entità
	//la view andrà a vedere nella lista del view quali dialoghi contiene l'entità con tale indice
	private int indexOfEntityInteract;
	
	//il giocatore ha dei booleani per descrivere il suo stato perchè può fare più cose contemporaneamente e può muoversi in più
	//direzioni allo stesso tempo. gli npc per semplicità non possono farlo
	private boolean parry, throwing, interacting, moving, idle, attacking, up, down, left, right;

	public boolean isUp() {
		return up;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

	//ci servono per non far iniziare un'altra animazione durante l'attacco
	private boolean isAttackAnimation;
	private int attackCounter; //durata attacco
	
	
	public PlayerController(Hitbox r, PlayStateController p) {
		//BRUTTO, DA CAMBIARE
		super(-1, "player", r, p);
		resetBooleans();
		
		int hitboxWidth = (int)(play.getController().getTileSize()*0.75);
		int hitboxHeight = play.getController().getTileSize()/2;
		super.setBounds((int)r.x, (int)r.y, hitboxWidth, hitboxHeight);	
		
		speed = play.getController().getGameScale()*1.2f;
	}
	
	public void setType(int gender) {
		if(gender == RAGAZZO) {			//il ragazzo ha più appunti
			notes = 20;
		}else if(gender == RAGAZZA){
			life = 100;					//la ragazza ha più concentrazione 
		}
	}
	
	public void update() {
		updatePos();
		isAbovePassage();
		isNearEvent();
		
	}

	private void updatePos() {
		
		//System.out.println(hitbox.x/play.getController().getTileSize() + " " + hitbox.x/play.getController().getTileSize());
		//System.out.println(direction + " " + moving);
		
		
		//durante l'intervallo mentre attacca, la velocità del personaggio diminuisce
		setPlayerSpeedDuringAttack();
		setMoving(false);
		
		if (left && !right && !parry) {
			tempHitboxForCheck.x = hitbox.x - speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) {
					hitbox.x -= speed;
					currentDirection = LEFT;
					setMoving(true);
				}
			}
		} 
		else if (right && !left && !parry) {
			tempHitboxForCheck.x = hitbox.x + speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) {
					hitbox.x += speed;
					currentDirection = RIGHT;
					setMoving(true);
				}
			}
		}
		if (up && !down  && !parry) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(play.getCollisionChecker().canMoveUp(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) {
					hitbox.y -= speed;
					currentDirection = UP;
					setMoving(true);
				}
			}
		} 
		else if (down && !up && !parry) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) {
					hitbox.y += speed;
					currentDirection = DOWN;
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
				speed = 0.5f; //int --> diventa 0 , rivedere
			
			else {
				speed = play.getController().getGameScale()*1.3f;
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
		idle = false;
		moving = false;	
		
		setMoving(false);
		setAttacking(false);
		setParry(false);
		setThrowing(false);
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}

	public void choiceDirection(int dir) {	
		switch (dir) {
		case UP:
			up = true;
			break;
		case LEFT:
			left = true;
			break;
		case DOWN:
			down = true;
			break;
		case RIGHT:
			right = true;
			break;
			
		}
	}
	
	public void resetDirection(int dir) {
		switch (dir) {
		case UP:
			up = false;
			break;
		case LEFT:
			left = false;
			break;
		case DOWN:
			down = false;
			break;
		case RIGHT:
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
	
	public void setMoving(boolean m) {
		moving = m;
}
		
	public boolean isMoving() {
	return moving;
}
	
	public void isAbovePassage() {
		// il player vede in che stanza è, vede la lista dei passaggi, e li controlla tutti
		// se si trova su un passaggio, cambia mappa e posiz del player
		int passageIndex = play.getController().getModel().checkPassagge(hitbox);
		
		if(passageIndex >= 0) {
			if(play.getController().getModel().getRoom(play.getCurrentroomIndex()).getPassaggi().get(passageIndex).isOpen()) {
				play.getController().getModel().saveNewRoomData();
				
				play.getController().getView().getTransition().setPrev(Gamestate.PLAYING);
				play.getController().getView().getTransition().setPrev(Gamestate.PLAYING);
				play.getController().setGameState(Gamestate.TRANSITION_STATE);
				
			}
			else {
				//se passaggio è chiuso, il passaggio stesso restituisce una stringa che viene stampata a video
				String s = play.getController().getModel().getRoom(play.getCurrentroomIndex()).getPassaggi().get(passageIndex).getMessage();
				play.getController().getView().getPlay().getUI().setMessage(s);
				play.getController().getView().getPlay().getUI().setShowMessage(true);
			}
		}		
	}
	
	public void isNearEvent() {
		//il player vede in che stanza è, vede la lista degli eventi, controlla se è vicino ad un evento.
		//in caso positivo, deve apparire il messaggio. infine se preme E parte l'interazione
		int eventIndex = play.getController().getModel().checkEvent(hitbox);
		
		if(eventIndex >= 0) {
			boolean alreadyInteracted = play.getController().getModel().getRoom(play.getCurrentroomIndex()).getEventi().get(eventIndex).isEndInteraction();
			if(!alreadyInteracted) {
				play.getController().getView().getPlay().getUI().setMessage("premi E per interagire");
				play.getController().getView().getPlay().getUI().setShowMessage(true);
				
				if(interacting) {
					play.getController().getModel().getRoom(play.getCurrentroomIndex()).getEventi().get(eventIndex).Interact();
					interacting = false;
				}
			}
		}
	}
	
	public void speak(int index) {
		play.getController().setGameState(Gamestate.DIALOGUE);
		indexOfEntityInteract = index;
		interacting = false;
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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getCfu() {
		return cfu;
	}

	public void setCfu(int cfu) {
		this.cfu = cfu;
	}

	public int getNotes() {
		return notes;
	}

	public void setNotes(int notes) {
		this.notes = notes;
	}
	
	public void decreaseBulletsNumber() {
		notes--;
	}
	
	public void addNotes() {
		notes += 5;
	}
	
	public void addCFU() {
		cfu += 10;
	}
	
	public void addLife() {
		life += 20;
		if(life > 100)
			life = 100;
	}
	
	public int getIndexOfEntityInteract() {
		return indexOfEntityInteract;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public boolean isIdle() {
		return idle;
	}

	
	
}





