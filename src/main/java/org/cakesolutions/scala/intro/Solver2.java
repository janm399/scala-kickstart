package org.cakesolutions.scala.intro;

/**
 * @author janmachacek
 */
public class Solver2 {

	static abstract class Solver {
		abstract double f(double x);

		double diff(double x) {
			double h = 0.001;
			return (f(x + h) - f(x)) / h;
		}

		double solve() {
			double x = -1000000;
			double tolerance = 0.001;
			int maxCount = 1000;

			for (int i = 0; Math.abs(f(x)) > tolerance && i <= maxCount; i++) {
				x = x - f(x) / diff(x);
			}

			return x;
		}
	}

	public static void main(String[] args) {
		double x = new Solver() {
			@Override
			double f(double x) {
				return x * x + x - 2;
			}
		}.solve();

		System.out.println(x);
	}

}
