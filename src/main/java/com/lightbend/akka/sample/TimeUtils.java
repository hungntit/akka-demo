package com.lightbend.akka.sample;

public class TimeUtils {
    public static void wait(int year) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < year * 1000) ;
        System.out.println(String.format(">>> %s years later <<<", year));

    }
}
