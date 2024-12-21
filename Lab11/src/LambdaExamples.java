import java.util.*;
import java.util.function.*;

public class LambdaExamples {

    public static void main(String[] args) {
        // 1.1. Лямбда для Function - преобразование строки в ее длину
        Function<String, Integer> stringLength = str -> str.length();
        System.out.println("Length of 'Hello': " + stringLength.apply("Hello")); // 5
        System.out.println("Length of 'Lambda': " + stringLength.apply("Lambda")); // 6

        // 1.2. Лямбда для Predicate - проверка, является ли число четным
        Predicate<Integer> isEven = num -> num % 2 == 0;
        System.out.println("Is 4 even? " + isEven.test(4)); // true
        System.out.println("Is 7 even? " + isEven.test(7)); // false

        // 1.3. Лямбда для Consumer - вывод строки на консоль
        Consumer<String> printMessage = message -> System.out.println("Message: " + message);
        printMessage.accept("Hello, World!"); // Message: Hello, World!
        printMessage.accept("Lambda expressions are fun!"); // Message: Lambda expressions are fun!

        // 1.4. Лямбда для Supplier - генерация случайного числа
        Supplier<Integer> randomNumber = () -> (int) (Math.random() * 100);
        System.out.println("Random number: " + randomNumber.get()); // случайное число
        System.out.println("Random number: " + randomNumber.get()); // случайное число

        // 1.5. Лямбда для BinaryOperator - сложение двух чисел
        BinaryOperator<Integer> addNumbers = (a, b) -> a + b;
        System.out.println("Sum of 5 and 3: " + addNumbers.apply(5, 3)); // 8
        System.out.println("Sum of 10 and 20: " + addNumbers.apply(10, 20)); // 30

        // 1.6. Лямбда для Comparator - сравнение двух строк по длине
        Comparator<String> compareByLength = (str1, str2) -> Integer.compare(str1.length(), str2.length());
        System.out.println("Comparison of 'apple' and 'banana': " + compareByLength.compare("apple", "banana")); // 0 (одинаковая длина)
        System.out.println("Comparison of 'apple' and 'kiwi': " + compareByLength.compare("apple", "kiwi")); // 1 (apple длиннее)

    }
}