package sless.ast.visitor
import sless.ast.node.{DeclarationNode, PropertyNode, RuleNode, SlessSheet}

object PropertyCounter {
  def apply(css: SlessSheet, property: String): Int = new PropertyCounter(property).visitSlessSheet(css)
}

class PropertyCounter(val property: String) {
  def visitSlessSheet(css: SlessSheet): Int = css.rules.map(visitRuleNode).sum

  def visitRuleNode(rule: RuleNode): Int = rule.declarations.map(visitDeclarationNode).sum

  def visitDeclarationNode(declaration: DeclarationNode): Int = visitPropertyNode(declaration.property)

  def visitPropertyNode(property: PropertyNode): Int = if (property.name == this.property) { 1 } else { 0 }
}
