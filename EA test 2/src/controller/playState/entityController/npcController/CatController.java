package controller.playState.entityController.npcController;


import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public class CatController extends EntityController {
	
	private static int hitboxWidth = 28, hitboxHeight = 20;
	
	private int counterRunning;

	//la ai del gatto è espressa tramite delle costanti che indicano gli stati in cui si può trovare
	public final static int NORMAL_STATE = 0, CHOSE_DIRECTION = 1, RUNNING = 2, CAUGHT = 3;
	//in base allo stato attuale, il gatto si comporterà in modo diverso
	private int currentState = NORMAL_STATE;
	
	
	public CatController(int i, String type, int xPos, int yPos, PlayStateController p) {
		super(i, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = (int)(play.getController().getGameScale()*1.2f);
	}
	
	public void update() {
		
		switch(currentState) {
		//quando è tranquillo controlla se il giovìcatore è vicino, in caso affermativo inizia a scappare, altrimenti si muove a caso
		case NORMAL_STATE:	
			
			int xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
			int yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
			if(xDistance < play.getController().getTileSize()*1.5 && yDistance < play.getController().getTileSize()*1.5) 
				currentState = CHOSE_DIRECTION;		
			else 
				randomMove();
			break;
			
		//se il giocatore è vicino, sceglie una direzione dove scappare e aumenta la velocità
		case CHOSE_DIRECTION:	 
			setRunAwayDirection();
			speed = (int)(play.getController().getGameScale()*1.5f);
			currentState = RUNNING;
			break;
			
		//corre per mezzo secondo nella direzione scelta
		case RUNNING:	
			handleRunningState();
			break;
			
		//se è catturato, puoi parlarci
		case CAUGHT:
			handleCaughtState();
			break;	
			
		}
		
	}

	private void handleCaughtState() {
		play.getController().getView().getPlay().getUI().setMessage("premi E per parlare");
		play.getController().getView().getPlay().getUI().setShowMessage(true);
		
		if(play.getPlayer().isInteracting()) {		
			tunrToInteract();
			play.getPlayer().speak(index);
		}
		
		int xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);		
		int yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
		//quando il giocatore si allontana, riprende la sua vita
		if(xDistance >= play.getController().getTileSize()*1.5 || yDistance >= play.getController().getTileSize()*1.5)
			currentState = NORMAL_STATE;	
		
	}

	private void handleRunningState() {
		counterRunning++;
		if(counterRunning < 150) {
			int xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
			int yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);	
			//se incontra un muro e il giocatore è vicino si arrende
			if(checkCollision() && xDistance < play.getController().getTileSize() && yDistance < play.getController().getTileSize()) {
				counterRunning = 0;
				speed = (int)(play.getController().getGameScale()*1.2f);
				currentState = CAUGHT;
			}
			//se incontra un muro e il giocatore è lontatno si tranquillizza
			else if(checkCollision() && xDistance > play.getController().getTileSize()*1.5 && yDistance > play.getController().getTileSize()*1.5) {		
				currentState = NORMAL_STATE;									
				speed = (int)(play.getController().getGameScale()*1.2f);
				counterRunning = 0;
			}
		}	
		//se è passato mezzo secondo, esce dallo stato di panico
		else {
			counterRunning = 0;			
			speed = (int)(play.getController().getGameScale()*1.2f);
			currentState = NORMAL_STATE;
		}	
		
	}

	private void setRunAwayDirection() {
		
		int playerDirection = play.getPlayer().getDirection();
		boolean isPlayerMoving = play.getPlayer().isMoving();
		moving = true;
		idle = false;
		
		//se il giocatore stava fermo ed è il gatto ad essersi avvicinato per caso
		if(!isPlayerMoving) 
			tornaIndietro();
			
		else
			scegliDirezAllontanamento(playerDirection);
	}
	
	private void scegliDirezAllontanamento(int playerDirection) {			
		//se il player si avvicina da sinistra, se può scappa a sinistra
		
		switch(playerDirection) {
		case LEFT:
			if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) {
				direction = LEFT;
				resetDirection();
				left = true;
			}
			else {
				if(play.getCollisionChecker().canMoveUp(hitbox)) {
					direction = UP;
					resetDirection();
					up = true;
				}
				else if (play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) {
					direction = DOWN;
					resetDirection();
					down = true;
				}
			}
			break;
		
		case RIGHT:
			if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck)) {
				direction = RIGHT;
				resetDirection();
				right = true;
			}
			else {
				if(play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) {
					direction = DOWN;
					resetDirection();
					down = true;
				}
				else if(play.getCollisionChecker().canMoveUp(tempHitboxForCheck)) {
					direction = UP;
					resetDirection();
					up = true;
				}
			}	
			break;
			
		case UP:
			if(play.getCollisionChecker().canMoveUp(tempHitboxForCheck)) {
				direction = UP;
				resetDirection();
				up = true;
			}
			else {
				if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck)) {
					direction = RIGHT;
					resetDirection();
					right = true;
				}
				else if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)){
					direction = LEFT;
					resetDirection();
					left = true;
				}
			}		
			break;
		
		case DOWN:
			if(play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) {
				direction = DOWN;
				resetDirection();
				down = true;
			}	
			else {
				if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) {
					direction = LEFT;
					resetDirection();
					left = true;
				}
				else if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck)){
					direction = RIGHT;
					resetDirection();
					right = true;
				}
			}		
		break;
				
		}
	}	
	
	private void tornaIndietro() {
		if(direction == UP) {
			direction = DOWN;
			resetDirection();
			down = true;
		}
		else if(direction == DOWN) {
			direction = UP;
			resetDirection();
			up = true;
		}
		else if(direction == LEFT) {
			direction = RIGHT;
			resetDirection();
			right = true;
		}
		else if(direction == RIGHT) {
			direction = LEFT;
			resetDirection();
			left = true;
		}		
	}

	public String toString() {
		return "gatto ( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " +  hitbox.height + " )";
	}
	
}
