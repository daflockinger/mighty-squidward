package com.flockinger.squidward.endpoint;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.AllDeadLetters;
import akka.actor.DeadLetter;
import akka.actor.Props;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class MainTentacleSystem {
	
	private final static String ACTOR_SYSTEM_NAME = "MightySquidward";
	
	public static void main(String[] args) throws InterruptedException {
		MainTentacleSystem tentacleSystem = new MainTentacleSystem();
		
		//startup 2 actor systems for failover
		tentacleSystem.createActorSystem("2551");
		tentacleSystem.createActorSystem("2552");
	}
	
	public void createActorSystem(String port){
		Config conf = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
		        withFallback(ConfigFactory.load());
		ActorSystem system = getTentacleSystem(conf);
		
		ActorRef consumerRef = system.actorOf(Props.create(MessageConsumer.class),"consumer");
		system.actorOf(Props.create(ResultProducer.class),"producer");
		
		// add dead letter stuff
		system.eventStream().subscribe(system.actorOf(Props.create(DeadLetterListener.class),"deadLetter"), AllDeadLetters.class);
	}
	
	public ActorSystem getTentacleSystem(Config conf){
		return ActorSystem.create(ACTOR_SYSTEM_NAME, conf);
	}
}
