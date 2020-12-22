package com.dgut.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UpdateUtil {
        /*
         * @Description:所有空值属性都不copy
         * @param source 1
         * @param target 2
         * @return : void
         * @author : CR
         * @date : 2020/6/21 11:18
         */
        public static void copyNullProperties(Object source, Object target) {
            BeanUtils.copyProperties(source, target, getNullField(source));
        }

        /*
         * @Description:获取属性中为空的字段
         * @param target 1
         * @return : java.lang.String[]
         * @author : CR
         * @date : 2020/6/21 11:17
         */
        private static String[] getNullField(Object target) {
            BeanWrapper beanWrapper = new BeanWrapperImpl(target);
            PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
            Set<String> notNullFieldSet = new HashSet<>();
            if (propertyDescriptors.length > 0) {
                for (PropertyDescriptor p : propertyDescriptors) {
                    String name = p.getName();
                    Object value = beanWrapper.getPropertyValue(name);
                    if (Objects.isNull(value)) {
                        notNullFieldSet.add(name);
                    }
                }
            }
            String[] notNullField = new String[notNullFieldSet.size()];
            return notNullFieldSet.toArray(notNullField);
        }
}
