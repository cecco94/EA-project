package view.playState;

import java.util.ArrayList;

import view.playState.drawOrder.SortableElement;
import view.playState.entityView.BossView;
import view.playState.entityView.EntityView;
import view.playState.entityView.NullaFacenteView;
import view.playState.entityView.RobotView;
import view.playState.entityView.npcview.CatView;
import view.playState.entityView.npcview.DocView;
import view.playState.entityView.npcview.ErmenegildoView;
import view.playState.entityView.npcview.NPCView;
import view.playState.entityView.npcview.NerdView;
import view.playState.entityView.npcview.PupaView;

public class RoomView {

	private ArrayList<NPCView> NPCviewList;
	private ArrayList<EntityView> enemyViewList;
	private PlayStateView play;
	
	public RoomView(PlayStateView p, int index) {
		play = p;
		NPCviewList = new ArrayList<>();
		enemyViewList = new ArrayList<>();
		
		//ciascuna roomview deve leggere nella roomcontroller corrispondente
		//quali NPC/nemici ci sono e li aggiunge alla propria lista
		getDataFromController(index);
	}
	
	private void getDataFromController(int roomIndex) {
		
		int temp = play.getView().getController().getNumberOfNpcInRoom(roomIndex);
		for(int index = 0; index < temp; index++) {
			addNPC(play.getView().getController().getNpcType(roomIndex, index), index);
		}
		
		int temp2 = play.getView().getController().getNumberOfEnemiesInRoom(roomIndex);
		for(int index = 0; index < temp2; index++) {
			addEnemy(play.getView().getController().getEnemyType(roomIndex, index), index);
		}
	}

	public void addEnemy(String type, int index) {		
		if(type.compareTo("robot") == 0) {
			enemyViewList.add(new RobotView(play.getView(), index));
		}
		
		else if(type.compareTo("nullafacente") == 0) 
			enemyViewList.add(new NullaFacenteView(play.getView(), index));
		
		else if(type.compareTo("boss") == 0) 
			enemyViewList.add(new BossView(play.getView(), index));
	}
	
	
	public void addNPC(String type, int index) {
		if(type.compareTo("gatto") == 0) 
			NPCviewList.add(new CatView(play.getView(), index));
		
		else if(type.compareTo("vecchio") == 0) 
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
		
		for(int i = 0; i < enemyViewList.size(); i++)
			if(enemyViewList.get(i).isInGameFrame(posizPlayerX, posizPlayerY, "enemy"))
				drawOrder.add(enemyViewList.get(i));
	}

	public NPCView getNPC(int index) {	
		return NPCviewList.get(index);
	}
	
	public EntityView getEnemy(int index) throws IndexOutOfBoundsException{
		return enemyViewList.get(index);
	}

	public void removeEnemy(int index) {
		
		for(int i = index; i < enemyViewList.size(); i++)
			enemyViewList.get(i).decreaseIndexInList();
		
		try {
			enemyViewList.remove(index);
		}
		
		catch(IndexOutOfBoundsException iobe) {
			iobe.printStackTrace();
		}		
	}
	
	
}





