package model.mappa;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.IModel;
import model.mappa.events.Event;
import model.mappa.events.Light;

public class RoomModel {
	
	private ArrayList<Passaggio> passaggi;
	private ArrayList<Event> eventi;
	private IModel model;

	//ogni entità deve vedere se spostandosi si schianta contro un'altra entità. Per farlo, invece di controllare tutta la 
	//lista di entità ogni volta, controlla in questa matrice di interi.
	//In questa matrice è inserito un numero che è il numero di entità sopra al tile.
	//L'entità controlla il tile dove si trova, se c'è un numero > 1 (uno no perchè è l'entità stessa), controlla nella lista 
	//se va a intersecarsi con un'altra entità.
	//Se deve controllare anche altri tile (sta a metà tra due colonne/righe) allora il numero deve essere > 0
	
	//private int[][] tilesOccupati;
		
		
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

						model.getController().getPlay().getRoom(index).addNPC(type, xPos, yPos);
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
						
						boolean open = Boolean.parseBoolean(datiNellaRiga[8]);
						String s = "";
						try {
							s = datiNellaRiga[9];
						}
						catch(ArrayIndexOutOfBoundsException e) {
							//se non c'è niente da scrivere l'array è più piccolo
						}
						
						passaggi.add(new Passaggio(open, s,prewX, prewY, width, height, newX, newY, Stanze.getStanzaAssociataAlNumero(newRoom)));
					}	
					
					else if(rigaLetta.contains("+")) {		//il + indica eventi
						int xPos = Integer.parseInt(datiNellaRiga[1]);	
						int yPos = Integer.parseInt(datiNellaRiga[2]);
						int width = Integer.parseInt(datiNellaRiga[3]);
						int height = Integer.parseInt(datiNellaRiga[4]);
						
						Rectangle r = new Rectangle(xPos, yPos, width, height);						
						eventi.add(new Light(r, model));
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
	
//	public void addNewPassaggio(boolean open, String s, int x, int y, int width, int height, int newX, int newY, Stanze newRoom) {
//		passaggi.add(new Passaggio(open, s, x, y, width, height, newX, newY, newRoom));
//	}

	public int checkPassInRoom(Rectangle hitbox) {
		int index = -1;
		for(int i = 0; i < passaggi.size(); i++) {
			if(passaggi.get(i).checkPlayer(hitbox))
				index = i;
		}
		return index;
	}
	
	public int checkEventInRoom(Rectangle hitbox) {
		int index = -1;
		for(int i = 0; i < eventi.size(); i++) {
			if(eventi.get(i).checkPlayer(hitbox))
				index = i;
		}
		return index;
	}
	
	public ArrayList<Passaggio> getPassaggi() {
		return passaggi;
	}
	
	public ArrayList<Event> getEventi(){
		return eventi;
	}
	
}

//per capire quanto è grande la stanza, prendiamo il primo strato della mappa corrispondente
//e ne prendiamo le dimensioni
//int matricePrimoStrato[][] = model.getMappa().getStrato(index, 0);
//int altezza = matricePrimoStrato.length;
//int larghezza = matricePrimoStrato[0].length;
//	tilesOccupati = new int[altezza][larghezza];
//	printTilesOccupati();
//}

//@SuppressWarnings("unused")
//private void printTilesOccupati() {
//for(int righe = 0; righe < tilesOccupati.length; righe++) {
//	for(int colonne = 0; colonne < tilesOccupati[0].length; colonne++) {
//		System.out.print(tilesOccupati[righe][colonne]);
//	}
//	System.out.println();
//}
//System.out.println();	
//}
//
//public void aggiungiEntitaAlTile(int riga, int colonna) {
//tilesOccupati[riga][colonna]++;
////System.out.println("prima ");
////printTilesOccupati();
//}
//
//public void togliEntitaAlTile(int riga, int colonna) {
//if(tilesOccupati[riga][colonna] > 0) {
//	tilesOccupati[riga][colonna]--;
////	System.out.println("dopo ");
////	printTilesOccupati();
//}
//}

//	public int getNumEntityIntile(int riga, int colonna){
//		return tilesOccupati[riga][colonna];
//	}
//	

