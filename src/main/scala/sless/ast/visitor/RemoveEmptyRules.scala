package sless.ast.visitor

import sless.ast.node.{DeclarationNode, FlatRuleNode, MergedSheet, NestedRuleNode, RuleNode, SingularSheet, SlessSheet}

// TODO: Write test for nested rules
// TODO: Write test for merged sheets
object RemoveEmptyRules {
  def apply(css: SlessSheet): (Boolean, SlessSheet) = visitSlessSheet(css)

  private def visitSlessSheet(css: SlessSheet): (Boolean, SlessSheet) = css match {
    case SingularSheet(rules) =>
      val res = rules.map(visitRuleNode)
      if (res.exists(_._1)) {
        (true, SingularSheet(res.filterNot(_._1).map(_._2)))
      } else {
        (false, css)
      }

    case MergedSheet(sheets) =>
      val res = sheets.map(visitSlessSheet)
      if (res.exists(_._1)) {
        (true, MergedSheet(res.filterNot(_._1).map(_._2)))
      } else {
        (false, css)
      }
  }

  private def visitRuleNode(rule: RuleNode): (Boolean, RuleNode) = rule match {
    case FlatRuleNode(_, declarations, _) => (declarations.isEmpty, rule)

    case NestedRuleNode(selector, nodes, comment) =>
      val res = nodes
        .map {
          case d: DeclarationNode => (false, d)
          case r: RuleNode => visitRuleNode(r)
        }

      if (res.exists(_._1)) {
        (true, NestedRuleNode(selector, res.filterNot(_._1).map(_._2), comment))
      } else {
        (false, rule)
      }
  }
}
