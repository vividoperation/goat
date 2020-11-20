package com.usc.csci401.testgameweb.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

public class BeanConvertUtils {

  public static <FROM, TO> TO copy(FROM from, TO to) throws Exception {
    return copy(from, to, true);
  }

  public static <FROM, TO> TO copy(FROM from, TO to, Boolean ignoreNull) throws Exception {
    Preconditions.checkArgument(null != from && null != to);
    ignoreNull = null != ignoreNull && ignoreNull;
    if (ignoreNull) {
      BeanUtils.copyProperties(from, to, getNullPropertyNames(from));
    } else {
      BeanUtils.copyProperties(from, to);
    }
    return to;
  }

  public static <FROM, TO> TO convert(FROM from, Class<TO> toClass) throws Exception {
    if (null == from) {
      return null;
    }
    TO to = toClass.newInstance();
    BeanUtils.copyProperties(from, to, getNullPropertyNames(from));
    return to;
  }

  public static <FROM, TO> TO convert(FROM from, Class<TO> toClass, Boolean ignoreNull) throws Exception {
    if (null == from) {
      return null;
    }
    ignoreNull = null != ignoreNull && ignoreNull;
    TO to = toClass.newInstance();
    if (ignoreNull) {
      BeanUtils.copyProperties(from, to, getNullPropertyNames(from));
    } else {
      BeanUtils.copyProperties(from, to);
    }
    return to;
  }

  public static <FROM, TO> List<TO> convert(List<FROM> froms, Class<TO> toClass) throws Exception {
    if (CollectionUtils.isEmpty(froms)) {
      return Lists.newArrayListWithCapacity(0);
    }
    List<TO> tos = Lists.newArrayListWithCapacity(froms.size());
    for (FROM from : froms) {
      tos.add(convert(from, toClass));
    }
    return tos;
  }


  public static <FROM, TO> List<TO> convert(List<FROM> froms, Class<TO> toClass, Boolean ignoreNull) throws Exception {
    if (CollectionUtils.isEmpty(froms)) {
      return Lists.newArrayListWithCapacity(0);
    }
    List<TO> tos = Lists.newArrayListWithCapacity(froms.size());
    for (FROM from : froms) {
      tos.add(convert(from, toClass, ignoreNull));
    }
    return tos;
  }

  private static String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
    return Stream.of(wrappedSource.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
        .toArray(String[]::new);
  }

}