package sless.ast.visitor

import sless.ast.node.{RuleNode, SlessSheet}

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

  private def visitRuleNode(rule: RuleNode): (Boolean, RuleNode) = (rule.declarations.isEmpty, rule)
}
