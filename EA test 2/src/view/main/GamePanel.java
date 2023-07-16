package view.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import view.inputs.KeyboardInputs;
import view.inputs.MouseInputs;

public class GamePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 20;
	public final static int TILES_IN_HEIGHT = 15;
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
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		view.prepareNewFrame(g2);
		g2.dispose();
	}
	
}
