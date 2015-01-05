package scala.scalajs.js.annotation

/**
 * For rationale for copying this class see
 * https://groups.google.com/forum/#!topic/scala-js/knNTjVcss8E
 */
/** Specifies that the given entity should be exported for use in raw JS.
  *
  *  @see [[http://www.scala-js.org/doc/export-to-javascript.html Export Scala.js APIs to JavaScript]]
  */
class JSExport extends scala.annotation.StaticAnnotation {
  def this(name: String) = this()
}