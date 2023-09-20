package controller.playState.entityController.enemyController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public class EnemyController extends EntityController{

	protected int life, attack, defense;
	public static final int NORMAL_STATE = 0, GO_TO_FIRST_TILE = 1, IN_WAY = 2;;
	protected int actualState = NORMAL_STATE ;
	protected int chasingCounter;
	
	public EnemyController(int ind, String type, Hitbox r, PlayStateController p) {
		super(ind, type, r, p);
	}

	@Override
	public void update() {

		switch(actualState) {
		case NORMAL_STATE:
			float xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
			float yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
			
			if(xDistance < play.getController().getTileSize()*3 && yDistance < play.getController().getTileSize()*3) {
				if(actualState == NORMAL_STATE) {
					play.getController().getView().getPlay().getUI().activeExclamation(index);
					goToYourDestination();	
				}
			}
			else
				randomMove();
			break;
			
		case GO_TO_FIRST_TILE:
			currentAction = MOVE;
			goToEdgeOfTile();
			break;
			
		case IN_WAY:
			if(currentPathIndex == path.size() - 1) {
				currentAction = IDLE;
				actualState = NORMAL_STATE;
				currentPathIndex = 0;
				System.out.println("giunto a destinazione");
			}
			else
				goTrhoughtSelectedPath();
			break;
		}
		
		
	}

	public void colpito() {
		life -= play.getPlayer().getAttack() - defense;
	}
	
	public void colpisci() {

	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	//possimao migliorarlo facendo capire al personaggio la posizione delle entità
	//così può scegliere un percorso che schivi anche loro
	private void goToYourDestination() {
		int startCol = (int)(hitbox.x)/play.getController().getTileSize();
		int startRow = (int)(hitbox.y)/play.getController().getTileSize();
		
		int playerCol = (int)(play.getPlayer().getHitbox().x)/play.getController().getTileSize();
		int playerRow = (int)(play.getPlayer().getHitbox().y)/play.getController().getTileSize();

		
		if(play.getPathFinder().search(startCol, startRow, playerCol, playerRow, true)) {
			actualState = GO_TO_FIRST_TILE;	
			path = play.getPathFinder().getPathList();
		}
	}
	
	protected void goTrhoughtSelectedPath() {
		
		//se è arrivato ad un tile del percorso, va al successivo
		if(hitbox.x == path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize() &&
		   hitbox.y == path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize()) {
				currentPathIndex++;
		}
		else {
			//per andare al successivo, vede quale direzione prendere
			float yDistance = hitbox.y - path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();
			float xDistance = hitbox.x - path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();
			
			int directionToCheck = 999;
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
			actualState = IN_WAY;	
		}
			
	}


}
