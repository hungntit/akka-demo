package com.lightbend.akka.sample.sumsquare.program;

import com.lightbend.akka.sample.HostnameUtils;
import com.lightbend.akka.sample.sumsquare.actor.clusterrouter.ClusterRouterActor;
import com.lightbend.akka.sample.sumsquare.actor.worker.SquareActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class ClusterBackend {
    private final static int WORKER_SIZE = 50;

    public static void main(String[] args) {

        final int port = HostnameUtils.generatePort(2551, 3000);
        System.out.println("Running Backend in Port: " + port);
        final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
                withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]")).
                withFallback(ConfigFactory.load().getConfig("clustersumsquare"));

        ActorSystem system = ActorSystem.create("application", config);
        system.actorOf(Props.create(ClusterRouterActor.class, WORKER_SIZE, Props.create(SquareActor.class).withDispatcher("examdispatcher")).withDispatcher("examdispatcher"), "workers");
    }
}
