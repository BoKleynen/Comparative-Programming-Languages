package sless.ast

class SlessSheet(val rules: Seq[RuleNode]) extends Node {
  def compile(): String = rules.map(_.compile()).mkString("")

  def pretty(spaces: Int): String = rules.map(_.pretty(spaces)).mkString("\n\n")
}
