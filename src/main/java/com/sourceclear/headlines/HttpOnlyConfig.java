package com.sourceclear.headlines;

import com.google.common.collect.ImmutableList;

/**
 *
 */
public class HttpOnlyConfig {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
  private volatile boolean enabled = true;  
  
  private volatile ImmutableList<String> sessionPatterns;
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  public HttpOnlyConfig() {
    sessionPatterns = ImmutableList.of("JSESSIONID", "jSessionId", "jSessionID");
  }
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //------------------------ Implements:
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     
  
  public boolean isEnabled() {
    return enabled;
  }
  
  public ImmutableList<String> getSessionPatterns() {
    return sessionPatterns; 
  }
}
