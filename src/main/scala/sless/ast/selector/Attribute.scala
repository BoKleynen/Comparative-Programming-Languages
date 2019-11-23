package sless.ast.selector

import sless.ast.{SelectorNode, ValueNode}

/**
  * The CSS attribute selector matches elements based on the presence or value of a given attribute.
  *
  * /* <a> elements with a title attribute */
  * a[title] {
  *   color: purple;
  * }
  *
  * /* <a> elements with an href matching "https://example.org" */
  * a[href="https://example.org"] {
  *   color: green;
  * }
  *
  * /* <a> elements with an href containing "example" */
  * a[href*="example"] {
  *   font-size: 2em;
  * }
  *
  * /* <a> elements with an href ending ".org" */
  * a[href$=".org"] {
  *   font-style: italic;
  * }
  *
  * /* <a> elements whose class attribute contains the word "logo" */
  * a[class~="logo"] {
  *   padding: 2px;
  * }
  *
  * @param selector
  * @param attr
  * @param value
  */
case class Attribute(selector: SelectorNode, attr: String, value: ValueNode) extends SelectorNode {
  override def compile(): String = s"""${selector.compile()}[$attr="${value.compile()}"]"""

  override def pretty(): String = s"""${selector.pretty()}[$attr="${value.pretty()}"]"""
}
