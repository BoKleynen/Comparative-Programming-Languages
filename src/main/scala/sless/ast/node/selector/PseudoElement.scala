package sless.ast.node.selector

import sless.ast.node.SelectorNode

/**
  * A CSS pseudo-element is a keyword added to a selector that lets you style a specific part of
  * the selected element(s). For example, ::first-line can be used to change the font of the
  * first line of a paragraph.
  *
  * p::first-line {
  *   color: blue;
  *   text-transform: uppercase;
  * }
  *
  * @param selector
  * @param part
  */
case class PseudoElement(selector: SelectorNode, part: String) extends SelectorNode
