package model.mappa;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;

public class MapReader {
	
	public static void loadMaps(String[] percorsiStanze) {
		int[][] strato;
		
		for(int i = 0; i < percorsiStanze.length; i++)		//per ogni stanza
			for(int j = 0; j < Map.NUM_STRATI; j++)			//per ogni strato
				strato = loadLayer(percorsiStanze[j]);
	}
	
	
	
	
	
	
	public static int[][] loadLayer(String fileName){
		
		List<String> righe = readLines(fileName);			//prende tutte le righe
		String[][] righeArray = new String[righe.size()][];
		int[][] righeInt = new int[righe.size()][];
		
		for(int i = 0; i < righe.size(); i++) {
			
			righeArray[i] = righe.get(i).split(" ");		//le splitta
			righeInt[i] = new int[righeArray[i].length];
			
			for(int j = 0; j < righeArray[i].length; j++)	//trasforma il contenuto in int
				righeInt[i][j] = Integer.parseInt(righeArray[i][j]);
		}
		
		return righeInt;
		
/*		for(int i = 0; i < righeArray.length; i++)
			for(int j = 0; j < righeArray[i].length; j++) {
				System.out.print(righeInt[i][j]);
				if(j == righeArray[i].length -1)
					System.out.println("");
			}													*/
	}
	
	    
	 public static List<String> readLines(String fileName){
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
	    
	   
	    
} 
