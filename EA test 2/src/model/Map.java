package model;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import view.ViewUtils;
import view.main.GamePanel;

public class Map {

	public static final int NUM_STANZE = 1, NUM_STRATI = 3;
	public static final int PRIMO_STRATO = 0, SECONDO_STRATO = 1, TERZO_STRATO = 2;
	public static final int BIBLIOTECA = 0, SALA = 1, LABORATORIO = 2, DORMITORIO = 3;
	String[] percorsiStanze = {"/mappe/provaMappa.txt"};
	String percorsoTileset = "/mappe/inizializzaTileset.txt";
	private BufferedImage sourceImage;
	private static final int numTilePrimoStrato = 5;
	
	//primo campo = stanza, secondo campo = strato, terzo e quarto = posizione x,y
	private int[][][][] mappa;
	private static ArrayList<Tile> tileStrato1;
	private static ArrayList<Tile> tileStrato2;
	private static ArrayList<Tile> tileStrato3;
	
	public Map() {	
		initMap();		
		inizializzaTipiDiTile();
	}

	private void initMap() {
		mappa = new int[NUM_STANZE][NUM_STRATI][][];
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
			
//			System.out.println(maxRow + ", " + maxCol);
//			for(int i = 0; i < maxRow; i++) {
//				for(int j = 0; j < maxCol; j++) 
//					System.out.print(stanza[2][i][j] + " ");
//				System.out.println();						
//				}					
			}
		catch(Exception e) {
			e.printStackTrace();			
		}										
		return stanza;
	}
	
	private void inizializzaTipiDiTile() {
		
		tileStrato1 = new ArrayList<Tile>();
		tileStrato2 = new ArrayList<Tile>();
		tileStrato3 = new ArrayList<Tile>();
		int stratoAttuale = 1;
		
		InputStream	is = getClass().getResourceAsStream(percorsoTileset);				
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String s = null;
		String[] datiDaInserire = new String[2];
		
		try {
			while((s = br.readLine()) != null) {
				if(!s.isEmpty() && s.contains("; ") && stratoAttuale == 1)	{	
					datiDaInserire = s.split("; ");
					tileStrato1.add(new Tile(datiDaInserire[0], datiDaInserire[1]));
				}
				
				else if(!s.isEmpty() && s.contains("; ") && stratoAttuale == 2) {
					datiDaInserire = s.split("; ");
					tileStrato2.add(new Tile(datiDaInserire[0], datiDaInserire[1]));
				}
				
				else if(!s.isEmpty() && s.contains("; ") && stratoAttuale == 3) {
					datiDaInserire = s.split("; ");
					tileStrato3.add(new Tile(datiDaInserire[0], datiDaInserire[1]));
				}
				
				else if(!s.isEmpty() && s.contains("/")) {
					stratoAttuale++;
				}
			}
			br.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
			}	
		
		for(Tile t : tileStrato1)
			System.out.println(t.toString());
		
		for(Tile t : tileStrato2)
			System.out.println(t.toString());
		
		for(Tile t : tileStrato3)
			System.out.println(t.toString());
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
