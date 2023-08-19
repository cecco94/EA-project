package view.inputs;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.Gamestate;
import view.IView;


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
			view.getController().getPlay().handleKeyReleased(e);
			break;
		case OPTIONS:
			view.getOptions().keyReleased(e.getKeyCode());
			break;
		default:
			break;
		}					
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			view.getController().getPlay().handleKeyPressed(e);
			break;
			
		default:
			break;
		}
	}
}