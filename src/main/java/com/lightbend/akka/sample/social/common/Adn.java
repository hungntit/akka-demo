package com.lightbend.akka.sample.social.common;

import java.util.*;

public class Adn {

    long[] values = new long[100];

    public Adn(long value) {
        Arrays.fill(values, value);
    }

    private Adn(long[] values) {
        this.values = values;
    }
    public Adn() {
         this(new Random().nextLong());
    }

    public static Adn combine(Adn adn1, Adn adn2) {
        List<Long> values = new ArrayList<>();

        for(int i = 0; i< 100; i++) {
            if(i % 2 == 0) {
                values.add(adn1.values[i]);
            } else {
                values.add(adn2.values[i]);
            }
        }
        Collections.shuffle(values);
        return new Adn(values.stream().mapToLong(i -> i.longValue()).toArray());
    }

    private static double getPercentage(long[] arrayA, long[] arrayB) {
        List<Long> listA = toList(arrayA);
        List<Long> listB = toList(arrayB);
        double percentage=0;
        for(int i = listA.size() - 1; i>=0 ; i--) {
            int idxOfB = listB.indexOf(listA.get(i));
            if(idxOfB > -1) {
                percentage ++;
                listB.remove(idxOfB);
            }

        }
        System.out.println("Percent: " + percentage);
        return (percentage/arrayA.length)*100; /*return the amount of times over the length times 100*/
    }

    public static boolean isParentChild(Adn adn1, Adn adn2) {
        return getPercentage(adn1.values, adn2.values) >= 0.50;
    }

    private static List<Long> toList(long[] array) {
        List<Long> rs = new ArrayList<>();
        for(long l : array) {
            rs.add(l);
        }
        return rs;
    }
}
