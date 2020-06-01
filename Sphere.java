import java.util.*;
import java.awt.*;
import java.lang.Math;

public class Sphere {
	public double xPos;
	public double yPos;
	public double xVelocity;
	public double yVelocity;
	public double xAccel;
	public double yAccel;
	public double mass;
	public double radius;
	public Color color;
	public ArrayList<Double> xTrail;
	public ArrayList<Double> yTrail;
	
	public Sphere(double xPos, double yPos, double xVelocity, double yVelocity, double xAccel, double yAccel, double mass, double radius) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.xAccel = xAccel;
		this.yAccel = yAccel;
		this.mass = mass;
		this.radius = radius;
		this.color = new Color((float) (Math.min(1, Math.pow(this.mass / Math.pow(10, 18), -0.2))), 0.0f, 0.0f);
		this.xTrail = new ArrayList<Double>();
		this.yTrail = new ArrayList<Double>();
	}
	public void updatePositionVelocity(double dt) {
		this.xVelocity += this.xAccel * dt;
		this.yVelocity += this.yAccel * dt;
		this.xPos += this.xVelocity * dt;
		this.yPos += this.yVelocity * dt;
	}
	public String toString() {
		return this.xPos + " " + this.yPos + " " + this.xVelocity + " " + this.yVelocity + " " + this.xAccel + " " + this.yAccel + " " + this.mass + " " + this.radius;
	}
}