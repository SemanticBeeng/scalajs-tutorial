package business

import org.scalajs.dom
import upickle._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

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
