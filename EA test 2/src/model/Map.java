package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;

public class Map {

	public static final int BIBLIOTECA = 0, SALA = 1, 
							LABORATORIO = 2, DORMITORIO = 3;
	public static final int NUM_STANZE = 1				, NUM_STRATI = 3;
	
	public static final int PRIMO_STRATO = 0, SECONDO_STRATO = 1, TERZO_STRATO = 2;
	
	//primo campo = stanza, secondo campo = strato, terzo e quarto = posizione x,y
	private int[][][][] mappa;
	
	
	public Map() {
		
		mappa = new int[NUM_STANZE][NUM_STRATI][][];
		String[] percorsiStanze = {"/mappe/provaMappa.txt"};
		
		for(int i = 0; i < NUM_STANZE; i++)
			mappa[i] = loadStanza(percorsiStanze[i]);
		
	}
	
	private int[][][] loadStanza(String percorsiStanze) {	
		int[][][] stanza = null;
		int numeroStrato = 0;
		int riga = 0;
		
		try {
			InputStream	is = getClass().getResourceAsStream(percorsiStanze);				//approfondire
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			String s = null;
			
			//legge la prima riga e carica le dimensioni
			s = br.readLine();
			String[] dimensioni = s.split(",");
			int maxRow = Integer.parseInt(dimensioni[0]);
			int maxCol = Integer.parseInt(dimensioni[1]);
			stanza = new int[NUM_STRATI][maxRow][maxCol];
			
			//serve per caricare i numeri usando la funzione split
			String[] numeriNellaRiga = new String[maxCol];
			
			while((s = br.readLine()) != null) {
				if(!s.isEmpty() && s.contains(" "))	{		//da cambiare con ",  "
					numeriNellaRiga = s.split(" ");
					for(int j = 0; j < maxCol; j++)
						//inserisci i valori nell'array
						stanza[numeroStrato][riga][j] = Integer.parseInt(numeriNellaRiga[j]);
					riga++;
				}
				else if(s.contains("/")) {		//serve per capire quando è finito lo strato
					numeroStrato++;
					riga = 0;
				}
				
			}
			br.close();
			
		/* System.out.println(maxRow + ", " + maxCol);
			for(int i = 0; i < maxRow; i++) {
				for(int j = 0; j < maxCol; j++) 
					System.out.print(stanza[2][i][j] + " ");
			System.out.println();						
			}				*/
			
		}
		catch(Exception e) {
			e.printStackTrace();			
		}									
		
		return stanza;
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
