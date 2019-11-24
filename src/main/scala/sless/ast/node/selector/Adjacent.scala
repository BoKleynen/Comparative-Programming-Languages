package sless.ast.node.selector

import sless.ast.node.SelectorNode

/**
  * The adjacent sibling combinator (+) separates two selectors and matches the second
  * element only if it immediately follows the first element, and both are children of
  * the same parent element.
  *
  * img + p {
  *   font-style: bold;
  * }
  *
  * @param lhs
  * @param rhs
  */
case class Adjacent(lhs: SelectorNode, rhs: SelectorNode) extends SelectorNode
