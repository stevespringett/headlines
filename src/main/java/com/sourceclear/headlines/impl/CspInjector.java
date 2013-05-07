package com.sourceclear.headlines.impl;

import com.google.common.collect.ImmutableList;
import com.sourceclear.headlines.AbstractHeaderLinesInjector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public final class CspInjector extends AbstractHeaderLinesInjector<CspConfig> {

  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private static final ImmutableList<String> HEADER_LIST =ImmutableList.of(
      "Content-Security-Policy", // W3 Standard
      "X-Content-Security-Policy", // IE 10
      "X-Webkit-CSP"); // Chrome < 25, Safari
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\   
  
  private volatile CspDirectives cspDirectives;
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
    
  public CspInjector() {
    super.setConfig(new CspConfig());
    cspDirectives = CspDirectives.build(getConfig());
  }
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //------------------------ Implements:
  
  //------------------------ Overrides: AbstractHeaderLinesInjector
  
  @Override
  public void setConfig(CspConfig config) {
    cspDirectives = CspDirectives.build(config);
    super.setConfig(config);    
  }
  
  public void inject(HttpServletRequest request, HttpServletResponse response) {
    
    CspConfig config = getConfig();
    
    if (config.isEnabled() && !cspDirectives.getCspDirectives().isEmpty()) {
      for (String header : HEADER_LIST) {
        response.setHeader(header, cspDirectives.getCspDirectives());
      }
    }
    
    if (config.isEnabled() && !cspDirectives.getReportCspDirectives().isEmpty()) {
      response.setHeader("Content-Security-Policy-Report-Only", cspDirectives.getReportCspDirectives());        
    }    
  }
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     



}
