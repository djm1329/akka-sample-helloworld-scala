package sample.hello

import akka.actor.ActorSystem

import com.typesafe.scalalogging.LazyLogging
import _root_.sample.hello.sample.sharding.HttpServer

object HelloApp extends App with LazyLogging {

   logger.info("starting actor system")
   implicit val system = ActorSystem("HelloActorSystem")

   logger.info("starting top-level HelloWorld actor")
   val helloActor = system.actorOf(HelloWorldActor.props, "HelloWorldActor")

   logger.info("starting HTTP server")
   HttpServer(helloActor)

}
