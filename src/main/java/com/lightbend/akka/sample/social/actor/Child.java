package com.lightbend.akka.sample.social.actor;

import akka.actor.ActorRef;
import com.lightbend.akka.sample.social.common.Adn;

public class Child extends Person {
    ActorRef father;
    public Child(ActorRef father, Adn adn) {
        super(Gender.random());
        this.father = father;
        this.adn = adn;
    }

    @Override
    public void sayHello() {
        System.out.println("\uD83D\uDC76: Waaa! Waaa! Waaa!");
    }
}
