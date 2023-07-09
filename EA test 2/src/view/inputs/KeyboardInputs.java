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

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			//gamePanel.getGame().getPlaying().keyReleased(e);
			break;
		default:
			break;
		}					
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (Gamestate.state) {
		case START_TITLE:
			if(e.getKeyCode() == KeyEvent.VK_ENTER) 
				view.getStart().skipTitle();
			break;
		default:
			break;
		}			
	}
}