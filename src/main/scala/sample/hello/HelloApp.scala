package sample.hello

import akka.actor.ActorSystem

import com.typesafe.scalalogging.LazyLogging

object HelloApp extends App with LazyLogging {

   logger.info("starting actor system")
   val system = ActorSystem("HelloActorSystem")

   logger.info("starting top-level HelloWorld actor")
   system.actorOf(HelloWorldActor.props, "HelloWorldActor")

}
