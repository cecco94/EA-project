package view.main;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameWindow(GamePanel gp) {
		setTitle("ENGINEERING ADVENTURE");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		Image mouseIcon = null;
		try {
			mouseIcon = ImageIO.read(getClass().getResourceAsStream("/cursor.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(mouseIcon,new Point(gp.getX(),gp.getY()),"custom cursor"));
		getContentPane().add(gp);
		pack();
		setLocationRelativeTo(null);
		addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				//gamePanel.getGame().windowFocusLost();	resetta i booleandella direzione del personaggio
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}
}
