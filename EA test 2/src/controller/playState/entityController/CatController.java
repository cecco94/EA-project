package controller.playState.entityController;

import java.awt.Rectangle;
import java.util.Random;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public class CatController extends EntityController {

	private int actionCounter;
	private Random randomGenerator = new Random();
	private int azioneACaso, direzioneACaso;
	private boolean scappa;
	private int counterFuga;

	
	public CatController(Rectangle r, PlayStateController p) {
		super(r, p);
		speed = (int)(GamePanel.SCALE*1.5f);
		type = 0;
	}
	
	public void update() {
		choseAction();
	}

	private void choseAction() {
		
		if(!scappa) {
			//se il giocatore si avvicina al gatto, il gatto si sposta nella direzione opposta per un secondo
			//se la direzione opposta è bloccata da un muro, va in un'altra direzione
			int distanzaX = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
			int distanzaY = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
			
			if(distanzaX < GamePanel.TILES_SIZE*2 && distanzaY < GamePanel.TILES_SIZE*2)
				scappa = true;
			else 
				scappa = false;
	
			if(scappa) 
					panic();			
			else 
				normalAction();	
		}
		
		else fuggi();
	//	System.out.println(scappa);
		
	}
	
	private void fuggi() {
		counterFuga++;
		
		if(counterFuga >= 200) {
			scappa = false;
			counterFuga = 0;
		}
		else
			checkCollision();
		
	}

	private void panic() {
		
		int playerDirection = play.getPlayer().getDirection();
		moving = true;
		idle = false;
		
		if(playerDirection == LEFT && play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) {
			direction = LEFT;
			resetDirection();
			left = true;
		}
		
		else if(playerDirection == LEFT && !play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) {
			if(play.getCollisionChecker().canMoveUp(hitbox)) {
				direction = UP;
				resetDirection();
				up = true;
			}
			else {
				direction = DOWN;
				resetDirection();
				down = true;
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
			else {
				direction = UP;
				resetDirection();
				up = true;
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
			else {
				direction = LEFT;
				resetDirection();
				left = true;
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
			else {
				direction = RIGHT;
				resetDirection();
				right = true;
			}
		}		
			
	}

	private void normalAction() {
		actionCounter++;	
		
		if(actionCounter >= 400) {
			resetaction();
			azioneACaso = randomGenerator.nextInt(2);
			
			if (azioneACaso == 0) {
				idle = true;
				moving = false;
			}
			else
				moving = true;
		}
		
		choseDirection();
	}

	private void resetaction() {
		idle = false;
		moving = false;
	}

	private void choseDirection() {
//	System.out.println("gatto colonna " + hitbox.x/GamePanel.TILES_SIZE + " riga " + hitbox.y/GamePanel.TILES_SIZE);
//	System.out.println("gatto x " + hitbox.x + " y " + hitbox.y);
		
	//mettendo un counter anche qui, il gatto cambia direzione anche se sta fermo, muove il muso
		if(actionCounter >= 400) {
			resetDirection();	
			direzioneACaso = randomGenerator.nextInt(4);
			
			if(direzioneACaso == 0) 
				up = true;
			
			else if (direzioneACaso == 1) 
				down = true;
			
			else if(direzioneACaso == 2) 
				left = true;
			
			else if(direzioneACaso == 3) 
				right = true;
			
			actionCounter = 0;
		}
		
		checkCollision();
	}

	private void checkCollision() {
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
		
		//se incontra ostacoli nella mappa, resta fermo
		if(collision) {
			moving = false;
			idle = true;
			
		}
		
	}
	
	public String toString() {
		return "gatto ( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " +  hitbox.height + " )";
	}
	
}
