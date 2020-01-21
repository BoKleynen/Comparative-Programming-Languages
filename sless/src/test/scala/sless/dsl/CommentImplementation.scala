package sless.dsl

import sless.ast.Sless

object CommentImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with CommentDSL with Compilable
  val dsl: DSL = new Sless
}
