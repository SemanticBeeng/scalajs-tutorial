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
