package com.sourceclear.headlines;

import com.google.common.collect.ImmutableList;
import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@ThreadSafe
public class HeaderSecurityInjector {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private static final ImmutableList<String> HEADER_LIST =ImmutableList.of(

      "Content-Security-Policy", // W3 Standard
      "X-Content-Security-Policy", // IE 10
      "X-Webkit-CSP"); // Chrome < 25, Safari
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private final HeaderSecurityConfig config;
  
  private final CspDirectives cspDirectives;
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  public HeaderSecurityInjector (HeaderSecurityConfig config) {
    this.config = config;
    cspDirectives = CspDirectives.build(config.getCspConfig());
  }
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  public void inject(HttpServletRequest request, HttpServletResponse response) {

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
    
    boolean secure = request.isSecure() | "https".equalsIgnoreCase(request.getHeader("X-Forwarded-Proto"));
    if (secure && hsts.isEnabled()) {
      String header = "max-age=" + hsts.getMaxAge();
      if (hsts.includeSubdomains()) {
        header += "; includeSubdomains";
      }
      response.setHeader("Strict-Transport-Security", header);
    }

    CspConfig cspConfig = config.getCspConfig();
    
    if (cspConfig.isEnabled() && !cspDirectives.getCspDirectives().isEmpty()) {
      for (String header : HEADER_LIST) {
        response.setHeader(header, cspDirectives.getCspDirectives());
      }
    }
    
    if (cspConfig.isEnabled() && !cspDirectives.getReportCspDirectives().isEmpty()) {
      response.setHeader("Content-Security-Policy-Report-Only", cspDirectives.getReportCspDirectives());        
    }    
  }
  
  //------------------------ Implements:
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     
}
