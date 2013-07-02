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
import java.util.logging.Logger;
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
   
    try {
      Reader reader = new InputStreamReader(is);
      config = GSON.fromJson(reader, Map.class);
      Logger.getLogger(getClass().getName()).info(GSON.toJson(config));
      Logger.getLogger(getClass().getName()).info("Loading custom config file.");
    } catch (Throwable t) {
      Logger.getLogger(getClass().getName()).info("No config file found, using restricted defaults");
    }
    
    // Time to load our services and initialize them with a config
    for (HeadLinesInjector injector : serviceLoader) {      
      Object injectorConfig = config.get(injector.getConfigClass().getSimpleName());
      Logger.getLogger(getClass().getName()).info("Config: " + injectorConfig);
      if (injectorConfig != null) {
        Logger.getLogger(getClass().getName()).info("Injecting config into " + injector.getConfigClass());
        injector.setConfig(GSON.fromJson(GSON.toJson(injectorConfig), injector.getConfigClass()));
      }
      
      builder.add(injector);
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
