package state;

import demo.Game;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartMenuState implements IState {
	Group root;
	Scene scene;
	private int menuSelector;
	private Circle selectDot;
	
	public StartMenuState(Group root2, Scene scene) {
		this.root = root2;
		this.scene = scene;
		menuSelector = 0;
	}
	
	@Override
	public void update(long currentNanoTime) {
		scene.setOnMouseClicked(e -> {
		        String msg =
		          "\n這個物件: ("       + e.getX()      + ",  "       + e.getY()       + ")\n " +
		          "scene: ("  + e.getSceneX() + ",  "  + e.getSceneY()  + ")\n " +
		          "screen: (" + e.getScreenX()+ ",  " + e.getScreenY() + ")";
		        System.out.println(msg);
		    });
		scene.setOnKeyPressed(e ->{
			if(e.getCode() == KeyCode.UP && menuSelector > 0) {
				menuSelector--;
			}
			if(e.getCode() == KeyCode.DOWN && menuSelector < 3) {
				menuSelector++;
			}
			if(e.getCode() == KeyCode.ENTER) {
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
		//selectDot.setOpacity(0.5);
		
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
    }

}