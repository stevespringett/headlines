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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.concurrent.Immutable;

/**
 *
 */
@Immutable
public class HttpOnlyConfig {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
  private volatile boolean enabled = true;  
  
  private volatile ImmutableList<String> sessionPatterns;
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  public HttpOnlyConfig() {
    sessionPatterns = ImmutableList.of("JSESSIONID");
  }
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  public ImmutableSet<Pattern> buildPatterns() {
    Set<Pattern> patterns = Sets.newHashSet();
            
    for (String pattern : sessionPatterns) {
      patterns.add(Pattern.compile(pattern, Pattern.CASE_INSENSITIVE));
    }
            
    return ImmutableSet.copyOf(patterns);
  }
  
  //------------------------ Implements:
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     
  
  public boolean isEnabled() {
    return enabled;
  }
  
  public ImmutableList<String> getSessionPatterns() {
    return sessionPatterns; 
  } 
}
