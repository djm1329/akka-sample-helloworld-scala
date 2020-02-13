package sample.hello

package sample.sharding

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.{Success, Failure}

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import scala.language.postfixOps
import akka.pattern.ask
import com.typesafe.scalalogging.LazyLogging

object HttpServer {
    def apply(helloActor: ActorRef)(implicit system: ActorSystem) = new HttpServer(helloActor)
}

class HttpServer(helloActor: ActorRef)(implicit system: ActorSystem) extends LazyLogging {
    import HelloWorldActor._

    implicit val ec: ExecutionContext = system.dispatcher
    implicit val timeout = Timeout(2 seconds)

    val route =
        (get & path("hello")) {
            val f = (helloActor ? AskForGreeting)
            onComplete(f) {
                case Failure(ex) => complete(HttpResponse(StatusCodes.BadRequest, entity = ex.toString))
                case Success(GreeterActor.Done) => complete(StatusCodes.OK -> s"greeter says hello\n")
                case Success(_) => complete(StatusCodes.InternalServerError)
            }
        }
  
    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)
    bindingFuture.onComplete {
        case Success(binding) =>
            logger.info("HttpServer started, ready to service requests on " + binding.localAddress.toString)
        case Failure(ex) => logger.error("Failed to start HttpServer", ex) 
    }
}



