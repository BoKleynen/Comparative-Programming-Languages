package sless.ast.node
import sless.ast.visitor.Visitor

class ValueNode(val value: String) extends Node {
  override def accept[T](v: Visitor[T]): T = v.visitValueNode(this)
}
