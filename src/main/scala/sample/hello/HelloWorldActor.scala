package sample.hello

import akka.actor._
import ch.qos.logback.core.pattern.color.GreenCompositeConverter

object HelloWorldActor {
   def props(): Props = Props(new HelloWorldActor())

   case object AskForGreeting
}

class HelloWorldActor extends Actor with ActorLogging {

   import HelloWorldActor._
   import GreeterActor._

   // create the greeter actor
   val greeter: ActorRef = context.actorOf(GreeterActor.props(), "greeter")

   override def preStart(): Unit = {
      // tell the greeter to perform the greeting
      // greeter ! Greet
   }

  def receive = {
     case AskForGreeting => greeter forward Greet
     case Done => 
  }

  override def postStop(): Unit = {
     log.info("The greeter has done its job ... shutting down the actor system.")
     context.system.terminate()
  }
}
