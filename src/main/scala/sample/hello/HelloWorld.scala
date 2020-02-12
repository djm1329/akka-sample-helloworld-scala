package sample.hello

import akka.actor._

object HelloWorld {
   def props(): Props = Props(new HelloWorld())
}

class HelloWorld extends Actor with ActorLogging {

  override def preStart(): Unit = {
     // create the greeter actor
     val greeter: ActorRef = context.actorOf(Greeter.props(), "greeter")
     // tell it to perform the greeting
     greeter ! Greeter.Greet
  }

  def receive = {
     // when the greeter is done, stop this actor and with it the application
     case Greeter.Done => context.stop(self)
  }

  override def postStop(): Unit = {
     log.info("The greeter has done its job ... shutting down the actor system.")
     context.system.terminate()
  }
}
