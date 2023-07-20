package view.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import controller.Gamestate;
import view.main.IView;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private IView view;
	
	public MouseInputs(IView v) {
		this.view = v;
	}

	//lui sente solo che il mouse ha fatto qualcosa, poi delega la gestione dell'evento ai vari gamestate
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			
			break;
			
		case OPTIONS:
			view.getOptions().mouseDragged(e);
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (Gamestate.state) {
		case MAIN_MENU:
			view.getMenu().mouseMoved(e);
			break;
		case PLAYING:
			//gamePanel.getGame().getPlaying().mouseMoved(e);
			break;
		case SELECT_AVATAR:
			view.getAvatarMenu().mouseMoved(e);
			break;
		case OPTIONS:
			view.getOptions().mouseMoved(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (Gamestate.state) {
		case START_TITLE:
			view.getStart().skipTitle();
			break;
		default:
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (Gamestate.state) {
		case MAIN_MENU:
			view.getMenu().mousePressed(e);
			break;
		case PLAYING:
			//gamePanel.getGame().getPlaying().mousePressed(e);
			break;
		case SELECT_AVATAR:
			view.getAvatarMenu().mousePressed(e);
			break;
		case OPTIONS:
			view.getOptions().mousePressed(e);
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
		case MAIN_MENU:
			view.getMenu().mouseReleased(e);
			break;
		case PLAYING:
			//gamePanel.getGame().getPlaying().mouseReleased(e);
			break;
		case SELECT_AVATAR:
			view.getAvatarMenu().mouseReleased(e);
			break;
		case OPTIONS:
			view.getOptions().mouseReleased(e);
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}