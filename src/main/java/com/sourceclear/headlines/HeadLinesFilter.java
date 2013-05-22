/*
  Copyright (c) 2013  SourceClear Inc.
      
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
  http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. 
 */

package com.sourceclear.headlines;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class HeadLinesFilter implements Filter {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
     
  private static final String DEFAULT_CONFIG_NAME = "headlines.conf";
  
  private static final String CONFIG_PARAM_NAME = "configFile";
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\     
  
  private volatile ImmutableList<HeadLinesInjector> injectors = ImmutableList.of();
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //------------------------ Implements: Filter
  
  public void init(FilterConfig fc) throws ServletException {
    try {
      String configFileName = DEFAULT_CONFIG_NAME;
     
      String configParam = fc.getInitParameter(CONFIG_PARAM_NAME);
      if (configParam != null) {
        configFileName = configParam;
      }
      
      InputStream is = fc.getServletContext().getResourceAsStream("/WEB-INF/" + configFileName);
      InjectorServiceLoader loader = new InjectorServiceLoader();
      loader.load(is);
      injectors = loader.getInjectorList();
      
    } catch (Throwable t) {
      throw new ServletException("Couldn't initialize CspFilter", t);
    }
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {

    for (HeadLinesInjector injector : injectors) {
      try {
        injector.inject(HttpServletRequest.class.cast(request), HttpServletResponse.class.cast(response));
      } catch (Throwable t) {
        Logger.getLogger(getClass().getName()).log(Level.WARNING, "Couldn't inject header", t);
      }
    }
    
    fc.doFilter(request, response);    
  }

  public void destroy() {}
  
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     
}
