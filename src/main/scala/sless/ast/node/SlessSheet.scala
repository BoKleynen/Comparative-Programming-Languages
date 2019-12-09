package sless.ast.node

sealed abstract class SlessSheet extends Node {
  def flatten(): FlatSheet
}

case class SingularSheet(rules: Seq[RuleNode]) extends SlessSheet {
  override def flatten(): FlatSheet = FlatSheet(rules.flatMap(_.flatten()))
}

case class NestedSheet(sheets: Seq[SlessSheet]) extends SlessSheet {
  override def flatten(): FlatSheet = {
    val sheet +: flatSheets = sheets.map(_.flatten())
    val mergedRules = flatSheets.foldLeft(sheet.rules)((res, sheet) => mergeRules(res, sheet.rules))
    FlatSheet(mergedRules)
  }

  private def mergeRules(lhsRules: Seq[FlatRuleNode], rhsRules: Seq[FlatRuleNode]): Seq[FlatRuleNode] = {
    rhsRules.foldLeft(lhsRules)((res, rhsRule) => {
      res.indexWhere(_.selector match {
        case List(selectors) => selectors.contains(rhsRule.selector)
        case s => s == rhsRule.selector
      }) match {
        case -1 => res :+ rhsRule
        case i =>
          val resRule = res(i)
          val mergedDeclarations = rhsRule.declarations.foldLeft(resRule.declarations)((resDecl, declaration) => {
            resDecl.indexWhere(_.property == declaration.property) match {
              case -1 => declaration +: resDecl
              case j => resDecl.updated(j, declaration)
            }
          })
          resRule.selector match {
            case List(selectors) => res
              .updated(i, FlatRuleNode(List(selectors.filterNot(_ == rhsRule.selector)), resRule.declarations)) :+
              FlatRuleNode(rhsRule.selector, mergedDeclarations)
            case selector => res.updated(i, FlatRuleNode(selector, mergedDeclarations))
          }
      }
    })
  }
}


case class FlatSheet(rules: Seq[FlatRuleNode]) extends SlessSheet {
  override def flatten(): FlatSheet = this
}