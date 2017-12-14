package com.lightbend.akka.sample.sample1;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.lightbend.akka.sample.Greeter;
import com.lightbend.akka.sample.Greeter.Greet;
import com.lightbend.akka.sample.Greeter.WhoToGreet;
import com.lightbend.akka.sample.Printer;

import java.io.IOException;

public class SocialMain {

    private static void wait(int year) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < year * 1000) ;
        System.out.println(String.format(">>> %s years later <<<", year));

    }

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("social");
        try {
            //#create-actors
            final ActorRef man =
                    system.actorOf(Props.create(Man.class), "ClarkKen");
            final ActorRef woman =
                    system.actorOf(Props.create(Woman.class), "Lana");
            final ActorRef neighbourMan =
                    system.actorOf(Props.create(Man.class), "NeighBourMan");

            woman.tell(new Person.ILoveYou(Person.Gender.male), man);
            //#main-send-messages

            wait(2);
            neighbourMan.tell(new Person.FallInLove(woman), ActorRef.noSender());
            wait(10);
        } finally {
            system.terminate();
        }
    }
}
