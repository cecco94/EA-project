package view.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import view.inputs.KeyboardInputs;
import view.inputs.MouseInputs;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	//qui vengono definite le dimensioni del gioco. non sulla finestra, perchè sulla finestra c'è quella maledetta barra coi tasti per 
	//chiudere, riurre a icona etc che rende tutto più complicato per capire le reali dimensioni
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static int TILES_IN_WIDTH = 20;
	public final static int TILES_IN_HEIGHT = 15;
	
	//sul mio pc scale = 1.5, viene tutto scalato in modo da far entrare  la finestra di gioco anche in schermi più piccoli
	static float computerScreenHeight = (float) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public final static float SCALE = computerScreenHeight/576;	
	
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	private IView view;

	public GamePanel(IView view, MouseInputs mi) {
		this.view = view;
		this.setDoubleBuffered(true);   	//improve rendering performance
		setPanelSize();
		addMouseListener(mi);
		addMouseMotionListener(mi);
		addKeyListener(new KeyboardInputs(view));
	}

	private void setPanelSize() {
		setBackground(Color.black);
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setSize(size);
		setPreferredSize(size);
	}
	
	//iview usa l'ambiente grafico del pannello, g, per disegnare il frame successivo, poi g si chiude per risparmiare risorse
	public void paintComponent(Graphics g) {	
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);			//migliora le prestazioni
		view.prepareNewFrame(g2);
		g2.dispose();
	}
	
}
