package com.sourceclear.headlines;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sourceclear.headlines.serialization.ImmutableListDeserializer;
import com.sourceclear.headlines.serialization.ImmutableMapDeserializer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.ServiceLoader;
import javax.annotation.concurrent.NotThreadSafe;

/**
 *
 */
@NotThreadSafe
public final class InjectorServiceLoader {
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  private static final Gson GSON = new GsonBuilder()
          .registerTypeAdapter(ImmutableList.class, new ImmutableListDeserializer())
          .registerTypeAdapter(ImmutableMap.class, new ImmutableMapDeserializer())
          .create();      
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\   
  
  private Map<String, Object> config = Maps.newHashMap();
  
  private volatile ImmutableList<HeadLinesInjector> injectors;
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  public void load(InputStream is) throws IOException {
    ServiceLoader<HeadLinesInjector> serviceLoader = ServiceLoader.load(HeadLinesInjector.class);  
    
    config.clear();
    ImmutableList.Builder<HeadLinesInjector> builder = new ImmutableList.Builder<HeadLinesInjector>();
   
    Reader reader = new InputStreamReader(is);
    config = GSON.fromJson(reader, Map.class);

    // Time to load our services and initialize them with a config
    for (HeadLinesInjector injector : serviceLoader) {
      Object injectorConfig = config.get(injector.getConfigClass().getSimpleName());
      if (injectorConfig != null) {
        injector.setConfig(GSON.fromJson(GSON.toJson(injectorConfig), injector.getConfigClass()));
        builder.add(injector);
      }
    }
    
    injectors = builder.build();
  }
  
  //------------------------ Implements:
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------    

  public ImmutableList<HeadLinesInjector> getInjectorList() {
    return injectors;
  }

}
