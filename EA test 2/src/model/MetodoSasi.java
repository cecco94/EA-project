package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class MetodoSasi {
	
	public static double[] readSequences(String fileName){						//prende una sequenza e la trasforma in un array di double
		LinkedList<Double> seq = new LinkedList<Double>();						//mette i valori in una lista 
		try {
			BufferedReader in = new BufferedReader (new FileReader(fileName));
			String line = in.readLine ();
			while (line != null){
				String[] sDati = line.split(" ");								//ogni riga del file diventa una stringa 
				for (int j=0; j < sDati.length; j++) {
					seq.add(Double.parseDouble(sDati[j]));						//i valori della stringa vengono trasformati in double e messi nella lista
			}	
				line = in.readLine ();	
			}
			in.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	
		double[] sequenza = new double[seq.size()];						//mette la lista in un array togliendo il primo elemento ogni volta
		for(int i = 0; i < seq.size(); i++)
			sequenza[i] = (double) seq.poll();
		
		return sequenza;
	}
	
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	public static void writeFile(String fileName, String txt){							//salva la stringa di output
		try {
			PrintWriter out = new PrintWriter (new FileWriter(fileName));
			out.println(txt);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

