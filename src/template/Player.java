package template;

import javafx.scene.image.Image;

public class Player extends Character {
	
	int hp;
	int mp;
	int lv;
	int exp;
	
	Image playerImg;
	
	
	public Player(String name, double x, double y, double width, double height, double velocity) {
		super(name, x, y, width, height, velocity);
		setImage("resources/character/Lama01.png");
	}
	
	// 或許有一天我的玩家會需要換個臉...或許？
	public void setImage(String playerImgUrl) {
		playerImg = new Image(playerImgUrl);
	}
	
	public Image getImage() {
		return playerImg;
	}
}
