package com.flockinger.squidward.endpoint;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import scala.concurrent.Await;
import scala.concurrent.Future;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.testkit.JavaTestKit;
import akka.testkit.TestProbe;
import akka.util.Timeout;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class MainTentacleSystemTest {

	private static ActorSystem system;

	private final static Timeout TIMEOUT = new Timeout(5, TimeUnit.SECONDS);
	private final static String CONSUMER_PATH = "/user/consumer";

	@BeforeClass
	public static void setup() {
		Config config = ConfigFactory.load("test.conf");
		MainTentacleSystem mainTentacle = new MainTentacleSystem();
		system = mainTentacle.getTentacleSystem(config);

		system.actorOf(Props.create(MessageConsumer.class),
				"consumer");
		system.actorOf(Props.create(ResultProducer.class),
				"producer");
	}

	@AfterClass
	public static void teardown() {
		JavaTestKit.shutdownActorSystem(system);
		system = null;
	}

	@Test
	public void startUpActorSystemTest() throws Exception {
		new JavaTestKit(system) {
			{
				TestProbe clusterProbe = new TestProbe(system);
				Cluster.get(system).subscribe(clusterProbe.ref(),
						ClusterEvent.MemberUp.class);
				clusterProbe
						.expectMsgClass(ClusterEvent.CurrentClusterState.class);

				Address clusterAddress = Cluster.get(system).selfAddress();
				Cluster.get(system).join(clusterAddress);
				clusterProbe.expectMsgClass(ClusterEvent.MemberUp.class);
			}
		};
	}

	@Test
	public void sendMessageToConsumerAndEchoBackForConfirmationTest() {
		new JavaTestKit(system) {
			{
				final JavaTestKit results = new JavaTestKit(system);

				new AwaitAssert(duration("10 seconds")) {
					protected void check() {
						ActorRef ref = getActorRefFromPath(CONSUMER_PATH);

						ref.tell("Test Message", results.getRef());
						results.expectMsgEquals("Message Received: Test Message");
					}
				};
			}
		};
	}

	private ActorRef getActorRefFromPath(String path) {
		ActorSelection sel = system.actorSelection(CONSUMER_PATH);
		Future<ActorRef> fut = sel.resolveOne(TIMEOUT);
		ActorRef ref = null;

		try {
			ref = Await.result(fut, TIMEOUT.duration());
		} catch (Exception e) {
		}

		return ref;
	}
}
