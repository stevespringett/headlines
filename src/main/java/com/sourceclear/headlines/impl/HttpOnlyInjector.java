package com.sourceclear.headlines.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.sourceclear.headlines.AbstractHeaderLinesInjector;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public final class HttpOnlyInjector extends AbstractHeaderLinesInjector<HttpOnlyConfig> {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
  private volatile ImmutableSet<Pattern> patterns;
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  public HttpOnlyInjector() {
    setConfig(new HttpOnlyConfig());
  }
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //------------------------ Implements:
  
  //------------------------ Overrides: AbstractHeaderLinesInjector

  @Override
  public void setConfig(HttpOnlyConfig config) {
    patterns = config.buildPatterns();
    super.setConfig(config);
  }
  
  public void inject(HttpServletRequest request, HttpServletResponse response) {

    HttpOnlyConfig config = getConfig();
    
    if (config.isEnabled() && request.getCookies() != null) {
      for (Cookie cookie : findMatches(request.getCookies())) {
        injectCookie(cookie, response);
      }
    }
  }
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  private Set<Cookie> findMatches(Cookie[] cookies) {
    Set<Cookie> matches = Sets.newHashSet();
    
    for (Cookie cookie : cookies) {
      for (Pattern pattern : patterns) {
        if (pattern.matcher(cookie.getName()).matches()) {
          matches.add(cookie);
        }
      }
    }
    
    return matches;
  }

  private void injectCookie(Cookie cookie, HttpServletResponse response) {
    Cookie injectedCookie = Cookie.class.cast(cookie.clone());
    injectedCookie.setHttpOnly(true);
    response.addCookie(injectedCookie);
  }
  
  //---------------------------- Property Methods -----------------------------     
}
