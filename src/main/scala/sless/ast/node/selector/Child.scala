package sless.ast.node.selector

import sless.ast.node.SelectorNode

/**
  * The child combinator (>) is placed between two CSS selectors. It matches only those
  * elements matched by the second selector that are the direct children of elements
  * matched by the first.
  *
  * /* List items that are children of the "my-things" list */
  * ul.my-things > li {
  *   margin: 2em;
  * }
  *
  * @param lhs
  * @param rhs
  */
case class Child(lhs: SelectorNode, rhs: SelectorNode) extends SelectorNode
