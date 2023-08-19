package controller.playState;

import model.mappa.Map;
import view.sound.SoundManager;

public enum Stanze {

	BIBLIOTECA(Map.BIBLIOTECA, SoundManager.BIBLIOTECA), DORMITORIO(Map.DORMITORIO, SoundManager.DORMITORIO), AULA_STUDIO(Map.AULA_STUDIO, SoundManager.SALA_STUDIO);
	
	Stanze(int i, int music) {
		indiceNellaMappa = i;
		indiceMusica = music;
	}

	public static Stanze stanzaAttuale = AULA_STUDIO;
	
	public int indiceNellaMappa, indiceMusica;
} 