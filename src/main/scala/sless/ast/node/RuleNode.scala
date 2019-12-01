package sless.ast.node

case class RuleNode(
                     selector: SelectorNode,
                     declarations: Seq[DeclarationNode],
                     comment: Option[CommentNode] = None
                   ) extends Node
