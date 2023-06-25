package org.pw;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;

public class Main {

    record Person (String name, String lastName, int age){
        @Override
        public String toString() {
            return this.name + " " + lastName;
        }
    }

    public static void main(String[] args) {

        List<Person> people = new ArrayList<>(Arrays.asList(
                new Main.Person("Lucy", "Van Pelt", 27),
                new Person("Sally", "Brown", 31),
                new Person("Linus", "Van Pelt", 30),
                new Person("Peppermint", "Patty", 29),
                new Person("Charle", "Brown", 32)
        ));

        people.sort((p1, p2) -> p1.lastName().compareTo(p2.lastName()));
        people.forEach(p -> System.out.println(p));

        interface EnchancedComparator<T> extends Comparator<T> {
            int secondLevel(T o1, T o2);
        }

        var compartorMixed = new EnchancedComparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                int firstLevelComparison = o1.name.compareTo(o2.name);
                if (firstLevelComparison == 0) {
                    return secondLevel(o1, o2);
                }
                return firstLevelComparison;
            }

            @Override
            public int secondLevel(Person o1, Person o2) {
                return o1.age - o2.age;
            }
        };

        people.sort(compartorMixed);
        people.forEach(System.out::println);
    }

}