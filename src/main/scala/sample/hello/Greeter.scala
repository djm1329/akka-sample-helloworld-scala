package sample.hello

import akka.actor._

object Greeter {
  def props(): Props = Props(new Greeter())

  case object Greet
  case object Done
}

class Greeter extends Actor with ActorLogging {

  import Greeter._

  def receive = {
    case Greet =>
      log.info("Hello World!")
      sender() ! Done
  }

}
