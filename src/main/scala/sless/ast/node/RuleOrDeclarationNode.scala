package sless.ast.node

sealed abstract class RuleOrDeclarationNode extends Node

case class DeclarationNode(
                            property: PropertyNode,
                            value: ValueNode,
                            comment: Option[CommentNode] = None
                          ) extends RuleOrDeclarationNode

sealed abstract class RuleNode extends RuleOrDeclarationNode {
  def flatten(): Seq[FlatRuleNode]
}

case class NestedRuleNode(
                           selector: SelectorNode,
                           nodes: Seq[RuleOrDeclarationNode],
                           comment: Option[CommentNode] = None
                         ) extends RuleNode
{
  override def flatten(): Seq[FlatRuleNode] = ???
}

case class FlatRuleNode(
                         selector: SelectorNode,
                         declarations: Seq[DeclarationNode],
                         comment: Option[CommentNode] = None
                       ) extends RuleNode
{
  override def flatten(): Seq[FlatRuleNode] = Seq(this)
}
