package org.scadalts.e2e.common.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.utils.TypeConstructors;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Log4j2
class ConfigUtil {

    static E2eConfig parse(File file, String... keyBases) {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(file));
            return _mapObject(properties, new E2eConfigDefault(), keyBases);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private static <T> T _mapObject(Properties properties, T object, String... keyBases) throws IntrospectionException,
            InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors())
        {

            String value = _getValue(properties, propertyDescriptor, keyBases);
            logger.debug("{} : {}", propertyDescriptor.getName(), value);
            if (value != null && propertyDescriptor.getWriteMethod() != null
                    && !value.contains("{") && !value.contains("}"))
            {
                Class<?> clazz = propertyDescriptor.getPropertyType();
                Object created = TypeConstructors.create(clazz, value);
                if(!Objects.isNull(created)) {
                    propertyDescriptor.getWriteMethod().invoke(object, created);
                }
            }
        }
        return object;
    }

    private static String _getValue(Properties properties, PropertyDescriptor propertyDescriptor, String... keyBases) {
        for (String base : keyBases) {
            String key = _getKey(base + propertyDescriptor.getName());
            String value = properties.getProperty(key);
            if(value != null)
                return value;
        }
        return properties.getProperty(propertyDescriptor.getName());
    }

    private static String _getKey(String value) {
        char[] chars = value.toCharArray();
        List<Character> characters = new ArrayList<>();
        char dash = '-';
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                char lower = Character.toLowerCase(chars[i]);
                characters.add(dash);
                characters.add(lower);
            } else {
                characters.add(chars[i]);
            }
        }
        return characters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
