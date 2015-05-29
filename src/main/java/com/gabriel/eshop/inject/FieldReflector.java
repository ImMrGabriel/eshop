package com.gabriel.eshop.inject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper class to obtain certain class fields using reflection
 */
public class FieldReflector {

    /**
     * Collects all fields, that are in classes from clazz to upperBound.
     * @param clazz the class from which to begin to move up the hierarchy
     * @param upperBound the class to which you want to move up (its fields are not included)
     * @return list of all fields
     */
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
     * Collects all fields that are marked with @Inject from the passed list
     * @param fields list of fields to search
     * @return list of all fields marked with @Inject
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
