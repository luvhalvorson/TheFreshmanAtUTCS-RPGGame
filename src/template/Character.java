package template;

import java.util.ArrayList;

public class Character extends GameObject{
	
	private boolean isFacingUp;
	private boolean isFacingDown;
	private boolean isFacingLeft;
	private boolean isFacingRight;
	private boolean isCollided;
	
	private ArrayList<GameObject> collisions = new ArrayList<>();
	
	public Character(String name, double x, double y, double width, double height, double velocity) {
		super(name, x, y, width, height, velocity);
		isFacingRight = true;
	}
	
	public boolean isFacingUp() {
		return isFacingUp;
	}
	public boolean isFacingDown() {
		return isFacingDown;
	}
	public boolean isFacingLeft() {
		return isFacingLeft;
	}	
	public boolean isFacingRight() {
		return isFacingRight;
	}
	
	public void setFaceUp() {
		isFacingUp = true;
		isFacingDown = false;
		isFacingLeft = false;
		isFacingRight = false;
	}
	
	public void setFaceDown() {
		isFacingUp = false;
		isFacingDown = true;
		isFacingLeft = false;
		isFacingRight = false;
	}

	public void setFaceLeft() {
		isFacingUp = false;
		isFacingDown = false;
		isFacingLeft = true;
		isFacingRight = false;
	}

	public void setFaceRight() {
		isFacingUp = false;
		isFacingDown = false;
		isFacingLeft = false;
		isFacingRight = true;
	}

	
	public void addCollisions(GameObject obj) {
		collisions.add(obj);
	}
	
	public void addCollisions(ArrayList<GameObject> obj) {
		collisions.addAll(obj);
	}
	
	public boolean checkCollide() {
		for(GameObject obj : collisions) {
			if (this.intersects(obj)) {
				isCollided = true;
				break;
			};
		}
		return isCollided;
	}
}
