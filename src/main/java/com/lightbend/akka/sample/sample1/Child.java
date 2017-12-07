package com.lightbend.akka.sample.sample1;

import akka.actor.ActorRef;

public class Child extends Person {
    ActorRef father;
    public Child(ActorRef father, Adn adn) {
        super(Gender.random());
        this.father = father;
        this.adn = adn;
    }

    @Override
    public void sayHello() {
        System.out.println("Waaa! Waaa! Waaa!");
    }
}
