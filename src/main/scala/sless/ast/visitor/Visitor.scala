package sless.ast.visitor

import sless.ast.node._

trait Visitor[T] {
  def visitSlessSheet(css: SlessSheet): T

  def visitRuleNode(rule: RuleNode): T

  def visitSelectorNode(selector: SelectorNode): T

  def visitDeclarationNode(declaration: DeclarationNode): T

  def visitPropertyNode(property: PropertyNode): T

  def visitValueNode(value: ValueNode): T
}
