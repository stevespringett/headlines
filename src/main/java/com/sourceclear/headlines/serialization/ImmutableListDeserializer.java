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

package com.sourceclear.headlines.serialization;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

/**
 * Customized JsonDeserializer which allows for deserializing Guava's ImmutableList
 * into objects via GSON.
 */
public class ImmutableListDeserializer implements JsonDeserializer<ImmutableList<?>> {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //------------------------ Implements: JsonDeserializer
  
  public ImmutableList<?> deserialize(JsonElement json, Type type,
          JsonDeserializationContext context) throws JsonParseException {
    final Type type2 = ParameterizedTypeImpl.make(List.class, ((ParameterizedType) type).getActualTypeArguments(), null);
    final List<?> list = context.deserialize(json, type2);

    return ImmutableList.copyOf(list);
  }
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     
}
