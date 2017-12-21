package com.lightbend.akka.sample.social.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import com.lightbend.akka.sample.social.common.Adn;

public class Woman extends Person {

    ActorRef husband;

    public Woman() {
        super(Gender.female);
    }

    @Override
    public Receive createReceive() {
        return super.createReceive().orElse(receiveBuilder().match(ILoveYou.class, iLoveYou -> {
            if (!hasLover() && iLoveYou.ownerGender != this.gender) {
                System.out.println(String.format("%s says: `I love you` to %s", getSender().path().name(), this.getSelf().path().name()));
                this.lover = getSender();
                this.lover.tell(new ILoveYou(this.gender), getSelf());
            }
        }).match(MarryMe.class, wannaMarie -> {
            System.out.println(String.format("%s give a \uD83D\uDC8D to %s", this.getSender().path().name(), this.getSelf().path().name()));
            System.out.println(String.format("%s said that `%s`", this.getSender().path().name(), wannaMarie.sentence));
            if (getSender().equals(this.lover) && wannaMarie.ring.price > 1000) {

                System.out.println(String.format("%s thought `He love me so much!`", this.getSelf().path().name()));

                husband = lover;
                husband.tell(new AnswerMarriage(true), getSelf());
            } else {
                System.out.println(String.format("%s says `I don't accept you!` (\uD83D\uDC8D price: %sUSD)", this.getSelf().path().name(), wannaMarie.ring.price));
                getSender().tell(new AnswerMarriage(false), getSelf());
            }
        }).match(WannaCreateBaby.class, wannaCreateBaby -> {
            System.out.println(String.format("%s wanna have baby with %s", this.getSender().path().name(), this.getSelf().path().name()));
            // birth baby
            Adn babyADN = Adn.combine(wannaCreateBaby.senderAdn, this.adn);
            ActorRef baby = this.getContext().actorOf(Props.create(Child.class, getSender(), babyADN));
            //tell to her husband
            if (husband != null) {
                husband.tell(new BornBaby(baby, babyADN), getSelf());
            }

        }).match(Message.class, message -> {
            System.out.println(String.format("%s receive from %s: %s", this.getSelf().path().name(),
                    getSender().path().name(),
                    message.message));
        }).build());
    }

    @Override
    public void sayHello() {
        System.out.println(String.format("I'm %s. I'm beautiful \uD83D\uDC8B", getSelf().path().name()));

    }


}
