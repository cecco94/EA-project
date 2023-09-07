package view.playState.entityView.npcview;

import java.awt.Graphics2D;

import view.IView;
import view.playState.entityView.EntityView;

public class NPCview extends EntityView {

	private String[] dialoghi;
	private IView view;
	
	public NPCview(IView v, String[] dial) {
		dialoghi = dial;
		view = v;
	}
	
	@Override
	public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
		// TODO Auto-generated method stub
		
	}

}
