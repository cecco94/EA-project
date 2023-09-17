package controller.playState.entityController.npcController;

import java.util.ArrayList;

import controller.playState.Hitbox;
import controller.playState.Node;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public class ErmenegildoController extends EntityController {
	
	private static int hitboxWidth = (int)(16*1.5), hitboxHeight = (int)(17*1.5);
	private final int RANDOM_MOVE = 0, GO_TO_FIRST_TILE = 1, IN_WAY = 2; 
	private int currentState = RANDOM_MOVE;
	private ArrayList<Node> path;
	private int currentPathIndex = 0;
	
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
			goToEdgeOfTile();
			break;
				
		case IN_WAY:
			if(currentPathIndex == path.size()) {
				currentState = RANDOM_MOVE;
				currentPathIndex = 0;
				System.out.println("giunto a destinazione");
			}
			else
				proseguiNelPercorso();
//				System.out.println(currentPathIndex);
//				System.out.println(path.get(currentPathIndex).getColInGraph() + " " + path.get(currentPathIndex).getRowInGraph());
			break;
		}
	
	//	System.out.println(currentState);
	}

	private void goToYourDestination() {
		int startCol = (int)(hitbox.x)/play.getController().getTileSize();
		int startRow = (int)(hitbox.y)/play.getController().getTileSize();
		
		if(play.getPathFinder().search(startCol, startRow, 26, 19)) {
			currentState = GO_TO_FIRST_TILE;	
			path = play.getPathFinder().getPathList();
		}
	}
	
	private void proseguiNelPercorso() {
		
		System.out.println("posizione " + hitbox.x/play.getController().getTileSize() + ", " + hitbox.y/play.getController().getTileSize());
		System.out.println("prossimo tile " + path.get(currentPathIndex).getColInGraph() + ", " + path.get(currentPathIndex).getRowInGraph());
		
		if(hitbox.x == path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize() &&
		   hitbox.x == path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize()) {
				currentPathIndex++;
		}
		else {
			checkLeft();
			checkRight();
			checkUp();
			checkDown();
		}
	}

	private void checkDown() {
		if(hitbox.y - path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize() < speed) {
//			hitbox.y += speed;
			System.out.println("devo scendere");
//		}
//		else {
//			hitbox.y = path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();
		}
	}

	private void checkUp() {
		if(hitbox.y - path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize() > speed) {
//			hitbox.y -= speed;
			System.out.println("devo salire");
//		}
//		else {
//			hitbox.y = path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();	
		}
	}

	private void checkRight() {
		if(hitbox.x - path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize() < speed) {
//			hitbox.x += speed;
			System.out.println("devo andare a destra");
//		}
//		else {
//			hitbox.x = path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();	
		}
	}

	private void checkLeft() {
		if(hitbox.x - path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize() > speed) {
//			hitbox.x -= speed;
			System.out.println("devo andare a sinistra");
//		}
//		else {
//			hitbox.x = path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();	
		}
	}
	

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
