package org.cakesolutions.scala.intro;

/**
 * @author janmachacek
 */
public class Solver3 {

	static abstract class Solver {
		abstract double f(double x);

		double diff(double x) {
			double h = 0.001;
			return (f(x + h) - f(x)) / h;
		}

		Double solve() {
			double x = -1000000;
			double tolerance = 0.001;
			int maxCount = 1000;
			int i;

			for (i = 0; Math.abs(f(x)) > tolerance && i <= maxCount; i++) {
				x = x - f(x) / diff(x);
			}

			return i == maxCount ? null : x;
		}
	}

	public static void main(String[] args) {
		Double x = new Solver() {
			@Override
			double f(double x) {
				return x * x + x - 2;
			}
		}.solve();

		System.out.println(x);
	}

}
