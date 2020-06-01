import java.util.*;
import java.lang.Math;

public class Calculate {
	public static ArrayList<Sphere> map = new ArrayList<Sphere>();
	public static int collisions = 0; //0 ignore collisions, 1 elastic, 2 inelastic, 3 merge
	public static double dt = 0.5;
	public static boolean running = false;
	private static final double G = 6.67 * Math.pow(10, -11);
	
	public static void iterateGravity(int iteration) {
		double forceX, forceY, distance, xdist, ydist;
		Sphere mass1, mass2;
		//set acceleration of all masses to 0 to "clear" the calculations from the last iteration
		for (int i = 0; i < map.size(); i++) {
			map.get(i).xAccel = 0;
			map.get(i).yAccel = 0;
		}
		//calculate the acceleration of each body
		for (int i = 0; i < map.size(); i++) {
			for (int j = i + 1; j < map.size(); j++) {
				mass1 = map.get(i);
				mass2 = map.get(j);
				xdist = Math.abs(mass1.xPos - mass2.xPos);
				ydist = Math.abs(mass1.yPos - mass2.yPos);
				distance = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
				//calculate force via newton's law of gravitation
				//multiply by xdist/distance or ydist/distance to obtain the x or y component
				forceX = G * mass1.mass * mass2.mass * xdist / Math.pow(distance, 3);
				forceY = G * mass1.mass * mass2.mass * ydist / Math.pow(distance, 3);
				//determine the direction of the force along each axis
				if (mass1.xPos > mass2.xPos) {
					map.get(i).xAccel -= forceX / mass1.mass;
					map.get(j).xAccel += forceX / mass2.mass;
				}
				else {
					map.get(i).xAccel += forceX / mass1.mass;
					map.get(j).xAccel -= forceX / mass2.mass;
				}
				if (mass1.yPos > mass2.yPos) {
					map.get(i).yAccel -= forceY / mass1.mass;
					map.get(j).yAccel += forceY / mass2.mass;
				}
				else {
					map.get(i).yAccel += forceY / mass1.mass;
					map.get(j).yAccel -= forceY / mass2.mass;
				}
			}
		}
		if (collisions == 1 || collisions == 2) {
			fixNoClip();
		}
		for (int i = 0; i < map.size(); i++) {
			map.get(i).updatePositionVelocity(dt);
		}
		checkCollisions();
		// occasionally redraw the map
		if (iteration % 10 == 0) {
			NBodyGraphics.redraw(map, iteration);
		}
	}
	public static void checkCollisions() {
		Sphere mass1, mass2;
		double x12, y12, x21, y21, vx12, vy12, vx21, vy21, distance, newXVelocity1, newYVelocity1, newXVelocity2, newYVelocity2;
		if (collisions == 0)
			return;
		if (collisions == 1) {
			for (int i = 0; i < map.size(); i++) {
				for (int j = i + 1; j < map.size(); j++) {
					mass1 = map.get(i);
					mass2 = map.get(j);
					x12 = mass1.xPos - mass2.xPos;
					y12 = mass1.yPos - mass2.yPos;
					x21 = mass2.xPos - mass1.xPos;
					y21 = mass2.yPos - mass1.yPos;
					vx12 = mass1.xVelocity - mass2.xVelocity;
					vy12 = mass1.yVelocity - mass2.yVelocity;
					vx21 = mass2.xVelocity - mass1.xVelocity;
					vy21 = mass2.yVelocity - mass1.yVelocity;
					distance = Math.sqrt(Math.pow(x21, 2) + Math.pow(y21, 2));
					if (distance <= mass1.radius + mass2.radius) {
						newXVelocity1 = mass1.xVelocity - (2 * mass2.mass / (mass1.mass + mass2.mass)) * (dotProduct(vx12, vy12, x12, y12) / (distance * distance)) * (x12);
						newYVelocity1 = mass1.yVelocity - (2 * mass2.mass / (mass1.mass + mass2.mass)) * (dotProduct(vx12, vy12, x12, y12) / (distance * distance)) * (y12);
						newXVelocity2 = mass2.xVelocity - (2 * mass1.mass / (mass1.mass + mass2.mass)) * (dotProduct(vx21, vy21, x21, y21) / (distance * distance)) * (x21);
						newYVelocity2 = mass2.yVelocity - (2 * mass1.mass / (mass1.mass + mass2.mass)) * (dotProduct(vx21, vy21, x21, y21) / (distance * distance)) * (y21);
						map.get(i).xVelocity = newXVelocity1;
						map.get(j).xVelocity = newXVelocity2;
						map.get(i).yVelocity = newYVelocity1;
						map.get(j).yVelocity = newYVelocity2;
					}
				}
			}
		}
		else if (collisions == 2) {
			for (int i = 0; i < map.size(); i++) {
				for (int j = i + 1; j < map.size(); j++) {
					mass1 = map.get(i);
					mass2 = map.get(j);
					x12 = Math.abs(mass1.xPos - mass2.xPos);
					y12 = Math.abs(mass1.yPos - mass2.yPos);
					distance = Math.sqrt(Math.pow(x12, 2) + Math.pow(y12, 2));
					if (distance <= mass1.radius + mass2.radius) {
						newXVelocity1 = (mass1.mass * mass1.xVelocity + mass2.mass * mass2.xVelocity) / (mass1.mass + mass2.mass);
						newYVelocity1 = (mass1.mass * mass1.yVelocity + mass2.mass * mass2.yVelocity) / (mass1.mass + mass2.mass);
						map.get(i).xVelocity = newXVelocity1;
						map.get(j).xVelocity = newXVelocity1;
						map.get(i).yVelocity = newYVelocity1;
						map.get(j).yVelocity = newYVelocity1;
					}
				}
			}
		}
		else {
			for (int i = 0; i < map.size(); i++) {
				for (int j = i + 1; j < map.size(); j++) {
					mass1 = map.get(i);
					mass2 = map.get(j);
					x12 = Math.abs(mass1.xPos - mass2.xPos);
					y12 = Math.abs(mass1.yPos - mass2.yPos);
					distance = Math.sqrt(Math.pow(x12, 2) + Math.pow(y12, 2));
					if (distance <= mass1.radius + mass2.radius) {
						merge(map.get(i), map.get(j));
					}
				}
			}
		}
	}
	public static double dotProduct(double a1, double b1, double a2, double b2) {
		return a1 * a2 + b1 * b2;
	}
	public static void fixNoClip() { //if two bodies are intersecting, then move the smaller one outside of the other one.
		Sphere mass1, mass2;
		double distance, xdist, ydist;
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.size(); j++) {
				if (i == j)
					continue;
				mass1 = map.get(i);
				mass2 = map.get(j);
				xdist = mass2.xPos - mass1.xPos;
				ydist = mass2.yPos - mass1.yPos;
				distance = Math.sqrt(Math.pow(xdist, 2) + Math.pow(ydist, 2));
				if (distance < mass1.radius + mass2.radius - 0.01) {
					/*p1 = Math.sqrt(Math.pow(mass1.xVelocity, 2) + Math.pow(mass1.yVelocity, 2)) * mass1.mass;*/
					if (mass1.radius <= mass2.radius) {
						map.get(i).xPos = mass2.xPos - (mass1.radius + mass2.radius) * xdist / distance;
						map.get(i).yPos = mass2.yPos - (mass1.radius + mass2.radius) * ydist / distance;
					}
					else {
						map.get(j).xPos = mass1.xPos + (mass1.radius + mass2.radius) * xdist / distance;
						map.get(j).yPos = mass1.yPos + (mass1.radius + mass2.radius) * ydist / distance;
					}
				}
			}
		}
	}
	public static void merge(Sphere sphere1, Sphere sphere2) {
		map.remove(sphere1);
		map.remove(sphere2);
		double r = Math.pow(Math.pow(sphere1.radius, 3) + Math.pow(sphere2.radius, 3), 0.3333333333);
		double m = sphere1.mass + sphere2.mass;
		double x = (sphere1.mass * sphere1.xPos + sphere2.mass * sphere2.xPos) / (sphere1.mass + sphere2.mass);
		double y = (sphere1.mass * sphere1.yPos + sphere2.mass * sphere2.yPos) / (sphere1.mass + sphere2.mass);
		double vx = (sphere1.mass * sphere1.xVelocity + sphere2.mass * sphere2.xVelocity) / (sphere1.mass + sphere2.mass);
		double vy = (sphere1.mass * sphere1.yVelocity + sphere2.mass * sphere2.yVelocity) / (sphere1.mass + sphere2.mass);
		map.add(new Sphere(x, y, vx, vy, 0, 0, m, r));
	}
}