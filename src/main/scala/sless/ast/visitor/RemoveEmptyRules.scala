package sless.ast.visitor

import sless.ast.node.{DeclarationNode, FlatRuleNode, NestedRuleNode, RuleNode, SlessSheet}

// TODO: Write test for nested rules
object RemoveEmptyRules {
  def apply(css: SlessSheet): (Boolean, SlessSheet) = visitSlessSheet(css)

  private def visitSlessSheet(css: SlessSheet): (Boolean, SlessSheet) = {
    val res = css.rules.map(visitRuleNode)
    if (res.exists(_._1)) {
      (true, SlessSheet(res.filterNot(_._1).map(_._2)))
    } else {
      (false, css)
    }
  }

  private def visitRuleNode(rule: RuleNode): (Boolean, RuleNode) = rule match {
    case NestedRuleNode(selector, nodes, comment) => {
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

    case FlatRuleNode(_, declarations, _) => (declarations.isEmpty, rule)
  }
}
