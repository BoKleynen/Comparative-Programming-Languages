package sless.ast.node.selector

import sless.ast.node.SelectorNode

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
case class List(selectors: Seq[SelectorNode]) extends SelectorNode
