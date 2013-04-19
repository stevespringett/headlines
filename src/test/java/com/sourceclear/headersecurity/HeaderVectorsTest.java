package com.sourceclear.headersecurity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sourceclear.headersecurity.serialization.ImmutableListDeserializer;
import com.sourceclear.headersecurity.serialization.ImmutableMapDeserializer;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 */
@RunWith(Parameterized.class)
public class HeaderVectorsTest {
  
  
  private static class TestVector {
  
    private final String description;
    
    private final HeaderSecurityConfig config;
    
    private final Map<String,String> headers;
    
    public TestVector(String description, HeaderSecurityConfig config, Map<String,String> headers) {
      this.description = description;
      this.config = config;
      this.headers = headers;
    }
  }
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private static final Gson GSON = new GsonBuilder()
          .registerTypeAdapter(ImmutableList.class, new ImmutableListDeserializer())
          .registerTypeAdapter(ImmutableMap.class, new ImmutableMapDeserializer())
          .create();  
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  @Parameters
  public static Collection getVectors() throws FileNotFoundException, IOException {  
    List<TestVector[]> list = Lists.newArrayList();
    
    File vectorFolder = new File(HeaderVectorsTest.class.getResource("/headerVectors").getFile());
    File[] vectors = vectorFolder.listFiles(new FileFilter(){

      public boolean accept(File pathname) {
        return (pathname.canRead() && !pathname.isDirectory() && pathname.getName().endsWith(".conf"));
      }
    });
    
    
    for (File file : vectors) {
      HeaderSecurityConfig config = GSON.fromJson(new FileReader(file), HeaderSecurityConfig.class);
      File propertiesFile = new File(file.getParent(), file.getName() + ".properties");
      Properties p = new Properties();
      p.load(new FileInputStream(propertiesFile));
      HashMap<String,String> map = Maps.newHashMap();
      for (String key :p.stringPropertyNames()) {
        map.put(key, p.getProperty(key));
      }
      
      list.add(Lists.newArrayList(new TestVector(file.getName(), config, map)).toArray(new TestVector[0]));      
    }   
    
    return list;
  }
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private final TestVector vector;
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  public HeaderVectorsTest(TestVector vector) {
    this.vector = vector;
  }
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  @Test
  public void testHeaderOutput() throws Exception {
    //
    // Verify for the given config that the injected headers match what's provided
    // in the test vector.
    //
    HeaderSecurityInjector injector = new HeaderSecurityInjector(vector.config);
    MockHttpServletResponse response = new MockHttpServletResponse();
    
    
    // For now assume all requests are over HTTPS
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setSecure(true);
    
    injector.inject(request, response);
    
//    for (String headerName : response.getHeaderNames()) {
//      System.out.println(headerName + " = " + response.getHeader(headerName));
//    }
    // Verify count
    
    System.out.println("\tTesting " + vector.description);
    assertEquals(vector.description + " - Injected header count doesn't match", vector.headers.size(), response.getHeaderNames().size());        
    
    // Verify contents
    for (String key : response.getHeaderNames()) {
      String responseValue = response.getHeader(key);
      String vectorValue = vector.headers.get(key);
      assertEquals(vector.description +  " - Injected header mismatch: " + key, vectorValue, responseValue);
    }
    
  }
  
  //------------------------ Implements:
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     
}
