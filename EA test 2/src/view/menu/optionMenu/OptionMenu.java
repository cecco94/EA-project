package view.menu.optionMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.main.Gamestate;

import static view.main.GamePanel.GAME_HEIGHT;
import static view.main.GamePanel.GAME_WIDTH;
import static view.main.GamePanel.SCALE;
import static view.menu.optionMenu.SoundBar.SE;
import static view.menu.optionMenu.SoundBar.MUSIC;

import view.IView;
import view.ViewUtils;
import view.menu.AbstractMenu;
import view.menu.AbstractMenuButton;
import view.menu.mainMenu.InitialMenuButton;


public class OptionMenu extends AbstractMenu{

	private IView view;
	private BufferedImage musica, se, difficolta;
	private SoundBar musicBar, effectBar;
	private String[] spiegazioneDifficolta;

	private int firstButtonHeigh = GAME_HEIGHT/4;
	private int soundbarsX = GAME_WIDTH/2 + GAME_WIDTH/10;
	public final int maxBarWidth = GAME_WIDTH/4;
	public int currentMusicWidth = maxBarWidth, currentSEVolume = maxBarWidth;
	
	private final int MATRICOLA = 0, FUORISEDE  = 1, LAVORATORE = 2,
						INDIETRO = 3, MUSICA = 4, SUONI = 5;
	private int buttonIndex = MUSICA;	
	private int diffSetted = MATRICOLA;
	
	
	
	public OptionMenu(IView v) {
		view = v;
		loadOptionTypeText();
		loadButtons();
		setExplaningLabels();
	}

