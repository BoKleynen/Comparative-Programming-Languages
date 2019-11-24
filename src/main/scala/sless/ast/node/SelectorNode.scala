package sless.ast.node
import sless.ast.visitor.Visitor

trait SelectorNode extends Node {
  def accept[T](v: Visitor[T]): T = v.visitSelectorNode(this)
}

