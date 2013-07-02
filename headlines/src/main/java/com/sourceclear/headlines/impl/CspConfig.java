/*
  Copyright (c) 2013  SourceClear Inc.
      
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
  http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. 
 */

package com.sourceclear.headlines.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import javax.annotation.concurrent.Immutable;

/**
 * Configuration object for the CSP code.  This will typically be deserialized
 * from a config file which will be located in the WEB-INF folder.
 */
@Immutable
public final class CspConfig {
  
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
  
  //------------------------ Implements
  
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
