package view.playState;

import java.util.ArrayList;

import view.playState.drawOrder.SortableElement;
import view.playState.entityView.EntityView;
import view.playState.entityView.RobotView;
import view.playState.entityView.npcview.CatView;
import view.playState.entityView.npcview.DocView;
import view.playState.entityView.npcview.ErmenegildoView;
import view.playState.entityView.npcview.NPCView;
import view.playState.entityView.npcview.NerdView;
import view.playState.entityView.npcview.PupaView;

public class RoomView {

	private ArrayList<NPCView> NPCviewList;
	private ArrayList<EntityView> enemyView;
	private PlayStateView play;
	
	public RoomView(PlayStateView p, int index) {
		play = p;
		NPCviewList = new ArrayList<>();
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
		
		int temp2 = play.getView().getController().getPlay().getRoom(roomIndex).getEnemy().size();
		for(int index = 0; index < temp2; index++) {
			addEnemy(play.getView().getController().getPlay().getRoom(roomIndex).getEnemy().get(index).getType(), index);
		}
	}

	public void addEnemy(String type, int index) {
		enemyView.add(new RobotView(play.getView(), index));
	}
	
	
	public void addNPC(String type, int index) {
		if(type.compareTo("gatto") == 0) {
			NPCviewList.add(new CatView(play.getView(), index));
		}
		
		if(type.compareTo("vecchio") == 0) 
			NPCviewList.add(new ErmenegildoView(play.getView(), index));
		
		else if(type.compareTo("prof") == 0)
			NPCviewList.add(new DocView(play.getView(), index));
		
		else if(type.compareTo("nerd") == 0)
			NPCviewList.add(new NerdView(play.getView(), index));
		

		else if(type.compareTo("pupa") == 0) 
			NPCviewList.add(new PupaView(play.getView(), index));
		
	}

	//mette nella lista da ordinare tutti e soli gli elementi vicini al player
	public void addEntitiesInFrameForSort(int posizPlayerX, int posizPlayerY, ArrayList<SortableElement> drawOrder) {
		//ogni npc prende la posizione del personaggio e vede se è dentro la finestra di gioco
		for(int i = 0; i < NPCviewList.size(); i++)
			if(NPCviewList.get(i).isInGameFrame(posizPlayerX, posizPlayerY, "npc"))
				drawOrder.add(NPCviewList.get(i));
		
		for(int i = 0; i < enemyView.size(); i++)
			if(enemyView.get(i).isInGameFrame(posizPlayerX, posizPlayerY, "enemy"))
				drawOrder.add(enemyView.get(i));
	}

	public NPCView getNPC(int index) {	
		return NPCviewList.get(index);
	}
	
	public EntityView getEnemy(int index) {
		return enemyView.get(index);
	}
	
	
}





