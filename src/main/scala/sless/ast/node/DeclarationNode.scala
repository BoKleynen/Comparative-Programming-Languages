package sless.ast.node

class DeclarationNode(
                       val property: PropertyNode,
                       val value: ValueNode,
                       val comment: Option[CommentNode] = None
                     ) extends Node
