package controller.playState.entityController;

import java.util.ArrayList;
import java.util.Random;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.pathfinding.Node;

public abstract class EntityController {

	//il punto in alto a sinistra della hitbox è la posizione dell'entità nella mappa
	protected Hitbox hitbox, tempHitboxForCheck;
	protected float speed;
	
	protected PlayStateController play;
	
	protected int currentDirection;
	public static final int DOWN = 0, RIGHT = 1, LEFT = 2, UP = 3;
	public static final int IDLE = 0, MOVE = 1, ATTACK = 2, PARRY = 3, THROW = 4, DIE = 5;
	protected int currentAction = IDLE;
	
	// per far camminare l'entità in modo randomico
	protected int actionCounter;
	protected Random randomGenerator = new Random();
	protected int randomAction, randomDirection;
	
	//per capire, se è un NPC quale sia
	protected String type;   
	//l'indice nella lista delle entità
	protected int index;
	
	//quando l'entità viene creata, si segna la posizione in righe & colonne
	//ogni volta che si muove, controlla se ha cambiato tile
	//se ha cambiato tile, aggiorna la mappa della posizione dei personaggi
	//segnando true sul nuovo quadratino e false su quello vecchio
	//infine mette la nuova posizione su quella vecchia
	protected int savedCol;
	protected int savedRow;
	//percorso che l'entità deve seguire
	protected ArrayList<Node> path;
	protected int currentPathIndex = 0;
	
	//per gestire gli npc ed i nemici come macchine a stati
	protected final int RANDOM_MOVE = 0, GO_TO_FIRST_TILE = 1, IN_WAY = 2; 
	protected int currentState = RANDOM_MOVE;
	
	public EntityController (int ind, String type, Hitbox r, PlayStateController p) {
		index = ind;
		
		this.type = type;
		
		play = p;
		hitbox = r;
		
		//settiamo la posizione nella mappa e scaliamo la dimensione
		//siccome stiamo usando i float per definire la posizione, forse servirebbe un cast a int 
		//perchè prima era tutto in int e non vorrei sminchiare la posizione delle entità
		hitbox.x *= play.getController().getTileSize(); 
		hitbox.y *= play.getController().getTileSize(); 
		
		hitbox.width  *= play.getController().getGameScale();
		hitbox.height *= play.getController().getGameScale();
		
		//hitbox che serve per le collisioni, prima di cambiare la hitbox, cambiamo questa
		//se controllando le collisioni va tutto bene, cambiamo anche la hitbox
		tempHitboxForCheck = new Hitbox((int)hitbox.x, (int)hitbox.y, hitbox.width, hitbox.height);
		
		savedCol = (int)(hitbox.x)/play.getController().getTileSize();
		savedRow = (int)(hitbox.y)/play.getController().getTileSize();
		path = new ArrayList<>();
		
		currentDirection = DOWN;
		currentAction = IDLE;

	}
	
	//settiamo la hitbox per creare le entità acnhe se non conosciamo tutti i dati.
	protected void setBounds(int x, int y, int w, int h) {
		hitbox.x = (float)x;
		tempHitboxForCheck.x = (float)x;
		
		hitbox.y = (float)y;
		tempHitboxForCheck.y = (float)y;
		
		hitbox.width = w;
		tempHitboxForCheck.width = w;
		
		hitbox.height = h;
		tempHitboxForCheck.height = h;
		
	}
	
	public abstract void update();
	
