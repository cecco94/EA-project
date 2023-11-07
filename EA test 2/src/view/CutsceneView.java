package view;

import java.awt.Graphics2D;

import view.main.GamePanel;

public class CutsceneView {

	private IView view;
	private static final int PHASE_0 = 0, PHASE_1 = 1;
	private int currentPhase = PHASE_0;
	
	//la camera salirà dal quadratino 24,22 verso il prof, indipendentemente da dove si trova il player
	private int cameraX = 24*GamePanel.TILES_SIZE, cameraY = 22*GamePanel.TILES_SIZE;
	
	public CutsceneView(IView v) {
		view = v;
	}
	
	public void drawCutscene(Graphics2D g2){
		switch(currentPhase) {
		//muovi la telecamera verso l'alto
		case PHASE_0:
			cameraY--;
			view.getPlay().drawCutSceneBackground(g2, cameraX, cameraY);
			
			if(cameraY <= 15*GamePanel.TILES_SIZE)
				currentPhase = PHASE_1;
			break;
			
		//la telecamera si ferma sul boss, che parla
		case PHASE_1:
			view.getPlay().drawCutSceneBackground(g2, cameraX, cameraY);
			view.getPlay().getUI().drawDialogue(g2);
			break;
		}
	}
}
