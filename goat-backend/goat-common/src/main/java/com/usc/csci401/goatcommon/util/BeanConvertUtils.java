package com.usc.csci401.goatcommon.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.usc.csci401.goatcommon.exception.SystemException;
import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

public class BeanConvertUtils {

  public static <FROM, TO> TO copy(FROM from, TO to) {
    return copy(from, to, true);
  }

  public static <FROM, TO> TO copy(FROM from, TO to, Boolean ignoreNull) {
    Preconditions.checkArgument(null != from && null != to);
    ignoreNull = null != ignoreNull && ignoreNull;
    try {
      if (ignoreNull) {
        BeanUtils.copyProperties(from, to, getNullPropertyNames(from));
      } else {
        BeanUtils.copyProperties(from, to);
      }
      return to;
    } catch (Exception e) {
      throw new SystemException("can not copy", e);
    }
  }

  public static <FROM, TO> TO convert(FROM from, Class<TO> toClass) {
    try {
      if (null == from) {
        return null;
      }
      TO to = toClass.newInstance();
      BeanUtils.copyProperties(from, to, getNullPropertyNames(from));
      return to;
    } catch (Exception e) {
      throw new SystemException("can not convert", e);
    }
  }

  public static <FROM, TO> TO convert(FROM from, Class<TO> toClass, Boolean ignoreNull) {
    try {
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
    } catch (Exception e) {
      throw new SystemException("can not convert", e);
    }
  }

  public static <FROM, TO> List<TO> convert(List<FROM> froms, Class<TO> toClass) {
    try {
      if (CollectionUtils.isEmpty(froms)) {
        return Lists.newArrayListWithCapacity(0);
      }
      List<TO> tos = Lists.newArrayListWithCapacity(froms.size());
      for (FROM from : froms) {
        tos.add(convert(from, toClass));
      }
      return tos;
    } catch (Exception e) {
      throw new SystemException("can not convert", e);
    }
  }


  public static <FROM, TO> List<TO> convert(List<FROM> froms, Class<TO> toClass, Boolean ignoreNull) {
    try {
      if (CollectionUtils.isEmpty(froms)) {
        return Lists.newArrayListWithCapacity(0);
      }
      List<TO> tos = Lists.newArrayListWithCapacity(froms.size());
      for (FROM from : froms) {
        tos.add(convert(from, toClass, ignoreNull));
      }
      return tos;
    } catch (Exception e) {
      throw new SystemException("can not convert", e);
    }
  }

  private static String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
    return Stream.of(wrappedSource.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
        .toArray(String[]::new);
  }

}