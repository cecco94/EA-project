package model.mappa;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import controller.playState.entityController.EnemyController;
import controller.playState.entityController.EntityController;
import controller.playState.entityController.NPCcontroller;

public class RoomModel {

	private ArrayList<EntityController> nemici;
	private ArrayList<EntityController> NPC;
	public ArrayList<Passaggio> passaggi;
	
	public RoomModel(String percorsoFile) {
		
		passaggi = new ArrayList<>();
		nemici = new ArrayList<>();
		NPC = new ArrayList<>();
		aggiungiElementiAllaStanza(percorsoFile);
	}

	public void addNewPassaggio(int x, int y, int width, int height, int newX, int newY, Stanze newRoom) {
		passaggi.add(new Passaggio(x, y, width, height, newX, newY, newRoom));
	}

	public int checkPassInRoom(Rectangle hitbox) {
		int index = -1;
		
		for(int i = 0; i < passaggi.size(); i++) {
			if(passaggi.get(i).checkPlayer(hitbox))
				index = i;
		}
		return index;
	}
	
	public void addEnemy(EntityController e) {
		nemici.add(e);
	}
	
	public void removeEnemy(int index) {
		nemici.remove(index);
	}
	
	public void addNPC(EntityController n) {
		NPC.add(n);
	}
	
	public void removeNPC(int index) {
		NPC.remove(index);
	}
	
	public ArrayList<EntityController> getEnemy(){
		return nemici;
	}
	
	public ArrayList<EntityController> getNPC(){
		return NPC;
	}
	
	private void aggiungiElementiAllaStanza(String percorsoFile) {
		try {
			InputStream	is = getClass().getResourceAsStream(percorsoFile);			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String rigaLetta = null;
			String[] datiNellaRiga = new String[8];
			
			while((rigaLetta = br.readLine()) != null) {
				
				if(!rigaLetta.isEmpty() && rigaLetta.contains(", ")) {		
					datiNellaRiga = rigaLetta.trim().split(", ");
				
					if(rigaLetta.contains("/")) {	// lo slash indica che è un nemico								
						int type = Integer.parseInt(datiNellaRiga[1]);	//parte da uno perchè il primo campo serve al lettore
						int xPos = Integer.parseInt(datiNellaRiga[2]);
						int yPos = Integer.parseInt(datiNellaRiga[3]);
						int width = Integer.parseInt(datiNellaRiga[4]);
						int height = Integer.parseInt(datiNellaRiga[5]);
					//	nemici.add(new EnemyController(type, xPos, yPos, width, height));
					}
					else if(rigaLetta.contains("-")) {		//il trattino indica npc
						int type = Integer.parseInt(datiNellaRiga[1]);	
						int xPos = Integer.parseInt(datiNellaRiga[2]);
						int yPos = Integer.parseInt(datiNellaRiga[3]);
						int width = Integer.parseInt(datiNellaRiga[4]);
						int height = Integer.parseInt(datiNellaRiga[5]);
					//	NPC.add(new NPCcontroller(type, xPos, yPos, width, height));
					}
					else if(rigaLetta.contains(";")) {		//il ; indica i passaggi
						int prewX = Integer.parseInt(datiNellaRiga[1]);	
						int prewY = Integer.parseInt(datiNellaRiga[2]);
						int width = Integer.parseInt(datiNellaRiga[3]);
						int height = Integer.parseInt(datiNellaRiga[4]);
						int newX = Integer.parseInt(datiNellaRiga[5]);
						int newY = Integer.parseInt(datiNellaRiga[6]);
						int newRoom = Integer.parseInt(datiNellaRiga[7]);
						passaggi.add(new Passaggio(prewX, prewY, width, height, newX, newY, Stanze.getStanzaAssociataAlNumero(newRoom)));
					}	
					
				}
				
			}	
			br.close();	
		//	printData();
		}
		catch(Exception e) {
			e.printStackTrace();			
		}										
	}
	
	public void printData() {
		for(EntityController n :nemici) 
			System.out.println(n.toString());
			
		for(EntityController a : NPC) 
			System.out.println(a.toString());	
		
		for(Passaggio p : passaggi)
			System.out.println(p.toString());
	}
	
}
