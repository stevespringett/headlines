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
}
