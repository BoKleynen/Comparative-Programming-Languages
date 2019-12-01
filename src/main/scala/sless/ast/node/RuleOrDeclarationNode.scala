package sless.ast.node

sealed abstract class RuleOrDeclarationNode extends Node

case class DeclarationNode(
  property: PropertyNode,
  value: ValueNode,
  comment: Option[CommentNode] = None
) extends RuleOrDeclarationNode

sealed abstract class RuleNode extends RuleOrDeclarationNode {
  def flatten(parent: Option[SelectorNode] = None): Seq[FlatRuleNode]
}

case class NestedRuleNode(
 selector: SelectorNode,
 nodes: Seq[RuleOrDeclarationNode],
 comment: Option[CommentNode] = None
) extends RuleNode {
  override def flatten(parent: Option[SelectorNode] = None): Seq[FlatRuleNode] = {
    val (rules, rule) = nodes.foldLeft((Seq[FlatRuleNode](), FlatRuleNode(selector, Seq[DeclarationNode](), comment)))((res, node) => {
      val (rules, rule) = res
      val flatSelector = selector.flatten(parent)

      node match {
        case d: DeclarationNode => (rules, FlatRuleNode(flatSelector, rule.declarations :+ d, rule.comment))
        case r: RuleNode => (rules ++ r.flatten(Some(flatSelector)), rule)
      }
    })

    if (rule.declarations.nonEmpty) {
      rule +: rules
    } else {
      rules
    }
  }
}

case class FlatRuleNode(
 selector: SelectorNode,
 declarations: Seq[DeclarationNode],
 comment: Option[CommentNode] = None
) extends RuleNode {
  override def flatten(parent: Option[SelectorNode] = None): Seq[FlatRuleNode] =
    Seq(FlatRuleNode(selector.flatten(parent), declarations, comment))
}
