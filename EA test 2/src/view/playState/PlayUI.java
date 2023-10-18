package view.playState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import view.ViewUtils;
import view.main.GamePanel;

// classe che mostra a video le informazioni come il punteggio, la vita, le munizioni...
// si occupa inoltre di disegnare la transizione da una stanza all'altra
public class PlayUI {

	private Font fontDisplay = new Font("Arial", Font.PLAIN, (int)(20*GamePanel.SCALE));
	private String message = "";
	
	private BufferedImage darkEffect, noteIcon, cfuIcon, exclamation;
	private BufferedImage[] lifeIcons;
	private String dataToShow = "";	
	private int yPosPlayerData = (int)(5*GamePanel.SCALE);
	private int xPosPlayerData = (int)(20*GamePanel.SCALE);
	
	//questi servono per disegnare il dormitorio buio... si può fare di meglio
	private boolean dark = true;
	private final int DORMITORIO = 1;
	
	private PlayStateView play;
	private int counterMessage = 0;
	private boolean showMessage;
	
	//per disegnare un punto esclamativo sopra ai nemici che vedono il player
	private boolean exclamationOn;
	private int exclamationCounter;
	private int indexEntityExclaming;
	
	public PlayUI(PlayStateView p) {
		play = p;
		loadImages();
		
	}
	
	private void loadImages() {
		try {
			darkEffect = ImageIO.read(getClass().getResourceAsStream("/ui/effettoBuioFinale.png"));
			darkEffect = ViewUtils.scaleImage(darkEffect, darkEffect.getWidth()*GamePanel.SCALE, darkEffect.getHeight()*GamePanel.SCALE);
			
			cfuIcon = ImageIO.read(getClass().getResourceAsStream("/ui/punteggioPiccolo.png"));
			cfuIcon = ViewUtils.scaleImage(cfuIcon, cfuIcon.getWidth()*GamePanel.SCALE, cfuIcon.getHeight()*GamePanel.SCALE);

			lifeIcons = new BufferedImage[4];
			BufferedImage temp = ImageIO.read(getClass().getResourceAsStream("/ui/vitaPiccola.png"));
			
			for(int i = 0; i < 4; i++) 
				lifeIcons[i] = temp.getSubimage(45*i, 0, 45, 32);
			
			for(int i = 0; i < 4; i++) {
				lifeIcons[i] = ViewUtils.scaleImage(lifeIcons[i], lifeIcons[i].getWidth()*GamePanel.SCALE, lifeIcons[i].getHeight()*GamePanel.SCALE);
			}
			noteIcon = ImageIO.read(getClass().getResourceAsStream("/ui/appuntiPiccoli.png"));
			noteIcon = ViewUtils.scaleImage(noteIcon, noteIcon.getWidth()*GamePanel.SCALE, noteIcon.getHeight()*GamePanel.SCALE);
			
			exclamation = ImageIO.read(getClass().getResourceAsStream("/ui/exclamation.png"));
			exclamation = ViewUtils.scaleImage(exclamation, exclamation.getWidth()/8*GamePanel.SCALE, exclamation.getHeight()/8*GamePanel.SCALE);
			
		} 
		catch (IOException e) {	
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		drawPlayerData(g2);
		drawExclamation(g2);
		drawDark(g2);
		drawMessage(g2);
		
	}
	
	private void drawPlayerData(Graphics2D g2) {
		drawBackGround(g2);
		drawLife(g2, dataToShow);
		drawNotes(g2, dataToShow);	
		drawPoints(g2, dataToShow);
		//resetta il valore della x
		xPosPlayerData = (int)(20*GamePanel.SCALE);
	}

	private void drawBackGround(Graphics2D g2) {
		g2.getColor().getAlpha();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g2.setColor(Color.yellow);
		g2.fillRoundRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT/12, 40, 40);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g2.setColor(Color.black);
		
	}

	private void drawLife(Graphics2D g2, String s) {
		int life = play.getView().getController().getPlay().getPlayer().getLife();
		s = life + " %";

		if(life > 75)
			g2.drawImage(lifeIcons[0], xPosPlayerData, yPosPlayerData, null);
		else if(life <=75 && life > 50)
			g2.drawImage(lifeIcons[1], xPosPlayerData, yPosPlayerData, null);
		else if(life <=50 && life > 25)
			g2.drawImage(lifeIcons[2], xPosPlayerData, yPosPlayerData, null);
		else if(life <= 25)
			g2.drawImage(lifeIcons[3], xPosPlayerData, yPosPlayerData, null);
		
		g2.setFont(fontDisplay);
		xPosPlayerData += lifeIcons[0].getWidth() + (int)(10*GamePanel.SCALE);
		g2.drawString(s, xPosPlayerData, yPosPlayerData + (int)(25*GamePanel.SCALE));
		
	}
	
