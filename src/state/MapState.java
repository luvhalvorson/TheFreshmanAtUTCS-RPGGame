package state;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MapState implements IState {
	
	Scene scene;
	GraphicsContext gc;
	
	public MapState(Scene scene, GraphicsContext gc) {
		this.gc = gc;
		this.scene = scene;
	}
    
    public void update(long currentNanoTime) {
        //nothing
    }

    public void render() {
        //nothing
    }

    public void onEnter() {
    	System.out.println("Map onEnter()");
    		gc.setFill(Color.WHITE);
    		gc.fillRoundRect(50, 50, 50, 50, 10, 10);
    }

    public void onExit() {
    		scene.setOnKeyPressed(null);
    }
}