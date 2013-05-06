/* 
 * Copyright (c) 2013 SourceClear Inc
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 */

package com.sourceclear.headlines.serialization;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

/**
 * Customized JsonDeserializer which allows for deserializing Guava's ImmutableMap
 * into objects via GSON.
 */
public class ImmutableMapDeserializer implements JsonDeserializer<ImmutableMap<?,?>>{
  
  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  
  
  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
  
  //------------------------ Implements: JsonDeserializer
  
  public ImmutableMap<?, ?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
    final Type type2 = ParameterizedTypeImpl.make(Map.class, ((ParameterizedType) type).getActualTypeArguments(), null);
    final Map<?,?> map = context.deserialize(json, type2);

    return ImmutableMap.copyOf(map);
  }  
  
  //------------------------ Overrides:
  
  //---------------------------- Abstract Methods -----------------------------
  
  //---------------------------- Utility Methods ------------------------------
  
  //---------------------------- Property Methods -----------------------------     

}
