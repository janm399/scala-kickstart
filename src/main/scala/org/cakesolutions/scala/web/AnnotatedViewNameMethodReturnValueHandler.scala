package org.cakesolutions.scala.web

import org.springframework.core.MethodParameter
import org.springframework.web.method.support.{ModelAndViewContainer, HandlerMethodReturnValueHandler}
import org.springframework.web.context.request.NativeWebRequest

/**
 * @author janmachacek
 */
class AnnotatedViewNameMethodReturnValueHandler extends HandlerMethodReturnValueHandler {

  def supportsReturnType(returnType: MethodParameter) = {
    returnType.getMethodAnnotation(classOf[ViewName]) != null
  }

  def handleReturnValue(returnValue: AnyRef, returnType: MethodParameter, mavContainer: ModelAndViewContainer, webRequest: NativeWebRequest) {
    def isRedirectViewName(viewName: String) = viewName.startsWith("redirect:")
    
    val annotation = returnType.getMethodAnnotation(classOf[ViewName])
    if (annotation != null) {
      val viewName = annotation.value
      mavContainer.setViewName(viewName)
      if (isRedirectViewName(viewName)) mavContainer.setRedirectModelScenario(true)
    }
  }
}
