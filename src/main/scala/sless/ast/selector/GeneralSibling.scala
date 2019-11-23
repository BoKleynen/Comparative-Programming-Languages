package sless.ast.selector

import sless.ast.SelectorNode

/**
  * The general sibling combinator (~) separates two selectors and matches the second
  * element only if it follows the first element (though not necessarily immediately),
  * and both are children of the same parent element.
  *
  * /* Paragraphs that are siblings of and
  *    subsequent to any image */
  * img ~ p {
  *   color: red;
  * }
  *
  * @param lhs
  * @param rhs
  */
case class GeneralSibling(lhs: SelectorNode, rhs: SelectorNode) extends SelectorNode {
  override def compile(): String = s"${lhs.compile()}~${rhs.compile()}"

  override def pretty(): String = s"${lhs.pretty()} ~ ${rhs.pretty()}"
}
