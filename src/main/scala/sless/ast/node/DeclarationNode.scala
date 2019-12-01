package sless.ast.node

case class DeclarationNode(
                            property: PropertyNode,
                            value: ValueNode,
                            comment: Option[CommentNode] = None
                     ) extends Node
