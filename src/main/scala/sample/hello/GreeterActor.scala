package sample.hello

import akka.actor._

object GreeterActor {
  def props(): Props = Props(new GreeterActor())

  case object Greet
  case object Done
}

class GreeterActor extends Actor with ActorLogging {

  import GreeterActor._

  def receive = {
    case Greet =>
      log.info("Hello World!")
      sender() ! Done
  }

}
