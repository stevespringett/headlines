package com.sourceclear.headersecurity;

import javax.annotation.concurrent.Immutable;

/**
 *
 */
@Immutable
public class HstsConfig {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
  private boolean enabled = true;
  
  private boolean includeSubdomains = true;
  
  private long maxAge = 31536000L;
  
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
