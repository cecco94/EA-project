package view.playState.entityView.npcview;

import java.awt.Graphics2D;

import controller.main.Gamestate;
import view.IView;
import view.SoundManager;
import view.playState.entityView.EntityView;
import view.playState.entityView.PlayerView;

public abstract class NPCView extends EntityView {

	//per i dialoghi, ma non tutte le entità hanno dei dialoghi (gatti, player..) 
	protected int dialogueIndex;
	protected String[] dialogues;
	
	public NPCView(IView v, int index) {
		super(v, index);
		setDialogues();
	}
	
	protected abstract void setDialogues();

	public String getCurrentDialogue() {
		return dialogues[dialogueIndex];
	}
	
	// dopo che ha detto una frase, va alla frase successiva, se sono finite le frasi esce dallo stato dialogue
	public void nextDialogueLine() {
		view.playSE(SoundManager.CAFFE);

		dialogueIndex++;
		if(dialogueIndex >= dialogues.length) {
			dialogueIndex--;
			view.changeGameState(Gamestate.PLAYING);
		}
	}
	
	@Override
	public void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap) {
		
		animationCounter++;
		setAction(this);
		setDirection(this);
		
		if (animationCounter > animationSpeed) {
			numSprite ++;	
			
			if(numSprite >= getAnimationLenght())
				numSprite = 0;	
			
			animationCounter = 0;
		}
		
		//distanza nella mappa tra il punto in alto a sinistra della hitbox 
		//del player ed il punto in alto a sinistra della hitbox del gatto
		int distanceX = xPlayerMap - xPosMapForSort + xOffset;
		int distanceY = yPlayerMap - yPosMapForSort + yOffset;
		
		//ci serve un offset perchè la distanza del gatto nella mappa rispetto al player è riferita al punto in
		//alto a sinistra della hitbox. Per mantenere la stessa distanza, dobbiamo aggiungere questo offset
		int xPosOnScreen = PlayerView.xOnScreen - distanceX - xOffset + PlayerView.getXOffset();
		int yPosOnScreen = PlayerView.yOnScreen - distanceY - yOffset + PlayerView.getYOffset();
		
		try {
			g2.drawImage(animation[0][currentAction][currentDirection][numSprite], xPosOnScreen, yPosOnScreen, null);
		}
		catch (ArrayIndexOutOfBoundsException a) {
			a.printStackTrace();
		}
	}
	
	protected abstract int getAnimationLenght();
	
}
