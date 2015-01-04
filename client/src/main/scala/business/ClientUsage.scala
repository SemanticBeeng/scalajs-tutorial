package business

import autowire._
import business.domain._
import org.scalajs.dom

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

/**
 *
 */
@JSExport
object ClientUsage {
  @JSExport
  def main(): Unit = {

    val inputBox = input.render
    val outputBox = div.render
    //List(EmailAddress("joe", "smith.com"))
    def updateOutput() = {

      val call: Future[Either[Customer, List[BusinessException]]] = Client[CustomerMgmtService].
        registerCustomer(Customer(100, "Joe", "Smith", Nil)).call()

      call.foreach { customerOrErrors =>

        outputBox.innerHTML = ""
        outputBox.appendChild(
          ul(
            for (e <- customerOrErrors.right.get) yield {
              li(BusinessMessages.messageFor(e.errorCode))
            }
          ).render
        )
      }
    }
    inputBox.onkeyup = { (e: dom.Event) =>
      updateOutput()
    }

    updateOutput()
    dom.document.body.appendChild(
      div(
        cls := "container",
        h1("Results container"),
        outputBox
      ).render
    )
  }
}