	private void drawPoints(Graphics2D g2, String s) {
		int cfu = play.getView().getController().getPlayerCfu();
		s = "" + cfu;

		xPosPlayerData = GamePanel.GAME_WIDTH - cfuIcon.getWidth() - (int)(60*GamePanel.SCALE);
		g2.drawImage(cfuIcon, xPosPlayerData, yPosPlayerData, null);
		
		xPosPlayerData += cfuIcon.getWidth() + (int)(10*GamePanel.SCALE);
		g2.drawString(s, xPosPlayerData, yPosPlayerData + (int)(25*GamePanel.SCALE));
	}

	private void drawNotes(Graphics2D g2, String s) {
		int notes = play.getView().getController().getPlayerNotes();
		s = "" + notes;

		xPosPlayerData = ViewUtils.getCenteredXPos(noteIcon.getWidth());
		g2.drawImage(noteIcon, xPosPlayerData, yPosPlayerData, null);
		
		xPosPlayerData += noteIcon.getWidth() + (int)(10*GamePanel.SCALE);
		g2.drawString(s, xPosPlayerData, yPosPlayerData + (int)(25*GamePanel.SCALE));
		
	}

	private void drawDark(Graphics2D g2) {
		if (dark && play.getView().getCurrentRoomIndex() == DORMITORIO)
			g2.drawImage(darkEffect, 0, 0, null);
	}

	private void drawMessage(Graphics2D g2) {
		if(showMessage) {
			
			counterMessage++;
			if(counterMessage < 360) {
				
				g2.setFont(fontDisplay);
				
				int x = ViewUtils.getXforCenterText(message, g2);
				int y = GamePanel.GAME_HEIGHT/2 + (int)(20*GamePanel.SCALE);
				int width = ViewUtils.getStringLenght(message, g2);
				int height = ViewUtils.getStringHeight(message, g2);
				
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
				g2.setColor(Color.yellow);
				g2.fillRoundRect(x, y, width + (int)(3*GamePanel.SCALE), height + (int)(4*GamePanel.SCALE), 30, 30);
				
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

				g2.setColor(Color.red);
				g2.drawString(message, x, y + height);
				g2.setColor(Color.black);	
			}
			
			else {
				counterMessage = 0;
				showMessage = false;
			}
		}
		
	}

	public void setMessage(String s) {
		message = s;
	}
	
	public void setShowMessage(boolean showMessage) {
		this.showMessage = showMessage;
	}
	
	public void setDark(boolean b) {
		dark = b;
	}

	public void drawDialogue(Graphics2D g2) {
		
		g2.setColor(Color.black);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
		g2.fillRoundRect(0, GamePanel.GAME_HEIGHT/2 + GamePanel.GAME_HEIGHT/4, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT/4, 30, 30);
		
		g2.setColor(Color.white);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g2.drawRoundRect((int)(5*GamePanel.SCALE), GamePanel.GAME_HEIGHT/2 + GamePanel.GAME_HEIGHT/4 + (int)(5*GamePanel.SCALE),
						   GamePanel.GAME_WIDTH - (int)(10*GamePanel.SCALE), GamePanel.GAME_HEIGHT/4 - (int)(10*GamePanel.SCALE), 30, 30);
		
		//prende l'indice dell'npc con cui parla il player, va nella stanza e prende quell'npc, prende le stringhe di dialogo da lì, poi le disegna
		int index = play.getView().getController().getIndexOfInteractingEntity();
		String text = play.getRoom(play.getView().getCurrentRoomIndex()).getNPC(index).getCurrentDialogue();
		g2.setFont(fontDisplay);
		
		int y = GamePanel.GAME_HEIGHT/2 + GamePanel.GAME_HEIGHT/4 + (int)(30*GamePanel.SCALE);
		
		for(String line : text.split("\n ")) {			
			g2.drawString(line, (int)(10*GamePanel.SCALE), y);
			y += 40;
		}
	
	}

	public void drawExclamation(Graphics2D g2) {
		if(exclamationOn) {
			exclamationCounter++;
			if(exclamationCounter <= 100) {
				try {
					int exclamationHeight = exclamation.getHeight();
					
					int xpos =  play.getRoom(play.getView().getCurrentRoomIndex()).getEnemy(indexEntityExclaming).getxPosOnScreen();
					int ypos =  play.getRoom(play.getView().getCurrentRoomIndex()).getEnemy(indexEntityExclaming).getyPosOnScreen();
		
					g2.drawImage(exclamation, xpos, ypos - exclamationHeight, null);
				}
				catch(IndexOutOfBoundsException iobe) {
					System.out.println("problema negli indici dei nemici nella ui");
				}
			}
			else {
				exclamationOn = false;
				exclamationCounter = 0;
			}
		}
	}
	
	public void activeExclamation(int index) {
		exclamationOn = true;
		indexEntityExclaming = index;
	}
}
