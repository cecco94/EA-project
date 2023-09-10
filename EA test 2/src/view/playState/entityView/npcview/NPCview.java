package view.playState.entityView.npcview;

import java.awt.Graphics2D;

import view.IView;
import view.playState.entityView.EntityView;

public abstract class NPCview extends EntityView {

	protected String[] dialoghi;
	protected IView view;
	
	public NPCview(IView v) {
		view = v;
	}
	
	@Override
	public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
		// TODO Auto-generated method stub
		
	}

}
