package sless.ast.node

class RuleNode(
                val selector: SelectorNode,
                val declarations: Seq[DeclarationNode],
                val comment: Option[CommentNode] = None
              ) extends Node
