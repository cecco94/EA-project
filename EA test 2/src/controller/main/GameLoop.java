package controller.main;

import controller.IController;
import model.IModel;
import view.IView;
import view.gameBegin.SplashScreenGame;

// classe contenente il game loop, il ciclo di update e repaint del gioco
public class GameLoop implements Runnable {
	private SplashScreenGame caricamento;
	
	// gioco gira su un thread diverso per gestirlo meglio in caso di crush
	private Thread gameThread;
	private int FPS_SET = 120;
	private int UPS_SET = 200;
	
	private IView view;
	private IController controller;
	private IModel model;

	
	public GameLoop() {
		initClasses();
		startGameLoop();
	}

	//inizializizza le interfacce Model,View,Controller
	private void initClasses() {
		
		caricamento = new SplashScreenGame();	
		controller = new IController();
		caricamento.showProgress(30);
		
		model = new IModel(controller);
		controller.setModel(model);
		caricamento.showProgress(60);
		
		view = new IView(controller, model);
		controller.setView(view);
		model.addView(view);
		caricamento.dispose();
	}

	//fa partire il gioco su un nuovo thread
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}


	@Override
	public void run() {		

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

//		int frames = 0;
//		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (gameThread != null) {
			long currentTime = System.nanoTime();

			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
			//	updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				render();
			//	frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
			//	System.out.println("FPS: " + frames + " | UPS: " + updates);  per debugging
//				frames = 0;
//				updates = 0;

			}
		}
	}

	private void render() {
		view.draw();	
	}

	private void update() {
		controller.updateGame();	
	}
	
}
