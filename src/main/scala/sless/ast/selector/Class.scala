package sless.ast.selector

import sless.ast.SelectorNode

/**
  * The CSS class selector matches elements based on the contents of their class attribute.
  *
  * /* All elements with class="spacious" */
  * .spacious {
  *   margin: 2em;
  * }
  *
  * /* All <li> elements with class="spacious" */
  * li.spacious {
  *   margin: 2em;
  * }
  *
  * /* All <li> elements with a class list that includes both "spacious" and "elegant" */
  * /* For example, class="elegant retro spacious" */
  * li.spacious.elegant {
  *   margin: 2em;
  * }
  *
  * @param selector
  * @param className
  */
case class Class(selector: SelectorNode, className: String) extends SelectorNode {
  override def compile(): String = s"${selector.compile()}.$className"

  override def pretty(): String = s"${selector.pretty()}.$className"
}
