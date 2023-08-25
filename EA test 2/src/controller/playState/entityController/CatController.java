package controller.playState.entityController;

import java.awt.Rectangle;
import java.util.Random;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public class CatController extends EntityController {

	private int actionCounter;
	private Random randomGenerator = new Random();
	private int azioneACaso, direzioneACaso;
	
//	//posizione nella mappa del roomModel, prima dello spostamento
//	private int tilePreOccupatoRiga;
//	private int tilePreOccupatoColonna;
	
	public CatController(Rectangle r, PlayStateController p) {
		super(r, p);
		
		speed = (int)(GamePanel.SCALE*1.0f);
		type = 0;

	}
	
	public void update() {
		choseAction();
		choseDirection();
		checkCollision();
	//	occupyPosition();
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

	private void resetDirection() {
		up = false;
		down = false;
		left = false;
		right = false;
	}

	private void checkCollision() {
		//collisione con la mappa
		boolean collision = true;
		
		if (moving && up) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(play.getCollisionChecker().canMoveUp(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().siSchiantaConQualcosaNellaLista(tempHitboxForCheck)) {
					collision = false;
					hitbox.y -= speed;
				}
			}
		}
		
		if (moving && down) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().siSchiantaConQualcosaNellaLista(tempHitboxForCheck)) {
					collision = false;
					hitbox.y += speed;
				}
			}	
		}
		
		if (moving && left) {
			tempHitboxForCheck.x = hitbox.x - speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().siSchiantaConQualcosaNellaLista(tempHitboxForCheck)) {
					collision = false;
					hitbox.x -= speed;
				}
			}				
		}
		
		if (moving && right) {
			tempHitboxForCheck.x = hitbox.x + speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck)) {
				if(!play.getCollisionChecker().siSchiantaConQualcosaNellaLista(tempHitboxForCheck)) {
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
	
//	//dice alla mappa nel roomModel che il tile dove si trova lui è occupato
//	private void occupyPosition() {
//		//tile occupato dopo lo spostamento
//		int tilePostOccupatoRiga = hitbox.y/GamePanel.TILES_SIZE;
//		int tilePostOccupatoColonna = hitbox.x/GamePanel.TILES_SIZE;
//		
//		//se ha cambiato tile da occupare, bidogna aggiornare i dati nella mappa del roomModel
//		if(tilePostOccupatoRiga != tilePreOccupatoRiga || tilePostOccupatoColonna != tilePreOccupatoColonna) {
//			
//			int indiceStanzaDaModificare = Stanze.stanzaAttuale.indiceMappa;
//			
//			play.getController().getModel().getStanza(indiceStanzaDaModificare).aggiungiEntitaAlTile(tilePostOccupatoRiga, tilePostOccupatoColonna);
//			play.getController().getModel().getStanza(indiceStanzaDaModificare).togliEntitaAlTile(tilePreOccupatoRiga, tilePreOccupatoColonna);
//			
////			System.out.println("tiple prima riga "+ tilePreOccupatoRiga + " colonna " + tilePreOccupatoColonna);
////			System.out.println("tiple dopo riga "+ tilePostOccupatoRiga + " colonna " + tilePostOccupatoColonna);
//			
//			tilePreOccupatoRiga = tilePostOccupatoRiga;
//			tilePreOccupatoColonna = tilePostOccupatoColonna;	
//		}
//		
//	}
	
}
