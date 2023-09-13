package controller.playState.entityController;

import java.util.Random;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public abstract class EntityController {

	//il punto in alto a sinistra della hitbox è la posizione dell'entità nella mappa
	protected Hitbox hitbox, tempHitboxForCheck;
	protected int speed;
	protected PlayStateController play;
	protected boolean moving, idle, attacking, up, down, left, right;
	protected int direction;
	public static final int DOWN = 0, RIGHT = 1, LEFT = 2, UP = 3;
	public static final int IDLE = 0, MOVE = 1, ATTACK = 2, PARRY = 3, THROW = 4, DIE = 5;
	protected int currentAction = IDLE;
	
	// per far camminare l'entità in modo randomico
	protected int actionCounter;
	protected Random randomGenerator = new Random();
	protected int randomAction, randomDirection;
	
	//per capire, se è un NPC quale sia
	protected String type;   
	//l'indice nella lista delle entità
	protected int index;
	
	public EntityController (int ind, String type, Hitbox r, PlayStateController p) {
		index = ind;
		
		this.type = type;
		
		play = p;
		hitbox = r;
		
		//settiamo la posizione nella mappa e scaliamo la dimensione
		hitbox.x *= play.getController().getTileSize(); 
		hitbox.y *= play.getController().getTileSize(); 
		
		hitbox.width  *= play.getController().getGameScale();
		hitbox.height *= play.getController().getGameScale();
		
		//hitbox che serve per le collisioni, prima di cambiare la hitbox, cambiamo questa
		//se controllando le collisioni va tutto bene, cambiamo anche la hitbox
		tempHitboxForCheck = new Hitbox(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		
		resetBooleans();
		idle = true;
		down = true;
		direction = DOWN;
		currentAction = IDLE;

	}
	
	//settiamo la hitbox per creare le entità acnhe se non conosciamo tutti i dati.
	protected void setBounds(int x, int y, int w, int h) {
		hitbox.x = x;
		tempHitboxForCheck.x = x;
		
		hitbox.y = y;
		tempHitboxForCheck.y = y;
		
		hitbox.width = w;
		tempHitboxForCheck.width = w;
		
		hitbox.height = h;
		tempHitboxForCheck.height = h;
		
	}
	
	public abstract void update();
	
	public String getType() {
		return type;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public int getCurrentAction() {
		if(idle) 
			return IDLE;
	
		else if(moving) 
			return MOVE;
	 
		else if(attacking) 
			return ATTACK;
		
		else 
			return THROW;	
		
	}
	
	//quando l'entità è ferma, tutti i booleans sono false e ritorna right di default
	public int getCurrentDirection() {
		if(up)
			return UP;
		
		else if(down)
			return DOWN;
		
		else if(left)
			return LEFT;
		else 
			return RIGHT;
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

	public void setMoving(boolean m) {
		moving = m;
	}
	
	protected void resetBooleans() {
		up = false;
		down = false;
		left = false;
		right = false;	
		idle = false;
		moving = false;	
	}
	
	protected void resetDirection() {
		up = false;
		down = false;
		left = false;
		right = false;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		return "( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " +  hitbox.height + " )";
	}
	
	
	// molti npc si muovono a caso nella stanza usando questo medoto
	public void randomMove() {
		actionCounter++;	
		//ogni due secondi cambia azione e direzione 
		if(actionCounter >= 400) {
			resetAction();
			randomAction = randomGenerator.nextInt(2);
			
			if (randomAction == 0) 
				idle = true;
			
			else
				moving = true;
		}
		
		choseDirection();
		checkCollision();
	}
	
	protected void resetAction() {
		idle = false;
		moving = false;
	}
	
	protected void choseDirection() {		
	//mettendo un counter anche qui, il gatto cambia direzione anche se sta fermo, muove il muso
		if(actionCounter >= 400) {
			resetDirection();	
			randomDirection = randomGenerator.nextInt(4);
			
			if(randomDirection == 0) 
				up = true;
			
			else if (randomDirection == 1) 
				down = true;
			
			else if(randomDirection == 2) 
				left = true;
			
			else if(randomDirection == 3) 
				right = true;
			
			actionCounter = 0;
		}
	}
	
	protected void checkCollision() {
		boolean collision = true;
		
		if (moving && up) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(play.getCollisionChecker().canMoveUp(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().checkCollisionInEntityList(tempHitboxForCheck)) {
					collision = false;
					hitbox.y -= speed;
				}
			}
		}
		
		if (moving && down) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().checkCollisionInEntityList(tempHitboxForCheck)) {
					collision = false;
					hitbox.y += speed;
				}
			}	
		}
		
		if (moving && left) {
			tempHitboxForCheck.x = hitbox.x - speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().checkCollisionInEntityList(tempHitboxForCheck)) {
					collision = false;
					hitbox.x -= speed;
				}
			}				
		}
		
		if (moving && right) {
			tempHitboxForCheck.x = hitbox.x + speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().checkCollisionInEntityList(tempHitboxForCheck)) {
					collision = false;
					hitbox.x += speed;
				}
			}		
		}	
		
		//se incontra ostacoli nella mappa, si ferma 
		if(collision) {
			moving = false;
			idle = true;
		}
		
	}
	
	protected void tunrToInteract() {
		
		resetDirection();
		moving = false;
		idle = true;
		
		if(play.getPlayer().getDirection() == DOWN) {
			direction = UP;
			up = true;
		}
		
		else if(play.getPlayer().getDirection() == UP) {
			direction = DOWN;
			down = true;
		}
		
		else if(play.getPlayer().getDirection() == RIGHT) {
			direction = LEFT;
			left = true;
		}
		
		else if(play.getPlayer().getDirection() == LEFT) {
			direction = RIGHT;
			right = true;
		}	
	}
}
