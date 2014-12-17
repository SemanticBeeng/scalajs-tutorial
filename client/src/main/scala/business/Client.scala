import upickle.{Reader, Writer}

object lient extends autowire.Client[String, upickle.Reader, upickle.Writer]{
  def write[Result: Writer](r: Result) = upickle.write(r)
  def read[Result: Reader](p: String) = upickle.read[Result](p)

  override def doCall(req: Request) = {
    println(req)
    Server.routes.apply(req)
  }
}