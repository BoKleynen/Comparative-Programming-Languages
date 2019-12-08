package sless.ast.node

sealed abstract class SlessSheet extends Node {
  def flatten(): FlatSheet
}

case class SingularSheet(rules: Seq[RuleNode]) extends SlessSheet {
  override def flatten(): FlatSheet = FlatSheet(rules.flatMap(_.flatten()))
}

// TODO: Make understandable :D
case class NestedSheet(sheets: Seq[SlessSheet]) extends SlessSheet {
  override def flatten(): FlatSheet = {
    val sheet +: flatSheets = sheets.map(_.flatten())
    flatSheets.foldLeft(sheet)((res, sheet) => {
      val rules = sheet.rules.foldLeft(res.rules)((rules, rule) => {
        rules.indexWhere(_.selector match {
          case List(selectors) => selectors.contains(rule.selector)
          case s => s == rule.selector
        }) match {
          case -1 => rules :+ rule
          case i =>
            val resRule = rules(i)
            val mergedDeclarations = rule.declarations.foldLeft(resRule.declarations)((resDecl, declaration) => {
              resDecl.indexWhere(_.property == declaration.property) match {
                case -1 => declaration +: resDecl
                case j => resDecl.updated(j, declaration)
              }
            })
            resRule.selector match {
              case List(selectors) => rules
                .updated(i, FlatRuleNode(List(selectors.filterNot(_ == rule.selector)), resRule.declarations)) :+
                  FlatRuleNode(rule.selector, mergedDeclarations)
              case selector => rules.updated(i, FlatRuleNode(selector, mergedDeclarations))
            }
        }
      })

      FlatSheet(rules)
    })
  }
}


case class FlatSheet(rules: Seq[FlatRuleNode]) extends SlessSheet {
  override def flatten(): FlatSheet = this
}