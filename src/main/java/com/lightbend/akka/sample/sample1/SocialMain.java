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
      System.out.println(">>> 2 years later <<<");
      System.in.read();
      neighbourMan.tell(new Person.FallInLove(woman),ActorRef.noSender());
      System.out.println(">>> Press ENTER to exit <<<");
      System.in.read();
    } catch (IOException ioe) {
    } finally {
      system.terminate();
    }
  }
}
