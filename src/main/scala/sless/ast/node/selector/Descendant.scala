package sless.ast.node.selector

import sless.ast.node.SelectorNode

/**
  * The descendant combinator — typically represented by a single space ( ) character —
  * combines two selectors such that elements matched by the second selector are selected if they
  * have an ancestor (parent, parent's parent, parent's parent's parent, etc) element matching the
  * first selector. Selectors that utilize a descendant combinator are called descendant selectors.
  *
  * /* List items that are descendants of the "my-things" list */
  * ul.my-things li {
  *   margin: 2em;
  * }
  *
  * @param lhs
  * @param rhs
  */
case class Descendant(lhs: SelectorNode, rhs: SelectorNode) extends SelectorNode
