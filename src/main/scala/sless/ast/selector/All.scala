package sless.ast.selector

import sless.ast.SelectorNode

case object All extends SelectorNode {
  override def compile(): String = "*"

  override def pretty(): String = "*"
}
