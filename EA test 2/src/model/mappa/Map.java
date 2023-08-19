package model.mappa;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Map {

	//primo campo = stanza, secondo campo = strato, terzo e quarto = posizione x,y
	private int[][][][] mappa;	
	
	//primo campo = stanza, secondo campo = larghezza/altezza
//	private int[][] dimensioniStanze;
	
	public static final int NUM_STANZE = 3, NUM_STRATI = 4;
	public static final int PRIMO_STRATO = 0, SECONDO_STRATO = 1, TERZO_STRATO = 2, QUARTO_STRATO = 3;
	public static final int BIBLIOTECA = 0,  DORMITORIO = 1, AULA_STUDIO = 2;
	private int MappaAttuale = AULA_STUDIO;
	
	private String[] percorsiStanze = {"/mappe/mappaBibliotecaQuattroStrati.txt", "/mappe/dormitorio.txt", "/mappe/aulaStudio.txt"};
	
	public Map() {	
		mappa = new int[NUM_STANZE][NUM_STRATI][][];
	//	dimensioniStanze = new int[NUM_STANZE][2];
		
		for(int stanzaDaCaricare = 0; stanzaDaCaricare < NUM_STANZE; stanzaDaCaricare++)
			mappa[stanzaDaCaricare] = loadStanza(percorsiStanze[stanzaDaCaricare], stanzaDaCaricare);		
	}

	private int[][][] loadStanza(String percorsiStanze, int stanzaDaCaricare) {	
		int[][][] stanza = null;
		try {
			InputStream	is = getClass().getResourceAsStream(percorsiStanze);			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String rigaLetta = null;
			
			//legge la prima riga e carica le dimensioni
			rigaLetta = br.readLine();
			String[] dimensioni = rigaLetta.trim().split("x");
			int maxRow = Integer.parseInt(dimensioni[0]);
			int maxCol = Integer.parseInt(dimensioni[1]);
	//		dimensioniStanze[stanzaDaCaricare][0] = maxCol;
	//		dimensioniStanze[stanzaDaCaricare][1] = maxRow;
			stanza = new int[NUM_STRATI][maxRow][maxCol];
			
			//serve per caricare i numeri usando la funzione split
			String[] numeriNellaRiga = new String[maxCol];
			int numeroStrato = 0;
			int riga = 0;
			while((rigaLetta = br.readLine()) != null) {
				
				if(!rigaLetta.isEmpty() && rigaLetta.contains(", "))	{		
					numeriNellaRiga = rigaLetta.trim().split(", ");
					
					if(numeriNellaRiga[maxCol -1].contains(",")) {		//capita spesso che l'ultimo numero abbia una virgola a destra
						
						String numeroDaSistemare = numeriNellaRiga[maxCol -1].split(",")[0];	//prendiamo solo l'ultimo numero
						int ultimoNumero = Integer.parseInt(numeroDaSistemare);
						
						for(int j = 0; j < maxCol -1; j++) 						//aggiunge tutti tranne l'ultimo
							stanza[numeroStrato][riga][j] = Integer.parseInt(numeriNellaRiga[j]);
						stanza[numeroStrato][riga][maxCol -1] = ultimoNumero;		//aggiunge l'ultimo
					}
					else {	
						for(int j = 0; j < maxCol; j++) 
							stanza[numeroStrato][riga][j] = Integer.parseInt(numeriNellaRiga[j]);
					}
					riga++;
				}
				else if(rigaLetta.contains("/")) {		//serve per capire quando è finito lo strato
					numeroStrato++;
					riga = 0;
				}
			}	
			br.close();	
			printStanza(maxRow, maxCol, stanza);
			
			}
		catch(Exception e) {
			e.printStackTrace();			
		}										
		return stanza;
	}
		
	public void printStanza(int maxRow, int maxCol, int[][][] stanza){			//for debugging
		for(int strato = 0; strato < NUM_STRATI; strato++) {		
			for(int riga = 0; riga < maxRow; riga++) {
				for(int colonna = 0; colonna < maxCol; colonna++) 
					System.out.print(stanza[strato][riga][colonna] + " ");
				System.out.println();						
				}		
			System.out.println();		
		}
	}
	
//	public int[] getDimensioniStanza(int stanza){
//		return dimensioniStanze[stanza];
//	}

	public int[][] getStrato(int stanza, int strato){
		return mappa[stanza][strato];
	}

	public int getMappaAttuale() {
		return MappaAttuale;
	}

	//cambia la mappa attuale
	public void setMappaAttuale(int mappaAttuale) {
		MappaAttuale = mappaAttuale;
	}
}	
	
