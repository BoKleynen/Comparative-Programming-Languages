package sless.ast.visitor
import sless.ast.node.{DeclarationNode, FlatRuleNode, NestedRuleNode, PropertyNode, RuleNode, RuleOrDeclarationNode, SlessSheet}

object PropertyCounter {
  def apply(css: SlessSheet, property: String): Int = new PropertyCounter(property).visitSlessSheet(css)
}

class PropertyCounter(val property: String) {
  def visitSlessSheet(css: SlessSheet): Int = css.rules.map(visitRuleNode).sum

  def visitRuleNode(rule: RuleNode): Int = rule match {
    case FlatRuleNode(_, declarations, _) => declarations.map(visitDeclarationNode).sum
    case NestedRuleNode(_, declarations, _) => declarations.map(visitRuleOrDeclarationNode).sum
  }

  def visitRuleOrDeclarationNode(node: RuleOrDeclarationNode): Int = node match {
    case DeclarationNode(property, _, _) => visitPropertyNode(property)
    case r: RuleNode => visitRuleNode(r)
  }

  def visitDeclarationNode(declaration: DeclarationNode): Int = visitPropertyNode(declaration.property)

  def visitPropertyNode(property: PropertyNode): Int = if (property.name == this.property) { 1 } else { 0 }
}
