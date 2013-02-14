package com.sourceclear.headersecurity;

import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@ThreadSafe
public class HeaderSecurityInjector {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private final HeaderSecurityConfig config;
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  public HeaderSecurityInjector (HeaderSecurityConfig config) {
    this.config = config;
  }
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  public void inject(HttpServletResponse response) {
    
    // XContentType
    if (config.getXContentTypeConfig().isEnabled()) {
      response.setHeader("X-Content-Type-Options", "nosniff");
    }
    
    
  }
  
  //------------------------ Implements:
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     
}