	public String getType() {
		return type;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public int getCurrentAction() {
		return currentAction;		
	}
	
	//quando l'entità è ferma, tutti i booleans sono false e ritorna right di default
	public int getCurrentDirection() {
		return currentDirection;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		return "( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " +  hitbox.height + " )";
	}
	
	// molti npc si muovono a caso nella stanza usando questo medoto
	public void randomMove() {
		actionCounter++;	
		//ogni due secondi cambia azione e direzione 
		if(actionCounter >= 400) {
			choseDirection();
			choseAction();
		}
		
		if(currentAction == MOVE && canMove()) {
			if(currentDirection == UP) {
				hitbox.y -= speed;	
			}
			else if(currentDirection == DOWN) {
				hitbox.y += speed;
			}
			else if(currentDirection == LEFT) {
				hitbox.x -= speed;
			}
			else if(currentDirection == RIGHT) {
				hitbox.y += speed;
			}
			
			//ogni volta che si muove, vede se ha cambiato tile, in tal caso aggiorna i dati nella mappa e la posizione precedente
			//si può migliorare, perchè una hitbox può essere a cavallo tra due o più tile
			int currentCol = (int)(hitbox.x)/play.getController().getTileSize();
			int currentRow = (int)(hitbox.y)/play.getController().getTileSize();

			if(savedCol != currentCol || savedRow != currentRow) {
				play.getRoom(play.getCurrentroomIndex()).getEntityPositionsForPathFinding()[savedRow][savedCol]	= 0;
				play.getRoom(play.getCurrentroomIndex()).getEntityPositionsForPathFinding()[currentRow][currentCol]	= 1;
				savedRow = currentRow;
				savedCol = currentCol;
			}
		}
		else
			currentAction = IDLE;
		
	}
	
	private void choseAction() {
		randomAction = randomGenerator.nextInt(2);
		
		if (randomAction == IDLE) 
			currentAction = IDLE;
		
		else 
			currentAction = MOVE;
			
	}
	
	protected void choseDirection() {		
	//mettendo un counter anche qui, il gatto cambia direzione anche se sta fermo, muove il muso
	//	if(actionCounter >= 400) {
		randomDirection = randomGenerator.nextInt(4);
		
		if(randomDirection == 0) { 
			currentDirection = DOWN;
		}
		
		else if (randomDirection == RIGHT) { 
			currentAction = RIGHT;
		}
		
		else if(randomDirection == LEFT) {
			currentDirection = LEFT;
		}
		
		else if(randomDirection == UP) {
			currentDirection = UP;
		}
		
		actionCounter = 0;
	//	}
	}
	
	protected boolean canMove() {
		boolean canMove = false;
		
		if(currentDirection == UP) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y - speed;
			if(play.getCollisionChecker().canMoveUp(tempHitboxForCheck)) 
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
					canMove = true;
		}
		
		if(currentDirection == DOWN) {
			tempHitboxForCheck.x = hitbox.x;
			tempHitboxForCheck.y = hitbox.y + speed;
			if(play.getCollisionChecker().canMoveDown(tempHitboxForCheck)) 
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
					canMove = true;
		}
		
		if(currentDirection == LEFT) {
			tempHitboxForCheck.x = hitbox.x - speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveLeft(tempHitboxForCheck)) 
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
					canMove = true;
		}
		
		if(currentDirection == RIGHT) {
			tempHitboxForCheck.x = hitbox.x + speed;
			tempHitboxForCheck.y = hitbox.y;
			if(play.getCollisionChecker().canMoveRight(tempHitboxForCheck)) 
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
					canMove = true;
		
		}	
		
		return canMove;
		
	}
	
	protected void tunrToInteract() {
		
		currentAction = IDLE;
		
		if(play.getPlayer().getCurrentDirection() == DOWN) {
			currentDirection = UP;
		}
		
		else if(play.getPlayer().getCurrentDirection() == UP) {
			currentDirection = DOWN;
		}
		
		else if(play.getPlayer().getCurrentDirection() == RIGHT) {
			currentDirection = LEFT;
		}
		
		else if(play.getPlayer().getCurrentDirection() == LEFT) {
			currentDirection = RIGHT;
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

	protected void checkDown(float yDistance) {
		
		if(Math.abs(yDistance) > speed) {
			currentDirection = DOWN;
			tempHitboxForCheck.y = hitbox.y + speed;
			tempHitboxForCheck.x = hitbox.x;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.y += speed;
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
			hitbox.y = path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();	
	}

	protected void checkUp(float yDistance) {
			
		if (Math.abs(yDistance) > speed) {
			currentDirection = UP;
			tempHitboxForCheck.y = hitbox.y - speed;
			tempHitboxForCheck.x = hitbox.x;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.y -= speed;
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
			hitbox.y = path.get(currentPathIndex).getRowInGraph()*play.getController().getTileSize();
	}
		
	protected void checkRight(float xDistance) {
		
		if(Math.abs(xDistance) > speed) {
			currentDirection = RIGHT;
			tempHitboxForCheck.x = hitbox.x + speed;
			tempHitboxForCheck.y = hitbox.y;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.x += speed;
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
			hitbox.x = path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();	
	}

	protected void checkLeft(float xDistance) {
			
		if(Math.abs(xDistance) > speed) {
			currentDirection = LEFT;
			tempHitboxForCheck.x = hitbox.x - speed;
			tempHitboxForCheck.y = hitbox.y;
			if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
				hitbox.x -= speed;
		}
		else if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck))
			hitbox.x = path.get(currentPathIndex).getColInGraph()*play.getController().getTileSize();	
	}
}
