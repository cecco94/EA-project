package view.inputs;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.main.Gamestate;
import view.IView;
import view.playState.entityView.EntityView;


public class KeyboardInputs implements KeyListener {

	private IView view;
	
	
	public KeyboardInputs(IView v) {
		this.view = v;
	}

	//lui sente solo che un tasto è stato rilasciato, poi delega la gestione del fatto ai vari gamestate
	@Override
	public void keyReleased(KeyEvent e) {
		switch (Gamestate.state) {
			case START_TITLE:
				if(e.getKeyCode() == KeyEvent.VK_ENTER) 
					view.getStart().skipTitle();
				break;
			case MAIN_MENU:
				view.getMenu().keyReleased(e.getKeyCode());
				break;
			case SELECT_AVATAR:
				view.getAvatarMenu().keyReleased(e.getKeyCode());
				break;
			case PLAYING:
				handleKeyReleasedPlayState(e);
				break;
			case OPTIONS:
				view.getOptions().keyReleased(e.getKeyCode());
				break;
			default:
				break;
		}					
	}
	
	private void handleKeyReleasedPlayState(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				view.getController().getPlay().stopPlayerAttacking();
				break;
			case KeyEvent.VK_SPACE:
				view.getController().getPlay().stopPlayerParring();
				break;
			case KeyEvent.VK_P:
				view.getController().getPlay().stopPlayerThrowing();
				break;
			case KeyEvent.VK_E:
				view.getController().getPlay().stopPlayerInteracting();
				break;	
			default:
				view.getController().getPlay().getPlayer().resetDirection(getActionAssociatedToKey(e));
				break;
		}
	}
	
	
	public int getActionAssociatedToKey(KeyEvent e) {
		
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				return EntityView.ATTACK;
			
			else if(e.getKeyCode() == KeyEvent.VK_SPACE)
				return EntityView.PARRY;
			
			else if(e.getKeyCode() == KeyEvent.VK_P)
				return EntityView.THROW;
			
			else if(e.getKeyCode() == KeyEvent.VK_A)
				return EntityView.LEFT;
			
			else if(e.getKeyCode() == KeyEvent.VK_D)
				return EntityView.RIGHT;	
			
			else if(e.getKeyCode() == KeyEvent.VK_W)
				return EntityView.UP;	
			
			else if(e.getKeyCode() == KeyEvent.VK_S)
				return EntityView.DOWN;
			
			else return -1;
			
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (Gamestate.state) {
			case PLAYING:
				handleKeypressedDuringPlayState(e);
				break;
			case PAUSE:
				view.getPause().keyPressed(e.getKeyCode());
				break;
			default:
				break;
		}
	}

	private void handleKeypressedDuringPlayState(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				view.getController().getPlay().startPlayerAttacking();
				break;
			case KeyEvent.VK_SPACE:
				view.getController().getPlay().startPlayerParring();
				break;
			case KeyEvent.VK_ESCAPE:
				view.getController().getPlay().goToPauseState();
				break;
			case KeyEvent.VK_P:
				view.getController().getPlay().startPlayerThrowing();
				break;
			case KeyEvent.VK_E:
				view.getController().getPlay().startPlayerInteract();
				break;	
			default:
				view.getController().getPlay().getPlayer().choiceDirection(getActionAssociatedToKey(e));
				break;
		}
		
	}
}




