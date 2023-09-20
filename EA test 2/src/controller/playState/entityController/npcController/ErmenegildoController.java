package controller.playState.entityController.npcController;

import java.util.ArrayList;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;
import controller.playState.pathfinding.Node;

public class ErmenegildoController extends EntityController {
	
	private static int hitboxWidth = (int)(16*1.5), hitboxHeight = (int)(17*1.5);
	private final int RANDOM_MOVE = 0, GO_TO_FIRST_TILE = 1, IN_WAY = 2; 
	private int currentState = RANDOM_MOVE;
	private ArrayList<Node> path;
	private int currentPathIndex = 0;
	private int directionToCheck = DOWN;
	
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
			
//			System.out.println("hitbox x " + hitbox.x + " hitbox y " + hitbox.y);
//			System.out.println("x da raggiungere " + path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize() +
//								" y da raggiungere " + path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize() );
			
			
			if(currentPathIndex == path.size()) {
				currentAction = IDLE;
				currentState = RANDOM_MOVE;
				currentPathIndex = 0;
				System.out.println("giunto a destinazione");
			}
			else
				proseguiNelPercorso();
			break;
		}
	}

	//possimao migliorarlo facendo capire al personaggio la posizione delle entità
	//così può scegliere un percorso che schivi anche loro
	private void goToYourDestination() {
		int startCol = (int)(hitbox.x)/play.getController().getTileSize();
		int startRow = (int)(hitbox.y)/play.getController().getTileSize();
		
		if(play.getPathFinder().search(startCol, startRow, 27, 9)) {
			currentState = GO_TO_FIRST_TILE;	
			path = play.getPathFinder().getPathList();
		}
	}
	
	private void proseguiNelPercorso() {
		
		//se è arrivato ad un tile del percorso, va al successivo
		if(hitbox.x == path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize() &&
		   hitbox.y == path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize()) {
				currentPathIndex++;
		}
		else {
			//per andare al successivo, vede quale direzione prendere
			float yDistance = hitbox.y - path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();
			float xDistance = hitbox.x - path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();
			
			//prima controlla se deve salire o scendere
			if(yDistance < 0)
				directionToCheck = DOWN;
			
			else if(yDistance > 0)
				directionToCheck = UP;
			
			//se non deve salire o scendere, vede se deve andare a destra o a sinistra
			else if(yDistance == 0) {
								
				if(xDistance > 0)
					directionToCheck = LEFT;
				
				else if(xDistance < 0)
					directionToCheck = RIGHT;
			}

			//capita la direzione da prendere, entra in questo switch
				switch (directionToCheck) {
				case DOWN:
					checkDown(yDistance);
					break;
	
				case UP:
					checkUp(yDistance);
					break;
				
				case RIGHT:
					checkRight(xDistance);
					break;
					
				case LEFT:
					checkLeft(xDistance);
					break;
				}
			
		}
	}

	private void checkDown(float yDistance) {
		
		if(Math.abs(yDistance) > speed) {
			currentDirection = DOWN;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.y += speed;
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(hitbox))
			hitbox.y = path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();	
	}

	private void checkUp(float yDistance) {
			
		if (Math.abs(yDistance) > speed) {
			currentDirection = UP;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.y -= speed;
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(hitbox))
			hitbox.y = path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();
	}
		
	private void checkRight(float xDistance) {
		
		if(Math.abs(xDistance) > speed) {
			currentDirection = RIGHT;
			tempHitboxForCheck.x = hitbox.x + speed;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.x += speed;
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(hitbox))
			hitbox.x = path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();	
	}

	private void checkLeft(float xDistance) {
			
		if(Math.abs(xDistance) > speed) {
			currentDirection = LEFT;
			tempHitboxForCheck.x = hitbox.x - speed;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.x -= speed;
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(hitbox))
			hitbox.x = path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();	
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
