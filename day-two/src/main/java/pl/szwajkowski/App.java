/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package pl.szwajkowski;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        Map<Type, Integer> collect = Files.readAllLines(Paths.get("input.txt"))
                .stream()
                .map(App::countLetters)
                .filter(t -> t != Type.NONE)
                .collect(Collectors.toMap(Function.identity(), a -> 1, (a, b) -> a + b));

        System.out.println((collect.get(Type.TWO) + collect.get(Type.BOTH)) * (collect.get(Type.THREE) + collect.get(Type.BOTH)));

        List<String> strings = Files.readAllLines(Paths.get("input.txt"));
        for (int i = 0; i < strings.size(); i++) {
            for (int j = 1; j < strings.size(); j++) {
                long count = zip(strings.get(i), strings.get(j)).stream()
                        .map(t -> t._1.equals(t._2))
                        .filter(b -> !b)
                        .count();
                if(count == 1) {
                    System.out.println(strings.get(i));
                    System.out.println(strings.get(j));
                }
            }
        }

    }

    private static Type countLetters(String line) {
        Map<Character, Integer> collect = line.chars().mapToObj(i -> (char) i)
                .collect(Collectors.toMap(a -> a, a -> 1, (a, b) -> a + b));

        Optional<Integer> two = collect.values().stream().filter(i -> i == 2).findAny();
        Optional<Integer> three = collect.values().stream().filter(i -> i == 3).findAny();
        if (two.isPresent() && three.isPresent()) {
            return Type.BOTH;
        }
        if (two.isPresent()) {
            return Type.TWO;
        }
        if (three.isPresent()) {
            return Type.THREE;
        }
        return Type.NONE;
    }

    public enum Type {
        TWO, THREE, BOTH, NONE
    }

    private static List<Tuple> zip(String a, String b) {
        ImmutableList<Character> characters = Lists.charactersOf(a);
        ImmutableList<Character> charactersB = Lists.charactersOf(b);
        List<Tuple> list = new ArrayList<>();
        for (int i = 0; i < a.length(); i++) {
            list.add(new Tuple(characters.get(i).toString(), charactersB.get(i).toString()));
        }
        return list;
    }

    private static class Tuple {
        public String _1;
        public String _2;

        public Tuple(String _1, String _2) {
            this._1 = _1;
            this._2 = _2;
        }
    }
}
