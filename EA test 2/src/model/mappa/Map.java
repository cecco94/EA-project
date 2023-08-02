package model.mappa;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Map {

	//primo campo = stanza, secondo campo = strato, terzo e quarto = posizione x,y
	private int[][][][] mappa;	
	
	//primo campo = stanza, secondo campo = larghezza/altezza
	private int[][] dimensioniStanze;
	
	public static final int NUM_STANZE = 1, NUM_STRATI = 3;
	public static final int PRIMO_STRATO = 0, SECONDO_STRATO = 1, TERZO_STRATO = 2;
	public static final int BIBLIOTECA = 0, SALA = 1, LABORATORIO = 2, DORMITORIO = 3;
	private String[] percorsiStanze = {"/mappe/provaMappa.txt"};
	
	public Map() {	
		mappa = new int[NUM_STANZE][NUM_STRATI][][];
		dimensioniStanze = new int[NUM_STANZE][2];
		
		for(int stanzaAttuale = 0; stanzaAttuale < NUM_STANZE; stanzaAttuale++)
			mappa[stanzaAttuale] = loadStanza(percorsiStanze[stanzaAttuale], stanzaAttuale);		
	}

	private int[][][] loadStanza(String percorsiStanze, int stanzaDaCaricare) {	
		int[][][] stanza = null;
		int numeroStrato = 0;
		int riga = 0;
		try {
			InputStream	is = getClass().getResourceAsStream(percorsiStanze);				//approfondire
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String s = null;
			
			//legge la prima riga e carica le dimensioni
			s = br.readLine();
			String[] dimensioni = s.trim().split("x");
			int maxRow = Integer.parseInt(dimensioni[0]);
			int maxCol = Integer.parseInt(dimensioni[1]);
			dimensioniStanze[stanzaDaCaricare][0] = maxCol;
			dimensioniStanze[stanzaDaCaricare][1] = maxRow;
			stanza = new int[NUM_STRATI][maxRow][maxCol];
			
			//serve per caricare i numeri usando la funzione split
			String[] numeriNellaRiga = new String[maxCol];
			
			while((s = br.readLine()) != null) {
				
				if(!s.isEmpty() && s.contains(", "))	{		
					numeriNellaRiga = s.trim().split(", ");
					
					for(int j = 0; j < maxCol; j++) 
						stanza[numeroStrato][riga][j] = Integer.parseInt(numeriNellaRiga[j]);

					riga++;
				}
				else if(s.contains("/")) {		//serve per capire quando è finito lo strato
					numeroStrato++;
					riga = 0;
				}
			}
			
			br.close();	
		//	printStanza(maxRow, maxCol, stanza);
			
			}
		catch(Exception e) {
			e.printStackTrace();			
		}										
		return stanza;
	}
	
	public void printStanza(int maxRow, int maxCol, int[][][] stanza){			//for debugging
		System.out.println(dimensioniStanze[0][1] + ", " + dimensioniStanze[0][0]);
		for(int strato = 0; strato < NUM_STRATI; strato++) {		
			for(int riga = 0; riga < maxRow; riga++) {
				for(int colonna = 0; colonna < maxCol; colonna++) 
					System.out.print(stanza[strato][riga][colonna] + " ");
				System.out.println();						
				}		
			System.out.println();		
		}
	}
	
	public int[] getDimensioniStanza(int stanza){
		return dimensioniStanze[stanza];
	}
	
	public int[][] getStrato(int stanza, int strato){
		return mappa[stanza][strato];
	}
}	








/*	public static int[][] loadLayer(String fileName){
		
		List<String> righe = readLines(fileName);			//prende tutte le righe
		String[][] righeArray = new String[righe.size()][];
		int[][] righeInt = new int[righe.size()][];
		
		for(int i = 0; i < righe.size(); i++) {
			
			righeArray[i] = righe.get(i).split(" ");		//le splitta
			righeInt[i] = new int[righeArray[i].length];
			
			for(int j = 0; j < righeArray[i].length; j++)	//trasforma il contenuto in int
				righeInt[i][j] = Integer.parseInt(righeArray[i][j]);
		}
		
		return righeInt;				*/
		
/*		for(int i = 0; i < righeArray.length; i++)
			for(int j = 0; j < righeArray[i].length; j++) {
				System.out.print(righeInt[i][j]);
				if(j == righeArray[i].length -1)
					System.out.println("");
			}													
	}
				*/
	    
/*	 public static List<String> readLines(String fileName){
		File file = new File(fileName);
		List<String> righe = null;
		try {
			righe = Files.readAllLines(file.toPath());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return righe;
	 }
	 
	 */	
