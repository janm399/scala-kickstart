package org.cakesolutions.scala.intro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author janmachacek
 */
@Component
public class JSetterInjected {
	private JDependency dependency;

	@Autowired
	public void setDependency(JDependency dependency) {
		this.dependency = dependency;
	}
}
