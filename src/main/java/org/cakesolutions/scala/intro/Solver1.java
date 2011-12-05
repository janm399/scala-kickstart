package org.cakesolutions.scala.intro;

/**
 * @author janmachacek
 */
public class Solver1 {

	static double diff(double x) {
		double h = 0.001;
		return (f(x + h) - f(x)) / h;
	}

	static double solve() {
		double x = -1000000;
		double tolerance = 0.001;
		int maxCount = 1000;

		for (int i = 0; Math.abs(f(x)) > tolerance && i <= maxCount; i++) {
			x = x - f(x) / diff(x);
		}

		return x;
	}

	static double f(double x) {
		return x * x + x - 2;
	}

	public static void main(String[] args) {
		final double x = solve();
		System.out.println(x);
	}

}
