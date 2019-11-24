package sless.ast.node
import sless.ast.visitor.Visitor

class SlessSheet(val rules: Seq[RuleNode]) extends Node {
  override def accept[T](v: Visitor[T]): T = v.visitSlessSheet(this)
}
