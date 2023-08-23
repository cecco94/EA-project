package model.mappa;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.IModel;

public class RoomModel {
	
	private ArrayList<Passaggio> passaggi;
	private IModel model;

	public RoomModel(String percorsoFile, IModel m, int index) {
		model = m;
		passaggi = new ArrayList<>();
		aggiungiElementiAllaStanza(percorsoFile, index);
	}

	// per non dividere le informazioni, tutti i dati di una stanza sono messi in un solo file
	// quindi nel file mettiamo anche informazoni per il controller.
	// leggendo il file una volta sola, qui finiscono anche cose relative al controller 
	private void aggiungiElementiAllaStanza(String percorsoFile, int index) {
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
						
						Rectangle r = new Rectangle(xPos, yPos, width, height);
						model.getController().getPlay().getRoom(index).addEnemy(type, r);
					}
					
					else if(rigaLetta.contains("-")) {		//il trattino indica npc
						int type = Integer.parseInt(datiNellaRiga[1]);	
						int xPos = Integer.parseInt(datiNellaRiga[2]);
						int yPos = Integer.parseInt(datiNellaRiga[3]);
						int width = Integer.parseInt(datiNellaRiga[4]);
						int height = Integer.parseInt(datiNellaRiga[5]);
						
						Rectangle r = new Rectangle(xPos, yPos, width, height);
						model.getController().getPlay().getRoom(index).addNPC(type, r);
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
		for(Passaggio p : passaggi)
			System.out.println(p.toString());
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
	
	public ArrayList<Passaggio> getPassaggi() {
		return passaggi;
	}
	
	
}
