package ru.otus;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import static com.google.common.collect.Iterables.*;

/**
 * To start the application:
 * ./gradlew build
 * java -jar ./hw01-gradle/build/libs/gradleHelloOtus-0.1.jar
 * <p>
 * To unzip the jar:
 * unzip -l hw01-gradle.jar
 * unzip -l gradleHelloOtus-0.1.jar
 */
public class HelloOtus {
    public static void print(Object... args) {
        Preconditions.checkNotNull(args, "There are no arguments to be printed");
        Arrays.stream(args).forEach(System.out::println);
    }

    public static void main(String... args) {
        List<String> immutableList = ImmutableList.of("Hello", "Petrelevich", "Strekalov", "Orudgev");
        Set<String> immutableList2 = Sets.newHashSet("Lapin", "Kucenko", "Kogan", "Otus");
        Iterable<String> concat = concat(immutableList, immutableList2);
        Iterable<String> skip = skip(concat, 1);
        Iterable<String> finalCollection = limit(skip, 6);

        StreamSupport.stream(finalCollection.spliterator(), false)
                .map(name -> Strings.padStart(name, 15, '.'))
                .forEach(HelloOtus::print);
    }
}
