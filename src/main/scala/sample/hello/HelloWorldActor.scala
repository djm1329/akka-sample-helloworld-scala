package sample.hello

import akka.actor._

object HelloWorldActor {
   def props(): Props = Props(new HelloWorldActor())
}

class HelloWorldActor extends Actor with ActorLogging {

  override def preStart(): Unit = {
     // create the greeter actor
     val greeter: ActorRef = context.actorOf(GreeterActor.props(), "greeter")
     // tell it to perform the greeting
     greeter ! GreeterActor.Greet
  }

  def receive = {
     // when the greeter is done, stop this actor and with it the application
     case GreeterActor.Done => context.stop(self)
  }

  override def postStop(): Unit = {
     log.info("The greeter has done its job ... shutting down the actor system.")
     context.system.terminate()
  }
}
