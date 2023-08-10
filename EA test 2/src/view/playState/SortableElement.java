package view.playState;

import java.awt.Graphics2D;

public abstract class SortableElement implements Comparable<SortableElement>{

	protected int xPos;
	protected int yPos;
	protected int type;	//2 = terzo strato, 3 = quarto strato, 4 = npc, 5 = player
	
	@Override
	public int compareTo(SortableElement e) {
		//se sono tile dello stesso tipo non ha senso ordinarli
		if(this.type == e.type && (this.type == 0 || this.type == 1))
			return 0;
		
		else {
			if(this.yPos < e.yPos) //se si trova più in alto deve essere disegnato prima
				return -1;
			
			else if(this.yPos > e.yPos) //se si trova più in basso deve essere disegnato dopo
				return 1;
			
			else if(this.yPos == e.yPos) {	//se si trova nella stessa posizione, prima si disegnano
				if(this.type < e.type)	    //gli oggetti con tipo più basso
					return -1;	
				else 
					return 1;
				
			}		
		}
		return 0;	//non dovremmo mai arrivare qui
	}
	
	public abstract void draw(Graphics2D g2);
	
}
