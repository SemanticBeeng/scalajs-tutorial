package business.domain

case class EmailAddress(name: String, domain: String)

case class Customer(firstName: String, lastName: String, emails: List[EmailAddress])

case class BusinessException(errorCode: Int)

trait BusinessMessages {
  val CUSTOMER_ALREADY_EEXISTS = "Customer already exists"
  val NO_CONTACT_INFO = "No contact info provided"
}

trait CustomerMgmtService {

  def registerCustomer(customer: Customer): Either[Customer, List[BusinessException]]
}

