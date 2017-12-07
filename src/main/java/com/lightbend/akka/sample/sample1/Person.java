package com.lightbend.akka.sample.sample1;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Person extends AbstractActor {
    Adn adn;
    Gender gender;
    ActorRef lover;
    List<ActorRef> children = new ArrayList<>();

    @Override
    public void postStop() throws Exception {
        super.postStop();
        System.out.println("Stop actor " + getSelf().path().name());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(ILoveYou.class, iLoveYou -> {
            if (!hasLover() && iLoveYou.ownerGender != this.gender) {
                System.out.println(String.format("%s says: `I love you` to %s", getSender().path().name(), this.getSelf().path().name()));
                this.lover = getSender();
                this.lover.tell(new ILoveYou(this.gender), getSelf());
            }
        }).match(Message.class, message -> {
            System.out.println(String.format("%s receive from %s: %s", this.getSelf().path().name(),
                                                                        getSender().path().name(),
                                                                        message.message));
        }).build();
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        System.out.println(String.format("-------PATH:%s--------", getSelf().path()));
        sayHello();
    }

    public Person(Gender gender, Adn adn) {
        this.gender = gender;
        this.adn = adn;
    }

    public Person(Gender gender) {
        this.gender = gender;
        this.adn = new Adn();
    }

    public abstract void sayHello();

    boolean hasLover() {
        return lover != null;
    }

    public static enum Gender {
        male, female;

        public static Gender random() {
            return Gender.values()[new Random().nextInt(Gender.values().length)];
        }
    }

    public static class Ring {
        int price; //in USD

        public Ring(int price) {
            this.price = price;
        }
    }

    public static class MarryMe {
        String sentence;
        Ring ring;

        public MarryMe(String sentence, Ring ring) {
            this.sentence = sentence;
            this.ring = ring;
        }

    }

    public static class AnswerMarriage {
        boolean accepted = false;

        public AnswerMarriage(boolean accepted) {
            this.accepted = accepted;
        }
    }

    public static class ILoveYou {
        Gender ownerGender;

        public ILoveYou(Gender ownerGender) {
            this.ownerGender = ownerGender;
        }
    }


    public static class WannaCreateBaby {
        Adn senderAdn;

        public WannaCreateBaby(Adn senderAdn) {
            this.senderAdn = senderAdn;
        }
    }

    public static class BornBaby {
        Adn babyAdn;
        ActorRef baby;

        public BornBaby(ActorRef baby, Adn babyAdn) {
            this.baby = baby;
            this.babyAdn = babyAdn;
        }
    }

    public static class Message {
        String message;

        Message(String message) {
            this.message = message;
        }
    }

    public static class FallInLove{
        ActorRef girl;
        FallInLove(ActorRef girl) {
            this.girl = girl;
        }
    }
}
