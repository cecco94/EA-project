package controller.playState.entityController;

import java.awt.Rectangle;
import java.util.Random;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public class CatController extends EntityController {

	private static int hitboxWidth = 28, hitboxHeight = 20;
	private static int catSpeed = (int)(GamePanel.SCALE*1.2f);
	
	private int actionCounter;
	private Random randomGenerator = new Random();
	private int azioneACaso, direzioneACaso;
	
	private boolean inFuga;
	private int counterFuga;

	
	public CatController(int xPos, int yPos, PlayStateController p) {
		super(new Rectangle(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = catSpeed;
		type = 0;
	}
	
	public void update() {
		choseAction();
	}

	private void choseAction() {
		
		if(!inFuga) {
			//se il giocatore si avvicina al gatto, il gatto si sposta nella direzione opposta per un secondo
			//se la direzione opposta è bloccata da un muro, va in un'altra direzione
			int distanzaX = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
			int distanzaY = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
			
			if(distanzaX < GamePanel.TILES_SIZE*2 && distanzaY < GamePanel.TILES_SIZE*2) {
				inFuga = true;
				setDiezioneFuga();	
				speed = (int)(GamePanel.SCALE*1.5f);
			}
			
			else 
				normalAction();	
		}
		
		else 
			fuggiInUNaDirezione();
		
	}
	
	private void fuggiInUNaDirezione() {
		counterFuga++;
		//dopo un secondo di fuga può controlare di nuovo se è inseguito
		if(counterFuga >= 200) {
			inFuga = false;
			counterFuga = 0;
			speed = (int)(GamePanel.SCALE*1.2f);
		}
		//se ancora non è passato un secondo, continua a correre in quella direzione
		//finchè non trovi un muro
		else
			checkCollision();
		
	}

	private void setDiezioneFuga() {
		
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
				inFuga = false;
				System.out.println("fermo");
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
				inFuga = false;
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
				inFuga = false;
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
				inFuga = false;
			}
		}		
			
	}

	private void normalAction() {
		actionCounter++;	
		//ogni due secondi cambia azione e direzione 
		if(actionCounter >= 400) {
			resetaction();
			azioneACaso = randomGenerator.nextInt(2);
			
			if (azioneACaso == 0) 
				idle = true;
			
			else
				moving = true;
		}
		
		choseDirection();
		checkCollision();
		
	}

	private void resetaction() {
		idle = false;
		moving = false;
		inFuga = false;
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
		
		//se incontra ostacoli nella mappa, si ferma e smette di scappare
		if(collision) {
			moving = false;
			idle = true;
			inFuga = false;
			speed = (int)(GamePanel.SCALE*1.2f);
		}
		
	}
	
	public String toString() {
		return "gatto ( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " +  hitbox.height + " )";
	}
	
}
