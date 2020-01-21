package sless.dsl

import sless.ast.Sless

object MergeImplementation {
  type DSL = PropertyDSL with SelectorDSL with ValueDSL with MergeDSL with Compilable
  val dsl: DSL = new Sless
}
