package view.playState.entityView.npcview;

import controller.main.Gamestate;
import view.IView;
import view.SoundManager;
import view.playState.entityView.EntityView;

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

}
