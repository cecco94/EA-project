package controller.playState.entityController;

import controller.main.Gamestate;
import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.enemyController.EnemyController;

public class PlayerController extends EnemyController {

	public static final int INTERACT = 10;
	private int RAGAZZO = 0 , RAGAZZA = 1;
	private int attack, defense, life, cfu = 180, notes;

	//quando il player parla con qualcuno, si salva l'indice nella lista di quell'entità
	//la view andrà a vedere nella lista del view quali dialoghi contiene l'entità con tale indice
	private int indexOfEntityInteract;
	
	//il giocatore ha dei booleani per descrivere il suo stato perchè può fare più cose contemporaneamente e può muoversi in più
	//direzioni allo stesso tempo. gli npc per semplicità non possono farlo
	private boolean parry, throwing, interacting, moving, idle, attacking, hitted, up, down, left, right;
	
	//ci servono per non far iniziare un'altra animazione durante l'attacco
	private boolean isAttackAnimation;
	private int attackCounter, hittedcounter; 	
	
	//hitbox che serve per quando il player attacca da vicino, se la hitbox di un nemico si interseca con essa, viene colpito
	private Hitbox attackHitbox;
	
	public PlayerController(Hitbox r, PlayStateController p) {
		//BRUTTO, DA CAMBIARE
		super(-1, "player", r, p);
		typeOfTarget = EntityController.PLAYER;
		
		resetBooleans();
		
		int hitboxWidth = (int)(play.getController().getTileSize()*0.72);
		int hitboxHeight = play.getController().getTileSize()/2;
		super.setBounds((int)r.x, (int)r.y, hitboxWidth, hitboxHeight);	
		
		speed = play.getController().getGameScale()*1.2f;
		
		attackHitbox = new Hitbox(0, 0, hitboxWidth, hitboxHeight);
		
	}
	
	public void setGender(int gender) {
		if(gender == RAGAZZO) {			//il ragazzo ha più appunti
			notes = 20;
			attack = 15;
			defense = 1;
			life = 70;
			speed = play.getController().getGameScale()*1.2f;
		}
		else if(gender == RAGAZZA){
			life = 100;					//la ragazza ha più concentrazione 
			notes = 10;
			attack = 10;
			defense = 3;
			speed = play.getController().getGameScale()*1.3f;
		}
	}
	
	public void update(float playerX, float playerY) {
		updatePos();
		isAbovePassage();
		isNearEvent();
		
		waitAfterHitted();
	}

	//mettiamo questo counter per evitare che il player muoia dopo una raffica di colpi super veloci
	private void waitAfterHitted() {
		if(hitted) {
			hittedcounter++;
			if(hittedcounter >= 100) {
				hittedcounter = 0;
				hitted = false;
			}
		}
	}

	private void updatePos() {
		
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
				speed = 0.2f; 
			
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
		int passageIndex = play.getController().getModel().checkPassagge(hitbox);
		
		// se si trova su un passaggio e questo è aperto, cambia stanza e posiz del player
		if(passageIndex >= 0) 	
			play.getController().getModel().faiQuelloCHeDeviFareColPassaggio(passageIndex, cfu);
				
	}
	
	public void isNearEvent() {
		//il player vede in che stanza è, vede la lista degli eventi, controlla se è vicino ad un evento.
		int eventIndex = play.getController().getModel().checkEvent(hitbox);
		
		//in caso positivo, deve apparire il messaggio. infine se preme E parte l'interazione
		if(eventIndex >= 0) 
			play.getController().getModel().faiQuelloCHeDeviFareConEvento(eventIndex, interacting);		
		
	}
	
	public void speak(int index) {
		play.getController().setGameState(Gamestate.DIALOGUE);
		indexOfEntityInteract = index;
		interacting = false;
	}
	
	public void hitted(int damage, int direction) {		
		//se si para nella giusta direzione non subisce danni
		if(isParring()) {
			if((direction == UP && isDown()) || (direction == DOWN && isUp()) || 
			   (direction == LEFT && isRight()) || (direction == RIGHT && isLeft())) {
					damage = 0;
			}
		}
		//se è stato colpito recentemente, il danno non c'è
		else if(hitted == true) {
			damage = 0;
		}
		
		else {
			hitted = true;
			int realDamage = damage - defense;
			if(realDamage > 0 && realDamage < 100) 
				life -= realDamage;	
		}
		
		moveToTheDirectionOfHit(direction);
		
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
	
	public void addCFU(int i) {
		cfu += i;
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

	public void damageEnemy() {
				
		if(currentDirection == UP) {
			attackHitbox.y = hitbox.y - attackHitbox.height;
			attackHitbox.x = hitbox.x;
		}
		else if(currentDirection == DOWN) {
			attackHitbox.x = hitbox.x;
			attackHitbox.y = hitbox.y + hitbox.height;
		}
		else if(currentDirection == LEFT) {
			attackHitbox.y = hitbox.y;
			attackHitbox.x = hitbox.x - attackHitbox.width;
		}
		else if(currentDirection == RIGHT) {
			attackHitbox.y = hitbox.y;
			attackHitbox.x = hitbox.x + hitbox.width;
		}
		//se la attackhitbox si interseca con la hitbox di una entità, salviamo il riferimento di quella entità
		EnemyController target = play.getCollisionChecker().isCollisionDuringPlayerAttack(attackHitbox);
		if(target != null) 
			target.hitted(attack, currentDirection, true);
	}
	
	public void setPlayerActionInNewRoom() {
		resetBooleans();
		idle = true;
		currentAction = IDLE;
	}
	
}





