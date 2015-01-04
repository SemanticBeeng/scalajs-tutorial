package business

import business.domain._
import autowire._
import org.scalajs.dom
import upickle._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
/**
 *
 */
object Client extends autowire.Client[String, upickle.Reader, upickle.Writer] {

  override def doCall(req: Request): Future[String] = {

    print(req.path)
    dom.extensions.Ajax.post(
      url = "/api/" + req.path.mkString("/"),
      data = upickle.write(req.args)
    ).map(_.responseText)
  }

  def write[Result: upickle.Writer](r: Result) = upickle.write(r)

  def read[Result: upickle.Reader](p: String) = upickle.read[Result](p)
}

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
