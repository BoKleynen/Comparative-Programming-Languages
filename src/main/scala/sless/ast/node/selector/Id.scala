package sless.ast.node.selector

import sless.ast.node.SelectorNode

/**
  * The CSS ID selector matches an element based on the value of its id attribute. In order for
  * the element to be selected, its ID attribute must match exactly the value given in the selector.
  *
  * /* The element with id="demo" */
  * #demo {
  *   border: red 2px solid;
  * }
  *
  * @param selector
  * @param id
  */
case class Id(selector: SelectorNode, id: String) extends SelectorNode
