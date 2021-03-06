package template;

import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;

public class GameObject extends Rectangle{
	
	private String name;
	private double x;
	private double y;
	private double width;
	private double height;
	private double velocity;
	
	private ArrayList<GameObject> collisions = new ArrayList<>();
	
	public GameObject(double x, double y, double width, double height) {

		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		
	}
	
	public GameObject(String name, double x, double y, double width, double height, double velocity) {
		
		setName(name);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setVelocity(velocity);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public String getName() {
		return name;
	}
	
	public double getVelocity() {
		return velocity;
	}
	
	public Rectangle2D getBounds() {
		
		return new Rectangle2D(x, y, width, height);
	}
	
	public boolean intersects(GameObject obj) {
		return  obj.getBounds().intersects(this.getBounds());
	}
	
	public void addCollisions(GameObject obj) {
		collisions.add(obj);
	}
	
	public void addCollisions(ArrayList<GameObject> obj) {
		collisions.addAll(obj);
	}
	
	public boolean checkCollide() {
		System.out.print("che");
		boolean isCollided = false;
		for(GameObject obj : collisions) {
			if (this.intersects(obj)) {
				isCollided = true;
				break;
			}
		}
		return isCollided;
	}
}
