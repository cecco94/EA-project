package controller.playState;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

import controller.Gamestate;
import controller.IController;
import model.mappa.Stanze;

//controlla ciò che accade nel gioco durante il play state
public class PlayStateController {
	
	private PlayerController playerController;
	private Collisions collisionCheck;
	private IController controller;
	
	public PlayStateController(IController c) {
		controller = c;
		collisionCheck = new Collisions(c); 
		playerController = new PlayerController(collisionCheck, controller);
	}

	public PlayerController getPlayer() {
		return playerController;
	}
	
	public void update() {
		//aggiorna il personaggio
		playerController.updatePos();
		playerController.isAbovePassaggio();
		
		//aggiorna gli altri elementi del gioco in base alla stanza dove si trova il giovatore
		//possiamo creare un array di room. ogni room contiene una lista di esseri ed oggetti
		//Aggiornando la stanza, aggiorniamo tutti gli elementi al suo interno
		//Per rendere la cosa più leggera, possiamo aggiornare solo quelli all'interno dell'area visibile
		switch(Stanze.stanzaAttuale) {
		case DORMITORIO:
			//model, dammi la lista deglle entità nel dormitorio, ciascuna la aggiorniamo dandole il player
			break;
		case BIBLIOTECA:
			break;
		default:
			break;
		}
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
	}
	
	public void handleKeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			controller.getView().getTransition().setNext(Gamestate.MAIN_MENU);
			controller.getView().getTransition().setPrev(Gamestate.PLAYING);
			controller.setGameState(Gamestate.TRANSITION);
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
			playerController.setAttacking(true);
			
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
			playerController.setParry(true);
			
		else if(e.getKeyCode() == KeyEvent.VK_P)
			playerController.setThrowing(true);
			
		else
			playerController.choiceDirection(e);
	}
	
	public void handleKeyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			playerController.setAttacking(false);
		
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			playerController.setParry(false);
			controller.getView().getPlay().getPlayer().resetParry();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_P)
			playerController.setThrowing(false);
		
		else
			playerController.resetDirection(e);
	}

	public void handleMousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e))
			playerController.setAttacking(true);
		else if(SwingUtilities.isRightMouseButton(e))
			playerController.setParry(true);
	}

	public void handleMouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e))
			playerController.setAttacking(false);
		else if(SwingUtilities.isRightMouseButton(e))
			playerController.setParry(false);
	}
	
}
