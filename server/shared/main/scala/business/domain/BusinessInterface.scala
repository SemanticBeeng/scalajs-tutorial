package business.domain

/**
 * See more about exporting and interoperability here
 * http://www.scala-js.org/doc/export-to-javascript.html
 * http://www.scala-js.org/doc/js-interoperability.html
 * https://groups.google.com/forum/#!topic/scala-js/knNTjVcss8E
 *
 */

import scala.annotation.meta.field
import scala.scalajs.js.annotation.JSExport

@JSExport
case class EmailAddress(@(JSExport @field) name: String,  @(JSExport @field) domain: String)

@JSExport("Customer")
case class Customer(id: Long, firstName: String, lastName: String, emails: List[EmailAddress])

@JSExport("BusinessException")
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
@JSExport("CustomerValidator")
object CustomerValidator extends CustomerValidator

