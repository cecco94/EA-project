package model.mappa;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import controller.playState.Hitbox;
import model.IModel;
import model.mappa.events.CFU;
import model.mappa.events.Caffe;
import model.mappa.events.Event;
import model.mappa.events.Light;
import model.mappa.events.Notes;

public class RoomModel {
	
	private ArrayList<Passage> passaggi;
	private ArrayList<Event> eventi;
	private IModel model;
		
		
	public RoomModel(String percorsoFile, IModel m, int index) {
		model = m;
		passaggi = new ArrayList<>();
		eventi = new ArrayList<>();
		aggiungiPassaggiCreatureEventi(percorsoFile, index);
	}
	
	// per non dividere le informazioni, tutti i dati di una stanza sono messi in un solo file
	// quindi nel file mettiamo anche informazoni per il controller.
	// leggendo il file una volta sola, qui finiscono anche cose relative al controller 
	private void aggiungiPassaggiCreatureEventi(String percorsoFile, int index) {
		try {
			InputStream	is = getClass().getResourceAsStream(percorsoFile);			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String rigaLetta = null;
			String[] datiNellaRiga = new String[10];
			
			while((rigaLetta = br.readLine()) != null) {
				
				if(!rigaLetta.isEmpty() && rigaLetta.contains(", ")) {		
					datiNellaRiga = rigaLetta.trim().split(", ");
				
					if(rigaLetta.contains("/")) {	// lo slash indica che è un nemico								
						String type = datiNellaRiga[1];	//parte da uno perchè il primo campo serve al lettore
						int xPos = Integer.parseInt(datiNellaRiga[2]);
						int yPos = Integer.parseInt(datiNellaRiga[3]);
						
						model.getController().addEnemy(index, xPos, yPos, type);
					}
					
					else if(rigaLetta.contains("-")) {		//il trattino indica npc
						String type = datiNellaRiga[1];	
						
						int xPos = Integer.parseInt(datiNellaRiga[2]);
						int yPos = Integer.parseInt(datiNellaRiga[3]);

						model.getController().addNPC(index, xPos, yPos, type);
						//sarebbe bello aggiungere qui anche le roomview, ma la view viene inizializzata dopo
					}
					
					else if(rigaLetta.contains(";")) {		//il ; indica i passaggi
						int prewX = Integer.parseInt(datiNellaRiga[1]);	
						int prewY = Integer.parseInt(datiNellaRiga[2]);
						
						int width = Integer.parseInt(datiNellaRiga[3]);
						int height = Integer.parseInt(datiNellaRiga[4]);
						
						int newX = Integer.parseInt(datiNellaRiga[5]);
						int newY = Integer.parseInt(datiNellaRiga[6]);
						
						int newRoom = Integer.parseInt(datiNellaRiga[7]);
						
						int open = Integer.parseInt(datiNellaRiga[8]);
						String s = "";
						try {
							s = datiNellaRiga[9];
						}
						catch(ArrayIndexOutOfBoundsException e) {
							//se non c'è niente da scrivere l'array è più piccolo
						}
						
						passaggi.add(new Passage(model, open, s,prewX, prewY, width, height, newX, newY, Rooms.getRoomLinkedToNumber(newRoom)));
					}	
					
					else if(rigaLetta.contains("+")) {		//il + indica eventi
						int xPos = Integer.parseInt(datiNellaRiga[1]);	
						int yPos = Integer.parseInt(datiNellaRiga[2]);
						int width = Integer.parseInt(datiNellaRiga[3]);
						int height = Integer.parseInt(datiNellaRiga[4]);
						String s = datiNellaRiga[5];
						
						Hitbox r = new Hitbox(xPos, yPos, width, height);	
						addEvent(r, s);
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
	
	private void addEvent(Hitbox r, String s) {
		
		if(s.compareTo("luce") == 0)
			eventi.add(new Light(r, model));
		
		else if(s.compareTo("caffe") == 0)
			eventi.add(new Caffe(r, model));
		
		else if(s.compareTo("appunti") == 0)
			eventi.add(new Notes(r, model));
		
		else if(s.compareTo("cfu") == 0)
			eventi.add(new CFU(r, model));
	}


	public void printData() {		
		for(Passage p : passaggi)
			System.out.println(p.toString());
	}

	public int checkPassInRoom(Hitbox hitbox) {
		int index = -1;
		for(int i = 0; i < passaggi.size(); i++) {
			if(passaggi.get(i).checkPlayer(hitbox)) //se giocatore con hitbox sta nel passaggio 
				index = i;
		}
		return index;
	}
	
	public int checkEventInRoom(Hitbox hitbox) {
		int index = -1;
		for(int i = 0; i < eventi.size(); i++) {
			if(eventi.get(i).checkPlayer(hitbox))
				index = i;
		}
		return index;
	}
	
	public ArrayList<Passage> getPassaggi() {
		return passaggi;
	}
	
	public ArrayList<Event> getEventi(){
		return eventi;
	}
	
}



