package business.domain
// must be here
import scala.scalajs.js.annotation.JSExport

@JSExport("domain.EmailAddress")
case class EmailAddress(name: String, domain: String)

@JSExport("domain.Customer")
case class Customer(id: Long, firstName: String, lastName: String, emails: List[EmailAddress])

@JSExport("domain.BusinessException")
case class BusinessException(errorCode: Int)

/**
 *
 */
object BusinessMessages {

  final val CUSTOMER_ALREADY_EXISTS:Int = 1001
  final val NO_CONTACT_INFO = 1002

  def messageFor(errorCode: Int) = errorCode match {
    case CUSTOMER_ALREADY_EXISTS => "Customer already exists"
    case NO_CONTACT_INFO => "No contact info provided"
    case _ => "Unknown error"
  }
}

trait CustomerMgmtService {

  def registerCustomer(customer: Customer): Either[Customer, List[BusinessException]]
}

trait CustomerValidator {

  def validate(customer: Customer) : List[BusinessException] = {
    var errors = List[BusinessException]()

    customer match {

      case Customer(id, _, _, _) if id != 0 => errors = errors :+ BusinessException(BusinessMessages
        .CUSTOMER_ALREADY_EXISTS)

      case Customer(_, _, _, List()) => errors = errors :+ BusinessException(BusinessMessages.NO_CONTACT_INFO)

      //case _ => Left(internalPersist(customer))
    }
    errors
  }

}
@JSExport("domain.CustomerValidator")
object CustomerValidator extends CustomerValidator
