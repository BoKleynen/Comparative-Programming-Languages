package sless.ast

class PropertyNode(val name: String) extends Node {
  def compile(): String = name

  def pretty(): String = name
}