	// carica le scritte "volume musica", "volume suoni" , "livello difficoltà"
	private void loadOptionTypeText() {
		try {
			musica = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/opzioni/volumemusica.png"));	
			musica = ViewUtils.scaleImage(musica, musica.getWidth()/4 * SCALE, musica.getHeight()/4 * SCALE);
			
			se = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/opzioni/volumeeffetti.png"));	
			se = ViewUtils.scaleImage(se, se.getWidth()/4 * SCALE, se.getHeight()/4 * SCALE);
			
			difficolta = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/opzioni/diff.png"));	
			difficolta = ViewUtils.scaleImage(difficolta, difficolta.getWidth()/2 * SCALE, difficolta.getHeight()/2 * SCALE);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadButtons() {
		String[] percorsoMatric = {"/menuiniziale/opzioni/matricola1.png", "/menuiniziale/opzioni/matricola2.png", "/menuiniziale/opzioni/matricola3.png"};
		String[] percorsoFuoric = {"/menuiniziale/opzioni/fuorisede1.png", "/menuiniziale/opzioni/fuorisede2.png", "/menuiniziale/opzioni/fuorisede3.png"};
		String[] percorsoLvorat = {"/menuiniziale/opzioni/lavoratore1.png", "/menuiniziale/opzioni/lavoratore2.png", "/menuiniziale/opzioni/lavoratore3.png"};
		String[] percorsoIndietro = {"/menuiniziale/indietro1.png", "/menuiniziale/indietro2.png", "/menuiniziale/indietro3.png"};

		buttons = new AbstractMenuButton[6];
		buttons[0] = new OptionButton(percorsoMatric, (int)(3*firstButtonHeigh - 50*SCALE),(int)(120*SCALE),(int)(16*SCALE), 0, view);
		buttons[1] = new OptionButton(percorsoFuoric, (int)(3*firstButtonHeigh - 25*SCALE),(int)(150*SCALE),(int)(14*SCALE), 1, view);
		buttons[2] = new OptionButton(percorsoLvorat, 3*firstButtonHeigh,(int)(250*SCALE),(int)(16*SCALE), 2, view);
		buttons[3] = new InitialMenuButton(percorsoIndietro, (int)(firstButtonHeigh*4 - 30*SCALE), (int)(100*SCALE), (int)(15*SCALE), Gamestate.MAIN_MENU, view);
		
		// carico a parte le soundbar perchè mi serve poi disinguerle
		Rectangle r1 = new Rectangle(soundbarsX, firstButtonHeigh, maxBarWidth, (int)(10*SCALE));
		musicBar = new SoundBar(this, r1, view, MUSIC);
		buttons[4] = musicBar;
		
		Rectangle r2 = new Rectangle(soundbarsX, (int)(firstButtonHeigh + 40*SCALE), maxBarWidth, (int)(10*SCALE));
		effectBar = new SoundBar(this, r2, view, SE);
		buttons[5] = effectBar;
	}
	
	private void setExplaningLabels() {
		spiegazioneDifficolta = new String[3];
		spiegazioneDifficolta[0] = "Ti sei appena diplomato, sei pieno di speranze ed energia.. povero illuso";
		spiegazioneDifficolta[1] = "Forgiato dal fuoco di mille ritardi di Trenitalia";
		spiegazioneDifficolta[2] = "Hai a malapena il tempo per chiederti 'chi me l'ha fatto fare?'";
	}

	public void draw(Graphics2D g2) {
		drawBackground(g2);
		drawOptionTypeText(g2);
		drawChoiceIndex(g2);
		drawButtons(g2);
		drawEplaningLabels(g2);
	}

	// per non caricare 1000 volte le immagini dello sfondo, le facciamo disegnare sempre al menu iniziale
	private void drawBackground(Graphics2D g2) {
		view.getMenu().drawBackground(g2);	
	}
	
	private void drawOptionTypeText(Graphics2D g2) {
		g2.drawImage(musica, (int)(GAME_WIDTH/2 - musica.getWidth() - 60*SCALE), firstButtonHeigh, null);
		g2.drawImage(se, (int)(GAME_WIDTH/2 - se.getWidth() - 60*SCALE), (int)(firstButtonHeigh + 40*SCALE), null);
		g2.drawImage(difficolta, ViewUtils.getCenteredXPos(difficolta.getWidth(null)), (int)(3*firstButtonHeigh - 80*SCALE), null);
	}
	
	// quando il mouse passa sopra ad un bottone, appare una scritta che spiega la difficoltà
	private void drawEplaningLabels(Graphics2D g2) {
		
		for (int i = 0; i < buttons.length - 3; i++) 
			if(buttons[i].getMouseOver() == true) {
			//	g2.setColor(Color.white);
				int x = ViewUtils.getXforCenterText(spiegazioneDifficolta[i], g2);
				g2.drawString(spiegazioneDifficolta[i], x, 3*firstButtonHeigh + 40*SCALE);
			}
	}
	
	// disegna un simbolo ">" davanti alla difficoltà selezionata
	private void drawChoiceIndex(Graphics2D g2) {		
		Font previousFont = g2.getFont();
		g2.setFont(new Font("Arial", Font.PLAIN, 20));
		g2.setColor(Color.red);
		
		String s = ">";
		int x = (int) (GAME_WIDTH/2 - ViewUtils.getStringLenght(s, g2) - 10*SCALE);
		int y = ViewUtils.getStringHeight(s, g2)/3;

		if(diffSetted == MATRICOLA) {
			x -= buttons[0].getWidth()/2;
			y += (3*firstButtonHeigh - 50*SCALE) + buttons[0].getBounds().height/2;
		}
		else if(diffSetted == FUORISEDE) {
			x -= buttons[1].getWidth()/2;
			y += 3*firstButtonHeigh - 25*SCALE + buttons[1].getBounds().height/2;
		}
		else if (diffSetted == LAVORATORE) {
			x -= buttons[2].getWidth()/2;
			y += 3*firstButtonHeigh + buttons[2].getBounds().height/2;
		}
		
		g2.drawString(s, x, y);	
		g2.setFont(previousFont);
	}
	
	public void keyReleased(int tasto) {	
		switch(buttonIndex) {
		case MUSICA:
			comportamentoMusica(tasto);
			break;
			
		case SUONI:
			comportamentoSuoni(tasto);
			break;
			
		case MATRICOLA:
			comportamentoMatricola(tasto);
			break;
			
		case FUORISEDE:
			comportamentoFuorisede(tasto);
			break;
		
		case LAVORATORE:
			comportamentoLavoratore(tasto);
			break;
			
		case INDIETRO:
			comportamentoIndietro(tasto);
			break;																			
		}
	}

	private void comportamentoMusica(int tasto) {
		if(tasto == KeyEvent.VK_W || tasto == KeyEvent.VK_UP) {
			view.setCursorPosition(soundbarsX + (int)musicBar.getMusicVolume(), firstButtonHeigh);
		}
		
		else if(tasto == KeyEvent.VK_D || tasto == KeyEvent.VK_RIGHT) {
			musicBar.reactToKeyRight();
			view.setCursorPosition(soundbarsX +(int)musicBar.getMusicVolume() + maxBarWidth/20, firstButtonHeigh);
		}
		
		else if(tasto == KeyEvent.VK_A || tasto == KeyEvent.VK_LEFT) {
			musicBar.reactToKeyLeft();
			view.setCursorPosition(soundbarsX + (int)musicBar.getMusicVolume() - maxBarWidth/20, firstButtonHeigh);
		}
		
		else if((tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN)) {
			buttonIndex = SUONI;
			view.setCursorPosition(soundbarsX + (int)effectBar.getMusicVolume(), (int)(firstButtonHeigh + 40*SCALE));
		}
	}

	private void comportamentoSuoni(int tasto) {
		if(tasto == KeyEvent.VK_D || tasto == KeyEvent.VK_RIGHT) {
			effectBar.reactToKeyRight();
			view.setCursorPosition(soundbarsX + (int)effectBar.getMusicVolume() + maxBarWidth/20, (int)(firstButtonHeigh + 40*SCALE));
		}
		else if(tasto == KeyEvent.VK_A || tasto == KeyEvent.VK_LEFT) {
			effectBar.reactToKeyLeft();
			view.setCursorPosition(soundbarsX + (int)effectBar.getMusicVolume() - maxBarWidth/20, (int)(firstButtonHeigh + 40*SCALE));
		}
		else if((tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN)) {
			buttonIndex = MATRICOLA;
			view.setCursorPosition(buttons[MATRICOLA].getBounds().x, buttons[MATRICOLA].getBounds().y);
		}
		else if((tasto == KeyEvent.VK_W || tasto == KeyEvent.VK_UP)) {
			buttonIndex = MUSICA;
			view.setCursorPosition(soundbarsX + (int)musicBar.getMusicVolume(), firstButtonHeigh);
		}
	}

	private void comportamentoMatricola(int tasto) {
		if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[FUORISEDE].getBounds().x, buttons[FUORISEDE].getBounds().y);
			buttonIndex = FUORISEDE;
			}
		else if(tasto == KeyEvent.VK_W || tasto == KeyEvent.VK_UP) {
			view.setCursorPosition(soundbarsX + (int)effectBar.getMusicVolume(), (int)(firstButtonHeigh + 40*SCALE));
			buttonIndex = SUONI;
			}
		else if(tasto == KeyEvent.VK_ENTER) {
			resetButtons();
			buttons[MATRICOLA].reactToMouse(null);
		}
	}

