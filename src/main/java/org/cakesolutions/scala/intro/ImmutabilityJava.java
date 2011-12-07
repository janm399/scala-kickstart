package org.cakesolutions.scala.intro;

import java.util.ArrayList;
import java.util.List;

/**
 * @author janmachacek
 */
public class ImmutabilityJava {
	
	static class Container {
		private final int x;

		Container(int x) {
			this.x = x;
		}

		public int getX() {
			return x;
		}
	}

	public static void main(String[] args) {
		final Container c1 = new Container(1);
		final Container c2 = new Container(2);
		
		final List<Container> containers = new ArrayList<Container>();
		containers.add(c1);
		containers.add(c2);
		containers.remove(c1);

		System.out.println(containers.get(0).getX());
	}
}
