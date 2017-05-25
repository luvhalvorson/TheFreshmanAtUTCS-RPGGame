package state;

import javafx.scene.Scene;

public class BattleState implements IState {
	Scene scene;
	
	public BattleState(Scene scene) {
		this.scene = scene;
	}
	
	public void update(long currentNanoTime) {
		//nothing
	}

    public void render() {
    	//nothing
    }

    public void onEnter() {
    	//nothing
    }

    public void onExit() {
    		scene.setOnKeyPressed(null);
    }
}