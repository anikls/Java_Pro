package main.java.ru.example.exceptions;

/**
 * Ошибка примения аннотаций
 *
 * @author NAgafonov
 */
public class AnnotationValidatorException extends IllegalStateException {

    public AnnotationValidatorException(String msg) {
        super(msg);
    }

    public AnnotationValidatorException(String msg, Throwable e) {
        super(msg, e);
    }
}
