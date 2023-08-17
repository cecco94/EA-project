package model.mappa;

import java.awt.Rectangle;
import java.util.ArrayList;

import model.IModel;
import model.Passaggio;

public class Room {

	private IModel model;
	public ArrayList<Passaggio> passaggi;
	
	
	public Room(IModel m) {
	model = m;
	passaggi = new ArrayList<>();
	}
	
	public void addNewPassaggio(int x, int y, int width, int height, int newX, int newY, int newRoom) {
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
}

//il giocatore ogni volta che si muove vede se sta sopra ad un passaggio
// per farlo, chiede al model, che controlla i passaggi di una sola stanza
// la stanza controlla tutti i suoi passaggi