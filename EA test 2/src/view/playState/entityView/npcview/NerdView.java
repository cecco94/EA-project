package view.playState.entityView.npcview;


import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.IView;
import view.ViewUtils;
import view.main.GamePanel;

public class NerdView extends NPCView {
	

	public NerdView(IView v, int index) {
		super(v, index);
		
		this.type = "npc";
		
		setDialogues();
		loadImages();	
		
		xOffset = (int)(0*GamePanel.SCALE); //3;
		yOffset = (int)(0*GamePanel.SCALE); //3;
		animationSpeed = 40;
		
		
	}

	private void loadImages() {
		BufferedImage image = null;
		BufferedImage temp = null;
		
		animation = new BufferedImage[1][2][][];		//un tipo di vecchio, due azioni

		animation[0][IDLE] = new BufferedImage[4][1];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		animation[0][MOVE] = new BufferedImage[4][3];		//ci sono 4 direzioni, ogni direzione ha 3 immagini
		
		loadRunImages(image, temp);
		loadIdleImages();
		
	}

	private void loadIdleImages() {
		//prendi le immagini già caricate, prendi la seconda ogni tre		
				for(int direction = 0;  direction < 4; direction++)
					animation[0][IDLE][direction][0] = animation[0][MOVE][direction][1]; 
		
	}

	private void loadRunImages(BufferedImage image, BufferedImage temp) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/entity/NerdGiusto.png"));
						
			int counter = 0;
			for(int direction = 0; direction < 4; direction++) {
				for(int index = 0; index < 3; index++) {
					temp = image.getSubimage(index*16 + 16*counter, 0, 16, 27);
					temp = ViewUtils.scaleImage(temp, temp.getWidth()*1.5f*GamePanel.SCALE, temp.getHeight()*1.5f*GamePanel.SCALE);
					animation[0][MOVE][direction][index] = temp;
				}
				counter += 3;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void setDialogues() {
		dialogues = new String[7];
		dialogues[0] = "capiti proprio nel momento giusto";
		dialogues[1] = "che ci faccio una tenda? semplice, \n non ho abbastanza soldi per affitare una casa \n "
						+ "e sono troppo asociale per vivere in un dormitorio";
		dialogues[2] = "noi nerd siamo fatti così";
		dialogues[3] = "la tua presenza mi disturba, ma ho bisogno di una mano";
		dialogues[4] = "sto programmando un videogioco per una esame, \n ma un gatto mi ha rubato l'hard disk con il codice sorgente!";
		dialogues[5] = "se lo trovi, portamenlo e \n condividerò con te la mia conoscenza nerdosa";
		dialogues[6] = "uscirei io stesso a cercarlo, ma ci sono troppi \n esseri umani in giro per i miei gusti";
	}
	
	protected int getAnimationLenght() {
		if(currentAction == IDLE)
			return 1;
		
		else if(currentAction == MOVE)
			return 3;
		
		return 0;
	}

}
