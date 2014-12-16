import business.domain._


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


//object AutowireServer extends autowire.Server[Customer, upickle.Reader[Customer], upickle.Writer[Customer]] {
//  def read[Result: upickle.Reader](p: Customer) = upickle.read[Result](p)
//
//  def write[Result: upickle.Writer](r: Result) = upickle.write(r)
//}


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
