package com.lightbend.akka.sample.social.actor;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import com.lightbend.akka.sample.social.common.Adn;

public class Man extends Person {

    ActorRef wife;


    public Man() {
        super(Gender.male);
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder().match(ILoveYou.class, iLoveYou -> {
            if (!hasLover() && iLoveYou.ownerGender != this.gender) {
                System.out.println(String.format("%s says: `I love you` to %s", getSender().path().name(), this.getSelf().path().name()));
                this.lover = getSender();
                this.lover.tell(new MarryMe("I bought this ring in Shop, Do you want to mary me?", new Ring(500)), getSelf());
            }
        }).match(AnswerMarriage.class, answer -> {
            if (answer.accepted) {
                System.out.println(String.format(" `I agree!` %s said to %s", getSender().path().name(), this.getSelf().path().name()));
                wife = lover;
                wife.tell(new WannaCreateBaby(this.adn), getSelf());
            } else {
                getSender().tell(new MarryMe("I love you so much. I give all I have to you. Do you wanna mary me?", new Ring(2000)), getSelf());
            }
        }).match(BornBaby.class, bornBaby -> {
            if(Adn.isParentChild(this.adn, bornBaby.babyAdn)) {
                System.out.println(String.format("%s say: I love you, %s", getSelf().path().name(), bornBaby.baby.path().name()));
            } else {
                System.out.println(String.format("%s say: this baby doesn't like me. I'm feeling sad", getSelf().path().name()));
                getSender().tell(new Message("I'm so sad. I want to drink poison. I hate you"), getSelf());
                getSelf().tell(PoisonPill.getInstance(), ActorRef.noSender());

            }
        }).match(FallInLove.class, fallInLove -> {
            fallInLove.girl.tell(new WannaCreateBaby(this.adn), getSelf());
        }).build();
    }

    @Override
    public void postStop() throws Exception {
        System.out.println(String.format("%s:Good bye all!", getSelf().path().name()));
        super.postStop();
    }

    @Override
    public void sayHello() {
        System.out.println(String.format("I'm %s. I'm strong", getSelf().path().name()));
    }
}
