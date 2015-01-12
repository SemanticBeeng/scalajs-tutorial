package business

// Business shared

import business.domain._

// Scala concurrency

import scala.concurrent.Future

// Autowire macro enabling

import autowire._

// UI specific

import scalatags.JsDom.all._
import org.scalajs.dom

// Scala.js specific

import scala.scalajs.js.annotation.JSExport
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

/**
 *
 */
@JSExport
object ClientUsage {
  @JSExport
  def main(): Unit = {

    val customer1: Customer = Customer(100, "Joe", "Smith", List())
    ///val customer2: Customer = Customer(0, "Joe", "Smith", List(EmailAddress("joe", "smith.com")))

    // Variable declared just to show that AutoWire wraps in a Future the return value of the calls
    val call1: Future[Either[Customer, List[BusinessException]]] = Client[CustomerMgmtService].
      registerCustomer(customer1).call()

    //val inputBox = input.render
    val outputBox = div.render

    /**
     *
     */
    def updateOutput(call1: Future[Either[Customer, List[BusinessException]]]) = {

      call1.foreach { customerOrErrors =>

        outputBox.innerHTML = ""
        outputBox.appendChild(
          ul(
            if (customerOrErrors.isRight) {
              for (e <- customerOrErrors.right.get) yield {
                li(BusinessMessages.messageFor(e.errorCode))
              }
            } else {
              li("Registration worked and new id is " + customerOrErrors.left.get.id)
            }
          ).render
        )
      }
    }
//    inputBox.onkeyup = { (e: dom.Event) =>
//      updateOutput()
//    }

    updateOutput(call1)
    dom.document.body.appendChild(
      div(
        cls := "container",
        h1("Results container"),
        outputBox
      ).render
    )
  }
}
