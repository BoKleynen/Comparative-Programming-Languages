package sless.ast

class RuleNode(val selector: SelectorNode, val declarations: Seq[DeclarationNode]) extends Node {
  def compile(): String = s"${selector.compile()}{${declarations.map(_.compile()).mkString("", ";", ";")}}"

  def pretty(spaces: Int): String = {
    val indentation = " " * spaces
    s"""${selector.pretty()} {
${declarations.map(_.pretty()).mkString(indentation, s";\n$indentation", ";")}
}"""
  }
}
