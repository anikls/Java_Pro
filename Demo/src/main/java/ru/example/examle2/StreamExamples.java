package main.java.ru.example.examle2;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author NAgafonov
 */
public class StreamExamples {

    public static void testRun() {

        System.out.println("========= 1 =========");
        System.out.println("Реализуйте удаление из листа всех дубликатов");
        System.out.println("Исходные данные:");
        final List<Integer> LIST_INT = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
        System.out.println(LIST_INT);
        System.out.println("Результат:");
        System.out.println(LIST_INT.stream().distinct().collect(Collectors.toList()));

        System.out.println("\n========= 2 =========");
        System.out.println("Найдите в списке целых чисел 3-е наибольшее число");
        System.out.println("Исходные данные:");
        System.out.println(LIST_INT);
        System.out.println("Результат:");
        System.out.println(LIST_INT.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(2));

        System.out.println("\n========= 3 =========");
        System.out.println(" Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9, в отличие от прошлой задачи здесь разные 10 считает за одно число)");
        System.out.println("Исходные данные:");
        System.out.println(LIST_INT);
        System.out.println("Результат:");
        System.out.println(LIST_INT.stream().sorted(Comparator.reverseOrder()).distinct().collect(Collectors.toList()).get(2));

        final List<Worker> LIST_WORKERS = List.of(
                new Worker("Star Star", 93, WorkerPosition.INGENER),
                new Worker("Bob", 34, WorkerPosition.WORKER),
                new Worker("Jon", 42, WorkerPosition.INGENER),
                new Worker("Billi", 20, WorkerPosition.WORKER),
                new Worker("Stepan", 50, WorkerPosition.DIRECTOR),
                new Worker("Ivan", 49, WorkerPosition.INGENER),
                new Worker("Alex", 45, WorkerPosition.INGENER)
        );

        System.out.println("\n========= 4 =========");
        System.out.println("Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста");
        System.out.println("Исходные данные:");
        System.out.println(LIST_WORKERS);
        System.out.println("Результат:");
        System.out.println(LIST_WORKERS.stream()
                .filter(worker -> WorkerPosition.INGENER.equals(worker.getPosition()))
                .sorted(Comparator.comparing(Worker::getAge).reversed())
                .limit(3)
                .map(Worker::getName)
                .collect(Collectors.toList()));

        System.out.println("\n========= 5 =========");
        System.out.println("Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст сотрудников с должностью «Инженер»");
        System.out.println("Исходные данные:");
        System.out.println(LIST_WORKERS);
        System.out.println("Результат:");
        System.out.println(LIST_WORKERS.stream()
                .filter(worker -> WorkerPosition.INGENER.equals(worker.getPosition()))
                .map(Worker::getAge)
                .reduce((age1, age2) -> Math.round((age1 + age2) / 2)).get());

        System.out.println("\n========= 6 =========");
        System.out.println("Найти в списке слов самое длинное слово:");
        System.out.println("Исходные данные:");
        List<String> in6 = List.of("Найти", "в", "списке", "слов", "самое", "длинное", "слово");
        System.out.println(in6);
        System.out.println("Результат:");
        System.out.println(in6.stream()
                .max(Comparator.comparing(s -> s.length()))
                .get());

        System.out.println("\n========= 7 =========");
        System.out.println("Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке");
        String in = "Хэш мэпа для хранения слов и их количества в тексте и мэпа дополнительная для списка слов и";
        System.out.println("Исходные данные:");
        System.out.println(in);
        System.out.println("Результат:");
        System.out.println(Arrays.stream(in.split("\s|,\s"))
                .map(String::toLowerCase)
                .collect(Collectors.toMap(key -> key, value -> new ArrayList<>(Arrays.asList(value)),
                        (value1, value2) -> { // в случае одинаковоых ключей объединим значение в один список
                            value1.addAll(value2);
                            return value1;
                        }))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(o -> o.getKey(), o -> o.getValue().size()))
        );

        System.out.println("\n========= 8 =========");
        String str = "Отпечатайте в консоль строки из списка в порядке увеличения длины слова, если слова имеют одинаковую длину, то должен сохраниться алфавитный порядок";
        System.out.println("Исходные данные:");
        System.out.println(str);
        System.out.println("Результат:");
        System.out.println(Arrays.stream(str.split("\s|,\s"))
                .sorted(Comparator.comparing(String::length).thenComparing(String::compareTo))
                .peek(System.out::println)
                .collect(Collectors.toList())
        );

        System.out.println("\n========= 9 =========");
        System.out.println("Имеется массив строк, в каждой из которых лежит набор из 5 строк, разделенных пробелом, найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них");
        String[] arrString = {
                "здесь представлена самая первая строка",
                "вторая строка будет тоже такая",
                "пусть это будет последняя строка"
        };
        System.out.println("Исходные данные:");
        System.out.println(arrString);
        System.out.println("Результат:");
        System.out.println(Arrays.stream(arrString)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .max(Comparator.comparing(s -> s.length()))
                .get()
        );
    }
}
