package sless.ast

class DeclarationNode(val property: PropertyNode, val value: ValueNode) extends Node{
  def compile(): String = s"${property.compile()}:${value.compile()}"

  def pretty(): String = s"${property.pretty()}: ${value.pretty()}"
}
