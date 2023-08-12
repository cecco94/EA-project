package view.playState;

import java.awt.Graphics2D;

//per disegnare tutti gli elementi della mappa dall'alto verso il basso, li ordiniamo secondo la loro posizione y e secondo il 
//loro tipo. Ogni elemento della mappa estenderà questa classe molto astratta che implementa una relazione di uguaglianza/disuguaglianza
public abstract class SortableElement implements Comparable<SortableElement> {

	protected int xPosMap;	//la posizione x non è strettamente necessaria, è utile per disegnare l'elemento dopo
	protected int yPosMap;
	protected int typeElemtToSort;	//2 = terzo strato, 3 = quarto strato, 4 = npc/player
	
	@Override
	public int compareTo(SortableElement e) {
		//se sono tile dello stesso tipo non ha senso ordinarli
		if(this.typeElemtToSort == e.typeElemtToSort && (this.typeElemtToSort == 0 || this.typeElemtToSort == 1))
			return 0;
		
		else {
			if(this.yPosMap < e.yPosMap) //se si trova più in alto deve essere disegnato prima
				return -1;
			
			else if(this.yPosMap > e.yPosMap) //se si trova più in basso deve essere disegnato dopo
				return 1;
			
			else if(this.yPosMap == e.yPosMap) {	//se si trova nella stessa posizione, prima si disegnano
				if(this.typeElemtToSort < e.typeElemtToSort)	    //gli oggetti con tipo più basso
					return -1;	
				else 
					return 1;
				
			}		
		}
		return 0;	//non dovremmo mai arrivare qui
	}
	
	public abstract void draw(Graphics2D g2, int xOffsetRespectTheCenterOfScreen, int yOffsetRespectTheCenterOfScreen, int xPlayerMap, int yPlayerMap);
	
	
}
