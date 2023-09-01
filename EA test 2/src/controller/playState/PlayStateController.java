package controller.playState;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import java.awt.Rectangle;

import controller.IController;
import controller.main.Gamestate;
import controller.playState.entityController.EntityController;
import controller.playState.entityController.PlayerController;
import controller.playState.entityController.Bullet;

import model.mappa.Stanze;

//controlla ciò che accade nel gioco durante il play state
public class PlayStateController {
	
	private PlayerController playerController;
	private Collisions collisionCheck;
	private IController controller;

	private ArrayList<Bullet> bulletsInRoom;
	private RoomController[] stanzeController;
	
	public PlayStateController(IController c) {
		controller = c;
		collisionCheck = new Collisions(c); 
		Rectangle r = new Rectangle(12, 9, 0, 0);
		playerController = new PlayerController(r, this);
		bulletsInRoom = new ArrayList<>();
		initRooms();
	}

	public void initRooms() {
		stanzeController = new RoomController[Stanze.numStanze];
		stanzeController[Stanze.BIBLIOTECA.indiceMappa] = new RoomController(this);
		stanzeController[Stanze.DORMITORIO.indiceMappa] = new RoomController(this);		
		stanzeController[Stanze.AULA_STUDIO.indiceMappa] = new RoomController(this);
		stanzeController[Stanze.TENDA.indiceMappa] = new RoomController(this);
		stanzeController[Stanze.LABORATORIO.indiceMappa] = new RoomController(this);
		stanzeController[Stanze.STUDIO_PROF.indiceMappa] = new RoomController(this);
		
	}
	
	public void update() {
		//aggiorna il personaggio
		playerController.update();		
		
		//aggiorna i proiettili
		updateBullets();
		
		//aggiorna gli altri elementi del gioco in base alla stanza dove si trova il giovatore
		//Per rendere la cosa più leggera, possiamo aggiornare solo quelli all'interno dell'area visibile
		switch(Stanze.stanzaAttuale) {
		case DORMITORIO:
			stanzeController[Stanze.DORMITORIO.indiceMappa].update();		
			break;
		case BIBLIOTECA:
			stanzeController[Stanze.BIBLIOTECA.indiceMappa].update();		
			break;
		case AULA_STUDIO:
			stanzeController[Stanze.AULA_STUDIO.indiceMappa].update();	
			break;
		case TENDA:
			stanzeController[Stanze.TENDA.indiceMappa].update();	
			break;
		case LABORATORIO:
			stanzeController[Stanze.LABORATORIO.indiceMappa].update();	
			break;
		case STUDIO_PROF: 
			stanzeController[Stanze.STUDIO_PROF.indiceMappa].update();
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
		controller.getView().getPlay().getAppunti().clear();
	}
	
	public void handleKeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) 
			controller.setGameState(Gamestate.PAUSE);
		
		else if (e.getKeyCode() == KeyEvent.VK_ENTER && !playerController.isParring())
			playerController.setAttacking(true);
			
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
			playerController.setParry(true);
			
		else if(e.getKeyCode() == KeyEvent.VK_P && !playerController.isParring()) {
			if(playerController.getNotes() > 0) 
				playerController.setThrowing(true);
			
			else {
				controller.getView().getPlay().getUI().setScritta("appunti finiti");
				controller.getView().getPlay().getUI().setShowMessage(true);
			}
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_E && !playerController.isParring())
			playerController.setInteracting(true);
			
		else
			playerController.choiceDirection(e);
	}
	
	public void handleKeyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER && !playerController.isParring())
			playerController.setAttacking(false);
		
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			playerController.setParry(false);
			controller.getView().getPlay().getPlayer().resetParry();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_P && !playerController.isParring()) {
			if(playerController.getNotes() > 0) {
				playerController.decreaseBulletsNumber();
				playerController.setThrowing(false);
				addBullets(playerController);
				controller.getView().getPlay().addProjectile();
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_E && !playerController.isParring())
			playerController.setInteracting(false);
		
		else
			playerController.resetDirection(e);
	}

	public void handleMousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e) && !playerController.isParring())
			playerController.setAttacking(true);
		
		else if(SwingUtilities.isRightMouseButton(e))
			playerController.setParry(true);
		
		else if(SwingUtilities.isMiddleMouseButton(e) && !playerController.isParring()) {
			if(playerController.getNotes() > 0) 
				playerController.setThrowing(true);	
			
			else {
				controller.getView().getPlay().getUI().setScritta("appunti finiti");
				controller.getView().getPlay().getUI().setShowMessage(true);
			}
		}
		
	}

	public void handleMouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e) && !playerController.isParring())
			playerController.setAttacking(false);
		
		else if(SwingUtilities.isRightMouseButton(e))
			playerController.setParry(false);
		
		else if(SwingUtilities.isMiddleMouseButton(e) && !playerController.isParring()) {			
			if(playerController.getNotes() > 0) {
				playerController.decreaseBulletsNumber();
				playerController.setThrowing(false);
				addBullets(playerController);
				controller.getView().getPlay().addProjectile();
			}
		}
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
		bulletsInRoom.add(new Bullet(this, bulletsInRoom.size(), owner));
	}
	
	public void removeBullets(int index) {
		//siccome tutti quelli a destra di index si spostano a sinistra di uno, l'indice deve 
		//essere aggiornato
		for(int i = index; i < bulletsInRoom.size(); i++)
			bulletsInRoom.get(i).decreaseIndexInList();
		try {
			bulletsInRoom.remove(index);
		}
		catch(IndexOutOfBoundsException iobe) {
			bulletsInRoom.clear();
			controller.getView().getPlay().getAppunti().clear();
		//	System.out.println("problemi appunti controller");
		//	iobe.printStackTrace();
		}
	}
	
	public ArrayList<Bullet> getBulletsInRoom(){
		return bulletsInRoom;
	}
	
	public RoomController getRoom(int index) {
		return stanzeController[index];
	}
	
}
