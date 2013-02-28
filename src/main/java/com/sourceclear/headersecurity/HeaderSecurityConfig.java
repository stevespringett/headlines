package com.sourceclear.headersecurity;

import javax.annotation.concurrent.Immutable;

/**
 *
 */
@Immutable
public class HeaderSecurityConfig {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private XContentTypeConfig xContentType = new XContentTypeConfig();
  
  private XFrameOptionsConfig xFrameOptions = new XFrameOptionsConfig();
  
  private XssProtectionConfig xssProtection = new XssProtectionConfig();
  
  private HstsConfig hstsConfig = new HstsConfig();
  
  private String proxyHeader = "X-Forwarded-Proto";
  
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
}