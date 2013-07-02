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
 * Cached directives to be placed into the various CSP headers. These will
 * exist as part of the server's HTTP responses.  Header values are compiled
 * on filter initialization and are cached for future requests.
 */
@Immutable
public final class CspDirectives {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public static CspDirectives build(CspConfig config) {
    return new CspDirectives(config);
  }

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private final String directivesCache;
  
  private final String reportDirectivesCache;

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private CspDirectives(CspConfig config) {
    directivesCache = formatDirectives(config.getCspMap());
    reportDirectivesCache = formatDirectives(config.getCspReportOnlyMap());
  }

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  //------------------------ Overrides:

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  private String formatDirectives(ImmutableMap<String,ImmutableList<String>> directives) {
    
    // In the case of an empty map return the empty string:
    if (directives.isEmpty()) {
      return "";
    }
    
    StringBuilder sb = new StringBuilder();
    
    // Outer loop - loop through each directive
    for (String directive : directives.keySet()) {
      
      // Don't add a directive if it has zero elements
      if (directives.get(directive).size() == 0) {        
        continue;
      }
      
      StringBuilder elements = new StringBuilder();
      
      // Inner loop = for each directive build up the element String
      for (String element: directives.get(directive)) {
        elements.append(element).append(" ");
      }
      
      if (sb.length() > 0) {
        sb.append(" ; ");
      }
      
      sb.append(directive).append(" ").append(elements.toString().trim());
    }
           
    return sb.toString().trim();
  }  
  
  //---------------------------- Property Methods -----------------------------
  
  public String getCspDirectives() {
    return directivesCache;
  }

  public String getReportCspDirectives() {
    return reportDirectivesCache;
  }

}
