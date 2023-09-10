package view.playState;

import java.util.ArrayList;

import view.playState.drawOrder.SortableElement;
import view.playState.entityView.CatView;
import view.playState.entityView.EntityView;
import view.playState.entityView.npcview.Ermenegildo;

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
	
	private void getDataFromController(int roomIndex) {
		
		int temp = play.getView().getController().getPlay().getRoom(roomIndex).getNPC().size();
		for(int index = 0; index < temp; index++) {
			addNPC(play.getView().getController().getPlay().getRoom(roomIndex).getNPC().get(index).getType(), index);
		}
	}

	public void addEnemy(EntityView e) {
		enemyView.add(e);
	}
	
	public void addNPC(String type, int index) {
		if(type.compareTo("gatto") == 0)
			NPCview.add(new CatView(play.getView(), index));
		
		else if(type.compareTo("vecchio") == 0) 
			NPCview.add(new Ermenegildo(play.getView(), index));
	}

	//mette nella lista da ordinare tutti e soli gli elementi vicini al player
	public void addEntitiesInFrameForSort(int posizPlayerX, int posizPlayerY, ArrayList<SortableElement> drawOrder) {
		//ogni npc prende la posizione del personaggio e vede se è dentro la finestra di gioco
		for(int i = 0; i < NPCview.size(); i++)
			if(NPCview.get(i).isInGameFrame(posizPlayerX, posizPlayerY))
				drawOrder.add(NPCview.get(i));
	}

	public EntityView getNPC(int index) {	
		return NPCview.get(index);
	}
	
	
	
	
}





