/* 
 * Copyright (c) 2013 SourceClear Inc
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 */

package com.sourceclear.headlines;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import javax.annotation.concurrent.Immutable;

/**
 * Configuration object for the CSP code.  This will typically be deserialized
 * from a config file which will be located in the WEB-INF folder.
 */
@Immutable
public class CspConfig {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private volatile boolean enabled = true;
  
  private volatile ImmutableMap<String,ImmutableList<String>> csp;
  
  private volatile ImmutableMap<String,ImmutableList<String>> cspReportOnly;

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  

  /**
   * Create a default CspConfig instance. This has a restrictive policy that
   * only allows the loading of resources from 'self'.  
   */
  public CspConfig() {
    this(ImmutableMap.of("default-src", ImmutableList.of("'self'")));       
  }
  
  public CspConfig(ImmutableMap<String, ImmutableList<String>> csp) {
    this(csp, new ImmutableMap.Builder<String, ImmutableList<String>>().build());
  }
  
  public CspConfig(ImmutableMap<String, ImmutableList<String>> csp, ImmutableMap<String, ImmutableList<String>> cspReportOnly) {
    this.csp = csp;
    this.cspReportOnly = cspReportOnly;
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
  
  public ImmutableMap<String, ImmutableList<String>> getCspMap() {
    return csp;
  }
  
  public ImmutableMap<String, ImmutableList<String>> getCspReportOnlyMap() {
    return cspReportOnly;
  }  
}
