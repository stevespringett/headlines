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

import com.sourceclear.headlines.AbstractHeaderLinesInjector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public final class HstsInjector extends AbstractHeaderLinesInjector<HstsConfig> {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\    
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  public HstsInjector() {
    setConfig(new HstsConfig());
  }
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //------------------------ Implements: HttpInjector
  
  public void inject(HttpServletRequest request, HttpServletResponse response) {
    
    HstsConfig config = getConfig();
    
    boolean secure = request.isSecure() | "https".equalsIgnoreCase(request.getHeader("X-Forwarded-Proto"));
    if (secure && config.isEnabled()) {
      String header = "max-age=" + config.getMaxAge();
      if (config.includeSubdomains()) {
        header += "; includeSubdomains";
      }
      response.setHeader("Strict-Transport-Security", header);
    }    
  }
  
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     


}
