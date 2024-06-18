package main.java.ru.example;

import main.java.ru.example.annotations.AfterSuite;
import main.java.ru.example.annotations.BeforeSuite;
import main.java.ru.example.annotations.CsvSource;
import main.java.ru.example.annotations.Test;
import main.java.ru.example.exceptions.AnnotationValidatorException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author NAgafonov
 */
public class TestRunner {

    public static void runTest(Class c) throws Exception {

        AnnotationValidator validator = new AnnotationValidator();
        validator.checkCountAnnotation(c, BeforeSuite.class, 1);
        validator.checkCountAnnotation(c, AfterSuite.class, 1);
        validator.checkStaticAnnotation(c, BeforeSuite.class);
        validator.checkStaticAnnotation(c, AfterSuite.class);

        Constructor<CustomTest> constructor = c.getConstructor();
        CustomTest customTest = constructor.newInstance();

        List<Method> beforeMethods = getMethodWithAnnotation(c, BeforeSuite.class);
        if (!beforeMethods.isEmpty()) {
            Method beforeMethod = beforeMethods.get(0);
            beforeMethod.invoke(customTest);
        }

        List<Method> testMethods = getMethodWithAnnotation(c, Test.class);
        if (!testMethods.isEmpty()) {
            Collections.sort(testMethods, Comparator.comparing(value -> value.getAnnotation(Test.class).priority()));
            Collections.reverse(testMethods);
            for (Method testMethod : testMethods) {
                if (testMethod.isAnnotationPresent(CsvSource.class)) {
                    String source = testMethod.getAnnotation(CsvSource.class).value();
                    String[] csvArg = source.split(",");
                    final Class<?>[] parameterTypes = testMethod.getParameterTypes();
                    List<Object> args = parseArgsFromString(parameterTypes, csvArg);
                    testMethod.invoke(customTest, args.toArray());
                }
            }
        }

        List<Method> afterMethods = getMethodWithAnnotation(c, AfterSuite.class);
        if (!afterMethods.isEmpty()) {
            Method afterMethod = afterMethods.get(0);
            afterMethod.invoke(customTest);
        }
    }

    private static List<Object> parseArgsFromString( Class<?>[] parameterTypes, String[] csvArg) {
        if (parameterTypes.length != csvArg.length) {
            throw new AnnotationValidatorException("Число параметров строки аннотации @CsvSource должно соответствовать числу аргументов метода  ");
        }
        List<Object> args = new ArrayList<>();
        for(int i = 0; i < parameterTypes.length; i++) {
            final Class<?> paramType = parameterTypes[i];
            final String noFormatArg = csvArg[i].trim();
            if (paramType.isPrimitive()) {
                if (Integer.TYPE.isAssignableFrom(paramType)) {
                    try {
                        args.add(Integer.valueOf(noFormatArg));
                    } catch (NumberFormatException e) {
                        throw new AnnotationValidatorException("Не удалось преобразовать к целочисленному типу", e);
                    }
                }
                if (Boolean.TYPE.isAssignableFrom(paramType)) {
                    try {
                        args.add(Boolean.valueOf(noFormatArg));
                    } catch (NumberFormatException e) {
                        throw new AnnotationValidatorException("Не удалось преобразовать к логическому типу", e);
                    }
                }
            } else {
                args.add(noFormatArg);
            }
        }
        return args;
    }

    private static List<Method> getMethodWithAnnotation(Class c, Class<? extends Annotation> annotationClass) {
        final List<Method> foundedMethods = new ArrayList<>();
        for (Method mthd : c.getDeclaredMethods()) {
            if (mthd.isAnnotationPresent(annotationClass)) {
                foundedMethods.add(mthd);
            }
        }
        return foundedMethods;
    }


}
