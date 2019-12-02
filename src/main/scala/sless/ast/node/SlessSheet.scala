package sless.ast.node

sealed abstract class SlessSheet extends Node {
  def flatten(): SingularSheet
}

case class SingularSheet(rules: Seq[RuleNode]) extends SlessSheet {
  override def flatten(): SingularSheet = this
}

case class MergedSheet(sheets: Seq[SlessSheet]) extends SlessSheet {
  override def flatten(): SingularSheet = ???
}
