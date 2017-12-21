package com.lightbend.akka.sample.sumsquare.actor.manager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import com.lightbend.akka.sample.sumsquare.message.sumsquare.SquareReq;
import com.lightbend.akka.sample.sumsquare.message.sumsquare.SumSquareReq;
import com.lightbend.akka.sample.sumsquare.message.sumsquare.SumSquareResp;

public class SumSquareTaskActor extends AbstractActor {
    private ActorRef routerWorker;
    private int working = 0;
    private int completed = 0;
    private ActorRef sender;
    private long result = 0;
    private int input;

    public SumSquareTaskActor(ActorRef routerWorker) {
        this.routerWorker = routerWorker;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(SumSquareReq.class, sumSquareReq -> {
            this.sender = this.getSender();
            input = sumSquareReq.getNumber();
            for (int i = 0; i < input; i++) {
                working++;
                //send job to worker
                this.routerWorker.tell(new SquareReq(i), getSelf());
            }
        }).match(Long.class, aLong -> {
            //System.out.println("Collect Result from worker with value: " + aLong);
            result += aLong;
            completed ++;
            if (completed == working) {
                //completed! send result to client
                this.sender.tell(new SumSquareResp(input, result), getSelf());
                // drink poison and kill itself
                getSelf().tell(PoisonPill.getInstance(), getSelf());
            }
        }).build();
    }
}
