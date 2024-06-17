package main.java.ru.example;

import main.java.ru.example.exceptions.AnnotationValidatorException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Проверка аннотаций
 *
 * @author NAgafonov
 */
public class AnnotationValidator {

    /**
     * Проверка допустимого числа аннотаций
     * @param clazz класс, в котором применяется аннотация
     * @param annotationClass проверяемая аннотация
     * @param limit максимально возможное число описаний аннотации
     * @throws AnnotationValidatorException исключение в случае превышения максимально допустимого значения
     */
    public void checkCountAnnotation(Class clazz, Class<? extends Annotation> annotationClass, int limit) throws AnnotationValidatorException {
        int annotationCounter = 0;
        for (Method mthd : clazz.getDeclaredMethods()) {
            if (mthd.isAnnotationPresent(annotationClass)) {
                annotationCounter++;
            }
        }
        if (annotationCounter > limit) {
            throw new AnnotationValidatorException("Превышено допустимое число описаний аннотации " + annotationClass);
        }
    }

    public void checkStaticAnnotation(Class c, Class<? extends Annotation> annotationClass) {
        for (Method mthd : c.getDeclaredMethods()) {
            if (mthd.isAnnotationPresent(annotationClass)) {
                if (!Modifier.isStatic(mthd.getModifiers())) {
                    throw new AnnotationValidatorException(annotationClass + " допускается только на статичные методы");
                }
            }
        }
    }
}
