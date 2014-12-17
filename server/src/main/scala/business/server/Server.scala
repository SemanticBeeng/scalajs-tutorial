import business.domain._
import upickle.{Reader, Writer}


object CustomerMgmtServiceImpl extends CustomerMgmtService with CustomerValidator {

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


object AutowireServer extends autowire.Server[String, upickle.Reader[_], upickle.Writer[_]] {
  def read[Result: upickle.Reader](p: String) = upickle.read[Result](p)

  def write[Result: upickle.Writer](r: Result) = upickle.write(r)

  val routes = AutowireServer.route[CustomerMgmtService](CustomerMgmtServiceImpl)

//  override def read[Result](p: String)(implicit evidence$3: Reader[_])
//
//  override def write[Result](r: Result)(implicit evidence$4: Writer[_])
}


//object Server extends SimpleRoutingApp with CustomerMgmtService {
//  def main(args: Array[String]): Unit = {
//    //implicit val system = ActorSystem()
//    startServer("0.0.0.0", port = 8080) {
//      get{
//        pathSingleSlash {
//          complete{
//            HttpEntity(
//              MediaTypes.`text/html`,
//              Template.txt
//            )
//          }
//        } ~
//          getFromResourceDirectory("")
//      } ~
//        post {
//          path("api" / Segments){ s =>
//            extract(_.request.entity.asString) { e =>
//              complete {
//                AutowireServer.route[Api](Server)(
//                  autowire.Core.Request(s, upickle.read[Map[String, String]](e))
//                )
//              }
//            }
//          }
//        }
//    }
//  }
//
//  def list(path: String): Seq[String] = {
//    val chunks = path.split("/", -1)
//    val prefix = "./" + chunks.dropRight(1).mkString("/")
//    val files = Option(new java.io.File(prefix).list()).toSeq.flatten
//    files.filter(_.startsWith(chunks.last))
//  }
//}
