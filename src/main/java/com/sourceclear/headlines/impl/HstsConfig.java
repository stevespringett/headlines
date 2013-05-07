package com.sourceclear.headlines.impl;

import javax.annotation.concurrent.Immutable;

/**
 *
 */
@Immutable
public final class HstsConfig {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
  private volatile boolean enabled = true;
  
  private volatile boolean includeSubdomains = true;
  
  private volatile long maxAge = 31536000L;
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //------------------------ Implements:
    
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     

  /**
   * @return the enabled
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @return the includeSubdomains
   */
  public boolean includeSubdomains() {
    return includeSubdomains;
  }

  /**
   * @return the maxAge
   */
  public long getMaxAge() {
    return maxAge;
  }

}
