package org.cakesolutions.scala.intro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author janmachacek
 */
@Component
public class JConstructorInjected {
	private final JDependency dependency;

	@Autowired
	public JConstructorInjected(JDependency dependency) {
		this.dependency = dependency;
	}
}
