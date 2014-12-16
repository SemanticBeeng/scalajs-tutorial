package tutorial.webapp

import org.scalajs.dom
import dom.document

/**
 *
 */
class TutorialAppHtml {
  def main(): Unit = {
    appendPar(document.body, "Hello World")
  }


  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }
}
