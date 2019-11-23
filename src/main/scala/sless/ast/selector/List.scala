package sless.ast.selector

import sless.ast.SelectorNode

/**
  * The CSS selector list (,) selects all the matching nodes.
  *
  * /* Selects all matching elements */
  * span,
  * div {
  *   border: red 2px solid;
  * }
  *
  * # Syntax
  * element, element, element { style properties }
  *
  * @param selectors
  */
case class List(selectors: Seq[SelectorNode]) extends SelectorNode {
  override def compile(): String = selectors.map(_.compile()).mkString(",")

  override def pretty(): String = selectors.map(_.pretty()).mkString(", ")
}
