package sless.ast.visitor

import sless.ast.node.{DeclarationNode, FlatRuleNode, FlatSheet, PropertyNode, RuleNode, SlessSheet, ValueNode}

object MarginAggregator {
  def apply(css: SlessSheet): (Boolean, SlessSheet) = visitSlessSheet(css)

  private def visitSlessSheet(css: SlessSheet): (Boolean, SlessSheet) =
    css.flatten()
      .rules
      .foldLeft((false, FlatSheet(Seq())))((res, rule) => {
        val (edited, sheet) = res

        val top = rule.declarations.indexWhere(_.property.name == "margin-top")
        val right = rule.declarations.indexWhere(_.property.name == "margin-right")
        val bottom = rule.declarations.indexWhere(_.property.name == "margin-bottom")
        val left = rule.declarations.indexWhere(_.property.name == "margin-left")

        if (top == -1 || right == -1 || bottom == -1 || left == -1) {
          return (edited, FlatSheet(sheet.rules :+ rule))
        }

        val firstMargin = Seq(top, right, bottom, left).min
        val topValue = rule.declarations(top).value.value
        val rightValue = rule.declarations(right).value.value
        val bottomValue = rule.declarations(bottom).value.value
        val leftValue = rule.declarations(left).value.value
        val margin = DeclarationNode(PropertyNode("margin"), ValueNode(s"$topValue $rightValue $bottomValue $leftValue"))
        val newRule = FlatRuleNode(
          rule.selector,
          rule.declarations
            .updated(firstMargin, margin)
            .filterNot(d => Seq("margin-top", "margin-right", "margin-bottom", "margin-left").contains(d.property.name)),
          rule.comment)

        (true, FlatSheet(sheet.rules :+ newRule ))
      })

//  private def visitRuleNode(rule: FlatRuleNode): (Boolean, FlatRuleNode) = {
//    val top = rule.declarations.indexWhere(_.property.name == "margin-top")
//    val right = rule.declarations.indexWhere(_.property.name == "margin-right")
//    val bottom = rule.declarations.indexWhere(_.property.name == "margin-bottom")
//    val left = rule.declarations.indexWhere(_.property.name == "margin-left")
//
//    if (top == -1 || right == -1 || bottom == -1 || left == -1) {
//      return (false, rule)
//    }
//
//    val firstMargin = Seq(top, right, bottom, left).min
//    val topValue = rule.declarations(top).value.value
//    val rightValue = rule.declarations(right).value.value
//    val bottomValue = rule.declarations(bottom).value.value
//    val leftValue = rule.declarations(left).value.value
//    val margin = DeclarationNode(PropertyNode("margin"), ValueNode(s"$topValue $rightValue $bottomValue $leftValue"))
//    val newRule = FlatRuleNode(
//      rule.selector,
//      rule.declarations
//        .updated(firstMargin, margin)
//        .filterNot(d => Seq("margin-top", "margin-right", "margin-bottom", "margin-left").contains(d.property.name)),
//      rule.comment)
//
//    (true, newRule)
//  }
}
