package controller.playState;

import model.mappa.Map;
import view.sound.SoundManager;

public enum Stanze {

	BIBLIOTECA(Map.BIBLIOTECA, SoundManager.BIBLIOTECA), DORMITORIO(Map.DORMITORIO, SoundManager.DORMITORIO);
	
	Stanze(int i, int music) {
		indiceNellaMappa = i;
		indiceMusica = music;
	}

	public static Stanze stanzaAttuale = DORMITORIO;
	
	public int indiceNellaMappa, indiceMusica;
}
