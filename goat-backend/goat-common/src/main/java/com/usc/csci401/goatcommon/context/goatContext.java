package com.usc.csci401.goatcommon.context;

import com.google.common.collect.Maps;
import java.util.Map;

@SuppressWarnings("unchecked")
public class goatContext {

  private static final ThreadLocal<Map<String,Object>> MAP_THREAD_LOCAL = ThreadLocal.withInitial(
      Maps::newHashMap);

  public static void put(String key, Object value) {
    MAP_THREAD_LOCAL.get().put(key, value);
  }

  public static <T> T get(String key) {
    return (T) MAP_THREAD_LOCAL.get().get(key);
  }

}
