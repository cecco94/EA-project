package model.mappa;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Map {

	//primo campo = stanza, secondo campo = strato, terzo e quarto = posizione x,y
	private int[][][][] map;	
	
	private static final int ROOMS_NUMBERS = Rooms.numStanze, LAYERS_NUMBER = 4;
	public static final int FIRST_LAYER = 0, SECOND_LAYER = 1, THIRD_LAYER = 2, FOURTH_LAYER = 3;
	
	
	private String[] roomPath = {"/mappe/biblioteca.txt", "/mappe/dormitorio.txt",
										"/mappe/aulaStudio.txt", "/mappe/tenda.txt", "/mappe/laboratorio.txt", "/mappe/studioProf.txt"};
	
	public Map() {	
		map = new int[ROOMS_NUMBERS][LAYERS_NUMBER][][];
		
		for(int roomToLoad = 0; roomToLoad < ROOMS_NUMBERS; roomToLoad++)
			map[roomToLoad] = loadRoom(roomPath[roomToLoad], roomToLoad);	
		
	}

		
	//primo campo= strato, secondo campo = posizione y ,terzo campo = posizizione x 
	private int[][][] loadRoom(String roomsPaths, int roomToLoad) {	
		int[][][] room = null; 
		try {
			InputStream	is = getClass().getResourceAsStream(roomsPaths);			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String readLine = null;
			
			//legge la prima riga e carica le dimensioni
			readLine = br.readLine();
			String[] dimensions = readLine.trim().split("x");
			int maxRow = Integer.parseInt(dimensions[0]);
			int maxCol = Integer.parseInt(dimensions[1]);
			room = new int[LAYERS_NUMBER][maxRow][maxCol];
			
			//serve per caricare i numeri usando la funzione split
			String[] numbersInLine = new String[maxCol];
			int layerNumber = 0;
			int row = 0;
			while((readLine = br.readLine()) != null) {
				
				if(!readLine.isEmpty() && readLine.contains(", "))	{		
					numbersInLine = readLine.trim().split(", ");
					
					if(numbersInLine[maxCol -1].contains(",")) {		//capita spesso che l'ultimo numero abbia una virgola a destra
						
						String numberToFix = numbersInLine[maxCol -1].split(",")[0];	//prendiamo solo l'ultimo numero
						int lastNumber = Integer.parseInt(numberToFix);
						
						for(int j = 0; j < maxCol -1; j++) 						//aggiunge tutti tranne l'ultimo
							room[layerNumber][row][j] = Integer.parseInt(numbersInLine[j]);
						
						room[layerNumber][row][maxCol -1] = lastNumber;		//aggiunge l'ultimo
					}
					else {	
						for(int j = 0; j < maxCol; j++) 
							room[layerNumber][row][j] = Integer.parseInt(numbersInLine[j]);
					}
					row++;
				}
				else if(readLine.contains("/")) {		//serve per capire quando è finito lo strato
					layerNumber++;
					row = 0;
				}
			}	
			br.close();	
		//	printStanza(maxRow, maxCol, stanza);
			
			}
		catch(Exception e) {
			e.printStackTrace();			
		}										
		return room;
	}
		
	/*public void printStanza(int maxRow, int maxCol, int[][][] stanza){			//for debugging
		for(int strato = 0; strato < LAYERS_NUMBER; strato++) {		
			for(int riga = 0; riga < maxRow; riga++) {
				for(int colonna = 0; colonna < maxCol; colonna++) 
					System.out.print(stanza[strato][riga][colonna] + " ");
				System.out.println();						
				}		
			System.out.println();		
		}
	}*/

	public int[][] getLayer(int stanza, int strato){
		return map[stanza][strato];
	}

}	
	
