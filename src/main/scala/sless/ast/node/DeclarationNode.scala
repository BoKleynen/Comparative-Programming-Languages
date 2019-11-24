package sless.ast.node
import sless.ast.visitor.Visitor

class DeclarationNode(val property: PropertyNode, val value: ValueNode) extends Node{
  override def accept[T](v: Visitor[T]): T = v.visitDeclarationNode(this)
}
