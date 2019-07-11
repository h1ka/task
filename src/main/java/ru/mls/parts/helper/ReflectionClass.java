package ru.mls.parts.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final  class ReflectionClass<T> {

    private ReflectionClass() {
    }



    public static Object getFieldValue(Object object, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.canAccess(object);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    public static List<String> getNamesField(Object object,Field withoutThisField){
        List<String> fields = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (var field : declaredFields){
            var nameField = field.getName();
            if(nameField!=withoutThisField.getName())
            fields.add(nameField);
        }
        return fields;
    }
    public static List<String> getValuesFields(Object object){
        List<String> values = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (var field : declaredFields){
            var nameField = field.getName();
            var value = getFieldValue(object,nameField).toString();
            values.add(value);
        }
        return values;
    }
    public static List<String> getValuesFields(Object object,Field withoutThisField){
        List<String> values = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (var field : declaredFields){
            var nameField = field.getName();
            if(nameField!=withoutThisField.getName()) {
                var value = getFieldValue(object, nameField).toString();
                values.add(value);
            }
        }
        return values;
    }

    public static Map<String,String> getFieldWithValue(Object object){
        Map<String,String> fieldWithValue = new HashMap<>();
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (var field : declaredFields){
            var nameField = field.getName();
            var fieldValue = getFieldValue(object, nameField).toString();
            fieldWithValue.put(nameField,fieldValue);
        }
        return fieldWithValue;
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }
}
