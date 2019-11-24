package sless.ast.node
import sless.ast.visitor.Visitor

class RuleNode(val selector: SelectorNode, val declarations: Seq[DeclarationNode]) extends Node {
  override def accept[T](v: Visitor[T]): T = v.visitRuleNode(this)
}
