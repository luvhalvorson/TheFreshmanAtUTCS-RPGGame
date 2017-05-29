package demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import apple.laf.JRSUIConstants.State;
import javafx.animation.AnimationTimer;

import state.*;


public class Game extends Application {
	//set public static for test convenience 之後用到的話請傳到constructor
	public static Group root;
	Scene scene;
	public static Canvas canvas;
	GraphicsContext gc;
	
	@Override
    public void init() throws Exception {

        // initialize JavaFX nodes
        root = new Group();
        scene = new Scene(root);
        canvas = new Canvas(700, 512);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        
        // load CSS file
        scene.getStylesheets().add(Game.class.getResource("StartMenu.css").toExternalForm());
        scene.setFill(Color.BLACK);
        
        // add initial states
        StateStack.add("startmenu", new StartMenuState(root, scene));
        StateStack.add("map", new MapState(scene, gc));
		//StateStack.add("battle", new BattleState());

        // push the first state -- start menu
        StateStack.push("startmenu");
        
    }
	
	@Override
	public void start(Stage primaryStage) {
		
		new AnimationTimer() {
	        @Override
	        public void handle(long currentNanoTime) {
	        	//System.out.println("in handle");
	            StateStack.getCurrentState().update(currentNanoTime);
	            StateStack.getCurrentState().render();
	        }
	    }.start();
	    
	    //
	    primaryStage.setScene(scene);
        primaryStage.setTitle("The Freshman");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
