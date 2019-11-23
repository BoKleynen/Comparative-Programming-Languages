package sless.ast

class ValueNode(val value: String) extends Node {
  def compile(): String = value

  def pretty(): String = value
}