	private void comportamentoFuorisede(int tasto) {
		if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[LAVORATORE].getBounds().x, buttons[LAVORATORE].getBounds().y);
			buttonIndex = LAVORATORE;
			}
			else if(tasto == KeyEvent.VK_W || tasto == KeyEvent.VK_UP) {
				view.setCursorPosition(buttons[MATRICOLA].getBounds().x, buttons[MATRICOLA].getBounds().y);
				buttonIndex = MATRICOLA;
				}
			else if(tasto == KeyEvent.VK_ENTER) {
				resetButtons();
				buttons[FUORISEDE].reactToMouse(null);
			}
	}

	private void comportamentoLavoratore(int tasto) {
		if(tasto == KeyEvent.VK_S || tasto == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[INDIETRO].getBounds().x, buttons[INDIETRO].getBounds().y);
			buttonIndex = INDIETRO;
			}
		else if(tasto == KeyEvent.VK_W || tasto == KeyEvent.VK_UP) {
			view.setCursorPosition(buttons[FUORISEDE].getBounds().x, buttons[FUORISEDE].getBounds().y);
			buttonIndex = FUORISEDE;
			}
		else if(tasto == KeyEvent.VK_ENTER) {	
			resetButtons();
			buttons[LAVORATORE].reactToMouse(null);	
		}
	}

	private void comportamentoIndietro(int tasto) {
		if(tasto == KeyEvent.VK_W || tasto == KeyEvent.VK_UP) {
			view.setCursorPosition(buttons[LAVORATORE].getBounds().x, buttons[LAVORATORE].getBounds().y);
			buttonIndex = LAVORATORE;
			}
		else if(tasto == KeyEvent.VK_ENTER) {
			resetButtons();
			view.changeGameState(buttons[INDIETRO].getButtonState());	
		}
	}

	public void setFifficolta(int i) {
		if(i == MATRICOLA)
			diffSetted = MATRICOLA;
		else if(i == FUORISEDE)
			diffSetted = FUORISEDE;
		else if (i == LAVORATORE)
			diffSetted = LAVORATORE;
	}

	// le soundbar cambiano quando il mouse viene trascinato dentro di loro


}
