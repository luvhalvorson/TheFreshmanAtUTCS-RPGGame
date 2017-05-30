package state;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import demo.Game;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import template.Player;
import template.GameObject;

public class MapState implements IState {
	
	Scene scene;
	GraphicsContext gc;
    
	Image[] tileSet = new Image[10];
	Image currentTileSet;
	int tilePixelSize = 32;
	int[] groundLayer;
	int[] put;
	int[] top;
	
	ArrayList<GameObject> sideWalls = new ArrayList<>();
	
	Player player;
	int playerPixelWidth = 48;
	int playerPixelHeight = 64;
	int spriteChooser[] = new int[] {1, 1, 1, 1};
	double worldOffsetX;
	double worldOffsetY;
	
	ArrayList<String> text;
	
	MediaPlayer mainMusic;
	public MapState(Scene scene, GraphicsContext gc) {
		this.gc = gc;
		this.scene = scene;
		loadMap();
		player = new Player("史上最可愛短短", 300, 200, playerPixelWidth, playerPixelHeight, 10);
	}
    public void loadMap() {
    		tileSet[0] = new Image("resources/tileSet/School-E.png");
		tileSet[1] = new Image("resources/tileSet/School-I.png");
		tileSet[2] = new Image("resources/tileSet/construct1.png");
		tileSet[3] = new Image("resources/tileSet/construct2.png");
		
		// 30 * 16(fixed) tiles
		groundLayer = new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,251,251,251,251,251,251,1,1,1,1,1,1,251,251,251,251,251,251,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,1,1,1,
				1,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,1,1,1,
				1,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,68,69,69,70,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,76,77,77,78,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		put = new int[] {0,0,241,242,257,258,260,260,260,260,258,259,242,242,287,287,242,242,257,258,260,260,260,260,258,259,242,0,0,0,
				0,0,241,242,269,269,269,269,269,269,269,270,242,242,287,287,241,242,269,269,269,269,269,269,269,270,242,0,0,0,
				0,0,241,242,259,259,260,260,260,260,259,259,242,242,287,287,241,242,257,258,260,260,260,260,258,259,242,0,0,0,
				0,0,241,242,283,284,265,265,265,265,265,266,241,242,281,282,241,242,265,265,265,265,265,266,283,284,242,0,0,0,
				0,0,242,242,291,292,273,273,273,273,273,274,242,242,289,290,241,242,273,273,273,273,273,274,291,292,242,0,0,0,
				0,0,299,299,299,299,299,299,299,299,299,299,0,0,279,280,0,0,299,299,299,299,299,299,299,299,299,299,0,0,
				0,0,287,295,287,295,287,295,287,295,287,295,0,0,0,0,0,0,287,295,287,295,287,295,287,295,287,295,0,0,
				0,0,295,287,295,287,295,287,295,287,295,287,0,0,0,0,0,0,295,287,295,287,295,287,295,287,295,287,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,60,61,61,62,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		top = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,277,278,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
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
    		Collections.addAll(sideWalls, new GameObject(65.7019, 226.405, 318.743, 30.1873));
    		player.getGameObject().addCollisions(sideWalls);
    		boolean isCollided;
    		for(GameObject obj : sideWalls) {
    			if (player.intersects(obj)) {
    				isCollided = true;
    				System.out.print("f\n");
    				break;
    			}
    		}
    		
		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.UP) {
				player.setFaceUp();
				
				if(player.getGameObject().checkCollide() != true) {
					//System.out.print(player.getGameObject().checkCollide());
					player.setY(player.getY() - player.getVelocity());
				}
				spriteChooser[0] += spriteChooser[0];
				if(spriteChooser[0] > 4) {
					spriteChooser[0] = 1;
				}
			}
			if(e.getCode() == KeyCode.DOWN) {
				System.out.print("down");
				player.setFaceDown();
				if(player.checkCollide() != true) {
					player.setY(player.getY() + player.getVelocity());
				}
				spriteChooser[1] += spriteChooser[1];
				if(spriteChooser[1] > 4) {
					spriteChooser[1] = 1;
				}
			}
			if(e.getCode() == KeyCode.LEFT) {
				System.out.print("不能向左喔");
				player.setFaceLeft();
				//worldOffsetX += player.getVelocity();
				spriteChooser[2] += spriteChooser[2];
				if(spriteChooser[2] > 4) {
					spriteChooser[2] = 1;
				}
			}
			if(e.getCode() == KeyCode.RIGHT) {
				System.out.print("r");
				player.setFaceRight();
				//player.setX(player.getX() + player.getVelocity());
				if(player.checkCollide() != true) {
					worldOffsetX -= player.getVelocity();
				}
				spriteChooser[3] += spriteChooser[3];
				if(spriteChooser[3] > 4) {
					spriteChooser[3] = 1;
				}
			}
		});
    }
    
    @Override
    public void render() {
    		gc.clearRect(0, 0, 700, 512);
    		renderMap(groundLayer, 30);
    		renderMap(put, 30);
    		renderMap(top, 30);
    		drawPlayer();
    		drawStatusBar();
    		drawDialogBox();
    }
    
    // Draw a tile map from the top left
    public void renderMap(int[] mapLayer, int mapWidth) {
    		
    		int tileRow = 1;
    		int tileColumn = 1;
    		for(int i = 0; i < mapLayer.length; i++) {
    			if (tileColumn > mapWidth) {
    				tileRow++;
    				tileColumn = 1;
    			}
    			if (mapLayer[i] != 0) {
    				drawTileFromTMX(mapLayer[i], worldOffsetX + (tileColumn - 1) * tilePixelSize, (tileRow - 1) * tilePixelSize);
    			}
    			tileColumn++; // move to the next column
    		}		
    }
    
    // Draw a 32 x 32 tile from the current source tile set to the canvas.
    // sourceNum format : row + "" + column
    public void drawTile(int sourceNum, double x, double y) {
		int row = sourceNum / 10;
		int column = sourceNum % 10;
		gc.drawImage(currentTileSet, 32 * (column - 1), 32 * (row - 1), tilePixelSize, tilePixelSize, x, y, tilePixelSize, tilePixelSize);
    }
    // Draw a Preferred Size of tile from the current source tile set to the canvas.
    // sourceNum format : row + "" + column
    // Also use to draw characters.
    public void drawTile(int sourceNum, double x, double y, int tilePixelWidth, int tilePixelHeight) {
    		int row = sourceNum / 10;
		int column = sourceNum % 10;
		gc.drawImage(currentTileSet, tilePixelWidth * (column - 1), tilePixelHeight * (row - 1), tilePixelWidth, tilePixelHeight, x, y, tilePixelWidth, tilePixelHeight);
    }
    // Same as drawTile function, but read global tile id 
    // (Depends on my tile set order, tile set max columns, etc.)
    // I use Tiled to create my TMX map. For more information, read Tiled documentation.
    public void drawTileFromTMX(int globalTileID, double x, double y) {
    		int tileCount[] = new int[] {456, 400, 45};
    		int tileColumns[] = new int[] {8, 8, 9};
    		int row = 0;
    		int column = 0;
    		// 1~456
	    	if(globalTileID < tileCount[0]) {
			setCurrentTileSet(tileSet[0]);
			row = (globalTileID / tileColumns[0] ) + 1;
			column = globalTileID % tileColumns[0] ;
		}
	    	// 1~400
	    	else if(globalTileID < (tileCount[1] + tileCount[0])) {
	    		setCurrentTileSet(tileSet[1]);
	    		globalTileID -= tileCount[0];
	    		row = (globalTileID / tileColumns[1]) + 1 ;
			column = globalTileID % tileColumns[1] ;
	    	}
	    	// 1~45
	    	else if(globalTileID < (tileCount[1] + tileCount[0] + tileCount[2])) {
	    		setCurrentTileSet(tileSet[2]);
	    		globalTileID -= (tileCount[0] + tileCount[1]);
	    		row = (globalTileID / tileColumns[2]) + 1 ;
	    		column = globalTileID % tileColumns[2] ;
	    	}
	    	gc.drawImage(currentTileSet, 32 * (column - 1), 32 * (row - 1), tilePixelSize, tilePixelSize, x, y, tilePixelSize, tilePixelSize);
    }
    
    public void setCurrentTileSet(Image tileSet) {
		currentTileSet = tileSet;
    }
    
    public void drawPlayer() {
	    	setCurrentTileSet(player.getImage());
	    	
	    	if(player.isFacingUp()) {
	    		if(spriteChooser[0] == 1) {
	    			drawTile(41, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[0] == 2) {
	    	    		drawTile(42, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[0] == 3){
	    			drawTile(43, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[0] == 4){
	    			drawTile(44, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    	}
	    
	    	if(player.isFacingDown()) {
	    		if(spriteChooser[1] == 1) {
	    			drawTile(11, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[1] == 2) {
	    	    		drawTile(12, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[1] == 3){
	    			drawTile(13, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[1] == 4){
	    			drawTile(14, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    	}
	    	if(player.isFacingLeft()) {
	    		if(spriteChooser[2] == 1) {
	    			drawTile(21, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[2] == 2) {
	    	    		drawTile(22, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[2] == 3){
	    			drawTile(23, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[2] == 4){
	    			drawTile(24, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    	}
	    	if(player.isFacingRight()) {
	    		if(spriteChooser[3] == 1) {
	    			drawTile(31, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[3] == 2) {
	    	    		drawTile(32, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[3] == 3){
	    			drawTile(33, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    		else if (spriteChooser[3] == 4){
	    			drawTile(34, player.getX(), player.getY(), playerPixelWidth, playerPixelHeight);
	    		}
	    	}
    }
    
    
    	
    // ================= (Bottom-left) Display the dialog box =================
    public void drawDialogBox() {
    		
    		gc.setFill(Color.WHITE);
    		gc.setGlobalAlpha(0.45);
  		gc.fillRoundRect(7, 425, 350, 75, 10, 10);
  		
  		gc.setFill(Color.BLACK);
  		gc.setGlobalAlpha(0.65);
  		
  		/* Using String...
  		String[] text = new String[] {"短短最可愛了", "哎呀怎麼那麼可愛", "完蛋了你要被短短可愛死了", "你掛了"};
  		for(int i = 0; i < text.length; i++) {
			if(i == text.length - 1) gc.setGlobalAlpha(1.0);
			gc.fillText(text[i], 15 , 442 + i * 17);
		}*/
  		
  		for(int i = 0; i < text.size(); i++) {
  			// for last String, set back the alpha to 1
  			if(i == text.size() - 1) gc.setGlobalAlpha(1.0);
  			gc.fillText(text.get(i), 15 , 442 + i * 17);
  		}
  		
    }
    
    //  ================= (Top-left) Display the player status =================
    public void drawStatusBar() {
    		
		ImageView profile = new ImageView(new Image("resources/eyeball.jpg"));
		profile.setFitWidth(50);
		profile.setFitHeight(50);
		// Display player name and HP/MP bar
		Text playerName = new Text("lv1" + "  史上最可愛短短");
		playerName.setTranslateX(60);
		playerName.setTranslateY(12);
		playerName.setFill(Color.WHITE);
		playerName.setFont(Font.font("Times New Roman", FontWeight.THIN, 12));
		
		gc.setFill(Color.WHITE);
		//gc.fillText("lv1" + "  史上最可愛短短", 60, 10);
		gc.setFill(Color.RED);
		gc.fillRoundRect(60, 20, 100, 5, 10, 10);
		gc.setFill(Color.BLUE);
		gc.fillRoundRect(60, 40, 100, 5, 10, 10);
		// 如果有用到node 加起來
		Game.root.getChildren().addAll(profile, playerName);
    }
    
    @Override
    public void onEnter() {
    		System.out.println("Map onEnter()");
    		text = new ArrayList<>();
      	Collections.addAll(text, "短短最可愛了", "哎呀怎麼那麼可愛", "完蛋了你要被短短可愛死了", "你掛了");
      	
      	// music
      	Media media = new Media(new File("src/resources/music/WindsOfStories.mp3").toURI().toString());
      	mainMusic = new MediaPlayer(media);
      	mainMusic.setVolume(0.65);
      	mainMusic.play();
    }
    
    @Override
    public void onExit() {
    		scene.setOnKeyPressed(null);
    		mainMusic.stop();
    }
}