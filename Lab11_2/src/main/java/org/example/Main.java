package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    // Класс Person с использованием @JsonCreator для конструктора
    static class Person {
        private final String job;
        private final int salary;
        private final int id;
        private final String city;
        private final int year;
        private final int age;

        // Конструктор с аннотациями @JsonCreator и @JsonProperty
        @JsonCreator
        public Person(
                @JsonProperty("job") String job,
                @JsonProperty("salary") int salary,
                @JsonProperty("id") int id,
                @JsonProperty("city") String city,
                @JsonProperty("year") int year,
                @JsonProperty("age") int age) {
            this.job = job;
            this.salary = salary;
            this.id = id;
            this.city = city;
            this.year = year;
            this.age = age;
        }

        // Геттеры и сеттеры
        public String getJob() {
            return job;
        }

        public int getSalary() {
            return salary;
        }

        public String getCity() {
            return city;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "job='" + job + '\'' +
                    ", salary=" + salary +
                    ", id=" + id +
                    ", city='" + city + '\'' +
                    ", year=" + year +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {

        // Загружаем данные из файла data.json
        ObjectMapper objectMapper = new ObjectMapper();  // Инициализация объекта ObjectMapper
        List<Person> people = Arrays.asList(objectMapper.readValue(new File("data.json"), Person[].class));

        // 2.1. Простые операции

        // 2.1.1. Фильтрация по произвольному предикату (зарплата больше 100000)
        System.out.println("2.1.1. Фильтрация по предикату (зарплата > 100000):");
        List<Person> filtered = people.stream()
                .filter(p -> p.getSalary() > 100000)
                .toList();
        filtered.forEach(System.out::println);

        // 2.1.2. Сортировка по произвольному полю (по зарплате)
        System.out.println("\n2.1.2. Сортировка по зарплате:");
        List<Person> sorted = people.stream()
                .sorted(Comparator.comparingInt(Person::getSalary))
                .toList();
        sorted.forEach(System.out::println);

        // 2.1.3. Ограничение количества выводимых объектов (3 человека)
        System.out.println("\n2.1.3. Ограничение количества (3 человека):");
        List<Person> limited = people.stream()
                .limit(3)
                .toList();
        limited.forEach(System.out::println);

        // 2.1.4. Преобразование объекта в другой объект (должность и зарплата)
        System.out.println("\n2.1.4. Преобразование объекта (должность и зарплата):");
        List<String> jobSalaries = people.stream()
                .map(p -> p.getJob() + ": " + p.getSalary())
                .toList();
        jobSalaries.forEach(System.out::println);

        // 2.2. Комбинирование простых операций

        // 2.2.1. Топ-10 максимальных зарплат в опрошенных, младше 25 лет, в городе "Прага"
        String targetCity = "Прага"; // пример города
        System.out.println("\n2.2.1. Топ-10 максимальных зарплат в Праге, младше 25 лет:");
        List<Person> topSalaries = people.stream()
                .filter(p -> p.getAge() < 25 && p.getCity().equals(targetCity))
                .sorted(Comparator.comparingInt(Person::getSalary).reversed())
                .limit(10)
                .toList();
        topSalaries.forEach(System.out::println);

        // 2.2.2. Количество опрошенных, получающих зарплаты выше 50 тыс., по профессии "Оператор call-центра"
        String targetJob = "Оператор call-центра"; // пример профессии
        System.out.println("\n2.2.2. Количество людей с зарплатой выше 50 тыс. по профессии 'Оператор call-центра':");
        long countByJob = people.stream()
                .filter(p -> p.getSalary() > 50000 && p.getJob().equals(targetJob))
                .count();
        System.out.println("Количество: " + countByJob);

        // 2.2.3. Максимальная зарплата среди опрошенных из города "София", в возрасте от 25 до 40
        String targetCity2 = "София"; // пример города
        int minAge = 25, maxAge = 40;
        System.out.println("\n2.2.3. Максимальная зарплата среди опрошенных из города София, в возрасте от 25 до 40:");
        OptionalInt maxSalaryInSofia = people.stream()
                .filter(p -> p.getCity().equals(targetCity2) && p.getAge() >= minAge && p.getAge() <= maxAge)
                .mapToInt(Person::getSalary)
                .max();
        maxSalaryInSofia.ifPresent(s -> System.out.println("Максимальная зарплата: " + s));

        // 2.2.4. Минимальный возраст среди опрошенных из города "Прага", получающих зарплату выше 100 тыс.
        String targetCity3 = "Прага"; // пример города
        int minSalary = 100000;
        System.out.println("\n2.2.4. Минимальный возраст среди опрошенных из Праги, получающих зарплату > 100 тыс.:");
        OptionalInt minAgeInPrague = people.stream()
                .filter(p -> p.getCity().equals(targetCity3) && p.getSalary() > minSalary)
                .mapToInt(Person::getAge)
                .min();
        minAgeInPrague.ifPresent(age -> System.out.println("Минимальный возраст: " + age));

        // 2.3. Группировки

        // 2.3.1. Сгруппировать все данные по произвольному строковому полю (по профессии)
        System.out.println("\n2.3.1. Группировка по профессии:");
        Map<String, List<Person>> groupedByJob = people.stream()
                .collect(Collectors.groupingBy(Person::getJob));
        groupedByJob.forEach((job, persons) -> {
            System.out.println(job + ":");
            persons.forEach(System.out::println);
        });

        // 2.3.2. Сгруппировать все данные по произвольному строковому полю и подсчитать количество элементов в каждой группе (по городу)
        System.out.println("\n2.3.2. Группировка по городу с подсчетом количества:");
        Map<String, Long> countByCity = people.stream()
                .collect(Collectors.groupingBy(Person::getCity, Collectors.counting()));
        countByCity.forEach((city, cityCount) -> System.out.println(city + ": " + cityCount));

        // 2.3.3. Сгруппировать все данные по городам, подсчитав максимальную зарплату, полученную в нем
        System.out.println("\n2.3.3. Группировка по городам с максимальной зарплатой:");
        Map<String, Optional<Integer>> maxSalaryByCity = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getCity,
                        Collectors.mapping(Person::getSalary, Collectors.maxBy(Integer::compareTo))
                ));
        maxSalaryByCity.forEach((city, maxSalary) ->
                System.out.println(city + ": " + (maxSalary.isPresent() ? maxSalary.get() : "Нет данных"))
        );

        // 2.3.4. Сгруппировать все данные по городам, подсчитав среднюю зарплату по каждой профессии
        System.out.println("\n2.3.4. Группировка по городам с средней зарплатой по профессии:");
        Map<String, Map<String, Double>> averageSalaryByCityAndJob = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getCity,
                        Collectors.groupingBy(
                                Person::getJob,
                                Collectors.averagingInt(Person::getSalary)
                        )
                ));
        averageSalaryByCityAndJob.forEach((city, jobAvgSalaries) -> {
            System.out.println(city + ":");
            jobAvgSalaries.forEach((job, avgSalary) ->
                    System.out.println("  " + job + ": " + avgSalary));
        });
    }
}