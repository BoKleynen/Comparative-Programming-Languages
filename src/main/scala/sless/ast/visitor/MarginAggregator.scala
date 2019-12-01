package sless.ast.visitor

import sless.ast.node.{RuleNode, SlessSheet}

// TODO
object MarginAggregator {
  def apply(css: SlessSheet): (Boolean, SlessSheet) = visitSlessSheet(css)

  private def visitSlessSheet(css: SlessSheet): (Boolean, SlessSheet) = ???
//  {
//    val res = css.rules.map(visitRuleNode)
//    if (res.exists(_._1)) {
//      (true, new SlessSheet(res.filterNot(_._1).map(_._2)))
//    } else {
//      (false, css)
//    }
//  }

  private def visitRuleNode(rule: RuleNode): (Boolean, RuleNode) = ???
}
