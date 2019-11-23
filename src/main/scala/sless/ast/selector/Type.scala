package sless.ast.selector

import sless.ast.SelectorNode

/**
  * The CSS type selector matches elements by node name. In other words, it selects all
  * elements of the given type within a document.
  *
  * /* All <a> elements. */
  * a {
  *   color: red;
  * }
  *
  * @param name
  */
case class Type(name: String) extends SelectorNode {
  override def compile(): String = name

  override def pretty(): String = name
}
