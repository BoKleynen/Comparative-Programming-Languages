package sless.ast.selector

import sless.ast.SelectorNode

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
case class Adjacent(lhs: SelectorNode, rhs: SelectorNode) extends SelectorNode {
  override def compile(): String = s"${lhs.compile()}+${rhs.compile()}"

  override def pretty(): String = s"${lhs.pretty()} + ${rhs.pretty()}"
}
