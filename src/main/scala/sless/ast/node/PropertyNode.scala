package sless.ast.node
import sless.ast.visitor.Visitor

class PropertyNode(val name: String) extends Node {
  override def accept[T](v: Visitor[T]): T = v.visitPropertyNode(this)
}
