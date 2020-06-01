import java.util.*;
import java.lang.Math;

public class NBodyConsole {
	/*
	setdt
	createSphere <x> <y> <xv> <yv> <mass> <radius>
	showPaths
	collisions <value>
	random <spheres>
	start
	*/
	public static void runCommand() {
		Scanner s = new Scanner(System.in);
		String input = s.nextLine();
		String[] command = input.split(" ", 0);
		if (command[0].equals("createSphere")) {
			Calculate.map.add(new Sphere(Double.valueOf(command[1]), Double.valueOf(command[2]), Double.valueOf(command[3]), Double.valueOf(command[4]), 0, 0, 1000000000 * Double.valueOf(command[5]), Double.valueOf(command[6])));
			System.out.println("Sphere added");
		}
		else if (command[0].equals("start")) {
			Calculate.running = true;
			NBodyGraphics.init();
		}
		else if (command[0].equals("collisions")) {
			Calculate.collisions = Integer.valueOf(command[1]);
			if (command[1].equals("0"))
				System.out.println("Ignoring collisions");
			else if (command[1].equals("1"))
				System.out.println("Collisions are now elastic");
			else if (command[1].equals("2"))
				System.out.println("Collisions are now inelastic");
			else if (command[1].equals("3"))
				System.out.println("Collisions will now merge");
			else
				System.out.println("Invalid argument (must be an integer between 0 and 3)");
		}
		else if (command[0].equals("random")) {
			for (int i = 0; i < Integer.valueOf(command[1]); i++) {
				Calculate.map.add(new Sphere(0.5 * NBodyGraphics.WIDTH * NBodyGraphics.RATIO * (Math.random() - 0.5), 0.5 * NBodyGraphics.HEIGHT * NBodyGraphics.RATIO * (Math.random() - 0.5), 500 * (Math.random() - 0.5), 500 * (Math.random() - 0.5), 0, 0, Math.pow(10, 18 + 4.5 * Math.random()) * Math.random(), 50000 * Math.random()));
			}
			System.out.println(Integer.valueOf(command[1]) + " spheres added");
		}
		else if (command[0].equals("setdt")) {
			Calculate.dt = Double.valueOf(command[1]);
			System.out.println("Each iteration now represents " + Double.valueOf(command[1]) + " seconds.");
		}
		else if (command[0].equals("showPaths")) {
			if (command[1].equals("1")) {
				NBodyGraphics.showPath = true;
				System.out.println("Now showing paths");
			}
			else if (command[1].equals("0")) {
				NBodyGraphics.showPath = false;
				System.out.println("Paths are now hidden");
			}
			else
				System.out.println("Invalid argument (must be either 0 or 1)");
		}
		else if (command[0].equals("help")) {
			System.out.println("List of commands: ");
			System.out.println("createSphere - creates a sphere with the given properties. Usage: createSphere <xpos> <ypos> <xvelocity> <yvelocity> <mass> <radius>");
			System.out.println("showPaths - whether the simulator will draw the paths of all spheres. Usage: showPaths <value> (0 to hide paths, 1 to show paths)");
			System.out.println("collisions - decides what will happen when two spheres collide. Usage: collisions <value> (0 for no collisions, 1 for elastic collisions, 2 for inelastic collisions, 3 for spheres to merge upon colliding");
			System.out.println("clear - removes all spheres on the map. Usage: clear");
			System.out.println("random - generates a number of spheres with random properties. Usage: random <number of spheres>");
			System.out.println("start - starts the simulation. Usage: start");
			System.out.println("setdt - sets the amount of simulated time that passes between each iteration. Lower values are slower, but more accurate, while higher values are faster, but less accurate. Interval is 0.5 by default. Usage: setdt <value>");
		}
		else if (command[0].equals("clear")) {
			Calculate.map = new ArrayList<Sphere>();
		}
		else
			System.out.println("Invalid command");
	}
}