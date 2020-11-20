package com.usc.csci401.testgameweb.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class JsonUtils {

  private static ObjectMapper objectMapper =  new ObjectMapper();

  public static String toJson(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      log.error("JsonUtils: toJson fail, object: {}, cause: {}", object, e.getMessage());
      throw new RuntimeException("JsonUtils: error encoding json from " + object.getClass(), e);
    }
  }

  public static <T> T toObject(String json, Class<T> cls) {
    try {
      return objectMapper.readValue(json, cls);
    } catch (Exception e) {
      log.error("JsonUtils: toObject fail, json: {}, cls: {}, cause: {}", json, cls, e.getMessage());
      throw new RuntimeException("JsonUtils: error decode json to " + cls, e);
    }
  }


  public static <T> T toObject(JsonNode node, Class<T> cls) {
    try {
      return objectMapper.treeToValue(node, cls);
    } catch (Exception e) {
      log.error("JsonUtils: toObject(JsonNode) fail, node: {}, cause: {}", node, e.getMessage());
      throw new RuntimeException("JsonUtils: toObject(JsonNode) fail", e);
    }
  }


  public static <T> T parseObject(String jsonStr, Class<T> clazz) {
    if (StringUtils.isBlank(jsonStr)) {
      return null;
    }
    try {
      return objectMapper.readValue(jsonStr, clazz);
    } catch (Exception e) {
      throw new RuntimeException("deserialize error", e);
    }
  }


  public static <T> T parseObject(String jsonStr, TypeReference<T> type) {
    try {
      return objectMapper.readValue(jsonStr, type);
    } catch (IOException e) {
      throw new RuntimeException("deserialize error", e);
    }
  }


  public static <V> Map<String, V> parseMap(String jsonStr) {
    if(StringUtils.isBlank(jsonStr)){
      return Maps.newHashMap();
    }
    try {
      return objectMapper.readValue(jsonStr, new TypeReference<Map<String, V>>() {});
    } catch (Exception e) {
      throw new RuntimeException("deserialize error", e);
    }
  }


  public static <K, V> Map<K, V> parseMap(String jsonStr, Class<K> k, Class<V> v) {
    try {
      return objectMapper.readValue(jsonStr, new TypeReference<Map<K, V>>() {});
    } catch (IOException e) {
      throw new RuntimeException("deserialize error", e);
    }
  }

  public static <T> List<T> parseList(String jsonStr, Class<T> clazz) {
    if (StringUtils.isBlank(jsonStr)) {
      return Lists.newArrayList();
    }
    try {
      CollectionType collectionType = objectMapper.getTypeFactory()
          .constructCollectionType(ArrayList.class, clazz);
      return objectMapper.readValue(jsonStr, collectionType);
    } catch (Exception e) {
      throw new RuntimeException("deserialize error", e);
    }
  }

  public static List<Map<String,Object>> parseList(String jsonStr) {
    if (StringUtils.isBlank(jsonStr)) {
      return Lists.newArrayList();
    }
    try {
      return parseObject(jsonStr, new TypeReference<List<Map<String,Object>>>(){});
    } catch (Exception e) {
      throw new RuntimeException("deserialize error", e);
    }
  }

}
