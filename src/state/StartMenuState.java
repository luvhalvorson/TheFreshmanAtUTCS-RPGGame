package state;

import java.io.File;
import java.net.URISyntaxException;

import demo.Game;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartMenuState implements IState {
	Group root;
	Scene scene;
	private int menuSelector;
	private Circle selectDot;
	
	MediaPlayer mainMusic;
	MediaPlayer menuSE1;
	MediaPlayer menuSE2;
	
	public StartMenuState(Group root2, Scene scene) {
		this.root = root2;
		this.scene = scene;
		menuSelector = 0;
	}
	
	@Override
	public void update(long currentNanoTime) {
		
		scene.setOnKeyPressed(e ->{
			
			if(e.getCode() == KeyCode.UP && menuSelector > 0) {
				menuSelector--;
				menuSE1.stop();
				menuSE1.play();
				
			}
			else if(e.getCode() == KeyCode.DOWN && menuSelector < 3) {
				menuSelector++;
				menuSE1.stop();
				menuSE1.play();
			}
			else if(e.getCode() == KeyCode.ENTER) {
				System.out.println("進來幹嘛還沒想好怎麼搞啦");
				switch (menuSelector) {
				case 0:
					StateStack.pop();
					StateStack.push("map");
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					//加個dialog？
					System.exit(0);
					break;
				default:
					break;
				}
			}
			else {
				menuSE2.stop();
				menuSE2.play();
			}
		});
		selectDot.setCenterY(241 + menuSelector * 40);
		
		
	}
	@Override
	public void render() {
		//this class doesn't use canvas
	}
	
	@Override
    public void onEnter() {
		System.out.println("StartMenu onEnter()");
		
		//title
		Text gameTitle = new Text(180, 110, "The Freshman");
		gameTitle.setId("title-text");
		Text second = new Text(450, 150, "- At UTCS");
		second.setId("subtitle-text");
		
		//menu
		int top = 250;
		int menuX = 290;
		int menuVInterval = 40;
		Text[] menuText = new Text[4];
		String[] menuStr = new String[] {"START", "LOAD", "OPTION", "BYE" };
		for(int i = 0; i < 4; i++) {
			menuText[i] = new Text(menuX, top + i * menuVInterval, menuStr[i]);
			menuText[i].setId("menu-text");
		}
		
		selectDot = new Circle((double)menuX - 20, (double)top - 9, 2.5);
		selectDot.setFill(Color.WHITESMOKE);
		
		// music 
		// this doesn't work Media media = new Media(getClass().getResource("src/music/Anthem.mp3").toURI().toString());       
		mainMusic = new MediaPlayer(new Media(new File("src/resources/music/Anthem.mp3").toURI().toString()));
		mainMusic.setVolume(0.5);
		mainMusic.play();
		
        menuSE1 = new MediaPlayer(new Media(new File("src/resources/soundEffects/menuP.wav").toURI().toString()));
        menuSE1.setVolume(0.35);
        
        menuSE2 = new MediaPlayer(new Media(new File("src/resources/soundEffects/menuN.wav").toURI().toString()));
        menuSE2.setVolume(0.9);
        
		root.getChildren().addAll(selectDot, gameTitle, second);
		root.getChildren().addAll(menuText);
    }
    
    @Override
    public void onExit() {
	    	System.out.println("exit menu");
	    	root.getChildren().clear();
	    	// Add Canvas back
	    	root.getChildren().add(Game.canvas);
	    	scene.setOnKeyPressed(null);
	    	mainMusic.stop();
    }

}