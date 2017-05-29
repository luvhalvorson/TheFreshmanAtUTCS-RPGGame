package state;

import java.util.ArrayList;

import demo.Game;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
		
		// 22 * 16(fixed) tiles
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
    public void update(long currentNanoTime) {
    		player.addCollisions(sideWalls);
    	
		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.UP) {
				System.out.print("up");
				player.setFaceUp();
				if(player.checkCollide() != true) {
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
    public void renderMap(int[] map, int mapWidth) {
    		
    		int tileRow = 1;
    		int tileColumn = 1;
    		for(int i = 0; i < map.length; i++) {
    			if (tileColumn > mapWidth) {
    				tileRow++;
    				tileColumn = 1;
    			}
    			if (map[i] != 0) {
    				drawTileFromTMX(map[i], worldOffsetX + (tileColumn - 1) * tilePixelSize, (tileRow - 1) * tilePixelSize);
    			}
    			tileColumn++; // move to the next column
    		}		
    }
    
    public void drawTileFromTMX(int sourceNum, double x, double y) {
    		int tileCount[] = new int[] {456, 400, 45};
    		int tileColumns[] = new int[] {8, 8, 9};
    		int row = 0;
    		int column = 0;
    		// 1~456
	    	if(sourceNum < tileCount[0]) {
			setCurrentTileSet(tileSet[0]);
			row = (sourceNum / tileColumns[0] ) + 1;
			column = sourceNum % tileColumns[0] ;
		}
	    	// 1~400
	    	else if(sourceNum < (tileCount[1] + tileCount[0])) {
	    		setCurrentTileSet(tileSet[1]);
	    		sourceNum -= tileCount[0];
	    		row = (sourceNum / tileColumns[1]) + 1 ;
			column = sourceNum % tileColumns[1] ;
	    	}
	    	// 1~45
	    	else if(sourceNum < (tileCount[1] + tileCount[0] + tileCount[2])) {
	    		setCurrentTileSet(tileSet[2]);
	    		sourceNum -= (tileCount[0] + tileCount[1]);
	    		// 878 -> 22 -> row = 22/9+1 =3 col = 4
	    		row = (sourceNum / tileColumns[2]) + 1 ;
	    		column = sourceNum % tileColumns[2] ;
	    	}
	    	gc.drawImage(currentTileSet, 32 * (column - 1), 32 * (row - 1), tilePixelSize, tilePixelSize, x, y, tilePixelSize, tilePixelSize);
	    	
    }
    // Draw a 32 x 32 tile from the current source tile set to the canvas
    // Also use to draw characters.
    public void drawTile(int sourceNum, double x, double y) {
		int row = sourceNum / 10;
		int column = sourceNum % 10;
		gc.drawImage(currentTileSet, 32 * (column - 1), 32 * (row - 1), tilePixelSize, tilePixelSize, x, y, tilePixelSize, tilePixelSize);
    }
    public void drawTile(int sourceNum, double x, double y, int tilePixelWidth, int tilePixelHeight) {
    		int row = sourceNum / 10;
		int column = sourceNum % 10;
		gc.drawImage(currentTileSet, tilePixelWidth * (column - 1), tilePixelHeight * (row - 1), tilePixelWidth, tilePixelHeight, x, y, tilePixelWidth, tilePixelHeight);
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
    
    public void setCurrentTileSet(Image tileSet) {
    		currentTileSet = tileSet;
    }
    public void drawDialogBox() {
    		// ================= (Bottom-left) Display the dialog box（之後改用有scrollbar的） =================
    		gc.setFill(Color.WHITE);
  		gc.fillRoundRect(5, 425, 350, 75, 10, 10);
    		// ==========================================================================
    }
    public void drawStatusBar() {
    		//  ================= (Top-left) Display the player status =================
		ImageView profile = new ImageView(new Image("resources/eyeball.jpg"));
		profile.setFitWidth(50);
		profile.setFitHeight(50);
		// Display player name and HP/MP bar
		Text playerName = new Text("lv1" + "  史上最可愛短短");
		playerName.setTranslateX(60);
		playerName.setTranslateY(10);
		playerName.setFill(Color.WHITE);
		playerName.setFont(Font.font("Times New Roman", FontWeight.THIN, 12));
		
		gc.setFill(Color.WHITE);
		//gc.fillText("lv1" + "  史上最可愛短短", 60, 10);
		gc.setFill(Color.RED);
		gc.fillRoundRect(60, 20, 100, 5, 10, 10);
		gc.setFill(Color.BLUE);
		gc.fillRoundRect(60, 40, 100, 5, 10, 10);
		// ==========================================================================

		// 如果有用到node 加起來
		Game.root.getChildren().addAll(profile, playerName);
    }
    
    public void onEnter() {
    		System.out.println("Map onEnter()");
    		drawStatusBar();
    		
    }

    public void onExit() {
    		scene.setOnKeyPressed(null);
    }
}