package com.lightbend.akka.sample.sumsquare.program;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;

public class ClusterLeader {
	 public static void main(String[] args) {
		
		 final String port = "2551";
		    final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
		      withFallback(ConfigFactory.parseString("akka.cluster.roles = []")).
		      withFallback(ConfigFactory.load().getConfig("clustersumsquare"));
		 ;
		
		ActorSystem.create("application",config);
		System.out.println("I'm a seed node");
		}
}
