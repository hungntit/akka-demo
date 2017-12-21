package com.lightbend.akka.sample.social;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.lightbend.akka.sample.TimeUtils;
import com.lightbend.akka.sample.social.actor.Man;
import com.lightbend.akka.sample.social.actor.Person;
import com.lightbend.akka.sample.social.actor.Woman;

public class SocialMain {


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

            TimeUtils.wait(2);
            neighbourMan.tell(new Person.FallInLove(woman), ActorRef.noSender());
            TimeUtils.wait(10);
            System.out.println("End of the world");
        } finally {
            system.terminate();
        }
    }
}
