package com.sourceclear.headlines;

import javax.annotation.concurrent.Immutable;

/**
 *
 */
@Immutable
public class HeaderSecurityConfig {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private volatile XContentTypeConfig xContentType = new XContentTypeConfig();
  
  private volatile XFrameOptionsConfig xFrameOptions = new XFrameOptionsConfig();
  
  private volatile XssProtectionConfig xssProtection = new XssProtectionConfig();
  
  private volatile HstsConfig hstsConfig = new HstsConfig();
  
  private volatile CspConfig cspConfig = new CspConfig(); 
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //------------------------ Implements:
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     
  
  public XContentTypeConfig getXContentTypeConfig() {
    return xContentType;
  }
  
  public XFrameOptionsConfig getXFrameOptionsConfig() {
    return xFrameOptions;
  }
  
  public XssProtectionConfig getXssProtectionConfig() {
    return xssProtection;
  }
  
  public HstsConfig getHstsConfig() {
    return hstsConfig;
  }
  
  public CspConfig getCspConfig() {
    return cspConfig;
  }
}
