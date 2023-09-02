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
	private BufferedImage musicIcon, soundEffectIcon, difficulty;
	private SoundBar musicBar, effectBar;
	private String[] difficultyText;

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
			musicIcon = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/opzioni/volumemusica.png"));	
			musicIcon = ViewUtils.scaleImage(musicIcon, musicIcon.getWidth()/4 * SCALE, musicIcon.getHeight()/4 * SCALE);
			
			soundEffectIcon = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/opzioni/volumeeffetti.png"));	
			soundEffectIcon = ViewUtils.scaleImage(soundEffectIcon, soundEffectIcon.getWidth()/4 * SCALE, soundEffectIcon.getHeight()/4 * SCALE);
			
			difficulty = ImageIO.read(getClass().getResourceAsStream("/menuiniziale/opzioni/diff.png"));	
			difficulty = ViewUtils.scaleImage(difficulty, difficulty.getWidth()/2 * SCALE, difficulty.getHeight()/2 * SCALE);
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
		difficultyText = new String[3];
		difficultyText[0] = "Ti sei appena diplomato, sei pieno di speranze ed energia.. povero illuso";
		difficultyText[1] = "Forgiato dal fuoco di mille ritardi di Trenitalia";
		difficultyText[2] = "Hai a malapena il tempo per chiederti 'chi me l'ha fatto fare?'";
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
		g2.drawImage(musicIcon, (int)(GAME_WIDTH/2 - musicIcon.getWidth() - 60*SCALE), firstButtonHeigh, null);
		g2.drawImage(soundEffectIcon, (int)(GAME_WIDTH/2 - soundEffectIcon.getWidth() - 60*SCALE), (int)(firstButtonHeigh + 40*SCALE), null);
		g2.drawImage(difficulty, ViewUtils.getCenteredXPos(difficulty.getWidth(null)), (int)(3*firstButtonHeigh - 80*SCALE), null);
	}
	
	// quando il mouse passa sopra ad un bottone, appare una scritta che spiega la difficoltà
	private void drawEplaningLabels(Graphics2D g2) {
		
		for (int i = 0; i < buttons.length - 3; i++) 
			if(buttons[i].getMouseOver() == true) {
			//	g2.setColor(Color.white);
				int x = ViewUtils.getXforCenterText(difficultyText[i], g2);
				g2.drawString(difficultyText[i], x, 3*firstButtonHeigh + 40*SCALE);
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
	
	public void keyReleased(int key) {	
		switch(buttonIndex) {
		case MUSICA:
			keyboardInputsMusic(key);
			break;
			
		case SUONI:
			keyboardInputsSoundEffect(key);
			break;
			
		case MATRICOLA:
			keyboardInputsMatricola(key);
			break;
			
		case FUORISEDE:
			keyboardInputsFuoriSede(key);
			break;
		
		case LAVORATORE:
			keyboardInputsLavoratore(key);
			break;
			
		case INDIETRO:
			keyboardInputsBack(key);
			break;																			
		}
	}

	private void keyboardInputsMusic(int key) {
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			view.setCursorPosition(soundbarsX + (int)musicBar.getMusicVolume(), firstButtonHeigh);
		}
		
		else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			musicBar.reactToKeyRight();
			view.setCursorPosition(soundbarsX +(int)musicBar.getMusicVolume() + maxBarWidth/20, firstButtonHeigh);
		}
		
		else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			musicBar.reactToKeyLeft();
			view.setCursorPosition(soundbarsX + (int)musicBar.getMusicVolume() - maxBarWidth/20, firstButtonHeigh);
		}
		
		else if((key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)) {
			buttonIndex = SUONI;
			view.setCursorPosition(soundbarsX + (int)effectBar.getMusicVolume(), (int)(firstButtonHeigh + 40*SCALE));
		}
	}

	private void keyboardInputsSoundEffect(int key) {
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			effectBar.reactToKeyRight();
			view.setCursorPosition(soundbarsX + (int)effectBar.getMusicVolume() + maxBarWidth/20, (int)(firstButtonHeigh + 40*SCALE));
		}
		else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			effectBar.reactToKeyLeft();
			view.setCursorPosition(soundbarsX + (int)effectBar.getMusicVolume() - maxBarWidth/20, (int)(firstButtonHeigh + 40*SCALE));
		}
		else if((key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)) {
			buttonIndex = MATRICOLA;
			view.setCursorPosition(buttons[MATRICOLA].getBounds().x, buttons[MATRICOLA].getBounds().y);
		}
		else if((key == KeyEvent.VK_W || key == KeyEvent.VK_UP)) {
			buttonIndex = MUSICA;
			view.setCursorPosition(soundbarsX + (int)musicBar.getMusicVolume(), firstButtonHeigh);
		}
	}

	private void keyboardInputsMatricola(int key) {
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[FUORISEDE].getBounds().x, buttons[FUORISEDE].getBounds().y);
			buttonIndex = FUORISEDE;
			}
		else if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			view.setCursorPosition(soundbarsX + (int)effectBar.getMusicVolume(), (int)(firstButtonHeigh + 40*SCALE));
			buttonIndex = SUONI;
			}
		else if(key == KeyEvent.VK_ENTER) {
			resetButtons();
			buttons[MATRICOLA].reactToMouse(null);
		}
	}

	private void keyboardInputsFuoriSede(int key) {
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[LAVORATORE].getBounds().x, buttons[LAVORATORE].getBounds().y);
			buttonIndex = LAVORATORE;
			}
			else if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
				view.setCursorPosition(buttons[MATRICOLA].getBounds().x, buttons[MATRICOLA].getBounds().y);
				buttonIndex = MATRICOLA;
				}
			else if(key == KeyEvent.VK_ENTER) {
				resetButtons();
				buttons[FUORISEDE].reactToMouse(null);
			}
	}

	private void keyboardInputsLavoratore(int key) {
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			view.setCursorPosition(buttons[INDIETRO].getBounds().x, buttons[INDIETRO].getBounds().y);
			buttonIndex = INDIETRO;
			}
		else if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			view.setCursorPosition(buttons[FUORISEDE].getBounds().x, buttons[FUORISEDE].getBounds().y);
			buttonIndex = FUORISEDE;
			}
		else if(key == KeyEvent.VK_ENTER) {	
			resetButtons();
			buttons[LAVORATORE].reactToMouse(null);	
		}
	}

	private void keyboardInputsBack(int key) {
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			view.setCursorPosition(buttons[LAVORATORE].getBounds().x, buttons[LAVORATORE].getBounds().y);
			buttonIndex = LAVORATORE;
			}
		else if(key == KeyEvent.VK_ENTER) {
			resetButtons();
			view.changeGameState(buttons[INDIETRO].getButtonState());	
		}
	}

	public void setDifficulty(int i) {
		if(i == MATRICOLA)
			diffSetted = MATRICOLA;
		else if(i == FUORISEDE)
			diffSetted = FUORISEDE;
		else if (i == LAVORATORE)
			diffSetted = LAVORATORE;
	}

	// le soundbar cambiano quando il mouse viene trascinato dentro di loro


}
