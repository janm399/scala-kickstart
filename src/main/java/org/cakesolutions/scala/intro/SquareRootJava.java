package org.cakesolutions.scala.intro;

/**
 * @author janmachacek
 */
public class SquareRootJava {

	private static double sqrt(double x) {
		double guess = 1;
		double tolerance = 0.001;
		
		while (Math.abs(guess * guess - x) > tolerance) {
			if (guess * guess > x) guess = guess / 2;
		}

		return guess;
	}

	public static void main(String[] args) {
		System.out.println(sqrt(2));
	}
	
}
