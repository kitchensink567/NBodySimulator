import java.util.*;
import java.lang.Math;
import java.awt.*;

/*public class NBodyGraphics {
	//1 pixel = 1 km
	public static DrawingPanel panel;
	public static Graphics2D g;
	public final static int RATIO = 1000;
	public final static int WIDTH = 1000;
	public final static int HEIGHT = 750;
	public static void init() {
		panel = new DrawingPanel(WIDTH, HEIGHT);
		g = panel.getGraphics();
		//g.setColor(Color.BLACK);
	}
	public static void redraw(ArrayList<Sphere> map) {
		panel.clear();
		Sphere s;
		int r;
		for (int i = 0; i < map.size(); i++) {
			s = map.get(i);
			g.setColor(s.color);
			System.out.println(s);
			r = (int) Math.round(s.radius / RATIO);
			g.fillOval((int) Math.round(s.xPos / RATIO) + WIDTH / 2 - r, HEIGHT / 2 - (int) Math.round(s.yPos / RATIO) - r, 2 * r, 2 * r);
			//g.fillOval(640, 480, 100, 100);
		}
	}
}*/

public class NBodyGraphics {
	public static boolean showPath = false;
	public final static int RATIO = 1000;
	public final static int WIDTH = 1000;
	public final static int HEIGHT = 750;
	public static void init() {
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(WIDTH, HEIGHT);
		StdDraw.setXscale(-WIDTH / 2, WIDTH / 2);
		StdDraw.setYscale(-HEIGHT / 2, HEIGHT / 2);
	}
	public static void redraw(ArrayList<Sphere> map, int iteration) {
		StdDraw.clear();
		Sphere s;
		for (int i = 0; i < map.size(); i++) {
			s = map.get(i);
			StdDraw.setPenColor(s.color);
			System.out.println(s);
			StdDraw.filledCircle(s.xPos / RATIO, s.yPos / RATIO, s.radius / RATIO);
			if (showPath) {
				for (int j = 0; j < s.xTrail.size(); j++) {
					StdDraw.filledCircle(s.xTrail.get(j) / RATIO, s.yTrail.get(j) / RATIO, 3);
				}
				if (iteration % 20 == 0) {
					map.get(i).xTrail.add(s.xPos);
					map.get(i).yTrail.add(s.yPos);
				}
			}
		}
		StdDraw.show();
	}
}