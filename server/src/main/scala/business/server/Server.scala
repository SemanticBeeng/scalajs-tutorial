package business.server

import akka.actor.ActorSystem
import business.domain._
import spray.http.{HttpEntity, MediaTypes}
import spray.routing.SimpleRoutingApp
import upickle._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 *
 */
trait CustomerMgmtServiceImpl extends CustomerMgmtService with CustomerValidator {

  /**
   *
   * @param customer
   * @return
   */
  override def registerCustomer(customer: Customer): Either[Customer, List[BusinessException]] = {

    val errors: List[BusinessException] = validate(customer)

    if (errors.isEmpty)
      Left(internalPersist(customer))
    else
      Right(errors)
  }

  /**
   *
   * @param customer
   */
  def internalPersist(customer: Customer): Customer = {

    print("Persisting " + customer.firstName)
    Customer(10, customer.firstName, customer.lastName, customer.emails)
  }
}

/**
 *
 */
object AutowireServer extends autowire.Server[String, upickle.Reader, upickle.Writer] {

  def read[Result: upickle.Reader](p: String) = upickle.read[Result](p)

  def write[Result: upickle.Writer](r: Result) = upickle.write(r)

  //val routes = AutowireServer.route[CustomerMgmtService](CustomerMgmtServiceImpl)
}

/**
 *
 */
object Server extends SimpleRoutingApp with CustomerMgmtServiceImpl {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem()

    startServer("0.0.0.0", port = 8080) {
      get {
        pathSingleSlash {
          complete {
            HttpEntity(
              MediaTypes.`text/html`,
              Template.txt
            )
          }
        } ~
          getFromResourceDirectory("")
      } ~
        post {
          path("api" / Segments) { s =>
            extract(_.request.entity.asString) { e =>
              complete {
                AutowireServer.route[CustomerMgmtService](Server)(
                  autowire.Core.Request(s, upickle.read[Map[String, String]](e))
                )
              }
            }
          }
        }
    }
  }
}

object Template{
  import scalatags.Text.all._
  import scalatags.Text.tags2.title
  val txt =
    "<!DOCTYPE html>" +
      html(
        head(
          title("Example Scala.js application"),
          meta(httpEquiv:="Content-Type", content:="text/html; charset=UTF-8"),
          script(`type`:="text/javascript", src:="/client-fastopt.js"),
          script(`type`:="text/javascript", src:="//localhost:12345/workbench.js") //@todo what does this do?
//          link(
//            rel:="stylesheet",
//            `type`:="text/css",
//            href:="META-INF/resources/webjars/bootstrap/3.2.0/css/bootstrap.min.css"
//          )
        ),
        body(margin:=0)(
          script("ClientUsage().main()")
        )
      )
}