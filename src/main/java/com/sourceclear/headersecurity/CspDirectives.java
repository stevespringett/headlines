/* 
 * Copyright (c) 2013 SourceClear Inc
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 */

package com.sourceclear.headersecurity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import javax.annotation.concurrent.Immutable;

/**
 * Cached directives to be placed into the various CSP headers. These will
 * exist as part of the server's HTTP responses.  Header values are compiled
 * on filter initialization and are cached for future requests.
 */
@Immutable
public class CspDirectives {

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
           
    return sb.toString();
  }  
  
  //---------------------------- Property Methods -----------------------------
  
  public String getCspDirectives() {
    return directivesCache;
  }

  public String getReportCspDirectives() {
    return reportDirectivesCache;
  }

}
