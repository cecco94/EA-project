package view.playState;

import java.util.ArrayList;

import controller.playState.entityController.EntityController;
import view.playState.drawOrder.SortableElement;
import view.playState.entityView.CatView;
import view.playState.entityView.EntityView;

public class RoomView {

	private ArrayList<EntityView> NPCview;
	private ArrayList<EntityView> enemyView;
	private PlayStateView play;
	
	public RoomView(PlayStateView p, int index) {
		play = p;
		NPCview = new ArrayList<>();
		enemyView = new ArrayList<>();
		
		//ciascuna roomview deve leggere nella roomcontroller corrispondente
		//quali NPC/nemici ci sono e li aggiunge alla propria lista
		getDataFromController(index);
	}
	
	private void getDataFromController(int index) {
		
		ArrayList<EntityController> temp = play.getView().getController().getPlay().getRoom(index).getNPC();
		for(int i = 0; i < temp.size(); i++) {
			addNPC(temp.get(i).getType(), i);
		}
	}

	public void addEnemy(EntityView e) {
		enemyView.add(e);
	}
	
	public void addNPC(int type, int index) {
		NPCview.add(new CatView(play.getView(), index));
	}

	//mette nella lista da ordinare tutti e soli gli elementi vicini al player
	public void addEntitiesInFrameForSort(int posizPlayerX, int posizPlayerY, ArrayList<SortableElement> drawOrder) {
		//ogni npc prende la posizione del personaggio e vede se è dentro la finestra di gioco
		for(int i = 0; i < NPCview.size(); i++)
			if(NPCview.get(i).isInGameFrame(posizPlayerX, posizPlayerY))
				drawOrder.add(NPCview.get(i));
	}
	
	
	
	
}





