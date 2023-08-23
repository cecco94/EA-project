package controller.playState.entityController;

import java.awt.Rectangle;
import java.util.Random;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public class CatController extends EntityController {

	private int actionCounter;
	private Random randomGenerator = new Random();
	private int azioneACaso, direzioneACaso;
	
	public CatController(Rectangle r, PlayStateController p) {
		super(r, p);
		speed = (int)(GamePanel.SCALE*1.3f);
		type = 0;
		
		down = true;
		idle = true;
	}

	public void update() {
		choseAction();
		choseDirection();
		checkCollision();
	}

	private void choseAction() {
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
		
	}

	private void resetaction() {
		idle = false;
		moving = false;
	}

	private void choseDirection() {
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

	private void resetDirection() {
		up = false;
		down = false;
		left = false;
		right = false;
	}

	private void checkCollision() {
		//collisione con la mappa
		if (moving && up) {
			if(!play.getCollisionChecker().canMoveUp(hitbox)) {
				moving = false;
				idle = true;
			}
			else
				hitbox.y -= speed;
		}
		
		if (moving && down) {
			if(!play.getCollisionChecker().canMoveDown(hitbox)) {
				moving = false;
				idle = true;
			}
			else
				hitbox.y += speed;
		}
		
		if (moving && left) {
			if(!play.getCollisionChecker().canMoveLeft(hitbox)) {
				moving = false;
				idle = true;
			}
			else
				hitbox.x -= speed;
		}
		
		if (moving && right) {
			if(!play.getCollisionChecker().canMoveRight(hitbox)) {
				moving = false;
				idle = true;
			}
			else
				hitbox.x += speed;
		}
		
		//collisione con gli altri enti
		
	}
	
	public String toString() {
		return "gatto ( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " +  hitbox.height + " )";
	}
	
	
}
