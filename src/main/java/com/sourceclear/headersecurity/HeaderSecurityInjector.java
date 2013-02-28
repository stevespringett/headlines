package com.sourceclear.headersecurity;

import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.http.HttpServletRequest;
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
  
  public void inject(HttpServletRequest request, HttpServletResponse response) {
    System.out.println("Injecting secure headers");
    // XContentType
    if (config.getXContentTypeConfig().isEnabled()) {
      response.setHeader("X-Content-Type-Options", "nosniff");
    }
    
    // XFrameOptions
    if (config.getXFrameOptionsConfig().isEnabled()) {
      response.setHeader("X-Frame-Options", config.getXFrameOptionsConfig().getValue());
    }
    
    // XssProtection
    response.setHeader("X-XSS-Protection", config.getXssProtectionConfig().isEnabled() ? "1" : "0");
    
    // HSTS. This is only used for current HTTPS connections.
    HstsConfig hsts = config.getHstsConfig();
    System.out.println("X-Forwarded-Proto: " + request.getHeader("X-Forwarded-Proto"));
    boolean secure = request.isSecure() | "https".equalsIgnoreCase(request.getHeader("X-Forwarded-Proto"));
    if (secure) {
      String header = "max-age=" + hsts.getMaxAge();
      if (hsts.includeSubdomains()) {
        header += "; includeSubdomains";
      }
      System.out.println("Setting Strict-Transport-Security to " + header);
      response.setHeader("Strict-Transport-Security", header);
    }
  }
  
  //------------------------ Implements:
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     
}
