package view.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import controller.Gamestate;
import view.IView;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private IView view;
	
	public MouseInputs(IView v) {
		this.view = v;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			
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
		default:
			break;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			//gamePanel.getGame().getPlaying().mouseClicked(e);
			break;
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
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}