package sample.hello

import akka.actor._
import ch.qos.logback.core.pattern.color.GreenCompositeConverter

object HelloWorld {
   def props(): Props = Props(new HelloWorld())

   case object AskForGreeting
}

class HelloWorld extends Actor with ActorLogging {

   import HelloWorld._
   import Greeter._

   // create the greeter actor
   val greeter: ActorRef = context.actorOf(Greeter.props(), "greeter")

   override def preStart(): Unit = {
      // tell the greeter to perform the greeting
      greeter ! Greet
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
