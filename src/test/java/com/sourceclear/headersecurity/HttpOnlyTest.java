package com.sourceclear.headersecurity;


import com.sourceclear.headlines.impl.HttpOnlyInjector;
import javax.servlet.http.Cookie;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 */
public class HttpOnlyTest {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  @Test
  public void testDefaultHttpOnly() throws Exception {

    // Instantiate our HttpOnlyInjector and set up a mock HttpRequest
    HttpOnlyInjector injector = new HttpOnlyInjector();
    
    // For now assume all requests are over HTTPS
    MockHttpServletRequest request = new MockHttpServletRequest();
    Cookie cookie = new Cookie("JSESSIONID", "123456789ABCDEF");
    cookie.setPath("/");
    cookie.setDomain("localhost");
    cookie.setMaxAge(3600);
    cookie.setSecure(true);
    
    Cookie randomCookie = new Cookie("randomStuff", "ABC123");    
    randomCookie.setPath("/");
    randomCookie.setDomain("localhost");
    randomCookie.setSecure(true);    
    randomCookie.setMaxAge(3601);
    
    request.setCookies(cookie, randomCookie);    
    
    MockHttpServletResponse response = new MockHttpServletResponse();
    response.addCookie(randomCookie);
    
    injector.inject(request, response);
    

    Cookie responseCookie = response.getCookie("JSESSIONID");
    
    assertTrue("JSESSIONID cookie doesn't match", assertCookies(cookie, responseCookie));
    assertTrue("JSESSIONID cookie is NOT HttpOnly", responseCookie.isHttpOnly());
    
    assertTrue("Random cookie has been modified", assertCookies(randomCookie, response.getCookie("randomStuff")));    
    assertFalse("Random cookie is HttpOnly", response.getCookie("randomStuff").isHttpOnly());
  }
  
  //------------------------ Implements:
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //
  // The stupid Cookie API has no methods for determining equivelance!  This 
  // ignores the HttpOnly flag, comments, and version.
  //
  private boolean assertCookies(Cookie c1, Cookie c2) {
    return 
            c1.getSecure() == c2.getSecure() &&
            c1.getDomain().equals(c2.getDomain()) &&
            c1.getMaxAge() == c2.getMaxAge() &&
            c1.getName().equals(c2.getName()) &&
            c1.getPath().equals(c2.getPath()) &&
            c1.getValue().equals(c2.getValue());
  }
  
  //---------------------------- Property Methods -----------------------------     
}
