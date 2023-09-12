package controller.playState.entityController;


import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class CatController extends EntityController {
	
	private static int hitboxWidth = 28, hitboxHeight = 20;
	
	private boolean runningAway;
	private int counterRunnigAway;

	
	public CatController(int i, String type, int xPos, int yPos, PlayStateController p) {
		super(i, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = (int)(play.getController().getGameScale()*1.2f);
	}
	
	public void update() {
		choseAction();
	}

	private void choseAction() {
		
		// se non sta scappando, controlla se il giocatore è nelle vicinanze. se il giocatore è vicino, inizia a scappare e scegli una direzione di fuga
		// se invece il giocatore è lontano, continua a camminare a caso
		// se infine stai già scappando, continua a scappare
		if(!runningAway) {
			//se il giocatore si avvicina al gatto, il gatto si sposta nella direzione opposta per un secondo
			//se la direzione opposta è bloccata da un muro, va in un'altra direzione
			int xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
			int yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
			
			if(xDistance < play.getController().getTileSize()*2 && yDistance < play.getController().getTileSize()*2) {
				runningAway = true;
				setRunAwayDirection();	
				speed = (int)(play.getController().getGameScale()*1.5f);
			}
			
			else 
				randomMove();
		}
		
		else 
			runAwayInChosenDirection();
		
	}
	
	private void runAwayInChosenDirection() {
		counterRunnigAway++;
		//dopo un secondo di fuga può controlare di nuovo se è inseguito
		if(counterRunnigAway >= 200) {
			runningAway = false;
			counterRunnigAway = 0;
			speed = (int)(play.getController().getGameScale()*1.2f);
		}
		//se ancora non è passato un secondo, continua a correre in quella direzione
		//finchè non trovi un muro
		else
			checkCollision();
		
	}

	private void setRunAwayDirection() {
		
		int playerDirection = play.getPlayer().getDirection();
		moving = true;
		idle = false;
		
		//se il giocatore stava fermo ed è il gatto ad essersi avvicinato per caso
		//cambia direzione e scappa
		if(playerDirection == direction) {
			direction++;
			
			if(direction > 3) 
				direction = 0;	
		}
		
		//se il player si avvicina da sinistra, se può scappa a sinistra
		else if(playerDirection == LEFT && play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) {
			direction = LEFT;
			resetDirection();
			left = true;
		}
		//se non può, controlla se può scappare su o giù
		else if(playerDirection == LEFT && !play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) {
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
			//se non può scappare resta fermo
			else {
				idle = true;
				moving = false;
				runningAway = false;
			}
				
		}
				
		else if(playerDirection == RIGHT && play.getCollisionChecker().canMoveRight(tempHitboxForCheck)) {
			direction = RIGHT;
			resetDirection();
			right = true;
		}
		
		else if(playerDirection == RIGHT && !play.getCollisionChecker().canMoveRight(tempHitboxForCheck)) {
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
			else {
				idle = true;
				moving = false;
				runningAway = false;
			}
		}		
		
		
		else if(playerDirection == UP && play.getCollisionChecker().canMoveUp(tempHitboxForCheck)) {
			direction = UP;
			resetDirection();
			up = true;
		}
		
		else if(playerDirection == UP && !play.getCollisionChecker().canMoveUp(tempHitboxForCheck)) {
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
			else {
				idle = true;
				moving = false;
				runningAway = false;
			}
		}		
		
		
		else if(playerDirection == DOWN && play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) {
			direction = DOWN;
			resetDirection();
			down = true;
		}
		
		else if(playerDirection == DOWN && !play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) {
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
			else {
				idle = true;
				moving = false;
				runningAway = false;
			}
		}		
			
	}

	protected void resetAction() {
		super.resetAction();
		runningAway = false;
	}

	protected void checkCollision() {
		//collisione con la mappa
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
		
		//se incontra ostacoli nella mappa, si ferma e smette di scappare
		if(collision) {
			moving = false;
			idle = true;
			runningAway = false;
			speed = (int)(play.getController().getGameScale()*1.2f);
		}
		
	}
	
	public String toString() {
		return "gatto ( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " +  hitbox.height + " )";
	}
	
}
