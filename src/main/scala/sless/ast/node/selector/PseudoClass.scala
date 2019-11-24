package sless.ast.node.selector

import sless.ast.node.SelectorNode

/**
  * A CSS pseudo-class is a keyword added to a selector that specifies a special state of the
  * selected element(s). For example, :hover can be used to change a button's color when the
  * user's pointer hovers over it.
  *
  * /* Any button over which the user's pointer is hovering */
  * button:hover {
  *   color: blue;
  * }
  *
  * @param selector
  * @param action
  */
case class PseudoClass(selector: SelectorNode, action: String) extends SelectorNode
