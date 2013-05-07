package com.sourceclear.headlines;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public interface HeadLinesInjector<T> {

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  public Class getConfigClass();
  
  public void setConfig(T config);
  
  public void inject(HttpServletRequest request, HttpServletResponse response);
  
}
