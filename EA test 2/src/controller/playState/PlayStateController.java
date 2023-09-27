package controller.playState;


import java.util.ArrayList;

import controller.IController;
import controller.main.Gamestate;
import controller.playState.entityController.EntityController;
import controller.playState.entityController.PlayerController;
import controller.playState.pathfinding.PathFinder;
import controller.playState.entityController.BulletController;

import model.mappa.Rooms;

//controlla ciò che accade nel gioco durante il play state
public class PlayStateController {
	
	private PlayerController playerController;
	private Collisions collisionCheck;
	private IController controller;	
	private ArrayList<BulletController> bulletsInRoom;
	private RoomController[] stanzeController;
	private PathFinder pathFinder;
	
	
	public PlayStateController(IController c) {
		controller = c;
		collisionCheck = new Collisions(c); 		
		Hitbox r = new Hitbox(18, 19, 0, 0);
		playerController = new PlayerController(r, this);
		bulletsInRoom = new ArrayList<>();
		initRooms();
		
		//per ora il pathfinder ha un grafo grande quanto la stanza più grande
		pathFinder = new PathFinder(this, 50, 50);
	
	}

	public void initRooms() {
		stanzeController = new RoomController[Rooms.numStanze];
		stanzeController[Rooms.BIBLIOTECA.mapIndex] = new RoomController(this, 30, 40);
		stanzeController[Rooms.DORMITORIO.mapIndex] = new RoomController(this, 34, 50);		
		stanzeController[Rooms.AULA_STUDIO.mapIndex] = new RoomController(this, 50, 50);
		stanzeController[Rooms.TENDA.mapIndex] = new RoomController(this, 24, 30);
		stanzeController[Rooms.LABORATORIO.mapIndex] = new RoomController(this, 34, 40);
		stanzeController[Rooms.STUDIO_PROF.mapIndex] = new RoomController(this, 41, 50);
		
	}

	public void update() {
		//aggiorna il personaggio
		playerController.update();		
		
		//aggiorna i proiettili
		updateBullets();
				
		//aggiorna gli altri elementi del gioco in base alla stanza dove si trova il giovatore
		//Per rendere la cosa più leggera, possiamo aggiornare solo quelli all'interno dell'area visibile
		switch(Rooms.currentRoom) {
		case DORMITORIO:
			stanzeController[Rooms.DORMITORIO.mapIndex].update();		
			break;
		case BIBLIOTECA:
			stanzeController[Rooms.BIBLIOTECA.mapIndex].update();		
			break;
		case AULA_STUDIO:
			stanzeController[Rooms.AULA_STUDIO.mapIndex].update();	
			break;
		case TENDA:
			stanzeController[Rooms.TENDA.mapIndex].update();	
			break;
		case LABORATORIO:
			stanzeController[Rooms.LABORATORIO.mapIndex].update();	
			break;
		case STUDIO_PROF: 
			stanzeController[Rooms.STUDIO_PROF.mapIndex].update();
			break;
		default:
			break;
		}
	}
	
	private void updateBullets() {
		for(int index = 0; index < bulletsInRoom.size(); index++)
			bulletsInRoom.get(index).update();	
	}

	public void setPlayerPos(int x, int y) {
		playerController.getHitbox().x = x;
		playerController.getHitbox().y = y;	
	}

	public void resumeGameAfterTransition() {
		controller.getModel().setNewRoom();
		
		playerController.getHitbox().x = controller.getModel().getNewXPos();
		playerController.getHitbox().y = controller.getModel().getNewYPos();	
		playerController.resetBooleans();
		
		bulletsInRoom.clear();
		controller.getView().getPlay().getBullets().clear();
	}
	
	public void goToPauseState() {
		controller.setGameState(Gamestate.PAUSE);
	}
	
	public void startPlayerAttacking() {
		if(!playerController.isParring())
			playerController.setAttacking(true);
	}
	
	public void startPlayerParring() {
		playerController.setParry(true);
	}
	
	public void startPlayerThrowing() {
		if(!playerController.isParring()) {
			
			if(playerController.getNotes() > 0) 
				playerController.setThrowing(true);
			
			else {
				controller.getView().getPlay().getUI().setMessage("appunti finiti");
				controller.getView().getPlay().getUI().setShowMessage(true);
			}
		}
	}
	
	public void startPlayerInteract() {
		if(!playerController.isParring())
			playerController.setInteracting(true);
	}
	

	public void stopPlayerAttacking() {
		if (!playerController.isParring()) {
			playerController.setAttacking(false);
			playerController.damageEnemy();
		}
	}
	
	public void stopPlayerParring() {
		playerController.setParry(false);
		controller.getView().getPlay().getPlayer().resetParry();
	}
	
	public void stopPlayerThrowing() {
		if(!playerController.isParring()) {
			
			if(playerController.getNotes() > 0) {
				playerController.decreaseBulletsNumber();
				playerController.setThrowing(false);
				addBullets(playerController);
				controller.getView().getPlay().addBullet();
			}
			
		}
	}
	
	public void stopPlayerInteracting() {
		if(!playerController.isParring())
			playerController.setInteracting(false);
	}
	
	public PlayerController getPlayer() {
		return playerController;
	}
	
	public Collisions getCollisionChecker() {
		return collisionCheck;
	}
	
	public IController getController() {
		return controller;
	}
	
	public void addBullets(EntityController owner) {
		bulletsInRoom.add(new BulletController(this, bulletsInRoom.size(), owner));
	}
	
	public void removeBullets(int index) {
		//siccome tutti quelli a destra di index si spostano a sinistra di uno, l'indice deve 
		//essere aggiornato
		try {
			for(int i = index; i < bulletsInRoom.size(); i++)
				bulletsInRoom.get(i).decreaseIndexInList();
			
			bulletsInRoom.remove(index);
		}
		catch(IndexOutOfBoundsException iobe) {
			bulletsInRoom.clear();
			controller.getView().getPlay().getBullets().clear();
			System.out.println("problemi appunti controller");
		//	iobe.printStackTrace();
		}
	}
	
	public ArrayList<BulletController> getBulletsInRoom(){
		return bulletsInRoom;
	}
	
	public RoomController getRoom(int index) {
		return stanzeController[index];
	}
	
	public int getCurrentroomIndex() {
		return Rooms.currentRoom.mapIndex;
	}

	public PathFinder getPathFinder() {
		return pathFinder;
	}

	public void printMatriceEntita() {
		for(RoomController room : stanzeController)
			room.printMatriceEntita();
	}

	public void removeEnemy(int index) {
		stanzeController[getCurrentroomIndex()].removeEnemy(index);	
	}
	
	public int getTileSize() {
		return controller.getTileSize();
	}
}
