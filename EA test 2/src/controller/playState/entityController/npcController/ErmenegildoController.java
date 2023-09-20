package controller.playState.entityController.npcController;

import java.util.ArrayList;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public class ErmenegildoController extends EntityController {
	
	private static int hitboxWidth = (int)(16*1.5), hitboxHeight = (int)(17*1.5);
	
	public ErmenegildoController(int i, String type, int xPos, int yPos, PlayStateController p) {
		super(i, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = play.getController().getGameScale()*0.3f;	
		path = new ArrayList<>();
	}

	@Override
	public void update() {
		switch(currentState){
		case RANDOM_MOVE:
			float xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
			float yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
			
			if(xDistance < play.getController().getTileSize()*1.5 && yDistance < play.getController().getTileSize()*1.5) {			
				play.getController().getView().getPlay().getUI().setMessage("premi E per parlare");
				play.getController().getView().getPlay().getUI().setShowMessage(true);
				
				if(play.getPlayer().isInteracting()) {
					goToYourDestination();
				//	tunrToInteract();
				//	play.getPlayer().speak(index);
				}
			}
			
			else {
				randomMove();
			}

			break;
			
		case GO_TO_FIRST_TILE:
			currentAction = MOVE;
			goToEdgeOfTile();
			break;
				
		case IN_WAY:			
			if(currentPathIndex == path.size()) {
				currentAction = IDLE;
				currentState = RANDOM_MOVE;
				currentPathIndex = 0;
				System.out.println("giunto a destinazione");
			}
			else
				goTrhoughtSelectedPath();
			break;
		}
	}

	//possimao migliorarlo facendo capire al personaggio la posizione delle entità
	//così può scegliere un percorso che schivi anche loro
	private void goToYourDestination() {
		int startCol = (int)(hitbox.x)/play.getController().getTileSize();
		int startRow = (int)(hitbox.y)/play.getController().getTileSize();
		
		if(play.getPathFinder().search(startCol, startRow, 27, 9, false)) {
			currentState = GO_TO_FIRST_TILE;	
			path = play.getPathFinder().getPathList();
		}
	}
	
	//possiamo renderlo più efficace facendolo andare non sul tile dove si trova ma sul primo tile dove deve andare
	//così può anche partire da un tile mezzo solido
	private void goToEdgeOfTile() {
		int startCol = (int)(hitbox.x)/play.getController().getTileSize();
		int startRow = (int)(hitbox.y)/play.getController().getTileSize();

		//se sta troppo a destra, si sposta a sinistra fino ad arrivare vicinissimo al bordo del tile, poi la hitbox si attacca al bordo
		if(hitbox.x > startCol*play.getController().getTileSize()) {
			if((hitbox.x - startCol*play.getController().getTileSize()) > speed) {
				currentDirection = LEFT;
				hitbox.x -= speed;
			}
			else {
				hitbox.x = (int)(startCol*play.getController().getTileSize());
			}
		}
		
		//se sta troppo in basso
		else if(hitbox.y > startRow*play.getController().getTileSize()) {
			if((hitbox.y - startRow*play.getController().getTileSize()) > speed) {
				currentDirection = UP;
				hitbox.y -= speed;
			}
			else {
				hitbox.y = (int)(startRow*play.getController().getTileSize());	
			}
		}
				
		if(hitbox.x == startCol*play.getController().getTileSize() && hitbox.y == startRow*play.getController().getTileSize()) {
			currentState = IN_WAY;	
		}
			
	}

	
}
