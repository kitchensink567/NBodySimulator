import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.awt.*;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		long start, stop;
		System.out.println("Type \"help\" to get a list of commands.");
		for (int i = 0; true; i++) {
			if (!Calculate.running)
				NBodyConsole.runCommand();
			else {
				start = System.currentTimeMillis();
				Calculate.iterateGravity(i);
				stop = System.currentTimeMillis();
				StdDraw.pause((int) (5 - start + stop));
			}
		}
	}
}