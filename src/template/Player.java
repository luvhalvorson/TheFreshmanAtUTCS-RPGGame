package template;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Player extends Character {
	double x;
	double y;
	double width;
	double height;
	int hp;
	int mp;
	int lv;
	int exp;
	
	Image playerImg;
	
	
	public Player(String name, double x, double y, double width, double height, double velocity) {
		super(name, x, y, width, height, velocity);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setImage("resources/character/Lama01.png");
	}
	public GameObject getGameObject() {
		return new GameObject(x, y, width, height);
	}
	// 或許有一天我的玩家會需要換個臉...或許？
	public void setImage(String playerImgUrl) {
		playerImg = new Image(playerImgUrl);
	}
	
	public Image getImage() {
		return playerImg;
	}
	
	/*public Rectangle2D getBounds() {
		return new Rectangle2D(getX(), getY(), getWidth(), getHeight());
	}
	
	public boolean intersects(GameObject obj) {
		return  obj.getBounds().intersects(this.getBounds());
	}*/
}
