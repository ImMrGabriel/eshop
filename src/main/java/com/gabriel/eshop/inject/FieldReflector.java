package com.gabriel.eshop.inject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldReflector {
    public static List<Field> collectUpTo(Class<?> clazz, Class<?> upperBound) {
        List<Field> result = new ArrayList<>();
        Class<?> current = clazz;
        while (current != upperBound) {
//            result.addAll(Arrays.asList(current.getFields()));          // All the public fields up the entire class hierarchy.
            result.addAll(Arrays.asList(current.getDeclaredFields()));  // return all field only for the current class
            current = current.getSuperclass();
        }
        return result;
    }

    /**
     * 1) Non static
     * 2) all scopes
     * 3) from all parents
     * 4) clazz fields - include
     * 5) upperBound fields - exclude
     */

    public static List<Field> filterInject(List<Field> fields) {
        List<Field> result = new ArrayList<>();
        for(Field field : fields) {
            Inject annotation = field.getAnnotation(Inject.class);
            if(annotation != null) {
                result.add(field);
            }
        }
        return result;
    }
}
